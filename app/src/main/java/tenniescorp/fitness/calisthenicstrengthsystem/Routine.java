package tenniescorp.fitness.calisthenicstrengthsystem;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import java.io.Serializable;

@Entity(tableName="routine_table")
public class Routine implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int routineId = 0;

    @NonNull
    private String routineName;

    private String routineDescription;


    public Routine(@NonNull String routineName, String routineDescription) {
        //this.routineId = routineId;
        this.routineName = routineName;
        this.routineDescription = routineDescription;
    }

    public int getRoutineId() {
        return routineId;
    }

    @NonNull
    public String getRoutineName() {
        return routineName;
    }

    public String getRoutineDescription() {
        return routineDescription;
    }

    public void setRoutineId(int routineId) {
        this.routineId = routineId;
    }

    public void setRoutineName(@NonNull String routineName) {
        this.routineName = routineName;
    }

    public void setRoutineDescription(String routineDescription) {
        this.routineDescription = routineDescription;
    }
}
