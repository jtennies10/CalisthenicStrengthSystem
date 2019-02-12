package tenniescorp.fitness.calisthenicstrengthsystem;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import org.w3c.dom.Text;

/*
Simple class that defines an activity for describing an exercise based on what exercise
is passed with the calling intent
 */
public class ExerciseDescriptionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_description);

        Exercise currentExercise = (Exercise) getIntent().getSerializableExtra("Exercise");

        TextView exerciseName = findViewById(R.id.exercise_description_name);
        TextView exerciseDescription = findViewById(R.id.exercise_description_description);

        exerciseName.setText(currentExercise.getExerciseName());
        exerciseDescription.setText(currentExercise.getExerciseDescription());
    }
}
