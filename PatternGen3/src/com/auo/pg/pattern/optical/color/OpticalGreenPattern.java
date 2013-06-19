package com.auo.pg.pattern.optical.color;

import java.util.TimerTask;

import com.auo.pg.pattern.optical.OpticalPattern;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Bitmap.Config;
import android.util.DisplayMetrics;
import android.widget.ImageView;

public class OpticalGreenPattern extends OpticalPattern {
    @Override
    public void setPattern(Context context, ImageView v) {
        if (mBitmap == null) {
            create(context, 31);
        }

        if (mIsFirst) {
            mIsFirst = false;
            v.setImageBitmap(mBitmap);
        } else {
            mIsFirst = true;
            v.setImageBitmap(mBitmap2);
        }
    }

    private void create(Context context, int level) {
        mInterval = 3 * 1000;

        DisplayMetrics dm = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(dm);

        mHeight = dm.heightPixels;
        mWidth = dm.widthPixels;

        Paint paint = new Paint();
        paint.setARGB(255, 0, 255, 0);

        Config config = Config.ARGB_8888;
        mBitmap = Bitmap.createBitmap(mWidth, mHeight, config);
        Canvas canvas = new Canvas(mBitmap);
        canvas.drawPaint(paint);

        mBitmap2 = Bitmap.createBitmap(mWidth, mHeight, config);
        canvas = new Canvas(mBitmap2);
        paint.setARGB(255, level, level, level);
        canvas.drawPaint(paint);

        mTimerTask = new TimerTask() {
            @Override
            public void run() {
                // send message to handler
            }
        };
    }
}
