package com.ksdriverapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import com.ksdriverapp.R;

/**
 * Created by SONI on 11/29/2018.
 */

public class SplashActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        try {
            initComponents();
           } catch (Exception e) {
            e.printStackTrace();
           }
    }

    @Override
    public void initComponents() {
        boolean handler = new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this, SignUpActivity.class);
                startActivity(intent);
                finish();
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
             }
         }, 2 * 1000);
     }
}
