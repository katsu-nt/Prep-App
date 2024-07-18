package vlu.android.prepapplication.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import vlu.android.prepapplication.Model.Classroom;
import vlu.android.prepapplication.Model.ClassroomStudentCrossRef;
import vlu.android.prepapplication.Model.Student;
import vlu.android.prepapplication.Model.Teacher;
import vlu.android.prepapplication.R;
import vlu.android.prepapplication.Repository.Repository;
import vlu.android.prepapplication.ViewModel.StudentViewModel;

public class UpdateAccountDialogFragment extends DialogFragment {
    EditText edtName,edtPass, edtCfPass;
    Button btnCancel,btnSave;
    private Repository repository;
    private Teacher teacherCheck;
    private Student studentCheck;
    private int role;//1 teacher 0 student
    public UpdateAccountDialogFragment(int role) {
        this.role = role;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return LayoutInflater.from(getContext()).inflate(R.layout.dialog_update_account_classroom,container,false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        edtName = view.findViewById(R.id.edtUpdateName);
        edtPass = view.findViewById(R.id.edtUpdatePass);
        edtCfPass = view.findViewById(R.id.edtUpdateCfPass);
        btnCancel = view.findViewById(R.id.btnUpdateCancel);
        btnSave = view.findViewById(R.id.btnUpdateSave);
        repository = new Repository(requireActivity().getApplication());
        Intent intent = getActivity().getIntent();
        int id;
        if(role == 1){
            id = intent.getIntExtra("teacherId",-1);
            repository.getTeacherById(id).observe(getViewLifecycleOwner(),teacher -> {
                edtName.setText(teacher.getName());
                edtPass.setText(teacher.getPassword());
                edtCfPass.setText(teacher.getPassword());
                teacherCheck = teacher;
            });
        }else{
            id = intent.getIntExtra("studentId",-1);
            repository.getStudentById(id).observe(getViewLifecycleOwner(),st -> {
                edtName.setText(st.getName());
                edtPass.setText(st.getPassword());
                edtCfPass.setText(st.getPassword());
                studentCheck = st;
            });
        }
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = edtName.getText().toString().trim();
                String pass = edtPass.getText().toString().trim();
                String cfPassword = edtCfPass.getText().toString().trim();
                if(!name.isEmpty()&&pass.length()>=6&&cfPassword.equals(pass)){
                    if(role==1){
                        if(name.equals(teacherCheck.getName())&&pass.equals(teacherCheck.getPassword())){
                            Toast.makeText(getActivity(), "Nothing change!", Toast.LENGTH_SHORT).show();
                        }else{
                            teacherCheck.setName(name);
                            teacherCheck.setPassword(pass);
                            repository.updateTeacher(teacherCheck);
                            Toast.makeText(getActivity(), "Success!", Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        if(name.equals(studentCheck.getName())&&pass.equals(studentCheck.getPassword())){
                            Toast.makeText(getActivity(), "Nothing change!", Toast.LENGTH_SHORT).show();
                        }else{
                            studentCheck.setName(name);
                            studentCheck.setPassword(pass);
                            repository.udpateStudent(studentCheck);
                            Toast.makeText(getActivity(), "Success!", Toast.LENGTH_SHORT).show();
                        }
                    }
                }else{
                    Toast.makeText(getActivity(), "Failed!", Toast.LENGTH_SHORT).show();
                }
                dismiss();
            }
        });
    }
}
