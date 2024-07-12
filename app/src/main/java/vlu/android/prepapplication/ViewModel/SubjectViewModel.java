package vlu.android.prepapplication.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import vlu.android.prepapplication.Model.Question;
import vlu.android.prepapplication.Repository.Repository;

public class SubjectViewModel extends AndroidViewModel {

    private Repository repository;
    private LiveData<List<Question>> questionsLiveData;
    private LiveData<Question> questionLiveData;

    public SubjectViewModel(@NonNull Application application) {
        super(application);
        repository = new Repository(application);
    }

    public LiveData<List<Question>> getAllQuestionLiveData() {
        questionsLiveData = repository.getAllQuestion();
        return questionsLiveData;
    }

    public LiveData<Question> getQuestionLiveData(int id) {
        questionLiveData = repository.getQuestionByID(id);
        return questionLiveData;
    }

    public void insert(Question question) {
        repository.insert(question);
    }
}
