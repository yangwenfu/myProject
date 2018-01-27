package com.xinyunlian.jinfu.olduser.dao;

import com.xinyunlian.jinfu.olduser.entity.OldUserPo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * Created by DongFC on 2016-11-07.
 */
public interface OldUserDao extends JpaRepository<OldUserPo, String>, JpaSpecificationExecutor<OldUserPo> {
    OldUserPo findByTobaccoCertificateNo(String tobaccoCertificateNo);
}
