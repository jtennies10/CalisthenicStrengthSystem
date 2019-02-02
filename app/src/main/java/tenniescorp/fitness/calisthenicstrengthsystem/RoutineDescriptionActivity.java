package tenniescorp.fitness.calisthenicstrengthsystem;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

public class RoutineDescriptionActivity extends AppCompatActivity {

    List<Exercise> routineExercises;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_routine_description);

        Toolbar toolbar = findViewById(R.id.routine_list_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);



        TextView routineName = findViewById(R.id.routine_description_name);
        TextView routineDescription = findViewById(R.id.routine_description_description);

        //get recycler view and populate using query to get all exercises with the correct workout_id
        RecyclerView recyclerView = findViewById(R.id.recyclerview);

        Routine currentRoutine = (Routine) getIntent().getSerializableExtra("Routine");
        routineName.setText(currentRoutine.getRoutineName());
        routineDescription.setText(currentRoutine.getRoutineDescription());

        CSSRoomDatabase db = CSSRoomDatabase.getDatabase(this);
        routineExercises = db.routineExerciseDao().getSpecificRoutineExercises(currentRoutine.getRoutineId());

        RecyclerViewClickListener clickListener = (view, position) -> {
            // Toast.makeText(view.getContext(), "Position " + position, Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(RoutineDescriptionActivity.this, ExerciseDescriptionActivity.class);

            //send the selected Exercise with the intent
            intent.putExtra("Exercise", routineExercises.get(position));
            startActivity(intent);
        };

        //create the list adapter and fill it with the data
        final RoutineExerciseAdapter adapter = new RoutineExerciseAdapter(getApplicationContext(), routineExercises, clickListener);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));



    }

    public void toggleRoutineOptions(View v) {
        Button deleteRoutineBtn = findViewById(R.id.routine_delete_button);
        Button editExerciseBtn = findViewById(R.id.routine_edit_exercises_button);
        Button editRoutineBtn = findViewById(R.id.routine_edit_info_button);
        if(deleteRoutineBtn.getVisibility() == View.GONE) {
            deleteRoutineBtn.setVisibility(View.VISIBLE);
            editExerciseBtn.setVisibility(View.VISIBLE);
            editRoutineBtn.setVisibility(View.VISIBLE);
        } else {
            deleteRoutineBtn.setVisibility(View.GONE);
            editExerciseBtn.setVisibility(View.GONE);
            editRoutineBtn.setVisibility(View.GONE);
        }

    }



     private class RoutineExerciseAdapter extends RecyclerView.Adapter<RoutineExerciseAdapter.RoutineExerciseViewHolder> {
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
            return routineExercises.size();
        }
    }
}
