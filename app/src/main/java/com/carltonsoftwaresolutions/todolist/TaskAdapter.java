package com.carltonsoftwaresolutions.todolist;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.io.FileInputStream;
import java.util.List;

/**
 * Created by ericcarlton on 8/5/14.
 */
public class TaskAdapter extends ArrayAdapter<Task> {

    private Context context;
    protected List<Task> tasks;

    public TaskAdapter(Context context, List<Task>tasks){
        super(context, R.layout.to_do_list_item, tasks);
        this.context = context;
        this.tasks = tasks;
    }

    public View getView(int position, View convertView, ViewGroup parent){
        if(convertView == null){
            LayoutInflater mLayoutInflater = LayoutInflater.from(context);
            convertView = mLayoutInflater.inflate(R.layout.to_do_list_item, null);
        }

        Task task = tasks.get(position);

        TextView descriptionView = (TextView) convertView.findViewById(R.id.taskDesc);
        TextView dateView = (TextView) convertView.findViewById(R.id.taskDate);

        descriptionView.setText(task.getName());
        dateView.setText(task.getDate());

        if(task.isCompleted()){
            descriptionView.setPaintFlags(descriptionView.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        }else{
            descriptionView.setPaintFlags(descriptionView.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
        }

        return convertView;
    }

}
