package tenniescorp.fitness.calisthenicstrengthsystem;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.ArrayList;
import java.util.List;

/*
Defines the DAO for interacting with the routine_exercise_table and exercise_table in the database
 */
@Dao
public interface RoutineExerciseDao {

    @Query("SELECT * FROM routine_exercise_table")
    LiveData<List<RoutineExercise>> getAllRoutineExercises();

    //gets all the exercises from exercise_table that are associated with the passed routineId
    //using a complex query
    @Query("SELECT * FROM exercise_table WHERE ExerciseId IN (SELECT ExerciseId FROM routine_exercise_table WHERE RoutineId = :routineId)")
    List<Exercise> getSpecificRoutineExercises(long routineId);

    @Insert
    long insert(RoutineExercise routineExercise);

    @Query("DELETE FROM routine_exercise_table WHERE :routineId = RoutineId")
    void deleteRoutineExercises(long routineId);
}