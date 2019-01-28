package tenniescorp.fitness.calisthenicstrengthsystem;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;

@Entity(tableName="routine_exercise_table", primaryKeys = "routineId exerciseId")
public class RoutineExercise {

    @ForeignKey(entity = Routine.class, parentColumns = "routineId", childColumns = "routineId")
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
}
