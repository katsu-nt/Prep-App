package vlu.android.prepapplication.Model;

import androidx.room.Embedded;
import androidx.room.Junction;
import androidx.room.Relation;

import java.util.List;

public class SubjectWithClassrooms {
    @Embedded
    public Subject subject;
    @Relation(
            parentColumn = "subjectId",
            entityColumn = "classroomId",
            associateBy = @Junction(ClassroomSubjectCrossRef.class)
    )
    public List<Classroom> classrooms;
}
