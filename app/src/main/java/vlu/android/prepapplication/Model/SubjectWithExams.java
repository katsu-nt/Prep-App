package vlu.android.prepapplication.Model;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class SubjectWithExams {
    @Embedded
    public Subject subject;
    @Relation(
            parentColumn = "subjectId",
            entityColumn = "examId"
    )
    public List<Exam> exams;
}
