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
    void deleteCategory(String category){
        categories.remove(category);
        // More code to remove the events from the category...


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
    String getUid(){
        return uid;
    }

    @Exclude
    ArrayList<Task> getTasksAsList(){
         return new ArrayList<>(tasks.values());
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
