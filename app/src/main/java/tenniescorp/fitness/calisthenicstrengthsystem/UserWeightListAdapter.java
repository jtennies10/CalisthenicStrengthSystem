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

public class UserWeightListAdapter extends RecyclerView.Adapter<UserWeightListAdapter.UserWeightViewHolder>{

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

    UserWeightListAdapter(Context context) {
        inflater = LayoutInflater.from(context);
    }

    @Override
    public UserWeightListAdapter.UserWeightViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.userweight_recyclerview_item, parent, false);
        return new UserWeightListAdapter.UserWeightViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(UserWeightListAdapter.UserWeightViewHolder holder, int position) {
        if (userWeightList != null) {
            UserWeight current = userWeightList.get(position);

            String userWeight = current.getWeightInPounds() + "lbs";
            if(current == userWeightList.get(userWeightList.size()-1)
                    && userWeightList.size() > 1) userWeight += " (Predicted)";

            holder.userWeightTextView.setText(userWeight);
            holder.dateTextView.setText(getDateAsString(current));

        } else {
            // Covers the case of data not being ready yet
            String noRecords = "No Records";
            holder.dateTextView.setText(noRecords);

        }
    }

    void setUserWeights(List<UserWeight> userWeights){
        userWeightList = userWeights;
        notifyDataSetChanged();
    }

    // getItemCount() is called many times, and when it is first called,
    // mWords has not been updated (means initially, it's null, and we can't return null).
    @Override
    public int getItemCount() {
        if (userWeightList != null)
            return userWeightList.size();
        else return 0;
    }

    private String getDateAsString(UserWeight userWeight) {
        Date date = new Date(userWeight.getWeightDateAsLong());
        return  new SimpleDateFormat("MM/dd/yyyy").format(date);
    }

}
