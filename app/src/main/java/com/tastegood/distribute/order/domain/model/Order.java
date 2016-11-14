package com.tastegood.distribute.order.domain.model;

import java.util.ArrayList;

/**
 * 订单主体信息
 *
 * Created by surandy on 2016/10/13.
 */

public class Order {

    private int id;

    private String orderNo;

    private int userId;

    private String nickname;

    private int payStatus;

    private long createTime;

    private long canelTime;

    private long callbackTime;

    private int orderStatus;

    private long printTime;

    private long getTime;

    private long sendoneTime;

    private long userConfirmTime;

    private int payType;

    private double totalCost;

    private double balancePrice;

    private double givenPrice;

    private double openPayPrice;

    private double lunchPrice;

    private double sendPrice;

    private double discountPrice;

    private long discountId;

    private long rechargeRecordId;

    private String rechargeRecordNo;

    private String cartJson;

    private String respJson;

    private int distributionId;

    private String loginName;

    private int storefrontId;

    private String storefrontName;

    private String storefrontTelephone;

    private String storefrontAddress;

    private int printNum;

    private String receiver;

    private String mobile;

    private String address;

    private double lng;

    private double lat;

    private String doorNum;

    private int evaluateStatus;

    private ArrayList<Cart> cartList;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(int payStatus) {
        this.payStatus = payStatus;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public long getCanelTime() {
        return canelTime;
    }

    public void setCanelTime(long canelTime) {
        this.canelTime = canelTime;
    }

    public long getCallbackTime() {
        return callbackTime;
    }

    public void setCallbackTime(long callbackTime) {
        this.callbackTime = callbackTime;
    }

    public int getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(int orderStatus) {
        this.orderStatus = orderStatus;
    }

    public long getPrintTime() {
        return printTime;
    }

    public void setPrintTime(long printTime) {
        this.printTime = printTime;
    }

    public long getGetTime() {
        return getTime;
    }

    public void setGetTime(long getTime) {
        this.getTime = getTime;
    }

    public long getSendoneTime() {
        return sendoneTime;
    }

    public void setSendoneTime(long sendoneTime) {
        this.sendoneTime = sendoneTime;
    }

    public long getUserConfirmTime() {
        return userConfirmTime;
    }

    public void setUserConfirmTime(long userConfirmTime) {
        this.userConfirmTime = userConfirmTime;
    }

    public int getPayType() {
        return payType;
    }

    public void setPayType(int payType) {
        this.payType = payType;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }

    public double getBalancePrice() {
        return balancePrice;
    }

    public void setBalancePrice(double balancePrice) {
        this.balancePrice = balancePrice;
    }

    public double getGivenPrice() {
        return givenPrice;
    }

    public void setGivenPrice(double givenPrice) {
        this.givenPrice = givenPrice;
    }

    public double getOpenPayPrice() {
        return openPayPrice;
    }

    public void setOpenPayPrice(double openPayPrice) {
        this.openPayPrice = openPayPrice;
    }

    public double getLunchPrice() {
        return lunchPrice;
    }

    public void setLunchPrice(double lunchPrice) {
        this.lunchPrice = lunchPrice;
    }

    public double getSendPrice() {
        return sendPrice;
    }

    public void setSendPrice(double sendPrice) {
        this.sendPrice = sendPrice;
    }

    public double getDiscountPrice() {
        return discountPrice;
    }

    public void setDiscountPrice(double discountPrice) {
        this.discountPrice = discountPrice;
    }

    public long getDiscountId() {
        return discountId;
    }

    public void setDiscountId(long discountId) {
        this.discountId = discountId;
    }

    public long getRechargeRecordId() {
        return rechargeRecordId;
    }

    public void setRechargeRecordId(long rechargeRecordId) {
        this.rechargeRecordId = rechargeRecordId;
    }

    public String getRechargeRecordNo() {
        return rechargeRecordNo;
    }

    public void setRechargeRecordNo(String rechargeRecordNo) {
        this.rechargeRecordNo = rechargeRecordNo;
    }

    public String getCartJson() {
        return cartJson;
    }

    public void setCartJson(String cartJson) {
        this.cartJson = cartJson;
    }

    public String getRespJson() {
        return respJson;
    }

    public void setRespJson(String respJson) {
        this.respJson = respJson;
    }

    public int getDistributionId() {
        return distributionId;
    }

    public void setDistributionId(int distributionId) {
        this.distributionId = distributionId;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public int getStorefrontId() {
        return storefrontId;
    }

    public void setStorefrontId(int storefrontId) {
        this.storefrontId = storefrontId;
    }

    public String getStorefrontName() {
        return storefrontName;
    }

    public void setStorefrontName(String storefrontName) {
        this.storefrontName = storefrontName;
    }

    public String getStorefrontTelephone() {
        return storefrontTelephone;
    }

    public void setStorefrontTelephone(String storefrontTelephone) {
        this.storefrontTelephone = storefrontTelephone;
    }

    public String getStorefrontAddress() {
        return storefrontAddress;
    }

    public void setStorefrontAddress(String storefrontAddress) {
        this.storefrontAddress = storefrontAddress;
    }

    public int getPrintNum() {
        return printNum;
    }

    public void setPrintNum(int printNum) {
        this.printNum = printNum;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public String getDoorNum() {
        return doorNum;
    }

    public void setDoorNum(String doorNum) {
        this.doorNum = doorNum;
    }

    public int getEvaluateStatus() {
        return evaluateStatus;
    }

    public void setEvaluateStatus(int evaluateStatus) {
        this.evaluateStatus = evaluateStatus;
    }

    public ArrayList<Cart> getCartList() {
        return cartList;
    }

    public void setCartList(ArrayList<Cart> cartList) {
        this.cartList = cartList;
    }
}
