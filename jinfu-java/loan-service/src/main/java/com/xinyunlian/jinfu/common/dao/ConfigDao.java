package com.xinyunlian.jinfu.common.dao;


import com.xinyunlian.jinfu.common.entity.ConfigPo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ConfigDao extends JpaRepository<ConfigPo, String>, JpaSpecificationExecutor<ConfigPo> {

}
