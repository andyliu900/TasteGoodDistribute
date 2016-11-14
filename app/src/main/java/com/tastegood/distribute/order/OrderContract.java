package com.tastegood.distribute.order;

import com.tastegood.distribute.gobal.BasePresenter;
import com.tastegood.distribute.gobal.BaseView;
import com.tastegood.distribute.order.domain.model.Order;

import java.util.List;

/**
 * Created by surandy on 2016/10/28.
 */

public interface OrderContract {

    interface OrderView extends BaseView<OrderContract.Presenter> {

        void showLoadingView();

        void showLoadingView(String message);

        void hideLoadingView();

        void showNetErrorView(boolean isFirst);

        void hideNetErrorView();

        void showNoDataView(boolean isFirst);

        void hideNoDataView();

        void showBusinessError(String businessErrorMessage);

        void showOrderList(List<Order> orderList);

        void showOrderArrivedResult(boolean arrivedResult);

    }

    interface Presenter extends BasePresenter {

        void getOrderList(long distributeId, int pageNumber, int pageSize, int status);

        void senderSureArrived(long distributeId, String orderNo);

    }

}
