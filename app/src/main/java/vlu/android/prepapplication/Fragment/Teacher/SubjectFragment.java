package vlu.android.prepapplication.Fragment.Teacher;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.Collections;
import java.util.List;

import vlu.android.prepapplication.Adapter.RecyclerViewSubjectAdapter;
import vlu.android.prepapplication.Fragment.UpdateAccountDialogFragment;
import vlu.android.prepapplication.Model.Subject;
import vlu.android.prepapplication.R;
import vlu.android.prepapplication.ViewModel.SubjectViewModel;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SubjectFragment#newInstance} factory method to
 * create an instance of this fragment.
 */


public class SubjectFragment extends Fragment {

    private SubjectViewModel subjectViewModel;
    private RecyclerViewSubjectAdapter adapter;
    private RecyclerView recySubject;
    private EditText edtSearchSubj;
    private ImageView btnAccount;
    public SubjectFragment() {
        // Required empty public constructor
    }

    public static SubjectFragment newInstance() {
        return new SubjectFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_subject, container, false);

        recySubject = view.findViewById(R.id.recySubject);
        edtSearchSubj = view.findViewById(R.id.edtSearchSubj);

        subjectViewModel = new ViewModelProvider(requireActivity()).get(SubjectViewModel.class);

        adapter = new RecyclerViewSubjectAdapter(subjectViewModel);
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
                fragmentTransaction.replace(R.id.flTeacher, new QuestionFragment());
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        Button btnCreateSub = view.findViewById(R.id.btnCreateSub);
        btnCreateSub.setOnClickListener(v-> {
            View dialog = LayoutInflater.from(getContext()).inflate(R.layout.dialog_add_subject_layout, (ViewGroup) view.getRootView(), false);
            EditText edtCreateName = dialog.findViewById(R.id.edtCreateName);
            EditText edtCreateDescription = dialog.findViewById(R.id.edtCreateDescription);

            new AlertDialog.Builder(view.getContext()).
                    setView(dialog).setPositiveButton("Add", ((dialogInterface, i) -> {
                        String name = edtCreateName.getText().toString();
                        String description = edtCreateDescription.getText().toString();

                        if (name.isEmpty()){
                            Toast.makeText(getContext(), "Missing name subject content", Toast.LENGTH_LONG).show();
                            return;
                        }

                        if (description.isEmpty()){
                            Toast.makeText(getContext(), "Missing description subject content", Toast.LENGTH_LONG).show();
                            return;
                        }

                        subjectViewModel.insert(new Subject(name, description));
                        Toast.makeText(getContext(), "Successfully insert new subject", Toast.LENGTH_LONG).show();
                        dialogInterface.cancel();
                    })).setNegativeButton("Cancel", ((dialogInterface, i) -> dialogInterface.cancel())).create().show();
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

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btnAccount = view.findViewById(R.id.btnSubjectAccount);
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
