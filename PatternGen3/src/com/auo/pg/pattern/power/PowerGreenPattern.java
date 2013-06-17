package com.auo.pg.pattern.power;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import com.auo.pg.R;
import com.auo.pg.pattern.Pattern;

public class PowerGreenPattern extends Pattern {

    @Override
    public void destroy() {
    }

    @Override
    public void setPattern(Context context, ImageView v) {
        Drawable drawable = context.getResources().getDrawable(R.drawable.green);
        v.setImageDrawable(drawable);
    }
}
