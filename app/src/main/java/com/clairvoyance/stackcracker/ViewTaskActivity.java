package com.clairvoyance.stackcracker;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ViewTaskActivity extends AppCompatActivity {

    Task task;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_task);

        // Will replace with Firebase Implementation w/ IDS
        task = (Task) getIntent().getSerializableExtra("focusTask");

        setLayout();
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
    }


}
