package vlu.android.prepapplication.Model.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import vlu.android.prepapplication.Model.Student;

@Dao
public interface StudentDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public void insert (Student student);
    @Update
    public void udpate(Student student);
    @Query("Select * from student where userName =:userName")
    public LiveData<Student> getStudentByUserName(String userName);
    @Query("select * from student where studentId=:id")
    public LiveData<Student> getStudentById(int id);
}
