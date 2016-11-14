package com.tastegood.distribute.order.domain.model;

/**
 * 购物车实体
 *
 * Created by surandy on 2016/10/27.
 */

public class Cart {

    private long id;

    private long userId;

    private long storeId;

    private long menuId;

    private String menuName;

    private double orgPrice;

    private double lunchPrice;

    private long num;

    private String specifications;

    private String remark;

    private int specialities;

    private double activityPrice;

    private String createTime;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getStoreId() {
        return storeId;
    }

    public void setStoreId(long storeId) {
        this.storeId = storeId;
    }

    public long getMenuId() {
        return menuId;
    }

    public void setMenuId(long menuId) {
        this.menuId = menuId;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public double getOrgPrice() {
        return orgPrice;
    }

    public void setOrgPrice(double orgPrice) {
        this.orgPrice = orgPrice;
    }

    public double getLunchPrice() {
        return lunchPrice;
    }

    public void setLunchPrice(double lunchPrice) {
        this.lunchPrice = lunchPrice;
    }

    public long getNum() {
        return num;
    }

    public void setNum(long num) {
        this.num = num;
    }

    public String getSpecifications() {
        return specifications;
    }

    public void setSpecifications(String specifications) {
        this.specifications = specifications;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public int getSpecialities() {
        return specialities;
    }

    public void setSpecialities(int specialities) {
        this.specialities = specialities;
    }

    public double getActivityPrice() {
        return activityPrice;
    }

    public void setActivityPrice(double activityPrice) {
        this.activityPrice = activityPrice;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
