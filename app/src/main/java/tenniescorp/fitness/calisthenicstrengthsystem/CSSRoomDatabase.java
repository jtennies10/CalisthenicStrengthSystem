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
import java.time.Instant;
import java.util.Calendar;
import java.util.Date;


/*
Defines the database used in the program. The database is created using the Room overlay for SQLite.

The code in the class relies heavily in the open source tutorial
found here https://codelabs.developers.google.com/codelabs/android-room-with-a-view/#6
*/
@Database(entities =
        {User.class, Routine.class, RoutineExercise.class,
                UserRoutine.class, UserWeight.class, Exercise.class},
        version = 1)
public abstract class CSSRoomDatabase extends RoomDatabase {

    //declare the abstract methods to get the database DAO's
    public abstract UserDao userDao();
    public abstract RoutineDao routineDao();
    public abstract ExerciseDao exerciseDao();
    public abstract RoutineExerciseDao routineExerciseDao();
    public abstract UserWeightDao userWeightDao();

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

    /*
    Defines the callback methods to populate the database.
     */
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

    /*
    Defines an AsyncTask subclass for populating the database.
     */
    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final RoutineDao routineDao;
        private final UserDao userDao;
        private final ExerciseDao exerciseDao;
        private final RoutineExerciseDao routineExerciseDao;
        private final UserWeightDao userWeightDao;

        /*
        Class constructor
         */
        PopulateDbAsync(CSSRoomDatabase db) {
            routineDao = db.routineDao();
            userDao = db.userDao();
            exerciseDao = db.exerciseDao();
            routineExerciseDao = db.routineExerciseDao();
            userWeightDao = db.userWeightDao();
        }

