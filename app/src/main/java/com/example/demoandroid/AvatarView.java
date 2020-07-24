package com.example.demoandroid;


import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Shader;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;

public class AvatarView extends androidx.appcompat.widget.AppCompatImageView {
    private String zhText;          //中文头像
    private String enText;          //英文头像
    private Bitmap defaultBitmap;   //默认头像
    private Bitmap networkBitmap;   //网络头像

    private int backgroundColor;    //背景色
    private int zhTextColor;        //中文颜色
    private int enTextColor;        //英文颜色
    private int zhTextSize;         //中文字体大小,px
    private int enTextSize;         //英文字体大小,px

    private BitmapShader mBitmapShader;
    private Paint mTextPaint;
    private Paint mBitmapPaint;

    public AvatarView(Context context) {
        this(context,null);
    }

    public AvatarView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context,attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mBitmapPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

        backgroundColor = Color.parseColor("#ffffff");
        zhTextColor = Color.parseColor("#5124E8");
        enTextColor = Color.parseColor("#5124E8");
        zhTextSize = (int) sp2px(16);
        enTextSize = (int) sp2px(16);
    }

    public void setDefaultBitmap(Bitmap defaultBitmap){
        this.defaultBitmap = defaultBitmap;
        invalidate();
    }

    public void setNetworkBitmap(Bitmap networkBitmap){
        this.networkBitmap = networkBitmap;
        invalidate();
    }

    public void setChineseText(String zhText){
        this.zhText = zhText;
        invalidate();
    }

    public void setEnglishText(String enText){
        this.enText = enText;
        invalidate();
    }

    public void setBackgroundColor(int color){
        backgroundColor = color;
        invalidate();
    }

    public void setZhTextColor(int zhTextColor) {
        this.zhTextColor = zhTextColor;
        invalidate();
    }

    public void setEnTextColor(int enTextColor) {
        this.enTextColor = enTextColor;
        invalidate();
    }

    public void setZhTextSize(int zhTextSize) {
        this.zhTextSize = zhTextSize;
        invalidate();
    }

    public void setEnTextSize(int enTextSize) {
        this.enTextSize = enTextSize;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if(networkBitmap != null){
            drawNetworkBitmap(canvas);
        }else if(!isEmpty(zhText)){
            drawZhText(canvas);
        }else if(!isEmpty(enText)){
            drawEnText(canvas);
        }else {
            drawDefaultBitmap(canvas);
        }
    }

    //画图片(网络)
    private void drawNetworkBitmap(Canvas canvas) {
        drawBitmap(canvas,false);
    }

    //画默认图标
    private void drawDefaultBitmap(Canvas canvas) {
        drawBitmap(canvas,true);
    }

    private void drawBitmap(Canvas canvas,boolean isDefaultBitmap){
        Bitmap bm = isDefaultBitmap ? defaultBitmap : networkBitmap;
        mBitmapShader = new BitmapShader(bm, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        mBitmapPaint.setShader(mBitmapShader);
        mBitmapPaint.setStyle(Paint.Style.FILL);

        int radius = getWidth() > getHeight() ? getHeight() / 2 : getWidth() / 2;
        int cx = getWidth() / 2;
        int cy = getHeight() / 2;
        canvas.drawCircle(cx,cy,radius,mBitmapPaint);
    }

    //画中文
    private void drawZhText(Canvas canvas) {
        drawText(canvas,true);
    }

    //画英文
    private void drawEnText(Canvas canvas) {
        drawText(canvas,false);
    }

    private void drawText(Canvas canvas,boolean isChinese){
        String text = isChinese ? zhText : enText;
        int textColor = isChinese ? zhTextColor : enTextColor;
        int textSize = isChinese ? zhTextSize : enTextSize;

        //1 画背景
        int cx = getWidth() / 2;
        int cy = getHeight() / 2;
        int radius = getWidth() > getHeight() ? getHeight() / 2 : getWidth() / 2;
        mTextPaint.setColor(backgroundColor);
        canvas.drawCircle(cx,cy,radius,mTextPaint);

        //2 写字
        mTextPaint.setColor(textColor);
        mTextPaint.setTextSize(textSize);
        int baseX = (int) (canvas.getWidth() / 2 - mTextPaint.measureText(text) / 2);
        int baseY = (int) ((canvas.getHeight() / 2) - ((mTextPaint.descent() + mTextPaint.ascent()) / 2));
        canvas.drawText(text, baseX, baseY, mTextPaint);
    }

    private boolean isEmpty(String text){
        if(text == null || "".equals(text)){
            return true;
        }

        return false;
    }

    private float sp2px(float sp){
        float scale = getResources().getDisplayMetrics().scaledDensity;
        return (int) (sp * scale + 0.5f);
    }
}
