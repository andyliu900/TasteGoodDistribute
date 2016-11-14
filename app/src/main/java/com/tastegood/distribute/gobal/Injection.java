package com.tastegood.distribute.gobal;

import com.tastegood.distribute.data.source.device.DeviceDataSourceImpl;
import com.tastegood.distribute.data.source.device.DeviceRepository;
import com.tastegood.distribute.data.source.oeder.OrderDataSourceImpl;
import com.tastegood.distribute.data.source.oeder.OrderRepository;
import com.tastegood.distribute.data.source.user.UserDataSourceImpl;
import com.tastegood.distribute.data.source.user.UserRepository;
import com.tastegood.distribute.order.domain.usecase.OrderArrivedTask;
import com.tastegood.distribute.order.domain.usecase.OrderTask;
import com.tastegood.distribute.user.domain.usecase.LoginTask;
import com.tastegood.distribute.user.domain.usecase.LogoutTask;

/**
 * Created by surandy on 2016/10/18.
 */

public class Injection {

    public static DeviceRepository provideDeviceRepository() {
        return DeviceRepository.getInstance(DeviceDataSourceImpl.getInstance());
    }

    public static UserRepository provideUserRepostitory() {
        return UserRepository.getInstance(UserDataSourceImpl.getInstance());
    }

    public static OrderRepository provideOrderRepostitory() {
        return OrderRepository.getInstance(OrderDataSourceImpl.getInstance());
    }

    public static UseCaseHandler provideUseCaseHandler() {
        return UseCaseHandler.getInstance();
    }

    public static LoginTask provideLoginTask() {
        return new LoginTask(Injection.provideUserRepostitory());
    }

    public static LogoutTask provideLogoutTask() {
        return new LogoutTask(Injection.provideUserRepostitory());
    }

    public static OrderTask provideOrderTask() {
        return new OrderTask(Injection.provideOrderRepostitory());
    }

    public static OrderArrivedTask provideOrderArrivedTask() {
        return new OrderArrivedTask(Injection.provideOrderRepostitory());
    }

}
