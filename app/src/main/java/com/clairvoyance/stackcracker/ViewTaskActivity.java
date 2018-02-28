package com.clairvoyance.stackcracker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

public class ViewTaskActivity extends AppCompatActivity {

    String taskID;
    Task task;
    Stack activeStack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_task);

        // Will replace with Firebase Implementation w/ IDS
        taskID = getIntent().getStringExtra("taskID");

        DatabaseReference stackRef = WebServiceHandler.getRootRef().child(WebServiceHandler.STACK_IDENTIFIER);
        ValueEventListener stackGetter = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                activeStack = dataSnapshot.getValue(Stack.class);
                if (activeStack == null){
                    Intent backToMain = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(backToMain);
                }else{
                    task = activeStack.getTasks().get(taskID);
                    setLayout();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        stackRef.addValueEventListener(stackGetter);

        setButtons();
    }

    void setLayout(){
        TextView categoryTextView = findViewById(R.id.category_text);
        String categoryString = "Category: " + task.getCategory();
        categoryTextView.setText(categoryString);

        TextView statusTextView = findViewById(R.id.status_text);
        String statusString = "Status: " + task.getStatus();
        statusTextView.setText(statusString);

        TextView nameTextView = findViewById(R.id.name_text);
        nameTextView.setText(task.getName());

        TextView noteTextView = findViewById(R.id.note_text);
        noteTextView.setText(task.getNotes());

        TextView dateTextView = findViewById(R.id.date_text);
        String dateString = "Deadline: " + task.getDateString();
        dateTextView.setText(dateString);
    }

    void setButtons() {
        Button finish = findViewById(R.id.finish_task);
        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Change the task state and save it in mainUser.
                task.setStatus(Task.FINISHED);
                // Save task
            }
        });

        Button delete = findViewById(R.id.delete_task);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Todo: This needs a dialog
                activeStack.removeTask(task.getId());
            }
        });
    }
}
