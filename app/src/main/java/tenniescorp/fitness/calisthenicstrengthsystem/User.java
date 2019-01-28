package tenniescorp.fitness.calisthenicstrengthsystem;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.util.Date;

@Entity(tableName="user_table")
public class User {

    @PrimaryKey(autoGenerate = true)
    private int userId;

    @NonNull
    private String userName;

    @NonNull
    private String email;

    @NonNull
    private String password;

    @NonNull
    private Date dateOfBirth;


    private int heightInInches;


    public User(int userId, @NonNull String userName,@NonNull String email,@NonNull String password,@NonNull Date dateOfBirth) {
        this.userId = userId;
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.dateOfBirth = dateOfBirth;
    }

    public User(int userId, String userName,@NonNull String email, @NonNull String password,@NonNull Date dateOfBirth, int heightInInches) {
        this.userId = userId;
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.dateOfBirth = dateOfBirth;
        this.heightInInches = heightInInches;
    }

    public int getUserId() {
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
    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public int getHeightInInches() {
        return heightInInches;
    }
}
