package com.ksdriverapp.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.ksdriverapp.R;

public class MyProfileFragment extends BaseFragment {

    public static String TAG = MyProfileFragment.class.getSimpleName();
    private ImageView imgOnnOffDuaty;

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
        try {
            init();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void init() {
        imgOnnOffDuaty.setOnClickListener(v -> {
            if (imgOnnOffDuaty.isSelected()) {
                imgOnnOffDuaty.setSelected(false);
            } else {
                imgOnnOffDuaty.setSelected(true);
            }

        });

    }
}
