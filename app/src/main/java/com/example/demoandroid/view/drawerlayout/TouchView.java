package com.example.demoandroid.view.drawerlayout;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class TouchView extends FrameLayout {
    public TouchView(@NonNull Context context) {
        super(context);
    }

    public TouchView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {

        Log.e("zh88","dispatchTouchEvent action=" + ev.getActionMasked());

        return super.dispatchTouchEvent(ev);

    }
}
