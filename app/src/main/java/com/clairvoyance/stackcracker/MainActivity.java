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

import java.text.DateFormat;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    User mainUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainUser = DataHandler.parseMainUserData(this);
        if (mainUser == null) {
            mainUser = new User("testID");
        }

        ListView mainList = findViewById(R.id.mainList);
        mainList.setAdapter(new MyAdapter());
        setButtons();
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
            return mainUser.getTasks().size();
        }

        @Override
        public String getItem(int position) {
            return mainUser.getTasks().get(position).getName();
        }

        String getNotes(int position){
            return mainUser.getTasks().get(position).getNotes();
        }

        String getDate(int position){
            Calendar taskDeadline = Calendar.getInstance();
            taskDeadline.setTimeInMillis(mainUser.getTasks().get(position).getDateDeadline());
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

            return convertView;
        }
    }
}
