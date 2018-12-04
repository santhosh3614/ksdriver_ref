package com.ksdriverapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ksdriverapp.R;
import com.ksdriverapp.utils.PoupUtils;

/**
 * Created by SONI on 11/29/2018.
 */

public class SignUpActivity extends BaseActivity {
    private ImageView imgProfile;
    private EditText edtFirstName, edtLastName, edtMobile, edtEmail, edtState, edtCarNo, edtCarModel, edtAddress;
    private TextView txtSubmit;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        try {
            initComponents();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initComponents() {
        edtFirstName = findViewById(R.id.edtFirstName);
        edtLastName = findViewById(R.id.edtLastName);
        edtMobile = findViewById(R.id.edtMobile);
        edtEmail = findViewById(R.id.edtEmail);
        edtState = findViewById(R.id.edtState);
        edtCarModel = findViewById(R.id.edtCarModel);
        txtSubmit = findViewById(R.id.txtSubmit);
        imgProfile = findViewById(R.id.imgProfile);
        edtAddress = findViewById(R.id.edtAddress);

        imgProfile.setOnClickListener(v -> {
            Toast.makeText(this, "calling here", Toast.LENGTH_SHORT).show();
        });

        txtSubmit.setOnClickListener(v -> {
            String firstName = edtFirstName.getText().toString().trim();
            String lastName = edtLastName.getText().toString().trim();
            String mobile = edtMobile.getText().toString().trim();
            String email = edtEmail.getText().toString().trim();
            String state = edtState.getText().toString().trim();
            String carModel = edtCarModel.getText().toString().trim();
            String address = edtAddress.getText().toString().trim();
            if (TextUtils.isEmpty(firstName)) {
                PoupUtils.showAlertDailog(this, "Enter First Name");
            } else if (TextUtils.isEmpty(lastName)) {
                PoupUtils.showAlertDailog(this, "Enter Last Name");
            } else if (TextUtils.isEmpty(mobile)) {
                PoupUtils.showAlertDailog(this, "Enter Mobile");
            } else if (TextUtils.isEmpty(email)) {
                PoupUtils.showAlertDailog(this, "Enter email");
            } else if (TextUtils.isEmpty(state)) {
                PoupUtils.showAlertDailog(this, "Enter state");
            } else if (TextUtils.isEmpty(carModel)) {
                PoupUtils.showAlertDailog(this, "Enter car model");
            } else if (TextUtils.isEmpty(address)) {
                PoupUtils.showAlertDailog(this, "Enter Address");
            } else {
                startActivity(new Intent(this, MainActivity.class));
            }
        });
    }
}
