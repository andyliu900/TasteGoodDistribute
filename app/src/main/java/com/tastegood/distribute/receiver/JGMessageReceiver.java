package com.tastegood.distribute.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import com.orhanobut.logger.Logger;
import com.tastegood.distribute.data.source.device.DeviceDataSource;
import com.tastegood.distribute.data.source.device.DeviceRepository;
import com.tastegood.distribute.data.source.device.model.UploadJpushTokenParams;
import com.tastegood.distribute.gobal.Constants;
import com.tastegood.distribute.gobal.Injection;
import com.tastegood.distribute.util.SPUtils;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by surandy on 2016/11/13.
 */

public class JGMessageReceiver extends BroadcastReceiver {

    private static final String TAG = JGMessageReceiver.class.getName();
    private String jpushToken;

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
        if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
            String regId = bundle.getString(JPushInterface.EXTRA_REGISTRATION_ID);
            if(!TextUtils.isEmpty(regId)){
                jpushToken = regId;
                Logger.i("极光token：" + regId);
                uploadJpushToken(jpushToken);
            }
        } else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
            String extras = bundle.getString(JPushInterface.EXTRA_EXTRA);
            String contentType = bundle.getString(JPushInterface.EXTRA_CONTENT_TYPE);
            Logger.i("[MyReceiver] 接收到推送下来的自定义消息: " + extras);
            JpushMessageOperater.operaterPushMessage(context, extras, contentType);
        }
    }

    private void uploadJpushToken(final String jpushToken) {
        UploadJpushTokenParams params = new UploadJpushTokenParams();
        params.setToken(jpushToken);

        final DeviceRepository deviceRepository = Injection.provideDeviceRepository();
        deviceRepository.uploadJpushToken(params, new DeviceDataSource.UploadJpushTokenCallback() {

            @Override
            public void onUploadSuccess(Integer uploadResult) {
                if (uploadResult == 0) {
                    SPUtils.put(Constants.JPUSH_TOKEN_KEY, jpushToken);
                }
            }

            @Override
            public void onUploadFail() {

            }

        });
    }

}
