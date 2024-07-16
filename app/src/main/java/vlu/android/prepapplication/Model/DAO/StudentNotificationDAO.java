package vlu.android.prepapplication.Model.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import vlu.android.prepapplication.Model.StudentNotification;

@Dao
public interface StudentNotificationDAO {
@Insert
    public void insert(StudentNotification studentNotification);
@Query("select * from studentNotification where studentId = :id")
    public LiveData<List<StudentNotification>> getAllByStudentId(int id);
}
