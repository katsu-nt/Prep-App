package vlu.android.prepapplication.Fragment.Teacher;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
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

    private SubjectViewModel subjectViewModel;
    private RecyclerViewSubjectAdapter adapter;
    private RecyclerView recySubject;
    private EditText edtSearchSubj;

    public FindFragment() {
        // Required empty public constructor
    }

    public static FindFragment newInstance() {
        return new FindFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_find, container, false);

        recySubject = view.findViewById(R.id.recySubject);
        edtSearchSubj = view.findViewById(R.id.edtSearchSubj);

        subjectViewModel = new ViewModelProvider(requireActivity()).get(SubjectViewModel.class);

        adapter = new RecyclerViewSubjectAdapter(subjectViewModel); // Fix constructor
        recySubject.setLayoutManager(new LinearLayoutManager(getContext()));
        recySubject.setAdapter(adapter);

        subjectViewModel.getAllSubjectLiveData().observe(getViewLifecycleOwner(), adapter::updateSubjects);

        edtSearchSubj.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() == 0) {
                    subjectViewModel.getAllSubjectLiveData().observe(getViewLifecycleOwner(), adapter::updateSubjects);
                } else {
                    searchSubjects(charSequence.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {}
        });

        adapter.setOnItemClickListener(new RecyclerViewSubjectAdapter.onItemClickListener() {
            @Override
            public void onItemClick(Subject subject) {
                FragmentManager fragmentManager = getParentFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.flTeacher, DetailedSubjectFragment.newInstance(subject.getName(), String.valueOf(subject.getSubjectId()), subject.getDescription()));
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        return view;
    }

    private void searchSubjects(String query) {
        try {
            int id = Integer.parseInt(query);
            LiveData<Subject> result = subjectViewModel.getSubjectLiveData(id);
            result.observe(getViewLifecycleOwner(), new Observer<Subject>() {
                @Override
                public void onChanged(Subject subject) {
                    if (subject != null) {
                        adapter.updateSubjects(Collections.singletonList(subject));
                    } else {
                        Toast.makeText(getContext(), "Cannot find subject with ID " + id, Toast.LENGTH_LONG).show();
                        adapter.updateSubjects(Collections.emptyList());
                    }
                    result.removeObserver(this);
                }
            });
        } catch (NumberFormatException e) {
            String name = query;
            LiveData<List<Subject>> result = subjectViewModel.getSubjectsLiveData(name);
            result.observe(getViewLifecycleOwner(), new Observer<List<Subject>>() {
                @Override
                public void onChanged(List<Subject> subjects) {
                    if (subjects != null && !subjects.isEmpty()) {
                        adapter.updateSubjects(subjects);
                    } else {
                        Toast.makeText(getContext(), "Cannot find subjects with name " + name, Toast.LENGTH_LONG).show();
                        adapter.updateSubjects(Collections.emptyList());
                    }
                    result.removeObserver(this);
                }
            });
        }
    }
}
