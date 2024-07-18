package vlu.android.prepapplication.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import vlu.android.prepapplication.Model.Student;
import vlu.android.prepapplication.R;
import vlu.android.prepapplication.ViewModel.StudentViewModel;

public class RecyclerViewStudentInClassroom extends RecyclerView.Adapter<RecyclerViewStudentInClassroom.StudentViewHoder> {
    private List<Student> students;
    private final StudentViewModel studentViewModel;
    public RecyclerViewStudentInClassroom(StudentViewModel studentViewModel){
        this.students = new ArrayList<>();
        this.studentViewModel = studentViewModel;
    }
    @NonNull
    @Override
    public StudentViewHoder onCreateViewHolder(@NonNull ViewGroup parent, int ViewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyler_view_detail_add_student_in_classroom,parent,false);
        return new StudentViewHoder(view);

    }
    @Override
    public void onBindViewHolder(@NonNull StudentViewHoder holder, int position) {
        Student student = students.get(position);
        String studentId = String.valueOf(student.getStudentId());
        String studentName = student.getName();
        holder.getTvStudentId().setText(studentId);
        holder.getTvStudentName().setText(studentName);
    }


    @Override
    public int getItemCount(){
        return students.size();
    }
    public void updateStudents(List<Student> students) {
        this.students.clear();
        this.students.addAll(students);
        notifyDataSetChanged();
    }

    public static class StudentViewHoder extends RecyclerView.ViewHolder{
            private final TextView tvStudentName, tvStudentId;
            public StudentViewHoder(@NonNull View itemView){
                super(itemView);
                tvStudentId = itemView.findViewById(R.id.tvStudentID);
                tvStudentName = itemView.findViewById(R.id.tvStudentName);

            }
            public TextView getTvStudentName(){
                return tvStudentName;
            }
            public TextView getTvStudentId(){
                return tvStudentId;
            }
    }

}
