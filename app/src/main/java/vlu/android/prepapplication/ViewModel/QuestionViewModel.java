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

    public LiveData<List<Question>> getAllQuestion(int subjectId) {
        subjectQuestionsLiveData = repository.getAllQuestion(subjectId);
        return subjectQuestionsLiveData;
    }


    public LiveData<Question> getQuestionByID(int id, int subjectId) {
        questionLiveData = repository.getQuestionByID(id, subjectId);
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
