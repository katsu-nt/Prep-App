package vlu.android.prepapplication.Model.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import vlu.android.prepapplication.Model.Question;

@Dao
public interface QuestionDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public void insert(Question question);

    @Query("SELECT * FROM question WHERE subjectId = :subjectId")
    public LiveData<List<Question>> getAllQuestion(int subjectId);

    @Query("SELECT * FROM question WHERE questionId = :id AND subjectId = :subjectId")
    public LiveData<Question> getQuestionByID(int id, int subjectId);

    @Update
    public void update(Question question);

    @Delete
    public void delete(Question question);
    @Query("Select count(*) from question where subjectId = :subjectId")
    public LiveData<Integer> countQuestion (int subjectId);
    @Query("SELECT * FROM ( " +
            "SELECT *, RANDOM() AS random_number FROM question WHERE subjectId = :subjectId " +
            ") ORDER BY random_number LIMIT 10")
    public LiveData<List<Question>> getQuestionForExam(int subjectId);
    @Query("Select * from question where questionId = :id")
    public LiveData<Question> getQuestionById (int id);
}
