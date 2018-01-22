package com.xinyunlian.jinfu.ad.service;

import com.xinyunlian.jinfu.ad.dao.AdInfDao;
import com.xinyunlian.jinfu.ad.dao.AdPicDao;
import com.xinyunlian.jinfu.ad.dao.AdPosSizeDao;
import com.xinyunlian.jinfu.ad.dao.AdPositionDao;
import com.xinyunlian.jinfu.ad.dto.*;
import com.xinyunlian.jinfu.ad.entity.AdInfPo;
import com.xinyunlian.jinfu.ad.entity.AdPicPo;
import com.xinyunlian.jinfu.ad.entity.AdPosSizePo;
import com.xinyunlian.jinfu.ad.entity.AdPositionPo;
import com.xinyunlian.jinfu.ad.enums.EAdStatus;
import com.xinyunlian.jinfu.ad.enums.EAdValidStatus;
import com.xinyunlian.jinfu.common.converter.ConverterService;
import com.xinyunlian.jinfu.common.enums.EErrorCode;
import com.xinyunlian.jinfu.common.exception.BizServiceException;
import com.xinyunlian.jinfu.common.security.StaticResourceSecurity;
import com.xinyunlian.jinfu.common.util.BizUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import java.util.*;

/**
 * Created by DongFC on 2016-08-22.
 */
@Service
public class AdServiceImpl implements AdService{

    private static final Logger LOGGER = LoggerFactory.getLogger(AdServiceImpl.class);

    @Autowired
    private AdInfDao adInfDao;

    @Autowired
    private AdPicDao adPicDao;

    @Autowired
    private AdPositionDao adPositionDao;

    @Autowired
    private AdPosSizeDao adPosSizeDao;

    @Value("${file.addr}")
    private String fileAddr;

    @Override
    public AdInfSearchDto getAdInfPage(AdInfSearchDto adInfSearchDto) throws BizServiceException {

        List<Long> posIds = new ArrayList<>();
        if (adInfSearchDto.getPosPlatform() != null){
            List<AdPositionPo> posList = adPositionDao.findByPosPlatform(adInfSearchDto.getPosPlatform());
            if (!CollectionUtils.isEmpty(posList)){
                posList.forEach( pos -> {
                    posIds.add(pos.getPosId());
                });
            }
        }

        Specification<AdInfPo> spec = (root,  query, cb) -> {
                Predicate predicate = cb.conjunction();
                List<Expression<Boolean>> expressions = predicate.getExpressions();
                if (adInfSearchDto != null) {
                    if (adInfSearchDto.getAdPosId() != null) {
                        expressions.add(cb.equal(root.get("adPosId"), adInfSearchDto.getAdPosId()));
                    }
                    if (adInfSearchDto.getAdStatus() == null){
                        expressions.add(cb.notEqual(root.get("adStatus"), EAdStatus.DELETE));
                    }
                    if (!StringUtils.isEmpty(adInfSearchDto.getAdName())){
                        expressions.add(cb.like(root.get("adName"), BizUtil.filterString(adInfSearchDto.getAdName())));
                    }
                    if (!CollectionUtils.isEmpty(posIds)){
                        expressions.add(cb.in(root.get("adPosId")).value(posIds));
                    }else if (CollectionUtils.isEmpty(posIds) && adInfSearchDto.getPosPlatform() != null){
                        posIds.clear();
                        posIds.add(-1l);
                        expressions.add(cb.in(root.get("adPosId")).value(posIds));
                    }
                    if (adInfSearchDto.getAdValidStatus() != null){
                        if (adInfSearchDto.getAdValidStatus() == EAdValidStatus.FURTURE){
                            expressions.add(cb.greaterThan(root.get("startDate"), new Date()));
                        }else if (adInfSearchDto.getAdValidStatus() == EAdValidStatus.USING){
                            expressions.add(cb.greaterThanOrEqualTo(root.get("endDate"), new Date()));
                            expressions.add(cb.lessThanOrEqualTo(root.get("startDate"), new Date()));
                        }else if (adInfSearchDto.getAdValidStatus() == EAdValidStatus.OVER_DUE){
                            expressions.add(cb.lessThan(root.get("endDate"), new Date()));
                        }
                    }
                }
                return predicate;
        };

        Pageable pageable = new PageRequest(adInfSearchDto.getCurrentPage() - 1, adInfSearchDto.getPageSize());
        Page<AdInfPo> page = adInfDao.findAll(spec, pageable);

        List<AdInfDto> data = new ArrayList<>();
        page.getContent().forEach(po -> {
            AdInfDto adInfDto = ConverterService.convert(po, AdInfDto.class);
            AdPositionPo adPositionPo = adPositionDao.findOne(adInfDto.getAdPosId());
            AdPositionDto adPositionDto = ConverterService.convert(adPositionPo, AdPositionDto.class);
            adInfDto.setAdPositionDto(adPositionDto);
            data.add(adInfDto);
        });

        adInfSearchDto.setList(data);
        adInfSearchDto.setTotalPages(page.getTotalPages());
        adInfSearchDto.setTotalRecord(page.getTotalElements());

        return adInfSearchDto;
    }

