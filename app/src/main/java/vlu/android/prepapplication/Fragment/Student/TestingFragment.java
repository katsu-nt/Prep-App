package vlu.android.prepapplication.Fragment.Student;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import vlu.android.prepapplication.Fragment.Student.CustomDialogFragment.JoinDialogFragment;
import vlu.android.prepapplication.Model.Student;
import vlu.android.prepapplication.R;
import vlu.android.prepapplication.ViewModel.StudentViewModel;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TestingFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TestingFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    EditText edtSearhToJoin;
    private Student student;
    private StudentViewModel studentViewModel;

    public TestingFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TestingFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TestingFragment newInstance(String param1, String param2) {
        TestingFragment fragment = new TestingFragment();
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
        return inflater.inflate(R.layout.fragment_testing, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Intent intent = getActivity().getIntent();
        int idStudent = intent.getIntExtra("studentId",-1);
        addControl(view);
        studentViewModel = new ViewModelProvider((requireActivity())).get(StudentViewModel.class);
        studentViewModel.getStudentById(idStudent).observe(getViewLifecycleOwner(),item->{
            student = item;
        });
        edtSearhToJoin.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId == EditorInfo.IME_ACTION_DONE){
                    int idQuery = Integer.parseInt(edtSearhToJoin.getText().toString().trim());
                    JoinDialogFragment joinDialogFragment = new JoinDialogFragment("Join with 110","Make sure you want join class with ID 110?");
                    joinDialogFragment.show(getActivity().getSupportFragmentManager(), null);
                    joinDialogFragment.setCancelable(false);
                    return true;
                }
                return false;
            }
        });
    }

    void addControl(View view){
        edtSearhToJoin = view.findViewById(R.id.edtSearchToJoin);
    }

}