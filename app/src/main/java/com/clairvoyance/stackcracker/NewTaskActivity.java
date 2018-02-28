package com.clairvoyance.stackcracker;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.util.Calendar;

public class NewTaskActivity extends AppCompatActivity {

    TextView dateTextView;

    User mainUser;
    Calendar taskDeadline;
    boolean isDeadlineSet = false;
    // Default RadioButton Status is Not Started Button
    int status = Task.NOT_STARTED;

    DatePickerDialog.OnDateSetListener startDateDialogListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            taskDeadline.set(Calendar.YEAR, year);
            taskDeadline.set(Calendar.MONTH, month);
            taskDeadline.set(Calendar.DAY_OF_MONTH, dayOfMonth);

            isDeadlineSet = true;

            // Update Text
            String dateText = DateFormat.getDateInstance().format(taskDeadline.getTime());
            dateTextView.setText(dateText);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_task);

        // Todo: Setup Local Data and eventually the WebServiceHandler
        mainUser = DataHandler.parseMainUserData(this);
        if(mainUser == null){
            mainUser = new User("UserID", "NewUser");
        }

        dateTextView = findViewById(R.id.date_text);
        taskDeadline = Calendar.getInstance();
        // Set Category Buttons
        setButtons();
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

                if (!isDeadlineSet || nameString.equals("") || noteString.equals("")){
                    Toast.makeText(getApplicationContext(), "All Fields are required", Toast.LENGTH_SHORT).show();
                    return;
                }

                task.setDateDeadline(taskDeadline.getTimeInMillis());

                mainUser.addTask(task);
                DataHandler.saveMainUserData(mainUser, NewTaskActivity.this);
                WebServiceHandler.editStack(mainUser.getActiveStack());
                WebServiceHandler.updateMainUserData(mainUser);

                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
    }
    public void onStatusButtonClicked(View view){
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.not_started_button:
                if (checked){
                    status = Task.NOT_STARTED;
                }
                break;
            case R.id.started_button:
                if (checked){
                    status = Task.STARTED;
                }
                break;
            case R.id.in_progress_button:
                if (checked){
                    status = Task.IN_PROGRESS;
                }
                break;
            case R.id.testing_me_button:
                if (checked){
                    status = Task.TESTING_ME;
                }
                break;
            case R.id.testing_ben_button:
                if (checked){
                    status = Task.TESTING_BEN;
                }

                break;
            case R.id.finished_button:
                if (checked){
                    status = Task.FINISHED;
                }
                break;
        }
    }
}
