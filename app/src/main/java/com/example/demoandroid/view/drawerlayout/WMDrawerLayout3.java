package com.example.demoandroid.view.drawerlayout;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Point;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.animation.DecelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.Scroller;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.demoandroid.GenericDrawerActivity;
import com.example.demoandroid.R;

import java.util.function.LongUnaryOperator;

public class WMDrawerLayout3 extends FrameLayout {
    private View leftMenu;
    private View mainView;
    private FrameLayout mainMask;

    private float percent;

    private int mLastX;
    private int mLastY;

    private GestureDetector gestureDetector;



    public WMDrawerLayout3(@NonNull Context context) {
        super(context);
        initView(context, null);
    }

    public WMDrawerLayout3(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context, attrs);
    }

    private void initView(Context context, AttributeSet attrs) {
        percent = 0.7f;
        gestureDetector = new GestureDetector(context,new GestureDetector.SimpleOnGestureListener(){
            @Override
            public boolean onDown(MotionEvent e) {
                return true;
            }

            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                if(leftMenu.getRight() == leftMenu.getWidth() && e.getX() > leftMenu.getWidth()){
                    closeDrawerLayout();
                }
                return super.onSingleTapUp(e);
            }
        });

        leftMenu = LayoutInflater.from(context).inflate(R.layout.left_menu_layout, null);
        mainView = LayoutInflater.from(context).inflate(R.layout.middle_menu_layout, null);
        mainMask = new FrameLayout(context);
        mainMask.setBackgroundColor(Color.parseColor("#814f4f4f"));

        addView(mainView);
        addView(mainMask);
        addView(leftMenu);

        mainMask.setAlpha(0);

        mainMask.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return gestureDetector.onTouchEvent(event);
            }
        });
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //1 决定自己的宽高
        setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);

        //2 决定 mainView 宽高
        mainView.measure(widthMeasureSpec, heightMeasureSpec);
        mainMask.measure(widthMeasureSpec, heightMeasureSpec);

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
        mainMask.layout(left, top, right, bottom);

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
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mLastX = (int) ev.getX();
                mLastY = (int) ev.getY();
                super.dispatchTouchEvent(ev);
                return true;
            case MotionEvent.ACTION_MOVE:
                int dx = Math.abs((int) ev.getX() - mLastX);
                int dy = Math.abs((int) ev.getY() - mLastY);
                if (dx > dy) {//水平
                    int left, top, right, bottom;
                    if (ev.getX() < mLastX) { //向左
                        left = leftMenu.getLeft() - dx;
                        if (left < -leftMenu.getWidth()) {
                            left = -leftMenu.getWidth();
                        }
                    } else {//向右
                        left = leftMenu.getLeft() + dx;
                        if (left > 0) {
                            left = 0;
                        }
                    }
                    top = leftMenu.getTop();
                    right = left + leftMenu.getWidth();
                    bottom = leftMenu.getBottom();
                    leftMenu.layout(left, top, right, bottom);
                    float alpha = (float) leftMenu.getRight() / (float) leftMenu.getWidth();
                    mainMask.setAlpha(Math.abs(alpha));
                }
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                springBack();
                mLastX = (int) ev.getX();
                mLastY = (int) ev.getY();
        }
        return super.dispatchTouchEvent(ev);
    }

    //回弹
    private void springBack(){
        int right = leftMenu.getRight();
        int width = leftMenu.getWidth();
        ValueAnimator valueAnimator;
        if (right >= width / 2) { //大于一半
            valueAnimator = ValueAnimator.ofInt(right, width).setDuration(170);
        } else {//小于一半
            valueAnimator = ValueAnimator.ofInt(right, 0).setDuration(170);
        }
        valueAnimator.addUpdateListener(animation -> {
            int curRight = (int) animation.getAnimatedValue();
            int left = curRight - width;
            int top = leftMenu.getTop();
            int bottom = leftMenu.getBottom();
            leftMenu.layout(left, top, curRight, bottom);
            float alpha = (float) leftMenu.getRight() / (float) leftMenu.getWidth();
            mainMask.setAlpha(Math.abs(alpha));
        });
        valueAnimator.start();
    }

    private void openDrawerLayout(){
        ValueAnimator valueAnimator = ValueAnimator.ofInt(0, leftMenu.getWidth()).setDuration(200);
        valueAnimator.addUpdateListener(animation -> {
            int curRight = (int) animation.getAnimatedValue();
            int left = curRight - leftMenu.getWidth();
            int top = leftMenu.getTop();
            int bottom = leftMenu.getBottom();
            leftMenu.layout(left, top, curRight, bottom);
            float alpha = (float) leftMenu.getRight() / (float) leftMenu.getWidth();
            mainMask.setAlpha(Math.abs(alpha));
        });
        valueAnimator.start();
    }

    private void closeDrawerLayout(){
        Log.e("zh11","closeDrawerLayout");
        ValueAnimator valueAnimator = ValueAnimator.ofInt(leftMenu.getWidth(),0).setDuration(200);
        valueAnimator.addUpdateListener(animation -> {
            int curRight = (int) animation.getAnimatedValue();
            int left = curRight - leftMenu.getWidth();
            int top = leftMenu.getTop();
            int bottom = leftMenu.getBottom();
            leftMenu.layout(left, top, curRight, bottom);
            float alpha = (float) leftMenu.getRight() / (float) leftMenu.getWidth();
            mainMask.setAlpha(Math.abs(alpha));
        });
        valueAnimator.start();

    }

}
