package com.example.mvp_bill.view;

import android.content.Intent;
import android.os.Bundle;


import com.example.mvp_bill.R;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class WelcomeView extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcom_view);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        Intent intent = new Intent(this,LoginActivity.class);
        new Thread(() -> {
            try {
                Thread.sleep(2000);
                startActivity(intent);
                finish();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }
}
