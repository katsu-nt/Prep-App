package vlu.android.prepapplication.Fragment.Teacher;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Collections;
import java.util.List;

import vlu.android.prepapplication.Adapter.RecyclerViewSubjectAdapter;
import vlu.android.prepapplication.Model.Subject;
import vlu.android.prepapplication.R;
import vlu.android.prepapplication.ViewModel.SubjectViewModel;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FindFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FindFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private SubjectViewModel subjectViewModel;

    public FindFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FindFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FindFragment newInstance(String param1, String param2) {
        FindFragment fragment = new FindFragment();
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
        View view = inflater.inflate(R.layout.fragment_find, container, false);

        RecyclerView recySubject = view.findViewById(R.id.recySubject);
        subjectViewModel = new ViewModelProvider((requireActivity())).get(SubjectViewModel.class);
//        subjectViewModel.insert(new Subject("Lập trình hướng đôi tượng", "Học C#"));
//        subjectViewModel.insert(new Subject("Lập trình di động", "Học Java"));

        RecyclerViewSubjectAdapter adapter = new RecyclerViewSubjectAdapter();
        recySubject.setAdapter(adapter);

        subjectViewModel.getAllSubjectLiveData().observe(getViewLifecycleOwner(), adapter::updateSubjects);

        RecyclerViewSubjectAdapter searchAdapter = new RecyclerViewSubjectAdapter();
        EditText edtSearchSubj = view.findViewById(R.id.edtSearchSubj);
        edtSearchSubj.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() == 0 && recySubject.getAdapter() != adapter){
                    recySubject.setAdapter(adapter);
                    return;
                }

                try {
                    // Tìm kiếm theo ID
                    int id = Integer.parseInt(charSequence.toString());
                    LiveData<Subject> result = subjectViewModel.getSubjectLiveData(id);
                    result.observe(getViewLifecycleOwner(), new Observer<Subject>() {
                        @Override
                        public void onChanged(Subject subject) {
                            if (subject != null) {
                                searchAdapter.updateSubjects(Collections.singletonList(subject));
                            } else {
                                Toast.makeText(getContext(), "Cannot find subject with ID " + id, Toast.LENGTH_LONG).show();
                                searchAdapter.updateSubjects(Collections.emptyList());
                            }
                            result.removeObserver(this);
                        }
                    });
                } catch (NumberFormatException e) {
                    // Tìm kiếm theo tên
                    String name = charSequence.toString();
                    LiveData<List<Subject>> result = subjectViewModel.getSubjectsLiveData(name);
                    result.observe(getViewLifecycleOwner(), new Observer<List<Subject>>() {
                        @Override
                        public void onChanged(List<Subject> subjects) {
                            if (subjects != null && !subjects.isEmpty()) {
                                searchAdapter.updateSubjects(subjects);
                            } else {
                                Toast.makeText(getContext(), "Cannot find subjects with name " + name, Toast.LENGTH_LONG).show();
                                searchAdapter.updateSubjects(Collections.emptyList());
                            }
                            result.removeObserver(this);
                        }
                    });
                }

                if (recySubject.getAdapter() != searchAdapter) {
                    recySubject.setAdapter(searchAdapter);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        return view;
    }
}