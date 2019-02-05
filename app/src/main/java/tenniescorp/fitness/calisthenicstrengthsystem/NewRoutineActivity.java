package tenniescorp.fitness.calisthenicstrengthsystem;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

public class NewRoutineActivity extends AppCompatActivity {

    public static final String EXTRA_REPLY = "com.example.android.wordlistsql.REPLY";

    private EditText newRoutineName;
    private EditText newRoutineDescription;
    private List<Exercise> routineExercises;
    private Routine currentRoutine;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_routine);

        Toolbar toolbar = findViewById(R.id.new_routine_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        newRoutineName = findViewById(R.id.new_routine_name);
        newRoutineDescription = findViewById(R.id.new_routine_description);

        //look for the intent that started the activity and see if a routine is found
        Bundle bundle = (getIntent().getBundleExtra("Routine Bundle"));

        RecyclerViewClickListener clickListener = (view, position) -> {
            // Toast.makeText(view.getContext(), "Position " + position, Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(NewRoutineActivity.this, ExerciseDescriptionActivity.class);

            //send the selected Exercise with the intent
            intent.putExtra("Exercise", routineExercises.get(position));
            startActivity(intent);
        };

        if(bundle != null) {
            currentRoutine = (Routine) bundle.getSerializable("Routine");
            newRoutineName.setText(currentRoutine.getRoutineName());
            newRoutineDescription.setText(currentRoutine.getRoutineDescription());

            routineExercises = (ArrayList) bundle.getSerializable("Exercises");
        }

        //get recycler view and populate using query to get all exercises with the correct workout_id
        RecyclerView recyclerView = findViewById(R.id.recyclerview);

        //create the list adapter and fill it with the data
        final RoutineExerciseAdapter adapter = new RoutineExerciseAdapter(getApplicationContext(), routineExercises, clickListener);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));



        final Button saveButton = findViewById(R.id.new_routine_save_button);
        saveButton.setOnClickListener(view -> {
            Intent replyIntent = new Intent();
            if (TextUtils.isEmpty(newRoutineName.getText())) {
                setResult(RESULT_CANCELED, replyIntent);
            } else {
                String name = newRoutineName.getText().toString();
                String description = newRoutineDescription.getText().toString();
                String[] routineInfo = {name, description};
                replyIntent.putExtra(EXTRA_REPLY, routineInfo);
                setResult(RESULT_OK, replyIntent);
            }
            finish();
        });

        final Button addExerciseButton = findViewById(R.id.new_routine_add_button);
        addExerciseButton.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), ExerciseListActivity.class);
            if(currentRoutine != null) intent.putExtra("Routine", currentRoutine);
            startActivity(intent);
        });
    }
}
