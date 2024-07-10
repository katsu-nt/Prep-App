package vlu.android.prepapplication.Model;

import androidx.room.Embedded;
import androidx.room.Junction;
import androidx.room.Relation;

import java.util.List;

public class TeacherWithSubjects {
    @Embedded
    public Teacher teacher;
    @Relation(
            parentColumn = "teacherId",
            entityColumn = "subjectId",
            associateBy = @Junction(TeacherSubjectCrossRef.class)
    )
    public List<Subject> subjects;
}
