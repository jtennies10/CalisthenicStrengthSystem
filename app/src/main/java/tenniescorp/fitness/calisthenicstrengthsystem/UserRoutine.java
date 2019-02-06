package tenniescorp.fitness.calisthenicstrengthsystem;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;

@Entity(tableName="user_routine_table", primaryKeys = {"userId", "routineId"})
public class UserRoutine {

    @ForeignKey(entity = User.class, parentColumns = "userId", childColumns = "userId")
    private long userId;

    @ForeignKey(entity = Routine.class, parentColumns = "routineId", childColumns = "routineId")
    private long routineId;

    public UserRoutine(long userId, long routineId) {
        this.userId = userId;
        this.routineId = routineId;
    }

    public long getUserId() {
        return userId;
    }

    public long getRoutineId() {
        return routineId;
    }


}
