package com.clairvoyance.stackcracker;

import android.support.annotation.Nullable;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


/**
 * Created by Sathya on 2/3/2018.
 * WebServiceHandler for Firebase
 */

class WebServiceHandler {

    final static int RC_SIGN_IN = 2899;
    final static String WEB_CLIENT_ID = "685512732127-8b060mvvjdahhktqhsoa4ssu054nma6s.apps.googleusercontent.com";
    private static FirebaseUser mUser;
    private static User loadedUser;

    final static String STACK_IDENTIFIER = "mainStack";

    private static DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
    static DatabaseReference getRootRef() {
        return rootRef;
    }


    private static boolean isMainUserAuthenticated(){
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        return mUser != null;
    }

    static String getUID(){
        return mUser.getUid();
    }

    @Nullable
    static User generateMainUser() throws IllegalAccessException{
        if (isMainUserAuthenticated()){
            User user = new User(mUser.getUid(), mUser.getDisplayName());

            // Key-line
            user = loadMainUserData(user);
            return user;
        }
        else {
            throw new IllegalAccessException();
        }
    }

    private static User loadMainUserData(final User user){
        // Load main data (and set listener) from database - if not already loaded
        if (loadedUser == null) {

            DatabaseReference userRef = rootRef.child("users").child(mUser.getUid());

            // Read Data
            userRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (!dataSnapshot.exists()) {
                        // Create new user in database
                        createNewUserData(user);
                        // Loaded User is whatever it is now since there is no previous data to load
                        loadedUser = user;
                    }
                    else {
                        // This works even after the initial data read since loadedUser's pointer is returned at the end of the method.
                        loadedUser = dataSnapshot.getValue(User.class);
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
        return loadedUser; // This will be null at first since the ValueEventListener only puts the data in this pointer after all the code (onCreate) has run
    }

    private static void createNewUserData(User user){
        if (isMainUserAuthenticated()){
            DatabaseReference userRef = FirebaseDatabase.getInstance().getReference().child("users");
            userRef.child(mUser.getUid()).setValue(user);
        }
        else {
            throw new IllegalStateException("User not authorized");
        }
    }

    static void editStack(Stack stack){
        if (isMainUserAuthenticated()) {
            DatabaseReference taskRef = rootRef.child(STACK_IDENTIFIER);
            taskRef.setValue(stack);
        }
        else{
            throw new IllegalStateException("Not Authorized");
        }
    }



}
