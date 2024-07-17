package vlu.android.prepapplication.Model.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import java.util.List;

import vlu.android.prepapplication.Model.Teacher;

@Dao
public interface TeacherDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public void insert(Teacher teacher);
    @Update
    public void update(Teacher teacher);
    @Query("Select * from teacher where userName like :userName")
    public LiveData<Teacher> getTeacherByUserName(String userName);
    @Query("Select * from teacher where teacherId = :id")
    public LiveData<Teacher> getTeacherById(int id);
}
