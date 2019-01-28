package tenniescorp.fitness.calisthenicstrengthsystem;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import java.util.List;


@Dao
public interface UserRoutineDao {

    @Query("SELECT * FROM user_routine_table")
    LiveData<List<UserRoutine>> getAllUserRoutines();
}