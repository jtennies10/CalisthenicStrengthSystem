package tenniescorp.fitness.calisthenicstrengthsystem;

import android.content.Context;
import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/*
Defines the adapter for displaying UserWeight data in a RecyclerView
 */
public class UserWeightListAdapter extends RecyclerView.Adapter<UserWeightListAdapter.UserWeightViewHolder>{

    /*
    Inner class that defines the ViewHolder for the UserWeights
     */
    class UserWeightViewHolder extends RecyclerView.ViewHolder {
        private final TextView userWeightTextView;
        private final TextView dateTextView;

        private UserWeightViewHolder(View itemView) {
            super(itemView);
            userWeightTextView = itemView.findViewById(R.id.user_weight_text_view);
            dateTextView = itemView.findViewById(R.id.date_text_view);
        }
    }

    private final LayoutInflater inflater;
    private List<UserWeight> userWeightList;

    /*
    Constructor that assigns the inflater based on the passed in Context
     */
    UserWeightListAdapter(Context context) {
        inflater = LayoutInflater.from(context);
    }

    /*
    Inflates the itemView when the ViewHolder is created and returns a new UserWeightViewHolder
     */
    @Override
    public UserWeightListAdapter.UserWeightViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.userweight_recyclerview_item, parent, false);
        return new UserWeightListAdapter.UserWeightViewHolder(itemView);
    }

    /*
    Populates the items in the list with the user weight dates and weights if the userWeightList is not null
     */
    @Override
    public void onBindViewHolder(UserWeightListAdapter.UserWeightViewHolder holder, int position) {
        if (userWeightList != null) {
            UserWeight current = userWeightList.get(position);

            String userWeight = current.getWeightInPounds() + "lbs";

            //if this is the last weight in the list and the list has a size greater than one,
            //denote this item is a predicted weight
            if(current == userWeightList.get(userWeightList.size()-1)
                    && userWeightList.size() > 1) userWeight += " (Predicted)";

            //set the view texts, using getDateAsString for the dateTextView
            holder.userWeightTextView.setText(userWeight);
            holder.dateTextView.setText(getDateAsString(current));

        } else {
            // Covers the case of data not being ready yet
            String noRecords = "No Records";
            holder.dateTextView.setText(noRecords);

        }
    }

    /*
    Updates the list of UserWeights used by the adapter
     */
    void setUserWeights(List<UserWeight> userWeights){
        userWeightList = userWeights;
        notifyDataSetChanged();
    }

    /*
    Returns the number of items in userWeightList or 0 if userWeightList is null
     */
    @Override
    public int getItemCount() {
        if (userWeightList != null)
            return userWeightList.size();
        else return 0;
    }

    /*
    Formats a date based on the passed in UserWeight
    @return the date as a string in format MM/dd/yyyy
     */
    private String getDateAsString(UserWeight userWeight) {
        Date date = new Date(userWeight.getWeightDateAsLong());
        return  new SimpleDateFormat("MM/dd/yyyy").format(date);
    }

}
