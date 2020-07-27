package com.example.demoandroid;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import com.example.demoandroid.view.drawerlayout.WMDrawerLayout;
import com.example.demoandroid.view.drawerlayout.WMDrawerLayout3;

public class WMDrawerActivity extends Activity {
    private WMDrawerLayout3 drawerLayout;
    private Button btnLeft;
    private Button btnMiddle;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_drawer_layout);
        drawerLayout = findViewById(R.id.drawlayout);
        btnLeft = drawerLayout.findViewById(R.id.btnLeft);
        btnMiddle = drawerLayout.findViewById(R.id.btnMiddle);

        btnMiddle.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Toast.makeText(WMDrawerActivity.this,"中间的button",Toast.LENGTH_SHORT).show();
            }
        });
        btnLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(WMDrawerActivity.this,"侧边栏的button",Toast.LENGTH_SHORT).show();
            }
        });

    }
}
