package tenniescorp.fitness.calisthenicstrengthsystem;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

public class RoutineRespository {

    private RoutineDao routineDao;
    private LiveData<List<Routine>> allRoutines;

    RoutineRespository(Application application) {
        CSSRoomDatabase db = CSSRoomDatabase.getDatabase(application);
        routineDao = db.routineDao();
        allRoutines = routineDao.getAllRoutines();
    }

    LiveData<List<Routine>> getAllRoutines() {
        return allRoutines;
    }

    public void insert(Routine routine) {
        new insertAsyncTask(routineDao).execute(routine);
    }

    private static class insertAsyncTask extends AsyncTask<Routine, Void, Void> {
        private RoutineDao asyncTaskDao;

        insertAsyncTask(RoutineDao dao) {
            asyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Routine... params) {
            asyncTaskDao.insert(params[0]);
            return null;
        }
    }
}
