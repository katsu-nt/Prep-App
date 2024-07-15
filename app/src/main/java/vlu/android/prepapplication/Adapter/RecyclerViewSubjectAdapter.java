package vlu.android.prepapplication.Adapter;

import android.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Collections;
import java.util.List;

import vlu.android.prepapplication.Model.Subject;
import vlu.android.prepapplication.R;
import vlu.android.prepapplication.ViewModel.SubjectViewModel;

public class RecyclerViewSubjectAdapter extends RecyclerView.Adapter<RecyclerViewSubjectAdapter.SubjectViewHolder> {
    private List<Subject> subjects = Collections.emptyList();
    private onItemClickListener listener;
    private final SubjectViewModel subjectViewModel;

    public RecyclerViewSubjectAdapter(SubjectViewModel subjectViewModel) {
        this.subjectViewModel = subjectViewModel;
    }

    public interface onItemClickListener {
        void onItemClick(Subject subject);
    }

    public void setOnItemClickListener(onItemClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public SubjectViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_subject_item_layout, parent, false);
        return new SubjectViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SubjectViewHolder holder, int position) {
        Subject subject = subjects.get(position);

        holder.getTvSubjectNameOrID().setText(subject.getName() + " - " + subject.getSubjectId());
        holder.getTvAbout().setText(subject.getDescription());
        holder.bind(subject, listener);

        holder.itemView.setOnLongClickListener(view -> {
            new AlertDialog.Builder(holder.itemView.getContext())
                    .setTitle("Delete Subject")
                    .setMessage("Do you really want to delete this subject?")
                    .setPositiveButton("Yes", (dialog, which) -> subjectViewModel.delete(subject))
                    .setNegativeButton("No", null)
                    .show();
            return true;
        });
    }

    @Override
    public int getItemCount() {
        return subjects.size();
    }

    public void updateSubjects(List<Subject> newSubjects) {
        subjects = newSubjects;
        notifyDataSetChanged();
    }

    public static class SubjectViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvSubjectNameOrID;
        private final TextView tvAbout;

        public SubjectViewHolder(@NonNull View itemView) {
            super(itemView);
            tvSubjectNameOrID = itemView.findViewById(R.id.tvSubjectNameOrID);
            tvAbout = itemView.findViewById(R.id.tvAbout);
        }

        public TextView getTvSubjectNameOrID() {
            return tvSubjectNameOrID;
        }

        public TextView getTvAbout() {
            return tvAbout;
        }

        public void bind(Subject subject, onItemClickListener listener) {
            itemView.setOnClickListener(view -> {
                if (listener != null && getAdapterPosition() != RecyclerView.NO_POSITION) {
                    listener.onItemClick(subject);
                }
            });
        }
    }
}
