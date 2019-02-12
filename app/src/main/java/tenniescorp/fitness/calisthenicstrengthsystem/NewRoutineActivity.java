package tenniescorp.fitness.calisthenicstrengthsystem;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/*
Defines the activity that allows a user to create a new routine.
 */
public class NewRoutineActivity extends AppCompatActivity {
    public static final int NEW_ROUTINE_ACTIVITY_CODE = 1;

    //member variables
    private EditText newRoutineName;
    private EditText newRoutineDescription;
    private ArrayList<Exercise> routineExercises;
    private Routine currentRoutine;
    private RecyclerView recyclerView;
    private RecyclerViewClickListener clickListener;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_routine);

        //set activity toolbar
        Toolbar toolbar = findViewById(R.id.new_routine_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        newRoutineName = findViewById(R.id.new_routine_name);
        newRoutineDescription = findViewById(R.id.new_routine_description);

        //look for the intent that started the activity and see if a routine is found
        Bundle bundle = (getIntent().getBundleExtra("Routine Bundle"));

        //create a click listener that opens an ExerciseDescriptionActivity for the given exercise that
        //is clicked
        clickListener = (view, position) -> {
            Intent intent = new Intent(NewRoutineActivity.this, ExerciseDescriptionActivity.class);

            //send the selected Exercise with the intent
            intent.putExtra("Exercise", routineExercises.get(position));
            startActivity(intent);
        };

        //if the bundle is not null, populate the views in the activity with the existing
        //routine information
        if(bundle != null) {
            currentRoutine = (Routine) bundle.getSerializable("Routine");
            newRoutineName.setText(currentRoutine.getRoutineName());
            newRoutineDescription.setText(currentRoutine.getRoutineDescription());

            routineExercises = (ArrayList) bundle.getSerializable("Exercises");
        }

        //get recycler view and populate using query to get all exercises with the correct workout_id
        recyclerView = findViewById(R.id.recyclerview);

        //create the list adapter and fill it with the data, then attach it to recyclerView
        final RoutineExerciseAdapter adapter = new RoutineExerciseAdapter(getApplicationContext(), routineExercises, clickListener);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //get the save button and assign a click listener that sends the routine name and description
        //as well as the routine exercises back to the calling intent
        final Button saveButton = findViewById(R.id.new_routine_save_button);
        saveButton.setOnClickListener(view -> {
            Intent replyIntent = new Intent();
            if (TextUtils.isEmpty(newRoutineName.getText())) {
                setResult(RESULT_CANCELED, replyIntent);
            } else {
                //if their is no existing routine in the member variable, assign it a new one
                if(currentRoutine == null) currentRoutine = new Routine(
                        newRoutineName.getText().toString(), newRoutineDescription.getText().toString());
                else {
                    currentRoutine.setRoutineName(newRoutineName.getText().toString());
                    currentRoutine.setRoutineDescription(newRoutineDescription.getText().toString());
                }
                replyIntent.putExtra("Routine", currentRoutine);
                replyIntent.putExtra("Exercises", routineExercises);
                setResult(RESULT_OK, replyIntent);
            }
            finish();
        });

        //get the add exercise button and start the ExerciseListActivity
        final Button addExerciseButton = findViewById(R.id.new_routine_add_button);
        addExerciseButton.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), ExerciseListActivity.class);
            startActivityForResult(intent, NEW_ROUTINE_ACTIVITY_CODE);
        });

    }

    /*
    Handles results of intents that were previously called by NewRooutineActivity.
    Sets the routine's exercises to the exercises passed with the intent, or alerts the user the routine
    was unsuccessful.
     */
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == NEW_ROUTINE_ACTIVITY_CODE && resultCode == RESULT_OK) {
            routineExercises = (ArrayList) data.getSerializableExtra("Exercises");

            final RoutineExerciseAdapter adapter = new RoutineExerciseAdapter(getApplicationContext(), routineExercises, clickListener);
            recyclerView.setAdapter(adapter);

        } else {
            Toast.makeText(
                    getApplicationContext(),
                    "Exercises not saved",
                    Toast.LENGTH_LONG).show();
        }
    }
}
