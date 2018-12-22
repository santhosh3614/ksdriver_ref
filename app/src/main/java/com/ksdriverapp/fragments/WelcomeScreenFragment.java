package com.ksdriverapp.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ksdriverapp.R;
import com.ksdriverapp.activities.MainActivity;

public class WelcomeScreenFragment extends BaseFragment {

    private MainActivity mainActivity;

    public static WelcomeScreenFragment getInstance() {
        WelcomeScreenFragment welcomeScreenFragment = new WelcomeScreenFragment();
        return welcomeScreenFragment;
    }

    public static String TAG = WelcomeScreenFragment.class.getSimpleName();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.fragment_welcome_screen, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        try {
            init();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void init() {
        setHeader();
    }

    private void setHeader() {
        mainActivity = (MainActivity) getActivity();
        mainActivity = (MainActivity) getActivity();
        mainActivity.imgBack.setVisibility(View.VISIBLE);
        mainActivity.imgMenu.setVisibility(View.GONE);
        mainActivity.txtTitle.setText("Welcome Screen");
    }


}
