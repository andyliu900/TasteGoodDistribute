package com.tastegood.distribute;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Build;
import android.text.TextUtils;

import com.orhanobut.logger.LogLevel;
import com.orhanobut.logger.Logger;
import com.tastegood.distribute.data.source.device.DeviceDataSource;
import com.tastegood.distribute.data.source.device.DeviceRepository;
import com.tastegood.distribute.data.source.device.model.DeviceSignParams;
import com.tastegood.distribute.data.source.device.model.UploadJpushTokenParams;
import com.tastegood.distribute.gobal.BaseActivity;
import com.tastegood.distribute.gobal.Constants;
import com.tastegood.distribute.gobal.Injection;
import com.tastegood.distribute.util.AndroidLogAdapter;
import com.tastegood.distribute.util.SPUtils;
import com.tastegood.distribute.util.StringUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;

/**
 * Created by surandy on 2016/10/12.
 */

public class DistributeApplication extends Application {

    private static DistributeApplication application;
    private List<Activity> activities = new ArrayList();
    private BaseActivity currentActivity = null;

    @Override
    public void onCreate() {
        super.onCreate();

        Logger.init(Constants.LOGGER_TAG)     // default PRETTYLOGGER or use just init()
                .methodCount(3)                 // default 2
                .logLevel(LogLevel.FULL)       // default LogLevel.FULL
                .methodOffset(2)                // default 0
                .logAdapter(new AndroidLogAdapter()); //default AndroidLogAdapter

        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);
        SPUtils.init(this);

        if (TextUtils.isEmpty((String)SPUtils.get(Constants.DEVICE_NO_KEY, ""))) {
            getDeviceToken();
        } else {
            uploadJpushToken();
            setJpushTag();
        }
    }

    public static DistributeApplication getApplication(Context context) {
        if (application == null) {
            application = (DistributeApplication)context.getApplicationContext();
        }
        return application;
    }

    public BaseActivity getCurrentActivity() {
        return currentActivity;
    }

    public void setCurrentActivity(BaseActivity activity) {
        currentActivity = activity;
    }

    public void addActivity(Activity activity){
        activities.add(activity);
    }

    public void finishActivity(Activity activity){
        if (activity!=null) {
            this.activities.remove(activity);
            activity.finish();
        }
    }

    public void exit() {
        for (Activity activity : activities) {
            if (activity!=null) {
                activity.finish();
            }
        }
        System.exit(0);
    }

    private void getDeviceToken() {
        DeviceSignParams params = new DeviceSignParams();
        params.setClientType(Constants.CLIENT_TYPE);
        params.setClientUdid(StringUtils.getUniqueID(this));
        params.setPhoneBrand(Build.BRAND);
        params.setPhoneModel(Build.MODEL);
        params.setPhonePlatform(Build.VERSION.RELEASE);
        params.setAppType(Constants.APP_TYPE);

        final DeviceRepository deviceRepository = Injection.provideDeviceRepository();
        deviceRepository.deviceSignIn(params, new DeviceDataSource.DeviceSignInCallback() {

            @Override
            public void onSignSuccess(String deviceNo) {
                SPUtils.put(Constants.DEVICE_NO_KEY, deviceNo);
            }

            @Override
            public void onSignFail() {
                deviceRepository.destroyInstance();
            }
        });
    }

    /**
     * 上传极光token
     */
    private void uploadJpushToken() {
        final String jpushToken = JPushInterface.getRegistrationID(this);
        if (TextUtils.isEmpty(jpushToken)) {
            return;
        }

        UploadJpushTokenParams params = new UploadJpushTokenParams();
        params.setToken(jpushToken);

        final DeviceRepository deviceRepository = Injection.provideDeviceRepository();
        deviceRepository.uploadJpushToken(params, new DeviceDataSource.UploadJpushTokenCallback() {

            @Override
            public void onUploadSuccess(Integer uploadResult) {
                if (uploadResult == 0) {
                    SPUtils.put(Constants.JPUSH_TOKEN_KEY, jpushToken);
                    setJpushTag();
                }
            }

            @Override
            public void onUploadFail() {

            }

        });
    }

    /**
     * 设置极光tag
     */
    public void setJpushTag() {
        String jpushTag = (String)SPUtils.get(Constants.JPUSH_TOKEN_KEY, "");
        if (TextUtils.isEmpty(jpushTag)) {
            Set<String> tag = new HashSet();
            tag.add(Constants.JGPUSH_TEST);
            JPushInterface.setTags(getApplicationContext(), tag,
                    new TagAliasCallback() {
                        @Override
                        public void gotResult(int code, String alias,
                                              Set<String> tags) {
                            if (code == 0) {
                                SPUtils.put(Constants.JPUSH_TOKEN_KEY, Constants.JGPUSH_TEST);
                            }
                        }
                    });
        }
    }
}
