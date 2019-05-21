package com.bobLearn.task;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.bobLearn.R;

public class TaskTestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_test);
    }

    public void task(View view) {
        moveTaskToBack(true);
    }
}
