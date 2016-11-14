package com.tastegood.distribute.data.source.device;

import android.support.annotation.NonNull;

import com.tastegood.distribute.data.source.device.model.DeviceSignParams;
import com.tastegood.distribute.data.source.device.model.UploadJpushTokenParams;


/**
 * Created by surandy on 2016/10/17.
 */

public interface DeviceDataSource {

    interface DeviceSignInCallback {

        void onSignSuccess(String deviceNo);

        void onSignFail();
    }

    interface UploadJpushTokenCallback {

        void onUploadSuccess(Integer uploadResult);

        void onUploadFail();
    }

    void deviceSignIn(DeviceSignParams params, @NonNull DeviceDataSource.DeviceSignInCallback callback);

    void uploadJpushToken(UploadJpushTokenParams params, @NonNull DeviceDataSource.UploadJpushTokenCallback callback);

}
