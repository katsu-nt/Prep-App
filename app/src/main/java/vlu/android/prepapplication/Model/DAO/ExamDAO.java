package vlu.android.prepapplication.Model.DAO;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;

import vlu.android.prepapplication.Model.Exam;

@Dao
public interface ExamDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public void insert(Exam exam);
}
