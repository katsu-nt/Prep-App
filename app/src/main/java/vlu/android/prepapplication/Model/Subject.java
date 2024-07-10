package vlu.android.prepapplication.Model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "subject")
public class Subject {
    @PrimaryKey(autoGenerate = true)
    private int subjectId;
    private String name;
    private String description;

    public Subject(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public int getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(int id) {
        this.subjectId = id;
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
}
