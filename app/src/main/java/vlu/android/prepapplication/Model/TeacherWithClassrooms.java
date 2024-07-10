package vlu.android.prepapplication.Model;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class TeacherWithClassrooms {
    @Embedded public Teacher teacher;
    @Relation(
            parentColumn = "teacherId",
            entityColumn = "classroomId"
    )
    public List<Classroom> classrooms;
}
