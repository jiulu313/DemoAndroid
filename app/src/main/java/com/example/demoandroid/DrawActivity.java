package com.example.demoandroid;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import com.example.demoandroid.view.SelectMailView;
import com.example.demoandroid.view.SpaceBetweenLayout;
import com.tencent.mmkv.MMKV;

public class DrawActivity extends Activity {
    private LinearLayout layout;

    @Override
    protected void onCreate(android.os.Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ScrollView scrollView = new ScrollView(this);
        layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        setContentView(scrollView);
        scrollView.addView(layout);

        addButton("GenericDrawer", GenericDrawerActivity.class);

        addButton("MaterialMenu", MaterialMenuActivity.class);
    }

    private void addButton(String name, final Class<?> cls) {
        Button btn = new Button(this);
        btn.setText(name);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DrawActivity.this, cls));
            }
        });
        layout.addView(btn, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
    }
}
