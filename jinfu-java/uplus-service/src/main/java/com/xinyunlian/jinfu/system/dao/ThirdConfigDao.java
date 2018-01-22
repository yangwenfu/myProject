package com.xinyunlian.jinfu.system.dao;

import com.xinyunlian.jinfu.system.entity.ThirdConfigPo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * 商品码DAO接口
 *
 * @author menglei
 */
public interface ThirdConfigDao extends JpaRepository<ThirdConfigPo, Long>, JpaSpecificationExecutor<ThirdConfigPo> {

    ThirdConfigPo findByAppId(String appId);

}
