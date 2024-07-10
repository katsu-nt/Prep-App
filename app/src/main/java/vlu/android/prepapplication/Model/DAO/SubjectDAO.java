package vlu.android.prepapplication.Model.DAO;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;

import vlu.android.prepapplication.Model.Subject;

@Dao
public interface SubjectDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public void insert(Subject subject);
}
