package vlu.android.prepapplication;

import android.app.Activity;
import android.os.Bundle;
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

import vlu.android.prepapplication.Fragment.Teacher.ClassroomFragment;
import vlu.android.prepapplication.Fragment.Teacher.SubjectFragment;
import vlu.android.prepapplication.ViewModel.TeacherViewModel;
import vlu.android.prepapplication.databinding.ActivityTeacherBinding;

public class TeacherActivity extends AppCompatActivity {
    ActivityTeacherBinding binding;
    private TeacherViewModel teacherViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding= ActivityTeacherBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        teacherViewModel = new ViewModelProvider(this).get(TeacherViewModel.class);
        replaceFragment(new ClassroomFragment());
        binding.navTeacher.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getTitle().toString().trim()){
                    case "Classroom":
                        replaceFragment(new ClassroomFragment());
                        break;
                    case "Subject":
                        replaceFragment(new SubjectFragment());
                        break;
                }
                return true;
            }
        });
    }
    private void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.flTeacher,fragment);
        fragmentTransaction.commit();
    }
}