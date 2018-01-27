package com.xinyunlian.jinfu.udesk.dto;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by King on 2017/4/11.
 */
public class CustomerDto {
    private List<String> cellphones = new ArrayList<>();
    private String nick_name;

    public void setCellphones(List<String> cellphones) {
        this.cellphones = cellphones;
    }

    public List<String> getCellphones() {
        return cellphones;
    }

    public String getNick_name() {
        return nick_name;
    }

    public void setNick_name(String nick_name) {
        this.nick_name = nick_name;
    }
}
