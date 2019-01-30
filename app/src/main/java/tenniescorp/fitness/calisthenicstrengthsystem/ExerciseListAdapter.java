package tenniescorp.fitness.calisthenicstrengthsystem;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class ExerciseListAdapter extends RecyclerView.Adapter<ExerciseListAdapter.ExerciseViewHolder>{

    class ExerciseViewHolder extends RecyclerView.ViewHolder {
        private final TextView exerciseItemView;

        private ExerciseViewHolder(View itemView) {
            super(itemView);
            exerciseItemView = itemView.findViewById(R.id.textView);
        }
    }

    private final LayoutInflater inflater;
    private List<Exercise> exerciseList; // Cached copy of words

    ExerciseListAdapter(Context context) { inflater = LayoutInflater.from(context); }

    @Override
    public ExerciseListAdapter.ExerciseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.recyclerview_item, parent, false);
        return new ExerciseListAdapter.ExerciseViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ExerciseListAdapter.ExerciseViewHolder holder, int position) {
        if (exerciseList != null) {
            Exercise current = exerciseList.get(position);
            holder.exerciseItemView.setText(current.getExerciseName());
        } else {
            // Covers the case of data not being ready yet.
            holder.exerciseItemView.setText("No Word");
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
}
