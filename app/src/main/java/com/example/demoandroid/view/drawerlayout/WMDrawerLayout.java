package com.example.demoandroid.view.drawerlayout;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.demoandroid.R;


/**
 * 侧边栏
 */
public class WMDrawerLayout extends FrameLayout {
    private View leftMenu;
    private View middleMenu;


    private float percent = 0.7f;      //左侧菜单的宽度的百分比（就是整个是整个宽度的百分比）


    public WMDrawerLayout(@NonNull Context context) {
        super(context);
        initLayout(context,null);
    }

    public WMDrawerLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initLayout(context,attrs);
    }

    private void initLayout(Context context, AttributeSet attrs) {
        leftMenu = LayoutInflater.from(context).inflate(R.layout.left_menu_layout,null);
        middleMenu = LayoutInflater.from(context).inflate(R.layout.middle_menu_layout,null);

        addView(leftMenu);
        addView(middleMenu);
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
        leftMenu.layout(left - leftMenu.getMeasuredWidth(),top,left,bottom);
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
}













