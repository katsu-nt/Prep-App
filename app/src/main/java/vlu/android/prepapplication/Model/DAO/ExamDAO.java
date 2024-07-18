package vlu.android.prepapplication.Model.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import vlu.android.prepapplication.Model.Exam;

@Dao
public interface ExamDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public void insert(Exam exam);
    @Query("select MAX(examId) from exam where subjectId=:subjectId")
    public LiveData<Integer> getNewestExamId(int subjectId);
    @Query("Select * from exam where subjectId=:subjectId")
    public LiveData<List<Exam>> getExams(int subjectId);
}
