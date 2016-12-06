package com.liangbx.android.banner.app;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.liangbx.android.banner.Indicator;
import com.liangbx.android.banner.model.BannerItem;
import com.rd.PageIndicatorView;

/**
 * <p>Title: <／p>
 * <p>Description: <／p>
 * <p>Copyright: Copyright (c) 2016<／p>
 * <p>Company: NetDragon<／p>
 *
 * @author liangbx
 * @version 2016/12/6
 */

public class CycleIndicator implements Indicator {

    private int mSize;
    private PageIndicatorView mIndicatorView;

    public CycleIndicator(int size) {
        mSize = size;
    }

    @Override
    public View getLayout(Context context, ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.banner_indicator, parent, false);
        mIndicatorView = (PageIndicatorView) view.findViewById(R.id.indicator);
        mIndicatorView.setCount(mSize);
        return view;
    }

    @Override
    public int getGravity() {
        return Gravity.BOTTOM | Gravity.CENTER;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels, BannerItem bannerItem) {
        mIndicatorView.onPageScrolled(position, positionOffset, positionOffsetPixels);
    }

    @Override
    public void onPageSelected(int position, BannerItem bannerItem) {
        mIndicatorView.onPageSelected(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        mIndicatorView.onPageScrollStateChanged(state);
    }
}
