package com.example.demoandroid;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ScrollView;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.example.demoandroid.util.BitmapUtil;

public class SavePictureActivity extends Activity implements View.OnClickListener {
    Button btnSave;
    Button btnTwoSave;
    WebView webView;
    ScrollView saveScrollView;
    ScrollView showScrollView;
    ImageView imageView;
    FrameLayout frameLayout;

    View tmpView;
    WebView tWebView;
    ScrollView tScrollView;





    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save);
        btnSave = findViewById(R.id.btnSave);
        btnTwoSave = findViewById(R.id.btnTwoSave);
        webView = findViewById(R.id.webView);
        saveScrollView = findViewById(R.id.saveScrollView);
        showScrollView = findViewById(R.id.showScrollView);
        imageView = findViewById(R.id.imageView);
        frameLayout = findViewById(R.id.frameLayout);
        btnSave.setOnClickListener(this);
        btnTwoSave.setOnClickListener(this);

        tmpView = LayoutInflater.from(this).inflate(R.layout.tmp_bitmap_layout,frameLayout);
        tWebView = tmpView.findViewById(R.id.tWebView);
        tScrollView = tmpView.findViewById(R.id.tScrollView);


        webView.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                Bitmap bitmap = BitmapUtil.getBitmap3(saveScrollView);
                imageView.setImageBitmap(bitmap);

                Log.e("zh33","saveScrollView.height=" + saveScrollView.getHeight() + "  imageView.height=" + imageView.getHeight());
            }
        });




//        tWebView.loadUrl("https://www.qq.com");
        webView.loadUrl("https://www.qq.com");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnSave:
                Bitmap bitmap = BitmapUtil.getBitmap3(saveScrollView);
                imageView.setImageBitmap(bitmap);
                break;
            case R.id.btnTwoSave:
                Bitmap bitmap3 = BitmapUtil.getBitmap3(tScrollView);
                imageView.setImageBitmap(bitmap3);

                break;
        }

    }
}
