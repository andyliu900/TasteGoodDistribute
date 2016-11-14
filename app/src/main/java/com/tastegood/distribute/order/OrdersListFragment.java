package com.tastegood.distribute.order;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;

import com.tastegood.distribute.R;
import com.tastegood.distribute.gobal.BaseFragment;
import com.tastegood.distribute.gobal.Constants;
import com.tastegood.distribute.gobal.Injection;
import com.tastegood.distribute.order.domain.model.Order;
import com.tastegood.distribute.order.event.RefreshFragmentEvent;
import com.tastegood.distribute.order.event.SwitchFragmentEvent;
import com.tastegood.distribute.order.view.OrderRecordArrivedView;
import com.tastegood.distribute.order.view.OrderRecordSendingView;
import com.tastegood.distribute.order.view.OrderRecordWaitHandleView;
import com.tastegood.distribute.user.LoginActivity;
import com.tastegood.distribute.user.domain.model.UserInfo;
import com.tastegood.distribute.util.NetworkUtils;
import com.tastegood.distribute.util.SPUtils;
import com.tastegood.distribute.util.UIUtils;
import com.tastegood.distribute.view.LoadRecyclerView;
import com.tastegood.distribute.view.LoadRecycleviewAdapter;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * 订单列表界面
 *
 * Created by surandy on 2016/10/13.
 */

