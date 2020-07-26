package com.example.demoandroid.view.drawerlayout;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.ViewCompat;
import androidx.customview.widget.ViewDragHelper;

import com.example.demoandroid.R;


/**
 * 侧边栏
 */
public class WMDrawerLayout2 extends FrameLayout {
    private View leftMenu;
    private View middleMenu;

    private ViewDragHelper dragHelper;



    private float percent = 0.7f;      //左侧菜单的宽度的百分比（就是整个是整个宽度的百分比）


    public WMDrawerLayout2(@NonNull Context context) {
        super(context);
        initLayout(context,null);
    }

    public WMDrawerLayout2(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initLayout(context,attrs);
    }

    private void initLayout(Context context, AttributeSet attrs) {
        leftMenu = LayoutInflater.from(context).inflate(R.layout.left_menu_layout,null);
        middleMenu = LayoutInflater.from(context).inflate(R.layout.middle_menu_layout,null);

        addView(middleMenu);
        addView(leftMenu);

        dragHelper = ViewDragHelper.create(this, new ViewDragHelper.Callback() {
            @Override
            public boolean tryCaptureView(@NonNull View child, int pointerId) {
                return child == middleMenu;
            }

            @Override
            public int clampViewPositionHorizontal(@NonNull View child, int left, int dx) {
                Log.d("zh","left=" + left);
                return left;
            }

            // 拖动结束后调用
            @Override
            public void onViewReleased(View releasedChild, float xvel, float yvel) {
                super.onViewReleased(releasedChild, xvel, yvel);
                //手指抬起后缓慢移动到指定位置
                if (leftMenu.getLeft() < 500) {
                    //关闭菜单
                    //相当于Scroller的startScroll方法
                    dragHelper.smoothSlideViewTo(leftMenu, 0, 0);
                    ViewCompat.postInvalidateOnAnimation(WMDrawerLayout2.this);
                } else {
                    //打开菜单
                    dragHelper.smoothSlideViewTo(leftMenu, 300, 0);
                    ViewCompat.postInvalidateOnAnimation(WMDrawerLayout2.this);
                }
            }

        });
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //1 决定自己的宽高
        setMeasuredDimension(widthMeasureSpec,heightMeasureSpec);

        //2 决定middleMenu宽高
        middleMenu.measure(widthMeasureSpec,heightMeasureSpec);

        //3 决定leftMenu宽度
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int realWidth = judgeLeftMenuWidth(width);
        int widthSpec = MeasureSpec.makeMeasureSpec(realWidth,MeasureSpec.EXACTLY);
        leftMenu.measure(widthSpec,heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

        //1 摆放middleMenu的位置
        middleMenu.layout(left,top,right,bottom);

        //2 摆放leftMenu的位置
        int leftWidth = leftMenu.getMeasuredWidth();
        leftMenu.layout(left - leftWidth,top,left,bottom);
    }

    /**
     * 决定leftMenu的宽度，以百分比为准，没有则以自己想要的宽度为准
     * @param width view自己想要的宽度
     * @return
     */
    private int judgeLeftMenuWidth(int width){
        if(percent > 0 && percent < 1){
            return (int) (percent * width);
        }

        return width;
    }


    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return dragHelper.shouldInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        dragHelper.processTouchEvent(event);
        return true;
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        super.computeScroll();
        if (dragHelper.continueSettling(true)) {
            ViewCompat.postInvalidateOnAnimation(this);
        }

    }
}