    @Override
    @Transactional
    public void saveAdInf(AdInfDto adInfDto) throws BizServiceException {

        try {
            if (adInfDto != null){
                AdInfPo adInfPo = ConverterService.convert(adInfDto, AdInfPo.class);
                adInfPo.setAdStatus(EAdStatus.NORMAL);
                AdInfPo dbAdInfPo = adInfDao.save(adInfPo);

                List<AdPicDto> adPicDtoList = adInfDto.getAdPicListAdd();
                if (!CollectionUtils.isEmpty(adPicDtoList)){
                    adPicDtoList.forEach(adPicDto -> {
                        AdPicPo adPicPo = ConverterService.convert(adPicDto, AdPicPo.class);
                        adPicPo.setAdId(dbAdInfPo.getAdId());
                        adPicDao.save(adPicPo);
                    });
                }
            }
        } catch (Exception e) {
            LOGGER.error("新增广告失败", e);
            throw new BizServiceException(EErrorCode.SYSTEM_AD_SAVE_ERROR);
        }

    }

    @Transactional
    @Override
    public void deleteAdInfBatch(List<Long> adIds) throws BizServiceException {
        try {
            if (!CollectionUtils.isEmpty(adIds)){
                adInfDao.updateAdStatusByAdIds(EAdStatus.DELETE.getCode(), adIds);
            }
        } catch (Exception e) {
            LOGGER.error("广告删除失败", e);
            throw new BizServiceException(EErrorCode.SYSTEM_AD_DELETE_ERROR);
        }
    }

    @Override
    @Transactional
    public void updateAdInf(AdInfDto adInfDto) throws BizServiceException {

        if (adInfDto != null){
            AdInfPo adInfPo = adInfDao.findOne(adInfDto.getAdId());
            adInfPo.setAdName(adInfDto.getAdName());
            adInfPo.setAdUrl(adInfDto.getAdUrl());
            adInfPo.setEndDate(adInfDto.getEndDate());
            adInfPo.setStartDate(adInfDto.getStartDate());
            adInfPo.setDisplay(adInfDto.getDisplay());
            adInfPo.setAdStatus(adInfDto.getAdStatus());

            if (!CollectionUtils.isEmpty(adInfDto.getAdPicListUpdate())){
                adInfDto.getAdPicListUpdate().forEach(adPicDto -> {
                    AdPicPo dbAdPicPo = adPicDao.findByUnindex(adPicDto.getAdPosSizeId(), adPicDto.getAdId());
                    if (dbAdPicPo == null){
                        AdPicPo adPicPo = ConverterService.convert(adPicDto, AdPicPo.class);
                        adPicDao.save(adPicPo);
                    }else {
                        dbAdPicPo.setPicPath(adPicDto.getPicPath());
                        dbAdPicPo.setPicWidth(adPicDto.getPicWidth());
                        dbAdPicPo.setPicHeight(adPicDto.getPicHeight());
                    }
                });
            }
        }

    }

    @Override
    public AdInfDto getAdInfById(Long adId) throws BizServiceException {
        AdInfDto adInfDto = null;

        if (adId != null){
            AdInfPo adInfPo = adInfDao.findOne(adId);
            adInfDto = ConverterService.convert(adInfPo, AdInfDto.class);
            AdPositionPo adPositionPo = adPositionDao.findOne(adInfPo.getAdPosId());
            AdPositionDto adPositionDto = ConverterService.convert(adPositionPo, AdPositionDto.class);
            adInfDto.setAdPositionDto(adPositionDto);

            List<AdPosSizePo> adPosSizePoList = adPosSizeDao.findByAdPosId(adInfPo.getAdPosId());
            Map<Long, AdPosSizeDto> adPosSizeDtoMap = new HashMap<>();
            if (!CollectionUtils.isEmpty(adPosSizePoList)){
                adPosSizePoList.forEach(adPosSizePo -> {
                    AdPosSizeDto dto = ConverterService.convert(adPosSizePo, AdPosSizeDto.class);
                    adPosSizeDtoMap.put(dto.getId(), dto);
                });
            }

            List<AdPicPo> adPicPoList = adPicDao.findByAdId(adId);
            List<AdPosSizeDto> adPosSizeDtoList = new ArrayList<>();
            if (!CollectionUtils.isEmpty(adPicPoList)){
                adPicPoList.forEach(adPicPo -> {
                    AdPicDto adPicDto = ConverterService.convert(adPicPo, AdPicDto.class);
                    adPicDto.setPicPath(fileAddr + StaticResourceSecurity.getSecurityURI(adPicDto.getPicPath()));
                    if (adPosSizeDtoMap.containsKey(adPicDto.getAdPosSizeId())){
                        adPosSizeDtoMap.get(adPicDto.getAdPosSizeId()).setAdPicDto(adPicDto);
                    }
                });
            }
            adPosSizeDtoList.addAll(adPosSizeDtoMap.values());
            adInfDto.setAdPosSizeList(adPosSizeDtoList);

        }

        return adInfDto;
    }

