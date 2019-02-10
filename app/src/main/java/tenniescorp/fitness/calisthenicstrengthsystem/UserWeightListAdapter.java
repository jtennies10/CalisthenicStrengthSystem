package tenniescorp.fitness.calisthenicstrengthsystem;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

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
    public void onBindViewHolder(ExerciseListAdapter.ExerciseViewHolder holder, int position) {
        if (userWeightList != null) {
            UserWeight current = userWeightList.get(position);
            holder.exerciseItemView.setText(current.getExerciseName());
        } else {
            // Covers the case of data not being ready yet.
            holder.exerciseItemView.setText("No Exercises");
        }
    }

    void setExercises(List<Exercise> exercises){
        exerciseList = exercises;
        notifyDataSetChanged();
    }

    // getItemCount() is called many times, and when it is first called,
    // mWords has not been updated (means initially, it's null, and we can't return null).
    @Override
    public int getItemCount() {
        if (exerciseList != null)
            return exerciseList.size();
        else return 0;
    }

    public void changeItemState(int position) {
        if(exerciseList.get(position).isSelected()) exerciseList.get(position).setSelected(false);
        else exerciseList.get(position).setSelected(true);
    }
}
