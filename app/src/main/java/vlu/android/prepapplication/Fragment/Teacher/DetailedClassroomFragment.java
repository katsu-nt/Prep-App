package vlu.android.prepapplication.Fragment.Teacher;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import vlu.android.prepapplication.Adapter.RecyclerViewStudentInClassroom;
import vlu.android.prepapplication.R;
import vlu.android.prepapplication.ViewModel.StudentViewModel;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DetailedClassroomFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DetailedClassroomFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private TextView tvClassName, tvClassId, tvDescription;
    View view;
    private RecyclerView recyStudent;
    private EditText edtFindStudentById;
    StudentViewModel studentViewModel;
    private RecyclerViewStudentInClassroom adapter;
    public DetailedClassroomFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ClassroomDetailFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DetailedClassroomFragment newInstance(String param1, String param2) {
        DetailedClassroomFragment fragment = new DetailedClassroomFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_classroom_detail, container, false);
        recyStudent = view.findViewById(R.id.recyStudent);
        edtFindStudentById = view.findViewById(R.id.edtFindStudentID);
        studentViewModel = new ViewModelProvider(requireActivity()).get(StudentViewModel.class);


        Button btnBack = view.findViewById(R.id.btnBack);
        btnBack.setOnClickListener(v -> {
            FragmentManager fragmentManager = getParentFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.flTeacher, new SubjectFragment());
            fragmentTransaction.commit();
        });
        tvClassName = view.findViewById(R.id.tvClassName);
        tvClassId = view.findViewById(R.id.tvClassid);
        tvDescription = view.findViewById(R.id.tvDescription);

        Bundle args = getArguments();
        if (args != null) {
            String className = args.getString("classname");
            int classId = args.getInt("classid");
            String description = args.getString("description");

            tvClassName.setText(className);
            tvClassId.setText("Class ID: "+ classId);
            tvDescription.setText(description);
        }
        Button addStudent = view.findViewById(R.id.addStudentDL);
        addStudent.setOnClickListener(v -> showAddStudentDialog());
        return view;
    }
    private void showAddStudentDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_add_student_layout, null);

        EditText edtStudentID = dialogView.findViewById(R.id.edtFindStudentID);

        builder.setView(dialogView)
                .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String studentID = edtStudentID.getText().toString().trim();
                        if (!studentID.isEmpty()) {
                            // Add student logic here (e.g., update database, update UI)
                            Toast.makeText(requireContext(), "Student added: " + studentID, Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(requireContext(), "Please enter a Student ID", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .create()
                .show();
    }

}