package tenniescorp.fitness.calisthenicstrengthsystem;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

/*
Defines the DAO for interacting with the user_weight_table
 */
@Dao
public interface UserWeightDao {

    @Query("SELECT * FROM user_weight_table")
    List<UserWeight> getAllUserWeights();

    /*
    Gets the weight records for the user ordered by their dates\
    @return the ordered list of UserWeights
     */
    @Query("SELECT * FROM user_weight_table WHERE userId=:userId ORDER BY weightDateAsLong")
    List<UserWeight> getCurrentUserWeights(long userId);

    @Query("DELETE FROM user_weight_table WHERE userId=:userId")
    void deleteCurrentUserWeights(long userId);

    @Insert
    long insert(UserWeight userWeight);
}