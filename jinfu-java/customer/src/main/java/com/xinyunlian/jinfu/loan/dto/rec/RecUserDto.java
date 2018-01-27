package com.xinyunlian.jinfu.loan.dto.rec;

import java.io.Serializable;

/**
 * @author willwang
 */
public class RecUserDto implements Serializable {

    /**
     * 推荐人手机号
     */
    private String recUser;

    /**
     * 推荐人姓名
     */
    private String recName;

    public String getRecUser() {
        return recUser;
    }

    public void setRecUser(String recUser) {
        this.recUser = recUser;
    }

    public String getRecName() {
        return recName;
    }

    public void setRecName(String recName) {
        this.recName = recName;
    }

}
