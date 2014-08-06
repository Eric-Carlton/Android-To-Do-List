package com.carltonsoftwaresolutions.todolist;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by ericcarlton on 8/5/14.
 */
public class Task {

    private String name;
    private boolean completed;
    private String date;

    public Task(){
        this("New Task");
    }

    public Task(String name){
        this(name, false );
    }

    public Task(String name, boolean completed){
        this.name = name;
        this.completed = completed;
        date = makeDate();
    }

    public Task(String name, boolean completed, String date){
        this.name = name;
        this.completed = completed;
        this.date = date;
    }

    public String getName(){
        return name;
    }

    public boolean isCompleted(){
        return completed;
    }

    public String getDate(){ return date; }

    public void markComplete(){
        completed = true;
    }

    public void toggleComplete() {completed = !completed; }
    @Override
    public String toString(){
        return name + " - " + completed + " - " + date;
    }

    @Override
    public boolean equals(Object obj){
        if(obj instanceof Task)
            if(((Task) obj).getName().equals(this.name) && ((Task) obj).isCompleted() == this.completed)
                return true;

        return false;
    }

    private String makeDate(){
        Calendar c = Calendar.getInstance();
        System.out.println("Current time => " + c.getTime());


        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss a");
        String date = formatter.format(c.getTime());

        return date;
    }
}
