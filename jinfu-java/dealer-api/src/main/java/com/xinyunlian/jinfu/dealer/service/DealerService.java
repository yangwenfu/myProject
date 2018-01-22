package com.xinyunlian.jinfu.dealer.service;

import com.xinyunlian.jinfu.common.exception.BizServiceException;
import com.xinyunlian.jinfu.dealer.dto.DealerDto;
import com.xinyunlian.jinfu.dealer.dto.DealerProdDto;
import com.xinyunlian.jinfu.dealer.dto.DealerSearchDto;
import com.xinyunlian.jinfu.dealer.dto.DealerStoreDto;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by menglei on 2016年08月26日.
 */
public interface DealerService {

    DealerSearchDto getDealerPage(DealerSearchDto dealerSearchDto);

    DealerDto getDealerById(String dealerId);

    void deleteDealer(String dealerId) throws BizServiceException;

    void createDealer(DealerDto dealerDto, List<DealerProdDto> dealerProdList, List<DealerStoreDto> dealerStoreList);

    DealerDto saveDealer(DealerDto dealerDto, List<DealerProdDto> dealerProdList);

    void updateDealer(DealerDto dealerDto, List<DealerProdDto> dealerProdList, List<DealerStoreDto> dealerStoreList) throws BizServiceException;

    /**
     * 更新业务信息
     * @param dealerDto
     * @param dealerProdList
     * @throws BizServiceException
     */
    void updateDealerProd(DealerDto dealerDto, List<DealerProdDto> dealerProdList) throws BizServiceException;

    /**
     * 根据一批分销商编号查询
     * @param dealerIds
     * @return
     */
    List<DealerDto> findByDealerIds(List<String> dealerIds);

    /**
     * 更新审核状态
     * @param dealerDto
     */
    void updateAudit(DealerDto dealerDto);

    /**
     * 状态冻结解冻
     * @param dealerId
     */
    void updateFrozen(String dealerId);

    List<DealerDto> findByDealerName(String dealerName);

    List<DealerDto> findByDealerNameLike(String dealerName);

    /**
     * 根据分销员手机号获得分销商服务费率
     * @param dealerUserMobile
     * @return
     */
    BigDecimal getDealerFeeRt(String dealerUserMobile);

}
