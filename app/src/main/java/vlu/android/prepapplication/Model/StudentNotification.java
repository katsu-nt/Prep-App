package vlu.android.prepapplication.Model;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "studentNotification",foreignKeys = {@ForeignKey(entity = Student.class,parentColumns = "studentId",childColumns = "studentId",onDelete = ForeignKey.CASCADE,onUpdate = ForeignKey.CASCADE)})
public class StudentNotification {
    @PrimaryKey(autoGenerate = true)
    private int studentNotificationId;
    private String messsage;

    private int studentId;

    public StudentNotification(String messsage, int studentId) {
        this.messsage = messsage;
        this.studentId = studentId;
    }

    public int getStudentNotificationId() {
        return studentNotificationId;
    }

    public void setStudentNotificationId(int studentNotificationId) {
        this.studentNotificationId = studentNotificationId;
    }

    public String getMesssage() {
        return messsage;
    }

    public void setMesssage(String messsage) {
        this.messsage = messsage;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }
}
