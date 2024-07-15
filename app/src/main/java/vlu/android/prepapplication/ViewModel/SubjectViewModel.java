package vlu.android.prepapplication.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import vlu.android.prepapplication.Model.Subject;
import vlu.android.prepapplication.Repository.Repository;

public class SubjectViewModel extends AndroidViewModel {
    private Repository repository;
    private LiveData<List<Subject>> subjectsLiveData;
    private LiveData<Subject> subjectLiveData;
    public SubjectViewModel(@NonNull Application application) {
        super(application);
        repository = new Repository(application);
    }

    public LiveData<List<Subject>> getAllSubjectLiveData() {
        subjectsLiveData = repository.getAllSubject();
        return subjectsLiveData;
    }

    public LiveData<Subject> getSubjectLiveData(int id) {
        subjectLiveData = repository.getSubjectByID(id);
        return subjectLiveData;
    }

    public LiveData<List<Subject>> getSubjectsLiveData(String name) {
        subjectsLiveData = repository.getSubjectByName(name);
        return subjectsLiveData;
    }

    public void insert(Subject subject) {
        repository.insertSubject(subject);
    }

    public void delete(Subject subject){
        repository.delete(subject);
    }
}
