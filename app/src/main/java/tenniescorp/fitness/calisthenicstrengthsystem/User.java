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
    private String username;

    @NonNull
    private String email;

    @NonNull
    private String password;

    @NonNull
    private Date dateOfBirth;


    private int heightInInches;


    public User(int userId, String username, String email, String password, Date dateOfBirth) {
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.password = password;
        this.dateOfBirth = dateOfBirth;
    }

    public User(int userId, String username, String email, String password, Date dateOfBirth, int heightInInches) {
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.password = password;
        this.dateOfBirth = dateOfBirth;
        this.heightInInches = heightInInches;
    }

    public int getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public int getHeightInInches() {
        return heightInInches;
    }
}
