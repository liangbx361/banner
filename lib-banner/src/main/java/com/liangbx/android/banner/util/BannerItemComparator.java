package com.liangbx.android.banner.util;


import com.liangbx.android.banner.model.BannerItem;

/**
 * <p>Title: <／p>
 * <p>Description: <／p>
 * <p>Copyright: Copyright (c) 2016<／p>
 * <p>Company: NetDragon<／p>
 *
 * @author liangbx
 * @version 2016/12/1
 */

public class BannerItemComparator extends ListComparator<BannerItem> {

    // FIXME 目前先简单比较URL属性，可进行完整的属性校验
    @Override
    public boolean isEqual(BannerItem t1, BannerItem t2) {
        return t1.getImageUrl().equals(t2.getImageUrl());
    }
}
