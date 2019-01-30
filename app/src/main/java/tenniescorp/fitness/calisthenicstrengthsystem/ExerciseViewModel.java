package tenniescorp.fitness.calisthenicstrengthsystem;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import java.util.List;

public class ExerciseViewModel extends AndroidViewModel {

    private ExerciseRepository repository;
    private LiveData<List<Exercise>> allExercises;

    public ExerciseViewModel(Application application) {
        super(application);
        repository = new ExerciseRepository(application);
        allExercises = repository.getAllExercises();
    }

    LiveData<List<Exercise>> getAllExercises() {
        return allExercises;
    }

    public void insert(Exercise exercise) {
        repository.insert(exercise);
    }
}
