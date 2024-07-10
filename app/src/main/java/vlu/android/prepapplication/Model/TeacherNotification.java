package vlu.android.prepapplication.Model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "teacherNotification")
public class TeacherNotification {
    @PrimaryKey(autoGenerate = true)
    private int teacherNotificationId;
    private String message;
    private int action;//-1 Reject     0 Accept     1 Applied

    public TeacherNotification(String message, int action) {
        this.message = message;
        this.action = action;
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
