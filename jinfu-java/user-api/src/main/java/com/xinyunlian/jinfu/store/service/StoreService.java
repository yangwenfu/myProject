package com.xinyunlian.jinfu.store.service;

import com.xinyunlian.jinfu.center.dto.CenterStoreDto;
import com.xinyunlian.jinfu.common.exception.BizServiceException;
import com.xinyunlian.jinfu.store.dto.StoreInfDto;
import com.xinyunlian.jinfu.store.dto.req.StoreSearchDto;
import com.xinyunlian.jinfu.store.enums.EDelReason;

import java.util.List;
import java.util.Set;

/**
 * 用户Service
 * @author KimLL
 * @version 
 */

public interface StoreService {

    StoreInfDto saveStore(StoreInfDto storeInfDto) throws BizServiceException;

    StoreInfDto save(StoreInfDto storeInfDto) throws BizServiceException;

    void deleteStore(Long storeId);

    void deleteStore(Long storeId, EDelReason reason);

    void updateStore(StoreInfDto storeInfDto) throws BizServiceException;

    StoreInfDto findByStoreId(Long storeId);

    List<StoreInfDto> findByStoreIds(List<Long> ids);

    List<StoreInfDto> findByStoreNameLike(String storeName);

    List<StoreInfDto> findByUserIds(Set<String> userIds);

    List<StoreInfDto> findByDistrictIds(List<String> ids);

    List<StoreInfDto> findStoreIdByDistrictIds(List<String> ids);

    List<StoreInfDto> findByUserId(String userId);

    void updateUserId(StoreInfDto storeInfDto);

    StoreSearchDto getStorePage(StoreSearchDto storeInfSearchDto);

    StoreInfDto saveAndUpdateStore(StoreInfDto storeInfDto) throws BizServiceException;

    StoreInfDto saveSupportAll(StoreInfDto storeInfDto) throws BizServiceException;

    StoreSearchDto getStorePointPage(StoreSearchDto storeInfSearchDto);

    List<StoreInfDto> findByNotPoint();

    StoreInfDto findByTobaccoCertificateNo(String tobaccoCertificateNo);

    List<StoreInfDto> findByTobaccoCertificateNoLike(String tobaccoCertificateNo);

    List<String> findUseridsByAddressIDs(List<String> list);
    List<String> findAllUsers();

    /**
     * 更新营业执照到期日（已存在到期日不更新）
     * @param storeInfDto
     */
    void updateBizEndDate(StoreInfDto storeInfDto);


    void updateStoreScore(Long storeId);

    void storeUpdateListener(String json);

    String saveFromUserCenter(CenterStoreDto centerStoreDto) throws BizServiceException;
}
