package com.xinyunlian.jinfu.user.dto;

import org.apache.commons.collections.CollectionUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by dongfangchao on 2016/12/6/0006.
 */
public class MgtDeptDto implements Serializable {

    private static final long serialVersionUID = -8332445933022974459L;

    private Long deptId;

    private String deptName;

    private Long deptParent;

    private String deptTreePath;

    private List<MgtDeptDto> childDepts = new ArrayList<>();

    public void getDeptTree(List<MgtDeptDto> allDepts){
        if (!CollectionUtils.isEmpty(allDepts)){
            allDepts.forEach( dept -> {
                if (dept.getDeptParent().equals(this.deptId)){
                    dept.getDeptTree(allDepts);
                    this.childDepts.add(dept);
                }
            });
        }
    }

    public List<MgtDeptDto> getChildDepts() {
        return childDepts;
    }

    public void setChildDepts(List<MgtDeptDto> childDepts) {
        this.childDepts = childDepts;
    }

    public Long getDeptId() {
        return deptId;
    }

    public void setDeptId(Long deptId) {
        this.deptId = deptId;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public Long getDeptParent() {
        return deptParent;
    }

    public void setDeptParent(Long deptParent) {
        this.deptParent = deptParent;
    }

    public String getDeptTreePath() {
        return deptTreePath;
    }

    public void setDeptTreePath(String deptTreePath) {
        this.deptTreePath = deptTreePath;
    }

}
