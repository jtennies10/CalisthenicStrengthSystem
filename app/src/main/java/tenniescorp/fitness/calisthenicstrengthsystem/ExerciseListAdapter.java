package tenniescorp.fitness.calisthenicstrengthsystem;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/*
Defines a custom adapter for the exercise list that extends RecyclerView.Adapter

Code adapted from
https://codelabs.developers.google.com/codelabs/android-room-with-a-view/#10
 */
public class ExerciseListAdapter extends RecyclerView.Adapter<ExerciseListAdapter.ExerciseViewHolder>{

    /*
    Defines an inner class for the ExerciseViewHolder
     */
    class ExerciseViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

        //member variables for the check mark, textview, and click listeners
        private final TextView exerciseItemView;
        private final ImageView exerciseCheckedView;
        private RecyclerViewClickListener listListener;
        private RecyclerViewLongClickListener longListListener;

        /*
        Private constructor that assigns the passed arguments to the member variables and attaches the
        click listeners
         */
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

        /*
        Defines the action taken on a click event, in which the listListener's onClick method is
        called with the adapter position
         */
        @Override
        public void onClick(View view) {
            listListener.onClick(view, getAdapterPosition());
            notifyDataSetChanged();
        }

        /*
        Defines the action taken on a click event, in which the longListListener's onLongClick method is
        called with the adapter position
         */
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

    /*
    Constructor the inflates the view and assigns the click listener arguments
     */
    ExerciseListAdapter(Context context, RecyclerViewClickListener listListener,
                        RecyclerViewLongClickListener longListListener) {
        inflater = LayoutInflater.from(context);
        this.listListener = listListener;
        this.longListListener = longListListener;
    }

    /*
    Called upon view holder creation, inflates the list and instantiates the view holder
    @return the view holder
     */
    @Override
    public ExerciseListAdapter.ExerciseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.exercise_recyclerview_item, parent, false);
        return new ExerciseListAdapter.ExerciseViewHolder(itemView, listListener, longListListener);
    }

    /*
    Called to populate each item in the list, sets each items TextView to the current Exercise's name
     */
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

    /*
    Used to update the exercises in the exerciseList
     */
    void setExercises(List<Exercise> exercises){
        exerciseList = exercises;
        notifyDataSetChanged();
    }

    /*
    Returns the item count for exerciseList or zero if the list is null
     */
    @Override
    public int getItemCount() {
        if (exerciseList != null)
            return exerciseList.size();
        else return 0;
    }
//
//    public void changeItemState(int position) {
//        if(exerciseList.get(position).isSelected()) exerciseList.get(position).setSelected(false);
//        else exerciseList.get(position).setSelected(true);
//    }
}
