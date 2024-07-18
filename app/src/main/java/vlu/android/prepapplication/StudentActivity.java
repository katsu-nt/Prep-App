package vlu.android.prepapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.navigation.NavigationBarView;

import vlu.android.prepapplication.Fragment.Student.HistoryFragment;
import vlu.android.prepapplication.Fragment.Student.TestingFragment;
import vlu.android.prepapplication.Fragment.Teacher.ClassroomFragment;
import vlu.android.prepapplication.Fragment.Teacher.SettingsFragment;
import vlu.android.prepapplication.Fragment.Teacher.SubjectFragment;
import vlu.android.prepapplication.Model.Student;
import vlu.android.prepapplication.ViewModel.StudentViewModel;
import vlu.android.prepapplication.ViewModel.TeacherViewModel;
import vlu.android.prepapplication.databinding.ActivityStudentBinding;
import vlu.android.prepapplication.databinding.ActivityTeacherBinding;

public class StudentActivity extends AppCompatActivity {
    ActivityStudentBinding binding;
    private StudentViewModel studentViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityStudentBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        studentViewModel = new ViewModelProvider(this).get(StudentViewModel.class);
        replaceFragment(new TestingFragment());
        addControl();
        addEvent();
        binding.navStudent.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getTitle().toString().trim()) {
                    case "Testing":
                        replaceFragment(new TestingFragment());
                        break;
                    case "History":
                        replaceFragment(new HistoryFragment());
                        break;
                    case "Setting":
                        replaceFragment(new SettingsFragment());
                        break;
                }
                return true;
            }
        });
    }
    void addControl(){

    }
    void addEvent(){}
    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.flStudent, fragment);
        fragmentTransaction.commit();
    }
}