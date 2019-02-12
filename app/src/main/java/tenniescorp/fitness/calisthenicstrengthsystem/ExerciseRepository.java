package tenniescorp.fitness.calisthenicstrengthsystem;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

/*
Defines a repository that holds the exercises in the database in faster accessed memory.

Code adapted from
https://codelabs.developers.google.com/codelabs/android-room-with-a-view/#10
 */
public class ExerciseRepository {

    private ExerciseDao exerciseDao;
    private LiveData<List<Exercise>> allExercises;

    /*
    Constructors that gets the exercises currently in the database
     */
    ExerciseRepository(Application application) {
        CSSRoomDatabase db = CSSRoomDatabase.getDatabase(application);
        exerciseDao = db.exerciseDao();
        allExercises = exerciseDao.getAllExercises();
    }

    LiveData<List<Exercise>> getAllExercises() {
        return allExercises;
    }

    /*
    Used as an overlay for inserting an exercise into the database
     */
    public void insert(Exercise exercise) {
        new ExerciseRepository.insertAsyncTask(exerciseDao).execute(exercise);
    }

    /*
    Defines an inner class for adding exercises to the database asynchronously.
     */
    private static class insertAsyncTask extends AsyncTask<Exercise, Void, Void> {
        private ExerciseDao asyncTaskDao;

        insertAsyncTask(ExerciseDao dao) {
            asyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Exercise... params) {
            asyncTaskDao.insert(params[0]);
            return null;
        }
    }
}
