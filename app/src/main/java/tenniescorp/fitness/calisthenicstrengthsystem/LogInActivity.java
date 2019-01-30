package tenniescorp.fitness.calisthenicstrengthsystem;

import android.arch.lifecycle.LiveData;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

public class LogInActivity extends AppCompatActivity {

    /*Click listener syntax derived from Option Two at https://hackernoon.com/4-ways-to-implement-onclicklistener-on-android-9b956cbd2928*/
    private View.OnClickListener signInListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            signIn();
        }
    };

    private View.OnClickListener registerListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            register();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        Button registerButton = findViewById(R.id.sign_in_register_button);
        registerButton.setOnClickListener(registerListener);

        Button signInButton = findViewById(R.id.sign_in_sign_in_button);
        signInButton.setOnClickListener(signInListener);
    }

    private void signIn() {
        User currentUser = verifyUserInformation();
        if(currentUser == null) {
            Toast t = Toast.makeText(this, "Invalid Login", Toast.LENGTH_SHORT);
            t.show();
            return;
        }

        CSSRoomDatabase db = CSSRoomDatabase.getDatabase(getApplication());
        UserDao uDao = db.userDao();

        List<User> queryResults = uDao.getSpecificUser(currentUser.getUserName(), currentUser.getPassword());


//        insertAsyncTask task = new insertAsyncTask(uDao);
//        task.execute(currentUser);
//        List<User> queryResults;
//
        if(queryResults == null || queryResults.size() == 0) {
            Toast t = Toast.makeText(this, "Invalid Login", Toast.LENGTH_SHORT);
            t.show();
            return;
        }

        Toast t = Toast.makeText(this, "Login successful", Toast.LENGTH_SHORT);
        t.show();


    }

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

        return new User(emailUsername, emailUsername, password, null, -1);
    }

//    private static class insertAsyncTask extends AsyncTask<User, Void, List<User>> {
//
//        private UserDao mAsyncTaskDao;
//        private List<User> queryResults;
//
//        insertAsyncTask(UserDao dao, List<User> queryResults) {
//            mAsyncTaskDao = dao;
//            this.queryResults = queryResults;
//        }
//
//        @Override
//        protected List<User> doInBackground(final User... params) {
//            User u = params[0];
//            //queryResults =
//              return mAsyncTaskDao.getAllUsers();       //getSpecificUser(u.getUserName(), u.getPassword());
//            //return null;
//        }
//
////        protected List<User> getQueryResults() {
////            return queryResults;
//////        }
//
//        @Override
//        protected void onPostExecute(List<User> results) {
//            queryResults = results;
//        }
//    }

    private void register() {

        Intent registerIntent = new Intent(this, RegisterActivity.class);
        startActivity(registerIntent);
    }
}
