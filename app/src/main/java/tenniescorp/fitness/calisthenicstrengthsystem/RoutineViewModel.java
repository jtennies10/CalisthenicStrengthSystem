package tenniescorp.fitness.calisthenicstrengthsystem;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import java.util.List;

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

    public void insert(Routine routine) {
        repository.insert(routine);
    }
}
