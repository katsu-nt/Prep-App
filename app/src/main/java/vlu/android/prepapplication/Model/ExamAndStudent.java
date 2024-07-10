package vlu.android.prepapplication.Model;

import androidx.room.Embedded;
import androidx.room.Relation;

public class ExamAndStudent {
    @Embedded
    public Exam exam;
    @Relation(
            parentColumn = "examId",
            entityColumn = "studentId"
    )
    public Student student;
}
