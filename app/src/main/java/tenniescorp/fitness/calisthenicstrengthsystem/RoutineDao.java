package tenniescorp.fitness.calisthenicstrengthsystem;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface RoutineDao {

    @Query("SELECT * FROM routine_table")
    LiveData<List<Routine>> getAllRoutines();
}