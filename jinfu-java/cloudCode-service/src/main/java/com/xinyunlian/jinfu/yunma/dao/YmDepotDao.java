package com.xinyunlian.jinfu.yunma.dao;

import com.xinyunlian.jinfu.yunma.entity.YmDepotPo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by menglei on 2017-08-28.
 */
public interface YmDepotDao extends JpaRepository<YmDepotPo, Long>, JpaSpecificationExecutor<YmDepotPo> {

    @Query(nativeQuery = true, value = "SELECT * FROM ym_depot WHERE QRCODE_NO IN ?1")
    List<YmDepotPo> findByQrcodeNo(List<String> qrCodeNo);

    @Query(nativeQuery = true, value = "SELECT * FROM ym_depot WHERE STATUS='UNBIND_UNUSE' and RECEIVE_STATUS='UNRECEIVE' limit 1")
    YmDepotPo findUnBindAndUnUse();

    YmDepotPo findByQrCodeNo(String qrCodeNo);

    @Query(nativeQuery = true, value = "SELECT * FROM ym_depot WHERE (STATUS='BIND_UNUSE' or STATUS='BIND_USE')")
    List<YmDepotPo> findBind();

}
