package vlu.android.prepapplication.Fragment.Teacher;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Collections;

import vlu.android.prepapplication.Adapter.RecyclerViewQuestionAdapter;
import vlu.android.prepapplication.Model.Question;
import vlu.android.prepapplication.R;
import vlu.android.prepapplication.ViewModel.QuestionViewModel;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link QuestionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class QuestionFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private QuestionViewModel questionViewModel;

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

        RecyclerView rcvQuestion = view.findViewById(R.id.rcvQuestion);
        questionViewModel = new ViewModelProvider((requireActivity())).get(QuestionViewModel.class);
//        questionViewModel.insert(new Question("1 + 1 = ?", "3", "1", "0", "2", "2"));
        //questionViewModel.insert(new Question("Java được phát minh vào năm?", "1994", "1995", "1996", "2024", "1995"));
        RecyclerViewQuestionAdapter adapter = new RecyclerViewQuestionAdapter(questionViewModel);
        rcvQuestion.setAdapter(adapter);
        questionViewModel.getAllQuestionLiveData().observe(getViewLifecycleOwner(), adapter::updateQuestions);

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
                LiveData<Question> result = questionViewModel.getQuestionLiveData(id);
                result.observe(getViewLifecycleOwner(), new Observer<Question>() {
                    @Override
                    public void onChanged(Question question) {
                        if (question != null) {
                            searchAdapter.updateQuestions(Collections.singletonList(question));
                            return;
                        }
                        Toast.makeText(requireContext(), "Cannot find question with id " + id, Toast.LENGTH_LONG).show();
                        searchAdapter.updateQuestions(Collections.emptyList());
                        result.removeObserver(this);
                    }
                });
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        Button btnBack = view.findViewById(R.id.btnBack);
        btnBack.setOnClickListener(v -> {
            FragmentManager fragmentManager = getParentFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.flTeacher, new SubjectFragment());
            fragmentTransaction.commit();
        });

        Button btnAddQuestion = view.findViewById(R.id.btnAddQuestion);
        btnAddQuestion.setOnClickListener(v -> {
            View dialog = LayoutInflater.from(v.getContext()).inflate(R.layout.dialog_add_question_layout, (ViewGroup) view.getRootView(), false);
            EditText edtQuestionContent = dialog.findViewById(R.id.edtQuestionContent);
            EditText edtAnswerA = dialog.findViewById(R.id.edtAnswerA);
            EditText edtAnswerB = dialog.findViewById(R.id.edtAnswerB);
            EditText edtAnswerC = dialog.findViewById(R.id.edtAnswerC);
            EditText edtAnswerD = dialog.findViewById(R.id.edtAnswerD);
            RadioGroup rdgCorrectAnswer = dialog.findViewById(R.id.rdgCorrectAnswer);

            AlertDialog alertDialog = new AlertDialog.
                    Builder(requireContext()).
                    setTitle("New question").
                    setView(dialog).
                    setPositiveButton("Save", null).
                    setNegativeButton("Cancel", (dialogInterface, i) -> dialogInterface.cancel()).
                    show();

            alertDialog.getButton(DialogInterface.BUTTON_POSITIVE).setOnClickListener(view1 -> {
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

                questionViewModel.insert(new Question(content, answerA, answerB, answerC, answerD, answer),
                        () -> requireActivity().runOnUiThread(() -> {
                            Toast.makeText(requireContext(), "successfully add new question", Toast.LENGTH_LONG).show();
                            alertDialog.dismiss();
                        }),
                        s -> requireActivity().runOnUiThread(() -> Toast.makeText(requireContext(), s, Toast.LENGTH_LONG).show()));
            });
        });

        return view;
    }
}