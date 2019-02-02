package tenniescorp.fitness.calisthenicstrengthsystem;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class NewRoutineActivity extends AppCompatActivity {

    public static final String EXTRA_REPLY = "com.example.android.wordlistsql.REPLY";

    private EditText newRoutineName;
    private EditText newRoutineDescription;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_routine);

        Toolbar toolbar = findViewById(R.id.new_routine_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        newRoutineName = findViewById(R.id.new_routine_name);
        newRoutineDescription = findViewById(R.id.new_routine_description);

        final Button save_button = findViewById(R.id.new_routine_save_button);
        save_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
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
            }
        });
    }
}
