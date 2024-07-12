package vlu.android.prepapplication.Model.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import vlu.android.prepapplication.Model.Question;

@Dao
public interface QuestionDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public void insert(Question question);

    @Query("SELECT * FROM question")
    public LiveData<List<Question>> getAllQuestion();

    @Query("SELECT * FROM question WHERE questionId = :id")
    public  LiveData<Question> getQuestionByID(int id);
}
