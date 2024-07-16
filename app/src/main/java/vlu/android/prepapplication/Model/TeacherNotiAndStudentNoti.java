package vlu.android.prepapplication.Model;

import androidx.room.Entity;
import androidx.room.ForeignKey;

@Entity(primaryKeys = {"teacherNotificationId","studentNotificationId"},foreignKeys = {@ForeignKey(entity = TeacherNotification.class,parentColumns = "teacherNotificationId",childColumns = "teacherNotificationId",onDelete = ForeignKey.CASCADE,onUpdate = ForeignKey.CASCADE),
        @ForeignKey(entity = StudentNotification.class,parentColumns = "studentNotificationId",childColumns = "studentNotificationId",onDelete = ForeignKey.CASCADE,onUpdate = ForeignKey.CASCADE)})
public class TeacherNotiAndStudentNoti {
    private int teacherNotificationId, studentNotificationId;

    public TeacherNotiAndStudentNoti(int teacherNotificationId, int studentNotificationId) {
        this.teacherNotificationId = teacherNotificationId;
        this.studentNotificationId = studentNotificationId;
    }

    public int getTeacherNotificationId() {
        return teacherNotificationId;
    }

    public void setTeacherNotificationId(int teacherNotificationId) {
        this.teacherNotificationId = teacherNotificationId;
    }

    public int getStudentNotificationId() {
        return studentNotificationId;
    }

    public void setStudentNotificationId(int studentNotificationId) {
        this.studentNotificationId = studentNotificationId;
    }
}
