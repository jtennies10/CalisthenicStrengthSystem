package tenniescorp.fitness.calisthenicstrengthsystem;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;

import static android.arch.persistence.room.ForeignKey.CASCADE;

@Entity(tableName="routine_exercise_table")
public class RoutineExercise {

    @PrimaryKey(autoGenerate = true)
    private long routineExerciseId;

    @ForeignKey(entity = Routine.class, parentColumns = "routineId", childColumns = "routineId",  onDelete = CASCADE)
    private long routineId;

    @ForeignKey(entity = Exercise.class, parentColumns = "exerciseId", childColumns = "exerciseId")
    private long exerciseId;

    public RoutineExercise(long routineId, long exerciseId) {
        this.routineId = routineId;
        this.exerciseId = exerciseId;
    }

    public long getRoutineId() {
        return routineId;
    }

    public long getExerciseId() {
        return exerciseId;
    }

    public long getRoutineExerciseId() {
        return routineExerciseId;
    }

    public void setRoutineExerciseId(long routineExerciseId) {
        this.routineExerciseId = routineExerciseId;
    }
}
