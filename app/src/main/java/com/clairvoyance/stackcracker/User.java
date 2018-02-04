package com.clairvoyance.stackcracker;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Sathya on 1/30/2018.
 * User of the Stack
 */

public class User implements Serializable {

    private String uid;
    private ArrayList<Task> mainStack = new ArrayList<>();

    User(String uid){
        this.uid = uid;
    }

    public void setMainStack(ArrayList<Task> mainStack) {
        this.mainStack = mainStack;
    }
    public void addTask(Task task){
        mainStack.add(task);
    }

    String getUid() {
        return uid;
    }
    ArrayList<Task> getMainStack() {
        return mainStack;
    }
}
