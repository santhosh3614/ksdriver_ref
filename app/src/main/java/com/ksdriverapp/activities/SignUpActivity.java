package com.ksdriverapp.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.ksdriverapp.R;
import com.ksdriverapp.utils.PermisionUtils;
import com.ksdriverapp.utils.PoupUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by SONI on 11/29/2018.
 */

public class SignUpActivity extends BaseActivity {

    private static final int REQUEST_CAMERA = 1001;
    private static final int SELECT_FILE = 1002;
    private ImageView imgProfile;
    private EditText edtFirstName, edtLastName, edtMobile, edtEmail, edtState, edtCarNo, edtCarModel, edtAddress;
    private TextView txtSubmit;
    private String selectedValue;

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
            PoupUtils.showCameraAndGallery(this, "Choose option",
                    camera -> {
                        selectedValue = camera.getTag().toString();
                        boolean result = PermisionUtils.checkPermission(this);
                        if (result)
                            cameraIntent();
                    },
                    gallary -> {
                        selectedValue = gallary.getTag().toString();
                        galleryIntent();
                    });
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

    private void cameraIntent() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_CAMERA);
    }

    private void galleryIntent() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);//
        startActivityForResult(Intent.createChooser(intent, "Select File"), SELECT_FILE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case PermisionUtils.MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (selectedValue.equals("Take Photo"))
                        cameraIntent();
                    else if (selectedValue.equals("Choose from Library"))
                        galleryIntent();
                } else {
                    //code for deny
                }
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == SELECT_FILE)
                onSelectFromGalleryResult(data);
            else if (requestCode == REQUEST_CAMERA)
                onCaptureImageResult(data);
        }
    }

    @SuppressWarnings("deprecation")
    private void onSelectFromGalleryResult(Intent data) {
        Bitmap bm = null;
        if (data != null) {
            try {
                bm = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), data.getData());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        imgProfile.setImageBitmap(bm);
    }

    private void onCaptureImageResult(Intent data) {
        Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
        File destination = new File(Environment.getExternalStorageDirectory(),
                System.currentTimeMillis() + ".jpg");
        FileOutputStream fo;
        try {
            destination.createNewFile();
            fo = new FileOutputStream(destination);
            fo.write(bytes.toByteArray());
            fo.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        imgProfile.setImageBitmap(thumbnail);
    }

}



