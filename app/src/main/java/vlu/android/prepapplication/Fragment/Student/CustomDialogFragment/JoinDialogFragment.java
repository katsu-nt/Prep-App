package vlu.android.prepapplication.Fragment.Student.CustomDialogFragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import vlu.android.prepapplication.Model.Classroom;
import vlu.android.prepapplication.Model.ClassroomStudentCrossRef;
import vlu.android.prepapplication.Model.Student;
import vlu.android.prepapplication.R;
import vlu.android.prepapplication.ViewModel.StudentViewModel;
import vlu.android.prepapplication.ViewModel.TeacherViewModel;

public class JoinDialogFragment extends DialogFragment {
    TextView txtHeading,txtContent;
    Button btnCancel, btnOk;
    private String heading, context;
    private StudentViewModel studentViewModel;
    private Classroom classroom;
    private Student student;

    public JoinDialogFragment(String heading, String context,Classroom classroom) {
        this.heading = heading;
        this.context= context;
        this.classroom = classroom;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return LayoutInflater.from(getContext()).inflate(R.layout.dialog_join_classroom,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        txtHeading = view.findViewById(R.id.txtDialogHeader);
        txtContent = view.findViewById(R.id.txtDialogContent);
        btnCancel = view.findViewById(R.id.btnCancel);
        btnOk = view.findViewById(R.id.btnOk);
        txtHeading.setText(heading);
        txtContent.setText(context);
        studentViewModel = new ViewModelProvider(requireActivity()).get(StudentViewModel.class);
        Intent intent = getActivity().getIntent();
        studentViewModel.getStudentById(intent.getIntExtra("studentId",-1)).observe(getViewLifecycleOwner(),student1 -> {
            student = student1;
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                studentViewModel.checkJoined(student.getStudentId(),classroom.getClassroomId()).observe(getViewLifecycleOwner(),flag->{
                    if(flag>=1){
                        Toast.makeText(getActivity(), "Was in classroom ID "+ classroom.getClassroomId(), Toast.LENGTH_SHORT).show();
                    }else{
                        studentViewModel.insertStudentToClassroom(new ClassroomStudentCrossRef(classroom.getClassroomId(),student.getStudentId()));
                    }
                });
                dismiss();
            }
        });
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }
}
