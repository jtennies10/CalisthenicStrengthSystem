package tenniescorp.fitness.calisthenicstrengthsystem;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;

import static android.arch.persistence.room.ForeignKey.CASCADE;

/*
Defines a UserRoutine object that is stored in the user_routine_table and act as a joining table
for User and Routine records.
 */
@Entity(tableName="user_routine_table", primaryKeys = {"userId", "routineId"})
public class UserRoutine {

    @ForeignKey(entity = User.class, parentColumns = "userId", childColumns = "userId", onDelete = CASCADE)
    private long userId;

    @ForeignKey(entity = Routine.class, parentColumns = "routineId", childColumns = "routineId", onDelete = CASCADE)
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
