package tenniescorp.fitness.calisthenicstrengthsystem;

import android.arch.lifecycle.ViewModelProviders;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;

public class WeightTrackerActivity extends AppCompatActivity {
    private List<UserWeight> userWeights;
    private long userId;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weight_tracker);

        Toolbar toolbar = findViewById(R.id.weight_tracker_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        SharedPreferences preferences = getApplicationContext().getSharedPreferences("UserPreferences", 0);
        userId = preferences.getLong("userId", -1);

        CSSRoomDatabase db = CSSRoomDatabase.getDatabase(getApplicationContext());
        userWeights = db.userWeightDao().getCurrentUserWeights(userId);

        recyclerView = findViewById(R.id.recyclerview);

        final UserWeightListAdapter adapter = new UserWeightListAdapter(this);
        adapter.setUserWeights(userWeights);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


//        exerciseViewModel = ViewModelProviders.of(this).get(ExerciseViewModel.class);
//
//        exerciseViewModel.getAllExercises().observe(this, exercises -> adapter.setExercises(exercises));
    }

    public void promptAddWeightRecord(View view) {
        LinearLayout addRecordPrompt = findViewById(R.id.prompt_add_weight_record);
        addRecordPrompt.setVisibility(View.VISIBLE);
    }

    public void promptDeleteWeightRecords(View view) {
        LinearLayout deleteWarning = findViewById(R.id.delete_warning);
        deleteWarning.setVisibility(View.VISIBLE);
    }

    public void cancelDeleteWeightRecords(View view) {
        LinearLayout deleteWarning = findViewById(R.id.delete_warning);
        deleteWarning.setVisibility(View.GONE);
    }

    public void deleteWeightRecords(View view) {
        CSSRoomDatabase db = CSSRoomDatabase.getDatabase(getApplicationContext());
        db.userWeightDao().deleteCurrentUserWeights(userId);

        userWeights = new ArrayList<>();

        final UserWeightListAdapter adapter = new UserWeightListAdapter(getApplicationContext());
        adapter.setUserWeights(userWeights);
        recyclerView.setAdapter(adapter);

        LinearLayout addRecordPrompt = findViewById(R.id.delete_warning);
        addRecordPrompt.setVisibility(View.GONE);
    }

    public void cancelAddWeightRecord(View view) {
        LinearLayout addRecordPrompt = findViewById(R.id.prompt_add_weight_record);
        addRecordPrompt.setVisibility(View.GONE);

    }

    public void addWeightRecord(View view) {
        EditText weightView = findViewById(R.id.new_user_weight);
        if(weightView.getText().toString().equals("")) {
            Toast.makeText(getApplicationContext(), "Weight required", Toast.LENGTH_SHORT).show();
            return;
        }
        int weightInPounds = Integer.parseInt(weightView.getText().toString());

        UserWeight newUserWeight = new UserWeight(userId, weightInPounds, getDateAsString());

        CSSRoomDatabase db = CSSRoomDatabase.getDatabase(getApplicationContext());
        db.userWeightDao().insert(newUserWeight);
        userWeights.add(newUserWeight);

        final UserWeightListAdapter adapter = new UserWeightListAdapter(getApplicationContext());
        adapter.setUserWeights(userWeights);
        recyclerView.setAdapter(adapter);

        Toast.makeText(getApplicationContext(), "Record added", Toast.LENGTH_SHORT).show();

        LinearLayout addRecordPrompt = findViewById(R.id.prompt_add_weight_record);
        addRecordPrompt.setVisibility(View.GONE);
    }

    private String getDateAsString() {
        Date date = new Date();
        String dateAsString = date.toString();
        //dateAsString += date.getDate();
        return dateAsString;
    }
}
