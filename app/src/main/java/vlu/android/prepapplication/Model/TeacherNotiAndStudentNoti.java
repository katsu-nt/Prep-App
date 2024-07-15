package vlu.android.prepapplication.Model;

import androidx.room.Entity;
import androidx.room.ForeignKey;

@Entity(primaryKeys = {"studentId","teacherId"},foreignKeys = {@ForeignKey(entity = Student.class,parentColumns = "studentId",childColumns = "studentId",onDelete = ForeignKey.CASCADE,onUpdate = ForeignKey.CASCADE),
        @ForeignKey(entity = Teacher.class,parentColumns = "teacherId",childColumns = "teacherId",onDelete = ForeignKey.CASCADE,onUpdate = ForeignKey.CASCADE)})
public class TeacherNotiAndStudentNoti {
    private int studentId,teacherId;

    public TeacherNotiAndStudentNoti(int studentId, int teacherId) {
        this.studentId = studentId;
        this.teacherId = teacherId;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public int getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(int teacherId) {
        this.teacherId = teacherId;
    }
}
