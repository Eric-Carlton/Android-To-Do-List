package com.carltonsoftwaresolutions.todolist;

import android.app.Activity;
import android.content.Context;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;


public class ToDoList extends Activity implements AdapterView.OnItemClickListener{

    private TextView createTaskTxt;
    private ListView toDoList;
    private TaskAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_do_list);
        createTaskTxt = (TextView)findViewById(R.id.createTaskTxt);
        toDoList = (ListView)findViewById(R.id.toDoList);
        adapter = new TaskAdapter(this, loadTasks());
        toDoList.setAdapter(adapter);
        toDoList.setOnItemClickListener(this);
    }

    @Override
    protected void onPause(){
        super.onPause();
        saveTasks();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.to_do_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void newTask(View v){
        if(createTaskTxt.getText().length() > 0){
            String name = createTaskTxt.getText().toString();
            Task task = new Task(name);
            adapter.insert(task, 0);
            createTaskTxt.setText("");
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Task task = adapter.getItem(position);
        TextView taskDescription = (TextView) view.findViewById(R.id.taskDesc);

        task.toggleComplete();

        if(task.isCompleted()){
            taskDescription.setPaintFlags(taskDescription.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        }else{
            taskDescription.setPaintFlags(taskDescription.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
        }
    }

    private ArrayList<Task> loadTasks(){

        ArrayList<Task> tasks = new ArrayList<Task>();
        try{
            FileInputStream in = openFileInput("tasks.txt");
            InputStreamReader inputStreamReader = new InputStreamReader(in);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                Task t = convertStringToTask(line);
                if(t != null)
                    tasks.add(t);
            }
        }catch(Exception e){

        }

        return tasks;
    }

    private Task convertStringToTask(String s){
        System.out.println(s);
        if(s.indexOf(" - ") >= 0){
            String[] props = s.split(" - ");
            if(props.length == 3){
                String name = props[0];
                boolean completed = false;
                if(props[1].equalsIgnoreCase("true"))
                    completed = true;
                String date = props[2];
                return new Task(name, completed, date);
            }
        }
        return null;
    }

    private void saveTasks(){
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(openFileOutput("tasks.txt", Context.MODE_PRIVATE));

            for(Task t : adapter.tasks)
                outputStreamWriter.write(t.toString()+"\n");

            outputStreamWriter.close();
        }
        catch (Exception e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }
}
