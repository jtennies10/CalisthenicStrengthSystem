package tenniescorp.fitness.calisthenicstrengthsystem;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

public class ExerciseRepository {

    private ExerciseDao exerciseDao;
    private LiveData<List<Exercise>> allExercises;

    ExerciseRepository(Application application) {
        CSSRoomDatabase db = CSSRoomDatabase.getDatabase(application);
        exerciseDao = db.exerciseDao();
        allExercises = exerciseDao.getAllExercises();
    }

    LiveData<List<Exercise>> getAllExercises() {
        return allExercises;
    }

    public void insert(Exercise exercise) {
        new ExerciseRepository.insertAsyncTask(exerciseDao).execute(exercise);
    }

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
