package com.tastegood.distribute.source;


import com.tastegood.distribute.data.source.device.DeviceDataSource;
import com.tastegood.distribute.data.source.device.DeviceDataSourceImpl;
import com.tastegood.distribute.data.source.device.model.DeviceSignParams;
import com.tastegood.distribute.data.source.oeder.OrderRepository;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

/**
 * Created by surandy on 2016/10/18.
 */

public class DeviceRepositoryTest {

    @Mock
    private DeviceDataSourceImpl mDeviceDataSourceImpl;

    @Mock
    private DeviceDataSource.DeviceSignInCallback mDeviceSignInCallback;

    @Before
    public void setupDeviceRepository() {
        MockitoAnnotations.initMocks(this);

        mDeviceDataSourceImpl = DeviceDataSourceImpl.getInstance();
    }

    @After
    public void destoryRepository() {
        OrderRepository.destroyInstance();
    }

    @Test
    public void deviceSignIn_remoteDataSource() {
        DeviceSignParams params = new DeviceSignParams();
        params.setClientType("ANDROID");
        params.setClientUdid("123123123");
        params.setPhoneBrand("");
        params.setPhoneModel("");
        params.setPhonePlatform("");
        params.setAppType("DZ");
        mDeviceDataSourceImpl.deviceSignIn(params, mDeviceSignInCallback);
    }

}
