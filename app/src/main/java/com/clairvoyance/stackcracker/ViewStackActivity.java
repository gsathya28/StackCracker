package com.clairvoyance.stackcracker;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ViewStackActivity extends AppCompatActivity {

    Task task;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_stack);

        // Get Task using the intent
        setButtons();
    }

    void setButtons(){

        Button finish = findViewById(R.id.finish_task);
        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Change the task state and save it in mainUser.

            }
        });

    }



}
