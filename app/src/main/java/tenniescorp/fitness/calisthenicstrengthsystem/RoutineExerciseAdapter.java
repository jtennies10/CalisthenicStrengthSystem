package tenniescorp.fitness.calisthenicstrengthsystem;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/*
Defines an adapter for the RoutineExercises that populates a recycler view.
 */
public class RoutineExerciseAdapter extends RecyclerView.Adapter<RoutineExerciseAdapter.RoutineExerciseViewHolder> {
    private List<Exercise> routineExercises;
    private RecyclerViewClickListener listListener;
    private Context context;

    /*
    Inner class defining the view holder for the adapter.
     */
    class RoutineExerciseViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView exerciseItemView;
        RecyclerViewClickListener listListener;
        RoutineExerciseViewHolder(View v, RecyclerViewClickListener listListener) {
            super(v);
            exerciseItemView = v.findViewById(R.id.textView);
            this.listListener = listListener;
            v.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            listListener.onClick(view, getAdapterPosition());
        }
    }

    RoutineExerciseAdapter(Context context, List<Exercise> routineExercises, RecyclerViewClickListener listListener) {
        this.routineExercises = routineExercises;
        this.listListener = listListener;
        this.context = context;

    }

    /*
    Inflates the view when called upon view holder creation
    @return a RoutineExerciseViewHolder
     */
    @Override
    public RoutineExerciseAdapter.RoutineExerciseViewHolder onCreateViewHolder(ViewGroup parent,
                                                                               int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.recyclerview_item, parent, false);

        return new RoutineExerciseViewHolder(v, listListener);
    }


    /*
     Sets the text for each exercise view in the view holder
     */
    @Override
    public void onBindViewHolder(RoutineExerciseViewHolder holder, int position) {
        holder.exerciseItemView.setText(routineExercises.get(position).getExerciseName());

    }


    /*
    Returns the number of exercises in routineExercise or 0 if routineExercises is null
     */
    @Override
    public int getItemCount() {
        return (routineExercises == null) ? 0 : routineExercises.size();
    }
}
