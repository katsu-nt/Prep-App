package vlu.android.prepapplication.Model.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import vlu.android.prepapplication.Model.ClassroomStudentCrossRef;

@Dao
public interface ClassroomStudentCrossRefDAO {
    @Insert
    public void insert(ClassroomStudentCrossRef classroomStudentCrossRef);
    @Query("select * from classroomstudentcrossref where studentId = :id")
    public LiveData<List<ClassroomStudentCrossRef>> getAllByStudentId (int id);
}
