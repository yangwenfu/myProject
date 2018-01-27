package com.xinyunlian.jinfu.ad.service;

import com.xinyunlian.jinfu.ad.dto.AdFrontDto;
import com.xinyunlian.jinfu.ad.dto.AdInfDto;
import com.xinyunlian.jinfu.ad.dto.AdInfSearchDto;
import com.xinyunlian.jinfu.ad.dto.AdPicDto;
import com.xinyunlian.jinfu.common.exception.BizServiceException;

import java.util.List;

/**
 * Created by DongFC on 2016-08-22.
 */
public interface AdService {

    /**
     * 分页查询广告列表
     * @param adInfSearchDto
     * @return
     */
    AdInfSearchDto getAdInfPage(AdInfSearchDto adInfSearchDto) throws BizServiceException;

    /**
     * 新增广告
     * @param adInfDto
     */
    void saveAdInf(AdInfDto adInfDto) throws BizServiceException;

    /**
     * 批量删除广告
     * @param adIds
     */
    void deleteAdInfBatch(List<Long> adIds) throws BizServiceException;

    /**
     * 更新广告信息
     * @param adInfDto
     */
    void updateAdInf(AdInfDto adInfDto) throws BizServiceException;

    /**
     * 根据id查询广告
     * @param adId
     * @return
     */
    AdInfDto getAdInfById(Long adId) throws BizServiceException;

    /**
     * 根据广告位查询广告
     * @param posId
     * @return
     * @throws BizServiceException
     */
    List<AdInfDto> getAdInfByPosId(Long posId) throws BizServiceException;

    /**
     * 根据广告位和广告位尺寸获取广告详细信息
     * @param posId
     * @param width
     * @param height
     * @return
     * @throws BizServiceException
     */
    List<AdFrontDto> getAdFront(Long posId, Integer width, Integer height) throws BizServiceException;

    /**
     * 获取最大尺寸的广告图片
     * @param adId
     * @return
     * @throws BizServiceException
     */
    AdPicDto getMaxSizePic(Long adId) throws BizServiceException;

}
