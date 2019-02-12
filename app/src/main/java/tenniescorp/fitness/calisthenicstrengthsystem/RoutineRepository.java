package tenniescorp.fitness.calisthenicstrengthsystem;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

/*
Defines a repository that holds the routines in the database in faster accessed memory.

Code adapted from
https://codelabs.developers.google.com/codelabs/android-room-with-a-view/#10
 */
public class RoutineRepository {

    private RoutineDao routineDao;
    private LiveData<List<Routine>> allRoutines;

    /*
    Constructors that gets the routines currently in the database
     */
    RoutineRepository(Application application) {
        CSSRoomDatabase db = CSSRoomDatabase.getDatabase(application);
        routineDao = db.routineDao();
        allRoutines = routineDao.getAllRoutines();
    }

    LiveData<List<Routine>> getAllRoutines() {
        return allRoutines;
    }

    /*
    Used as an overlay for inserting a routine into the database
     */
    public void insert(Routine routine) {
         new insertAsyncTask(routineDao).execute(routine);
    }

    /*
    Defines an inner class for adding routines to the database asynchronously.
     */
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
