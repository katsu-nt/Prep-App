package vlu.android.prepapplication.ViewModel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import vlu.android.prepapplication.Model.Classroom;
import vlu.android.prepapplication.Model.ClassroomStudentCrossRef;
import vlu.android.prepapplication.Model.Student;
import vlu.android.prepapplication.Model.Subject;
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
    public LiveData<Classroom> getClassroomById(int id){
        return repository.getClassroomById(id);
    }
    public void insertStudentToClassroom(ClassroomStudentCrossRef classroomStudentCrossRef){
        repository.insertStudentToClassroom(classroomStudentCrossRef);
    }
    public LiveData<Integer> checkJoined(int studentId,int classId){
        return repository.checkJoined(studentId,classId);
    }
    public LiveData<List<Integer>> getListClassroomId(int studentId){
        return repository.getListClassroomId(studentId);
    }
    public LiveData<List<Classroom>> getClassroomsByIds(List<Integer> ids){
        return repository.getClassroomsByIds(ids);
    }
    public LiveData<List<Subject>> getSubjectsByClassroomId(List<Integer> ids){
        return repository.getSubjectsByClassroomId(ids);
    }
    public LiveData<List<Integer>> getSubjectIds(int classroomId){
        return repository.getSubjectIds(classroomId);
    }
}
