package com.xinyunlian.jinfu.yunma.dao;

import com.xinyunlian.jinfu.yunma.entity.YMMemberPo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * 云码商铺DAO接口
 * @author jll
 * @version 
 */
public interface YMMemberDao extends JpaRepository<YMMemberPo, Long>, JpaSpecificationExecutor<YMMemberPo> {

    @Query(nativeQuery = true, value = "select * from ym_member where STATUS in ('0','1') and STORE_ID=?1")
    YMMemberPo findByStoreId(Long storeId);

    @Query(nativeQuery = true, value = "select * from ym_member where STATUS in ('0','1')  and USER_ID=?1 order by ID desc")
    List<YMMemberPo> findByUserId(String userId);

    @Query(nativeQuery = true, value = "select * from ym_member where STATUS in ('0','1') and QRCODE_NO=?1")
    YMMemberPo findByQrCodeNo(String qrCodeNo);

    @Query(nativeQuery = true, value = "select * from ym_member where STATUS in ('0','1')  and STORE_ID in ?1")
    List<YMMemberPo> findByStoreIds(List<Long> ids);

    @Query(nativeQuery = true, value = "select * from ym_member where STATUS in ('0','1')  and MEMBER_NO=?1")
    YMMemberPo findByMemberNo(String memberNo);

    @Modifying
    @Query(nativeQuery = true, value = "update ym_member set STATUS=3 where STORE_ID=?1 and STATUS=2")
    void deleteByStoreId(Long storeId);

    @Modifying
    @Query(nativeQuery = true, value = "update ym_member set STATUS=3 where USER_ID=?1 and STATUS=2")
    void deleteByUserId(String userId);

    @Query(nativeQuery = true, value = "select * from ym_member where STATUS in ('0','1')  and USER_ID=?1 and AUDIT_STATUS=?2 order by ID desc")
    List<YMMemberPo> findByUserIdAndMemberAuditStatus(String userId, String memberAuditStatus);

    @Query(nativeQuery = true, value = "select * from ym_member where STATUS in ('0','1') and QRCODE_NO=?1 and USER_ID=?2")
    YMMemberPo findByQrCodeNoAndUserId(String qrCodeNo, String userId);

    @Query(nativeQuery = true, value = "select * from ym_member where STATUS in ('0','1')  and ID=?1")
    YMMemberPo findById(Long id);

    @Query(nativeQuery = true, value = "select * from ym_member where STATUS in ('0','1')  and BANK_CARD_ID=?1")
    List<YMMemberPo> findByBankCardId(Long bankCardId);

}
