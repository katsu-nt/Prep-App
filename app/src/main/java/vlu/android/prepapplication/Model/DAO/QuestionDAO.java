package vlu.android.prepapplication.Model.DAO;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;

import vlu.android.prepapplication.Model.Question;

@Dao
public interface QuestionDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public void insert (Question question);
}
