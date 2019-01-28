package tenniescorp.fitness.calisthenicstrengthsystem;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;


/*The code in the class relies heavily on the open source tutorial
found here https://codelabs.developers.google.com/codelabs/android-room-with-a-view/#6*/
@Database(entities =
        {User.class, Routine.class, RoutineExercise.class,
                UserRoutine.class, UserWeight.class, Exercise.class},
        version = 1)
public abstract class cssRoomDatabase extends RoomDatabase {

    public abstract UserDao userDao();

    private static volatile cssRoomDatabase INSTANCE;

    static cssRoomDatabase getDatabase(final Context context) {
        if(INSTANCE == null) {
            synchronized (cssRoomDatabase.class) {
                if(INSTANCE == null) {
                    //create database here
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            cssRoomDatabase.class, "css_database").build();
                }
            }
        }
        return INSTANCE;
    }
}
