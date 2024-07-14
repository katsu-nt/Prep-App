package vlu.android.prepapplication.Fragment.Teacher;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import vlu.android.prepapplication.Model.Subject;
import vlu.android.prepapplication.R;

public class DetailedSubjectFragment extends Fragment {

    private static final String ARG_SUBJECT_NAME = "subject_name";
    private static final String ARG_SUBJECT_ID = "subject_id";
    private static final String ARG_SUBJECT_DESCRIPTION = "subject_description";

    private String subjectName;
    private String subjectId;
    private String subjectDescription;

    public DetailedSubjectFragment() {
        // Required empty public constructor
    }

    public static DetailedSubjectFragment newInstance(String subjectName, String subjectId, String subjectDescription) {
        DetailedSubjectFragment fragment = new DetailedSubjectFragment();
        Bundle args = new Bundle();
        args.putString(ARG_SUBJECT_NAME, subjectName);
        args.putString(ARG_SUBJECT_ID, subjectId);
        args.putString(ARG_SUBJECT_DESCRIPTION, subjectDescription);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            subjectName = getArguments().getString(ARG_SUBJECT_NAME);
            subjectId = getArguments().getString(ARG_SUBJECT_ID);
            subjectDescription = getArguments().getString(ARG_SUBJECT_DESCRIPTION);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detailed_subject, container, false);

        TextView tvSubjectNameOrID = view.findViewById(R.id.tvSubjectNameOrID);
        TextView tvSubjectNumber = view.findViewById(R.id.tvNumberOfQuestion);
        TextView tvAbout = view.findViewById(R.id.tvAbout);

        tvSubjectNameOrID.setText(subjectName + " - " + subjectId);
        tvAbout.setText(subjectDescription);

        return view;
    }
}
