package com.clairvoyance.stackcracker;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Sathya on 1/30/2018.
 * User of the Stack
 */

public class User implements Serializable {

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

    String getUid() {
        return uid;
    }
    ArrayList<Task> getTasks() {
        return tasks;
    }
}
