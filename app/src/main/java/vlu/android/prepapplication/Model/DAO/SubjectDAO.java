package vlu.android.prepapplication.Model.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import vlu.android.prepapplication.Model.Subject;

@Dao
public interface SubjectDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public void insert(Subject subject);

    @Query("SELECT * FROM subject")
    public LiveData<List<Subject>> getAllSubject();

    @Query("SELECT * FROM subject WHERE subjectId = :id")
    public LiveData<Subject> getSubjectByID(int id);

    @Query("SELECT * FROM subject WHERE name LIKE '%' || :name || '%'")
    public LiveData<List<Subject>> getSubjectByName(String name);

    @Delete
    public void deleteSubjet(Subject subject);
    @Query("Select * from subject where subjectId in (:ids)")
    public LiveData<List<Subject>> getSubjectsByClassroomId(List<Integer> ids);
}
