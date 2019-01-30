package tenniescorp.fitness.calisthenicstrengthsystem;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;


/*The code in the class relies heavily on the open source tutorial
found here https://codelabs.developers.google.com/codelabs/android-room-with-a-view/#6*/
@Database(entities =
        {User.class, Routine.class, RoutineExercise.class,
                UserRoutine.class, UserWeight.class, Exercise.class},
        version = 1)
public abstract class CSSRoomDatabase extends RoomDatabase {

    public abstract UserDao userDao();
    public abstract RoutineDao routineDao();
    public abstract ExerciseDao exerciseDao();
    public abstract RoutineExerciseDao routineExerciseDao();

    private static volatile CSSRoomDatabase INSTANCE;

    static CSSRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (CSSRoomDatabase.class) {
                if (INSTANCE == null) {
                    //create database here
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            CSSRoomDatabase.class, "css_database")
                            .addCallback(roomDatabaseCallback).build();
                }
            }
        }
        return INSTANCE;
    }

    private static RoomDatabase.Callback roomDatabaseCallback =
            new RoomDatabase.Callback() {

                @Override
                public void onOpen(@NonNull SupportSQLiteDatabase db) {
                    super.onOpen(db);
                    new PopulateDbAsync(INSTANCE).execute();
                }
            };

    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final RoutineDao routineDao;
//        private final ExerciseDao exerciseDao;
//        private final RoutineExerciseDao routineExerciseDao;

        PopulateDbAsync(CSSRoomDatabase db) {
            routineDao = db.routineDao();
        }

        @Override
        protected Void doInBackground(final Void... params) {
            if(routineDao.getAllRoutines().getValue() == null) {
                Routine routine = new Routine("Beginner Push",
                        "A beginner level workout routine that works the pushing muscles of the upper body. This workout is good for those who are " +
                                "looking to get into a good fitness routine and hope to build a foundation of strength.");
                routineDao.insert(routine);
                routine = new Routine("Beginner Pull",
                        " A beginner level workout routine that works the pulling muscles of the upper body. This workout is good for those who are looking to get into a " +
                                "good fitness routine and hope to build a foundation of strength.");
                routineDao.insert(routine);
                routine = new Routine("Beginner Legs",
                        "A beginner level workout routine that works the muscles of the lower body. This workout is good for those who are looking to get into" +
                                " a good fitness routine and hope to build a foundation of strength.");
                routineDao.insert(routine);
            }
            return null;
        }
    }
}
