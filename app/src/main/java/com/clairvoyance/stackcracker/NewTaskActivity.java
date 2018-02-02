package com.clairvoyance.stackcracker;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import java.text.DateFormat;
import java.util.Calendar;

public class NewTaskActivity extends AppCompatActivity {

    TextView dateTextView;

    User mainUser;
    Calendar taskDeadline;

    DatePickerDialog.OnDateSetListener startDateDialogListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            taskDeadline.set(Calendar.YEAR, year);
            taskDeadline.set(Calendar.MONTH, month);
            taskDeadline.set(Calendar.DAY_OF_MONTH, dayOfMonth);

            // Update Text
            String dateText = DateFormat.getDateInstance().format(taskDeadline.getTime());
            dateTextView.setText(dateText);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_task);

        dateTextView = findViewById(R.id.date_text);
        taskDeadline = Calendar.getInstance();

        setButtons();
        // Todo: Setup Local Data and eventually the WebServiceHandler
        mainUser = new User("UserID");
    }

    void setButtons(){

        Button calendarButton = findViewById(R.id.choose_date);
        calendarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Todo: Choose Date Dialog goes here
                DatePickerDialog dialog = new DatePickerDialog(NewTaskActivity.this, startDateDialogListener, taskDeadline.get(Calendar.YEAR), taskDeadline.get(Calendar.MONTH), taskDeadline.get(Calendar.DAY_OF_MONTH));
                dialog.getDatePicker().setMinDate(Calendar.getInstance().getTimeInMillis() + 1000);
                dialog.setTitle("Set Task Deadline");
                dialog.show();
            }
        });

        Button addTaskButton = findViewById(R.id.add_task);
        addTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Task task = new Task(mainUser.getUid());

                String nameString = ((EditText) findViewById(R.id.name_text)).getText().toString();
                task.setName(nameString);

                String noteString = ((EditText) findViewById(R.id.note_text)).getText().toString();
                task.setNotes(noteString);

                if (taskDeadline != null){
                    task.setDateDeadline(taskDeadline.getTimeInMillis());
                }

                // Todo: Save Task Data
                mainUser.addTask(task);
                DataHandler.saveMainUserData(mainUser, NewTaskActivity.this);

                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
    }



}