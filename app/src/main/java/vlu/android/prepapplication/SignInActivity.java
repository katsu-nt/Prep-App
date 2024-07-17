package vlu.android.prepapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import vlu.android.prepapplication.Model.Student;
import vlu.android.prepapplication.Model.Teacher;
import vlu.android.prepapplication.Repository.Repository;

public class SignInActivity extends AppCompatActivity {
    private String role = "Student";
    Button btnSignIn;
    EditText edtUsername, edtPass;
    TextView txtForgot, txtSignUp;
    private Repository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sign_in);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        repository = new Repository(getApplication());
        repository.connectDB();
        addControl();
        addEvent();
    }

    void addControl() {
        btnSignIn = findViewById(R.id.btnSignInOk);
        edtPass = findViewById(R.id.edtSignInPass);
        edtUsername = findViewById(R.id.edtSignInUsername);
        txtForgot = findViewById(R.id.txtSignInForgot);
        txtSignUp = findViewById(R.id.txtSignInToSignUp);
    }

    void addEvent() {
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username, password;
                username = edtUsername.getText().toString();
                password = edtPass.getText().toString();

                if (username.length() < 6 || password.length() < 6) {
                    Toast.makeText(SignInActivity.this, "Invalid username or password", Toast.LENGTH_SHORT).show();
                } else {
                    if (role.equals("Student")) {
                        LiveData<Student> studentLiveData = repository.getStudentByUsername(username);
                        studentLiveData.observe(SignInActivity.this, new Observer<Student>() {
                            @Override
                            public void onChanged(Student student) {
                                if (student != null && student.getPassword().equals(password)) {
                                    Toast.makeText(SignInActivity.this, "Welcome " + student.getName(), Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(SignInActivity.this, StudentActivity.class);
                                    intent.putExtra("studentId", student.getStudentId());
                                    startActivity(intent);
                                    finish();
                                } else {
                                    Toast.makeText(SignInActivity.this, "Invalid username or password", Toast.LENGTH_SHORT).show();
                                }
                                studentLiveData.removeObserver(this);
                            }
                        });

                    } else {
                        LiveData<Teacher> teacherLiveData = repository.getTeacherByUsername(username);
                        teacherLiveData.observe(SignInActivity.this, new Observer<Teacher>() {
                            @Override
                            public void onChanged(Teacher teacher) {
                                if (teacher != null && teacher.getPassword().equals(password)) {
                                    Toast.makeText(SignInActivity.this, "Welcome " + teacher.getName(), Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(SignInActivity.this, TeacherActivity.class);
                                    intent.putExtra("teacherId", teacher.getTeacherId());
                                    startActivity(intent);
                                    finish();
                                } else {
                                    Toast.makeText(SignInActivity.this, "Invalid username or password", Toast.LENGTH_SHORT).show();
                                }
                                teacherLiveData.removeObserver(this);
                            }
                        });
                    }

                }

            }
        });
        txtSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignInActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });
    }

    public void choseRole(View view) {
        RadioButton rdTeacher = findViewById(R.id.rdTeacher);
        RadioButton rdStudent = findViewById(R.id.rdStudent);
        boolean checked = ((RadioButton) view).isChecked();
        if (view.getId() == R.id.rdStudent && checked) {
            role = "Student";
            rdTeacher.setChecked(false);
        } else if (view.getId() == R.id.rdTeacher && checked) {
            role = "Teacher";
            rdStudent.setChecked(false);
        }
    }
}
