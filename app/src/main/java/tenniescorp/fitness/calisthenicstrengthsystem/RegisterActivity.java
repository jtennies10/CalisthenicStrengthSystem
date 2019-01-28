package tenniescorp.fitness.calisthenicstrengthsystem;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

public class RegisterActivity extends AppCompatActivity {

    /*Click listener syntax derived from Option Two at https://hackernoon.com/4-ways-to-implement-onclicklistener-on-android-9b956cbd2928*/
    private View.OnClickListener registerListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            register();
        }
    };

    private View.OnClickListener signInListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            signIn();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Button register = findViewById(R.id.register_register_button);
        register.setOnClickListener(registerListener);

        Button signIn = findViewById(R.id.register_sign_in_button);
        signIn.setOnClickListener(signInListener);

        Spinner birthDaySpinner = findViewById(R.id.register_birth_day);
        Spinner birthMonthSpinner = findViewById(R.id.register_birth_month);
        Spinner birthYearSpinner = findViewById(R.id.register_birth_year);

        ArrayAdapter<CharSequence> birthDayAdapter =
                ArrayAdapter.createFromResource(this, R.array.months_array, android.R.layout.simple_spinner_item);
        birthDayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        birthDaySpinner.setAdapter(birthDayAdapter);

        String[] days = generateStringArray(1, 31);

        ArrayAdapter<String> birthMonthAdapter =
                new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, days);
        birthMonthAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        birthMonthSpinner.setAdapter(birthMonthAdapter);

        String[] years = generateStringArray(1900, 2007);

        ArrayAdapter<String> birthYearAdapter =
                new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, years);
        birthYearAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        birthYearSpinner.setAdapter(birthYearAdapter);

    }

    private void register() {
        //database

    }

    private void signIn() {
        Intent signInIntent = new Intent(this, MainActivity.class);
        startActivity(signInIntent);
    }

    private String[] generateStringArray(int lower, int upper) {
        String[] a = new String[upper-lower+1];
        for(int i = 0; i < a.length; i++) {
            a[i] = String.valueOf(i+lower);
        }

        return a;
    }
}
