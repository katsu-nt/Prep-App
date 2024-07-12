package vlu.android.prepapplication.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import vlu.android.prepapplication.Model.Question;
import vlu.android.prepapplication.R;

public class RecyclerViewQuestionAdapter extends RecyclerView.Adapter<RecyclerViewQuestionAdapter.QuestionViewHolder> {

    private final List<Question> questions;

    public RecyclerViewQuestionAdapter(List<Question> questions) {
        this.questions = questions;
    }

    @NonNull
    @Override
    public QuestionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_question_item_layout, parent, false);
        return new QuestionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull QuestionViewHolder holder, int position) {
        Question question = questions.get(position);
        holder.getTvQuestionID().setText(String.valueOf(question.getQuestionId()));
        holder.getTvQuestionContent().setText(question.getContent());
    }

    @Override
    public int getItemCount() {
        return questions.size();
    }

    public static class QuestionViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvQuestionID, tvQuestionContent;

        public QuestionViewHolder(@NonNull View itemView) {
            super(itemView);
            tvQuestionID = itemView.findViewById(R.id.tvQuestionID);
            tvQuestionContent = itemView.findViewById(R.id.tvQuestionContent);
        }

        public TextView getTvQuestionID() {
            return tvQuestionID;
        }

        public TextView getTvQuestionContent() {
            return tvQuestionContent;
        }
    }
}
