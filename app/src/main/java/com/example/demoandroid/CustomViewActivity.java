package com.example.demoandroid;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import com.example.demoandroid.view.EditTextLayout;

public class CustomViewActivity extends Activity {
    private Button btnTest;
    private TextView tvHello;
    private EditTextLayout editTextLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_view);
        btnTest = findViewById(R.id.btnTest);
        tvHello = findViewById(R.id.tvHello);
        editTextLayout = findViewById(R.id.editTextLayout);

        initEditLayout();


        btnTest.setOnClickListener(v -> {
            editTextLayout.moveTextViewOnce();
        });

        editTextLayout.setOnFocusChangeListener((v, hasFocus) -> {
            if(hasFocus){
                editTextLayout.setEditTextHint("");
                editTextLayout.moveTextViewOnce();
            }
        });


    }

    private void initEditLayout() {
        editTextLayout.setLineActiveColor(Color.parseColor("#5124E8"));
        editTextLayout.setLineColor(Color.parseColor("#DFE2E5"));
    }
}
