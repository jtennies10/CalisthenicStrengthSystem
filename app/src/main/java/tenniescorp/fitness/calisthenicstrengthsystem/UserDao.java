package tenniescorp.fitness.calisthenicstrengthsystem;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

/*
Defines a DAO for interacting with the user_table to insert, delete, and retrieve Users.
 */
@Dao
public interface UserDao {

    @Insert
    long insert(User user);

    @Query("DELETE FROM user_table")
    void deleteAll();

    @Query("SELECT * FROM user_table ORDER BY userName ASC")
    List<User> getAllUsers();

    @Query("SELECT * FROM user_table WHERE (userName = :userNameEmail OR email = :userNameEmail)" +
            "AND password = :userPassword")
    List<User>getSpecificUser(String userNameEmail, String userPassword);

}
