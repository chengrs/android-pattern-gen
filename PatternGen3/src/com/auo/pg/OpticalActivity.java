package com.auo.pg;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

import com.auo.pg.pattern.Pattern;
import com.auo.pg.pattern.optical.OpticalFlickerPattern;
import com.auo.pg.pattern.optical.OpticalPattern.OpticalPatternType;
import com.auo.pg.pattern.optical.color.OpticalGreenPattern;
import com.auo.pg.pattern.optical.color.OpticalRedPattern;
import com.auo.pg.pattern.optical.gray.OpticalGray127Pattern;
import com.auo.pg.pattern.optical.gray.OpticalGray159Pattern;
import com.auo.pg.pattern.optical.gray.OpticalGray191Pattern;
import com.auo.pg.pattern.optical.gray.OpticalGray233Pattern;
import com.auo.pg.pattern.optical.gray.OpticalGray31Pattern;
import com.auo.pg.pattern.optical.gray.OpticalGray63Pattern;
import com.auo.pg.pattern.optical.gray.OpticalGray95Pattern;
import com.auo.pg.pattern.optical.stick.OpticalStick2Pattern;
import com.auo.pg.pattern.optical.xtalk.OpticalXTalk1Pattern;
import com.auo.pg.pattern.optical.xtalk.OpticalXTalk2Pattern;

public class OpticalActivity extends NoTitleActivity {
    private final String TAG = "OpticalActivity";

    // constant values defined for handler to use
    private final static byte REFRESH_IMAGE = 0x01;

    private Timer mTimer = null;
    private TimerTask mTimerTask = null;

    private Handler mHandler;

    private ImageView mView;

    private Pattern mPattern;

    private ArrayList<Class<? extends Pattern>> mPatternList = new ArrayList<Class<? extends Pattern>>();

    private int mIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.v(TAG, "onCreate()");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_optical);

        mView = (ImageView) findViewById(R.id.optical_pattern);

        mHandler = new PatternHandler(OpticalActivity.this);

        setPatterns(getIntent().getByteExtra(OpticalPatternType.PATTERN_TYPE, OpticalPatternType.TYPE_DEFAULT));
    }

    private void setPatterns(byte type) {
        mPatternList.clear();
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
        mPatternList.add(OpticalRedPattern.class);
        mPatternList.add(OpticalGreenPattern.class);

        if (mIndex >= mPatternList.size()) {
            mIndex = 0;
        }

        try {
            mPattern = (mPatternList.get(mIndex)).newInstance();
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

                if (mIndex >= mPatternList.size()) {
                    mIndex = 0;
                }

                try {
                    mPattern = (mPatternList.get(mIndex)).newInstance();
                    mPattern.setPattern(OpticalActivity.this, (ImageView) v);
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
        mPatternList.add(OpticalFlickerPattern.class);

        if (mIndex >= mPatternList.size()) {
            mIndex = 0;
        }

        try {
            mPattern = (mPatternList.get(mIndex)).newInstance();
            mPattern.setPattern(OpticalActivity.this, mView);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        mView.setOnClickListener(null);
    }

    private void setGrayPattern() {
        mPatternList.add(OpticalGray233Pattern.class);
        mPatternList.add(OpticalGray191Pattern.class);
        mPatternList.add(OpticalGray159Pattern.class);
        mPatternList.add(OpticalGray127Pattern.class);
        mPatternList.add(OpticalGray95Pattern.class);
        mPatternList.add(OpticalGray63Pattern.class);
        mPatternList.add(OpticalGray31Pattern.class);

        showPattern();

        mView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mPattern.destroy();
                showPattern();
            }
        });
    }

    private void showPattern() {
        if (mIndex >= mPatternList.size()) {
            mIndex = 0;
        }

        try {
            mPattern = (mPatternList.get(mIndex)).newInstance();
            mPattern.setPattern(OpticalActivity.this, mView);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        mIndex++;
    }

    private void setXtalkPattern() {
        mPatternList.add(OpticalXTalk1Pattern.class);
        mPatternList.add(OpticalXTalk2Pattern.class);

        if (mIndex >= mPatternList.size()) {
            mIndex = 0;
        }

        try {
            mPattern = (mPatternList.get(mIndex)).newInstance();
            mPattern.setPattern(OpticalActivity.this, mView);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        mView.setOnClickListener(null);
    }

    private void setStickPattern() {
        // mColorPatternList.add(OpticalStick1Pattern.class);
        mPatternList.add(OpticalStick2Pattern.class);

        if (mIndex >= mPatternList.size()) {
            mIndex = 0;
        }

        try {
            mPattern = (mPatternList.get(mIndex)).newInstance();
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
