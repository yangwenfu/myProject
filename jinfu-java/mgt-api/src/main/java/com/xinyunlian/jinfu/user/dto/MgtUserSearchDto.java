package com.xinyunlian.jinfu.user.dto;

import com.xinyunlian.jinfu.common.dto.PagingDto;
import com.xinyunlian.jinfu.user.enums.EMgtUserSearchType;
import com.xinyunlian.jinfu.user.enums.EMgtUserStatus;

import java.util.List;

/**
 * Created by DongFC on 2016-08-23.
 */
public class MgtUserSearchDto extends PagingDto<MgtUserDto> {
    private static final long serialVersionUID = -6104791434152952083L;

    private String userId;

    private String loginId;

    private String password;

    private String name;

    private EMgtUserStatus status;

    private EMgtUserSearchType searchType;

    private Long deptId;

    private String deptTreePath;

    private String fuzzyMatch;

    private List<String> userIdList;

    public String getLoginId() {
        return loginId;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public EMgtUserStatus getStatus() {
        return status;
    }

    public void setStatus(EMgtUserStatus status) {
        this.status = status;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Long getDeptId() {
        return deptId;
    }

    public void setDeptId(Long deptId) {
        this.deptId = deptId;
    }

    public List<String> getUserIdList() {
        return userIdList;
    }

    public void setUserIdList(List<String> userIdList) {
        this.userIdList = userIdList;
    }

    public String getDeptTreePath() {
        return deptTreePath;
    }

    public void setDeptTreePath(String deptTreePath) {
        this.deptTreePath = deptTreePath;
    }

    public EMgtUserSearchType getSearchType() {
        return searchType;
    }

    public void setSearchType(EMgtUserSearchType searchType) {
        this.searchType = searchType;
    }

    public String getFuzzyMatch() {
        return fuzzyMatch;
    }

    public void setFuzzyMatch(String fuzzyMatch) {
        this.fuzzyMatch = fuzzyMatch;
    }
}
