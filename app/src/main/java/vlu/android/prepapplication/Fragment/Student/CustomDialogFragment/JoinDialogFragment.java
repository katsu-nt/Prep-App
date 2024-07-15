package vlu.android.prepapplication.Fragment.Student.CustomDialogFragment;

import android.content.Context;
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

import vlu.android.prepapplication.Model.Student;
import vlu.android.prepapplication.R;
import vlu.android.prepapplication.ViewModel.StudentViewModel;
import vlu.android.prepapplication.ViewModel.TeacherViewModel;

public class JoinDialogFragment extends DialogFragment {
    TextView txtHeading,txtContent;
    Button btnCancel, btnOk;
    private String heading, context;
    private StudentViewModel studentViewModel;

    public JoinDialogFragment(String heading, String context) {
        this.heading = heading;
        this.context= context;
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
        Student st = new Student("113113","113113","113113");

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Student st = new Student("113113","113113","113113");
                studentViewModel.insertStudent(st);
                dismiss();
            }
        });
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }
}
