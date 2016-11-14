package com.tastegood.distribute.data.source.user.model;

import com.tastegood.distribute.data.source.BaseParams;

/**
 * Created by surandy on 2016/10/17.
 */

public class UserLoginParams extends BaseParams {

    private String loginName;
    private String password;

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
