package com.xinyunlian.jinfu.dict.dao;

import com.xinyunlian.jinfu.dict.entity.DictionaryItemPo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by DongFC on 2016-08-19.
 */
public interface DictionaryItemDao extends JpaRepository<DictionaryItemPo, Long>, JpaSpecificationExecutor<DictionaryItemPo> {

    @Query(nativeQuery = true, value = "select t1.* from dictionary_item t1 " +
            "inner join dictionary_type t2 on t1.TYPE_ID = t2.ID WHERE t2.CODE = ?1 order by SORT ASC")
    List<DictionaryItemPo> findByDictTypeCode(String dictTypeCode);

}
