package com.tastegood.distribute.data.source.device;

import android.support.annotation.NonNull;

import com.tastegood.distribute.data.source.device.model.DeviceSignParams;
import com.tastegood.distribute.data.source.device.model.UploadJpushTokenParams;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by surandy on 2016/10/17.
 */

public class DeviceRepository implements DeviceDataSource {

    private static DeviceRepository INSTANCE = null;

    private final DeviceDataSource mDeviceDataSource;

    // Prevent direct instantiation.
    private DeviceRepository(@NonNull DeviceDataSource deviceDataSource) {
        mDeviceDataSource = checkNotNull(deviceDataSource);
    }

    public static DeviceRepository getInstance(DeviceDataSource deviceDataSource) {
        if (INSTANCE == null) {
            INSTANCE = new DeviceRepository(deviceDataSource);
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }

    @Override
    public void deviceSignIn(DeviceSignParams params, final @NonNull DeviceSignInCallback callback) {
        mDeviceDataSource.deviceSignIn(params, new DeviceSignInCallback() {

            @Override
            public void onSignSuccess(String deviceNo) {
                callback.onSignSuccess(deviceNo);
            }

            @Override
            public void onSignFail() {
                callback.onSignFail();
            }
        });
    }

    @Override
    public void uploadJpushToken(UploadJpushTokenParams params, final @NonNull UploadJpushTokenCallback callback) {
        mDeviceDataSource.uploadJpushToken(params, new UploadJpushTokenCallback() {

            @Override
            public void onUploadSuccess(Integer uploadResult) {
                callback.onUploadSuccess(uploadResult);
            }

            @Override
            public void onUploadFail() {
                callback.onUploadFail();
            }

        });
    }
}