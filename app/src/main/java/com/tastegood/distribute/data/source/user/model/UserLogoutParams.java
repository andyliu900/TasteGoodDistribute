package com.tastegood.distribute.data.source.user.model;

import com.tastegood.distribute.data.source.BaseParams;

/**
 * Created by surandy on 2016/10/17.
 */

public class UserLogoutParams extends BaseParams {

    private String nickname;

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

}
