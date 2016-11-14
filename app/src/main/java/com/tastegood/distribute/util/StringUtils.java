package com.tastegood.distribute.util;

import android.content.Context;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.provider.Settings;

import com.orhanobut.logger.Logger;

import java.text.SimpleDateFormat;
import java.util.Date;

import static android.content.ContentValues.TAG;

/**
 * Created by surandy on 2016/10/18.
 */

public class StringUtils {

    public static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

    /**
     * 获取设备唯一的ID，普遍形式，不获取IMEI
     *
     * @return
     */
    public static String getUniqueID(Context context) {
        String m_szDevIDShort = "35" + //we make this look like a valid IMEI
                Build.BOARD.length() % 10 +
                Build.BRAND.length() % 10 +
                Build.CPU_ABI.length() % 10 +
                Build.DEVICE.length() % 10 +
                Build.DISPLAY.length() % 10 +
                Build.HOST.length() % 10 +
                Build.ID.length() % 10 +
                Build.MANUFACTURER.length() % 10 +
                Build.MODEL.length() % 10 +
                Build.PRODUCT.length() % 10 +
                Build.TAGS.length() % 10 +
                Build.TYPE.length() % 10 +
                Build.USER.length() % 10;

        String m_szAndroidID = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);

        String m_szWLANMAC = "";
        try {
            WifiManager wm = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
            m_szWLANMAC = wm.getConnectionInfo().getMacAddress();
        } catch (Exception e) {
            e.printStackTrace();
            Logger.e(TAG, e.getMessage());
        }

        String m_szLongID = m_szDevIDShort
                + m_szAndroidID + m_szWLANMAC;

        return MD5Util.md5(m_szLongID);
    }

    public static String CoverLongTime2String(long time) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE_TIME_FORMAT);
        String daysString = simpleDateFormat.format(new Date(time));
        return daysString;
    }

}
