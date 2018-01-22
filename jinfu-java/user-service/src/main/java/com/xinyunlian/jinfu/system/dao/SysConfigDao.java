package com.xinyunlian.jinfu.system.dao;

import com.xinyunlian.jinfu.system.entity.SysConfigPo;
import com.xinyunlian.jinfu.system.enums.ESysConfigType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @author menglei
 */
public interface SysConfigDao extends JpaRepository<SysConfigPo, String>, JpaSpecificationExecutor<SysConfigPo> {

    SysConfigPo findByType(ESysConfigType type);

}
