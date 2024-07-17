package vlu.android.prepapplication.Model;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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

@Database(entities = {Teacher.class,Student.class,Classroom.class, Subject.class,
        Question.class,StudentNotification.class,TeacherNotification.class, Exam.class,
        ClassroomSubjectCrossRef.class,TeacherSubjectCrossRef.class,ClassroomStudentCrossRef.class,
        StudentExamQuestionCrossRef.class,TeacherNotiAndStudentNoti.class},version = 1,exportSchema = false)
public abstract class PrepDatabase extends RoomDatabase {
    public abstract TeacherDAO teacherDAO();
    public abstract StudentDAO studentDAO();
    public abstract ClassroomDAO classroomDAO();
    public abstract SubjectDAO subjectDAO();
    public abstract QuestionDAO questionDAO();
    public abstract ExamDAO examDAO();
    public abstract StudentNotificationDAO studentNotificationDAO();
    public abstract TeacherSubjectCrossRefDAO teacherSubjectCrossRefDAO();
    public abstract TeacherNotificationDAO teacherNotificationDAO();
    public abstract ClassroomSubjectCrossRefDAO classroomSubjectCrossRefDAO();
    public abstract ClassroomStudentCrossRefDAO classroomStudentCrossRefDAO();
    public abstract StudentExamQuestionCrossRefDAO studentExamQuestionCrossRefDAO();
    public abstract TeacherNotiAndStudentNotiDAO teacherNotiAndStudentNotiDAO();


    private static volatile PrepDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static PrepDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (PrepDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    PrepDatabase.class, "prep_database")
                            .addCallback(sRoomDatabaseCallback).build();
                }
            }
        }
        return INSTANCE;
    }
    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);

            // If you want to keep data through app restarts,
            // comment out the following block
            databaseWriteExecutor.execute(() -> {
                // Populate the database in the background.
                // If you want to start with more words, just add them.
                TeacherDAO dao = INSTANCE.teacherDAO();
                Teacher teacher = new Teacher("teacher","teacher01","123456");
                dao.insert(teacher);
                teacher = new Teacher("teacher01","teacher02","123456");
                dao.insert(teacher);
                teacher = new Teacher("teacher02","teacher03","123456");
                dao.insert(teacher);
                StudentDAO daost = INSTANCE.studentDAO();
                Student st = new Student("student","student01","123456");
                daost.insert(st);
                st = new Student("student01","student02","123456");
                daost.insert(st);
                st = new Student("student01","student03","123456");
                daost.insert(st);
                ClassroomDAO daocl = INSTANCE.classroomDAO();
                Classroom cl = new Classroom("class-A","description-01",1);
                daocl.insert(cl);
                cl = new Classroom("class-B","description-02",1);
                daocl.insert(cl);
                cl = new Classroom("class-C","description-03",1);
                daocl.insert(cl);
                SubjectDAO daosb = INSTANCE.subjectDAO();
                Subject sj = new Subject("Subject A","description-01");
                daosb.insert(sj);
                sj = new Subject("Subject B","description-02");
                daosb.insert(sj);
                sj = new Subject("Subject C","description-03");
                daosb.insert(sj);
                QuestionDAO daoqs = INSTANCE.questionDAO();
                Question qs = new Question("Content 01","A","B","C","D","A",1);
                daoqs.insert(qs);
                qs = new Question("Content 02","A","B","C","D","B",1);
                daoqs.insert(qs);
                qs = new Question("Content 03","A","B","C","D","C",1);
                daoqs.insert(qs);
                qs = new Question("Content 04","A","B","C","D","D",1);
                daoqs.insert(qs);
                qs = new Question("Content 05","A","B","C","D","A",1);
                daoqs.insert(qs);
                qs = new Question("Content 06","A","B","C","D","B",1);
                daoqs.insert(qs);
                qs = new Question("Content 07","A","B","C","D","C",1);
                daoqs.insert(qs);
                qs = new Question("Content 08","A","B","C","D","D",1);
                daoqs.insert(qs);
                qs = new Question("Content 09","A","B","C","D","A",1);
                daoqs.insert(qs);
                qs = new Question("Content 10","A","B","C","D","B",1);
                daoqs.insert(qs);
                qs = new Question("Content 11","A","B","C","D","C",1);
                daoqs.insert(qs);
                qs = new Question("Content 12","A","B","C","D","D",1);
                daoqs.insert(qs);
                ExamDAO daoex = INSTANCE.examDAO();
                Exam ex = new Exam(1);
                daoex.insert(ex);
                TeacherNotificationDAO daont = INSTANCE.teacherNotificationDAO();
                TeacherNotification tc = new TeacherNotification("Student01 want to join",-1,1);
                daont.insert(tc);
                StudentNotificationDAO dao1 = INSTANCE.studentNotificationDAO();
                StudentNotification tc1 = new StudentNotification("Your request in waiting",1);
                dao1.insert(tc1);
                TeacherNotiAndStudentNotiDAO daotcst = INSTANCE.teacherNotiAndStudentNotiDAO();
                TeacherNotiAndStudentNoti tcst = new TeacherNotiAndStudentNoti(1,1);
                daotcst.insert(tcst);
                TeacherSubjectCrossRefDAO daotcsb = INSTANCE.teacherSubjectCrossRefDAO();
                TeacherSubjectCrossRef tcsb = new TeacherSubjectCrossRef(1,1);
                daotcsb.insert(tcsb);
                tcsb = new TeacherSubjectCrossRef(1,2);
                daotcsb.insert(tcsb);
                tcsb = new TeacherSubjectCrossRef(1,3);
                daotcsb.insert(tcsb);
                ClassroomSubjectCrossRefDAO daoclsb = INSTANCE.classroomSubjectCrossRefDAO();
                ClassroomSubjectCrossRef clsb = new ClassroomSubjectCrossRef(1,1);
                daoclsb.insert(clsb);
                clsb = new ClassroomSubjectCrossRef(1,2);
                daoclsb.insert(clsb);
                clsb = new ClassroomSubjectCrossRef(1,3);
                daoclsb.insert(clsb);
                ClassroomStudentCrossRefDAO daoclst = INSTANCE.classroomStudentCrossRefDAO();
                ClassroomStudentCrossRef clst = new ClassroomStudentCrossRef(1,1);
                daoclst.insert(clst);
                clst = new ClassroomStudentCrossRef(1,2);
                daoclst.insert(clst);
                clst = new ClassroomStudentCrossRef(1,3);
                daoclst.insert(clst);
                StudentExamQuestionCrossRefDAO daostex = INSTANCE.studentExamQuestionCrossRefDAO();

                StudentExamQuestionCrossRef stex = new StudentExamQuestionCrossRef(1,1,1,"A");
                daostex.insert(stex);
                stex = new StudentExamQuestionCrossRef(2,1,1,"C");
                daostex.insert(stex);
                stex = new StudentExamQuestionCrossRef(3,1,1,"D");
                daostex.insert(stex);
            });

        }
    };
}
