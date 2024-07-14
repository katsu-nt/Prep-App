package vlu.android.prepapplication.Model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "classroom")
public class Classroom {
    @PrimaryKey(autoGenerate = true)
    private int classroomId;
    private String name;
    private String description;

    public Classroom(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public int getClassroomId() {
        return classroomId;
    }

    public void setClassroomId(int id) {
        this.classroomId = id;
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

    public int getId() {
        return 0;
    }
}
