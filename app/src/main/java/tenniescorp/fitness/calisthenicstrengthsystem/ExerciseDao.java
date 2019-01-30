package tenniescorp.fitness.calisthenicstrengthsystem;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface ExerciseDao {

    @Query("SELECT * FROM exercise_table")
    LiveData<List<Exercise>> getAllExercises();

    @Insert
    void insert(Exercise exercise);
}
