package com.example.demoandroid.util;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ScrollView;

public class BitmapUtil {

    //可用
    public static Bitmap getBitmapForView(View view) {
        Bitmap bitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(bitmap);//使用bitmap构建一个Canvas，绘制的所有内容都是绘制在此Bitmap上的
        Drawable bgDrawable = view.getBackground();
        bgDrawable.draw(c);//绘制背景
        view.draw(c);//绘制前景
        return bitmap;
    }

    public static Bitmap getBitmap2(View view){
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache(true);
        Bitmap b = Bitmap.createBitmap(view.getDrawingCache(),0,0,view.getWidth() ,view.getHeight() );
        view.setDrawingCacheEnabled(false);
        return b;
    }

    public static Bitmap getBitmap3(ScrollView scrollView){
        int h = 0;
        int childCount = scrollView.getChildCount();
        for(int i = 0 ; i < childCount;i++){
            h += scrollView.getChildAt(i).getMeasuredHeight();
        }

        Bitmap bitmap = Bitmap.createBitmap(scrollView.getWidth(),h, Bitmap.Config.ARGB_8888);
        Drawable drawable = scrollView.getBackground();
        drawable.setBounds(0,0,bitmap.getWidth(),bitmap.getHeight());
        Canvas canvas = new Canvas(bitmap);
        drawable.draw(canvas);               //绘制背景
        scrollView.draw(canvas);
        return bitmap;
    }

}
