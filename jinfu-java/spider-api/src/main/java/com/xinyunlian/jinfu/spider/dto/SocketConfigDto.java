package com.xinyunlian.jinfu.spider.dto;

/**
 * Created by carrot on 2017/8/7.
 */
public class SocketConfigDto {

    private int loginSuccCode = 10100;

    private String codeImgReq = "0600122701000000";

    private String codeImgRefreshReq = "0a001327050000002d770100";

    private String loginReq = "3000112702000000";

    private String bindReq = "3100102700000000";

    private String loadMapReadyReq = "0600762700000000";

    private String userInfoReq = "13008b4e06000000";

    private String orderListReq = "a32709000000";

    private String toHomeReq = "1600514e04000000456e7465724d61700035006a6f696e00100075270300000005000101000000030000";

    private String toOrderReq = "1600514e08000000456e7465724d61700033006a6f696e00100075271a0000000300af00000056030000";

    private boolean encodePwd = true;

    private String authIp = "218.6.93.66";

    private int authPort = 18001;

    private String clientId = "10077";

    private boolean authCode = false;

    public int getLoginSuccCode() {
        return loginSuccCode;
    }

    public void setLoginSuccCode(int loginSuccCode) {
        this.loginSuccCode = loginSuccCode;
    }

    public String getCodeImgReq() {
        return codeImgReq;
    }

    public void setCodeImgReq(String codeImgReq) {
        this.codeImgReq = codeImgReq;
    }

    public String getLoginReq() {
        return loginReq;
    }

    public void setLoginReq(String loginReq) {
        this.loginReq = loginReq;
    }

    public String getBindReq() {
        return bindReq;
    }

    public void setBindReq(String bindReq) {
        this.bindReq = bindReq;
    }

    public String getUserInfoReq() {
        return userInfoReq;
    }

    public void setUserInfoReq(String userInfoReq) {
        this.userInfoReq = userInfoReq;
    }

    public String getOrderListReq() {
        return orderListReq;
    }

    public void setOrderListReq(String orderListReq) {
        this.orderListReq = orderListReq;
    }

    public String getToHomeReq() {
        return toHomeReq;
    }

    public void setToHomeReq(String toHomeReq) {
        this.toHomeReq = toHomeReq;
    }

    public String getToOrderReq() {
        return toOrderReq;
    }

    public void setToOrderReq(String toOrderReq) {
        this.toOrderReq = toOrderReq;
    }

    public boolean isEncodePwd() {
        return encodePwd;
    }

    public void setEncodePwd(boolean encodePwd) {
        this.encodePwd = encodePwd;
    }

    public String getAuthIp() {
        return authIp;
    }

    public void setAuthIp(String authIp) {
        this.authIp = authIp;
    }

    public int getAuthPort() {
        return authPort;
    }

    public void setAuthPort(int authPort) {
        this.authPort = authPort;
    }

    public String getLoadMapReadyReq() {
        return loadMapReadyReq;
    }

    public void setLoadMapReadyReq(String loadMapReadyReq) {
        this.loadMapReadyReq = loadMapReadyReq;
    }

    public String getCodeImgRefreshReq() {
        return codeImgRefreshReq;
    }

    public void setCodeImgRefreshReq(String codeImgRefreshReq) {
        this.codeImgRefreshReq = codeImgRefreshReq;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public boolean isAuthCode() {
        return authCode;
    }

    public void setAuthCode(boolean authCode) {
        this.authCode = authCode;
    }
}
