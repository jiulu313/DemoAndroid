package com.example.demoandroid.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Size;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.RequiresApi;

import com.example.demoandroid.util.SizeUtil;

public class ClickStatusView extends View {
    private boolean selected = true;

    private String text;
    private int textSize; //sp
    private int textColor;
    private int selectedTextColor;
    private int backgroundColor;
    private int selectedBackgroundColor;
    private int roundCorner; //圆角大小

    private Paint mPaint;
    private TextPaint mTextPaint;

    private OnClickListener onClickListener;

    public ClickStatusView(Context context) {
        this(context,null);
    }

    public ClickStatusView(Context context, AttributeSet attrs) {
        super(context, attrs);
        selectedBackgroundColor = Color.parseColor("#ff00ff");
        backgroundColor = Color.parseColor("#F0F0F0");
        roundCorner = SizeUtil.dip2px(context,17);
        textSize = 13;
        textColor = Color.parseColor("#454951");
        selectedTextColor = Color.parseColor("#ffffff");

        text = "未读邮件";

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setTextSize(SizeUtil.sp2px(context,textSize));
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onDraw(Canvas canvas) {
        if(selected){
            mPaint.setColor(selectedBackgroundColor);
            mTextPaint.setColor(selectedTextColor);
        }else {
            mPaint.setColor(backgroundColor);
            mTextPaint.setColor(textColor);
        }

        //1 画背景
        canvas.drawRoundRect(0,0,getWidth(),getHeight(),roundCorner,roundCorner,mPaint);

        //2 画字
        float fontWidth = mTextPaint.measureText(text);
        int baseX = (int) (canvas.getWidth() / 2 - fontWidth / 2);
        int baseY = (int) ((canvas.getHeight() / 2) - ((mTextPaint.descent() + mTextPaint.ascent()) / 2));
        canvas.drawText(text, baseX, baseY, mTextPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                selected = !selected;
                invalidate();
                if(onClickListener != null){
                    onClickListener.onClick(this);
                }
                break;
        }
        return super.onTouchEvent(event);
    }

    @Override
    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    @Override
    public boolean isSelected() {
        return selected;
    }

    @Override
    public void setSelected(boolean selected) {
        this.selected = selected;
        invalidate();
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
        invalidate();
    }

    public int getTextSize() {
        return textSize;
    }

    public void setTextSize(int textSize) {
        this.textSize = textSize;
        invalidate();
    }

    public int getTextColor() {
        return textColor;
    }

    public void setTextColor(int textColor) {
        this.textColor = textColor;
        invalidate();
    }

    public int getSelectedTextColor() {
        return selectedTextColor;
    }

    public void setSelectedTextColor(int selectedTextColor) {
        this.selectedTextColor = selectedTextColor;
        invalidate();
    }

    public int getBackgroundColor() {
        return backgroundColor;
    }

    @Override
    public void setBackgroundColor(int backgroundColor) {
        this.backgroundColor = backgroundColor;
        invalidate();
    }

    public int getSelectedBackgroundColor() {
        return selectedBackgroundColor;
    }

    public void setSelectedBackgroundColor(int selectedBackgroundColor) {
        this.selectedBackgroundColor = selectedBackgroundColor;
        invalidate();
    }

    public int getRoundCorner() {
        return roundCorner;
    }

    public void setRoundCorner(int roundCorner) {
        this.roundCorner = roundCorner;
        invalidate();
    }
}
