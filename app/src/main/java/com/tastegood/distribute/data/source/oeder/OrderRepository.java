package com.tastegood.distribute.data.source.oeder;

import android.support.annotation.NonNull;

import com.tastegood.distribute.data.source.oeder.model.GetOrderParams;
import com.tastegood.distribute.data.source.oeder.model.OrderArrivedParams;
import com.tastegood.distribute.order.domain.model.Order;

import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * 订单模块获取数据功能
 *
 * Created by surandy on 2016/10/13.
 */

public class OrderRepository implements OrderDataSource {

    private static OrderRepository INSTANCE = null;

    private final OrderDataSource mOrderRemoteDataSource;

    private OrderRepository(@NonNull OrderDataSource orderDataSource) {
        mOrderRemoteDataSource = checkNotNull(orderDataSource);
    }

    public static OrderRepository getInstance(OrderDataSource orderDataSource) {
        if (INSTANCE == null) {
            INSTANCE = new OrderRepository(orderDataSource);
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }

    @Override
    public void getOrders(@NonNull GetOrderParams orderParams, @NonNull final LoadOrdersCallback callback) {
        checkNotNull(callback);

        mOrderRemoteDataSource.getOrders(orderParams, new LoadOrdersCallback() {
            @Override
            public void onOrdersLoaded(List<Order> tasks) {
                callback.onOrdersLoaded(tasks);
            }

            @Override
            public void onOrdersBusinessFail(String errorMessage) {
                callback.onOrdersBusinessFail(errorMessage);
            }

            @Override
            public void onDataNotAvailable() {
                callback.onDataNotAvailable();
            }
        });
    }

    @Override
    public void senderSureArrived(OrderArrivedParams params, final @NonNull OrderArrivedCallback callback) {
        checkNotNull(callback);

        mOrderRemoteDataSource.senderSureArrived(params, new OrderArrivedCallback() {
            @Override
            public void onOrderArrived(boolean arrivedResult) {
                callback.onOrderArrived(arrivedResult);
            }

            @Override
            public void onOrdersBusinessFail(String errorMessage) {
                callback.onOrdersBusinessFail(errorMessage);
            }

            @Override
            public void onDataNotAvailable() {
                callback.onDataNotAvailable();
            }
        });
    }

}
