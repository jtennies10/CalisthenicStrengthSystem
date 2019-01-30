package tenniescorp.fitness.calisthenicstrengthsystem;

import android.arch.lifecycle.LiveData;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.List;

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


        String[] days = generateStringArray(1, 31);

        ArrayAdapter<String> birthDayAdapter =
                new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, days);
        birthDayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        birthDaySpinner.setAdapter(birthDayAdapter);



        ArrayAdapter<CharSequence> birthMonthAdapter =
                ArrayAdapter.createFromResource(this, R.array.months_array, android.R.layout.simple_spinner_item);
        birthMonthAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        birthMonthSpinner.setAdapter(birthMonthAdapter);

        String[] years = generateStringArray(1900, 2007);

        ArrayAdapter<String> birthYearAdapter =
                new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, years);
        birthYearAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        birthYearSpinner.setAdapter(birthYearAdapter);



    }

    private void register() {
        //Add record to database
        User newUser = verifyUserInfo();

        if(newUser == null) return;

        CSSRoomDatabase db = CSSRoomDatabase.getDatabase(getApplication());
        UserDao uDao = db.userDao();

        insertAsyncTask task = new insertAsyncTask(uDao);
        task.execute(newUser);

        Toast t = Toast.makeText(getApplicationContext(), "Registration complete, please sign in", Toast.LENGTH_SHORT);
        t.show();

        Intent intent = new Intent(this, LogInActivity.class);
        startActivity(intent);

    }

    private User verifyUserInfo() {

        EditText etEmail = findViewById(R.id.register_email);
        String email = etEmail.getText().toString();
        if(email.equals("")) {
            Toast t = Toast.makeText(getApplicationContext(), "Email required", Toast.LENGTH_SHORT);
            t.show();
            return null;
        }

        EditText etUsername = findViewById(R.id.register_username);
        String username = etUsername.getText().toString();
        if(username.equals("")) {
            Toast t = Toast.makeText(getApplicationContext(), "Username required", Toast.LENGTH_SHORT);
            t.show();
            return null;
        }

        EditText etPassword = findViewById(R.id.register_password);
        String password = etPassword.getText().toString();
        if(password.equals("")) {
            Toast t = Toast.makeText(getApplicationContext(), "Password required", Toast.LENGTH_SHORT);
            t.show();
            return null;
        }

        //retrieve and format the date of birth
        Spinner birthMonthSpinner = findViewById(R.id.register_birth_month);
        String birthMonth = birthMonthSpinner.getSelectedItem().toString();

        Spinner birthYearSpinner = findViewById(R.id.register_birth_year);
        String birthYear = birthYearSpinner.getSelectedItem().toString();

        Spinner birthDaySpinner = findViewById(R.id.register_birth_day);
        String birthDay = birthDaySpinner.getSelectedItem().toString();

        String dateOfBirth = birthMonth + " " + birthDay + ", " + birthYear;

        //get height, set to -1 if null
        EditText etHeight = findViewById(R.id.register_height);
        String heightAsString = etHeight.getText().toString();
        int heightInInches;
        if(heightAsString.equals("")) {
            heightInInches = -1;
        } else {
            heightInInches = Integer.parseInt(heightAsString);
        }

        //get weight, set to -1 if null
        EditText etWeight = findViewById(R.id.register_weight);
        String weightAsString = etWeight.getText().toString();
        int weightInPounds;
        if(weightAsString.equals("")) {
            weightInPounds = -1;
        } else {
            weightInPounds = Integer.parseInt(weightAsString);
        }

        return new User(username, email, password, dateOfBirth, heightInInches);
    }

    private static class insertAsyncTask extends AsyncTask<User, Void, Void> {

        private UserDao mAsyncTaskDao;

        insertAsyncTask(UserDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final User... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }

    private void signIn() {
        Intent signInIntent = new Intent(this, LogInActivity.class);
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
