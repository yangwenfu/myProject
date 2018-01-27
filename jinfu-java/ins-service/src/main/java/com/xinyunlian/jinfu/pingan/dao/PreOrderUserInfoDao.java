package com.xinyunlian.jinfu.pingan.dao;

import com.xinyunlian.jinfu.pingan.entity.PreOrderUserInfoPo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * Created by dongfangchao on 2017/6/13/0013.
 */
public interface PreOrderUserInfoDao extends JpaSpecificationExecutor<PreOrderUserInfoPo>, JpaRepository<PreOrderUserInfoPo, Long> {

    PreOrderUserInfoPo findByPreInsuranceOrderNo(String preInsOrderNo);

}
