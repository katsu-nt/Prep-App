package vlu.android.prepapplication.ViewModel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import vlu.android.prepapplication.Model.Student;
import vlu.android.prepapplication.Repository.Repository;

public class StudentViewModel extends AndroidViewModel {
    private Repository repository;
    private LiveData<Student> studentLiveData;
    public StudentViewModel(Application application){
        super(application);
        repository = new Repository(application);

    }


}
