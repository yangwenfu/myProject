package com.xinyunlian.jinfu.udesk.dto;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by King on 2017/4/11.
 */
public class CustomerUpdateDto {
    private List<List<Long>> cellphones = new ArrayList<>();
    private String nick_name;

    public List<List<Long>> getCellphones() {
        return cellphones;
    }

    public void setCellphones(List<List<Long>> cellphones) {
        this.cellphones = cellphones;
    }

    public String getNick_name() {
        return nick_name;
    }

    public void setNick_name(String nick_name) {
        this.nick_name = nick_name;
    }
}
