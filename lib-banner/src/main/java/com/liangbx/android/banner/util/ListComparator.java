package com.liangbx.android.banner.util;

import android.support.annotation.NonNull;

import java.util.List;

/**
 * <p>Title: 列表比较工具<／p>
 * <p>Description: <／p>
 * <p>Copyright: Copyright (c) 2016<／p>
 * <p>Company: NetDragon<／p>
 *
 * @author liangbx
 * @version 2016/12/1
 */

public abstract class ListComparator<T> {

    public boolean isEqual(@NonNull List<T> data1, @NonNull List<T> data2) {
        if(data1.isEmpty() && !data2.isEmpty()) {
            return false;
        }

        if(!data1.isEmpty() && data2.isEmpty()) {
            return false;
        }

        if(data1.size() != data2.size()) {
            return false;
        }

        for(int i=0; i<data1.size(); i++) {
            if(!isEqual(data1.get(i), data2.get(i))) {
                return false;
            }
        }

        return true;
    }

    public abstract boolean isEqual(T t1, T t2);
}
