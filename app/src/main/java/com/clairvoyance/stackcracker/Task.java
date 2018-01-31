package com.clairvoyance.stackcracker;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.UUID;

/**
 * Created by Sathya on 1/30/2018.
 * Task in the Stack
 */

class Task {

    private String id;
    private String uid;
    private long dateCreated;
    private long dateDeadline;
    private ArrayList<Task> subTasks;

    private String name = "Untitled Task";
    private String notes = "No Notes";

    public Task(String uid){
        this.uid = uid;
        this.id = UUID.randomUUID().toString();
        this.dateCreated = Calendar.getInstance().getTimeInMillis();
    }

    public void setDateDeadline(long dateDeadline) {
        this.dateDeadline = dateDeadline;
    }
    public void setSubTasks(ArrayList<Task> subTasks) {
        this.subTasks = subTasks;
    }
    public void addSubTask(Task subTask){
        subTasks.add(subTask);
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getId() {
        return id;
    }
    public String getUid() {
        return uid;
    }
    public long getDateCreated() {
        return dateCreated;
    }
    public long getDateDeadline() {
        return dateDeadline;
    }
    public ArrayList<Task> getSubTasks() {
        return subTasks;
    }

    public String getName() {
        return name;
    }
    public String getNotes() {
        return notes;
    }

}
