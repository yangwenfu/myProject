package com.xinyunlian.jinfu.loan.dto.risk;

import java.io.Serializable;

/**
 * @author Willwang
 */
public class AuthorizeDto implements Serializable{

    private String username;

    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
