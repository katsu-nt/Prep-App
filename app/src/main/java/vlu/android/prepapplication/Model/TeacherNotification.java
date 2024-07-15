package vlu.android.prepapplication.Model;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "teacherNotification",foreignKeys = {@ForeignKey(entity = Teacher.class,parentColumns = "teacherId",childColumns = "teacherId",onDelete = ForeignKey.CASCADE,onUpdate = ForeignKey.CASCADE)})
public class TeacherNotification {
    @PrimaryKey(autoGenerate = true)
    private int teacherNotificationId;
    private String message;
    private int action;//-1 Reject     0 Accept     1 Applied
    private int teacherId;

    public TeacherNotification(String message, int action, int teacherId) {
        this.message = message;
        this.action = action;
        this.teacherId = teacherId;
    }

    public int getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(int teacherId) {
        this.teacherId = teacherId;
    }


    public int getTeacherNotificationId() {
        return teacherNotificationId;
    }

    public void setTeacherNotificationId(int teacherNotificationId) {
        this.teacherNotificationId = teacherNotificationId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getAction() {
        return action;
    }

    public void setAction(int action) {
        this.action = action;
    }
}
