package com.example.login;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class ListAdapter extends ArrayAdapter {

    private Activity mContext;
    List<Student> studentList;

    public ListAdapter(Activity mContext, List<Student> studentList){
        super(mContext,R.layout.list_item,studentList);
        this.mContext = mContext;
        this.studentList = studentList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = mContext.getLayoutInflater();
        View listItemView = inflater.inflate(R.layout.list_item,null,true);

        TextView tvname = listItemView.findViewById(R.id.tvName);
        TextView tvroll = listItemView.findViewById(R.id.tvRollno);
        TextView tvdob = listItemView.findViewById(R.id.tvdob);
        TextView tvcs = listItemView.findViewById(R.id.tvcs);
        TextView tvt = listItemView.findViewById(R.id.tvtea);

        Student student = studentList.get(position);

        tvname.setText(student.getName());
        tvroll.setText(student.getroll_no());
        tvdob.setText(student.getdate_of_birth());
        tvcs.setText(student.getclass_section());
        tvt.setText(student.getclass_teacher());

        return listItemView;
    }
}
