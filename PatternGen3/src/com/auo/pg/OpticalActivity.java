package com.auo.pg;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;

import com.auo.pg.pattern.Pattern;
import com.auo.pg.pattern.optical.OpticalFlickerPattern;
import com.auo.pg.pattern.optical.OpticalPattern.OpticalPatternType;
import com.auo.pg.pattern.optical.color.OpticalGreenPattern;
import com.auo.pg.pattern.optical.color.OpticalRedPattern;
import com.auo.pg.pattern.optical.gray.OpticalGray31Pattern;
import com.auo.pg.pattern.optical.gray.OpticalGray63Pattern;
import com.auo.pg.pattern.optical.gray.OpticalGray95Pattern;
import com.auo.pg.pattern.optical.stick.OpticalStick1Pattern;
import com.auo.pg.pattern.optical.stick.OpticalStick2Pattern;
import com.auo.pg.pattern.optical.xtalk.OpticalXTalk1Pattern;
import com.auo.pg.pattern.optical.xtalk.OpticalXTalk2Pattern;

public class OpticalActivity extends Activity {
    private final String TAG = "OpticalActivity";

    // constant values defined for handler to use
    private final static byte REFRESH_IMAGE = 0x01;

    private Timer mTimer = null;
    private TimerTask mTimerTask = null;

    private Handler mHandler;

    private ImageView mView;

    private Pattern mPattern;

    private ArrayList<Class<? extends Pattern>> mColorPatternList = new ArrayList<Class<? extends Pattern>>();
    private ArrayList<Class<? extends Pattern>> mGrayPatternList = new ArrayList<Class<? extends Pattern>>();
    private ArrayList<Class<? extends Pattern>> mXTalkPatternList = new ArrayList<Class<? extends Pattern>>();
    private ArrayList<Class<? extends Pattern>> mImageStickPatternList = new ArrayList<Class<? extends Pattern>>();

    private int mIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.v(TAG, "onCreate()");

        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_optical);

        mView = (ImageView) findViewById(R.id.optical_pattern);

        mHandler = new PatternHandler(OpticalActivity.this);

        setPatterns(getIntent().getByteExtra(OpticalPatternType.PATTERN_TYPE, OpticalPatternType.TYPE_DEFAULT));
    }

    private void setPatterns(byte type) {
        mColorPatternList.clear();
        mIndex = 0;

        switch (type) {
        case OpticalPatternType.TYPE_GRAY:
            setGrayPattern();
            break;
        case OpticalPatternType.TYPE_XTALK:
            setXtalkPattern();
            break;
        case OpticalPatternType.TYPE_FLICKER:
            setFlickerPattern();
            break;
        case OpticalPatternType.TYPE_STICK:
            setStickPattern();
            startTimer(type);
            break;
        case OpticalPatternType.TYPE_DEFAULT:
        case OpticalPatternType.TYPE_COLOR:
        default:
            setColorPatterns();
            startTimer(type);
            break;
        }
    }

    private void setColorPatterns() {
        mColorPatternList.add(OpticalRedPattern.class);
        mColorPatternList.add(OpticalGreenPattern.class);

        if (mIndex >= mColorPatternList.size()) {
            mIndex = 0;
        }

        try {
            mPattern = (mColorPatternList.get(mIndex)).newInstance();
            mPattern.setPattern(OpticalActivity.this, mView);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        mView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mPattern.destroy();

                if (mIndex >= mColorPatternList.size()) {
                    mIndex = 0;
                }

                try {
                    mPattern = (mColorPatternList.get(mIndex)).newInstance();
                    mPattern.setPattern(OpticalActivity.this, (ImageView)v);
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }

                mIndex++;
            }
        });
    }

    private void setFlickerPattern() {
        mColorPatternList.add(OpticalFlickerPattern.class);

        if (mIndex >= mColorPatternList.size()) {
            mIndex = 0;
        }

        try {
            mPattern = (mColorPatternList.get(mIndex)).newInstance();
            mPattern.setPattern(OpticalActivity.this, mView);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        mView.setOnClickListener(null);
    }

    private void setGrayPattern() {
        mColorPatternList.add(OpticalGray31Pattern.class);
        mColorPatternList.add(OpticalGray63Pattern.class);
        mColorPatternList.add(OpticalGray95Pattern.class);

        if (mIndex >= mColorPatternList.size()) {
            mIndex = 0;
        }

        try {
            mPattern = (mColorPatternList.get(mIndex)).newInstance();
            mPattern.setPattern(OpticalActivity.this, mView);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        mView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mPattern.destroy();

                if (mIndex >= mColorPatternList.size()) {
                    mIndex = 0;
                }

                try {
                    mPattern = (mColorPatternList.get(mIndex)).newInstance();
                    mPattern.setPattern(OpticalActivity.this, (ImageView)v);
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }

                mIndex++;
            }
        });
    }

    private void setXtalkPattern() {
        mColorPatternList.add(OpticalXTalk1Pattern.class);
        mColorPatternList.add(OpticalXTalk2Pattern.class);

        if (mIndex >= mColorPatternList.size()) {
            mIndex = 0;
        }

        try {
            mPattern = (mColorPatternList.get(mIndex)).newInstance();
            mPattern.setPattern(OpticalActivity.this, mView);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        mView.setOnClickListener(null);
    }

    private void setStickPattern() {
//        mColorPatternList.add(OpticalStick1Pattern.class);
        mColorPatternList.add(OpticalStick2Pattern.class);

        if (mIndex >= mColorPatternList.size()) {
            mIndex = 0;
        }

        try {
            mPattern = (mColorPatternList.get(mIndex)).newInstance();
            mPattern.setPattern(OpticalActivity.this, mView);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        mView.setOnClickListener(null);
    }

    private void startTimer(byte type) {
        if ((type == OpticalPatternType.TYPE_GRAY) || (type == OpticalPatternType.TYPE_FLICKER)) {
            return;
        }

        if (mTimer != null) {
            mTimer.cancel();
            mTimer = null;
        }

        if (mTimerTask != null) {
            mTimerTask.cancel();
            mTimerTask = null;
        }

        mTimer = new Timer();
        mTimerTask = new TimerTask() {
            @Override
            public void run() {
                Message message = mHandler.obtainMessage(REFRESH_IMAGE);
                mHandler.sendMessage(message);
            }
        };

        switch (type) {
        case OpticalPatternType.TYPE_COLOR:
            mTimer.schedule(mTimerTask, mPattern.mInterval, mPattern.mInterval);
            break;
        case OpticalPatternType.TYPE_STICK:
            mTimer.schedule(mTimerTask, mPattern.mInterval);
            break;
        }
    }

    static class PatternHandler extends Handler {
        private final WeakReference<OpticalActivity> mActivity;

        public PatternHandler(OpticalActivity activity) {
            mActivity = new WeakReference<OpticalActivity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            OpticalActivity activity = mActivity.get();

            switch (msg.what) {
            case REFRESH_IMAGE:
                activity.mPattern.setPattern(activity, activity.mView);
                break;
            }
        }
    }
}
