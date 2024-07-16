package vlu.android.prepapplication.Model.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import vlu.android.prepapplication.Model.TeacherNotification;

@Dao
public interface TeacherNotificationDAO {
    @Insert
    public void insert(TeacherNotification teacherNotification);
    @Query("Select * from teacherNotification where teacherId = :id")
    public LiveData<List<TeacherNotification>> getAllByTeacherId(int id);
}
