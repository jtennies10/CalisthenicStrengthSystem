package tenniescorp.fitness.calisthenicstrengthsystem;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface RoutineDao {

    @Insert
    void insert(Routine routine);

    @Query("SELECT * FROM routine_table")
    LiveData<List<Routine>> getAllRoutines();

    @Query("DELETE FROM routine_table")
    void deleteAll();
}