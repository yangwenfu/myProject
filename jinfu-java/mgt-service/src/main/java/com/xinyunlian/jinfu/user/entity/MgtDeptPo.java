package com.xinyunlian.jinfu.user.entity;

import com.xinyunlian.jinfu.common.entity.BaseMaintainablePo;

import javax.persistence.*;

/**
 * Created by dongfangchao on 2016/12/6/0006.
 */
@Entity
@Table(name = "mgt_dept")
public class MgtDeptPo extends BaseMaintainablePo {

    private static final long serialVersionUID = -6495615402093899311L;

    @Id
    @Column(name = "DEPT_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long deptId;

    @Column(name = "DEPT_NAME")
    private String deptName;

    @Column(name = "DEPT_PARENT")
    private Long deptParent;

    @Column(name = "DEPT_TREE_PATH")
    private String deptTreePath;

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
