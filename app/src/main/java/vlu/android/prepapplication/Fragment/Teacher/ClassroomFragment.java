    package vlu.android.prepapplication.Fragment.Teacher;

    import android.widget.Toast;
    import androidx.appcompat.app.AlertDialog;

    import android.content.DialogInterface;
    import android.os.Bundle;
    import android.text.Editable;
    import android.text.TextWatcher;
    import android.view.LayoutInflater;
    import android.view.View;
    import android.view.ViewGroup;
    import android.widget.Button;
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
    import vlu.android.prepapplication.ViewModel.QuestionViewModel;
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
           // classroomViewModel.insert(new Classroom("Lop 01","Lop hoc 01"));
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
            Button btnAdd = view.findViewById(R.id.btnAddClassroom);
            btnAdd.setOnClickListener(v -> {
                View dialog = LayoutInflater.from(v.getContext()).inflate(R.layout.dialog_add_classroom_layout, (ViewGroup) view.getRootView(), false);
                EditText edtNameClassroom = dialog.findViewById(R.id.edtClassName);
                EditText edtDescription = dialog.findViewById(R.id.edtDescription);

                AlertDialog alertDialog = new AlertDialog.Builder(requireContext())

                        .setView(dialog)
                        .setPositiveButton("Save", null)
                        .setNegativeButton("Cancel", (dialogInterface, i) -> dialogInterface.cancel())
                        .show();

                alertDialog.getButton(DialogInterface.BUTTON_POSITIVE).setOnClickListener(view1 -> {
                    String nameClassroom = edtNameClassroom.getText().toString();
                    String description = edtDescription.getText().toString();
                    classroomViewModel.insert(new Classroom(nameClassroom, description),
                            () -> requireActivity().runOnUiThread(() -> {
                                Toast.makeText(requireContext(), "Successfully added new classroom", Toast.LENGTH_LONG).show();
                                alertDialog.dismiss();
                            }),
                            errorMessage -> requireActivity().runOnUiThread(() ->
                                Toast.makeText(requireContext(), errorMessage, Toast .LENGTH_LONG).show())
                            );
                });
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
