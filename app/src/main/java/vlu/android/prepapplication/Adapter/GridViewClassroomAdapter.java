package vlu.android.prepapplication.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import vlu.android.prepapplication.Model.Classroom;
import vlu.android.prepapplication.R;
import vlu.android.prepapplication.ViewModel.ClassroomViewModel;

public class GridViewClassroomAdapter extends BaseAdapter {
    private List<Classroom> classroom;
    private final LayoutInflater inflater;

    public GridViewClassroomAdapter(Context context, ClassroomViewModel classroomViewModel) {
        this.classroom = new ArrayList<>();
        this.inflater = LayoutInflater.from(context);
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
        holder.tvClassroomID.setText(currentClassroom.getId());
        holder.tvClassroomDe.setText(currentClassroom.getDescription());

        return convertView;
    }

    public void updateClassroom(List<Classroom> classroom) {
        this.classroom = classroom;
        notifyDataSetChanged();
    }

    private static class ClassroomViewHolder {
        private final TextView tvClassroomID;
        private final TextView tvClassroomDe;

        public ClassroomViewHolder(View view) {
            tvClassroomID = view.findViewById(R.id.tvIDClass);
            tvClassroomDe = view.findViewById(R.id.tvDescription);
        }
    }
}
