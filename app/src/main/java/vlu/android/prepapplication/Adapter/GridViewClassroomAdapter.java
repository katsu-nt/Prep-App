package vlu.android.prepapplication.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.util.ArrayList;
import java.util.List;


import vlu.android.prepapplication.Fragment.Teacher.DetailedClassroomFragment;


import vlu.android.prepapplication.Model.Classroom;
import vlu.android.prepapplication.R;
import vlu.android.prepapplication.ViewModel.ClassroomViewModel;

public class GridViewClassroomAdapter extends BaseAdapter {
    private List<Classroom> classroom;
    private final LayoutInflater inflater;
    private final ClassroomViewModel classroomViewModel;

    public GridViewClassroomAdapter(Context context, ClassroomViewModel classroomViewModel) {
        this.classroom = new ArrayList<>();
        this.inflater = LayoutInflater.from(context);
        this.classroomViewModel = classroomViewModel;
    }

    @Override
    public int getCount() {
        return classroom.size();
    }

    @Override
    public Object getItem(int position) {
        return classroom.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ClassroomViewHolder holder;
        Classroom classroom1 = classroom.get(position);

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.grid_view_classroom_item_layout, parent, false);
            holder = new ClassroomViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ClassroomViewHolder) convertView.getTag();
        }
        String classroomID = String.valueOf(classroom1.getClassroomId());
        String classroomDE =classroom1.getDescription() ;

        holder.getTvClassroomID().setText(classroomID);
        holder.getTvClassroomDe().setText(classroomDE);

        // Click listener for item view
        convertView.setOnClickListener(view -> {
            Context context = parent.getContext();
            if (context instanceof AppCompatActivity) {
                AppCompatActivity activity = (AppCompatActivity) context;
                FragmentManager fragmentManager = activity.getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                Fragment fragment = new DetailedClassroomFragment();
                fragmentTransaction.replace(R.id.flTeacher, fragment);
                fragmentTransaction.commit();
            }
        });


        // Long click listener for item view
        convertView.setOnLongClickListener(view -> {
            new AlertDialog.Builder(view.getContext()).
                    setTitle(String.format(
                            "Are you sure you want to delete classroom with id: %s",
                            classroomID)
                    ).
                    setPositiveButton("Confirm", (dialogInterface, i) -> classroomViewModel.delete(classroom1)).
                    setNegativeButton("Cancel", (dialogInterface, i) -> dialogInterface.cancel()).
                    show();
            return true;
        });

        return convertView;
    }

    public void updateClassroom(List<Classroom> classroom) {
        this.classroom = classroom;
        notifyDataSetChanged();
    }

    static class ClassroomViewHolder {
        private final TextView tvClassroomID;
        private final TextView tvClassroomDe;

        ClassroomViewHolder(View itemView) {
            tvClassroomID = itemView.findViewById(R.id.tvIDClass);
            tvClassroomDe = itemView.findViewById(R.id.tvDescription);
        }

        TextView getTvClassroomID() {
            return tvClassroomID;
        }

        TextView getTvClassroomDe() {
            return tvClassroomDe;
        }
    }
}
