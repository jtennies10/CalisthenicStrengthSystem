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
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
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
//        if(recyclerView == null) {
//            Log.d("NUll", "NULL");
//        }

        RecyclerViewClickListener clickListener = (view, position) -> {

            Exercise selectedExercise = exerciseViewModel.getAllExercises().getValue().get(position);
            if(!selectedExercise.isSelected()) selectedExercise.setSelected(true);
            else selectedExercise.setSelected(false);
            //Toast.makeText(view.getContext(), exerciseViewModel.getAllExercises().getValue().get(position).isSelected() + " ", Toast.LENGTH_SHORT).show();

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

//            Intent callingIntent = getIntent();
            Intent replyIntent = new Intent();

            //get the selected exercises and pass them back with the reply intent
            //as well as the exercises' parent routine
            ArrayList<Exercise> selectedExercises = new ArrayList<>();
            List<Exercise> allExercises = exerciseViewModel.getAllExercises().getValue();
            for(int i = 0; i < allExercises.size(); i++) {
                if(allExercises.get(i).isSelected()) selectedExercises.add(allExercises.get(i));
            }

            replyIntent.putExtra("Routine", parentRoutine);
            replyIntent.putExtra("Exercises", selectedExercises);
            setResult(RESULT_OK, replyIntent);


            finish();

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
