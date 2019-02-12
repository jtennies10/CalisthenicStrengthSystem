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

/*
Defines the WeightTrackerActivity which allows users to track their weight progress and see
a predictive model of where they may end up progressing to.
 */
public class WeightTrackerActivity extends AppCompatActivity {
    private List<UserWeight> userWeights;
    private long userId;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weight_tracker);

        //set the activity toolbar
        Toolbar toolbar = findViewById(R.id.weight_tracker_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        //get the current user's userId from sharedPreferences
        SharedPreferences preferences = getApplicationContext().getSharedPreferences("UserPreferences", 0);
        userId = preferences.getLong("userId", -1);

        //assign the userWeights member variable the UserWeights in the database using the current
        //user's userId
        CSSRoomDatabase db = CSSRoomDatabase.getDatabase(getApplicationContext());
        userWeights = db.userWeightDao().getCurrentUserWeights(userId);

        //if there is more than one item in userWeights, add a predictive weight and
        //populate the chart
        if(userWeights.size()>1) {
            userWeights.add(calculateFutureUserWeight());
            populateChart();
        }

        recyclerView = findViewById(R.id.recyclerview);

        //create the adapter and attach it to the recyclerView
        final UserWeightListAdapter adapter = new UserWeightListAdapter(this);
        adapter.setUserWeights(userWeights);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }

    /*
    Opens the prompt to add a UserWeight record
     */
    public void promptAddWeightRecord(View view) {
        LinearLayout addRecordPrompt = findViewById(R.id.prompt_add_weight_record);
        addRecordPrompt.setVisibility(View.VISIBLE);
    }

    /*
    Opens the prompt to delete all UserWeight records
     */
    public void promptDeleteWeightRecords(View view) {
        LinearLayout deleteWarning = findViewById(R.id.delete_warning);
        deleteWarning.setVisibility(View.VISIBLE);
    }

    /*
    Closes the prompt to delete all UserWeight records
     */
    public void cancelDeleteWeightRecords(View view) {
        LinearLayout deleteWarning = findViewById(R.id.delete_warning);
        deleteWarning.setVisibility(View.GONE);
    }

    /*
    Deletes all weight records from the database
     */
    public void deleteWeightRecords(View view) {
        CSSRoomDatabase db = CSSRoomDatabase.getDatabase(getApplicationContext());
        db.userWeightDao().deleteCurrentUserWeights(userId);

        while(userWeights.size() > 0) {
            userWeights.remove(0);
        }

        //create a new adapter with the updated userWeights and attach it to the recyclerView
        final UserWeightListAdapter adapter = new UserWeightListAdapter(getApplicationContext());
        adapter.setUserWeights(userWeights);
        recyclerView.setAdapter(adapter);

        //hide the delete warning prompt
        LinearLayout deleteWarning = findViewById(R.id.delete_warning);
        deleteWarning.setVisibility(View.GONE);

        populateChart();
    }

    /*
    Closes the prompt to add a UserWeight record
     */
    public void cancelAddWeightRecord(View view) {
        LinearLayout addRecordPrompt = findViewById(R.id.prompt_add_weight_record);
        addRecordPrompt.setVisibility(View.GONE);

    }

    /*
    Adds a new UserWeight to the database if a valid weight is present in the weightView.
    If necessary, deletes the predictive UserWeight and creates a new predictive weight based on the
    updated information.
     */
    public void addWeightRecord(View view) {
        EditText weightView = findViewById(R.id.new_user_weight);
        if(weightView.getText().toString().equals("")) {
            Toast.makeText(getApplicationContext(), "Weight required", Toast.LENGTH_SHORT).show();
            return;
        }

        //retrieve the weight from the weightView
        int weightInPounds = Integer.parseInt(weightView.getText().toString());

        if(weightInPounds <= 0) {
            Toast.makeText(getApplicationContext(), "Invalid weight", Toast.LENGTH_SHORT).show();
            return;
        }

        //create a new UserWeight object with the current date
        UserWeight newUserWeight = new UserWeight(userId, weightInPounds, new Date().getTime());

        //insert the new UserWeight into the database
        CSSRoomDatabase db = CSSRoomDatabase.getDatabase(getApplicationContext());
        db.userWeightDao().insert(newUserWeight);

        //if the userWeights already has more than one record, then a predictive weight is present
        //and needs to be removed
        if(userWeights.size() > 1) userWeights.remove(userWeights.size()-1);
        userWeights.add(newUserWeight);

        //if there is more than one record in userWeights, add a predictive weight based on the
        //existing records using calculateFutureUserWeight, then repopulate the chart
        if(userWeights.size() > 1) {
            userWeights.add(calculateFutureUserWeight());
            populateChart();
        }

        //create a new adapter with the
        final UserWeightListAdapter adapter = new UserWeightListAdapter(getApplicationContext());
        adapter.setUserWeights(userWeights);
        recyclerView.setAdapter(adapter);

        //alert the user the record was added which can also be seen as the list updates
        Toast.makeText(getApplicationContext(), "Record added", Toast.LENGTH_SHORT).show();

        //hide the add record prompt
        LinearLayout addRecordPrompt = findViewById(R.id.prompt_add_weight_record);
        addRecordPrompt.setVisibility(View.GONE);


        //Hide the soft keyboard as it is no longer needed
        //Hide keyboard solution found at https://stackoverflow.com/questions/4165414/how-to-hide-soft-keyboard-on-android-after-clicking-outside-edittext
        InputMethodManager inputMethodManager =(InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);


    }



    /*
    Populates the chart with the UserWeight records present

    This chart is built off the open source MpAndroidChart library found here: https://github.com/PhilJay/MPAndroidChart
    Knowledge base for creating the chart is from the tutorial found at https://www.numetriclabz.com/android-line-chart-using-mpandroidchart-tutorial/
     */
    private void populateChart() {

        //get the lineChart and turn off the right axis and description labels
        LineChart lineChart = findViewById(R.id.user_weight_chart);
        lineChart.getAxisRight().setEnabled(false);
        lineChart.getDescription().setEnabled(false);

        //add the UserWeight records in userWeights as Entry objects
        //to an ArrayList titled userWeightEntries
        ArrayList<Entry> userWeightEntries = new ArrayList<>();
        for(int i = 0; i < userWeights.size(); i++) {
            UserWeight current = userWeights.get(i);
            userWeightEntries.add(new Entry(current.getWeightDateAsLong(), current.getWeightInPounds()));
        }

        //create a dataset from the userWeightEntries and color it red to match
        //the theme of the application
        LineDataSet dataSet = new LineDataSet(userWeightEntries, "User Weights");
        dataSet.setColor(Color.RED);
        dataSet.setHighLightColor(Color.RED);

        //create a LineData from the LineDataSet and use it to set the data in the lineChart
        LineData data = new LineData(dataSet);
        lineChart.setData(data);

        //the xAxis is not needed in this display as the list below shows the dates of each
        //record so turn it off
        lineChart.getXAxis().setEnabled(false);

    }

    /*
    The predictive model for the application, creates a predictive UserWeight based
    in the future using the existing UserWeights and a linear progression method

    @return the predicted future UserWeight object
     */
    private UserWeight calculateFutureUserWeight() {
        //get the first and last records for finding the date range
        UserWeight firstWeightRecord = userWeights.get(0);
        UserWeight lastWeightRecord = userWeights.get(userWeights.size()-1);

        long minWeightDate = firstWeightRecord.getWeightDateAsLong();
        long maxWeightDate = lastWeightRecord.getWeightDateAsLong();

        //get the date range and use it calculate the date of the predicted weight
        long datesRange = maxWeightDate - minWeightDate;
        long predictedWeightDate = maxWeightDate + (datesRange / 2);

        //find the min and max weights and their indices in userWeights
        int minWeightLbsIndex = findMinWeightIndex();
        int minWeight = userWeights.get(minWeightLbsIndex).getWeightInPounds();

        int maxWeightLbsIndex = findMaxWeightIndex();
        int maxWeight = userWeights.get(maxWeightLbsIndex).getWeightInPounds();

        //calculate the weight range for the user
        int weightLbsRange = maxWeight - minWeight;
        if(weightLbsRange < 0) weightLbsRange *= -1;

        //calculate the future weight based on the weight range and whether the user's weight is on a downtrend
        //or an uptrend, the prediction follows this trend
        int predictedWeightLbs;
        if(minWeightLbsIndex > maxWeightLbsIndex) predictedWeightLbs = userWeights.get(userWeights.size()-1).getWeightInPounds() - (weightLbsRange/2);
        else predictedWeightLbs = userWeights.get(userWeights.size()-1).getWeightInPounds() + (weightLbsRange/2);

        return new UserWeight(userId, predictedWeightLbs, predictedWeightDate);

    }

    /*
    Finds the index of the minimum weight in userWeights
    @return the index of the minimum weight in userWeights
     */
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

    /*
    Finds the index of the maximum weight in userWeights
    @return the index of the maximum weight in userWeights
     */
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
