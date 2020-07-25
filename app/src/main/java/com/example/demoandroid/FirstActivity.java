package com.example.demoandroid;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.demoandroid.view.SelectMailView;
import com.example.demoandroid.view.SpaceBetweenLayout;
import com.tencent.mmkv.MMKV;

import java.util.ArrayList;
import java.util.List;

public class FirstActivity extends Activity {
    Button btnTest;
    SelectMailView selectMailView;
    Button btnSave;
    Button btnDrawLayout;
    Button btnLayout;

    SpaceBetweenLayout betweenLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);
        btnTest = findViewById(R.id.btnTest);
        selectMailView = findViewById(R.id.selectMailView);
        betweenLayout = findViewById(R.id.betweenLayout);
        btnSave = findViewById(R.id.btnSave);
        btnDrawLayout = findViewById(R.id.btnDrawLayout);
        btnLayout = findViewById(R.id.btnLayout);

        MMKV kv = MMKV.defaultMMKV();
        kv.encode("name","tom");

        selectMailView.setOnRightIconClickListener(view -> {
            Toast.makeText(FirstActivity.this,"点击了加号",Toast.LENGTH_SHORT).show();

            Log.e("zh","name=" + kv.decodeString("name"));


        });
        btnTest.setOnClickListener(v -> onTest());

        btnSave.setOnClickListener(v->onSavePicture());

        btnDrawLayout.setOnClickListener(v -> {
            Intent intent = new Intent(FirstActivity.this,DrawActivity.class);
            startActivity(intent);
        });

        btnLayout.setOnClickListener(v -> {
            Intent intent = new Intent(FirstActivity.this,WMDrawerActivity.class);
            startActivity(intent);
        });
    }

    //保存长图
    private void onSavePicture() {
        Intent intent = new Intent(this,SavePictureActivity.class);
        startActivity(intent);
    }

    private void onTest() {
        selectMailView.addTextView("你好",true);


//        List<SpaceBetweenLayout.LabelTagBean> list = new ArrayList<>();
//        list.add(new SpaceBetweenLayout.LabelTagBean(101,"全部1"));
//        list.add(new SpaceBetweenLayout.LabelTagBean(102,"全部2"));
//        list.add(new SpaceBetweenLayout.LabelTagBean(103,"全部3"));
//        list.add(new SpaceBetweenLayout.LabelTagBean(104,"全部4"));
//        list.add(new SpaceBetweenLayout.LabelTagBean(105,"全部5"));
//        list.add(new SpaceBetweenLayout.LabelTagBean(106,"全部6"));
//        list.add(new SpaceBetweenLayout.LabelTagBean(107,"全部7"));
//        list.add(new SpaceBetweenLayout.LabelTagBean(108,"全部8"));
//        betweenLayout.addTags(list);
    }
}
