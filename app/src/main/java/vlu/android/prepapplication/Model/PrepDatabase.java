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
import vlu.android.prepapplication.Model.DAO.ClassroomSubjectCrossRefDAO;
import vlu.android.prepapplication.Model.DAO.ExamDAO;
import vlu.android.prepapplication.Model.DAO.QuestionDAO;
import vlu.android.prepapplication.Model.DAO.StudentDAO;
import vlu.android.prepapplication.Model.DAO.StudentNotificationDAO;
import vlu.android.prepapplication.Model.DAO.SubjectDAO;
import vlu.android.prepapplication.Model.DAO.TeacherDAO;
import vlu.android.prepapplication.Model.DAO.TeacherNotificationDAO;
import vlu.android.prepapplication.Model.DAO.TeacherSubjectCrossRefDAO;

@Database(entities = {Teacher.class,Student.class,Classroom.class, Subject.class, Question.class,StudentNotification.class,TeacherNotification.class, Exam.class, ClassroomSubjectCrossRef.class,TeacherSubjectCrossRef.class},version = 1,exportSchema = false)
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
                Teacher teacher = new Teacher("teacher","benbill","113113");
                dao.insert(teacher);
            });
        }
    };
}
