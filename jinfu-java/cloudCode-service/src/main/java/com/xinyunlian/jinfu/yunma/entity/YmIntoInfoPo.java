package com.xinyunlian.jinfu.yunma.entity;

import com.xinyunlian.jinfu.common.entity.BaseMaintainablePo;

import javax.persistence.*;

/**
 * Created by menglei on 2017-07-19.
 */
@Entity
@Table(name = "ym_into_info")
public class YmIntoInfoPo extends BaseMaintainablePo {
    private static final long serialVersionUID = 2962130238021106783L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "MEMBER_NO")
    private String memberNo;

    @Column(name = "OUT_MEMBER_NO")
    private String outMemberNo;

    @Column(name = "CHANNEL")
    private String channel;

    @Column(name = "STATUS")
    private String status;

    @Column(name = "REMARK")
    private String remark;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMemberNo() {
        return memberNo;
    }

    public void setMemberNo(String memberNo) {
        this.memberNo = memberNo;
    }

    public String getOutMemberNo() {
        return outMemberNo;
    }

    public void setOutMemberNo(String outMemberNo) {
        this.outMemberNo = outMemberNo;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
