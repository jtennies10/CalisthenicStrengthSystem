package tenniescorp.fitness.calisthenicstrengthsystem;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;


public class MainActivity extends AppCompatActivity {


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
        setContentView(R.layout.activity_main);

        Button registerButton = findViewById(R.id.sign_in_register_button);
        registerButton.setOnClickListener(registerListener);

        Button signInButton = findViewById(R.id.sign_in_sign_in_button);
        signInButton.setOnClickListener(signInListener);
    }

    private void signIn() {
        Toast t = Toast.makeText(getApplicationContext(), "Test", Toast.LENGTH_LONG);
        t.show();
    }

    private void register() {
//        Toast t = Toast.makeText(getApplicationContext(), "Registering", Toast.LENGTH_LONG);
//        t.show();

        Intent registerIntent = new Intent(this, RegisterActivity.class);
        startActivity(registerIntent);
    }
}
