package vlu.android.prepapplication.Model;

import androidx.room.Entity;
import androidx.room.ForeignKey;

@Entity(primaryKeys = {"questionId","examId","studentId"},foreignKeys = {@ForeignKey(entity = Question.class,parentColumns = "questionId",childColumns = "questionId",onDelete = ForeignKey.CASCADE,onUpdate = ForeignKey.CASCADE),
        @ForeignKey(entity = Exam.class,parentColumns = "examId",childColumns = "examId",onDelete = ForeignKey.CASCADE,onUpdate = ForeignKey.CASCADE),
        @ForeignKey(entity = Student.class,parentColumns = "studentId",childColumns = "studentId",onDelete = ForeignKey.CASCADE,onUpdate = ForeignKey.CASCADE)})
public class StudentExamQuestionCrossRef {
    private int questionId,examId,studentId;


    private String studentAnswer;

    public StudentExamQuestionCrossRef(int questionId, int examId, int studentId, String studentAnswer) {
        this.questionId = questionId;
        this.examId = examId;
        this.studentId = studentId;
        this.studentAnswer = studentAnswer;
    }

    public String getStudentAnswer() {
        return studentAnswer;
    }

    public void setStudentAnswer(String studentAnswer) {
        this.studentAnswer = studentAnswer;
    }

    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    public int getExamId() {
        return examId;
    }

    public void setExamId(int examId) {
        this.examId = examId;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }
}
