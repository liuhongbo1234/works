package com.xiaomi.mi_deepclean;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.xiaomi.mi_deepclean.base.BaseFragment;
import com.xiaomi.mi_deepclean.model.SDCardInfo;
import com.xiaomi.mi_deepclean.utils.AppUtil;
import com.xiaomi.mi_deepclean.utils.L;
import com.xiaomi.mi_deepclean.utils.StorageUtil;
import com.xiaomi.mi_deepclean.widget.ArcProgress;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


public class MainFragment extends BaseFragment {
    @BindView(R.id.arc_store)
    ArcProgress arcStore;
    @BindView(R.id.capacity)
    TextView capacity;

    private Unbinder unbinder;
    private Context mContext;
    private Timer timer;
    private Timer timer2;
    private TextView textView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        unbinder = ButterKnife.bind(this, view);
        mContext = getActivity();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        fillData();
    }

    private void fillData() {
        textView = getActivity().findViewById(R.id.beginclean);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, DeepCleanActivity.class);
                startActivity(intent);
            }
        });
        timer = new Timer();
        timer2 = new Timer();
        SDCardInfo mSDCardInfo = StorageUtil.getSDCardInfo();
        SDCardInfo mSystemInfo = StorageUtil.getSystemSpaceInfo(mContext);

        long nAvailaBlock;
        long TotalBlocks;
        if (mSDCardInfo != null) {
            nAvailaBlock = mSDCardInfo.free + mSystemInfo.free;
            TotalBlocks = mSDCardInfo.total + mSystemInfo.total;
        } else {
            nAvailaBlock = mSystemInfo.free;
            TotalBlocks = mSystemInfo.total;
        }
        final double percentStore = (((TotalBlocks - nAvailaBlock) / (double) TotalBlocks) * 100);
        capacity.setText(StorageUtil.convertStorage(TotalBlocks - nAvailaBlock) + "/" + StorageUtil.convertStorage(TotalBlocks));
        arcStore.setProgress(0);
        arcStore.setBottomText(arcStore.getBottomText());
        timer2.schedule(new TimerTask() {
            @Override
            public void run() {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (arcStore.getProgress() >= (int) percentStore) {
                            timer2.cancel();
                        } else {
                            arcStore.setProgress(arcStore.getProgress() + 1);
                        }
                    }
                });
            }
        }, 10, 15);
    }
}

