package com.tastegood.distribute.source;

import com.tastegood.distribute.data.source.oeder.OrderDataSource;
import com.tastegood.distribute.data.source.oeder.OrderDataSourceImpl;
import com.tastegood.distribute.data.source.oeder.OrderRepository;
import com.tastegood.distribute.data.source.oeder.model.GetOrderParams;
import com.tastegood.distribute.data.source.oeder.model.PrintOrderParams;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

/**
 * 数据获取单元测试
 *
 * Created by surandy on 2016/10/14.
 */

public class OrderRepositoryTest {

    @Mock
    private OrderDataSourceImpl mOrderDataSourceImpl;

    @Mock
    private OrderDataSource.LoadOrdersCallback mLoadOrdersCallback;

    @Mock
    private OrderDataSource.PrintOrdersBatchCallback mPrintOrdersBatchCallback;

    @Captor
    private ArgumentCaptor<OrderDataSource.LoadOrdersCallback> mOrdersCallbackCaptor;

    @Before
    public void setupOrderRepository() {
        MockitoAnnotations.initMocks(this);

        mOrderDataSourceImpl = OrderDataSourceImpl.getInstance();
    }

    @After
    public void destoryRepository() {
        OrderRepository.destroyInstance();
    }

//    @Test
//    public void getOrders_repository() {
//        mOrderRepository.getOrders(mLoadOrdersCallback);
//
//        verify(mOrderDataSource).getOrders(mOrdersCallbackCaptor.capture());
//
//        mOrdersCallbackCaptor.getValue().onDataNotAvailable();
//
//        verify(mOrderDataSource).getOrders(mOrdersCallbackCaptor.capture());
//
//        mOrdersCallbackCaptor.getValue().onOrdersLoaded(ORDERS);
//
//        mOrderRepository.getOrders(mLoadOrdersCallback);
//
//    }

    @Test
    public void getOrders_remoteDataSource() {
        GetOrderParams params = new GetOrderParams();
        params.setStorefrontId(5);
        params.setPageNumber(1);
        params.setPageSize(20);
        params.setStatus(0);
        params.setDevicesn("j6rDVX8zJ3yE1L2uon1V0xaMYaiucvfK");

        mOrderDataSourceImpl.getOrders(params, mLoadOrdersCallback);
    }

    @Test
    public void printOrders_remoteDataSource() {
        PrintOrderParams params = new PrintOrderParams();
        params.setStorefrontId(5);
        params.setOrderIds(new int[]{6, 8});
        params.setDevicesn("j6rDVX8zJ3yE1L2uon1V0xaMYaiucvfK");

        mOrderDataSourceImpl.printOrdersBatch(params, mPrintOrdersBatchCallback);
    }

}
