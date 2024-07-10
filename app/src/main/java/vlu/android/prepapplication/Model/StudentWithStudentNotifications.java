package vlu.android.prepapplication.Model;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class StudentWithStudentNotifications {
    @Embedded public Student student;
    @Relation(
            parentColumn = "studentId",
            entityColumn = "studentNotificationId"
    )
    public List<StudentNotification> studentNotifications;
}
