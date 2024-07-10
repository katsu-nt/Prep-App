package vlu.android.prepapplication.Model;

import androidx.room.Embedded;
import androidx.room.Relation;

public class TeacherNotificationAndStudentNotification {
    @Embedded public TeacherNotification teacherNotification;
    @Relation(
            parentColumn = "teacherNotificationId",
            entityColumn = "studentNotificationId"
    )
    public StudentNotification studentNotification;
}
