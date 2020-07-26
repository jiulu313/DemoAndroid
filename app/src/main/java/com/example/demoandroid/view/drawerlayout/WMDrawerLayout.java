package com.example.demoandroid.view.drawerlayout;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.demoandroid.R;

public class WMDrawerLayout extends FrameLayout {
    private View leftMenu;
    private View mainView;

    private float percent = 0.7f;
    private int sloup;

    public WMDrawerLayout(@NonNull Context context) {
        super(context);
        initView(context, null);
    }

    public WMDrawerLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context, attrs);
    }

    private void initView(Context context, AttributeSet attrs) {
        leftMenu = LayoutInflater.from(context).inflate(R.layout.left_menu_layout, null);
        mainView = LayoutInflater.from(context).inflate(R.layout.middle_menu_layout, null);

        addView(mainView);
        addView(leftMenu);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //1 决定自己的宽高
        setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);

        //2 决定 mainView 宽高
        mainView.measure(widthMeasureSpec, heightMeasureSpec);

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



}
