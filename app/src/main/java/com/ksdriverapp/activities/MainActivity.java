package com.ksdriverapp.activities;

import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.widget.LinearLayout;

import com.ksdriverapp.R;
import com.ksdriverapp.fragments.MyProfileFragment;

public class MainActivity extends BaseActivity {
    private LinearLayout llProfie;
    private DrawerLayout drawer;

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
        llProfie.setOnClickListener(v -> {
            closeDrawer();
            Bundle bundle = new Bundle();
            replaceFragmenr(MyProfileFragment.getInstance(bundle), MyProfileFragment.TAG);
        });
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