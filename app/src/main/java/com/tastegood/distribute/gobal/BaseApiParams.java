package com.tastegood.distribute.gobal;

import com.tastegood.distribute.util.MD5Util;

/**
 * Created by surandy on 2016/10/17.
 */

public class BaseApiParams {

    private long id;
    private String caller;
    private String sign;
    private Object data;

    public BaseApiParams(Object object) {
        setId(System.currentTimeMillis());
        setCaller(Constants.API_CALLER);
        setSign(MD5Util.md5(Constants.API_CALLER + "clientTime=" + getId()
                + Constants.API_SECRET_KEY));
        setData(object);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCaller() {
        return caller;
    }

    public void setCaller(String caller) {
        this.caller = caller;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
