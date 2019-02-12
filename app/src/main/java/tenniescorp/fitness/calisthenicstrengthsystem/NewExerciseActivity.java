package tenniescorp.fitness.calisthenicstrengthsystem;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/*
Defines the activity that allows users to create new exercises that are stored in the database.
 */
public class NewExerciseActivity extends AppCompatActivity {

    //member variable
    private EditText newExerciseName;
    private EditText newExerciseDescription;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_exercise);

        //set the activity toolbar
        Toolbar toolbar = findViewById(R.id.new_exercise_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        newExerciseName = findViewById(R.id.new_exercise_name);
        newExerciseDescription = findViewById(R.id.new_exercise_description);

        //get the save button and attach a click listener
        //that gets the exercise data and finishes the activity when clicked
        final Button save_button = findViewById(R.id.new_exercise_save_button);
        save_button.setOnClickListener(view -> {
            Intent replyIntent = new Intent();
            if (TextUtils.isEmpty(newExerciseName.getText())) {
                setResult(RESULT_CANCELED, replyIntent);
            } else {
                String exerciseName = newExerciseName.getText().toString();
                String exerciseDescription = newExerciseDescription.getText().toString();
                Exercise exercise = new Exercise(exerciseName, exerciseDescription);
                replyIntent.putExtra("Exercise", exercise);
                setResult(RESULT_OK, replyIntent);
            }
            finish();

        });
    }

}
