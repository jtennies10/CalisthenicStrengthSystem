package tenniescorp.fitness.calisthenicstrengthsystem;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.io.Serializable;

@Entity(tableName="exercise_table")
public class Exercise implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int exerciseId = 0;

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

    public int getExerciseId() {
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

    public void setExerciseId(int exerciseId) {
        this.exerciseId = exerciseId;
    }

    public void setExerciseName(@NonNull String exerciseName) {
        this.exerciseName = exerciseName;
    }

    public void setExerciseDescription(String exerciseDescription) {
        this.exerciseDescription = exerciseDescription;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
