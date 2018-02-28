package com.clairvoyance.stackcracker;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class NewTaskActivity extends AppCompatActivity {

    LinearLayout mainLayout;
    TextView dateTextView;
    RadioGroup categoryRadioGroup;
    RadioGroup statusRadioGroup;

    User mainUser;
    Stack activeStack;
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

        // Todo: Set try-catch
        try {
            mainUser = WebServiceHandler.generateMainUser();
        }catch (IllegalAccessException i){
            Intent backToLogin = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(backToLogin);
        }

        DatabaseReference stackRef = WebServiceHandler.getRootRef().child(WebServiceHandler.STACK_IDENTIFIER);
        ValueEventListener stackGetter = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                activeStack = dataSnapshot.getValue(Stack.class);
                if (activeStack == null){
                    activeStack = new Stack(mainUser.getUid());
                }else{
                    setLayout();
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };

        stackRef.addValueEventListener(stackGetter);

        dateTextView = findViewById(R.id.date_text);
        taskDeadline = Calendar.getInstance();
        // Set Category Buttons

        statusRadioGroup = findViewById(R.id.status_radio);
        statusRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {

                switch(i) {
                    case R.id.not_started_button:
                        status = Task.NOT_STARTED;
                        break;
                    case R.id.started_button:
                        status = Task.STARTED;
                        break;
                    case R.id.in_progress_button:
                        status = Task.IN_PROGRESS;
                        break;
                    case R.id.testing_me_button:
                        status = Task.TESTING_ME;
                        break;
                    case R.id.testing_ben_button:
                        status = Task.TESTING_BEN;
                        break;
                    case R.id.finished_button:
                        status = Task.FINISHED;
                        break;
                }
            }
        });

        mainLayout = findViewById(R.id.main_layout);

        setToolbar();
        setButtons();
    }

    void setToolbar(){
        Toolbar myToolbar = findViewById(R.id.add_task_toolbar);
        myToolbar.setTitleTextColor(Color.parseColor("#FFFFFF"));
        myToolbar.setTitle("New Task");
        setSupportActionBar(myToolbar);
        ActionBar ab = getSupportActionBar();

        ab.setDisplayHomeAsUpEnabled(true);
    }

    void setLayout(){

        // Set Radio group
        categoryRadioGroup = new RadioGroup(getApplicationContext());
        ArrayList<String> categories = new ArrayList<>();
        if (activeStack != null) {
            categories = activeStack.getCategories();
        }

        if(categories.isEmpty()){
            TextView noCategories = new TextView(getApplicationContext());
            noCategories.setText("No Categories yet!");
            mainLayout.addView(noCategories);
            // Maybe add a button to a dialog here??
        }else {
            RadioButton defaultButton = new RadioButton(getApplicationContext());
            defaultButton.setText("No Category");
            categoryRadioGroup.addView(defaultButton);
            defaultButton.setChecked(true);
            for(String category: categories){
                RadioButton radioButton = new RadioButton(getApplicationContext());
                radioButton.setText(category);
                categoryRadioGroup.addView(radioButton);
            }
        }

        mainLayout.addView(categoryRadioGroup);
    }

    void setButtons(){

        Button calendarButton = findViewById(R.id.choose_date);
        calendarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
                Task task = new Task(WebServiceHandler.getUID());

                String nameString = ((EditText) findViewById(R.id.name_text)).getText().toString();
                task.setName(nameString);

                String noteString = ((EditText) findViewById(R.id.note_text)).getText().toString();
                task.setNotes(noteString);

                if (!isDeadlineSet || nameString.equals("") || noteString.equals("")){
                    Toast.makeText(getApplicationContext(), "All Fields are required", Toast.LENGTH_SHORT).show();
                    return;
                }

                task.setDateDeadline(taskDeadline.getTimeInMillis());
                task.setStatus(status);

                RadioButton button = findViewById(categoryRadioGroup.getCheckedRadioButtonId());
                String category = button.getText().toString();
                task.setCategory(category);

                activeStack.addTask(task);
                activeStack.saveStack();

                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
    }

}
