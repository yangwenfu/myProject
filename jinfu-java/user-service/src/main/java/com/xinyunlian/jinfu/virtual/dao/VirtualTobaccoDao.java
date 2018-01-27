package com.xinyunlian.jinfu.virtual.dao;

import com.xinyunlian.jinfu.virtual.entity.VirtualTobaccoPo;
import com.xinyunlian.jinfu.virtual.enums.ETakeStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * 虚拟烟草证DAO接口
 * @author jll
 * @version 
 */
public interface VirtualTobaccoDao extends JpaRepository<VirtualTobaccoPo, Long>, JpaSpecificationExecutor<VirtualTobaccoPo> {
	@Query(nativeQuery = true, value = "SELECT * FROM VIRTUAL_TOBACCO WHERE " +
            "STATUS=?1 AND AREA_CODE=?2  ORDER BY RAND() LIMIT ?3 ")
    List<VirtualTobaccoPo> findByRand(String status, String areaCode, int num);

    @Query(nativeQuery = true, value = "SELECT * FROM VIRTUAL_TOBACCO WHERE " +
            "STATUS='INITIAL' AND AREA_CODE=?1  ORDER BY RAND() LIMIT ?2 ")
    List<VirtualTobaccoPo> findByRand(String areaCode,int num);

    @Query("select max(serial) from VirtualTobaccoPo where areaCode=?1 and pinCode=?2")
    Long findMaxSerial(String areaCode,String pinCode);

    Long countByStatus(ETakeStatus status);

    @Query(nativeQuery = true, value = "SELECT count(*) FROM VIRTUAL_TOBACCO A,STORE_INF B " +
            "WHERE A.TOBACCO_CERTIFICATE_NO=B.TOBACCO_CERTIFICATE_NO")
    Long countUsed();


}
