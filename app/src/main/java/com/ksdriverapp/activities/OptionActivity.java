package com.ksdriverapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.LinearLayout;

import com.ksdriverapp.R;
import com.ksdriverapp.prefrences.SessionManager;

public class OptionActivity extends BaseActivity {

    private LinearLayout llCar, llBookDriver;
    private SessionManager sessionManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_option);
        try {
            initComponents();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initComponents() {
        llCar = findViewById(R.id.llCar);
        llBookDriver = findViewById(R.id.llBookDriver);
        sessionManager = new SessionManager(this);

        llCar.setOnClickListener(v -> {
            startActivity(new Intent(OptionActivity.this, CarSignUpActivity.class));
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        });

        llBookDriver.setOnClickListener(v -> {
            if (sessionManager.getUserId().equalsIgnoreCase("-1")) {
                startActivity(new Intent(OptionActivity.this, SignUpDriverActivity.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
             } else {
                startActivity(new Intent(OptionActivity.this, MainActivity.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
             }
        });
    }

}
