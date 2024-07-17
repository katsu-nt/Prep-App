package vlu.android.prepapplication.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.function.Consumer;

import vlu.android.prepapplication.Model.Question;
import vlu.android.prepapplication.Repository.Repository;

public class QuestionViewModel extends AndroidViewModel {

    private Repository repository;
    private LiveData<List<Question>> questionsLiveData;
    private LiveData<List<Question>> subjectQuestionsLiveData;
    private LiveData<Question> questionLiveData;

    public QuestionViewModel(@NonNull Application application) {
        super(application);
        repository = new Repository(application);
    }

    public LiveData<List<Question>> getAllQuestionLiveData() {
        questionsLiveData = repository.getAllQuestion();
        return questionsLiveData;
    }

    public LiveData<List<Question>> getQuestionBySubjectID(int subjectId) {
        subjectQuestionsLiveData = repository.getQuestionsBySubjectID(subjectId);
        return subjectQuestionsLiveData;
    }


    public LiveData<Question> getQuestionLiveData(int id) {
        questionLiveData = repository.getQuestionByID(id);
        return questionLiveData;
    }

    public void insert(Question question, Runnable onSuccess, Consumer<String> onFailure) {
        repository.insert(question, onSuccess, onFailure);
    }

    public boolean isCorrectAnswer(Question question, String answer) {
        return question.getCorrectAnswer().equals(answer);
    }

    public void delete(Question question) {
        repository.delete(question);
    }
}
