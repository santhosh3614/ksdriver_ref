package com.ksdriverapp.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ksdriverapp.R;
import com.ksdriverapp.fragments.OnlieOffLineFragment;
import com.ksdriverapp.fragments.PaymetHistoryFragment;

public class MainActivity extends BaseActivity {

    private LinearLayout llProfie;
    private DrawerLayout drawer;
    private ImageView imgProfile;
    private TextView txtPaymentHistoy, txtSupport, txtTermAndCond, txtRegisterCar, txtLogOut;
    public ImageView imgMenu, imgNotification, imgBack;
    public TextView txtTitle;

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
        imgBack = findViewById(R.id.imgBack);
        imgNotification = findViewById(R.id.imgNotification);
        txtTitle = findViewById(R.id.txtTitle);

        imgMenu.setOnClickListener(v -> {
            drawer.openDrawer(Gravity.START);
        });

        llProfie.setOnClickListener(v -> {
            closeNavigation();
            Bundle bundle = new Bundle();
            replaceFragmenr(OnlieOffLineFragment.getInstance(bundle), OnlieOffLineFragment.TAG);
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
        FragmentManager fragmentManager = getSupportFragmentManager();
        int count = fragmentManager.getBackStackEntryCount();
        String fragmentTag = fragmentManager.getBackStackEntryAt(count - 1).getName();
        if (fragmentTag.equalsIgnoreCase(OnlieOffLineFragment.TAG)) {
            super.onBackPressed();
        } else {
            fragmentManager.popBackStackImmediate();
        }
    }

    private void closeNavigation() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
    }


}
