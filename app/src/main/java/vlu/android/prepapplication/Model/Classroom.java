package vlu.android.prepapplication.Model;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "classroom", foreignKeys = {@ForeignKey(entity = Teacher.class,parentColumns = "teacherId",childColumns = "teacherId",onDelete = ForeignKey.CASCADE,onUpdate = ForeignKey.CASCADE)})
public class Classroom {
    @PrimaryKey(autoGenerate = true)
    private int classroomId;
    private String name;
    private String description;
    private int teacherId;
    public Classroom(String name, String description, int teacherId) {
        this.name = name;
        this.description = description;
        this.teacherId = teacherId;
    }


    public int getClassroomId() {
        return classroomId;
    }

    public void setClassroomId(int classroomId) {
        this.classroomId = classroomId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(int teacherId) {
        this.teacherId = teacherId;
    }
}
