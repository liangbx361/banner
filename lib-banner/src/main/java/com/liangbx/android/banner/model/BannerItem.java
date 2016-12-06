package com.liangbx.android.banner.model;

/**
 * <p>Title: Banner的基础ViewMode<／p>
 * <p>Description: 可根据扩展该ViewMode实现定制化<／p>
 * <p>Copyright: Copyright (c) 2016<／p>
 * <p>Company: NetDragon<／p>
 *
 * @author liangbx
 * @version 2016/12/1
 */

public class BannerItem {

    private String imageUrl;
    private String title;

    public BannerItem(String imageUrl, String title) {
        this.imageUrl = imageUrl;
        this.title = title;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getTitle() {
        return title;
    }
}
