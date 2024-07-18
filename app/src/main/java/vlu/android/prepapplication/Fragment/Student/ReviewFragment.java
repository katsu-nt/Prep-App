package vlu.android.prepapplication.Fragment.Student;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import vlu.android.prepapplication.Model.Exam;
import vlu.android.prepapplication.Model.Question;
import vlu.android.prepapplication.Model.StudentExamQuestionCrossRef;
import vlu.android.prepapplication.R;
import vlu.android.prepapplication.ViewModel.StudentViewModel;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ReviewFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ReviewFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private RadioGroup rdReviewGr;
    private RadioButton rdA, rdB, rdC, rdD;
    private TextView txtQuestionNum, txtExamContent, txtExamId,txtCorrect;
    private Button btnPrev, btnNext,btnCancel;
    private StudentViewModel studentViewModel;

    private int crrNumQuestion = 0;
    private List<Question> questionList;
    private String[] arrAnswer = new String[10];
    public ReviewFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ReviewFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ReviewFragment newInstance(String param1, String param2) {
        ReviewFragment fragment = new ReviewFragment();
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
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_review, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initializeViews(view);
        initializeListeners();
        loadExamData();
    }
    private void initializeViews(View view) {
        rdReviewGr = view.findViewById(R.id.rdReviewGr);
        rdA = view.findViewById(R.id.rdReviewA);
        rdB = view.findViewById(R.id.rdReviewB);
        rdC = view.findViewById(R.id.rdReviewC);
        rdD = view.findViewById(R.id.rdReviewD);
        btnNext = view.findViewById(R.id.btnReviewNext);
        btnPrev = view.findViewById(R.id.btnReviewPrev);
        btnCancel = view.findViewById(R.id.btnReviewCancel);
        txtExamContent = view.findViewById(R.id.txtReviewContent);
        txtQuestionNum = view.findViewById(R.id.txtReviewNum);
        txtExamId = view.findViewById(R.id.txtExamId);
        txtCorrect = view.findViewById(R.id.txtCorrect);

        txtQuestionNum.setText(String.valueOf(crrNumQuestion + 1));


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

        rdReviewGr.setOnCheckedChangeListener((group, checkedId) -> {
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
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.flStudent, new HistoryFragment()).addToBackStack(null).commit();
            }
        });

    }

    private void saveCurrentAnswer() {
        int checkedRadioButtonId = rdReviewGr.getCheckedRadioButtonId();
        if (checkedRadioButtonId != -1) {
            RadioButton selectedRadioButton = rdReviewGr.findViewById(checkedRadioButtonId);
            arrAnswer[crrNumQuestion] = selectedRadioButton.getText().toString();
        } else {
            arrAnswer[crrNumQuestion] = "";
        }
    }

    private void loadExamData() {
        int examId = getActivity().getIntent().getIntExtra("examId",-1);
        txtExamId.setText("Exam ID: "+examId);
        questionList = new ArrayList<>();
        studentViewModel.getAllExam(examId).observe(getViewLifecycleOwner(),studentExamQuestionCrossRefs -> {
            studentExamQuestionCrossRefs.forEach(studentExamQuestionCrossRef -> {
                studentViewModel.getQuestionByID(studentExamQuestionCrossRef.getQuestionId()).observe(getViewLifecycleOwner(),question -> {
                    questionList.add(question);
                    arrAnswer[questionList.size()-1] = studentExamQuestionCrossRef.getStudentAnswer();
                    loadQuestionData();
                });
            });
        });
    }

    private void loadQuestionData() {
        Question crrQuestion = questionList.get(crrNumQuestion);
        txtCorrect.setText(crrQuestion.getCorrectAnswer());
        txtQuestionNum.setText(String.valueOf(crrNumQuestion + 1));
        txtExamContent.setText(crrQuestion.getContent());
        rdA.setText(crrQuestion.getAnswerA());
        rdB.setText(crrQuestion.getAnswerB());
        rdC.setText(crrQuestion.getAnswerC());
        rdD.setText(crrQuestion.getAnswerD());

        updateSelectedOption();
    }

    private void updateSelectedOption() {
        rdReviewGr.setOnCheckedChangeListener(null);
        rdReviewGr.clearCheck();
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

        rdReviewGr.setOnCheckedChangeListener((group, checkedId) -> {
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