package vlu.android.prepapplication.Model.DAO;

import androidx.room.Dao;
import androidx.room.Insert;

import vlu.android.prepapplication.Model.TeacherNotiAndStudentNoti;

@Dao
public interface TeacherNotiAndStudentNotiDAO {
    @Insert
    public void insert (TeacherNotiAndStudentNoti teacherNotiAndStudentNoti);

}
