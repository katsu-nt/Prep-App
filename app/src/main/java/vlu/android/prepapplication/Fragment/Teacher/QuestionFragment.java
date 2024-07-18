package vlu.android.prepapplication.Fragment.Teacher;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;



import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.Collections;

import vlu.android.prepapplication.Adapter.RecyclerViewQuestionAdapter;
import vlu.android.prepapplication.Fragment.UpdateAccountDialogFragment;
import vlu.android.prepapplication.Model.Question;
import vlu.android.prepapplication.R;
import vlu.android.prepapplication.ViewModel.QuestionViewModel;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link QuestionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class QuestionFragment extends Fragment {
    private int subjectId;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private QuestionViewModel questionViewModel;
    private Button btnAccount;
    public QuestionFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SubjectFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static QuestionFragment newInstance(String param1, String param2) {
        QuestionFragment fragment = new QuestionFragment();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_question, container, false);

        Button btnBack = view.findViewById(R.id.btnBack);
        btnBack.setOnClickListener(v -> {
            FragmentManager fragmentManager = getParentFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.flTeacher, new SubjectFragment());
            fragmentTransaction.commit();
        });

        questionViewModel = new ViewModelProvider((requireActivity())).get(QuestionViewModel.class);
//        questionViewModel.insert(new Question("1 + 1 = ?", "3", "1", "0", "2", "2"));
        //questionViewModel.insert(new Question("Java được phát minh vào năm?", "1994", "1995", "1996", "2024", "1995"));
        RecyclerViewQuestionAdapter adapter = new RecyclerViewQuestionAdapter(questionViewModel);

        Bundle bundle = getArguments();
        if (bundle != null && (subjectId = bundle.getInt("subjectId", -1)) != -1) {
            questionViewModel.getAllQuestion(subjectId).observe(getViewLifecycleOwner(), adapter::updateQuestions);
        } else {
            Toast.makeText(requireContext(), "you are not allowed to be here", Toast.LENGTH_LONG).show();
            return view;
        }

        RecyclerView rcvQuestion = view.findViewById(R.id.rcvQuestion);
        rcvQuestion.setAdapter(adapter);

        RecyclerViewQuestionAdapter searchAdapter = new RecyclerViewQuestionAdapter(questionViewModel);
        EditText edtSearchByID = view.findViewById(R.id.edtSearchByID);

        edtSearchByID.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() == 0 && rcvQuestion.getAdapter() != adapter) {
                    rcvQuestion.setAdapter(adapter);
                    return;
                }
                int id = Integer.parseInt(charSequence.toString());
                if (rcvQuestion.getAdapter() != searchAdapter) {
                    rcvQuestion.setAdapter(searchAdapter);
                }
                LiveData<Question> result = questionViewModel.getQuestionByID(id, subjectId);
                result.observe(getViewLifecycleOwner(), new Observer<Question>() {
                    @Override
                    public void onChanged(Question question) {
                        if (question != null) {
                            searchAdapter.updateQuestions(Collections.singletonList(question));
                            return;
                        }
                        Toast.makeText(requireContext(), String.format(getString(
                                        R.string.cannot_find_question
                                ), id, subjectId),
                                Toast.LENGTH_LONG).show();
                        searchAdapter.updateQuestions(Collections.emptyList());
                        result.removeObserver(this);
                    }
                });
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        Button btnAddQuestion = view.findViewById(R.id.btnAddQuestion);
        btnAddQuestion.setOnClickListener(v -> {
            View dialogView = LayoutInflater.from(v.getContext()).
                    inflate(R.layout.dialog_add_question_layout, (ViewGroup) view.getRootView(), false);

            AlertDialog alertDialog = new AlertDialog.
                    Builder(requireContext()).
                    setTitle("New question").
                    setView(dialogView).
                    setPositiveButton("Save", null).
                    setNegativeButton("Cancel", (dialogInterface, i) -> dialogInterface.cancel()).
                    show();

            alertDialog.getButton(DialogInterface.BUTTON_POSITIVE).
                    setOnClickListener(view1 -> handleAddQuestion(dialogView, alertDialog));
        });

        return view;
    }

    private void handleAddQuestion(View dialogView, AlertDialog alertDialog) {
        EditText edtQuestionContent = dialogView.findViewById(R.id.edtQuestionContent);
        EditText edtAnswerA = dialogView.findViewById(R.id.edtAnswerA);
        EditText edtAnswerB = dialogView.findViewById(R.id.edtAnswerB);
        EditText edtAnswerC = dialogView.findViewById(R.id.edtAnswerC);
        EditText edtAnswerD = dialogView.findViewById(R.id.edtAnswerD);
        RadioGroup rdgCorrectAnswer = dialogView.findViewById(R.id.rdgCorrectAnswer);

        String content = edtQuestionContent.getText().toString();
        String answerA = edtAnswerA.getText().toString();
        String answerB = edtAnswerB.getText().toString();
        String answerC = edtAnswerC.getText().toString();
        String answerD = edtAnswerD.getText().toString();

        int correctAnswerId = rdgCorrectAnswer.getCheckedRadioButtonId();
        String answer = answerA;

        if (correctAnswerId == R.id.rdoAnswerB) {
            answer = answerB;
        } else if (correctAnswerId == R.id.rdoAnswerC) {
            answer = answerC;
        } else if (correctAnswerId == R.id.rdoAnswerD) {
            answer = answerD;
        }

//        questionViewModel.insert(new Question(content, answerA, answerB, answerC, answerD, answer, subjectId),
//                () -> requireActivity().
//                        runOnUiThread(() -> {
//                            Toast.makeText(requireContext(), "successfully add new question", Toast.LENGTH_LONG).show();
//                            alertDialog.dismiss();
//                        }),
//                s -> requireActivity().
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btnAccount = view.findViewById(R.id.btnQuestionAccount);
        btnAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpdateAccountDialogFragment updateAccountDialogFragment = new UpdateAccountDialogFragment(1);
                updateAccountDialogFragment.show(getActivity().getSupportFragmentManager(),null);
                updateAccountDialogFragment.setCancelable(false);
            }
        });

    }
}