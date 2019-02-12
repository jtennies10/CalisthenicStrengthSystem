package tenniescorp.fitness.calisthenicstrengthsystem;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/*
Defines the RoutineListActivity, which is the opening Activity once a user is logged in.
In this activity the user can view all routines with their favorited routines listed first,
as well as can select to create a new routine or click on a routine to see it in detail.
 */
public class RoutineListActivity extends AppCompatActivity {

    public static final int NEW_ROUTINE_ACTIVITY_REQUEST_CODE = 1;
    private RoutineViewModel routineViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_routine_list);

        //set the activity toolbar
        Toolbar toolbar = findViewById(R.id.routine_list_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);


        RecyclerView recyclerView = findViewById(R.id.recyclerview);

        //create a click listener to start the RoutineDescriptionActivity
        RecyclerViewClickListener clickListener = (view, position) -> {
            Intent intent = new Intent(RoutineListActivity.this, RoutineDescriptionActivity.class);

            //send the selected Routine with the intent
            intent.putExtra("Routine",
                    routineViewModel.getAllRoutines().getValue().get(position));

            startActivity(intent);
        };

        //create the RoutineListAdapter and attach it to the recycler view
        final RoutineListAdapter adapter = new RoutineListAdapter(this, clickListener);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //assign the routineViewModel and set it to observe the adapter
        routineViewModel = ViewModelProviders.of(this).get(RoutineViewModel.class);

        routineViewModel.getAllRoutines().observe(this, routines -> adapter.setRoutines(routines));

        //get the newRoutineButton and give it a click listener that starts the NewRoutineActivity
        FloatingActionButton newRoutineButton = findViewById(R.id.routine_list_add_button);
        newRoutineButton.setOnClickListener(v -> {
            Intent intent = new Intent(RoutineListActivity.this, NewRoutineActivity.class);
            startActivityForResult(intent, NEW_ROUTINE_ACTIVITY_REQUEST_CODE);

        });

    }

    /*
    Handles returning intents from the NewRoutineActivity by adding the routine and it's
    associated exercises to their appropriate tables in the database if RESULT_OK is the code returned
    with the intent
     */
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //if the returning intent is from NewRoutineActivity, get the routine and routineExercises\
        //and insert them into the database
        if(requestCode == NEW_ROUTINE_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            Routine routine = (Routine) data.getSerializableExtra("Routine");
            List<Exercise> routineExercises = (ArrayList) data.getSerializableExtra("Exercises");


            CSSRoomDatabase db = CSSRoomDatabase.getDatabase(getApplicationContext());
            long routineId = db.routineDao().insert(routine); //gets the routineId of the newly inserted routine

            //insert the routineExercises if there are any
            if(routineExercises != null) {
                for (int i = 0; i < routineExercises.size(); i++) {
                    RoutineExercise routineExercise = new RoutineExercise(routineId, routineExercises.get(i).getExerciseId());
                    Log.d(routineId + ",", " " + routineExercises.get(i).getExerciseId());
                    db.routineExerciseDao().insert(routineExercise);
                }
            }

        } else {
            Toast.makeText(
                    getApplicationContext(),
                    R.string.new_routine_empty_not_saved,
                    Toast.LENGTH_LONG).show();
        }
    }

    /*
    Toggles the options that drop down from the toolbar
     */
    public void toggleMoreOptions(View view) {
        Button weightTrackerBtn = findViewById(R.id.weight_tracker_button);
        Button signOutBtn = findViewById(R.id.sign_out_button);

        if(weightTrackerBtn.getVisibility() == View.GONE) {
            weightTrackerBtn.setVisibility(View.VISIBLE);
            signOutBtn.setVisibility(View.VISIBLE);
        } else {
            weightTrackerBtn.setVisibility(View.GONE);
            signOutBtn.setVisibility(View.GONE);
        }

    }

    public void startWeightTracker(View view) {
        Intent intent = new Intent(this, WeightTrackerActivity.class);
        startActivity(intent);
    }

    /*
    Signs the user out and deletes the user information from SharedPreferences
     */
    public void signOut(View view) {
        SharedPreferences preferences = getSharedPreferences("UserPreferences", 0);
        SharedPreferences.Editor preferencesEditor = preferences.edit();
        preferencesEditor.remove("userId");
        preferencesEditor.remove("userName");
        preferencesEditor.remove("userEmail");
        preferencesEditor.apply();

        Intent intent = new Intent(this, LogInActivity.class);
        startActivity(intent);
    }
}
