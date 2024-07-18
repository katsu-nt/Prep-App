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

import vlu.android.prepapplication.Model.Student;
import vlu.android.prepapplication.Model.Teacher;
import vlu.android.prepapplication.Repository.Repository;

public class SignUpActivity extends AppCompatActivity {
    private String role = "Student";
    Button btnSignUp;
    EditText edtName,edtUsername, edtPass,edtConfirmPass;
    TextView txtSignIn;
    private Repository repository;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sign_up);
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
    void addControl(){
        btnSignUp = findViewById(R.id.btnSignUpOk);
        edtName = findViewById(R.id.edtSignUpName);
        edtUsername = findViewById(R.id.edtSignUpUsername);
        edtPass = findViewById(R.id.edtSignUpPass);
        edtConfirmPass = findViewById(R.id.edtSignUpCfPass);
        txtSignIn = findViewById(R.id.txtSignUpToSignIn);
    }
    void addEvent(){
        txtSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUpActivity.this,SignInActivity.class);
                startActivity(intent);
                finish();
            }
        });
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = edtName.getText().toString().trim();
                String username = edtUsername.getText().toString().trim();
                String pass = edtPass.getText().toString().trim();
                String cfpass = edtConfirmPass.getText().toString().trim();
                if(!name.isEmpty()&&username.length()>=6&&pass.length()>=6&&pass.equals(cfpass)){
                    if(role.equals("Student")){
                        repository.getStudentByUsername(username).observe(SignUpActivity.this,st->{
                            if(st==null){
                                Student stu = new Student(name,username,pass);
                                repository.insertStudent(stu);
                                Toast.makeText(SignUpActivity.this, "Successful!", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(SignUpActivity.this,SignInActivity.class);
                                startActivity(intent);
                                finish();
                            }else{
                                Toast.makeText(SignUpActivity.this, "Try again something wrong!", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }else{
                        repository.getTeacherByUsername(username).observe(SignUpActivity.this,tc->{
                            if(tc==null){
                                Teacher tch = new Teacher(name,username,pass);
                                repository.insert(tch);
                                Toast.makeText(SignUpActivity.this, "Successful!", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(SignUpActivity.this,SignInActivity.class);
                                startActivity(intent);
                                finish();
                            }else{
                                Toast.makeText(SignUpActivity.this, "Try again something wrong!", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }else{
                    Toast.makeText(SignUpActivity.this, "Try again something wrong!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    public void choseRole(View view) {
        RadioButton rdTeacher = findViewById(R.id.rdSignUpTeacher);
        RadioButton rdStudent = findViewById(R.id.rdSignUpStudent);
        boolean checked = ((RadioButton) view).isChecked();
        if (view.getId() == R.id.rdSignUpStudent && checked) {
            role = "Student";
            rdTeacher.setChecked(false);
        } else if (view.getId() == R.id.rdSignUpTeacher && checked) {
            role = "Teacher";
            rdStudent.setChecked(false);
        }
    }
}