package com.example.demoandroid.view;

import android.content.Context;
import android.graphics.Paint;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public class WeightTextView extends View {
    private TextPaint paint;

    public WeightTextView(Context context) {
        this(context,null);
    }

    public WeightTextView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,-1);
    }

    public WeightTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        paint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
        TextPaint paint2 = new TextPaint();
    }





}
