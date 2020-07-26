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

public class WMDrawerLayout extends FrameLayout {
    private View leftMenu;
    private View mainView;
    private FrameLayout mainMask;

    private float percent;
    private int touchSlop;

    private int mLastX;
    private int mLastY;

    private Scroller mScroller;

    public WMDrawerLayout(@NonNull Context context) {
        super(context);
        initView(context, null);
    }

    public WMDrawerLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context, attrs);
    }

    public View getLeftMenu(){
        return leftMenu;
    }

    public View getMainView(){
        return mainView;
    }

    private void initView(Context context, AttributeSet attrs) {
        mScroller = new Scroller(context,new DecelerateInterpolator());
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
    public void scrollTo(int x, int y) {
        super.scrollTo(x, y);
        int curX = getScrollX();
        float alpha = (float) curX / (float)leftMenu.getMeasuredWidth();
        Log.e("zh33","alpha=" + alpha);
        mainMask.setAlpha(Math.abs(alpha));
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


    //测试是一个什么样的事件
    private boolean isTestComplete;

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (!isTestComplete) {
            getEventType(ev);
            return true;
        }

        if (isHorizontalScroll) {
            switch (ev.getAction()) {
                case MotionEvent.ACTION_MOVE:
                    int curScrollX = getScrollX();
                    int disX = (int) (ev.getX() - point.x);
                    int expectX = -disX + curScrollX;
                    int finalX = 0;
                    if (expectX < 0) {//向左滑动
                        finalX = Math.max(expectX, -leftMenu.getMeasuredWidth());
                    } else {//向右滑动

                    }

                    scrollTo(finalX, 0);
                    point.x = (int) ev.getX();
                    point.y = (int) ev.getY();
                    break;
                case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_CANCEL:
                    curScrollX = getScrollX();
                    if(Math.abs(curScrollX) > (leftMenu.getMeasuredWidth() / 2 )){
                        if(curScrollX < 0){//左
                            mScroller.startScroll(curScrollX,0,-leftMenu.getMeasuredWidth() - curScrollX,0,150);
                        }else {//右
                            mScroller.startScroll(curScrollX,0,leftMenu.getMeasuredWidth() - curScrollX,0,150);
                        }
                    }else {
                        mScroller.startScroll(curScrollX,0,-curScrollX,150);//150ms
                    }
                    invalidate();

                    isHorizontalScroll = false;
                    isTestComplete = false;
                    break;
            }
        }else {
            switch (ev.getAction()){
                case MotionEvent.ACTION_UP:
                    isHorizontalScroll = false;
                    isTestComplete = false;
                    break;
            }
        }

        return super.dispatchTouchEvent(ev);
    }

    private Point point = new Point();
    private boolean isHorizontalScroll; //是否是水平滑块

    private void getEventType(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                point.x = (int) ev.getX();
                point.y = (int) ev.getY();
                super.dispatchTouchEvent(ev);
                break;
            case MotionEvent.ACTION_MOVE:
                int dx = (int) Math.abs(ev.getX() - point.x);
                int dy = (int) Math.abs(ev.getY() - point.y);
                if (dx > touchSlop && dx > dy) { // 水平滑动
                    isHorizontalScroll = true;
                    point.x = (int) ev.getX();
                    point.y = (int) ev.getY();
                    isTestComplete = true;
                } else if (dy > touchSlop && dy > dx) { //垂直滑块
                    isHorizontalScroll = false;
                    point.x = (int) ev.getX();
                    point.y = (int) ev.getY();
                    isTestComplete = true;
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                super.dispatchTouchEvent(ev);
                isHorizontalScroll = false;
                isTestComplete = false;
                break;

        }
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if(!mScroller.computeScrollOffset()){
            return;
        }

        int tempX = mScroller.getCurrX();
        scrollTo(tempX,0);
    }


}
