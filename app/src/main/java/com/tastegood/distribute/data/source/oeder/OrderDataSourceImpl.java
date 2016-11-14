package com.tastegood.distribute.data.source.oeder;

import android.support.annotation.NonNull;

import com.tastegood.distribute.api.BaseCallBack;
import com.tastegood.distribute.api.OrderApi;
import com.tastegood.distribute.api.RetrofitInstance;
import com.tastegood.distribute.data.source.oeder.model.GetOrderParams;
import com.tastegood.distribute.data.source.oeder.model.OrderArrivedParams;
import com.tastegood.distribute.gobal.BaseApiParams;
import com.tastegood.distribute.gobal.BaseApiResponEntity;
import com.tastegood.distribute.order.domain.model.Order;

import java.util.List;

import retrofit2.Call;
import retrofit2.Response;


/**
 * 订单网络获取数据实现
 *
 * Created by surandy on 2016/10/13.
 */

public class OrderDataSourceImpl implements OrderDataSource {

    private static OrderDataSourceImpl INSTANCE;

    private OrderApi orderApi;

    public static OrderDataSourceImpl getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new OrderDataSourceImpl();
        }
        return INSTANCE;
    }

    private OrderDataSourceImpl() {
        if (orderApi == null) {
            orderApi = RetrofitInstance.getInstance().create(OrderApi.class);
        }
    }

    @Override
    public void getOrders(GetOrderParams params, final @NonNull LoadOrdersCallback callback) {
        BaseApiParams baseApiParams = new BaseApiParams(params);
        final Call<BaseApiResponEntity<List<Order>>> call = orderApi.getOrders(baseApiParams);

//        try {
//            Response<BaseApiResponEntity<List<Order>>> result =  call.execute();
//            System.out.println(result.body());
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        call.enqueue(new BaseCallBack<BaseApiResponEntity<List<Order>>>() {
            @Override
            public void doSuccess(Response<BaseApiResponEntity<List<Order>>> response) {
                callback.onOrdersLoaded(response.body().getData());
            }

            @Override
            public void doBusinessFail(Response<BaseApiResponEntity<List<Order>>> response) {
                callback.onOrdersBusinessFail(response.body().getMessage());
            }

            @Override
            public void doFail(Throwable error) {
                callback.onDataNotAvailable();
            }
        });
    }

    @Override
    public void senderSureArrived(OrderArrivedParams params, final @NonNull OrderArrivedCallback callback) {
        BaseApiParams baseApiParams = new BaseApiParams(params);
        final Call<BaseApiResponEntity<Boolean>> call = orderApi.orderArrived(baseApiParams);

//        try {
//            Response<BaseApiResponEntity<List<Order>>> result =  call.execute();
//            System.out.println(result.body());
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        call.enqueue(new BaseCallBack<BaseApiResponEntity<Boolean>>() {

            @Override
            public void doSuccess(Response<BaseApiResponEntity<Boolean>> response) {
                callback.onOrderArrived(response.body().getData());
            }

            @Override
            public void doBusinessFail(Response<BaseApiResponEntity<Boolean>> response) {
                callback.onOrdersBusinessFail(response.body().getMessage());
            }

            @Override
            public void doFail(Throwable error) {
                callback.onDataNotAvailable();
            }
        });
    }

}
