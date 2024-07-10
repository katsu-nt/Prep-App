package vlu.android.prepapplication.Model;

import androidx.room.Embedded;
import androidx.room.Junction;
import androidx.room.Relation;

import java.util.List;

public class ClassroomWithSubjects {
    @Embedded
    public Classroom classroom;
    @Relation(
            parentColumn = "classroomId",
            entityColumn = "subjectId",
            associateBy = @Junction(ClassroomSubjectCrossRef.class)
    )
    public List<Subject> subjects;
}
