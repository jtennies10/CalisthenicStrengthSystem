package tenniescorp.fitness.calisthenicstrengthsystem;

import android.app.Activity;
import android.arch.lifecycle.ViewModelProviders;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.text.SimpleDateFormat;
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

        populateChart();
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

        UserWeight newUserWeight = new UserWeight(userId, weightInPounds, new Date().getTime());

        CSSRoomDatabase db = CSSRoomDatabase.getDatabase(getApplicationContext());
        db.userWeightDao().insert(newUserWeight);
        userWeights.add(newUserWeight);

        final UserWeightListAdapter adapter = new UserWeightListAdapter(getApplicationContext());
        adapter.setUserWeights(userWeights);
        recyclerView.setAdapter(adapter);

        Toast.makeText(getApplicationContext(), "Record added", Toast.LENGTH_SHORT).show();

        LinearLayout addRecordPrompt = findViewById(R.id.prompt_add_weight_record);
        addRecordPrompt.setVisibility(View.GONE);


        //Hide keyboard code found at https://stackoverflow.com/questions/4165414/how-to-hide-soft-keyboard-on-android-after-clicking-outside-edittext
        InputMethodManager inputMethodManager =(InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

//    private String getDateAsString() {
//        Date date = new Date();
//        return  new SimpleDateFormat("MM/dd/yyyy").format(date);
//    }


    private void populateChart() {
        /*
        This chart is built off the open source MpAndroidChart library found here: https://github.com/PhilJay/MPAndroidChart
        Knowledge base for creating the chart is from the tutorial found at https://www.numetriclabz.com/android-line-chart-using-mpandroidchart-tutorial/
         */
        LineChart lineChart = findViewById(R.id.user_weight_chart);
        lineChart.getAxisRight().setEnabled(false);
        lineChart.getDescription().setEnabled(false);

        //entries need to go in order
        ArrayList<Entry> userWeightEntries = new ArrayList<>();
        for(int i = 0; i < userWeights.size(); i++) {
            UserWeight current = userWeights.get(i);
            userWeightEntries.add(new Entry(current.getWeightDateAsLong(), current.getWeightInPounds()));
        }



        LineDataSet dataSet = new LineDataSet(userWeightEntries, "User Weights");
        dataSet.setColor(Color.RED);
        dataSet.setHighLightColor(Color.RED);
        //dataSet.setDrawFilled(true);


//        ArrayList<String> labels = new ArrayList<>();
//        labels.add("January");
//        labels.add("February");
//        labels.add("March");
//        labels.add("April");

        LineData data = new LineData(dataSet);
        lineChart.setData(data);

        XAxis xAxis = lineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);



    }
}
