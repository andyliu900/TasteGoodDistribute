package com.tastegood.distribute.order.domain.usecase;

import android.support.annotation.NonNull;

import com.tastegood.distribute.gobal.UseCase;
import com.tastegood.distribute.order.domain.model.Order;
import com.tastegood.distribute.data.source.oeder.OrderDataSource;
import com.tastegood.distribute.data.source.oeder.OrderRepository;
import com.tastegood.distribute.data.source.oeder.model.GetOrderParams;

import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by surandy on 2016/10/28.
 */

public class OrderTask extends UseCase<OrderTask.RequestValues, OrderTask.ResponseValue> {

    private final OrderRepository mOrderRepository;

    public OrderTask(@NonNull OrderRepository orderRepository) {
        mOrderRepository = checkNotNull(orderRepository, "orderRepository cannot be null!");
    }

    @Override
    protected void executeUseCase(OrderTask.RequestValues requestValues) {
        GetOrderParams params = new GetOrderParams();
        params.setDistributionId(requestValues.mDistributionId);
        params.setPageNumber(requestValues.mPageNumber);
        params.setPageSize(requestValues.mPageSize);
        params.setStatus(requestValues.mStatus);

        mOrderRepository.getOrders(params, new OrderDataSource.LoadOrdersCallback() {

            @Override
            public void onOrdersLoaded(List<Order> tasks) {
                OrderTask.ResponseValue responseValue = new OrderTask.ResponseValue(tasks);
                getUseCaseCallback().onSuccess(responseValue);
            }

            @Override
            public void onOrdersBusinessFail(String errorMessage) {
                getUseCaseCallback().onBusinessError(errorMessage);
            }

            @Override
            public void onDataNotAvailable() {
                getUseCaseCallback().onError();
            }

        });
    }

    public static final class RequestValues implements UseCase.RequestValues {

        private final long mDistributionId;
        private final int mPageNumber;
        private final int mPageSize;
        private final int mStatus;

        public RequestValues(long distributionId, int pageNumber, int pageSize, int status) {
            mDistributionId = distributionId;
            mPageNumber = pageNumber;
            mPageSize = pageSize;
            mStatus = status;
        }

    }

    public static final class ResponseValue implements UseCase.ResponseValue {

        private final List<Order> mOrderList;

        public ResponseValue(@NonNull List<Order> orderList) {
            mOrderList = checkNotNull(orderList, "order cannot be null!");
        }

        public List<Order> getOrders() {
            return mOrderList;
        }
    }

}
