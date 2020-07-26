package com.example.demoandroid.view.drawerlayout;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Point;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.animation.DecelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.Scroller;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.demoandroid.R;

import java.util.function.LongUnaryOperator;

public class WMDrawerLayout3 extends FrameLayout {
    private View leftMenu;
    private View mainView;
    private FrameLayout mainMask;

    private float percent;
    private int touchSlop;

    private int mLastX;
    private int mLastY;


    public WMDrawerLayout3(@NonNull Context context) {
        super(context);
        initView(context, null);
    }

    public WMDrawerLayout3(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context, attrs);
    }

    private void initView(Context context, AttributeSet attrs) {
        touchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        percent = 0.7f;

        leftMenu = LayoutInflater.from(context).inflate(R.layout.left_menu_layout, null);
        mainView = LayoutInflater.from(context).inflate(R.layout.middle_menu_layout, null);
        mainMask = new FrameLayout(context);
        mainMask.setBackgroundColor(Color.parseColor("#4f4f4f"));

        addView(mainView);
        addView(leftMenu);
        addView(mainMask);

        mainMask.setAlpha(0);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //1 决定自己的宽高
        setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);

        //2 决定 mainView 宽高
        mainView.measure(widthMeasureSpec, heightMeasureSpec);
        mainMask.measure(widthMeasureSpec,heightMeasureSpec);

        //3 决定leftMenu宽度
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int realWidth = judgeLeftMenuWidth(width);
        int widthSpec = MeasureSpec.makeMeasureSpec(realWidth, MeasureSpec.EXACTLY);
        leftMenu.measure(widthSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

        //1 摆放 mainView 的位置
        mainView.layout(left, top, right, bottom);
        mainMask.layout(left,top,right,bottom);

        //2 摆放leftMenu的位置
        int leftWidth = leftMenu.getMeasuredWidth();
        leftMenu.layout(left - leftWidth, top, left, bottom);
    }

    private int judgeLeftMenuWidth(int width) {
        if (percent > 0 && percent < 1) {
            return (int) (percent * width);
        }

        return width;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.e("zh55","dispatchTouchEvent  action=" + ev.getAction());
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                mLastX = (int) ev.getX();
                mLastY = (int) ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                int dx = Math.abs((int) ev.getX() - mLastX);
                int dy = Math.abs((int) ev.getY() - mLastY);
                Log.e("zh55","dx=" + dx + "  dy=" + dy);
                if(dx > dy && dx > touchSlop){//水平
                    Log.e("zh55","水平");
                    int left,top,right,bottom;
                    if(ev.getX() < mLastX){ //向左
                        left = leftMenu.getLeft() - dx;
                    }else {//向右
                        left = leftMenu.getLeft() + dx;
                    }
                    top = leftMenu.getTop();
                    right = left + leftMenu.getWidth();
                    bottom = leftMenu.getBottom();
                    leftMenu.layout(left,top,right,bottom);
                }

                mLastX = (int) ev.getX();
                mLastY = (int) ev.getY();
                return true;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                break;

        }

        return super.dispatchTouchEvent(ev);
    }



}
