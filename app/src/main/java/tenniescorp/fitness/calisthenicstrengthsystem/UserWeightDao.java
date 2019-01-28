package tenniescorp.fitness.calisthenicstrengthsystem;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import java.util.List;


@Dao
public interface UserWeightDao {

    @Query("SELECT * FROM user_weight_table")
    LiveData<List<UserWeight>> getAllUserWeights();
}