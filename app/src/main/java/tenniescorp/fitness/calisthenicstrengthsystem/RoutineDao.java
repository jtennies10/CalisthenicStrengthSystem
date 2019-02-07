package tenniescorp.fitness.calisthenicstrengthsystem;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface RoutineDao {

    @Insert
    long insert(Routine routine);

    @Query("SELECT * FROM routine_table ORDER BY favorited DESC")
    LiveData<List<Routine>> getAllRoutines();

    @Query("DELETE FROM routine_table")
    void deleteAll();

    @Query("DELETE FROM routine_table WHERE routineId = :routineId")
    void deleteSpecificRoutine(long routineId);

    @Update
    void update(Routine routine);
}