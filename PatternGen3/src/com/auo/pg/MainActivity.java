package com.auo.pg;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;

public class MainActivity extends Activity {
    private final String TAG = "MainActivity";

    private Button mPowerBtn;
    private Button mOpticalBtn;
    private Button mImageBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.v(TAG, "onCreate()");

        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        mPowerBtn = (Button) findViewById(R.id.btn_power);
        mOpticalBtn = (Button) findViewById(R.id.btn_optical);
        mImageBtn = (Button) findViewById(R.id.btn_image);

        mPowerBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, PowerActivity.class);
                startActivity(intent);
            }
        });

        mOpticalBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, PatternSelectActivity.class);
                startActivity(intent);
            }
        });

        mImageBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ImageActivity.class);
                startActivity(intent);
            }
        });
    }
}
