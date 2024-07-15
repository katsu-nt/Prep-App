package vlu.android.prepapplication.Model;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class ClassroomWithStudents {
    @Embedded public Classroom classroom;
    @Relation(
            parentColumn = "classroomId",
            entityColumn = "studentId"
    )
    public List<Student> students;
}
