package tenniescorp.fitness.calisthenicstrengthsystem;

import android.arch.lifecycle.LiveData;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

public class LogInActivity extends AppCompatActivity {


    /*Click listener syntax derived from Option Two at https://hackernoon.com/4-ways-to-implement-onclicklistener-on-android-9b956cbd2928*/
    private View.OnClickListener signInListener = v -> signIn();

    private View.OnClickListener registerListener = v -> register();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if(checkUserSignedIn()) {
            Intent intent = new Intent(this, RoutineListActivity.class);
            startActivity(intent);
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);


        //get the buttons and set their on click listeners
        Button registerButton = findViewById(R.id.sign_in_register_button);
        registerButton.setOnClickListener(registerListener);

        Button signInButton = findViewById(R.id.sign_in_sign_in_button);
        signInButton.setOnClickListener(signInListener);

        //create db here and run an obsolete query
        //this ensures the database is successfully pre-populated before any
        //legitimate queries are ran
        CSSRoomDatabase db = CSSRoomDatabase.getDatabase(getApplication());
        UserDao userDao = db.userDao();
        userDao.getAllUsers();
    }

    /*
    Attempts to sign the user in and writes the user information to SharedPreferences if successful.
     */
    private void signIn() {
        //retrieve the user
        User currentUser = verifyUserInformation();

        if(currentUser == null) {
            Toast t = Toast.makeText(this, "Invalid Login", Toast.LENGTH_SHORT);
            t.show();
            return;
        }

        //create an instance of the database and get query for the currentUser
        CSSRoomDatabase db = CSSRoomDatabase.getDatabase(getApplication());
        List<User> queryResults = db.userDao().getSpecificUser(currentUser.getUserName(), currentUser.getPassword());

        //if the query returns null or 0 records, the user login attempt is invalid
        if(queryResults == null || queryResults.size() == 0) {
            Log.d("2nd", "fail");
            Log.d(currentUser.getUserName(), currentUser.getPassword());
            Toast t = Toast.makeText(this, "Invalid Login", Toast.LENGTH_SHORT);
            t.show();
            return;
        }

        //at this point the login was successful, so record user information to allow
        //immediate login next time they open the application
        User verifiedUser = queryResults.get(0);
        SharedPreferences preferences = getApplicationContext().getSharedPreferences("UserPreferences", 0);
        SharedPreferences.Editor preferencesEditor = preferences.edit();
        preferencesEditor.putLong("userId", verifiedUser.getUserId());
        preferencesEditor.putString("userName", verifiedUser.getUserName());
        preferencesEditor.putString("userEmail", verifiedUser.getEmail());
        preferencesEditor.apply();

        Toast.makeText(this, "Login successful", Toast.LENGTH_SHORT).show();

        //start the logged in content for the user
        Intent intent = new Intent(this, RoutineListActivity.class);
        startActivity(intent);

    }

    /*
    Retrieves the user credentials and ensures they are not blank
    @return a User object representing the user credentials
     */
    private User verifyUserInformation() {
        EditText etEmailUsername = findViewById(R.id.sign_in_username_email);
        String emailUsername = etEmailUsername.getText().toString();
        if(emailUsername.equals("")) {
            Toast t = Toast.makeText(getApplicationContext(), "Username/Email required", Toast.LENGTH_SHORT);
            t.show();
            return null;
        }

        EditText etPassword = findViewById(R.id.sign_in_password);
        String password = etPassword.getText().toString();
        if(password.equals("")) {
            Toast t = Toast.makeText(getApplicationContext(), "Password required", Toast.LENGTH_SHORT);
            t.show();
            return null;
        }

        return new User(emailUsername, emailUsername, password, "1/1/97", -1);
    }

    /*
    Starts the register activity
     */
    private void register() {

        Intent registerIntent = new Intent(this, RegisterActivity.class);
        startActivity(registerIntent);
    }

    /*
    Checks to see if a user is already signed in
    @return true if the user is signed in, false if not
     */
    private boolean checkUserSignedIn() {
        SharedPreferences preferences = getApplicationContext().getSharedPreferences("UserPreferences", 0);
        return (preferences.getAll() != null && preferences.getAll().size() > 0);
    }
}
