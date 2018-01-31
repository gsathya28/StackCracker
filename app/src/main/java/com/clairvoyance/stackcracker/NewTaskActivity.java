package com.clairvoyance.stackcracker;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import java.text.DateFormat;
import java.util.Calendar;

public class NewTaskActivity extends AppCompatActivity {

    User mainUser;
    Calendar taskDeadline;

    DatePickerDialog.OnDateSetListener startDateDialogListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

            taskDeadline.set(Calendar.YEAR, year);
            taskDeadline.set(Calendar.MONTH, month);
            taskDeadline.set(Calendar.DAY_OF_MONTH, dayOfMonth);

            boolean eventInPast = (taskDeadline.before(Calendar.getInstance()));
            if (eventInPast){
                // Clear taskDeadline and
                // Reload this shit! (the dialog)
            }
            else{
                // Dismiss the dialog
            }

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_task);
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
                Calendar startCalendar = Calendar.getInstance();
                DatePickerDialog dialog = new DatePickerDialog(getApplicationContext(), startDateDialogListener, startCalendar.get(Calendar.YEAR), startCalendar.get(Calendar.MONTH), startCalendar.get(Calendar.DAY_OF_MONTH));
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

                // Todo: Parse Task Data

                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
    }



}
