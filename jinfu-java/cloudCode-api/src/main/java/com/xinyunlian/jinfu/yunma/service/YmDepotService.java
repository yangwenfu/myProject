package com.xinyunlian.jinfu.yunma.service;

import com.xinyunlian.jinfu.common.exception.BizServiceException;
import com.xinyunlian.jinfu.yunma.dto.YmDepotDto;
import com.xinyunlian.jinfu.yunma.dto.YmDepotSearchDto;
import com.xinyunlian.jinfu.yunma.dto.YmDepotViewDto;

import java.util.List;

/**
 * Created by menglei on 2017-08-28.
 */
public interface YmDepotService {

    /**
     * 云码库列表
     * @param searchDto
     * @return
     */
    YmDepotSearchDto getDepotPage(YmDepotSearchDto searchDto);

    /**
     * 批量添加
     * @param ymDepotDtos
     * @throws BizServiceException
     */
    void saveBatch(List<YmDepotDto> ymDepotDtos) throws BizServiceException;

    /**
     * 批量更新云码已使用
     * @param qrCodeNo
     * @throws BizServiceException
     */
    void updateBatchUsed(List<String> qrCodeNo) throws BizServiceException;

    /**
     * 根据云码查云码详情
     * @param qrCodeNo
     * @return
     */
    List<YmDepotDto> findByQrCodeNo(List<String> qrCodeNo);

    /**
     * 查询不符合状态的云码
     * @return
     */
    List<YmDepotViewDto> findErrorQrCodeNo();

    /**
     * 获取未绑定未使用云码并更新云码已绑定
     * @return
     */
    YmDepotDto findNewBind() throws BizServiceException;

    /**
     * 申请物料
     * @param ymDepotDto
     */
    void updateMailInfo(YmDepotDto ymDepotDto);

    /**
     * 根据云码id查询码库
     * @param ymIds
     * @return
     */
    List<YmDepotViewDto> findByYmIds(List<String> ymIds);

    /**
     * 根据云码编号查云码
     * @param qrCodeNo
     * @return
     */
    YmDepotDto findByQrCodeNo(String qrCodeNo);

    /**
     * 云码库状态更新
     * @param ymDepotDto
     * @throws BizServiceException
     */
    void updateStatusAndReceiveStatus(YmDepotDto ymDepotDto) throws BizServiceException;

    /**
     * 已绑定云码列表
     * @return
     */
    List<YmDepotDto> findBind();

}
