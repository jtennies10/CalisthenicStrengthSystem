package tenniescorp.fitness.calisthenicstrengthsystem;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

/*
Defines the DAO for working with the exercise_table
 */
@Dao
public interface ExerciseDao {

    //Query that returns all the exercises in the database
    @Query("SELECT * FROM exercise_table")
    LiveData<List<Exercise>> getAllExercises();

    //Inserts an exercise into exercise_table
    @Insert
    long insert(Exercise exercise);
}
