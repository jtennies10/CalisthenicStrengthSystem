package tenniescorp.fitness.calisthenicstrengthsystem;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;

import static android.arch.persistence.room.ForeignKey.CASCADE;

@Entity(tableName="routine_exercise_table")
public class RoutineExercise {

    @PrimaryKey(autoGenerate = true)
    private int routineExerciseId;

    @ForeignKey(entity = Routine.class, parentColumns = "routineId", childColumns = "routineId",  onDelete = CASCADE)
    private int routineId;

    @ForeignKey(entity = Exercise.class, parentColumns = "exerciseId", childColumns = "exerciseId")
    private int exerciseId;

    public RoutineExercise(int routineId, int exerciseId) {
        this.routineId = routineId;
        this.exerciseId = exerciseId;
    }

    public int getRoutineId() {
        return routineId;
    }

    public int getExerciseId() {
        return exerciseId;
    }

    public int getRoutineExerciseId() {
        return routineExerciseId;
    }

    public void setRoutineExerciseId(int routineExerciseId) {
        this.routineExerciseId = routineExerciseId;
    }
}
