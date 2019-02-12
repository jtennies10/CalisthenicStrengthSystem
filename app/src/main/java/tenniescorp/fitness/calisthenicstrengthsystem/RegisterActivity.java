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

/*
Defines the activity that allows a user to register a new account in the database
 */
public class RegisterActivity extends AppCompatActivity {



    /*Click listener syntax derived from Option Two at https://hackernoon.com/4-ways-to-implement-onclicklistener-on-android-9b956cbd2928*/
    private View.OnClickListener registerListener = v -> register();

    private View.OnClickListener signInListener = v -> signIn();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //get the register and sign in buttons and assign their onClickListeners
        Button register = findViewById(R.id.register_register_button);
        register.setOnClickListener(registerListener);

        Button signIn = findViewById(R.id.register_sign_in_button);
        signIn.setOnClickListener(signInListener);

        //retrieve the spinners that represents birth day, month, and year
        Spinner birthDaySpinner = findViewById(R.id.register_birth_day);
        Spinner birthMonthSpinner = findViewById(R.id.register_birth_month);
        Spinner birthYearSpinner = findViewById(R.id.register_birth_year);

        //get an array of strings that represents the range of days
        //a user can select from
        String[] days = generateStringArray(1, 31);

        //assign the days array to an adapter, use that adapter for the birthDaySpinner
        ArrayAdapter<String> birthDayAdapter =
                new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, days);
        birthDayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        birthDaySpinner.setAdapter(birthDayAdapter);

        //assign the months array to an adapter, use that adapter for the birthMonthSpinner
        ArrayAdapter<CharSequence> birthMonthAdapter =
                ArrayAdapter.createFromResource(this, R.array.months_array, android.R.layout.simple_spinner_item);
        birthMonthAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        birthMonthSpinner.setAdapter(birthMonthAdapter);

        //get an array of strings that represents the range of years
        //a user can select from
        String[] years = generateStringArray(1940, 2007);

        //assign the years array to an adapter, use that adapter for the birthYearSpinner
        ArrayAdapter<String> birthYearAdapter =
                new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, years);
        birthYearAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        birthYearSpinner.setAdapter(birthYearAdapter);



    }

    /*
    Called to attempt to a create a new User within the database, if successful notifies the user and
    starts the LogInActivity
     */
    private void register() {
        //get the user using verifyUserInfo()
        User newUser = verifyUserInfo();

        if(newUser == null) return;

        //Add record to database
        CSSRoomDatabase db = CSSRoomDatabase.getDatabase(getApplication());
        UserDao uDao = db.userDao();

        insertAsyncTask task = new insertAsyncTask(uDao);
        task.execute(newUser);

        //alert the user the registration was successful
        Toast.makeText(getApplicationContext(),
                "Registration complete, please sign in", Toast.LENGTH_SHORT).show();


        //start the LogInActivity
        Intent intent = new Intent(this, LogInActivity.class);
        startActivity(intent);

    }

    /*
    Verifies the user info to ensure all required fields are present
    @return a User object representing the new credentials if all requirements are satisfied, null if not
     */
    private User verifyUserInfo() {

        //get the email and ensure it isn't empty
        EditText etEmail = findViewById(R.id.register_email);
        String email = etEmail.getText().toString();
        if(email.equals("")) {
            Toast t = Toast.makeText(getApplicationContext(), "Email required", Toast.LENGTH_SHORT);
            t.show();
            return null;
        }

        //get the username and ensure it isn't empty
        EditText etUsername = findViewById(R.id.register_username);
        String username = etUsername.getText().toString();
        if(username.equals("")) {
            Toast t = Toast.makeText(getApplicationContext(), "Username required", Toast.LENGTH_SHORT);
            t.show();
            return null;
        }

        //get the password and ensure it isn't empty
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

        return new User(username, email, password, dateOfBirth, heightInInches);
    }

    /*
    Defines a class for inserting the user asynchronously into the database
     */
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

    /*
    Starts LogInActivity
     */
    private void signIn() {
        Intent signInIntent = new Intent(this, LogInActivity.class);
        startActivity(signInIntent);
    }

    /*
    Generates a string array of numbers in from the range lower to upper, both inclusive
    @return the string array
     */
    private String[] generateStringArray(int lower, int upper) {
        String[] a = new String[upper-lower+1];
        for(int i = 0; i < a.length; i++) {
            a[i] = String.valueOf(i+lower);
        }

        return a;
    }
}