    @Override
    public List<AdInfDto> getAdInfByPosId(Long posId) throws BizServiceException {

        List<AdInfPo> list = adInfDao.findByAdStatusAndAdPosId(EAdStatus.NORMAL, posId);

        List<AdInfDto> data = new ArrayList<>();
        list.forEach(po -> {
            AdInfDto adInfDto = ConverterService.convert(po, AdInfDto.class);
            data.add(adInfDto);
        });

        return data;
    }

    @Override
    public List<AdFrontDto> getAdFront(Long posId, Integer width, Integer height) throws BizServiceException {
        List<AdFrontDto> retList = new ArrayList<>();

        List<AdPosSizePo> adPosSizeList = adPosSizeDao.findByAdPosId(posId);
        if (!CollectionUtils.isEmpty(adPosSizeList)){

            Map<String, Object> availableMap = new HashMap<>();
            adPosSizeList.forEach( adPosSizePo -> {
                Date now = new Date();
                List<AdPicPo> adPicPoList = adPicDao.findByAdPosSizeId(adPosSizePo.getId(), now, now);
                if (!CollectionUtils.isEmpty(adPicPoList)){

                    Integer widthDiff = width - adPosSizePo.getAdPosWidth();
                    Integer heightDiff = height - adPosSizePo.getAdPosHeight();

                    boolean updateMap = false;
                    if (availableMap.isEmpty()){
                        updateMap = true;
                    }else {
                        Integer widthDiffMap = (Integer) availableMap.get("widthDiff");
                        Integer heightDiffMap = (Integer) availableMap.get("heightDiff");

                        if (Math.abs(widthDiff) < Math.abs(widthDiffMap)){
                            updateMap = true;
                        }else if (Math.abs(widthDiff) == Math.abs(widthDiffMap)){
                            if (Math.abs(heightDiff) < Math.abs(heightDiffMap)){
                                updateMap = true;
                            }else if (Math.abs(heightDiff) == Math.abs(heightDiffMap)){
                                if (widthDiff > widthDiffMap){
                                    updateMap = true;
                                }else if (widthDiff == widthDiffMap && heightDiff > heightDiffMap){
                                    updateMap = true;
                                }
                            }
                        }
                    }

                    if (updateMap){
                        availableMap.put("widthDiff", widthDiff);
                        availableMap.put("heightDiff", heightDiff);
                        availableMap.put("posSize", adPosSizePo);
                        availableMap.put("adPicList", adPicPoList);
                    }
                }
            });

            if (availableMap.isEmpty()){
                LOGGER.debug("广告位下没有配置图片");
                return null;
            }else {
                AdPosSizePo adPosSizePo = (AdPosSizePo)availableMap.get("posSize");
                List<AdPicPo> adPicPoList = (List<AdPicPo>)availableMap.get("adPicList");

                if (!CollectionUtils.isEmpty(adPicPoList)){
                    for (AdPicPo adPicPo : adPicPoList) {
                        AdInfPo adInfPo = adInfDao.findOne(adPicPo.getAdId());
                        AdFrontDto adFrontDto = ConverterService.convert(adPosSizePo, AdFrontDto.class);
                        adFrontDto.setPicPath(fileAddr + StaticResourceSecurity.getSecurityURI(adPicPo.getPicPath()));
                        adFrontDto.setAdUrl(adInfPo.getAdUrl());
                        adFrontDto.setAdName(adInfPo.getAdName());
                        adFrontDto.setPicHeight(adPicPo.getPicHeight());
                        adFrontDto.setPicWidth(adPicPo.getPicWidth());

                        retList.add(adFrontDto);
                    }
                }
            }

        }else {
            LOGGER.debug("广告位下没有配置广告");
            return null;
        }

        return retList;
    }

    @Override
    public AdPicDto getMaxSizePic(Long adId) throws BizServiceException {
        AdPicPo po = adPicDao.findMaxPicByAdId(adId, adId);
        AdPicDto dto = ConverterService.convert(po, AdPicDto.class);
        return dto;
    }

}
