package tenniescorp.fitness.calisthenicstrengthsystem;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class RoutineDescriptionActivity extends AppCompatActivity {
    public static final int EDIT_EXERCISES_ROUTINE_DESCRIPTION_ACTIVITY_CODE = 2;
    public static final int EDIT_ROUTINE_ROUTINE_DESCRIPTION_ACTIVITY_CODE = 3;

    Routine currentRoutine;
    ArrayList<Exercise> routineExercises;
    RecyclerView recyclerView;
    RecyclerViewClickListener clickListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_routine_description);

        Toolbar toolbar = findViewById(R.id.routine_description_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        TextView routineName = findViewById(R.id.routine_description_name);
        TextView routineDescription = findViewById(R.id.routine_description_description);

        //get recycler view and populate using query to get all exercises with the correct workout_id
        recyclerView = findViewById(R.id.recyclerview);

        currentRoutine = (Routine) getIntent().getSerializableExtra("Routine");
        Log.d("Routine name, id", currentRoutine.getRoutineName() + " " + currentRoutine.getRoutineId());
        routineName.setText(currentRoutine.getRoutineName());
        routineDescription.setText(currentRoutine.getRoutineDescription());

        if(currentRoutine.isFavorited()) {
            ImageView favoriteImage = findViewById(R.id.routine_description_favorite_star);
            favoriteImage.setImageResource(R.drawable.ic_star_black_24dp);
        }

        CSSRoomDatabase db = CSSRoomDatabase.getDatabase(this);
        routineExercises = new ArrayList<>(
                db.routineExerciseDao().getSpecificRoutineExercises(currentRoutine.getRoutineId()));


        clickListener = (view, position) -> {
            // Toast.makeText(view.getContext(), "Position " + position, Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(RoutineDescriptionActivity.this, ExerciseDescriptionActivity.class);

            //send the selected Exercise with the intent
            intent.putExtra("Exercise", routineExercises.get(position));
            startActivity(intent);
        };

        //create the list adapter and fill it with the data
        final RoutineExerciseAdapter adapter = new RoutineExerciseAdapter(getApplicationContext(), routineExercises, clickListener);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));



    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        CSSRoomDatabase db = CSSRoomDatabase.getDatabase(getApplicationContext());
        db.routineDao().update(currentRoutine);
    }

    public void toggleRoutineOptions(View v) {
        Button deleteRoutineBtn = findViewById(R.id.routine_delete_button);
        Button editExerciseBtn = findViewById(R.id.routine_edit_exercises_button);
        Button editRoutineBtn = findViewById(R.id.routine_edit_info_button);
        if(deleteRoutineBtn.getVisibility() == View.GONE) {
            deleteRoutineBtn.setVisibility(View.VISIBLE);
            editExerciseBtn.setVisibility(View.VISIBLE);
            editRoutineBtn.setVisibility(View.VISIBLE);
        } else {
            editRoutineBtn.setVisibility(View.GONE);
            editExerciseBtn.setVisibility(View.GONE);
            deleteRoutineBtn.setVisibility(View.GONE);
        }
    }

    public void toggleRoutineFavorite(View v) {
        ImageView star = findViewById(R.id.routine_description_favorite_star);
//        Set<String> favoriteRoutines = new HashSet<>();
//
//        SharedPreferences preferences = getApplicationContext().getSharedPreferences("UserPreferences", 0);
//        preferences.getStringSet("Favorited exercises", favoriteRoutines);
//        SharedPreferences.Editor preferencesEditor = preferences.edit();
//
//        String routineIds[] = favoriteRoutines.toArray();


        if(currentRoutine.isFavorited()) {
            star.setImageResource(R.drawable.ic_star_border_black_24dp);
            currentRoutine.setFavorited(false);
        } else {
            star.setImageResource(R.drawable.ic_star_black_24dp);
            currentRoutine.setFavorited(true);
        }
//        preferencesEditor.apply();
    }

    public void promptDeleteRoutine(View view) {
        LinearLayout deleteWarning = findViewById(R.id.delete_warning);
        deleteWarning.setVisibility(View.VISIBLE);

    }

    public void deleteRoutine(View view) {
        //delete the routine
        CSSRoomDatabase db = CSSRoomDatabase.getDatabase(getApplicationContext());
        db.routineDao().deleteSpecificRoutine(currentRoutine.getRoutineId());

        //use an intent to go back to the routine lists
        Intent intent = new Intent(this, RoutineListActivity.class);
        startActivity(intent);
    }

    public void cancelDeleteRoutine(View view) {
        //close the popup
        LinearLayout deleteWarning = findViewById(R.id.delete_warning);
        deleteWarning.setVisibility(View.GONE);
    }

    public void editExercises(View view) {
        //start exercise activity, sending current exercise list with it
        Intent intent = new Intent(getApplicationContext(), ExerciseListActivity.class);
        intent.putExtra("Routine", currentRoutine);
        startActivityForResult(intent, EDIT_EXERCISES_ROUTINE_DESCRIPTION_ACTIVITY_CODE);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK) {
                Routine routine = (Routine) data.getSerializableExtra("Routine");

                routineExercises = (ArrayList) data.getSerializableExtra("Exercises");
                //delete the current routineExercises
                CSSRoomDatabase db = CSSRoomDatabase.getDatabase(getApplicationContext());
                db.routineExerciseDao().deleteRoutineExercises(routine.getRoutineId());

                //insert the new routineExercise records
                for(int i = 0; i < routineExercises.size(); i++) {
                    RoutineExercise routineExercise = new RoutineExercise(routine.getRoutineId(), routineExercises.get(i).getExerciseId());
                    db.routineExerciseDao().insert(routineExercise);
                }

                final RoutineExerciseAdapter adapter = new RoutineExerciseAdapter(getApplicationContext(), routineExercises, clickListener);
                recyclerView.setAdapter(adapter);

                if(requestCode == EDIT_ROUTINE_ROUTINE_DESCRIPTION_ACTIVITY_CODE) {
                    Log.d("ROUTINE", "TRUE");
                    db.routineDao().update(routine);

                    //set the update routine name and description
                    TextView routineName = findViewById(R.id.routine_description_name);
                    TextView routineDescription = findViewById(R.id.routine_description_description);

                    Log.d("ROUTINE NAME", routine.getRoutineName());
                    Log.d("ROUTINE DESC", routine.getRoutineDescription());

                    routineName.setText(routine.getRoutineName());
                    routineDescription.setText(routine.getRoutineDescription());
                }

        }  else {
            Toast.makeText(
                    getApplicationContext(),
                    "Not saved",
                    Toast.LENGTH_LONG).show();
        }
    }

    public void editRoutineInfo(View view) {
        //start new routine activity with current routine info
        Intent intent = new Intent(this, NewRoutineActivity.class);

        Bundle b = new Bundle();
        b.putSerializable("Routine", currentRoutine);
        b.putSerializable("Exercises", routineExercises);

        intent.putExtra("Routine Bundle", b);
        startActivityForResult(intent, EDIT_ROUTINE_ROUTINE_DESCRIPTION_ACTIVITY_CODE);

    }
}
