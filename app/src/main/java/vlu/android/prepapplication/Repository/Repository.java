package vlu.android.prepapplication.Repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

import vlu.android.prepapplication.Model.Classroom;
import vlu.android.prepapplication.Model.ClassroomWithStudents;
import vlu.android.prepapplication.Model.DAO.ClassroomDAO;
import vlu.android.prepapplication.Model.DAO.ClassroomSubjectCrossRefDAO;
import vlu.android.prepapplication.Model.DAO.ExamDAO;
import vlu.android.prepapplication.Model.DAO.QuestionDAO;
import vlu.android.prepapplication.Model.DAO.StudentDAO;
import vlu.android.prepapplication.Model.DAO.StudentNotificationDAO;
import vlu.android.prepapplication.Model.DAO.SubjectDAO;
import vlu.android.prepapplication.Model.DAO.TeacherDAO;
import vlu.android.prepapplication.Model.DAO.TeacherNotificationDAO;
import vlu.android.prepapplication.Model.DAO.TeacherSubjectCrossRefDAO;
import vlu.android.prepapplication.Model.PrepDatabase;
import vlu.android.prepapplication.Model.Question;
import vlu.android.prepapplication.Model.Student;
import vlu.android.prepapplication.Model.Subject;
import vlu.android.prepapplication.Model.Teacher;

public class Repository {
    private TeacherDAO teacherDAO;
    private StudentDAO studentDAO;
    private ClassroomDAO classroomDAO;
    private SubjectDAO subjectDAO;
    private QuestionDAO questionDAO;
    private ExamDAO examDAO;
    private StudentNotificationDAO studentNotificationDAO;
    private TeacherSubjectCrossRefDAO teacherSubjectCrossRefDAO;
    private TeacherNotificationDAO teacherNotificationDAO;
    private ClassroomSubjectCrossRefDAO classroomSubjectCrossRefDAO;

    public Repository(Application application) {
        PrepDatabase db = PrepDatabase.getDatabase(application);
        teacherDAO = db.teacherDAO();
        studentDAO = db.studentDAO();
        classroomDAO = db.classroomDAO();
        subjectDAO = db.subjectDAO();
        questionDAO = db.questionDAO();
        examDAO = db.examDAO();
        studentNotificationDAO = db.studentNotificationDAO();
        teacherSubjectCrossRefDAO = db.teacherSubjectCrossRefDAO();
        teacherNotificationDAO = db.teacherNotificationDAO();
        classroomSubjectCrossRefDAO = db.classroomSubjectCrossRefDAO();
    }

    public LiveData<Teacher> getTeacherByUserName(String username) {
        return teacherDAO.getTeacherByUserName(username);
    }

    public LiveData<Student> getStudentByUserName(String username) {
        return studentDAO.getStudentByUserName(username);
    }

    public LiveData<List<Question>> getAllQuestion() {
        return questionDAO.getAllQuestion();
    }

    public LiveData<Question> getQuestionByID(int id) {
        return questionDAO.getQuestionByID(id);
    }
    public void delete(Question question) {
        PrepDatabase.databaseWriteExecutor.execute(() -> questionDAO.deleteQuestion(question));
}
    public LiveData<List<Classroom>>getAllClassroom(){
        return classroomDAO.getAllClassroom();
    }
    public LiveData<Classroom> getClassroomByID(int id){
        return classroomDAO.getQuestionByID (id);
    }
    public void insert(Teacher teacher){
        PrepDatabase.databaseWriteExecutor.execute(()->{
            teacherDAO.insert(teacher);
        });
    }
    public LiveData<List<Subject>> getAllSubject(){
        return subjectDAO.getAllSubject();
    }
    public LiveData<Subject> getSubjectByID(int id) {
        return subjectDAO.getSubjectByID(id);
    }
    public LiveData<List<Subject>> getSubjectByName(String name) {return subjectDAO.getSubjectByName(name);}
    public void insert(Question question){
        PrepDatabase.databaseWriteExecutor.execute(()-> questionDAO.insert(question));
    }
    public void insert(Classroom classroom) {
        PrepDatabase.databaseWriteExecutor.execute(() -> {
            classroomDAO.insert(classroom);
        });
    }

    public void insertSubject(Subject subject) {
        PrepDatabase.databaseWriteExecutor.execute(()-> subjectDAO.insert(subject));
    }

    public void delete(Subject subject){
        PrepDatabase.databaseWriteExecutor.execute(()-> subjectDAO.deleteSubjet(subject));
    }

    public void delete(Classroom classroom) {
        PrepDatabase.databaseWriteExecutor.execute(()-> classroomDAO.deleteClassroom(classroom));
    }
    public void insertStudent(Student  student){
        PrepDatabase.databaseWriteExecutor.execute(()-> studentDAO.insert(student));
    }
    public LiveData<Student> getStudentById(int id){
        return studentDAO.getStudentById(id);
    }
    public LiveData<List<ClassroomWithStudents>> getClassroomWithStudents(){
        return classroomDAO.getClassroomWithStudents();
    }

}