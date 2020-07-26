package com.example.demoandroid;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import androidx.annotation.Nullable;
import com.example.demoandroid.view.drawerlayout.WMDrawerLayout;

public class WMDrawerActivity extends Activity {
    private WMDrawerLayout drawerLayout;
    private Button btnLeft;
    private Button btnMiddle;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_drawer_layout);

        drawerLayout = findViewById(R.id.drawlayout);

        btnMiddle = drawerLayout.findViewById(R.id.btnMiddle);
        btnLeft = drawerLayout.findViewById(R.id.btnLeft);
        btnMiddle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("zh33","btnMiddle clik");
            }
        });
        btnLeft.setOnClickListener(v -> Log.e("zh33","onClick..."));


    }
}
