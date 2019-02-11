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
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

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

        if(userWeights.size()>1) {
            userWeights.add(calculateFutureUserWeight());
            populateChart();
        }

        recyclerView = findViewById(R.id.recyclerview);

        final UserWeightListAdapter adapter = new UserWeightListAdapter(this);
        adapter.setUserWeights(userWeights);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

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

        while(userWeights.size() > 0) {
            userWeights.remove(0);
        }

        final UserWeightListAdapter adapter = new UserWeightListAdapter(getApplicationContext());
        adapter.setUserWeights(userWeights);
        recyclerView.setAdapter(adapter);

        LinearLayout addRecordPrompt = findViewById(R.id.delete_warning);
        addRecordPrompt.setVisibility(View.GONE);

        populateChart();
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

        if(weightInPounds <= 0) {
            Toast.makeText(getApplicationContext(), "Invalid weight", Toast.LENGTH_SHORT).show();
            return;
        }

        UserWeight newUserWeight = new UserWeight(userId, weightInPounds, new Date().getTime());

        CSSRoomDatabase db = CSSRoomDatabase.getDatabase(getApplicationContext());
        db.userWeightDao().insert(newUserWeight);

        if(userWeights.size() > 0) userWeights.remove(userWeights.size()-1);
        userWeights.add(newUserWeight);

        if(userWeights.size() > 1) {
            userWeights.add(calculateFutureUserWeight());
            populateChart();
        }

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

//        String[] months = getXAxisMonths();

        LineDataSet dataSet = new LineDataSet(userWeightEntries, "User Weights");
        dataSet.setColor(Color.RED);
        dataSet.setHighLightColor(Color.RED);

        LineData data = new LineData(dataSet);
        lineChart.setData(data);
        lineChart.getXAxis().setEnabled(false);

    }

    private UserWeight calculateFutureUserWeight() {
        UserWeight firstWeightRecord = userWeights.get(0);
        UserWeight lastWeightRecord = userWeights.get(userWeights.size()-1);

        long minWeightDate = firstWeightRecord.getWeightDateAsLong();
        long maxWeightDate = lastWeightRecord.getWeightDateAsLong();

        long datesRange = maxWeightDate - minWeightDate;
        long predictedWeightDate = maxWeightDate + (datesRange / 2);

        int minWeightLbsIndex = findMinWeightIndex();
        int minWeight = userWeights.get(minWeightLbsIndex).getWeightInPounds();

        int maxWeightLbsIndex = findMaxWeightIndex();
        int maxWeight = userWeights.get(maxWeightLbsIndex).getWeightInPounds();

        int weightLbsRange = maxWeight - minWeight;
        if(weightLbsRange < 0) weightLbsRange *= -1;

        //todo:predict weight based on range and whether min or max comes first
        int predictedWeightLbs;

        if(minWeightLbsIndex > maxWeightLbsIndex) predictedWeightLbs = userWeights.get(userWeights.size()-1).getWeightInPounds() - (weightLbsRange/2);
        else predictedWeightLbs = userWeights.get(userWeights.size()-1).getWeightInPounds() + (weightLbsRange/2);

        return new UserWeight(userId, predictedWeightLbs, predictedWeightDate);

    }

    private int findMinWeightIndex() {
        int currentMinWeight = Integer.MAX_VALUE;
        int currentMinIndex = 0;

        for(int i = 0; i < userWeights.size(); i++) {
            if(userWeights.get(i).getWeightInPounds() < currentMinWeight) {
                currentMinWeight = userWeights.get(i).getWeightInPounds();
                currentMinIndex = i;
            }
        }

        return currentMinIndex;
    }

    private int findMaxWeightIndex() {
        int currentMaxWeight = -1;
        int currentMaxIndex = 0;

        for(int i = 0; i < userWeights.size(); i++) {
            if(userWeights.get(i).getWeightInPounds() > currentMaxWeight) {
                currentMaxWeight = userWeights.get(i).getWeightInPounds();
                currentMaxIndex = i;
            }
        }

        return currentMaxIndex;
    }


}
