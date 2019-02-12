package tenniescorp.fitness.calisthenicstrengthsystem;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import java.io.Serializable;

/*
Defines a Routine object which is stored in the database table named routine_table
 */
@Entity(tableName="routine_table")
public class Routine implements Serializable {

    //autoGenerate the primary key
    @PrimaryKey(autoGenerate = true)
    private long routineId;

    @NonNull
    private String routineName;

    private String routineDescription;

    //denotes whether a given routine is favorited or not
    private boolean favorited = false;


    public Routine(@NonNull String routineName, String routineDescription) {
        //this.routineId = routineId;
        this.routineName = routineName;
        this.routineDescription = routineDescription;
    }

    public long getRoutineId() {
        return routineId;
    }

    @NonNull
    public String getRoutineName() {
        return routineName;
    }

    public String getRoutineDescription() {
        return routineDescription;
    }

    public boolean isFavorited() {
        return favorited;
    }

    public void setRoutineId(long routineId) {
        this.routineId = routineId;
    }

    public void setRoutineName(@NonNull String routineName) {
        this.routineName = routineName;
    }

    public void setRoutineDescription(String routineDescription) {
        this.routineDescription = routineDescription;
    }

    public void setFavorited(boolean favorited) {
        this.favorited = favorited;
    }
}
