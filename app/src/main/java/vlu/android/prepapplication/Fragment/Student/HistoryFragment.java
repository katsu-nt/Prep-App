package vlu.android.prepapplication.Fragment.Student;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import vlu.android.prepapplication.Adapter.SpinnerHistoryExamAdapter;
import vlu.android.prepapplication.Adapter.SpinnerTestingClassroomAdapter;
import vlu.android.prepapplication.Adapter.SpinnerTestingSubjectAdapter;
import vlu.android.prepapplication.Fragment.Student.CustomDialogFragment.JoinDialogFragment;
import vlu.android.prepapplication.Model.Classroom;
import vlu.android.prepapplication.Model.Exam;
import vlu.android.prepapplication.Model.Student;
import vlu.android.prepapplication.Model.Subject;
import vlu.android.prepapplication.R;
import vlu.android.prepapplication.ViewModel.StudentViewModel;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HistoryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HistoryFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    EditText edtSearhToJoin;
    Spinner spinClassroom, spinSubject,spinExam;

    private Student student;
    private StudentViewModel studentViewModel;
    private List<Classroom> listClassroom;
    private List<Subject> listSubjects;
    private List<Exam> examList;
    Button btnReview;
    private Exam selectedExam;
    private Subject selectedSubject;
    public HistoryFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HistoryFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HistoryFragment newInstance(String param1, String param2) {
        HistoryFragment fragment = new HistoryFragment();
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
        return inflater.inflate(R.layout.fragment_history, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        studentViewModel = new ViewModelProvider((requireActivity())).get(StudentViewModel.class);
        Intent intent = getActivity().getIntent();
        int idStudent = intent.getIntExtra("studentId",-1);
        addControl(view);
        loadSpinner(idStudent);
        addEvent(idStudent);
    }
    void addControl(View view){
        edtSearhToJoin = view.findViewById(R.id.edtHistoryJoinClass);
        spinClassroom = view.findViewById(R.id.spinHistoryClassroom);
        spinSubject = view.findViewById(R.id.spinHistorySubject);
        spinExam = view.findViewById(R.id.spinHistoryExam);
        btnReview = view.findViewById(R.id.btnReview);
    }
    void addEvent(int idStudent){
        edtSearhToJoin.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId == EditorInfo.IME_ACTION_DONE){
                    if(!edtSearhToJoin.getText().toString().trim().isEmpty()){
                        int idQuery = Integer.parseInt(edtSearhToJoin.getText().toString().trim());
                        studentViewModel.getClassroomById(idQuery).observe(getViewLifecycleOwner(),classroom -> {
                            if(classroom!=null){
                                JoinDialogFragment joinDialogFragment = new JoinDialogFragment("Join with "+ classroom.getName(),"Make sure you want join class with ID "+classroom.getClassroomId()+"?",classroom);
                                joinDialogFragment.show(getActivity().getSupportFragmentManager(), null);
                                joinDialogFragment.setCancelable(false);
                                edtSearhToJoin.setText("");
                            }
                        });
                    }else{
                        Toast.makeText(getActivity(), "ID classroom was not existed!", Toast.LENGTH_SHORT).show();
                    }
                    loadSpinner(idStudent);
                    return false;
                }
                return false;
            }
        });
        spinClassroom.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(listClassroom.size()>0){
                    int selectedId = listClassroom.get(position).getClassroomId();
                    studentViewModel.getSubjectIds(selectedId).observe(getViewLifecycleOwner(),ids->{
                        studentViewModel.getSubjectsByClassroomId(ids).observe(getViewLifecycleOwner(),subjects -> {
                            listSubjects = subjects;
                            SpinnerTestingSubjectAdapter subjectAdapter = new SpinnerTestingSubjectAdapter(getActivity(), listSubjects);
                            spinSubject.setAdapter(subjectAdapter);
                        });
                    });
                }else{
                    listSubjects = null;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinSubject.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(listSubjects.size()>0){
                    selectedSubject = listSubjects.get(position);
                    studentViewModel.getExams(selectedSubject.getSubjectId()).observe(getViewLifecycleOwner(),exams -> {
                            examList = exams;
                            SpinnerHistoryExamAdapter examAdapter = new SpinnerHistoryExamAdapter(getActivity(), examList);
                            spinExam.setAdapter(examAdapter);
                    });
                }else {
                    examList = null;
                    selectedSubject = null;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinExam.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(examList.size()>0){
                    selectedExam = examList.get(position);
                }else{
                    selectedExam= null;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        btnReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(selectedExam!=null){
                    getActivity().getIntent().putExtra("examId",selectedExam.getExamId());
                    replaceFragment(new ReviewFragment());
                }else{
                    Toast.makeText(getActivity(),"Please select exam!",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.flStudent, fragment);
        fragmentTransaction.commit();
    }
    void loadSpinner(int idStudent){
        studentViewModel.getStudentById(idStudent).observe(getViewLifecycleOwner(),item->{
            student = item;
            studentViewModel.getListClassroomId(student.getStudentId()).observe(getViewLifecycleOwner(),ls -> {
                if(ls.size()>0){
                    studentViewModel.getClassroomsByIds(ls).observe(getViewLifecycleOwner(),classrooms -> {
                        listClassroom = classrooms;
                        SpinnerTestingClassroomAdapter classroomAdapter = new SpinnerTestingClassroomAdapter(getActivity(), classrooms);
                        spinClassroom.setAdapter(classroomAdapter);
                    });
                }
            });
        });
    }
}