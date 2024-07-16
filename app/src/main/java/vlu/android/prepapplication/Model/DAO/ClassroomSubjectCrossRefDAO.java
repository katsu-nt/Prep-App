package vlu.android.prepapplication.Model.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import vlu.android.prepapplication.Model.ClassroomSubjectCrossRef;

@Dao
public interface ClassroomSubjectCrossRefDAO {
    @Insert
    public void insert(ClassroomSubjectCrossRef classroomSubjectCrossRef);
    @Query("Select * from classroomsubjectcrossref where classroomId = :id")
    public LiveData<List<ClassroomSubjectCrossRef>> getAllByClassroomId(int id);
}
