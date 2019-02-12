package tenniescorp.fitness.calisthenicstrengthsystem;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

/*
Defines the DAO for working with the routine_table
 */
@Dao
public interface RoutineDao {

    @Insert
    long insert(Routine routine);

    @Query("SELECT * FROM routine_table ORDER BY favorited DESC")
    LiveData<List<Routine>> getAllRoutines();

    @Query("DELETE FROM routine_table WHERE routineId = :routineId")
    void deleteSpecificRoutine(long routineId);

    @Query("UPDATE routine_table SET routineDescription=:routineDescription, routineName=:routineName, favorited=:favorited WHERE routineId=:routineId")
    void update(long routineId, String routineName, String routineDescription, boolean favorited);
}