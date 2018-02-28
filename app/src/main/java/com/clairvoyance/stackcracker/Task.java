package com.clairvoyance.stackcracker;

import java.io.Serializable;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.UUID;

/**
 * Created by Sathya on 1/30/2018.
 * Task in the Stack
 */

class Task implements Serializable {

    // Static Fields
    final static int NOT_STARTED = 0;
    final static int STARTED = 1;
    final static int IN_PROGRESS = 2;
    final static int TESTING_ME = 3;
    final static int TESTING_BEN = 4;
    final static int FINISHED = 5;

    private String id;
    private String username;
    private long dateCreated;
    private long dateDeadline;
    private int status = 0;
    private String category;

    private String name = "Untitled Task";
    private String notes = "No Notes";

    Task(String username){
        this.username = username;
        this.id = UUID.randomUUID().toString();
        this.dateCreated = Calendar.getInstance().getTimeInMillis();
    }

    void setDateDeadline(long dateDeadline) {
        this.dateDeadline = dateDeadline;
    }
    public void setName(String name) {
        this.name = name;
    }
    void setNotes(String notes) {
        this.notes = notes;
    }
    void setStatus(int status) {
        this.status = status;
    }
    public void setCategory(String category) {
        this.category = category;
    }

    public String getId() {
        return id;
    }
    public String getUsername() {
        return username;
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
    public String getName() {
        return name;
    }
    String getNotes() {
        return notes;
    }
    String getCategory(){
        return category;
    }
    String getStatus(){
        switch (status){
            case NOT_STARTED:
                return "Not Started";
            case STARTED:
                return "Started/Planning";
            case IN_PROGRESS:
                return "In Progress";
            case TESTING_ME:
                return "Testing by Sathya";
            case TESTING_BEN:
                return "Testing by Ben";
            case FINISHED:
                return "Finished";
            default:
                return "No status assigned...";
        }
    }
}
