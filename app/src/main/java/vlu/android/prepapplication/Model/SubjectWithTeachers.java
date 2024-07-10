package vlu.android.prepapplication.Model;

import androidx.room.Embedded;
import androidx.room.Junction;
import androidx.room.Relation;

import java.util.List;

public class SubjectWithTeachers {
    @Embedded
    public Subject subject;
    @Relation(
            parentColumn = "subjectId",
            entityColumn = "teacherId",
            associateBy = @Junction(TeacherSubjectCrossRef.class)
    )
    public List<Teacher> teachers;
}
