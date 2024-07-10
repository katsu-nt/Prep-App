package vlu.android.prepapplication.Model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "studentNotification")
public class StudentNotification {
    @PrimaryKey(autoGenerate = true)
    private int studentNotificationId;
    private String messsage;

    public StudentNotification(String messsage) {
        this.messsage = messsage;
    }

    public int getStudentNotificationId() {
        return studentNotificationId;
    }

    public void setStudentNotificationId(int id) {
        this.studentNotificationId = id;
    }

    public String getMesssage() {
        return messsage;
    }

    public void setMesssage(String messsage) {
        this.messsage = messsage;
    }
}
