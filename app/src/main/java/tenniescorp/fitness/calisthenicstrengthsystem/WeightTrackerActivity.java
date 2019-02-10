package tenniescorp.fitness.calisthenicstrengthsystem;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;

public class WeightTrackerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weight_tracker);

        Toolbar toolbar = findViewById(R.id.weight_tracker_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    public void addNewWeightRecord(View view) {
        //TODO:start activity to get new weight record data
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
        //TODO:ensure this works properly
        SharedPreferences preferences = getApplicationContext().getSharedPreferences("UserPreferences", 0);
        long userId = preferences.getLong("userId", -1);

        CSSRoomDatabase db = CSSRoomDatabase.getDatabase(getApplicationContext());
        db.userWeightDao().deleteCurrentUserWeights(userId);
    }
}
