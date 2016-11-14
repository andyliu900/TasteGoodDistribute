package com.tastegood.distribute;

import android.os.Handler;
import android.os.Message;

import com.tastegood.distribute.gobal.BaseActivity;
import com.tastegood.distribute.gobal.Constants;
import com.tastegood.distribute.user.LoginActivity;
import com.tastegood.distribute.user.domain.model.UserInfo;
import com.tastegood.distribute.util.SPUtils;
import com.tastegood.distribute.util.UIUtils;

/**
 * Created by surandy on 2016/10/17.
 */

public class SplashActivity extends BaseActivity {

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_splash;
    }

    @Override
    protected void initViews() {
        handler.sendEmptyMessageDelayed(1, 1500);
    }

    @Override
    protected void initDatas() {

    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    UserInfo userInfo = (UserInfo)SPUtils.get(Constants.USER_INFO_KEY, UserInfo.class);
                    if (userInfo == null) {
                        UIUtils.openActivity(SplashActivity.this, LoginActivity.class);
                    } else {
                        UIUtils.openActivity(SplashActivity.this, MainActivity.class);
                    }
                    handler.removeMessages(1);
                    finish();
                    break;
            }
        }
    };
}
