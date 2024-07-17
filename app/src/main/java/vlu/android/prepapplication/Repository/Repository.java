package vlu.android.prepapplication.Repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.function.Consumer;

import vlu.android.prepapplication.Model.Classroom;
import vlu.android.prepapplication.Model.DAO.ClassroomDAO;
import vlu.android.prepapplication.Model.DAO.ClassroomStudentCrossRefDAO;
import vlu.android.prepapplication.Model.DAO.ClassroomSubjectCrossRefDAO;
import vlu.android.prepapplication.Model.DAO.ExamDAO;
import vlu.android.prepapplication.Model.DAO.QuestionDAO;
import vlu.android.prepapplication.Model.DAO.StudentDAO;
import vlu.android.prepapplication.Model.DAO.StudentExamQuestionCrossRefDAO;
import vlu.android.prepapplication.Model.DAO.StudentNotificationDAO;
import vlu.android.prepapplication.Model.DAO.SubjectDAO;
import vlu.android.prepapplication.Model.DAO.TeacherDAO;
import vlu.android.prepapplication.Model.DAO.TeacherNotiAndStudentNotiDAO;
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
    private ClassroomStudentCrossRefDAO classroomStudentCrossRefDAO;
    private StudentExamQuestionCrossRefDAO studentExamQuestionCrossRefDAO;
    private TeacherNotiAndStudentNotiDAO teacherNotiAndStudentNotiDAO;

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
        classroomStudentCrossRefDAO = db.classroomStudentCrossRefDAO();
        studentExamQuestionCrossRefDAO = db.studentExamQuestionCrossRefDAO();
        teacherNotiAndStudentNotiDAO = db.teacherNotiAndStudentNotiDAO();
    }

    public LiveData<Teacher> getTeacherByUserName(String username) {
        return teacherDAO.getTeacherByUserName(username);
    }
    public void connectDB(){
        PrepDatabase.databaseWriteExecutor.execute(() -> studentDAO.connectDB());
    }
    public LiveData<Student> getStudentByUsername(String username){
        return studentDAO.getStudentByUserName(username);
    }
    public LiveData<Teacher> getTeacherByUsername(String username){
        return teacherDAO.getTeacherByUserName(username);
    }
    public LiveData<Teacher> getTeacherById(int id){
        return teacherDAO.getTeacherById(id);
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

    public LiveData<List<Classroom>> getAllClassroom() {
        return classroomDAO.getAllClassroom();
    }

    public LiveData<Classroom> getClassroomByID(int id) {
        return classroomDAO.getQuestionByID(id);
    }

    public void insert(Teacher teacher) {
        PrepDatabase.databaseWriteExecutor.execute(() -> {
            teacherDAO.insert(teacher);
        });
    }

    public LiveData<List<Subject>> getAllSubject() {
        return subjectDAO.getAllSubject();
    }

    public LiveData<Subject> getSubjectByID(int id) {
        return subjectDAO.getSubjectByID(id);
    }

    public LiveData<List<Subject>> getSubjectByName(String name) {
        return subjectDAO.getSubjectByName(name);
    }

    public void insert(Question question, Runnable onSuccess, Consumer<String> onFailure) {
        PrepDatabase.databaseWriteExecutor.execute(() -> {
            String content = question.getContent().trim();
            String answerA = question.getAnswerA().trim();
            String answerB = question.getAnswerB().trim();
            String answerC = question.getAnswerC().trim();
            String answerD = question.getAnswerD().trim();
            String correctAnswer = question.getCorrectAnswer().trim();

            if (content.isEmpty() ||
                    answerA.isEmpty() ||
                    answerB.isEmpty() ||
                    answerC.isEmpty() ||
                    answerD.isEmpty() ||
                    correctAnswer.isEmpty()) {
                if (onFailure != null) {
                    onFailure.accept("there is a missing field in the question");
                }
                return;
            }

            HashSet<String> answerSet = new HashSet<>(Arrays.asList(answerA, answerB, answerC, answerD));

            if (answerSet.size() < 4) {
                onFailure.accept("there are repeated answer");
                return;
            }

            if (!answerSet.contains(correctAnswer)) {
                onFailure.accept("correct answer is not present in the answer set");
                return;
            }

            questionDAO.insert(question);
            if (onSuccess != null) {
                onSuccess.run();
            }
        });
    }

    public void insert(Classroom classroom, Runnable onSuccess, Consumer<String> onFailure) {
        PrepDatabase.databaseWriteExecutor.execute(() -> {
            String nameClassroom = classroom.getName();
            String description = classroom.getDescription();
            int teacherId = classroom.getTeacherId();

            if (nameClassroom.isEmpty() || description.isEmpty()) {
                if (onFailure != null) {
                    onFailure.accept("There is a missing field in the classroom");
                }
                return;
            }

            // Kiểm tra khóa ngoại
            Teacher teacher = classroomDAO.getTeacherById(teacherId);
            if (teacher == null) {
                if (onFailure != null) {
                    onFailure.accept("Invalid teacherId: " + teacherId);
                }
                return;
            }

            try {
                classroomDAO.insert(classroom);
                if (onSuccess != null) {
                    onSuccess.run();
                }
            } catch (Exception e) {
                if (onFailure != null) {
                    onFailure.accept("Failed to insert classroom: " + e.getMessage());
                }
                e.printStackTrace();
            }
        });
    }


    public void insertSubject(Subject subject) {
        PrepDatabase.databaseWriteExecutor.execute(() -> subjectDAO.insert(subject));
    }

    public void delete(Subject subject){
        PrepDatabase.databaseWriteExecutor.execute(()-> subjectDAO.deleteSubjet(subject));
    }

    public void delete(Classroom classroom) {
        PrepDatabase.databaseWriteExecutor.execute(()-> classroomDAO.deleteClassroom(classroom));
    }

    //Hoang repository
    public void insertStudent(Student  student){
        PrepDatabase.databaseWriteExecutor.execute(()-> studentDAO.insert(student));
    }
    public LiveData<Student> getStudentById(int id){
        return studentDAO.getStudentById(id);
    }


}