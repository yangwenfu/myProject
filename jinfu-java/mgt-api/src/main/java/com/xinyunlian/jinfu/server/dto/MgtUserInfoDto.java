package com.xinyunlian.jinfu.server.dto;

import java.io.Serializable;
import java.util.Map;
import java.util.Set;

/**
 * Created by dongfangchao on 2016/12/27/0027.
 */
public class MgtUserInfoDto implements Serializable {

    private static final long serialVersionUID = 4834206597657861299L;

    private String userId;

    private String name;

    private String mobile;

    private String token;

    private Map<String, Object> paramMap;

    private Set<String> roleCodes;

    private Set<String> permissionCodes;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Map<String, Object> getParamMap() {
        return paramMap;
    }

    public void setParamMap(Map<String, Object> paramMap) {
        this.paramMap = paramMap;
    }

    public Set<String> getRoleCodes() {
        return roleCodes;
    }

    public void setRoleCodes(Set<String> roleCodes) {
        this.roleCodes = roleCodes;
    }

    public Set<String> getPermissionCodes() {
        return permissionCodes;
    }

    public void setPermissionCodes(Set<String> permissionCodes) {
        this.permissionCodes = permissionCodes;
    }
}
