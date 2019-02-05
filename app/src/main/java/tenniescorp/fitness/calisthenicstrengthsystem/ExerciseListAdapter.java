package tenniescorp.fitness.calisthenicstrengthsystem;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class ExerciseListAdapter extends RecyclerView.Adapter<ExerciseListAdapter.ExerciseViewHolder>{

    class ExerciseViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        private final TextView exerciseItemView;
        private final ImageView exerciseCheckedView;
        private RecyclerViewClickListener listListener;
        private RecyclerViewLongClickListener longListListener;

        private ExerciseViewHolder(View itemView, RecyclerViewClickListener listListener,
                                   RecyclerViewLongClickListener longListListener) {
            super(itemView);
            exerciseItemView = itemView.findViewById(R.id.exercise_text_view);
            exerciseCheckedView = itemView.findViewById(R.id.exercise_checked);
            this.listListener = listListener;
            this.longListListener = longListListener;
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);

        }

        @Override
        public void onClick(View view) {
            listListener.onClick(view, getAdapterPosition());
        }

        @Override
        public boolean onLongClick(View view) {
            longListListener.onClick(view, getAdapterPosition());
            return false;
        }
    }

    private final LayoutInflater inflater;
    private List<Exercise> exerciseList; // Cached copy of words
    private RecyclerViewClickListener listListener;
    private RecyclerViewLongClickListener longListListener;

    ExerciseListAdapter(Context context, RecyclerViewClickListener listListener,
                        RecyclerViewLongClickListener longListListener) {
        inflater = LayoutInflater.from(context);
        this.listListener = listListener;
        this.longListListener = longListListener;
    }

    @Override
    public ExerciseListAdapter.ExerciseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.exercise_recyclerview_item, parent, false);
        return new ExerciseListAdapter.ExerciseViewHolder(itemView, listListener, longListListener);
    }

    @Override
    public void onBindViewHolder(ExerciseListAdapter.ExerciseViewHolder holder, int position) {
        if (exerciseList != null) {
            Exercise current = exerciseList.get(position);
            if(current.isSelected()) holder.exerciseCheckedView.setVisibility(View.VISIBLE);
            //else holder.exerciseCheckedView.setVisibility(View.VISIBLE);
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
