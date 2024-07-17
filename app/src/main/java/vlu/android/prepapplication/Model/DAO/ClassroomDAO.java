package vlu.android.prepapplication.Model.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;

import java.util.List;

import vlu.android.prepapplication.Model.Classroom;
import vlu.android.prepapplication.Model.Teacher;

@Dao
public interface ClassroomDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public void insert (Classroom classroom);


    @Query("SELECT * FROM classroom")
    LiveData<List<Classroom>> getAllClassroom();
    @Query("SELECT * FROM classroom WHERE classroomId = :id")
    LiveData<Classroom> getQuestionByID(int id);
    @Query("SELECT * FROM classroom WHERE classroomId = :id")
    public LiveData<Classroom> getClassroomById(int id);
    @Delete
    public void deleteClassroom(Classroom classroom);
    @Query("SELECT * FROM teacher WHERE teacherId = :teacherId")
    Teacher getTeacherById(int teacherId);
    @Query("SELECT * FROM classroom WHERE classroomId IN (:ids)")
    public LiveData<List<Classroom> >getClassroomsByIds(List<Integer> ids);

}
