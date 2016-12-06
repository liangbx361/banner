package com.liangbx.android.banner.app;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.liangbx.android.banner.BannerView;
import com.liangbx.android.banner.listener.OnItemClickListener;
import com.liangbx.android.banner.model.BannerItem;

import java.util.ArrayList;
import java.util.List;

public class SampleActivity extends AppCompatActivity {

    private BannerView mBannerView;
    private Spinner mSpinner;
    private CheckBox mCbAutoPlay;
    private CheckBox mCbCycleMode;
    private EditText mEtIntervalTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample);

        initView();
    }

    private void initView() {
        mBannerView = (BannerView) findViewById(R.id.banner);
        mBannerView.setImageLoader(new GlideImageLoader());
        mBannerView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Toast.makeText(SampleActivity.this, "click position --> " + position, Toast.LENGTH_SHORT).show();
            }
        });

        mSpinner = (Spinner) findViewById(R.id.bannerNum);
        ArrayAdapter<Integer> numberAdapter = new ArrayAdapter<Integer>(
                this,
                android.R.layout.simple_list_item_1,
                android.R.id.text1,
                new Integer[]{0, 1, 2, 3, 4});
        mSpinner.setAdapter(numberAdapter);

        mCbAutoPlay = (CheckBox) findViewById(R.id.autoPlay);
        mCbCycleMode = (CheckBox) findViewById(R.id.cycleMode);
        mEtIntervalTime = (EditText) findViewById(R.id.intervalTime);
    }

    private List<BannerItem> getBannerItems(int size) {
        List<BannerItem> bannerItems = new ArrayList<>();
        for(int i=0; i<size; i++) {
            BannerItem bannerItem = new BannerItem(Resources.IMAGE_URLS[i], "小鸟" + i);
            bannerItems.add(bannerItem);
        }
        return bannerItems;
    }

    public void onRefresh(View view) {
        boolean isAutoPlay = mCbAutoPlay.isChecked();
        boolean cycleMode = mCbCycleMode.isChecked();
        long intervalTime = Integer.valueOf(mEtIntervalTime.getText().toString());
        int size = mSpinner.getSelectedItemPosition();

        mBannerView.setAutoPlayMode(isAutoPlay);
        mBannerView.setCycleMode(cycleMode);
        mBannerView.setIntervalTime(intervalTime);
        mBannerView.setIndicator(new CycleIndicator(size));

        List<BannerItem> bannerItems = getBannerItems(size);
        mBannerView.setData(bannerItems);
        if(isAutoPlay) {
            mBannerView.stopAutoPlay();
            mBannerView.startAutoPlay();
        } else {
            mBannerView.stopAutoPlay();
        }
    }
}
