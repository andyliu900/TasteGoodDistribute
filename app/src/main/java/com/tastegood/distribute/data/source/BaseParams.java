package com.tastegood.distribute.data.source;

import android.text.TextUtils;

import com.tastegood.distribute.gobal.Constants;
import com.tastegood.distribute.util.SPUtils;

/**
 * Created by surandy on 2016/10/19.
 */

public class BaseParams {

    private String devicesn;

    public BaseParams() {
        String deviceNo = (String) SPUtils.get(Constants.DEVICE_NO_KEY, "");
        if (!TextUtils.isEmpty(deviceNo)) {
            setDevicesn(deviceNo);
        }
    }

    public String getDevicesn() {
        return devicesn;
    }

    public void setDevicesn(String devicesn) {
        this.devicesn = devicesn;
    }
}
