package vlu.android.prepapplication.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;


import vlu.android.prepapplication.Model.Subject;
import vlu.android.prepapplication.R;

public class RecyclerViewSubjectAdapter extends RecyclerView.Adapter<RecyclerViewSubjectAdapter.SubjectViwHolder> {
    private List<Subject> subjects;
    public RecyclerViewSubjectAdapter() {this.subjects = new ArrayList<>();}
    public RecyclerViewSubjectAdapter(List<Subject> subjects) {this.subjects = subjects;}

    @NonNull
    @Override
    public SubjectViwHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_subject_item_layout, parent, false);
        return new SubjectViwHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SubjectViwHolder holder, int position) {
        Subject subject = subjects.get(position);
        holder.getTvSubjectNameOrID().setText(subject.getName() + " - " + subject.getSubjectId());
//        holder.getTvSubjectNumberOfQuestion().setText("Number of questions: " + /* insert logic to get number of questions */);
        holder.getTvAbout().setText(subject.getDescription());
    }

    @Override
    public int getItemCount() {
        return subjects.size();
    }

    public void updateSubjects(List<Subject> subjects) {
        this.subjects = subjects;
        notifyDataSetChanged();
    }

    public static class SubjectViwHolder extends RecyclerView.ViewHolder {
        private final TextView tvSubjectNameOrID, tvSubjectNumberOfQuestion, tvAbout;
        public SubjectViwHolder(@NonNull View itemView) {
            super(itemView);
            tvSubjectNameOrID = itemView.findViewById(R.id.tvSubjectNameOrID);
            tvSubjectNumberOfQuestion = itemView.findViewById(R.id.tvNumberOfQuestion);
            tvAbout = itemView.findViewById(R.id.tvAbout);
        }

        public TextView getTvSubjectNameOrID(){
            return tvSubjectNameOrID;
        }

        public TextView getTvSubjectNumberOfQuestion(){
            return tvSubjectNumberOfQuestion;
        }

        public TextView getTvAbout(){
            return tvAbout;
        }
    }
}
