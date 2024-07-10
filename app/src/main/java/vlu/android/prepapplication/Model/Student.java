package vlu.android.prepapplication.Model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "student")
public class Student {
    @PrimaryKey(autoGenerate = true)
    private int studentId;
    private String name;
    private String userName;
    private String password;

    public Student(String name, String userName, String password) {
        this.name = name;
        this.userName = userName;
        this.password = password;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int id) {
        this.studentId = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
