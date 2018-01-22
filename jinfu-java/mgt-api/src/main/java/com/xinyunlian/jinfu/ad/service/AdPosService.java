package com.xinyunlian.jinfu.ad.service;

import com.xinyunlian.jinfu.ad.dto.AdPosSearchDto;
import com.xinyunlian.jinfu.ad.dto.AdPosSizeDto;
import com.xinyunlian.jinfu.ad.dto.AdPositionDto;
import com.xinyunlian.jinfu.common.exception.BizServiceException;

import java.util.List;

/**
 * Created by dongfangchao on 2016/12/21/0021.
 */
public interface AdPosService {

    /**
     * 分页获取广告位
     * @param searchDto
     * @return
     */
    AdPosSearchDto getAdPositionPage(AdPosSearchDto searchDto) throws BizServiceException;

    /**
     * 新增广告位
     * @param dto
     * @return
     */
    void saveAdPos(AdPositionDto dto) throws BizServiceException;

    /**
     * 删除广告位
     * @param posId
     * @return
     */
    void deleteAdPos(Long posId) throws BizServiceException;

    /**
     * 更新广告位
     * @param dto
     * @return
     */
    void updateAdPos(AdPositionDto dto) throws BizServiceException;

    /**
     * 根据广告位id查询广告位详情
     * @param posId
     * @return
     * @throws BizServiceException
     */
    AdPositionDto getAdPosById(Long posId) throws BizServiceException;

    /**
     * 获取所有正常的广告位
     * @return
     * @throws BizServiceException
     */
    List<AdPositionDto> getAdPosList() throws BizServiceException;

    /**
     * 获取指定广告位的所有的广告位尺寸
     * @param adPosId
     * @return
     * @throws BizServiceException
     */
    List<AdPosSizeDto> getAdPosSizeList(Long adPosId) throws BizServiceException;
}
