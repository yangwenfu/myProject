package com.xinyunlian.jinfu.dict.dao;

import com.xinyunlian.jinfu.dict.entity.DictionaryTypePo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * Created by DongFC on 2016-08-19.
 */
public interface DictionaryTypeDao extends JpaRepository<DictionaryTypePo, Long>, JpaSpecificationExecutor<DictionaryTypePo> {

}
