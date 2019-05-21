package com.bobLearn.orm;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.bobLearn.R;

public class OrmActivity extends AppCompatActivity {
    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orm);
        mTextView = findViewById(R.id.tv_show);
    }

    public void insert(View view) {
        GreenDaoApi.insertUser(new User("1", "haha", "sys"));
        User user = GreenDaoApi.getUser();
        Log.d("11", "user = " + user.toString());
    }

    public void update(View view) {
        User user = new User();
        user.setId("1");
        user.setName("xixi");
        GreenDaoApi.insertUser(user);
        User user1 = GreenDaoApi.getUser();
        Log.d("22", "user = " + user.toString());
    }
}
