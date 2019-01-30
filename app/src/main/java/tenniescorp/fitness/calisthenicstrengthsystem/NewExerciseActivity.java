package tenniescorp.fitness.calisthenicstrengthsystem;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class NewExerciseActivity extends AppCompatActivity {


    public static final String EXTRA_REPLY = "com.example.android.wordlistsql.REPLY";

    private EditText newExerciseName;
    private EditText newExerciseDescription;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_exercise);

        Toolbar toolbar = findViewById(R.id.new_exercise_toolbar);
        setSupportActionBar(toolbar);

        newExerciseName = findViewById(R.id.new_exercise_name);
        newExerciseDescription = findViewById(R.id.new_exercise_description);

        final Button save_button = findViewById(R.id.new_exercise_save_button);
        save_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent replyIntent = new Intent();
                if (TextUtils.isEmpty(newExerciseName.getText())) {
                    setResult(RESULT_CANCELED, replyIntent);
                } else {
                    String name = newExerciseName.getText().toString();
                    String description = newExerciseDescription.getText().toString();
                    String[] exerciseInfo = {name, description};
                    replyIntent.putExtra(EXTRA_REPLY, exerciseInfo);
                    setResult(RESULT_OK, replyIntent);
                }
                finish();
            }
        });
    }

}
