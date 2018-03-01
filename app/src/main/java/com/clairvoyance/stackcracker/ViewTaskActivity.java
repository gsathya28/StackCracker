package com.clairvoyance.stackcracker;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

public class ViewTaskActivity extends AppCompatActivity {

    String taskID;
    Task task;
    Stack activeStack;

    DatabaseReference stackRef;
    ValueEventListener stackGetter;


    Toolbar myToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_task);

        taskID = getIntent().getStringExtra("taskID");

        stackRef = WebServiceHandler.getRootRef().child(WebServiceHandler.STACK_IDENTIFIER);
        stackGetter = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                activeStack = dataSnapshot.getValue(Stack.class);
                if (activeStack == null){
                    Intent backToMain = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(backToMain);
                }else{
                    task = activeStack.getTasks().get(taskID);
                    if (task == null){
                        // Task was deleted or non-existent - go back..
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                        Toast.makeText(getApplicationContext(), "Task Deleted", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        setLayout();
                    }

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        stackRef.addValueEventListener(stackGetter);
        setToolbar();
        setButtons();
    }

    void setToolbar(){
        myToolbar = findViewById(R.id.view_task_toolbar);
        myToolbar.setTitleTextColor(Color.parseColor("#FFFFFF"));
        myToolbar.setTitle("Task Name...");
        setSupportActionBar(myToolbar);
        ActionBar ab = getSupportActionBar();
        if (ab != null){
            ab.setDisplayHomeAsUpEnabled(true);
        }
    }

    void setLayout(){
        TextView categoryTextView = findViewById(R.id.category_text);
        String categoryString = "Category: " + task.getCategory();
        categoryTextView.setText(categoryString);

        TextView statusTextView = findViewById(R.id.status_text);
        String statusString = "Status: " + task.getStringStatus();
        statusTextView.setText(statusString);

        TextView nameTextView = findViewById(R.id.name_text);
        nameTextView.setText(task.getName());
        myToolbar.setTitle(task.getName());

        TextView noteTextView = findViewById(R.id.note_text);
        noteTextView.setText(task.getNotes());

        TextView dateTextView = findViewById(R.id.date_text);
        String dateString = "Deadline: " + task.getDateString();
        dateTextView.setText(dateString);

        int status = task.getStatus();
        RadioGroup statusRadioGroup = findViewById(R.id.status_radio);

        RadioButton button = (RadioButton) statusRadioGroup.getChildAt(status);
        button.setChecked(true);

        statusRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                // Add edit code
                switch(i) {
                    case R.id.not_started_button:
                        task.setStatus(Task.NOT_STARTED);
                        break;
                    case R.id.started_button:
                        task.setStatus(Task.STARTED);
                        break;
                    case R.id.in_progress_button:
                        task.setStatus(Task.IN_PROGRESS);
                        break;
                    case R.id.testing_me_button:
                        task.setStatus(Task.TESTING_ME);
                        break;
                    case R.id.testing_ben_button:
                        task.setStatus(Task.TESTING_BEN);
                        break;
                    case R.id.finished_button:
                        task.setStatus(Task.FINISHED);
                        break;
                }

                activeStack.addTask(task);
                activeStack.saveStack();
            }
        });
    }

    void setButtons() {
        Button finish = findViewById(R.id.finish_task);
        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Change the task state and save it in mainUser.
                task.setStatus(Task.FINISHED);
                activeStack.addTask(task);
                activeStack.saveStack();
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
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

        Button edit = findViewById(R.id.make_stack);
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), EditTaskActivity.class);
                intent.putExtra("taskID", taskID);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onDestroy() {
        stackRef.removeEventListener(stackGetter);
        super.onDestroy();
    }
}
