package tenniescorp.fitness.calisthenicstrengthsystem;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.util.Date;

/*
Defines a User object that is stored in the database within the user_table
 */
@Entity(tableName="user_table", indices = {@Index(value = "userName", unique = true), @Index(value = "email", unique = true)})
public class User {

    @PrimaryKey(autoGenerate = true)
    private long userId;

    @NonNull
    private String userName;

    @NonNull
    private String email;

    @NonNull
    private String password;

    @NonNull
    private String dateOfBirthAsString;


    private int heightInInches;


    public User(String userName,@NonNull String email, @NonNull String password,@NonNull String dateOfBirthAsString, int heightInInches) {
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.dateOfBirthAsString = dateOfBirthAsString;
        this.heightInInches = heightInInches;

    }

    public long getUserId() {
        return userId;
    }

    @NonNull
    public String getUserName() {
        return userName;
    }

    @NonNull
    public String getEmail() {
        return email;
    }

    @NonNull
    public String getPassword() {
        return password;
    }

    @NonNull
    public String getDateOfBirthAsString() {
        return dateOfBirthAsString;
    }

    public int getHeightInInches() {
        return heightInInches;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

}
