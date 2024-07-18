package vlu.android.prepapplication.Fragment.Student;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.List;

import vlu.android.prepapplication.Model.Exam;
import vlu.android.prepapplication.Model.Question;
import vlu.android.prepapplication.Model.StudentExamQuestionCrossRef;
import vlu.android.prepapplication.Model.Subject;
import vlu.android.prepapplication.R;
import vlu.android.prepapplication.ViewModel.StudentViewModel;

public class InExamFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private RadioGroup radioGroup;
    private RadioButton rdA, rdB, rdC, rdD;
    private TextView txtQuestionNum, txtExamContent, txtSubject;
    private Button btnPrev, btnNext,btnQuit,btnSubmit;
    private StudentViewModel studentViewModel;

    private int crrNumQuestion = 0;
    private List<Question> questionList;
    private String[] arrAnswer = new String[10];

    public InExamFragment() {
        // Required empty public constructor
    }

    public static InExamFragment newInstance(String param1, String param2) {
        InExamFragment fragment = new InExamFragment();
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
        studentViewModel = new ViewModelProvider(requireActivity()).get(StudentViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_in_exam, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initializeViews(view);
        initializeListeners();
        int subjectId = requireActivity().getIntent().getIntExtra("subjectId", -1);
        loadExamData(subjectId);
    }

    private void initializeViews(View view) {
        radioGroup = view.findViewById(R.id.rdGroupExam);
        rdA = view.findViewById(R.id.rdExamA);
        rdB = view.findViewById(R.id.rdExamB);
        rdC = view.findViewById(R.id.rdExamC);
        rdD = view.findViewById(R.id.rdExamD);
        btnNext = view.findViewById(R.id.btnExamNext);
        btnPrev = view.findViewById(R.id.btnExamPrev);
        btnQuit = view.findViewById(R.id.btnQuitExam);
        btnSubmit = view.findViewById(R.id.btnSubmitExam);
        txtExamContent = view.findViewById(R.id.txtExamContent);
        txtQuestionNum = view.findViewById(R.id.txtExamNum);
        txtSubject = view.findViewById(R.id.txtExamSubject);

        txtQuestionNum.setText(String.valueOf(crrNumQuestion + 1));
        for (int i = 0; i < arrAnswer.length; i++) {
            arrAnswer[i] = "";
        }
    }

    private void initializeListeners() {
        btnNext.setOnClickListener(v -> {
            saveCurrentAnswer();
            if (crrNumQuestion < questionList.size() - 1) {
                crrNumQuestion++;
                loadQuestionData();
            }
        });

        btnPrev.setOnClickListener(v -> {
            saveCurrentAnswer();
            if (crrNumQuestion > 0) {
                crrNumQuestion--;
                loadQuestionData();
            }
        });

        radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.rdExamA) {
                arrAnswer[crrNumQuestion] = rdA.getText().toString();
            } else if (checkedId == R.id.rdExamB) {
                arrAnswer[crrNumQuestion] = rdB.getText().toString();
            } else if (checkedId == R.id.rdExamC) {
                arrAnswer[crrNumQuestion] = rdC.getText().toString();
            } else if (checkedId == R.id.rdExamD) {
                arrAnswer[crrNumQuestion] = rdD.getText().toString();
            }
        });
        btnQuit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.flStudent, new TestingFragment()).addToBackStack(null).commit();
            }
        });
        btnSubmit.setOnClickListener(new View.OnClickListener() {

            int idStudent = requireActivity().getIntent().getIntExtra("studentId", -1);
            int subjectId = requireActivity().getIntent().getIntExtra("subjectId", -1);
            @Override
            public void onClick(View v) {
                studentViewModel.insertExam(new Exam(subjectId));
                studentViewModel.getNewestExamId(subjectId).observe(getViewLifecycleOwner(),idExam->{
                    for (int i = 0; i < questionList.size(); i++) {
                        StudentExamQuestionCrossRef studentExamQuestionCrossRef = new StudentExamQuestionCrossRef(questionList.get(i).getQuestionId(),idExam,idStudent,arrAnswer[i]);
                        studentViewModel.submitExam(studentExamQuestionCrossRef);
                    }
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.flStudent, new TestingFragment()).addToBackStack(null).commit();
                });
            }
        });
    }

    private void saveCurrentAnswer() {
        int checkedRadioButtonId = radioGroup.getCheckedRadioButtonId();
        if (checkedRadioButtonId != -1) {
            RadioButton selectedRadioButton = radioGroup.findViewById(checkedRadioButtonId);
            arrAnswer[crrNumQuestion] = selectedRadioButton.getText().toString();
        } else {
            arrAnswer[crrNumQuestion] = "";
        }
    }

    private void loadExamData(int subjectId) {
        studentViewModel.getSubjectById(subjectId).observe(getViewLifecycleOwner(), subject -> {
            if (subject != null) {
                txtSubject.setText(subject.getName());
                studentViewModel.getQuestionForExam(subject.getSubjectId()).observe(getViewLifecycleOwner(), questions -> {
                    questionList = questions;
                    loadQuestionData();
                });
            }
        });
    }

    private void loadQuestionData() {
        Question crrQuestion = questionList.get(crrNumQuestion);
        txtQuestionNum.setText(String.valueOf(crrNumQuestion + 1));
        txtExamContent.setText(crrQuestion.getContent());
        rdA.setText(crrQuestion.getAnswerA());
        rdB.setText(crrQuestion.getAnswerB());
        rdC.setText(crrQuestion.getAnswerC());
        rdD.setText(crrQuestion.getAnswerD());

        updateSelectedOption();
    }

    private void updateSelectedOption() {
        radioGroup.setOnCheckedChangeListener(null);
        radioGroup.clearCheck();

        String answer = arrAnswer[crrNumQuestion];
        if (answer.equals(rdA.getText().toString())) {
            rdA.setChecked(true);
        } else if (answer.equals(rdB.getText().toString())) {
            rdB.setChecked(true);
        } else if (answer.equals(rdC.getText().toString())) {
            rdC.setChecked(true);
        } else if (answer.equals(rdD.getText().toString())) {
            rdD.setChecked(true);
        }

        radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.rdExamA) {
                arrAnswer[crrNumQuestion] = rdA.getText().toString();
            } else if (checkedId == R.id.rdExamB) {
                arrAnswer[crrNumQuestion] = rdB.getText().toString();
            } else if (checkedId == R.id.rdExamC) {
                arrAnswer[crrNumQuestion] = rdC.getText().toString();
            } else if (checkedId == R.id.rdExamD) {
                arrAnswer[crrNumQuestion] = rdD.getText().toString();
            }
        });
    }
}
