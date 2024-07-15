package vlu.android.prepapplication.Model;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "exam",foreignKeys = {@ForeignKey(entity = Subject.class,parentColumns = "subjectId",childColumns = "subjectId",onDelete = ForeignKey.CASCADE,onUpdate = ForeignKey.CASCADE)})
public class Exam {
    @PrimaryKey(autoGenerate = true)
    private int examId;
    private int subjectId;

    public Exam( int subjectId) {
        this.subjectId = subjectId;
    }

    public int getExamId() {
        return examId;
    }

    public void setExamId(int examId) {
        this.examId = examId;
    }

    public int getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(int subjectId) {
        this.subjectId = subjectId;
    }
}
