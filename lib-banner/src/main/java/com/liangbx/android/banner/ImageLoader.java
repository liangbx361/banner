package com.liangbx.android.banner;

import android.widget.ImageView;

/**
 * <p>Title: <／p>
 * <p>Description: <／p>
 * <p>Copyright: Copyright (c) 2016<／p>
 * <p>Company: NetDragon<／p>
 *
 * @author liangbx
 * @version 2016/12/6
 */

public interface ImageLoader {

    void onLoad(ImageView imageView, String imageUrl);

    void onUnLoad(ImageView imageView);
}
