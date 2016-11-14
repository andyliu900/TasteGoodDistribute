package com.tastegood.distribute.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by surandy on 2016/10/28.
 */

public class NetworkUtils {

    /**
     * 获取当前网络状态
     * @param context
     * @return
     */
    public static boolean isConnected(Context context) {
        if(context==null) return false;
        ConnectivityManager connectMgr = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = connectMgr.getActiveNetworkInfo();
        if (info != null && info.isConnected()) {
            return true;
        }
        return false;
    }

}
