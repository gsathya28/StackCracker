package com.clairvoyance.stackcracker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    User mainUser;
    Stack activeStack;

    ListView mainList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            mainUser = WebServiceHandler.generateMainUser();
        }catch (IllegalAccessException i){
            Intent backToLogin = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(backToLogin);
        }

        setToolbar();
        mainList = findViewById(R.id.mainList);

        DatabaseReference stackRef = WebServiceHandler.getRootRef().child(WebServiceHandler.STACK_IDENTIFIER);
        ValueEventListener stackGetter = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                activeStack = dataSnapshot.getValue(Stack.class);
                if (activeStack == null){
                    activeStack = new Stack(WebServiceHandler.getUID());
                    activeStack.saveStack();
                    // Todo: Run Empty Stack Code
                }
                else{
                    mainList.setAdapter(new MyAdapter());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        stackRef.addValueEventListener(stackGetter);
        setButtons();
    }

    // Todo: Set up Menu for Toolbar
    // Todo: Set up New Category Dialog
    // Todo: Delete Category Dialog

    public void setToolbar(){


    }

    public void setButtons(){
        Button addTask = findViewById(R.id.add_task_button);
        addTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), NewTaskActivity.class);
                startActivity(intent);
            }
        });
    }

    /**
     * A simple array adapter
     */
    private class MyAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return activeStack.getTasksAsList().size();
        }

        @Override
        public String getItem(int position) {
            return activeStack.getTasksAsList().get(position).getName();
        }
        String getNotes(int position){
            return activeStack.getTasksAsList().get(position).getNotes();
        }
        Task getTask(int position){
            return activeStack.getTasksAsList().get(position);
        }
        String getDate(int position){
            Calendar taskDeadline = Calendar.getInstance();
            taskDeadline.setTimeInMillis(activeStack.getTasksAsList().get(position).getDateDeadline());
            return "Deadline: " + DateFormat.getDateInstance().format(taskDeadline.getTime());
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup container) {
            if (convertView == null) {
                convertView = getLayoutInflater().inflate(R.layout.task_item, container, false);
            }

            ((TextView) convertView.findViewById(android.R.id.text1))
                    .setText(getItem(position));
            ((TextView) convertView.findViewById(android.R.id.text2))
                    .setText(getNotes(position));
            ((TextView) convertView.findViewById(R.id.text3))
                    .setText(getDate(position));

            final Task task = getTask(position);

            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent;
                    intent = new Intent(MainActivity.this, ViewTaskActivity.class);
                    intent.putExtra("taskID", task.getId());

                    startActivity(intent);
                }
            });

            return convertView;
        }
    }
}
