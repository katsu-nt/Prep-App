package vlu.android.prepapplication.Model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "exam")
public class Exam {
    @PrimaryKey(autoGenerate = true)
    private int examId;

    public Exam(int examId) {
        this.examId = examId;
    }

    public int getExamId() {
        return examId;
    }

    public void setExamId(int examId) {
        this.examId = examId;
    }
}
