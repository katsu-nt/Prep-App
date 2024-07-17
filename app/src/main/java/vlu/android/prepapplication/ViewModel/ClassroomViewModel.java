package vlu.android.prepapplication.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.function.Consumer;

import vlu.android.prepapplication.Model.Classroom;
import vlu.android.prepapplication.Model.Question;
import vlu.android.prepapplication.R;
import vlu.android.prepapplication.Repository.Repository;

public class ClassroomViewModel extends AndroidViewModel {
    private Repository repository;
    private LiveData<List<Classroom>> classromsLiveData;
    private LiveData<Classroom> classroomLiveData;

    public ClassroomViewModel(@NonNull Application application){
        super(application);
        repository = new Repository(application);
    }
    public LiveData<List<Classroom>> getAllClassromLiveData(){
        classromsLiveData = repository.getAllClassroom();
        return classromsLiveData;
    }

    public LiveData<Classroom> getClassroomLiveData(int id) {
        classroomLiveData= repository.getClassroomByID(id);
        return classroomLiveData;
        }
    public  void insert(Classroom classroom, Runnable onSucces, Consumer<String> onFailure)
    {
        repository.insert(classroom,onSucces,onFailure);
    }

    public void delete(Classroom classroom) {
        repository.delete(classroom);
    }
}
