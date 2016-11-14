package com.tastegood.distribute.source;

import com.tastegood.distribute.data.source.user.UserDataSource;
import com.tastegood.distribute.data.source.user.UserDataSourceImpl;
import com.tastegood.distribute.data.source.user.model.UserLoginParams;
import com.tastegood.distribute.data.source.user.model.UserLogoutParams;
import com.tastegood.distribute.util.MD5Util;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

/**
 * 数据获取单元测试
 *
 * Created by surandy on 2016/10/14.
 */

public class UserRepositoryTest {

    @Mock
    private UserDataSourceImpl mUserDataSourceImpl;

    @Mock
    private UserDataSource.UserLoginCallback mUserLoginCallback;

    @Mock
    private UserDataSource.UserLogoutCallback mUserLogoutCallback;

    @Before
    public void setupUserRepository() {
        MockitoAnnotations.initMocks(this);

        mUserDataSourceImpl = UserDataSourceImpl.getInstance();
    }

    @After
    public void destoryRepository() {
//        OrderRepository.destroyInstance();
    }

    @Test
    public void login_remoteDataSource() {
        UserLoginParams params = new UserLoginParams();
        params.setLoginName("xxxxx");
        params.setPassword(MD5Util.md5("123"));
        params.setDevicesn("12313123123");

        mUserDataSourceImpl.login(params, mUserLoginCallback);
    }

    @Test
    public void logout_remoteDataSource() {
        UserLogoutParams params = new UserLogoutParams();
        params.setNickname("我是聪明小天屎");
        params.setDevicesn("j6rDVX8zJ3yE1L2uon1V0xaMYaiucvfK");

        mUserDataSourceImpl.logout(params, mUserLogoutCallback);
    }

}
