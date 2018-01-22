package com.xinyunlian.jinfu.clerk.entity;

import com.xinyunlian.jinfu.clerk.enums.EClerkApplyStatus;
import com.xinyunlian.jinfu.clerk.enums.converters.EClerkApplyStatusConverter;
import com.xinyunlian.jinfu.common.entity.BaseMaintainablePo;

import javax.persistence.*;

/**
 * Created by menglei on 2016-12-06.
 */
@Entity
@Table(name = "clerk_apply")
public class ClerkApplyPo extends BaseMaintainablePo {

    private static final long serialVersionUID = -6027402680599742591L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "APPLY_ID")
    private Long applyId;

    @Column(name = "USER_ID")
    private String userId;

    @Column(name = "CLERK_ID")
    private String clerkId;

    @Column(name = "STATUS")
    @Convert(converter = EClerkApplyStatusConverter.class)
    private EClerkApplyStatus status;

    public Long getApplyId() {
        return applyId;
    }

    public void setApplyId(Long applyId) {
        this.applyId = applyId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getClerkId() {
        return clerkId;
    }

    public void setClerkId(String clerkId) {
        this.clerkId = clerkId;
    }

    public EClerkApplyStatus getStatus() {
        return status;
    }

    public void setStatus(EClerkApplyStatus status) {
        this.status = status;
    }
}
