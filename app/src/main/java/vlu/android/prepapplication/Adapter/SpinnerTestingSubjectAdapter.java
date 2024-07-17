package vlu.android.prepapplication.Adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import vlu.android.prepapplication.Model.Classroom;
import vlu.android.prepapplication.Model.Subject;
import vlu.android.prepapplication.R;

public class SpinnerTestingSubjectAdapter extends BaseAdapter {
    Activity activity;
    List<Subject> list;

    public SpinnerTestingSubjectAdapter(Activity activity, List<Subject> list) {
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
        txtItem.setText(list.get(position).getSubjectId()+" - "+list.get(position).getName());
        return convertView;
    }
}
