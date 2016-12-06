package com.liangbx.android.banner;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.liangbx.android.banner.model.BannerItem;

/**
 * <p>Title: 指示器<／p>
 * <p>Description: <／p>
 * <p>Copyright: Copyright (c) 2016<／p>
 * <p>Company: NetDragon<／p>
 *
 * @author liangbx
 * @version 2016/12/6
 */

public interface Indicator {

    View getLayout(Context context, ViewGroup parent);

    int getGravity();

    void onPageScrolled(int position, float positionOffset, int positionOffsetPixels,
                        BannerItem bannerItem);

    void onPageSelected(int position, BannerItem bannerItem);

    void onPageScrollStateChanged(int state);
}
