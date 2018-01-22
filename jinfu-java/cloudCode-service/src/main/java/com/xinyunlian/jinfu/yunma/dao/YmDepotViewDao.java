package com.xinyunlian.jinfu.yunma.dao;

import com.xinyunlian.jinfu.yunma.entity.YmDepotViewPo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by menglei on 2017-08-28.
 */
public interface YmDepotViewDao extends JpaRepository<YmDepotViewPo, String>, JpaSpecificationExecutor<YmDepotViewPo> {

    @Query(nativeQuery = true, value = "SELECT * FROM ym_depot_view WHERE (STATUS='UNBIND_UNUSE' or STATUS='UNBIND_USE') AND BIND_TIME is not null")
    List<YmDepotViewPo> findErrorQrCodeNo();

    List<YmDepotViewPo> findByYmIdIn(List<String> ymIds);

}
