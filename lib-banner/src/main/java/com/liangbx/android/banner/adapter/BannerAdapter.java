package com.liangbx.android.banner.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.liangbx.android.banner.ImageLoader;
import com.liangbx.android.banner.listener.OnItemClickListener;
import com.liangbx.android.banner.model.BannerItem;
import com.liangbx.android.banner.util.PositionUtil;

import java.util.List;

/**
 * <p>Title: <／p>
 * <p>Description: <／p>
 * <p>Copyright: Copyright (c) 2016<／p>
 * <p>Company: NetDragon<／p>
 *
 * @author liangbx
 * @version 2016/12/1
 */

public class BannerAdapter extends PagerAdapter {

    private List<ImageView> mViews;
    private List<BannerItem> mBannerItems;
    private boolean mIsCycle;
    private OnItemClickListener mListener;
    private ImageLoader mImageLoader;

    public BannerAdapter(List<ImageView> views,
                         List<BannerItem> bannerItems,
                         boolean isCycle,
                         OnItemClickListener listener,
                         ImageLoader imageLoader) {
        mViews = views;
        mBannerItems = bannerItems;
        mIsCycle = isCycle;
        mListener = listener;
        mImageLoader = imageLoader;
    }

    @Override
    public int getCount() {
        return mViews.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public View instantiateItem(ViewGroup container, final int position) {
        ImageView imageView = mViews.get(position);
        if(mListener != null) {
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int vPosition = position;
                    if(mIsCycle) {
                        vPosition = PositionUtil.getRealPosition(position, mViews.size());
                    }
                    mListener.onItemClick(vPosition);
                }
            });
        }

        BannerItem bannerItem = mBannerItems.get(position);
        if(mImageLoader!= null) {
            mImageLoader.onLoad(imageView, bannerItem.getImageUrl());
        }

        if(imageView.getParent() != null) {
            container.removeView(imageView);
        }
        container.addView(imageView);
        return imageView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ImageView imageView = (ImageView) object;
        container.removeView(imageView);

        if(mImageLoader!= null) {
            mImageLoader.onUnLoad(imageView);
        }
    }
}
