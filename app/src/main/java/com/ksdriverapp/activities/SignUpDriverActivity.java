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
import com.ksdriverapp.models.CityListModel;
import com.ksdriverapp.models.SignUpModel;
import com.ksdriverapp.models.StateModel;
import com.ksdriverapp.prefrences.SessionManager;
import com.ksdriverapp.retrofit.WsFactory;
import com.ksdriverapp.retrofit.WsResponse;
import com.ksdriverapp.retrofit.WsUtils;
import com.ksdriverapp.utils.PermisionUtils;
import com.ksdriverapp.utils.PoupUtils;
import com.ksdriverapp.utils.StaticUtils;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import dmax.dialog.SpotsDialog;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;


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
    private TextView txtState, txtCity;
    private String stateId = "0";

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
        edtAddress = findViewById(R.id.edtAddress);
        edtLicenceNo = findViewById(R.id.edtLicenceNo);
        edtExpr = findViewById(R.id.edtExpr);
        txtSubmit = findViewById(R.id.txtSubmit);
        imgProfile = findViewById(R.id.imgProfile);
        txtState = findViewById(R.id.txtState);
        txtCity = findViewById(R.id.txtCity);
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
        txtState.setOnClickListener(v -> {
            progressDialog.show();
            Call sateList = WsFactory.getSatate();
            WsUtils.getReponse(sateList, StaticUtils.REQUEST_STATE_LIST, this);
        });
        txtCity.setOnClickListener(v -> {
            progressDialog.show();
            Map<String, String> map = new HashMap<>();
            map.put("iStatesId", stateId);
            Call cityListModel = WsFactory.getCityState(map);
            WsUtils.getReponse(cityListModel, StaticUtils.REQUEST_STATE_CITY, this);
        });

        txtSubmit.setOnClickListener(v -> {
            String fullName = edtFullName.getText().toString().trim();
            String mobile = edtMobile.getText().toString().trim();
            String email = edtEmail.getText().toString().trim();
            String licNo = edtLicenceNo.getText().toString().trim();
            String address = edtAddress.getText().toString().trim();
            String expr = edtExpr.getText().toString().trim();
            String state = txtState.getText().toString().trim();
            String city = txtCity.getText().toString().trim();

            if (TextUtils.isEmpty(fullName)) {
                PoupUtils.showAlertDailog(this, "Enter First Name");
            } else if (TextUtils.isEmpty(mobile)) {
                PoupUtils.showAlertDailog(this, "Enter Mobile");
            } else if (TextUtils.isEmpty(email)) {
                PoupUtils.showAlertDailog(this, "Enter email");
            } else if (TextUtils.isEmpty(state)) {
                PoupUtils.showAlertDailog(this, "Select state");
            } else if (TextUtils.isEmpty(city)) {
                PoupUtils.showAlertDailog(this, "Select city");
            } else if (TextUtils.isEmpty(licNo)) {
                PoupUtils.showAlertDailog(this, "Enter licNo");
            } else if (TextUtils.isEmpty(expr)) {
                PoupUtils.showAlertDailog(this, "Enter Exprence.");
            } else if (TextUtils.isEmpty(address)) {
                PoupUtils.showAlertDailog(this, "Enter Address");
            } else if (TextUtils.isEmpty(filePath)) {
                PoupUtils.showAlertDailog(this, "Please select picture");
            } else {
                progressDialog.show();
                File file = new File(filePath);
                //This is
                RequestBody reqFile = RequestBody.create(MediaType.parse("image/*"), file);
                MultipartBody.Part body = MultipartBody.Part.createFormData("vDriverImage", file.getName(), reqFile);

                //This is strin value change in RequestBody
                RequestBody vDriverName = RequestBody.create(MediaType.parse("text/plain"), fullName);
                RequestBody vLicenceNumber = RequestBody.create(MediaType.parse("text/plain"), licNo);
                RequestBody iDriverContactNo = RequestBody.create(MediaType.parse("text/plain"), mobile);
                RequestBody txDriverAddress = RequestBody.create(MediaType.parse("text/plain"), address);
                RequestBody vDriverEmail = RequestBody.create(MediaType.parse("text/plain"), email);
                RequestBody vDriverExp = RequestBody.create(MediaType.parse("text/plain"), expr);
                RequestBody vCity = RequestBody.create(MediaType.parse("text/plain"), city);
                RequestBody vState = RequestBody.create(MediaType.parse("text/plain"), state);
                RequestBody txDeviceToken = RequestBody.create(MediaType.parse("text/plain"), sessionManager.getNotificationToken());

                Map<String, RequestBody> map = new HashMap<>();
                map.put("vDriverName", vDriverName);
                map.put("vLicenceNumber", vLicenceNumber);
                map.put("iDriverContactNo", iDriverContactNo);
                map.put("txDriverAddress", txDriverAddress);
                map.put("vDriverEmail", vDriverEmail);
                map.put("vDriverExp", vDriverExp);
                map.put("vCity", vCity);
                map.put("vState", vState);
                map.put("txDeviceToken", txDeviceToken);
                Call loginWsCall = WsFactory.signUp(body, map);
                WsUtils.getReponse(loginWsCall, StaticUtils.REQUEST_SIGN_UP, this);
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
                break;
            case StaticUtils.REQUEST_STATE_LIST:
                StateModel stateModel = (StateModel) response;
                if (stateModel != null) {
                    PoupUtils.showState(SignUpDriverActivity.this, "Select State", stateModel.getResponseData(), (view, pos) -> {
                        StateModel.ResponseDatum responseDatum = stateModel.getResponseData().get(pos);
                        stateId = responseDatum.getIStatesId();
                        txtState.setText(responseDatum.getVStateName());
                    });
                }
                break;
            case StaticUtils.REQUEST_STATE_CITY:
                CityListModel cityListModel = (CityListModel) response;
                if (cityListModel != null) {
                    PoupUtils.showCity(SignUpDriverActivity.this, "Select City", cityListModel.getResponseData(), (view, pos) -> {
                        CityListModel.ResponseDatum responseDatum = cityListModel.getResponseData().get(pos);
                        txtCity.setText(responseDatum.getVCityName());
                    });
                }
                break;
        }
    }

    @Override
    public void failureRespons(Throwable error, int code) {
        progressDialog.cancel();
    }

}



