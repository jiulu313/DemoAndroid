package com.example.demoandroid;

import android.app.Application;
import android.util.Log;

import com.tencent.mmkv.MMKV;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        String rootDir = MMKV.initialize(this);

        Log.e("zh","dir=" + rootDir);
    }
}
