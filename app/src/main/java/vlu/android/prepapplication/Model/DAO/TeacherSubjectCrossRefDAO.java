package vlu.android.prepapplication.Model.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import vlu.android.prepapplication.Model.TeacherSubjectCrossRef;

@Dao
public interface TeacherSubjectCrossRefDAO {
    @Insert
    public void insert(TeacherSubjectCrossRef teacherSubjectCrossRef);
    @Query("select * from TeacherSubjectCrossRef where teacherId = :id")
    public LiveData<List<TeacherSubjectCrossRef>> getAllByTeacherId(int id);
}
