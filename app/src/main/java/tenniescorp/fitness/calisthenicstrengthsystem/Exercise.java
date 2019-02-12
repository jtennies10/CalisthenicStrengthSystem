package tenniescorp.fitness.calisthenicstrengthsystem;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.io.Serializable;

/*
Defines and exercise object to be used in the database.
 */
@Entity(tableName="exercise_table")
public class Exercise implements Serializable {

    //autoGenerate the primary key for each exercise
    @PrimaryKey(autoGenerate = true)
    private long exerciseId;

    @NonNull
    private String exerciseName;

    private String exerciseDescription;

    @Ignore
    private boolean selected = false;

    public Exercise(@NonNull String exerciseName, String exerciseDescription) {
       // this.exerciseId = exerciseId;
        this.exerciseName = exerciseName;
        this.exerciseDescription = exerciseDescription;
    }

    public long getExerciseId() {
        return exerciseId;
    }

    @NonNull
    public String getExerciseName() {
        return exerciseName;
    }

    public String getExerciseDescription() {
        return exerciseDescription;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setExerciseId(long exerciseId) {
        this.exerciseId = exerciseId;
    }


    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
