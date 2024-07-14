package vlu.android.prepapplication.Fragment.Teacher;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import java.util.Collections;

import vlu.android.prepapplication.Adapter.GridViewClassroomAdapter;
import vlu.android.prepapplication.Model.Classroom;
import vlu.android.prepapplication.Model.Teacher;
import vlu.android.prepapplication.R;
import vlu.android.prepapplication.ViewModel.ClassroomViewModel;
import vlu.android.prepapplication.ViewModel.TeacherViewModel;

public class ClassroomFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    private TeacherViewModel teacherViewModel;
    private Teacher teacher;
    private TextView txtTeacher;
    private ClassroomViewModel classroomViewModel;

    public ClassroomFragment() {
        // Required empty public constructor
    }

    public static ClassroomFragment newInstance(String param1, String param2) {
        ClassroomFragment fragment = new ClassroomFragment();
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
        View view = inflater.inflate(R.layout.fragment_classroom, container, false);
        GridView grVClassroom = view.findViewById(R.id.grVClassroom);
        classroomViewModel = new ViewModelProvider(requireActivity()).get(ClassroomViewModel.class);

        GridViewClassroomAdapter adapter = new GridViewClassroomAdapter(getContext(), classroomViewModel);
        grVClassroom.setAdapter(adapter);
        classroomViewModel.getAllClassromLiveData().observe(getViewLifecycleOwner(), adapter::updateClassroom);

        GridViewClassroomAdapter searchAdapter = new GridViewClassroomAdapter(getContext(), classroomViewModel);
        EditText edtSearchByID = view.findViewById(R.id.edtSearchByID);
        edtSearchByID.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() == 0 && grVClassroom.getAdapter() != adapter) {
                    grVClassroom.setAdapter(adapter);
                    return;
                }
                try {
                    int id = Integer.parseInt(charSequence.toString());
                    if (grVClassroom.getAdapter() != searchAdapter) {
                        grVClassroom.setAdapter(searchAdapter);
                    }
                    LiveData<Classroom> result = classroomViewModel.getClassroomLiveData(id);
                    result.observe(getViewLifecycleOwner(), new Observer<Classroom>() {
                        @Override
                        public void onChanged(Classroom classroom) {
                            if (classroom != null) {
                                searchAdapter.updateClassroom(Collections.singletonList(classroom));
                            } else {
                                Toast.makeText(getContext(), "Cannot find classroom with id " + id, Toast.LENGTH_LONG).show();
                                searchAdapter.updateClassroom(Collections.emptyList());
                            }
                            result.removeObserver(this);
                        }
                    });
                } catch (NumberFormatException e) {
                    Toast.makeText(getContext(), "Invalid ID format", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {}
        });

        return view;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        teacherViewModel = new ViewModelProvider(requireActivity()).get(TeacherViewModel.class);
        teacherViewModel.insert(new Teacher("ben", "ben", "ben"));
    }
}
