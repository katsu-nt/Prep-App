package vlu.android.prepapplication.ViewModel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import vlu.android.prepapplication.Model.Classroom;
import vlu.android.prepapplication.Model.Student;
import vlu.android.prepapplication.Repository.Repository;

public class StudentViewModel extends AndroidViewModel {
    private Repository repository;
    private LiveData<Student> student;
    public StudentViewModel(Application application){
        super(application);
        repository = new Repository(application);
    }
    public LiveData<Student> getStudentById(int id){
        student = repository.getStudentById(id);
        return student;
    }
    public void insertStudent(Student student){
        repository.insertStudent(student);
    }
//    public void insertClassroom(Classroom classroom){
//        repository.insert(classroom);
//    }

}
