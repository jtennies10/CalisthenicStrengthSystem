package tenniescorp.fitness.calisthenicstrengthsystem;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import java.util.List;

/*
Defines a view model for routines using the RoutineRepository and the routines in the database.
 */
public class RoutineViewModel extends AndroidViewModel {

    private RoutineRepository repository;
    private LiveData<List<Routine>> allRoutines;

    public RoutineViewModel(Application application) {
        super(application);
        repository = new RoutineRepository(application);
        allRoutines = repository.getAllRoutines();
    }

    LiveData<List<Routine>> getAllRoutines() {
        return allRoutines;
    }

    /*
    Inserts the passed routine into the repository
     */
    public void insert(Routine routine) {
        repository.insert(routine);
    }
}
