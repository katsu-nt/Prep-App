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
    @Query("select * from StudentExamQuestionCrossRef where examId = :id")
    public LiveData<List<StudentExamQuestionCrossRef>> getAllExam(int id);
    @Query("select * from studentexamquestioncrossref where studentId = :studentId")
    public LiveData<List<StudentExamQuestionCrossRef>> getAllExamByStudent (int studentId);

}
