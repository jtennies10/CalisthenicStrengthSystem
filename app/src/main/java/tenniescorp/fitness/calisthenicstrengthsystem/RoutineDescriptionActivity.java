package tenniescorp.fitness.calisthenicstrengthsystem;

import android.database.DataSetObserver;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ScrollView;
import android.widget.TextView;

public class RoutineDescriptionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_routine_description);

        Toolbar toolbar = findViewById(R.id.routine_list_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        Routine currentRoutine = (Routine) getIntent().getSerializableExtra("Routine");

        TextView routineName = findViewById(R.id.routine_description_name);
        TextView routineDescription = findViewById(R.id.routine_description_description);

        routineName.setText(currentRoutine.getRoutineName());
        routineDescription.setText(currentRoutine.getRoutineDescription());

        //get recycler view and populate using query to get all exercises with the correct workout_id
        RecyclerView recyclerView = findViewById(R.id.recyclerview);

        //create the list adapter and fill it with the data

    }
}
