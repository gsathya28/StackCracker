package com.clairvoyance.stackcracker;

import com.google.firebase.database.Exclude;

import java.util.ArrayList;
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
    private ArrayList<Task> tasks = new ArrayList<>();

    public Stack(){

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
            tasks.add(task);
        }
    }

    ArrayList<String> getCategories(){
        return categories;
    }
    ArrayList<Task> getTasks(){
        return tasks;
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
