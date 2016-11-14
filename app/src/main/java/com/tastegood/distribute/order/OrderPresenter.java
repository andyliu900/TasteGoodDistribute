package com.tastegood.distribute.order;

import android.support.annotation.NonNull;

import com.tastegood.distribute.gobal.UseCase;
import com.tastegood.distribute.gobal.UseCaseHandler;
import com.tastegood.distribute.order.domain.model.Order;
import com.tastegood.distribute.order.domain.usecase.OrderArrivedTask;
import com.tastegood.distribute.order.domain.usecase.OrderTask;

import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by surandy on 2016/10/28.
 */

public class OrderPresenter implements OrderContract.Presenter {

    private OrderContract.OrderView mOrderView;
    private OrderTask mOrderTask;
    private OrderArrivedTask mOrderArrivedTask;

    private final UseCaseHandler mUseCaseHandler;

    public OrderPresenter(@NonNull UseCaseHandler useCaseHandler,
                          @NonNull OrderContract.OrderView orderView,
                          @NonNull OrderTask orderTask,
                          @NonNull OrderArrivedTask orderArrivedTask) {
        mUseCaseHandler = checkNotNull(useCaseHandler, "usecaseHandler cannot be null");
        mOrderView = checkNotNull(orderView, "orderView cannot be null!");
        mOrderTask = checkNotNull(orderTask, "orderTask cannot be null!");
        mOrderArrivedTask = checkNotNull(orderArrivedTask, "orderArrivedTask cannot be null!");

        mOrderView.setPresenter(this);
    }

    @Override
    public void getOrderList(long distributeId, final int pageNumber, int pageSize, int status) {
        OrderTask.RequestValues requestValues = new OrderTask.RequestValues(distributeId, pageNumber, pageSize, status);

        mUseCaseHandler.execute(mOrderTask, requestValues,
                new UseCase.UseCaseCallback<OrderTask.ResponseValue>() {
                    @Override
                    public void onSuccess(OrderTask.ResponseValue response) {
                        mOrderView.hideLoadingView();
                        List<Order> orderList = response.getOrders();
                        mOrderView.showOrderList(orderList);
                    }

                    @Override
                    public void onBusinessError(String businessErrorMessage) {
                        mOrderView.hideLoadingView();
                        mOrderView.showBusinessError(businessErrorMessage);
                    }

                    @Override
                    public void onError() {
                        if (pageNumber == 1) {
                            mOrderView.showNetErrorView(true);
                        } else {
                            mOrderView.showNetErrorView(false);
                        }
                        mOrderView.hideLoadingView();
                    }
                });
    }

    @Override
    public void senderSureArrived(long distributeId, String orderNo) {
        OrderArrivedTask.RequestValues requestValues = new OrderArrivedTask.RequestValues(distributeId, orderNo);

        mUseCaseHandler.execute(mOrderArrivedTask, requestValues,
                new UseCase.UseCaseCallback<OrderArrivedTask.ResponseValue>() {

                    @Override
                    public void onSuccess(OrderArrivedTask.ResponseValue response) {
                        mOrderView.hideLoadingView();
                        boolean arrivedResult = response.getArrivedResult();
                        mOrderView.showOrderArrivedResult(arrivedResult);
                    }

                    @Override
                    public void onBusinessError(String businessErrorMessage) {
                        mOrderView.hideLoadingView();
                        mOrderView.showBusinessError(businessErrorMessage);
                    }

                    @Override
                    public void onError() {
                        mOrderView.hideLoadingView();
                    }
                });
    }

}
