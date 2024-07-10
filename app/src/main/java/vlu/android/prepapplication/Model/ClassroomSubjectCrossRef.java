package vlu.android.prepapplication.Model;

import androidx.room.Entity;

@Entity(primaryKeys = {"classroomId","subjectId"})
public class ClassroomSubjectCrossRef {
    public int classroomId;
    public int subjectId;

    public ClassroomSubjectCrossRef(int classroomId, int subjectId) {
        this.classroomId = classroomId;
        this.subjectId = subjectId;
    }

    public int getClassroomId() {
        return classroomId;
    }

    public void setClassroomId(int classroomId) {
        this.classroomId = classroomId;
    }

    public int getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(int subjectId) {
        this.subjectId = subjectId;
    }
}
