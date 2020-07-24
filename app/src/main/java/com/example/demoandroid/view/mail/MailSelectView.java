package com.example.demoandroid.view.mail;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

/**
 * 邮件选择控件
 */
public class MailSelectView extends ViewGroup {
    private EditText editText;

    public MailSelectView(Context context) {
        super(context);
    }

    public MailSelectView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);



    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int ccCount = getChildCount();

        int x = getPaddingLeft();
        int y = getPaddingTop();

        for (int i =0;i < ccCount;i++){
            View child = getChildAt(i);

            MarginLayoutParams mp = (MarginLayoutParams) child.getLayoutParams();
            x += mp.leftMargin;
            y += mp.topMargin;
            child.layout(x,y,x + child.getMeasuredWidth(),y + child.getMeasuredHeight());
        }
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(),attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        EditText editText = new EditText(getContext());
        MarginLayoutParams mp = new MarginLayoutParams(MarginLayoutParams.MATCH_PARENT,MarginLayoutParams.MATCH_PARENT);
        mp.leftMargin = 40;
        mp.rightMargin = 40;
        addView(editText,mp);
    }
}



























