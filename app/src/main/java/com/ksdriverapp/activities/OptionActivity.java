package com.ksdriverapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.LinearLayout;

import com.ksdriverapp.R;

public class OptionActivity extends BaseActivity {

    private LinearLayout llCar, llBookDriver;


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
        llBookDriver = findViewById(R.id.llCar);

        llCar.setOnClickListener(v -> {
            startActivity(new Intent(OptionActivity.this, CarSignUpActivity.class));
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        });
        
        llBookDriver.setOnClickListener(v -> {
            startActivity(new Intent(OptionActivity.this, SignUpActivity.class));
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        });

    }


}