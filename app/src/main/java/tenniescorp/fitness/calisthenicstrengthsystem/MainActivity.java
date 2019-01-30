package tenniescorp.fitness.calisthenicstrengthsystem;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import java.util.List;


public class MainActivity extends AppCompatActivity {

    private RoutineViewModel routineViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        Button registerButton = findViewById(R.id.sign_in_register_button);
//        registerButton.setOnClickListener(registerListener);
//
//        Button signInButton = findViewById(R.id.sign_in_sign_in_button);
//        signInButton.setOnClickListener(signInListener);

        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        final RoutineListAdapter adapter = new RoutineListAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        routineViewModel = ViewModelProviders.of(this).get(RoutineViewModel.class);

        routineViewModel.getAllRoutines().observe(this, new Observer<List<Routine>>() {
            @Override
            public void onChanged(@Nullable final List<Routine> routines) {
                adapter.setRoutines(routines);
            }
        });
    }

}
