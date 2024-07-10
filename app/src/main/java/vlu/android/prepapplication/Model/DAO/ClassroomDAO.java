package vlu.android.prepapplication.Model.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;

import java.util.List;

import vlu.android.prepapplication.Model.Classroom;

@Dao
public interface ClassroomDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public void insert (Classroom classroom);

}
