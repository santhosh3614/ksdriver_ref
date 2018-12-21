package com.ksdriverapp.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.ksdriverapp.R;
import com.ksdriverapp.models.SignUpModel;
import com.ksdriverapp.prefrences.SessionManager;
import com.ksdriverapp.retrofit.WsResponse;
import com.ksdriverapp.utils.PermisionUtils;
import com.ksdriverapp.utils.PoupUtils;
import com.ksdriverapp.utils.StaticUtils;

import dmax.dialog.SpotsDialog;

/**
 * Created by SONI on 11/29/2018.
 */

public class SignUpDriverActivity extends BaseActivity implements WsResponse {

    private static final int REQUEST_CAMERA = 1001;
    private static final int SELECT_FILE = 1002;
    private ImageView imgProfile;
    private EditText edtFullName, edtMobile, edtEmail, edtCity, edtAddress, edtLicenceNo, edtExpr;
    private TextView txtSubmit;
    private String selectedValue;
    private String filePath = "";
    private AlertDialog progressDialog;
    private SessionManager sessionManager;


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

        edtFullName = findViewById(R.id.edtFullName);
        edtMobile = findViewById(R.id.edtMobile);
        edtEmail = findViewById(R.id.edtEmail);
        edtCity = findViewById(R.id.edtCity);
        edtAddress = findViewById(R.id.edtAddress);
        edtLicenceNo = findViewById(R.id.edtLicenceNo);
        edtExpr = findViewById(R.id.edtExpr);

        txtSubmit = findViewById(R.id.txtSubmit);
        imgProfile = findViewById(R.id.imgProfile);
        progressDialog = new SpotsDialog(this, R.style.Custom);
        sessionManager = new SessionManager(this);

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
                        boolean result = PermisionUtils.checkPermission(this);
                        if (result)
                            galleryIntent();
                    });
        });

        txtSubmit.setOnClickListener(v -> {
            String fullName = edtFullName.getText().toString().trim();
            String mobile = edtMobile.getText().toString().trim();
            String email = edtEmail.getText().toString().trim();
            String city = edtCity.getText().toString().trim();
            String licNo = edtLicenceNo.getText().toString().trim();
            String address = edtAddress.getText().toString().trim();
            String expr = edtExpr.getText().toString().trim();


            if (TextUtils.isEmpty(fullName)) {
                PoupUtils.showAlertDailog(this, "Enter First Name");
            } else if (TextUtils.isEmpty(mobile)) {
                PoupUtils.showAlertDailog(this, "Enter Mobile");
            } else if (TextUtils.isEmpty(email)) {
                PoupUtils.showAlertDailog(this, "Enter email");
            } else if (TextUtils.isEmpty(city)) {
                PoupUtils.showAlertDailog(this, "Enter city");
            } else if (TextUtils.isEmpty(licNo)) {
                PoupUtils.showAlertDailog(this, "Enter licNo");
            } else if (TextUtils.isEmpty(expr)) {
                PoupUtils.showAlertDailog(this, "Enter Exprence.");
            } else if (TextUtils.isEmpty(address)) {
                PoupUtils.showAlertDailog(this, "Enter Address");
            } else if (TextUtils.isEmpty(filePath)) {
                PoupUtils.showAlertDailog(this, "Please select picture");
            } else {
                /* progressDialog.show();
                File file = new File(filePath);
                RequestBody reqFile = RequestBody.create(MediaType.parse("image/*"), file);
                MultipartBody.Part body = MultipartBody.Part.createFormData("vDriverImage", file.getName(), reqFile);
                RequestBody vDriverName = RequestBody.create(MediaType.parse("text/plain"), fullName);
                RequestBody vLicenceNumber = RequestBody.create(MediaType.parse("text/plain"), licNo);
                RequestBody iDriverContactNo = RequestBody.create(MediaType.parse("text/plain"), mobile);
                RequestBody txDriverAddress = RequestBody.create(MediaType.parse("text/plain"), address);
                RequestBody vDriverEmail = RequestBody.create(MediaType.parse("text/plain"), email);
                RequestBody vDriverExp = RequestBody.create(MediaType.parse("text/plain"), expr);
                RequestBody vCity = RequestBody.create(MediaType.parse("text/plain"), city);
                Map<String, RequestBody> map = new HashMap<>();
                map.put("vDriverName", vDriverName);
                map.put("vLicenceNumber", vLicenceNumber);
                map.put("iDriverContactNo", iDriverContactNo);
                map.put("txDriverAddress", txDriverAddress);
                map.put("vDriverEmail", vDriverEmail);
                map.put("vDriverExp", vDriverExp);
                map.put("vCity", vCity);
                Call loginWsCall = WsFactory.signUp(body, map);
                WsUtils.getReponse(loginWsCall, StaticUtils.REQUEST_SIGN_UP, this);*/
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
        Uri picUri = data.getData();
        filePath = StaticUtils.getPath(getApplicationContext(), picUri);
        imgProfile.setImageURI(picUri);
        Log.e("Image path", "" + filePath);
    }

    private void onCaptureImageResult(Intent data) {
        Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
        imgProfile.setImageBitmap(thumbnail);
        Uri tempUri = StaticUtils.getImageUriFromCameraBitmap(getApplicationContext(), thumbnail);
        filePath = StaticUtils.getPath(getApplicationContext(), tempUri);
        Log.e("Camera image", "" + filePath);
    }

    @Override
    public void successResponse(Object response, int code) {
        progressDialog.cancel();
        switch (code) {
            case StaticUtils.REQUEST_SIGN_UP:
                SignUpModel signUpModel = (SignUpModel) response;
                sessionManager.setUserId(signUpModel.getResponseData().getIDriverId() + "");
                Intent intent = new Intent(SignUpDriverActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        }
    }

    @Override
    public void failureRespons(Throwable error, int code) {
        progressDialog.cancel();
    }

}



