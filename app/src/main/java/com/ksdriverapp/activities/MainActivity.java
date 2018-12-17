package com.ksdriverapp.activities;

import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.text.TextUtils;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.ksdriverapp.R;
import com.ksdriverapp.fragments.MyProfileFragment;
import com.ksdriverapp.fragments.OnlieOffLineFragment;
import com.ksdriverapp.fragments.PaymetHistoryFragment;

public class MainActivity extends BaseActivity {

    private LinearLayout llProfie;
    private DrawerLayout drawer;
    private ImageView imgMenu, imgProfile;
    private TextView txtPaymentHistoy, txtSupport, txtTermAndCond, txtRegisterCar, txtLogOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        try {
            initComponents();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initComponents() {
        llProfie = findViewById(R.id.llProfie);
        drawer = findViewById(R.id.drawer_layout);
        imgMenu = findViewById(R.id.imgMenu);
        imgProfile = findViewById(R.id.imgProfile);
        txtPaymentHistoy = findViewById(R.id.txtPaymentHistoy);
        txtSupport = findViewById(R.id.txtSupport);
        txtTermAndCond = findViewById(R.id.txtTermAndCond);
        txtRegisterCar = findViewById(R.id.txtRegisterCar);
        txtLogOut = findViewById(R.id.txtLogOut);

        imgMenu.setOnClickListener(v -> {
            drawer.openDrawer(Gravity.START);
        });

        llProfie.setOnClickListener(v -> {
            closeDrawer();
            Bundle bundle = new Bundle();
            replaceFragmenr(MyProfileFragment.getInstance(), MyProfileFragment.TAG);
        });
        try {
            defaultCall();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void defaultCall() {

        txtPaymentHistoy.setOnClickListener(v -> {
            replaceFragmenr(PaymetHistoryFragment.getInstance(), PaymetHistoryFragment.TAG);
         });


        txtSupport.setOnClickListener(v -> {

        });


        txtTermAndCond.setOnClickListener(v -> {

        });


        txtLogOut.setOnClickListener(v -> {

        });


        Bundle bundle = new Bundle();
        replaceFragmenr(OnlieOffLineFragment.getInstance(bundle), OnlieOffLineFragment.TAG);
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    private void closeDrawer() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
    }

}