        /*
        Method called on database creation to populate the database tables.


        NOTE:The data used in this method is used for testing purposes, to see how data the application handles
        given data sets.
         */
        @Override
        protected Void doInBackground(final Void... params) {
            long userId;

            UserWeight userWeight;

            Routine routine;
            long routineId;

            RoutineExercise routineExercise;
            Exercise exercise;
            long exerciseId;

            //add a user and some user weights
            User u = new User("test", "testuser@wgu.edu", "test", "Jan 01, 2000", 70);
            userId = userDao.insert(u);


            userWeight = new UserWeight(userId, 170, (Calendar.getInstance().getTimeInMillis()-3000000000L));
            userWeightDao.insert(userWeight);
            userWeight = new UserWeight(userId, 160, (Calendar.getInstance().getTimeInMillis()-2500000000L));
            userWeightDao.insert(userWeight);
            userWeight = new UserWeight(userId, 155, (Calendar.getInstance().getTimeInMillis()-1900000000L));
            userWeightDao.insert(userWeight);
            userWeight = new UserWeight(userId, 153, (Calendar.getInstance().getTimeInMillis()-1400000000L));
            userWeightDao.insert(userWeight);



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



            //add some routines and exercises, as well as routineExercises that join them
            routine = new Routine("Beginner Push",
                    "A beginner level workout routine that works the pushing muscles of the upper body. This workout is good for those who are " +
                            "looking to get into a good fitness routine and hope to build a foundation of strength.A beginner level workout routine that works the pushing muscles of the upper body. This workout is good for those who are" +
                            "looking to get into a good fitness routine and hope to build a foundation of strength.A beginner level workout routine that works the pushing muscles of the upper body. This workout is good for those who are" +
                            "looking to get into a good fitness routine and hope to build a foundation of strength.");
            routineId = routineDao.insert(routine);

            exercise = new Exercise("Push Up", "Traditional pull up movement, get into a high plank position, lower to the ground, and push back up to the start.");
            exerciseId = exerciseDao.insert(exercise);
            routineExercise = new RoutineExercise(routineId, exerciseId);
            routineExerciseDao.insert(routineExercise);

            exercise = new Exercise("Dips", "Place both hands on an elevated surface and hold yourself up. Lower down until you reach your full range of motion or slightly less if your shoulders are injury-prone. " +
                    "Push yourself back to the starting position.");
            exerciseId = exerciseDao.insert(exercise);
            routineExercise = new RoutineExercise(routineId, exerciseId);
            routineExerciseDao.insert(routineExercise);

            exercise = new Exercise("Pike Push Up", "Align your body in a V-like shape with your hands and feet on the ground. Lower your head towards your hands and push back up.");
            exerciseId = exerciseDao.insert(exercise);
            routineExercise = new RoutineExercise(routineId, exerciseId);
            routineExerciseDao.insert(routineExercise);

            exercise = new Exercise("Plank", "Assume the push up position and hold or lower you elbows to the ground and hold.");
            exerciseId = exerciseDao.insert(exercise);
            routineExercise = new RoutineExercise(routineId, exerciseId);
            routineExerciseDao.insert(routineExercise);

            exercise = new Exercise("V-Ups", "Lie on your back on the floor with your arms overhead. Contract and bring you upper and lower body off the floor, trying to meet in the middle.");
            exerciseId = exerciseDao.insert(exercise);
            routineExercise = new RoutineExercise(routineId, exerciseId);
            routineExerciseDao.insert(routineExercise);



            //create another routine
            routine = new Routine("Beginner Pull",
                    " A beginner level workout routine that works the pulling muscles of the upper body. This workout is good for those who are looking to get into a " +
                            "good fitness routine and hope to build a foundation of strength.");
            routineId = routineDao.insert(routine);

            exercise = new Exercise("Pull Up", "The traditional pull up! Grab an overhanging bar or structure palms with your palms facing away from you and pull yourself up to the bar using your " +
                    "back muscles. Control the movement on the way down.");
            exerciseId = exerciseDao.insert(exercise);
            routineExercise = new RoutineExercise(routineId, exerciseId);
            routineExerciseDao.insert(routineExercise);

            exercise = new Exercise("Inverted Row", "Lie on your back with an overhanging bar or structure above your head. Grab the structure " +
                    "and pull yourself up to the structure until you reach your full range of motion. Lower down while controlling your body.");
            exerciseId = exerciseDao.insert(exercise);
            routineExercise = new RoutineExercise(routineId, exerciseId);
            routineExerciseDao.insert(routineExercise);

            exercise = new Exercise("Eccentric Pull Up", "The same movement as a traditional pull up, but focus on exploding up to the bar." +
                    " Over time, you should be able to pull your chest to level with the bar.");
            exerciseId = exerciseDao.insert(exercise);
            routineExercise = new RoutineExercise(routineId, exerciseId);
            routineExerciseDao.insert(routineExercise);

            exercise = new Exercise("Bicycle Crunches", "Lie on your back and raise your knees up 90 degrees. Crunch toward one knee while extending the opposite leg. Repeat the other direction.");
            exerciseId = exerciseDao.insert(exercise);
            routineExercise = new RoutineExercise(routineId, exerciseId);
            routineExerciseDao.insert(routineExercise);





            //create another routine
            routine = new Routine("Beginner Legs",
                    "A beginner level workout routine that works the muscles of the lower body. This workout is good for those who are looking to get into" +
                            " a good fitness routine and hope to build a foundation of strength.");
            routineId = routineDao.insert(routine);

            exercise = new Exercise("Squat", "The traditional squat! Stand with your feet shoulder width apart, and bend your legs to lower yourself " +
                    "down. Extend your legs and drive your hips forward to return to the starting position.");
            exerciseId = exerciseDao.insert(exercise);
            routineExercise = new RoutineExercise(routineId, exerciseId);
            routineExerciseDao.insert(routineExercise);

            exercise = new Exercise("Calf Raises", "Stand on a slightly elevated surface with just the balls of your feet. Raise up using only your calf muscles. Return to the starting position.");
            exerciseId = exerciseDao.insert(exercise);
            routineExercise = new RoutineExercise(routineId, exerciseId);
            routineExerciseDao.insert(routineExercise);

            exercise = new Exercise("Sprints", "Traditional sprints. Run as fast you can for your time or distance.");
            exerciseId = exerciseDao.insert(exercise);
            routineExercise = new RoutineExercise(routineId, exerciseId);
            routineExerciseDao.insert(routineExercise);

            exercise = new Exercise("Plyo Squats", "Stand feet less than should width apart. Hop out to your feet just beyond shoulder width apart and perform a squat. Hop back to the starting position.");
            exerciseId = exerciseDao.insert(exercise);
            routineExercise = new RoutineExercise(routineId, exerciseId);
            routineExerciseDao.insert(routineExercise);

            exercise = new Exercise("Side Plank", "Lay on one side on the floor. Raise up onto only your feet and your elbow and hold.");
            exerciseId = exerciseDao.insert(exercise);
            routineExercise = new RoutineExercise(routineId, exerciseId);
            routineExerciseDao.insert(routineExercise);

            exercise = new Exercise("Russian Twist", "Sit on the floor and lean back to engage your core. Place your arms in front of you and rotate side to side.");
            exerciseId = exerciseDao.insert(exercise);
            routineExercise = new RoutineExercise(routineId, exerciseId);
            routineExerciseDao.insert(routineExercise);

            exercise = new Exercise("Back Bridge", "Lay on your back. Place your hands face down on the floor next to your head and press up, forming a bridge with your body.");
            exerciseId = exerciseDao.insert(exercise);
            routineExercise = new RoutineExercise(routineId, exerciseId);
            routineExerciseDao.insert(routineExercise);


            return null;
        }
    }
}
