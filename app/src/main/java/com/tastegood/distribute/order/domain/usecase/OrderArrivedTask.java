package com.tastegood.distribute.order.domain.usecase;

import android.support.annotation.NonNull;

import com.tastegood.distribute.data.source.oeder.OrderDataSource;
import com.tastegood.distribute.data.source.oeder.OrderRepository;
import com.tastegood.distribute.data.source.oeder.model.OrderArrivedParams;
import com.tastegood.distribute.gobal.UseCase;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by surandy on 2016/11/13.
 */

public class OrderArrivedTask extends UseCase<OrderArrivedTask.RequestValues, OrderArrivedTask.ResponseValue> {

    private final OrderRepository mOrderRepository;

    public OrderArrivedTask(@NonNull OrderRepository orderRepository) {
        mOrderRepository = checkNotNull(orderRepository, "orderRepository cannot be null!");
    }

    @Override
    protected void executeUseCase(OrderArrivedTask.RequestValues requestValues) {
        OrderArrivedParams params = new OrderArrivedParams();
        params.setDistributionId(requestValues.mDistributionId);
        params.setOrderNo(requestValues.mOrderNo);

        mOrderRepository.senderSureArrived(params, new OrderDataSource.OrderArrivedCallback() {
            @Override
            public void onOrderArrived(boolean arrivedResult) {
                OrderArrivedTask.ResponseValue responseValue = new OrderArrivedTask.ResponseValue(arrivedResult);
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
        private final String mOrderNo;

        public RequestValues(long distributionId, String orderNo) {
            mDistributionId = distributionId;
            mOrderNo = orderNo;
        }

    }

    public static final class ResponseValue implements UseCase.ResponseValue {

        private final boolean mArrivedResult;

        public ResponseValue(@NonNull boolean arrivedResult) {
            mArrivedResult = arrivedResult;
        }

        public boolean getArrivedResult() {
            return mArrivedResult;
        }
    }

}
