package learn.bob.com.androidlearn.aop;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import learn.bob.com.androidlearn.R;

public class AOPDoubleClickActivity extends AppCompatActivity {
    private int index;

    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aopdouble_click);
        mTextView = findViewById(R.id.tvTestClick);
        findViewById(R.id.btnClick).setOnClickListener(new View.OnClickListener() {
            @SingleClick
            @Override
            public void onClick(View v) {
                index++;
                mTextView.setText("点击:" + index);
            }
        });
    }

//    @SingleClick
//    public void textClick(View view) {
//
//    }

}
