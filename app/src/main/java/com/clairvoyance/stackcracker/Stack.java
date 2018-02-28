package com.clairvoyance.stackcracker;

import com.google.firebase.database.Exclude;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

/**
 * Created by Sathya on 2/27/2018.
 *
 */

class Stack {

    // Categories
    private String uid;
    private String stackID;
    private ArrayList<String> categories = new ArrayList<>();
    private HashMap<String, Task> tasks = new HashMap<>();

    Stack(){

    }

    Stack(String uid){
        this.uid = uid;
        this.stackID = UUID.randomUUID().toString();
    }

    void addCategory(String category){
        categories.add(category);
    }
    void addTask(Task task){
        if(task != null) {
            tasks.put(task.getId(), task);
        }
    }

    ArrayList<String> getCategories(){
        return categories;
    }
    HashMap<String, Task> getTasks(){
        return tasks;
    }
    ArrayList<Task> getTasksAsList(){
         return (ArrayList<Task>) tasks.values();
    }
    String getStackID() {
        return stackID;
    }

    @Exclude
    int getCategoryIndexOf(String category){
        int index = categories.indexOf(category);
        if(index != -1){
            return index;
        }
        else{
            throw new IllegalArgumentException();
        }
    }
    @Exclude
    String getCategoryString(int index){

        String category = categories.get(index);

        if(category != null){
            return category;
        }
        else{
            throw new IllegalArgumentException();
        }
    }
    @Exclude
    void saveStack(){
        WebServiceHandler.editStack(this);
    }
}
