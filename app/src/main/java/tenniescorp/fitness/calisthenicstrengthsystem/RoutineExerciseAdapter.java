package tenniescorp.fitness.calisthenicstrengthsystem;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class RoutineExerciseAdapter extends RecyclerView.Adapter<RoutineExerciseAdapter.RoutineExerciseViewHolder> {
    List<Exercise> routineExercises;
    RecyclerViewClickListener listListener;
    Context context;

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


    @Override
    public RoutineExerciseAdapter.RoutineExerciseViewHolder onCreateViewHolder(ViewGroup parent,
                                                                               int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.recyclerview_item, parent, false);

        return new RoutineExerciseViewHolder(v, listListener);
    }


    @Override
    public void onBindViewHolder(RoutineExerciseViewHolder holder, int position) {
        holder.exerciseItemView.setText(routineExercises.get(position).getExerciseName());

    }


    @Override
    public int getItemCount() {
        return (routineExercises == null) ? 0 : routineExercises.size();
    }
}
