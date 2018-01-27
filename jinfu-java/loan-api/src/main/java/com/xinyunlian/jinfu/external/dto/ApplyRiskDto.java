package com.xinyunlian.jinfu.external.dto;

import java.io.Serializable;

/**
 * Created by godslhand on 2017/7/1.
 */
public class ApplyRiskDto implements Serializable {

    private String bizInfo01;//店铺档位
    private String bizInfo04;//店铺类型
    private String bizInfo05;//市场类型
    private String bizInfo06;//用户类型
    private String bizInfo07;//店铺经营年限
    private String bizInfo12;//负责人是否一致
    private String bizInfo13;//负责人电话是否一致
    private String bizInfo14;//烟草登记地址是否一致
    private String bizInfo08 ;//一年定烟月份数
    private String bizInfo09 ;//月均定烟额
    private String bizInfo10;// 订烟额波动率
    private String bizInfo11;// 订烟活跃度
    private String bizInfo26; //近两周是否有定烟数据




    public String getBizInfo01() {
        return bizInfo01;
    }

    public void setBizInfo01(String bizInfo01) {
        this.bizInfo01 = bizInfo01;
    }

    public String getBizInfo04() {
        return bizInfo04;
    }

    public void setBizInfo04(String bizInfo04) {
        this.bizInfo04 = bizInfo04;
    }

    public String getBizInfo05() {
        return bizInfo05;
    }

    public void setBizInfo05(String bizInfo05) {
        this.bizInfo05 = bizInfo05;
    }

    public String getBizInfo06() {
        return bizInfo06;
    }

    public void setBizInfo06(String bizInfo06) {
        this.bizInfo06 = bizInfo06;
    }

    public String getBizInfo07() {
        return bizInfo07;
    }

    public void setBizInfo07(String bizInfo07) {
        this.bizInfo07 = bizInfo07;
    }

    public String getBizInfo12() {
        return bizInfo12;
    }

    public void setBizInfo12(String bizInfo12) {
        this.bizInfo12 = bizInfo12;
    }

    public String getBizInfo13() {
        return bizInfo13;
    }

    public void setBizInfo13(String bizInfo13) {
        this.bizInfo13 = bizInfo13;
    }

    public String getBizInfo14() {
        return bizInfo14;
    }

    public void setBizInfo14(String bizInfo14) {
        this.bizInfo14 = bizInfo14;
    }

    public String getBizInfo08() {
        return bizInfo08;
    }

    public void setBizInfo08(String bizInfo08) {
        this.bizInfo08 = bizInfo08;
    }

    public String getBizInfo09() {
        return bizInfo09;
    }

    public void setBizInfo09(String bizInfo09) {
        this.bizInfo09 = bizInfo09;
    }

    public String getBizInfo10() {
        return bizInfo10;
    }

    public void setBizInfo10(String bizInfo10) {
        this.bizInfo10 = bizInfo10;
    }

    public String getBizInfo11() {
        return bizInfo11;
    }

    public void setBizInfo11(String bizInfo11) {
        this.bizInfo11 = bizInfo11;
    }

    public String getBizInfo26() {
        return bizInfo26;
    }

    public void setBizInfo26(String bizInfo26) {
        this.bizInfo26 = bizInfo26;
    }

    @Override
    public String toString() {
        return "ApplyRiskDto{" +
                "bizInfo01='" + bizInfo01 + '\'' +
                ", bizInfo04='" + bizInfo04 + '\'' +
                ", bizInfo05='" + bizInfo05 + '\'' +
                ", bizInfo06='" + bizInfo06 + '\'' +
                ", bizInfo07='" + bizInfo07 + '\'' +
                ", bizInfo12='" + bizInfo12 + '\'' +
                ", bizInfo13='" + bizInfo13 + '\'' +
                ", bizInfo14='" + bizInfo14 + '\'' +
                ", bizInfo08='" + bizInfo08 + '\'' +
                ", bizInfo09='" + bizInfo09 + '\'' +
                ", bizInfo10='" + bizInfo10 + '\'' +
                ", bizInfo11='" + bizInfo11 + '\'' +
                ", bizInfo26='" + bizInfo26 + '\'' +
                '}';
    }
}
