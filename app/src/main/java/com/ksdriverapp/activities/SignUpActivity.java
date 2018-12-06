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

public class SignUpActivity extends BaseActivity implements WsResponse {

    private static final int REQUEST_CAMERA = 1001;
    private static final int SELECT_FILE = 1002;
    private ImageView imgProfile;
    private EditText edtFirstName, edtLastName, edtMobile, edtEmail, edtState, edtCarNo, edtCarModel, edtAddress;
    private TextView txtSubmit;
    private String selectedValue;
    private String filePath = "";
    private AlertDialog progressDialog;


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
        progressDialog = new SpotsDialog(this, R.style.Custom);

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
            } else if (TextUtils.isEmpty(filePath)) {
                PoupUtils.showAlertDailog(this, "Please select picture");
            } else {
//                startActivity(new Intent(this, MainActivity.class));

                progressDialog.show();
                File file = new File(filePath);
                RequestBody reqFile = RequestBody.create(MediaType.parse("image/*"), file);
                MultipartBody.Part body = MultipartBody.Part.createFormData("vLicenceImage", file.getName(), reqFile);

                RequestBody vDriverName = RequestBody.create(MediaType.parse("text/plain"), firstName + " " + lastName);
                RequestBody iCarCetegoryId = RequestBody.create(MediaType.parse("text/plain"), "2");
                RequestBody vCarNumber = RequestBody.create(MediaType.parse("text/plain"), "sdfsdf154451");
                RequestBody iCarModel = RequestBody.create(MediaType.parse("text/plain"), "2016");
                RequestBody vLicenceNumber = RequestBody.create(MediaType.parse("text/plain"), "sdfsdf564645");
                RequestBody iDriverContactNo = RequestBody.create(MediaType.parse("text/plain"), "123456789");
                RequestBody txDriverAddress = RequestBody.create(MediaType.parse("text/plain"), "asdfasdfgsdfgdfgsdfg");
                RequestBody iDriverAlterContactNo = RequestBody.create(MediaType.parse("text/plain"), "315648474654");
                RequestBody vDriverEmail = RequestBody.create(MediaType.parse("text/plain"), "dhgfg@gmail.com");
                RequestBody vDriverExp = RequestBody.create(MediaType.parse("text/plain"), "5 yr");
                RequestBody vCity = RequestBody.create(MediaType.parse("text/plain"), "Ahemdabad");

                Map<String, RequestBody> map = new HashMap<>();
                map.put("vDriverName", vDriverName);
                map.put("iCarCetegoryId", iCarCetegoryId);
                map.put("vCarNumber", vCarNumber);
                map.put("iCarModel", iCarModel);
                map.put("vLicenceNumber", vLicenceNumber);
                map.put("iDriverContactNo", iDriverContactNo);
                map.put("txDriverAddress", txDriverAddress);
                map.put("iDriverAlterContactNo", iDriverAlterContactNo);
                map.put("vDriverEmail", vDriverEmail);
                map.put("vDriverExp", vDriverExp);
                map.put("vCity", vCity);
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
        /*ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
        File destination = new File(Environment.getExternalStorageDirectory(), System.currentTimeMillis() + ".jpg");
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
        }*/
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
                Log.e("Response", "" + signUpModel);
        }
    }

    @Override
    public void failureRespons(Throwable error, int code) {
        progressDialog.cancel();
    }

}



