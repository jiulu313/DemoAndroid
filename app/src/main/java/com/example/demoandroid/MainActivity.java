package com.example.demoandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.view.View;
import android.widget.EditText;

import com.example.demoandroid.view.mail.MailSelectView;

public class MainActivity extends Activity {

    private MailSelectView mailSelectView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        editText = findViewById(R.id.editText);
//        editText.setFilters(new InputFilter[]{new OrderListInputFilter()});

        findViewById(R.id.btnTest).setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this,FirstActivity.class);
            startActivity(intent);
        });

        findViewById(R.id.btnCustomView).setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this,CustomViewActivity.class);
            startActivity(intent);
        });


    }
}
