package tenniescorp.fitness.calisthenicstrengthsystem;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class RoutineDescriptionActivity extends AppCompatActivity {

    ExerciseViewModel exerciseViewModel;

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
        List<Exercise> routineExercises = db.routineExerciseDao().getSpecificRoutineExercises(currentRoutine.getRoutineId());

        //create the list adapter and fill it with the data
        final RoutineExerciseAdapter adapter = new RoutineExerciseAdapter(routineExercises);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));



    }

     public class RoutineExerciseAdapter extends RecyclerView.Adapter<RoutineExerciseAdapter.RoutineExerciseViewHolder> {
         List<Exercise> routineExercises;

         class RoutineExerciseViewHolder extends RecyclerView.ViewHolder {
            TextView exerciseItemView;
            RoutineExerciseViewHolder(View v) {
                super(v);
                exerciseItemView = v.findViewById(R.id.textView);
            }
        }

        RoutineExerciseAdapter(List<Exercise> routineExercises) {
            this.routineExercises = routineExercises;
        }


        @Override
        public RoutineExerciseAdapter.RoutineExerciseViewHolder onCreateViewHolder(ViewGroup parent,
                                                         int viewType) {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.recyclerview_item, parent, false);

            return new RoutineExerciseViewHolder(v);
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
