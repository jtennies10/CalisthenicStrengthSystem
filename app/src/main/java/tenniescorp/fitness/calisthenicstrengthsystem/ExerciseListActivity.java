package tenniescorp.fitness.calisthenicstrengthsystem;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import java.util.List;

public class ExerciseListActivity extends AppCompatActivity {

    public static final int NEW_EXERCISE_ACTIVITY_REQUEST_CODE = 1;
    private ExerciseViewModel exerciseViewModel;
    private Routine parentRoutine;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_list);

        Toolbar toolbar = findViewById(R.id.exercise_list_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        parentRoutine = (Routine) getIntent().getSerializableExtra("Routine");

        RecyclerView recyclerView = findViewById(R.id.recyclerview);

        RecyclerViewClickListener clickListener = (view, position) -> {
            Toast.makeText(view.getContext(), "Position " + position, Toast.LENGTH_SHORT).show();
        };

        RecyclerViewLongClickListener longClickListener = (view, position) -> {
            Intent intent = new Intent(ExerciseListActivity.this, ExerciseDescriptionActivity.class);

            //send the selected Routine with the intent
            intent.putExtra("Exercise",
                    exerciseViewModel.getAllExercises().getValue().get(position));

            startActivity(intent);
        };

        final ExerciseListAdapter adapter = new ExerciseListAdapter(this, clickListener, longClickListener);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        exerciseViewModel = ViewModelProviders.of(this).get(ExerciseViewModel.class);

        exerciseViewModel.getAllExercises().observe(this, exercises -> adapter.setExercises(exercises));

        FloatingActionButton newExerciseButton = findViewById(R.id.exercise_list_add_button);
        newExerciseButton.setOnClickListener(v -> {
            Intent intent = new Intent(ExerciseListActivity.this, NewExerciseActivity.class);
            startActivityForResult(intent, NEW_EXERCISE_ACTIVITY_REQUEST_CODE);

        });

        FloatingActionButton saveExercisesButton = findViewById(R.id.exercise_list_save_button);
        saveExercisesButton.setOnClickListener(v -> {
            //TODO: UPDATE ROUTINE EXERCISES IN DB ACCORDINGLY, ADD SOME KIND OF TOAST

            Intent intent = new Intent(ExerciseListActivity.this, RoutineListActivity.class);
            startActivity(intent);
        });

    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == NEW_EXERCISE_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            Exercise exercise = (Exercise) data.getSerializableExtra("Exercise");
            exerciseViewModel.insert(exercise);
        } else {
            Toast.makeText(
                    getApplicationContext(),
                    "Exercise not saved",
                    Toast.LENGTH_LONG).show();
        }
    }
}
