package vlu.android.prepapplication.Adapter;

import android.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import vlu.android.prepapplication.Model.Question;
import vlu.android.prepapplication.R;
import vlu.android.prepapplication.ViewModel.QuestionViewModel;

public class RecyclerViewQuestionAdapter extends RecyclerView.Adapter<RecyclerViewQuestionAdapter.QuestionViewHolder> {

    private List<Question> questions;
    private final QuestionViewModel questionViewModel;

    public RecyclerViewQuestionAdapter(QuestionViewModel questionViewModel) {
        this.questions = new ArrayList<>();
        this.questionViewModel = questionViewModel;
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

        String questionId = String.valueOf(question.getQuestionId());
        String questionContent = question.getContent();
        String answerA = question.getAnswerA();
        String answerB = question.getAnswerB();
        String answerC = question.getAnswerC();
        String answerD = question.getAnswerD();

        holder.getTvQuestionID().setText(questionId);
        holder.getTvQuestionContent().setText(questionContent);

        View itemView = holder.itemView;

        int red = itemView.getResources().getColor(R.color.red, null);
        int green = itemView.getResources().getColor(R.color.green, null);

        itemView.setOnClickListener(view -> {
            View dialogView = LayoutInflater.
                    from(view.getContext()).
                    inflate(R.layout.dialog_question_detail_layout,
                            (ViewGroup) itemView.getRootView(), false);

            TextView tvQuestionContent = dialogView.findViewById(R.id.tvQuestionContent);
            Button btnAnswerA = dialogView.findViewById(R.id.btnAnswerA);
            Button btnAnswerB = dialogView.findViewById(R.id.btnAnswerB);
            Button btnAnswerC = dialogView.findViewById(R.id.btnAnswerC);
            Button btnAnswerD = dialogView.findViewById(R.id.btnAnswerD);
            Function<String, Integer> getColor = s -> questionViewModel.isCorrectAnswer(question, s) ? green : red;

            tvQuestionContent.setText(questionContent);

            btnAnswerA.setText(dialogView.getContext().getString(R.string.answer_a, answerA));
            btnAnswerA.setBackgroundColor(getColor.apply(answerA));

            btnAnswerB.setText(dialogView.getContext().getString(R.string.answer_b, answerB));
            btnAnswerB.setBackgroundColor(getColor.apply(answerB));

            btnAnswerC.setText(dialogView.getContext().getString(R.string.answer_c, answerC));
            btnAnswerC.setBackgroundColor(getColor.apply(answerC));

            btnAnswerD.setText(dialogView.getContext().getString(R.string.answer_d, answerD));
            btnAnswerD.setBackgroundColor(getColor.apply(answerD));

            new AlertDialog.
                    Builder(view.getContext()).
                    setTitle(questionId).
                    setView(dialogView).
                    setPositiveButton("Confirm", (dialogInterface, i) -> dialogInterface.cancel()).
                    create().
                    show();
        });

        itemView.setOnLongClickListener(view -> {
            new AlertDialog.Builder(view.getContext()).
                    setTitle(String.format(
                            "Are you sure you want to delete question with id: %s",
                            questionId)
                    ).
                    setPositiveButton("Confirm", (dialogInterface, i) -> questionViewModel.delete(question)).
                    setNegativeButton("Cancel", (dialogInterface, i) -> dialogInterface.cancel()).
                    show();
            return true;
        });
    }

    @Override
    public int getItemCount() {
        return questions.size();
    }

    public void updateQuestions(List<Question> questions) {
        this.questions = questions;
        notifyDataSetChanged();
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
