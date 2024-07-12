package vlu.android.prepapplication.Fragment.Teacher;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import java.security.KeyStore;
import java.util.ArrayList;
import java.util.Arrays;

import vlu.android.prepapplication.Adapter.RecyclerViewQuestionAdapter;
import vlu.android.prepapplication.Model.Question;
import vlu.android.prepapplication.R;
import vlu.android.prepapplication.Repository.Repository;
import vlu.android.prepapplication.ViewModel.SubjectViewModel;
import vlu.android.prepapplication.ViewModel.TeacherViewModel;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SubjectFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SubjectFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private SubjectViewModel subjectViewModel;

    public SubjectFragment() {
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
    public static SubjectFragment newInstance(String param1, String param2) {
        SubjectFragment fragment = new SubjectFragment();
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
        View view = inflater.inflate(R.layout.fragment_subject, container, false);

        RecyclerView rcvQuestion = view.findViewById(R.id.rcvQuestion);
        subjectViewModel = new ViewModelProvider((requireActivity())).get(SubjectViewModel.class);
//        subjectViewModel.insert(new Question("1 + 1 = ?", "3", "1", "0", "2", "2"));
//        subjectViewModel.insert(new Question("Java được phát minh vào năm?", "1994", "1995", "1996", "2024", "1995"));
        rcvQuestion.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        rcvQuestion.setLayoutManager(new LinearLayoutManager(getContext()));
        rcvQuestion.setItemAnimator(new DefaultItemAnimator());
        subjectViewModel.getAllQuestionLiveData().observe(getViewLifecycleOwner(), questions ->
                rcvQuestion.setAdapter(new RecyclerViewQuestionAdapter(questions))
        );

        // TODO
        EditText edtSearchByID = view.findViewById(R.id.edtSearchByID);
        edtSearchByID.setOnKeyListener((view1, keycode, keyEvent) -> {
            return false;
        });
        // TODO

        return view;
    }
}