package vlu.android.prepapplication.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import vlu.android.prepapplication.Model.Teacher;
import vlu.android.prepapplication.Repository.Repository;

public class TeacherViewModel extends AndroidViewModel {
    private Repository repository ;
    private LiveData<Teacher> teacherLiveData;

    public TeacherViewModel(@NonNull Application application) {
        super(application);
        repository = new Repository(application);

    }
//    public LiveData<Teacher> getTeacherLiveData(String name){
//        teacherLiveData = repository.getTeacherByUserName(name);
//        return teacherLiveData;
//    }
    public void insert(Teacher teacher){
        repository.insert(teacher);
    }

}
