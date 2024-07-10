package vlu.android.prepapplication.Model;

import androidx.room.Embedded;
import androidx.room.Relation;

public class ExamAndQuestion {
    @Embedded public Exam exam;
    @Relation(
            parentColumn = "examId",
            entityColumn = "questionId"
    )
    public Question question;
}
