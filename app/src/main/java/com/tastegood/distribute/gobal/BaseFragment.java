package com.tastegood.distribute.gobal;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tastegood.distribute.DistributeApplication;
import com.tastegood.distribute.view.CustomProgressDialog;
import com.tastegood.distribute.view.ShowDialog;
import com.tastegood.distribute.view.listener.ShowProgressDialogListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.ButterKnife;


/**
 * Fragment基类
 *
 * Created by surandy on 2016/10/12.
 */

public abstract class BaseFragment extends Fragment implements ShowDialog.DialogListener, ShowProgressDialogListener {

    public DistributeApplication application;
    public Activity pActivity = null;
    private CustomProgressDialog mProgressDialog;
    private ShowDialog mConflictDialog = null;

    @LayoutRes
    protected abstract int getLayoutRes();

    @Override
    public void onAttach(Context contet) {
        super.onAttach(contet);
        EventBus.getDefault().register(this);

        pActivity = getActivity();
    }

    @Override public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mProgressDialog = new CustomProgressDialog(pActivity);
        mProgressDialog.setCanceledOnTouchOutside(false);
        mConflictDialog = new ShowDialog(pActivity, this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutRes(), container, false);
        ButterKnife.bind(this, view);

        application = DistributeApplication.getApplication(pActivity);

        initViews();
        initDatas();

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe
    public void onEventMainThread(Object event) {

    }

    protected abstract void initViews();

    protected abstract void initDatas();

    public void showdialog(String title, String content, String[] menus) {
        if (mConflictDialog != null) {
            mConflictDialog.show(title, content, menus);
        }
    }

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

    @Override
    public void showProgressDialog(String textString) {
        try {
            if (mProgressDialog != null && !mProgressDialog.isShowing()) {
                mProgressDialog.setMessage(textString);
                mProgressDialog.show();
            }
        } catch (Exception e) {

        }
    }

    @Override
    public void showProgressDialog(String textString, boolean cancleable) {
        try {
            if (mProgressDialog != null && !mProgressDialog.isShowing()) {
                mProgressDialog.setCancelable(cancleable);
                mProgressDialog.setMessage(textString);
                mProgressDialog.show();
            }
        } catch (Exception e) {

        }
    }

    @Override
    public void dismissProgressDialog() {
        try {
            if (mProgressDialog != null && mProgressDialog.isShowing()) {
                mProgressDialog.dismiss();
            }
        } catch (Exception e) {

        }
    }
}
