package com.liangbx.android.banner.app;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.liangbx.android.banner.ImageLoader;

/**
 * <p>Title: <／p>
 * <p>Description: <／p>
 * <p>Copyright: Copyright (c) 2016<／p>
 * <p>Company: NetDragon<／p>
 *
 * @author liangbx
 * @version 2016/12/6
 */

public class GlideImageLoader implements ImageLoader {

    @Override
    public void onLoad(ImageView imageView, String imageUrl) {
        Glide.with(imageView.getContext())
                .load(imageUrl)
                .placeholder(R.mipmap.defautl_image)
                .error(R.mipmap.defautl_image)
                .centerCrop()
                .crossFade()
                .into(imageView);
    }

    @Override
    public void onUnLoad(ImageView imageView) {

    }
}
