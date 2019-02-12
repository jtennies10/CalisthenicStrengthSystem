package tenniescorp.fitness.calisthenicstrengthsystem;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import java.util.List;

/*
Defines the DAO for interacting with the user_routine_table
 */
@Dao
public interface UserRoutineDao {

    @Query("SELECT * FROM user_routine_table WHERE userId = :userId")
    List<UserRoutine> getCurrentUserRoutines(int userId);
}