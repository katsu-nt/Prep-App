package vlu.android.prepapplication.Adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;

import java.util.List;

import vlu.android.prepapplication.Model.Classroom;
import vlu.android.prepapplication.R;

public class SpinnerTestingClassroomAdapter extends BaseAdapter {
    Activity activity;
    List<Classroom> list;

    public SpinnerTestingClassroomAdapter(Activity activity, List<Classroom> list) {
        this.activity = activity;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = activity.getLayoutInflater();
        convertView = inflater.inflate(R.layout.spinner_classroom_testing_item_layout,null);
        TextView txtItem = convertView.findViewById(R.id.txtSpinnerTestingItem);
        txtItem.setText(list.get(position).getClassroomId()+" - "+list.get(position).getName());
        return convertView;
    }
}