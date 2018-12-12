package com.ksdriverapp.utils;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.view.Window;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.ksdriverapp.R;

import java.util.Calendar;


public class PoupUtils {

    public static void showConfirmationDailog(Activity activity, String message, View.OnClickListener yesClick, View.OnClickListener noClick) {
        final Dialog dialog = new Dialog(activity, android.R.style.Theme_Black_NoTitleBar_Fullscreen);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawableResource(R.color.black_tran_60);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.layout_dialog);
        TextView txtYes = dialog.findViewById(R.id.txtYes);
        TextView txtNo = dialog.findViewById(R.id.txtNo);
        TextView txtTitle = dialog.findViewById(R.id.txtTitle);
        txtTitle.setText(message);
        txtYes.setOnClickListener(v -> {
            dialog.cancel();
            yesClick.onClick(txtYes);

        });
        txtNo.setOnClickListener(v -> {
            dialog.cancel();
            noClick.onClick(txtNo);
        });
        dialog.show();
    }

    public static void showCameraAndGallery(Activity activity, String message, View.OnClickListener cameraClick, View.OnClickListener galleryClick) {
        final Dialog dialog = new Dialog(activity, android.R.style.Theme_Black_NoTitleBar_Fullscreen);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawableResource(R.color.black_tran_60);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.layout_camera_galary_dialog);
        TextView txtCamera = dialog.findViewById(R.id.txtCamera);
        TextView txtGallery = dialog.findViewById(R.id.txtGallery);
        TextView txtTitle = dialog.findViewById(R.id.txtTitle);
        txtTitle.setText(message);
        txtCamera.setOnClickListener(v -> {
            txtCamera.setTag("Take Photo");
            dialog.cancel();
            cameraClick.onClick(txtCamera);
        });
        txtGallery.setOnClickListener(v -> {
            txtGallery.setTag("Choose from Library");
            dialog.cancel();
            galleryClick.onClick(txtGallery);
        });
        dialog.show();
    }


    public static void showAlertDailog(Activity activity, String message) {

        final Dialog dialog = new Dialog(activity, android.R.style.Theme_Black_NoTitleBar_Fullscreen);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawableResource(R.color.black_tran_30);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.layout_alert_dailog);
        TextView txtOK = dialog.findViewById(R.id.txtOK);
        TextView txtTitle = dialog.findViewById(R.id.txtTitle);
        txtTitle.setText(message);

        txtOK.setOnClickListener(v -> {
            dialog.cancel();

        });

        dialog.show();
    }


    public static void showDatePicker(Context context) {
        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(context,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                    }
                }, mYear, mMonth, mDay);

        datePickerDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "cancel", (v, which) -> {
            Toast.makeText(context, "click on cancel", Toast.LENGTH_SHORT).show();
        });
        datePickerDialog.setButton(DialogInterface.BUTTON_POSITIVE, "OK", (v, which) -> {
            showTimePicker(context);

        });
        datePickerDialog.show();
    }

    public static void showTimePicker(Context context) {
        final Calendar c = Calendar.getInstance();
        int mHour = c.get(Calendar.HOUR_OF_DAY);
        int mMinute = c.get(Calendar.MINUTE);
        TimePickerDialog timePickerDialog = new TimePickerDialog(context,
                (view, hourOfDay, minute) -> {
//                        txtTime.setText(hourOfDay + ":" + minute);
                }, mHour, mMinute, false);

        timePickerDialog.show();

        timePickerDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "cancel",
                (view, which) -> {

                });

        timePickerDialog.setButton(DialogInterface.BUTTON_POSITIVE, "cancel",
                (view, which) -> {

                });

    }

}


