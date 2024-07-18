package vlu.android.prepapplication.Adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Arrays;
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

        holder.getTvQuestionID().setText(questionId);
        holder.getTvQuestionContent().setText(questionContent);

        View itemView = holder.itemView;
        itemView.setOnClickListener(view -> onItemClick(view, question));
        itemView.setOnLongClickListener(view -> onItemLongClick(view, question));
    }

    private void onItemClick(View view, Question question) {
        new AlertDialog.
                Builder(view.getContext()).
                setTitle(view.getContext().getString(R.string.id_with_value, question.getQuestionId())).
                setView(getQuestionDetailDialogView(view.getContext(), (ViewGroup) view.getParent(), question)).
                setPositiveButton("Update", (dialogInterface, i) -> {
                    View dialogView = getUpdateDialogView(view.getContext(), (ViewGroup) view.getParent(), question);

                    AlertDialog alertDialog = new AlertDialog.
                            Builder(view.getContext()).
                            setTitle("Update question").
                            setView(dialogView).
                            setPositiveButton("Save", null).
                            setNegativeButton("Cancel", (dialogInterface1, i1) -> dialogInterface1.cancel()).
                            show();

                    alertDialog.getButton(DialogInterface.BUTTON_POSITIVE).
                            setOnClickListener(view1 -> handleUpdateQuestion(dialogView, alertDialog, question));
                }).
                setNegativeButton("Cancel", (dialogInterface, i) -> dialogInterface.cancel()).
                create().
                show();
    }

    private void handleUpdateQuestion(View dialogView, AlertDialog alertDialog, Question question) {
        EditText edtQuestionContent = dialogView.findViewById(R.id.edtQuestionContent);
        EditText edtAnswerA = dialogView.findViewById(R.id.edtAnswerA);
        EditText edtAnswerB = dialogView.findViewById(R.id.edtAnswerB);
        EditText edtAnswerC = dialogView.findViewById(R.id.edtAnswerC);
        EditText edtAnswerD = dialogView.findViewById(R.id.edtAnswerD);
        RadioGroup rdgCorrectAnswer = dialogView.findViewById(R.id.rdgCorrectAnswer);

        String content = edtQuestionContent.getText().toString();
        String answerA = edtAnswerA.getText().toString();
        String answerB = edtAnswerB.getText().toString();
        String answerC = edtAnswerC.getText().toString();
        String answerD = edtAnswerD.getText().toString();

        int correctAnswerId = rdgCorrectAnswer.getCheckedRadioButtonId();
        String answer = answerA;

        if (correctAnswerId == R.id.rdoAnswerB) {
            answer = answerB;
        } else if (correctAnswerId == R.id.rdoAnswerC) {
            answer = answerC;
        } else if (correctAnswerId == R.id.rdoAnswerD) {
            answer = answerD;
        }

        Context context = dialogView.getContext();
        Activity activity = (Activity) context;

        question.setContent(content);
        question.setAnswerA(answerA);
        question.setAnswerB(answerB);
        question.setAnswerC(answerC);
        question.setAnswerD(answerD);
        question.setCorrectAnswer(answer);

        questionViewModel.update(question,
                () -> activity.runOnUiThread(() -> {
                    Toast.makeText(context, "successfully update question", Toast.LENGTH_LONG).show();
                    alertDialog.dismiss();
                }),
                s -> activity.
                        runOnUiThread(() -> Toast.makeText(context, s, Toast.LENGTH_LONG).show())
        );
    }

    private View getQuestionDetailDialogView(Context context, ViewGroup parent, Question question) {
        String questionContent = question.getContent();

        String answerA = question.getAnswerA();
        String answerB = question.getAnswerB();
        String answerC = question.getAnswerC();
        String answerD = question.getAnswerD();

        View dialogView = LayoutInflater.
                from(context).
                inflate(R.layout.dialog_question_detail_layout,
                        parent, false);

        int red = dialogView.getResources().getColor(R.color.red, null);
        int green = dialogView.getResources().getColor(R.color.green, null);


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

        return dialogView;
    }

    private View getUpdateDialogView(Context context, ViewGroup parent, Question question) {
        View dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_add_question_layout, parent, false);

        String answerA = question.getAnswerA();
        String answerB = question.getAnswerB();
        String answerC = question.getAnswerC();
        String answerD = question.getAnswerD();
        String answer = question.getCorrectAnswer();

        EditText edtQuestionContent = dialogView.findViewById(R.id.edtQuestionContent);
        edtQuestionContent.setText(question.getContent());

        EditText edtAnswerA = dialogView.findViewById(R.id.edtAnswerA);
        edtAnswerA.setText(answerA);

        EditText edtAnswerB = dialogView.findViewById(R.id.edtAnswerB);
        edtAnswerB.setText(answerB);

        EditText edtAnswerC = dialogView.findViewById(R.id.edtAnswerC);
        edtAnswerC.setText(answerC);

        EditText edtAnswerD = dialogView.findViewById(R.id.edtAnswerD);
        edtAnswerD.setText(answerD);

        RadioGroup rdgCorrectAnswer = dialogView.findViewById(R.id.rdgCorrectAnswer);

        if (answer.equals(answerB)) {
            rdgCorrectAnswer.check(R.id.rdoAnswerB);
        } else if (answer.equals(answerC)) {
            rdgCorrectAnswer.check(R.id.rdoAnswerC);
        } else if (answer.equals(answerD)) {
            rdgCorrectAnswer.check(R.id.rdoAnswerD);
        }

        return dialogView;
    }

    private boolean onItemLongClick(View view, Question question) {
        return new AlertDialog.Builder(view.getContext()).
                setTitle(view.getContext().getString(R.string.confirm_delete_question_message, question.getQuestionId())).
                setPositiveButton("Confirm", (dialogInterface, i) -> questionViewModel.delete(question)).
                setNegativeButton("Cancel", (dialogInterface, i) -> dialogInterface.cancel()).
                show() != null;
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
