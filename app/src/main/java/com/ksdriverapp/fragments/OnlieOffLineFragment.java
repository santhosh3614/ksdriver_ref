package com.ksdriverapp.fragments;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.ksdriverapp.R;
import com.ksdriverapp.activities.MainActivity;
import com.ksdriverapp.models.OnlineOffline;
import com.ksdriverapp.prefrences.SessionManager;
import com.ksdriverapp.retrofit.WsFactory;
import com.ksdriverapp.retrofit.WsResponse;
import com.ksdriverapp.retrofit.WsUtils;
import com.ksdriverapp.utils.PoupUtils;
import com.ksdriverapp.utils.StaticUtils;

import java.util.HashMap;

import dmax.dialog.SpotsDialog;
import retrofit2.Call;

public class OnlieOffLineFragment extends BaseFragment implements WsResponse {

    public static String TAG = OnlieOffLineFragment.class.getSimpleName();
    private ImageView imgOnnOffDuaty;
    private TextView txtSubmit;
    private int onlineOffline = 1;
    private SessionManager sessionManager;
    private AlertDialog progressDialog;
    private MainActivity mainActivity;
    private TextView txtStartTime, txtEndTime;


    public static OnlieOffLineFragment getInstance(Bundle bundle) {
        OnlieOffLineFragment myProfileFragment = new OnlieOffLineFragment();
        myProfileFragment.setArguments(bundle);
        return myProfileFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.fragment_online_offlie, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        imgOnnOffDuaty = view.findViewById(R.id.imgOnnOffDuaty);
        txtSubmit = view.findViewById(R.id.txtSubmit);
        txtStartTime = view.findViewById(R.id.txtStartTime);
        txtEndTime = view.findViewById(R.id.txtEndTime);
        mainActivity = (MainActivity) getActivity();

        txtStartTime.setOnClickListener(v -> {
            PoupUtils.showDatePicker(mainActivity, txtStartTime);
        });

        txtEndTime.setOnClickListener(v -> {
            PoupUtils.showDatePicker(mainActivity, txtEndTime);
        });

        try {
            init();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setHeader() {
        mainActivity = (MainActivity) getActivity();
        mainActivity.imgBack.setVisibility(View.GONE);
        mainActivity.imgMenu.setVisibility(View.VISIBLE);
        mainActivity.txtTitle.setText("Shedule Your Work");
    }


    @Override
    public void init() {
        mainActivity = (MainActivity) getActivity();
        sessionManager = new SessionManager(mainActivity);
        progressDialog = new SpotsDialog(mainActivity, R.style.Custom);
        String onlineofline = sessionManager.getOnlineOfline();
        setHeader();

        if (TextUtils.isEmpty(onlineofline)) {
            if (onlineofline.equalsIgnoreCase("Driver Online")) {
                imgOnnOffDuaty.setSelected(true);
            } else {
                imgOnnOffDuaty.setSelected(false);
            }
        }
        imgOnnOffDuaty.setOnClickListener(v -> {
            if (imgOnnOffDuaty.isSelected()) {
                imgOnnOffDuaty.setSelected(false);
                onlineOffline = 0;
            } else {
                imgOnnOffDuaty.setSelected(true);
                onlineOffline = 1;
            }
        });
        txtSubmit.setOnClickListener(v -> {
            String startTime = txtStartTime.getText().toString().trim();
            String endTime = txtEndTime.getText().toString().trim();
            if (TextUtils.isEmpty(startTime)) {
                PoupUtils.showAlertDailog(mainActivity, "Please select start time");
            } else if (TextUtils.isEmpty(endTime)) {
                PoupUtils.showAlertDailog(mainActivity, "Please select end time");
            } else {
//                mainActivity.replaceFragmenr(WelcomeScreenFragment.getInstance(), WelcomeScreenFragment.TAG);
                onlineoffline();
            }
        });
    }

    private void onlineoffline() {
        progressDialog.show();
        HashMap<String, String> map = new HashMap<>();
        map.put("iDriverId", sessionManager.getUserId());
        map.put("tiOnlineStatus", onlineOffline + "");
        Call callOnlineOffline = WsFactory.onlineOffline(map);
        WsUtils.getReponse(callOnlineOffline, StaticUtils.REQUEST_ONLINE_OFFLINE, this);
    }

    @Override
    public void successResponse(Object response, int code) {
        progressDialog.cancel();
        switch (code) {
            case StaticUtils.REQUEST_ONLINE_OFFLINE:
                OnlineOffline onlineOffline = (OnlineOffline) response;
                sessionManager.setOnlineOfline(onlineOffline.getResponseMessage());
                break;
            default:
                break;
        }
    }

    @Override
    public void failureRespons(Throwable error, int code) {
        progressDialog.cancel();
    }
}
