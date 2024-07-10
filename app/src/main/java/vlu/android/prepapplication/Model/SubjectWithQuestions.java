package vlu.android.prepapplication.Model;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class SubjectWithQuestions {
    @Embedded
    public Subject subject;
    @Relation(
            parentColumn = "subjectId",
            entityColumn = "questionId"
    )
    public List<Question> questions;
}
