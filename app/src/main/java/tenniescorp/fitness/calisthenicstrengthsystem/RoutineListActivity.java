package tenniescorp.fitness.calisthenicstrengthsystem;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.List;


public class RoutineListActivity extends AppCompatActivity {

    public static final int NEW_ROUTINE_ACTIVITY_REQUEST_CODE = 1;
    private RoutineViewModel routineViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_routine_list);

        Toolbar toolbar = findViewById(R.id.routine_list_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);


        RecyclerView recyclerView = findViewById(R.id.recyclerview);

        RecyclerViewClickListener clickListener = (view, position) -> {
           // Toast.makeText(view.getContext(), "Position " + position, Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(RoutineListActivity.this, RoutineDescriptionActivity.class);

            //send the selected Routine with the intent
            intent.putExtra("Routine",
                    routineViewModel.getAllRoutines().getValue().get(position));

            startActivity(intent);
        };


        final RoutineListAdapter adapter = new RoutineListAdapter(this, clickListener);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        routineViewModel = ViewModelProviders.of(this).get(RoutineViewModel.class);

        routineViewModel.getAllRoutines().observe(this, new Observer<List<Routine>>() {
            @Override
            public void onChanged(@Nullable final List<Routine> routines) {
                adapter.setRoutines(routines);
            }
        });

        FloatingActionButton newRoutineButton = findViewById(R.id.routine_list_add_button);
        newRoutineButton.setOnClickListener(v -> {
            Intent intent = new Intent(RoutineListActivity.this, NewRoutineActivity.class);
            startActivityForResult(intent, NEW_ROUTINE_ACTIVITY_REQUEST_CODE);

        });

    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == NEW_ROUTINE_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            String[] newRoutineAsArray = data.getStringArrayExtra(NewRoutineActivity.EXTRA_REPLY);
            Routine routine = new Routine(newRoutineAsArray[0], newRoutineAsArray[1]);
            routineViewModel.insert(routine);
        } else {
            Toast.makeText(
                    getApplicationContext(),
                    R.string.new_routine_empty_not_saved,
                    Toast.LENGTH_LONG).show();
        }
    }

}
