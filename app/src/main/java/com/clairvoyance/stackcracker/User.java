package com.clairvoyance.stackcracker;

import java.util.ArrayList;

/**
 * Created by Sathya on 1/30/2018.
 * User of the Stack
 */

public class User {

    private String uid;
    private ArrayList<Task> tasks = new ArrayList<>();

    User(String uid){
        this.uid = uid;
    }

    public void setTasks(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }
    public void addTask(Task task){
        tasks.add(task);
    }

    public String getUid() {
        return uid;
    }
    public ArrayList<Task> getTasks() {
        return tasks;
    }
}
