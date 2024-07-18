package vlu.android.prepapplication.Repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.function.Consumer;

import vlu.android.prepapplication.Model.Classroom;
import vlu.android.prepapplication.Model.ClassroomStudentCrossRef;
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
import vlu.android.prepapplication.Model.Exam;
import vlu.android.prepapplication.Model.PrepDatabase;
import vlu.android.prepapplication.Model.Question;
import vlu.android.prepapplication.Model.Student;
import vlu.android.prepapplication.Model.StudentExamQuestionCrossRef;
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

    public void connectDB() {
        PrepDatabase.databaseWriteExecutor.execute(() -> studentDAO.connectDB());
    }

    public LiveData<Student> getStudentByUsername(String username) {
        return studentDAO.getStudentByUserName(username);
    }

    public LiveData<Teacher> getTeacherByUsername(String username) {
        return teacherDAO.getTeacherByUserName(username);
    }

    public LiveData<Teacher> getTeacherById(int id) {
        return teacherDAO.getTeacherById(id);
    }

    public LiveData<List<Question>> getAllQuestion(int subjectId) {
        return questionDAO.getAllQuestion(subjectId);
    }

    public LiveData<Question> getQuestionByID(int id, int subjectId) {
        return questionDAO.getQuestionByID(id, subjectId);
    }

    public LiveData<Question> getQuestionByQuestionID(int id) {
        return questionDAO.getQuestionByQuestionID(id);
    }

    public void delete(Question question) {
        PrepDatabase.databaseWriteExecutor.execute(() -> questionDAO.deleteQuestion(question));
    }

    public LiveData<List<Classroom>> getAllClassroom() {
        return classroomDAO.getAllClassroom();
    }

    public LiveData<List<Student>> getAllStudent() {
        return studentDAO.getAllStudent();
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

    private String validateQuestion(Question question) {
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
            return "there is a missing field in the question";
        }

        HashSet<String> answerSet = new HashSet<>(Arrays.asList(answerA, answerB, answerC, answerD));

        if (answerSet.size() < 4) {
            return "there are repeated answer";
        }

        if (!answerSet.contains(correctAnswer)) {
            return "correct answer is not present in the answer set";
        }

        return "";
    }

    public void insert(Question question, Runnable onSuccess, Consumer<String> onFailure) {
        PrepDatabase.databaseWriteExecutor.execute(() -> {
            String problem = validateQuestion(question);

            if (!problem.isEmpty()) {
                if (onFailure != null) {
                    onFailure.accept(problem);
                }
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

    public void update(Question question, Runnable onSuccess, Consumer<String> onFailure) {
        PrepDatabase.databaseWriteExecutor.execute(() -> {
            String problem = validateQuestion(question);

            if (!problem.isEmpty()) {
                if (onFailure != null) {
                    onFailure.accept(problem);
                }
                return;
            }

            questionDAO.update(question);
            if (onSuccess != null) {
                onSuccess.run();
            }
        });
    }


    public void insertSubject(Subject subject) {
        PrepDatabase.databaseWriteExecutor.execute(() -> subjectDAO.insert(subject));
    }

    public void delete(Subject subject) {
        PrepDatabase.databaseWriteExecutor.execute(() -> subjectDAO.deleteSubjet(subject));
    }

    public void delete(Classroom classroom) {
        PrepDatabase.databaseWriteExecutor.execute(() -> classroomDAO.deleteClassroom(classroom));
    }

    //Hoang repository
    public void insertStudent(Student student) {
        PrepDatabase.databaseWriteExecutor.execute(() -> studentDAO.insert(student));
    }

    public LiveData<Student> getStudentById(int id) {
        return studentDAO.getStudentById(id);
    }

    public LiveData<Classroom> getClassroomById(int id) {
        return classroomDAO.getClassroomById(id);
    }

    public void insertStudentToClassroom(ClassroomStudentCrossRef classroomStudentCrossRef) {
        PrepDatabase.databaseWriteExecutor.execute(() -> classroomStudentCrossRefDAO.insert(classroomStudentCrossRef));
    }

    public LiveData<Integer> checkJoined(int studentId, int classId) {
        return classroomStudentCrossRefDAO.checkJoined(studentId, classId);
    }

    public LiveData<List<Integer>> getListClassroomId(int studentId) {
        return classroomStudentCrossRefDAO.getListClassroomId(studentId);
    }

    public LiveData<List<Classroom>> getClassroomsByIds(List<Integer> ids) {
        return classroomDAO.getClassroomsByIds(ids);
    }

    public LiveData<List<Integer>> getSubjectIds(int classroomId) {
        return classroomSubjectCrossRefDAO.getSubjectIds(classroomId);
    }

    public LiveData<List<Subject>> getSubjectsByClassroomId(List<Integer> ids) {
        return subjectDAO.getSubjectsByClassroomId(ids);
    }

    public LiveData<Integer> countQuestion(int subjectId) {
        return questionDAO.countQuestion(subjectId);
    }

    public LiveData<List<Question>> getQuestionForExam(int subjectId) {
        return questionDAO.getQuestionForExam(subjectId);
    }

    public void submitExam(StudentExamQuestionCrossRef studentExamQuestionCrossRef) {
        PrepDatabase.databaseWriteExecutor.execute(() -> studentExamQuestionCrossRefDAO.insert(studentExamQuestionCrossRef));
    }

    public void insertExam(Exam exam) {
        PrepDatabase.databaseWriteExecutor.execute(() -> examDAO.insert(exam));
    }

    public LiveData<Integer> getNewestExamId(int subjectId) {
        return examDAO.getNewestExamId(subjectId);
    }

    public LiveData<List<Exam>> getExams(int subjectId) {
        return examDAO.getExams(subjectId);
    }

    public LiveData<List<StudentExamQuestionCrossRef>> getAllExam(int examId) {
        return studentExamQuestionCrossRefDAO.getAllExam(examId);
    }

}
