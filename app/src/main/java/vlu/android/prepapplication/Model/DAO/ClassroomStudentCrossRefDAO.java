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
    @Query("select count(*) from classroomstudentcrossref where studentId = :studentId and classroomId =:classId")
    public LiveData<Integer> checkJoined (int studentId, int classId);
    @Query("select classroomId from classroomstudentcrossref where studentId = :id")
    public LiveData<List<Integer>> getListClassroomId(int id);
}
