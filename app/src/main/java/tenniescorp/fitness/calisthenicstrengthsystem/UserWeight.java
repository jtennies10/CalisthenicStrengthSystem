package tenniescorp.fitness.calisthenicstrengthsystem;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.util.Date;

@Entity(tableName="user_weight_table")
public class UserWeight {

    @PrimaryKey(autoGenerate = true)
    private int weightId;

    @ForeignKey(entity = User.class, parentColumns = "userId", childColumns = "userId")
    private int userId;

    @NonNull
    private int weightInPounds;

    @NonNull
    private String weightDateAsString;

    public UserWeight(int weightId, int userId, int weightInPounds, @NonNull String weightDateAsString) {
        this.weightId = weightId;
        this.userId = userId;
        this.weightInPounds = weightInPounds;
        this.weightDateAsString = weightDateAsString;
    }

    public int getWeightId() {
        return weightId;
    }

    public int getUserId() {
        return userId;
    }

    public int getWeightInPounds() {
        return weightInPounds;
    }

    @NonNull
    public String getWeightDateAsString() {
        return weightDateAsString;
    }

    public void setWeightInPounds(int weightInPounds) {
        this.weightInPounds = weightInPounds;
    }

    public void setWeightDateAsString(@NonNull String weightDateAsString) {
        this.weightDateAsString = weightDateAsString;
    }
}
