package tenniescorp.fitness.calisthenicstrengthsystem;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;


/*The code in the class relies heavily in the open source tutorial
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
                            CSSRoomDatabase.class, "css_database").allowMainThreadQueries()
                            .addCallback(roomDatabaseCallback).build();
                }
            }
        }
        return INSTANCE;
    }

    private static RoomDatabase.Callback roomDatabaseCallback =
            new RoomDatabase.Callback() {

                @Override
                public void onCreate(@NonNull SupportSQLiteDatabase db) {
                    new PopulateDbAsync(INSTANCE).execute();
                }

                @Override
                public void onOpen(@NonNull SupportSQLiteDatabase db) {
                    super.onOpen(db);
                   // new PopulateDbAsync(INSTANCE).execute();
                }
            };

    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final RoutineDao routineDao;
        private final UserDao userDao;
        private final ExerciseDao exerciseDao;
        private final RoutineExerciseDao routineExerciseDao;

        PopulateDbAsync(CSSRoomDatabase db) {
            routineDao = db.routineDao();
            userDao = db.userDao();
            exerciseDao = db.exerciseDao();
            routineExerciseDao = db.routineExerciseDao();
        }

        @Override
        protected Void doInBackground(final Void... params) {
            //if(routineDao.getAllRoutines(). == 0) {

                Routine routine = new Routine("Beginner Push",
                        "A beginner level workout routine that works the pushing muscles of the upper body. This workout is good for those who are " +
                                "looking to get into a good fitness routine and hope to build a foundation of strength.A beginner level workout routine that works the pushing muscles of the upper body. This workout is good for those who are \" +\n" +
                                "                                \"looking to get into a good fitness routine and hope to build a foundation of strength.A beginner level workout routine that works the pushing muscles of the upper body. This workout is good for those who are \" +\n" +
                                "                                \"looking to get into a good fitness routine and hope to build a foundation of strength.");
                routineDao.insert(routine);
                routine = new Routine("Beginner Pull",
                        " A beginner level workout routine that works the pulling muscles of the upper body. This workout is good for those who are looking to get into a " +
                                "good fitness routine and hope to build a foundation of strength.");
                routineDao.insert(routine);
                routine = new Routine("Beginner Legs",
                        "A beginner level workout routine that works the muscles of the lower body. This workout is good for those who are looking to get into" +
                                " a good fitness routine and hope to build a foundation of strength.");
                routineDao.insert(routine);
           //}

                Exercise exercise = new Exercise("Push Up", "Push Up decription here");
                exerciseDao.insert(exercise);
                exercise = new Exercise("Push up 2", "desc");
                exerciseDao.insert(exercise);
                exercise = new Exercise("Push up 3", "desc");
                exerciseDao.insert(exercise);
                exercise = new Exercise("Push up 4", "desc");
                exerciseDao.insert(exercise);
                exercise = new Exercise("Pull Up", "Pull Up desc");
                exerciseDao.insert(exercise);
                exercise = new Exercise("Push up 5", "desc");
                exerciseDao.insert(exercise);
                exercise = new Exercise("Push up 6", "desc");
                exerciseDao.insert(exercise);
                exercise = new Exercise("Push up 7", "Pull Up desc");
                exerciseDao.insert(exercise);
                exercise = new Exercise("Push Up", "Push Up decription here");
                exerciseDao.insert(exercise);
                exercise = new Exercise("Push up 2", "desc");
                exerciseDao.insert(exercise);
                exercise = new Exercise("Push up 3", "desc");
                exerciseDao.insert(exercise);
                exercise = new Exercise("Push up 4", "desc");
                exerciseDao.insert(exercise);
                exercise = new Exercise("Pull Up", "Pull Up desc");
                exerciseDao.insert(exercise);
                exercise = new Exercise("Push up 5", "desc");
                exerciseDao.insert(exercise);
                exercise = new Exercise("Push up 6", "desc");
                exerciseDao.insert(exercise);
                exercise = new Exercise("Push up 7", "Pull Up desc");
                exerciseDao.insert(exercise);
                exercise = new Exercise("Squat", "squat desc");
                exerciseDao.insert(exercise);

                RoutineExercise routineExercise = new RoutineExercise(1, 1);
                routineExerciseDao.insert(routineExercise);
                routineExercise = new RoutineExercise(1, 2);
                routineExerciseDao.insert(routineExercise);
                routineExercise = new RoutineExercise(1, 3);
                routineExerciseDao.insert(routineExercise);
                routineExercise = new RoutineExercise(1, 4);
                routineExerciseDao.insert(routineExercise);
                routineExercise = new RoutineExercise(1, 5);
                routineExerciseDao.insert(routineExercise);
                routineExercise = new RoutineExercise(1, 6);
                routineExerciseDao.insert(routineExercise);
                routineExercise = new RoutineExercise(1, 7);
                routineExerciseDao.insert(routineExercise);
                routineExercise = new RoutineExercise(1, 8);
                routineExerciseDao.insert(routineExercise);
                routineExercise = new RoutineExercise(1, 9);
                routineExerciseDao.insert(routineExercise);
                routineExercise = new RoutineExercise(1, 10);
                routineExerciseDao.insert(routineExercise);
                routineExercise = new RoutineExercise(1, 11);
                routineExerciseDao.insert(routineExercise);
                routineExercise = new RoutineExercise(1, 12);
                routineExerciseDao.insert(routineExercise);
                routineExercise = new RoutineExercise(1, 13);
                routineExerciseDao.insert(routineExercise);
                routineExercise = new RoutineExercise(1, 14);
                routineExerciseDao.insert(routineExercise);
                routineExercise = new RoutineExercise(1, 15);
                routineExerciseDao.insert(routineExercise);
                routineExercise = new RoutineExercise(1, 16);
                routineExerciseDao.insert(routineExercise);
//                Log.d("File", " being read now");
//                File file = new File("ExercisesPlainText");
//                String absolutePath = file.getAbsolutePath();
//                try(BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath))) {
//                    //BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
//                    String exercise;
//                    while((exercise = bufferedReader.readLine()) != null) {
//                        String[] exerciseInfo = exercise.split(" - ");
//                        Log.d(exerciseInfo[0], (", " + exerciseInfo[1]));
//                        exerciseDao.insert(new Exercise(exerciseInfo[0], exerciseInfo[1]));
//                    }
//
//                } catch(IOException ex) {
//                    Log.d("Read in", "FAILED");
//                    ex.printStackTrace();
//                }

            //}



            //TODO:Delete this code when moving past login functionality
            User u = new User("jtennies10", "jtennies69@gmail.com", "4thgrade", "1111", 160);
            userDao.insert(u);

            return null;
        }
    }
}
