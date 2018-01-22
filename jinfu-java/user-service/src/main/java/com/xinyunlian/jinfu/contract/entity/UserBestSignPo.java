package com.xinyunlian.jinfu.contract.entity;
import com.xinyunlian.jinfu.common.entity.BaseMaintainablePo;
import javax.persistence.*;
/**
 * Created by dongfangchao on 2017/2/8/0008.
 */
@Entity
@Table(name = "user_best_sign")
public class UserBestSignPo  extends BaseMaintainablePo {
    private static final long serialVersionUID = -1437359567370165022L;
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "USER_ID")
    private String userId;
    @Column(name = "BEST_SIGN_UID")
    private String bestSignUid;
    @Column(name = "BEST_SIGN_CA")
    private Boolean bestSignCa;
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getUserId() {
        return userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }
    public String getBestSignUid() {
        return bestSignUid;
    }
    public void setBestSignUid(String bestSignUid) {
        this.bestSignUid = bestSignUid;
    }
    public Boolean getBestSignCa() {
        return bestSignCa;
    }
    public void setBestSignCa(Boolean bestSignCa) {
        this.bestSignCa = bestSignCa;
    }
}