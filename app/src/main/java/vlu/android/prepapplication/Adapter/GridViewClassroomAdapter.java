package vlu.android.prepapplication.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

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

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.grid_view_classroom_item_layout, parent, false);
            holder = new ClassroomViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ClassroomViewHolder) convertView.getTag();
        }

        Classroom currentClassroom = classroom.get(position);
        holder.getTvClassroomID().setText(String.valueOf(currentClassroom.getId()));
        holder.getTvClassroomDe().setText(currentClassroom.getDescription());

        // Click listener for item view
        convertView.setOnClickListener(view -> {
            Classroom clickedClassroom = classroom.get(position);
            Bundle bundle = new Bundle();
            bundle.putInt("classroom_id", clickedClassroom.getId());

        });


        // Long click listener for item view
        convertView.setOnLongClickListener(view -> {
            Classroom longClickedClassroom = classroom.get(position);
            new AlertDialog.Builder(view.getContext())
                    .setTitle("Delete Classroom")
                    .setMessage("Are you sure you want to delete this classroom?")
                    .setPositiveButton("Confirm", (dialogInterface, i) -> {
                        // Delete classroom from ViewModel
                        classroomViewModel.delete(longClickedClassroom);
                        Toast.makeText(view.getContext(), "Classroom deleted: " + longClickedClassroom.getId(), Toast.LENGTH_SHORT).show();
                    })
                    .setNegativeButton("Cancel", (dialogInterface, i) -> dialogInterface.cancel())
                    .show();
            return true; // Consume long click
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
