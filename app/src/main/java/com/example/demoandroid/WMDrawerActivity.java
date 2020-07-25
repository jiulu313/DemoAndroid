package com.example.demoandroid;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.demoandroid.view.SelectMailView;
import com.example.demoandroid.view.SpaceBetweenLayout;
import com.tencent.mmkv.MMKV;

public class WMDrawerActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer_layout);
    }
}