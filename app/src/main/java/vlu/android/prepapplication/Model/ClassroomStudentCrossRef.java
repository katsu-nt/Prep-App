package vlu.android.prepapplication.Model;

import androidx.room.Entity;
import androidx.room.ForeignKey;

@Entity(primaryKeys = {"classroomId","studentId"},foreignKeys = {@ForeignKey(entity = Classroom.class,parentColumns = "classroomId",childColumns = "classroomId",onDelete = ForeignKey.CASCADE,onUpdate = ForeignKey.CASCADE)
        ,@ForeignKey(entity = Student.class,parentColumns = "studentId",childColumns = "studentId",onDelete = ForeignKey.CASCADE,onUpdate = ForeignKey.CASCADE)
        })
public class ClassroomStudentCrossRef {
    public int classroomId;
    public int studentId;

    public ClassroomStudentCrossRef(int classroomId, int studentId) {
        this.classroomId = classroomId;
        this.studentId = studentId;
    }

    public int getClassroomId() {
        return classroomId;
    }

    public void setClassroomId(int classroomId) {
        this.classroomId = classroomId;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }
}
