package com.xinyunlian.jinfu.carbank.dto;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/6/16 0016.
 */
public class BrandDto implements Serializable {

    private Integer id;

    private String initial;

    private String name;

    private Integer isAllowLoan;

    public Integer getIsAllowLoan() {
        return isAllowLoan;
    }

    public void setIsAllowLoan(Integer isAllowLoan) {
        this.isAllowLoan = isAllowLoan;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getInitial() {
        return initial;
    }

    public void setInitial(String initial) {
        this.initial = initial;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "BrandDto{" +
                "id=" + id +
                ", initial='" + initial + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
