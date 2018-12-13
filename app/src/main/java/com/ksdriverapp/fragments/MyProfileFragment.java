package com.ksdriverapp.fragments;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ksdriverapp.R;
import com.ksdriverapp.models.OnlineOffline;
import com.ksdriverapp.prefrences.SessionManager;
import com.ksdriverapp.retrofit.WsFactory;
import com.ksdriverapp.retrofit.WsResponse;
import com.ksdriverapp.retrofit.WsUtils;
import com.ksdriverapp.utils.StaticUtils;

import java.util.HashMap;

import dmax.dialog.SpotsDialog;
import retrofit2.Call;

public class MyProfileFragment extends BaseFragment implements WsResponse {

    public static String TAG = MyProfileFragment.class.getSimpleName();
    private ImageView imgOnnOffDuaty;
    private TextView txtSubmit;
    private int onlineOffline = 1;
    private SessionManager sessionManager;
    private AlertDialog progressDialog;


    public static MyProfileFragment getInstance(Bundle bundle) {
        MyProfileFragment myProfileFragment = new MyProfileFragment();
        myProfileFragment.setArguments(bundle);
        return myProfileFragment;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.fragment_my_profile, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        imgOnnOffDuaty = view.findViewById(R.id.imgOnnOffDuaty);
        txtSubmit = view.findViewById(R.id.txtSubmit);
        try {
            init();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void init() {
        sessionManager = new SessionManager(getContext());
        progressDialog = new SpotsDialog(getContext(), R.style.Custom);
        imgOnnOffDuaty.setOnClickListener(v -> {
            if (imgOnnOffDuaty.isSelected()) {
                imgOnnOffDuaty.setSelected(false);
                onlineOffline = 1;
            } else {
                imgOnnOffDuaty.setSelected(true);
                onlineOffline = 1;
            }
        });

        txtSubmit.setOnClickListener(v -> {
            progressDialog.show();
            HashMap<String, String> map = new HashMap<>();
            map.put("iDriverId", sessionManager.getUserId());
            map.put("tiOnlineStatus", onlineOffline + "");
            Call callOnlineOffline = WsFactory.onlineOffline(map);
            WsUtils.getReponse(callOnlineOffline, StaticUtils.REQUEST_ONLINE_OFFLINE, this);
        });
    }

    @Override
    public void successResponse(Object response, int code) {
        progressDialog.cancel();
        switch (code) {
            case StaticUtils.REQUEST_ONLINE_OFFLINE:
                OnlineOffline onlineOffline = (OnlineOffline) response;



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
