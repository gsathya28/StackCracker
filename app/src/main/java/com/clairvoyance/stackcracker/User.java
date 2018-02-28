package com.clairvoyance.stackcracker;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Exclude;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

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
    private String activeStackId;

    public User(){

    }

    User(String uid, String name){
        this.uid = uid;
        this.name = name;
        // Yeah, this will change too... (ArrayList Life)
        if (WebServiceHandler.hasRootAccess(this)){
            this.activeStack = new Stack(uid);
            this.activeStackId = activeStack.getStackID();
        }else{
            // get current activeStack function code.
            Query stackRef = WebServiceHandler.getRootRef().child(WebServiceHandler.STACK_IDENTIFIER).limitToFirst(1);
            ValueEventListener getStack = new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for(DataSnapshot data: dataSnapshot.getChildren()){
                        if (data != null){
                            activeStack = data.getValue(Stack.class);
                            if (activeStack != null)
                            {
                                activeStackId = activeStack.getStackID();
                            }
                            else{
                                throw new IllegalStateException("No Stack Created");
                            }

                            return;
                        }
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            };
            stackRef.addListenerForSingleValueEvent(getStack);
        }
    }

    void addTask(Task task){
        activeStack.addTask(task);
    }
    String getUid() {
        return uid;
    }
    String getName(){ return name; }
    String getActiveStackId() {
        return activeStackId;
    }

    @Exclude
    Stack getActiveStack() {
        return activeStack;
    }

}
