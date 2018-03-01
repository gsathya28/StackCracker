package com.clairvoyance.stackcracker;

import com.google.firebase.database.Exclude;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Sathya on 2/27/2018.
 *
 */

class Stack {

    // Categories
    private String uid;
    private ArrayList<String> categories = new ArrayList<>();
    private HashMap<String, Task> tasks = new HashMap<>();

    Stack(){

    }

    Stack(String uid){
        this.uid = uid;
    }

    void addCategory(String category){
        categories.add(category);
    }

    void setCategories(ArrayList<String> categories){
        this.categories = categories;
    }

    void addTask(Task task){
        if(task != null) {
            tasks.put(task.getId(), task);
        }
    }
    void removeTask(String taskID){
        tasks.remove(taskID);
        saveStack();
    }

    ArrayList<String> getCategories(){
        return categories;
    }
    HashMap<String, Task> getTasks(){
        return tasks;
    }

    String getUid(){
        return uid;
    }

    @Exclude
    ArrayList<Task> getTasksAsList(){
         return new ArrayList<>(tasks.values());
    }

    @Exclude
    void saveStack(){
        WebServiceHandler.editStack(this);
    }
}
