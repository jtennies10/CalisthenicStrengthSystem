package tenniescorp.fitness.calisthenicstrengthsystem;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName="user_routine_table", primaryKeys = {"userId", "routineId"})
public class UserRoutine {

    @ForeignKey(entity = User.class, parentColumns = "userId", childColumns = "userId")
    private int userId;

    @ForeignKey(entity = Routine.class, parentColumns = "routineId", childColumns = "routineId")
    private int routineId;

    public UserRoutine(int userId, int routineId) {
        this.userId = userId;
        this.routineId = routineId;
    }

    public int getUserId() {
        return userId;
    }

    public int getRoutineId() {
        return routineId;
    }


}
