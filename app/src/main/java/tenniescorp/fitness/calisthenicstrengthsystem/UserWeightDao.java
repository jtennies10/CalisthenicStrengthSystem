package tenniescorp.fitness.calisthenicstrengthsystem;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;


@Dao
public interface UserWeightDao {

    @Query("SELECT * FROM user_weight_table")
    List<UserWeight> getAllUserWeights();

    @Query("SELECT * FROM user_weight_table WHERE userId=:userId")
    List<UserWeight> getCurrentUserWeights(long userId);

    @Query("DELETE FROM user_weight_table WHERE userId=:userId")
    void deleteCurrentUserWeights(long userId);

    @Insert
    long insert(UserWeight userWeight);
}