package com.clairvoyance.stackcracker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

public class EditTaskActivity extends AppCompatActivity {

    String taskID;
    DatabaseReference stackRef;
    ValueEventListener stackGetter;

    User mainUser;
    Stack activeStack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_task);

        try {
            mainUser = WebServiceHandler.generateMainUser();
        }catch (IllegalAccessException i){
            Intent backToLogin = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(backToLogin);
        }

        // Use Constant String as name - - -
        taskID = getIntent().getStringExtra("taskID");

        stackRef = WebServiceHandler.getRootRef().child(WebServiceHandler.STACK_IDENTIFIER);
        stackGetter = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                activeStack = dataSnapshot.getValue(Stack.class);
                if (activeStack == null){
                    // Update UI
                    loadBack();
                }else{
                    setLayout();
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };



    }

    private void setLayout(){

    }

    private void loadBack(){

    }

    @Override
    protected void onDestroy() {
        stackRef.removeEventListener(stackGetter);
        super.onDestroy();
    }
}
