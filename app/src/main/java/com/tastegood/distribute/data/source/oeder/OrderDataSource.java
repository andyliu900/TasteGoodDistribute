package com.tastegood.distribute.data.source.oeder;

import android.support.annotation.NonNull;

import com.tastegood.distribute.data.source.oeder.model.GetOrderParams;
import com.tastegood.distribute.data.source.oeder.model.OrderArrivedParams;
import com.tastegood.distribute.order.domain.model.Order;

import java.util.List;

/**
 * 数据源接口
 *
 * Created by surandy on 2016/10/13.
 */

public interface OrderDataSource {

    interface LoadOrdersCallback {

        void onOrdersLoaded(List<Order> tasks);

        void onOrdersBusinessFail(String errorMessage);

        void onDataNotAvailable();
    }

    interface OrderArrivedCallback {

        void onOrderArrived(boolean arrivedResult);

        void onOrdersBusinessFail(String errorMessage);

        void onDataNotAvailable();
    }

    void getOrders(GetOrderParams params, @NonNull LoadOrdersCallback callback);

    void senderSureArrived(OrderArrivedParams params, @NonNull OrderArrivedCallback callback);

}
