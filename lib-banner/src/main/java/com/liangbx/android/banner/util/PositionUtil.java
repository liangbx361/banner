package com.liangbx.android.banner.util;

/**
 * <p>Title: <／p>
 * <p>Description: <／p>
 * <p>Copyright: Copyright (c) 2016<／p>
 * <p>Company: NetDragon<／p>
 *
 * @author liangbx
 * @version 2016/12/2
 */

public class PositionUtil {

    /**
     * 计算循环模式下的实践位置
     */
    public static int getRealPosition(int position, int size) {
        int realPosition;
        int maxIndex = size - 1;

        if(position == 0) {
            realPosition = maxIndex - 2;
        } else if(position == maxIndex) {
            realPosition = 0;
        } else {
            realPosition = position - 1;
        }

        return realPosition;
    }
}
