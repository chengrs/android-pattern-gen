package com.auo.pg;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;

import com.auo.pg.pattern.Pattern;
import com.auo.pg.pattern.power.Power1Pattern;
import com.auo.pg.pattern.power.Power2Pattern;
import com.auo.pg.pattern.power.Power3Pattern;
import com.auo.pg.pattern.power.PowerBlackPattern;
import com.auo.pg.pattern.power.PowerBluePattern;
import com.auo.pg.pattern.power.PowerGreenPattern;
import com.auo.pg.pattern.power.PowerRedPattern;
import com.auo.pg.pattern.power.PowerWhitePattern;

public class PowerActivity extends Activity {
    private final String TAG = "PowerActivity";

    private ImageView mView;

    private Pattern mPattern;

    private ArrayList<Class<? extends Pattern>> mPatternList = new ArrayList<Class<? extends Pattern>>();
    private int mIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.v(TAG, "onCreate()");

        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_power);

        mView = (ImageView) findViewById(R.id.power_pattern);

        mPatternList.add(PowerRedPattern.class);
        mPatternList.add(PowerGreenPattern.class);
        mPatternList.add(PowerBluePattern.class);
        mPatternList.add(PowerBlackPattern.class);
        mPatternList.add(PowerWhitePattern.class);
        mPatternList.add(Power1Pattern.class);
        mPatternList.add(Power2Pattern.class);
        mPatternList.add(Power3Pattern.class);

        if (mIndex >= mPatternList.size()) {
            mIndex = 0;
        }

        try {
            mPattern = (mPatternList.get(mIndex)).newInstance();
            mPattern.setPattern(PowerActivity.this, mView);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        mView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mPattern.destroy();

                if (mIndex >= mPatternList.size()) {
                    mIndex = 0;
                }

                try {
                    mPattern = (mPatternList.get(mIndex)).newInstance();
                    mPattern.setPattern(PowerActivity.this, (ImageView)v);
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }

                mIndex++;
            }
        });
    }

    
}
