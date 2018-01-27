package com.xinyunlian.jinfu.user.entity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by dongfangchao on 2016/12/6/0006.
 */
@Entity
@Table(name = "mgt_dept_user")
public class MgtDeptUserPo implements Serializable {

    private static final long serialVersionUID = -1904991788795903135L;
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "DEPT_ID")
    private Long deptId;

    @Column(name = "DEPT_TREE_PATH")
    private String deptTreePath;

    @Column(name = "USER_ID")
    private String userId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getDeptId() {
        return deptId;
    }

    public void setDeptId(Long deptId) {
        this.deptId = deptId;
    }

    public String getDeptTreePath() {
        return deptTreePath;
    }

    public void setDeptTreePath(String deptTreePath) {
        this.deptTreePath = deptTreePath;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