public class OrdersListFragment extends BaseFragment implements OrderContract.OrderView,
        LoadRecycleviewAdapter.ILoadListener {

    private final int MY_PERMISSIONS_REQUEST_CALL_PHONE = 11;

    private static final String ORDER_STATUS = "order_status";
    private static final int LISTTYPE_WAITHANDLE = 0;
    private static final int LISTTYPE_SEDING = 1;
    private static final int LISTTYPE_ARRIVED = 2;

    private OrderContract.Presenter mPresenter;

    @Bind(R.id.ptr_frame)
    PtrClassicFrameLayout mPtrFrame;
    @Bind(R.id.recyclerview)
    LoadRecyclerView recyclerview;
    @Bind(R.id.view_nodata)
    View view_nodata;
    @Bind(R.id.view_nonetwork)
    View view_nonetwork;

    private UserInfo userInfo;
    private OrderRecordAdapter orderRecordAdapter;
    private boolean isRefreshing;
    private boolean isLoading;
    private boolean isLoaded;
    private int current_page;
    private int status;
    List<Order> items = new ArrayList();
    LinearLayoutManager linearLayoutManager;

    private String servicePhone = "";

    private OrderRecordWaitHandleView.ItemClickListener waitHandleItemClicklistener;
    private OrderRecordSendingView.ItemClickListener sendingItemClicklistener;
    private OrderRecordArrivedView.ItemClickListener arrivedItemClicklistener;

    public static OrdersListFragment newInstance(int orderStatus) {
        Bundle args = new Bundle();
        args.putInt(ORDER_STATUS, orderStatus);
        OrdersListFragment fragment = new OrdersListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.common_recyclerviewlist_layout;
    }

    @Override
    protected void initViews() {
        current_page = 1;
        linearLayoutManager = new LinearLayoutManager(pActivity);
        recyclerview.setLayoutManager(linearLayoutManager);
        orderRecordAdapter = new OrderRecordAdapter(true, this);

        mPtrFrame.setLastUpdateTimeRelateObject(this);
        mPtrFrame.setPtrHandler(new PtrHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                if (!NetworkUtils.isConnected(pActivity)) {
                    UIUtils.showShort(pActivity, "网络连接出了问题，请检查网络");
                    mPtrFrame.refreshComplete();
                    return;
                }
                if (!isRefreshing) {
                    current_page = 1;
                    isRefreshing = true;
                    isLoading = false;
                    isLoaded = false;
                    mPresenter.getOrderList(userInfo.getDistributionId(), current_page, Constants.PAGE_SIZE, status);
                }
            }

            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                //设置下拉刷新的条件
                return linearLayoutManager.findFirstCompletelyVisibleItemPosition() == 0;
            }
        });

        waitHandleItemClicklistener = new OrderRecordWaitHandleView.ItemClickListener() {
            @Override
            public void itemPhoneClick(String phone) {
                if (!TextUtils.isEmpty(phone)) {
                    servicePhone = phone;
                    showdialog(null, phone, new String[]{"取消", "呼叫"});
                } else {
                    UIUtils.showShort(pActivity, "抱歉，该店铺没有联系电话");
                }
            }
        };

        sendingItemClicklistener = new OrderRecordSendingView.ItemClickListener() {
            @Override
            public void itemSureArrvideClick(Order order) {
                showLoadingView();
                mPresenter.senderSureArrived(userInfo.getDistributionId(), order.getOrderNo());
            }
        };

        arrivedItemClicklistener = new OrderRecordArrivedView.ItemClickListener() {
            @Override
            public void itemPhoneClick(String phone) {
                if (!TextUtils.isEmpty(phone)) {
                    servicePhone = phone;
                    showdialog(null, phone, new String[]{"取消", "呼叫"});
                } else {
                    UIUtils.showShort(pActivity, "抱歉，该店铺没有联系电话");
                }
            }
        };

    }

    @Override
    protected void initDatas() {
        mPresenter = new OrderPresenter(
                Injection.provideUseCaseHandler(),
                this,
                Injection.provideOrderTask(),
                Injection.provideOrderArrivedTask());

        if (NetworkUtils.isConnected(pActivity)) {
            userInfo = (UserInfo) SPUtils.get(Constants.USER_INFO_KEY, UserInfo.class);
            if (userInfo != null) {
                isRefreshing = true;
                showLoadingView();
                status = getArguments().getInt(ORDER_STATUS);
                mPresenter.getOrderList(userInfo.getDistributionId(), current_page, Constants.PAGE_SIZE, status);
            } else {
                UIUtils.showShort(pActivity, "请先登录");
                UIUtils.openActivity(pActivity, LoginActivity.class);
                pActivity.finish();
            }
        } else {
            showNetErrorView(true);
        }
    }

    @OnClick({R.id.nodate_tv})
    public void click(View v) {
        switch (v.getId()) {
            case R.id.nodate_tv:
                showLoadingView();
                current_page = 1;
                isRefreshing = true;
                isLoading = false;
                isLoaded = false;
                mPresenter.getOrderList(userInfo.getDistributionId(), current_page, Constants.PAGE_SIZE, status);
                break;
        }
    }

    @Override
    public void showLoadingView() {
        showProgressDialog("加载中...");
    }

    @Override
    public void showLoadingView(String message) {
        showProgressDialog(message);
    }

    @Override
    public void hideLoadingView() {
        dismissProgressDialog();
    }

    @Override
    public void showNetErrorView(boolean isFirst) {
        if (isFirst) {
            view_nonetwork.setVisibility(View.VISIBLE);
            view_nodata.setVisibility(View.GONE);
            mPtrFrame.setVisibility(View.GONE);
        } else {
            UIUtils.showShort(pActivity, "网络连接错误，请重试");
        }
    }

    @Override
    public void hideNetErrorView() {
        view_nonetwork.setVisibility(View.GONE);
        view_nodata.setVisibility(View.GONE);
        mPtrFrame.setVisibility(View.VISIBLE);
    }

    @Override
    public void showNoDataView(boolean isFirst) {
        if (isFirst) {
            mPtrFrame.setVisibility(View.GONE);
            view_nonetwork.setVisibility(View.GONE);
            view_nodata.setVisibility(View.VISIBLE);
        } else {
            UIUtils.showShort(pActivity, "暂无记录");
        }
    }

    @Override
    public void hideNoDataView() {
        view_nonetwork.setVisibility(View.GONE);
        view_nodata.setVisibility(View.GONE);
        mPtrFrame.setVisibility(View.VISIBLE);
    }

    @Override
    public void showBusinessError(String businessErrorMessage) {
        UIUtils.showShort(pActivity, businessErrorMessage);
    }

    @Override
    public void showOrderList(List<Order> orderList) {
        if (orderList != null) {
            if (current_page == 1) {
                if (orderList != null && orderList.size() > 0) {
                    if (recyclerview.getRecyclerView().getAdapter() == null) {
                        recyclerview.setAdapter(orderRecordAdapter);
                    }
                    items.clear();
                    items.addAll(orderList);
                    orderRecordAdapter.notifyDataSetChanged();
                } else {
                    showNoDataView(true);
                }
            } else {
                if (orderList != null && orderList.size() > 0) {
                    items.addAll(orderList);
                    orderRecordAdapter.notifyDataSetChanged();
                } else {
                    isLoaded = true;
                    orderRecordAdapter.setLoadFooterState(LoadRecycleviewAdapter.LoadState.LoadFinish);
                    return;
                }
            }

            if (orderList.size() < Constants.PAGE_SIZE) {
                isLoaded = true;
                orderRecordAdapter.setLoadFooterState(LoadRecycleviewAdapter.LoadState.LoadFinish);
            } else {
                isLoading = false;
                orderRecordAdapter.setLoadFooterState(LoadRecycleviewAdapter.LoadState.LoadAgain);
            }
        } else {
            if (current_page == 1) {
                if (items.isEmpty()) {
                    showNoDataView(true);
                }
            }
            if (isLoading) {
                current_page--;
                orderRecordAdapter.setLoadFooterState(LoadRecycleviewAdapter.LoadState.LoadFailed);
            }
        }
        if (isRefreshing) {
            isRefreshing = false;
            mPtrFrame.refreshComplete();
        }
        if (isLoading) {
            isLoading = false;
        }

    }

    @Override
    public void showOrderArrivedResult(boolean arrivedResult) {
        if (arrivedResult) {
            current_page = 1;
            isRefreshing = true;
            isLoading = false;
            isLoaded = false;
            mPresenter.getOrderList(userInfo.getDistributionId(), current_page, Constants.PAGE_SIZE, status);

            EventBus.getDefault().post(new SwitchFragmentEvent(new OrdersListFragment()));
            EventBus.getDefault().post(new RefreshFragmentEvent(2));
        } else {
            UIUtils.showShort(pActivity, "操作失败，请重试");
        }
    }

    @Override
    public void setPresenter(OrderContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }

    @Override
    public void loading() {
        if (!isLoading && !isLoaded) {
            current_page++;
            isLoading = true;
            mPresenter.getOrderList(userInfo.getDistributionId(), current_page, Constants.PAGE_SIZE, status);
        }
    }

    @Override
    public void SureListener() {
        super.SureListener();
        if (ContextCompat.checkSelfPermission(pActivity,
                Manifest.permission.CALL_PHONE)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(pActivity,
                    new String[]{Manifest.permission.CALL_PHONE},
                    MY_PERMISSIONS_REQUEST_CALL_PHONE);
        } else {
            Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + servicePhone));
            startActivity(intent);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_CALL_PHONE: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    try {
                        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + servicePhone));
                        startActivity(intent);
                    } catch (SecurityException e) {

                    }
                } else {
                    UIUtils.showShort(pActivity, "请进去权限功能，允许拨打电话后再启用该功能！");
                }
                return;
            }
        }
    }

    class OrderRecordAdapter extends LoadRecycleviewAdapter {

        public OrderRecordAdapter(boolean isEnableLoad, ILoadListener listener) {
            super(isEnableLoad, listener);
        }

        @Override
        public int getSubItemCount() {
            return items == null ? 0 : items.size();
        }

        @Override
        public void onBindSubViewHolder(RecyclerView.ViewHolder holder, int position) {
            if (holder instanceof OrderRecordWaitHandleView) {
                ((OrderRecordWaitHandleView) holder).setData(items.get(position));
            } else if (holder instanceof OrderRecordSendingView) {
                ((OrderRecordSendingView) holder).setData(items.get(position));
            } else if (holder instanceof OrderRecordArrivedView) {
                ((OrderRecordArrivedView) holder).setData(items.get(position));
            }
        }

        @Override
        public RecyclerView.ViewHolder onCreateSubViewHolder(ViewGroup parent, int viewType) {
            if (viewType == LISTTYPE_WAITHANDLE) {
                return new OrderRecordWaitHandleView(parent, waitHandleItemClicklistener);
            } else if (viewType == LISTTYPE_SEDING) {
                return new OrderRecordSendingView(parent, sendingItemClicklistener);
            } else if (viewType == LISTTYPE_ARRIVED) {
                return new OrderRecordArrivedView(parent, arrivedItemClicklistener);
            } else {
                return new OrderRecordWaitHandleView(parent, waitHandleItemClicklistener);
            }
        }

        @Override
        public int getSubItemViewType(int position) {
            if (status == Constants.ORDER_STATUS_WAITHANDLE) {
                return LISTTYPE_WAITHANDLE;
            } else if (status == Constants.ORDER_STATUS_SENDING) {
                return LISTTYPE_SEDING;
            } else if (status == Constants.ORDER_STATUS_ARRIVED) {
                return LISTTYPE_ARRIVED;
            } else {
                return 0;
            }
        }
    }

    @Override
    public void onEventMainThread(Object event) {
        if (event instanceof RefreshFragmentEvent) {
            status = ((RefreshFragmentEvent)event).getStatus();
            current_page = 1;
            isRefreshing = true;
            isLoading = false;
            isLoaded = false;
            mPresenter.getOrderList(userInfo.getDistributionId(), current_page, Constants.PAGE_SIZE, status);
        }
    }
}