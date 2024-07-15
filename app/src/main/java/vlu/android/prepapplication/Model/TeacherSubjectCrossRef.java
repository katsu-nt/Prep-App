package vlu.android.prepapplication.Model;

import androidx.room.Entity;
import androidx.room.ForeignKey;

@Entity(primaryKeys = {"teacherId","subjectId"},foreignKeys = {@ForeignKey(entity = Teacher.class,parentColumns = "teacherId",childColumns = "teacherId",onDelete = ForeignKey.CASCADE,onUpdate = ForeignKey.CASCADE),
        @ForeignKey(entity = Subject.class,parentColumns = "subjectId",childColumns = "subjectId",onDelete = ForeignKey.CASCADE,onUpdate = ForeignKey.CASCADE)})
public class TeacherSubjectCrossRef {
    private int teacherId;
    private int subjectId;

    public TeacherSubjectCrossRef(int teacherId, int subjectId) {
        this.teacherId = teacherId;
        this.subjectId = subjectId;
    }

    public int getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(int teacherId) {
        this.teacherId = teacherId;
    }

    public int getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(int subjectId) {
        this.subjectId = subjectId;
    }
}
