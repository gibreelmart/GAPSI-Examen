package com.gibreelm.gapsi.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

public class SplashScreenActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new Handler().postDelayed (() -> {
            Intent i = new Intent (SplashScreenActivity.this, MainActivity.class);
            startActivity(i);
            finish ();
        }, 2500);
    }

    @Override
    public void onBackPressed() {
        try {
        } catch (Exception ex) {
        }
    }

}