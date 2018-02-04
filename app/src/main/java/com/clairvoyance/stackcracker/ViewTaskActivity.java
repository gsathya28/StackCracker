package com.clairvoyance.stackcracker;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

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
                task.setFinished(true);
                // Save task
            }
        });
    }
}
