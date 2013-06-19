package com.auo.pg.pattern.optical.gray;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import com.auo.pg.R;
import com.auo.pg.pattern.Pattern;

public class OpticalGray95Pattern extends Pattern {

    @Override
    public void destroy() {
    }

    @Override
    public void setPattern(Context context, ImageView v) {
        Drawable drawable = context.getResources().getDrawable(R.drawable.gray95);
        v.setImageDrawable(drawable);
    }
}
