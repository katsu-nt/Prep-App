package vlu.android.prepapplication.Model;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class TeacherWithTeacherNotifications {
    @Embedded
    public Teacher teacher;
    @Relation(
            parentColumn = "teacherId",
            entityColumn = "teacherNotificationId"
    )
    public List<TeacherNotification> teacherNotifications;
}
