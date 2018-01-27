package com.xinyunlian.jinfu.spider.dto;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by menglei on 2016年11月08日.
 */
public class AllInfo {

    private boolean success;//是否能获取参数
    private UserInfo userInfo;
    private List<OrderInfo> orderList;

    public AllInfo() {
        success = false;
        userInfo = new UserInfo();
        orderList = new ArrayList<>();
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    public List<OrderInfo> getOrderList() {
        return orderList;
    }

    public void setOrderList(List<OrderInfo> orderList) {
        this.orderList = orderList;
    }
}
