package com.example.demoandroid.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.EditText;

import androidx.annotation.RequiresApi;


@SuppressLint("AppCompatCustomView")
public class IconEditText extends EditText {
    public IconEditText(Context context) {
        super(context);
    }

    public IconEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Drawable[] drawables = getCompoundDrawables();
        if(drawables == null || drawables.length == 0)
            return false;

        Drawable rightDrawable = drawables[2];
        if(rightDrawable == null)
            return false;

        Rect rect = rightDrawable.getBounds();
        if(rect == null){
            return false;
        }

        int drawableWidth = rect.right - rect.left;
        int x = (int) event.getX();

        //点击到右边的drawable上了
        if(x > (getWidth() - drawableWidth - 20) && event.getAction() == MotionEvent.ACTION_UP){
            Log.e("zh33","点击到了图标");
        }

        return super.onTouchEvent(event);
    }
}
