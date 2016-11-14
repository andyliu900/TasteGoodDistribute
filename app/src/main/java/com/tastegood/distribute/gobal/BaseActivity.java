package com.tastegood.distribute.gobal;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.tastegood.distribute.DistributeApplication;
import com.tastegood.distribute.view.CustomProgressDialog;
import com.tastegood.distribute.view.ShowDialog;
import com.tastegood.distribute.view.listener.ShowProgressDialogListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.ButterKnife;
import cn.jpush.android.api.JPushInterface;

/**
 * Activity基类
 *
 * Created by surandy on 2016/10/12.
 */

public abstract class BaseActivity extends AppCompatActivity implements ShowDialog.DialogListener, ShowProgressDialogListener {

    protected DistributeApplication application;
    private CustomProgressDialog mProgressDialog;
    private ShowDialog mConflictDialog;

    @LayoutRes
    protected abstract int getLayoutRes();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        setContentView(getLayoutRes());
        ButterKnife.bind(this);

        application = DistributeApplication.getApplication(this);
        application.addActivity(this);

        mProgressDialog = new CustomProgressDialog(this);
        mProgressDialog.setCanceledOnTouchOutside(false);
        mConflictDialog = new ShowDialog(this, this);

        initViews();
        initDatas();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        application.finishActivity(this);
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void onResume() {
        super.onResume();

        JPushInterface.onResume(this);
        if (application != null) {
            application.setCurrentActivity(this);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        JPushInterface.onPause(this);
    }

    @Subscribe
    public void onEventMainThread(Object event) {

    }

    protected abstract void initViews();

    protected abstract void initDatas();

    /**
     * 显示等待对话框
     */
    @Override
    public void showProgressDialog(String textString) {
        // 显示进度对话框
        try {
            if (mProgressDialog != null && !mProgressDialog.isShowing()) {
                mProgressDialog.setMessage(textString);
                mProgressDialog.show();
            }
        } catch (Exception e) {

        }
    }

    /**
     * 显示等待对话框
     */
    @Override
    public void showProgressDialog(String textString, boolean cancleable) {
        // 显示进度对话框
        try {
            if (mProgressDialog != null && !mProgressDialog.isShowing()) {
                mProgressDialog.setCancelable(cancleable);
                mProgressDialog.setMessage(textString);
                mProgressDialog.show();
            }
        } catch (Exception e) {

        }
    }

    /**
     * 关闭进度对话框
     */
    @Override
    public void dismissProgressDialog() {
        // 关闭进度对话框
        try {
            if (mProgressDialog != null && mProgressDialog.isShowing()) {
                mProgressDialog.dismiss();
            }
        } catch (Exception e) {

        }
    }

    /**
     * 弹出框
     */
    public void showdialog(String title, String content, String[] menus) {
        if (mConflictDialog != null) {
            mConflictDialog.show(title, content, menus);
        }
    }

    /**
     * 弹出框
     */
    public void showdialog(String title, String content, String[] menus, boolean isleft) {
        if (mConflictDialog != null) {
            mConflictDialog.show(title, content, menus, isleft);
        }
    }

    /**
     * 弹出框选择取消
     */
    @Override
    public void ProListener() {
        // TODO Auto-generated method stub

    }

    /**
     * 弹出框选择确认
     */
    @Override
    public void SureListener() {
        // TODO Auto-generated method stub

    }

}
