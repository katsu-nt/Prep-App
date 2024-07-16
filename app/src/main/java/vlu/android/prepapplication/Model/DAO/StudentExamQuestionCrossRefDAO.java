package vlu.android.prepapplication.Model.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import vlu.android.prepapplication.Model.StudentExamQuestionCrossRef;

@Dao
public interface StudentExamQuestionCrossRefDAO {
    @Insert
    public void insert(StudentExamQuestionCrossRef studentExamQuestionCrossRef);
    @Query("select * from StudentExamQuestionCrossRef where studentId = :id")
    public LiveData<List<StudentExamQuestionCrossRef>> getAllByStudentId(int id);

}
