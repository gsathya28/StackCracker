package com.clairvoyance.stackcracker;

import java.io.Serializable;

/**
 * Created by Sathya on 1/30/2018.
 * User of the Stack
 */

class User implements Serializable {

    private String uid;
    private String name;
    // Make an arrayList of Stacks later
    private Stack activeStack;

    User(String uid, String name){
        this.uid = uid;
        this.name = name;
        // Yeah, this will change too... (ArrayList Life)
        this.activeStack = new Stack(uid);
    }

    void addTask(Task task){
        activeStack.addTask(task);
    }
    String getUid() {
        return uid;
    }
    String getName(){ return name; }
    Stack getActiveStack() {
        return activeStack;
    }

}
