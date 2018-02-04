package com.clairvoyance.stackcracker;

import java.io.Serializable;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.UUID;

/**
 * Created by Sathya on 1/30/2018.
 * Task in the Stack
 */

class Task implements Serializable {

    private boolean isStack = false;
    private boolean isFinished = false;
    private String id;
    private String uid;
    private long dateCreated;
    private long dateDeadline;
    private ArrayList<Task> subTasks;

    private String name = "Untitled Task";
    private String notes = "No Notes";

    Task(String uid){
        this.uid = uid;
        this.id = UUID.randomUUID().toString();
        this.dateCreated = Calendar.getInstance().getTimeInMillis();
    }

    void setDateDeadline(long dateDeadline) {
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
    void setNotes(String notes) {
        this.notes = notes;
    }
    public void setFinished(boolean finished) {
        isFinished = finished;
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
    long getDateDeadline() {
        return dateDeadline;
    }
    String getDateString(){
        Calendar taskDeadline = Calendar.getInstance();
        taskDeadline.setTimeInMillis(dateDeadline);
        return DateFormat.getDateInstance().format(taskDeadline.getTime());
    }
    public ArrayList<Task> getSubTasks() {
        return subTasks;
    }

    public String getName() {
        return name;
    }
    String getNotes() {
        return notes;
    }
    boolean isStack() {return isStack;}
}
