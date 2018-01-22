package com.xinyunlian.jinfu.ad.service;

import com.xinyunlian.jinfu.ad.dao.AdInfDao;
import com.xinyunlian.jinfu.ad.dao.AdPicDao;
import com.xinyunlian.jinfu.ad.dao.AdPosSizeDao;
import com.xinyunlian.jinfu.ad.dao.AdPositionDao;
import com.xinyunlian.jinfu.ad.dto.AdPosSearchDto;
import com.xinyunlian.jinfu.ad.dto.AdPosSizeDto;
import com.xinyunlian.jinfu.ad.dto.AdPositionDto;
import com.xinyunlian.jinfu.ad.entity.AdInfPo;
import com.xinyunlian.jinfu.ad.entity.AdPosSizePo;
import com.xinyunlian.jinfu.ad.entity.AdPositionPo;
import com.xinyunlian.jinfu.ad.enums.EAdStatus;
import com.xinyunlian.jinfu.ad.enums.EPosStatus;
import com.xinyunlian.jinfu.common.converter.ConverterService;
import com.xinyunlian.jinfu.common.enums.EErrorCode;
import com.xinyunlian.jinfu.common.exception.BizServiceException;
import com.xinyunlian.jinfu.common.util.BizUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by dongfangchao on 2016/12/21/0021.
 */
@Service
public class AdPosServiceImpl implements AdPosService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AdPosServiceImpl.class);

    @Autowired
    private AdPositionDao adPositionDao;

    @Autowired
    private AdInfDao adInfDao;

    @Autowired
    private AdPosSizeDao adPosSizeDao;

    @Autowired
    private AdPicDao adPicDao;

    @Override
    public AdPosSearchDto getAdPositionPage(AdPosSearchDto searchDto) throws BizServiceException {

        Specification<AdPositionPo> spec = (root, query, cb) -> {
            Predicate predicate = cb.conjunction();
            List<Expression<Boolean>> expressions = predicate.getExpressions();
            if (searchDto != null) {
                if (!StringUtils.isEmpty(searchDto.getPosName())) {
                    expressions.add(cb.like(root.get("posName"), BizUtil.filterString(searchDto.getPosName())));
                }
                if (searchDto.getPosStatus() == null){
                    expressions.add(cb.notEqual(root.get("posStatus"), EPosStatus.DELETE));
                }
                if (searchDto.getPosPlatform() != null){
                    expressions.add(cb.equal(root.get("posPlatform"), searchDto.getPosPlatform()));
                }
            }
            return predicate;
        };

        Pageable pageable = new PageRequest(searchDto.getCurrentPage() - 1, searchDto.getPageSize());
        Page<AdPositionPo> page = adPositionDao.findAll(spec, pageable);

        List<AdPositionDto> data = new ArrayList<>();
        page.getContent().forEach(po -> {
            AdPositionDto dto = ConverterService.convert(po, AdPositionDto.class);
            data.add(dto);
        });

        data.forEach(adPositionDto -> {
            List<AdPosSizePo> adPosSizePoList = adPosSizeDao.findByAdPosId(adPositionDto.getPosId());
            if (!CollectionUtils.isEmpty(adPosSizePoList)){
                List<AdPosSizeDto> adPosSizeDtoList = new ArrayList<>();
                adPosSizePoList.forEach(adPosSizePo -> {
                    AdPosSizeDto adPosSizeDto = ConverterService.convert(adPosSizePo, AdPosSizeDto.class);
                    adPosSizeDtoList.add(adPosSizeDto);
                });
                adPositionDto.setAdPosSizeList(adPosSizeDtoList);
            }
        });

        searchDto.setList(data);
        searchDto.setTotalPages(page.getTotalPages());
        searchDto.setTotalRecord(page.getTotalElements());

        return searchDto;
    }

    @Override
    @Transactional
    public void saveAdPos(AdPositionDto dto) throws BizServiceException{
        try {
            AdPositionPo po = ConverterService.convert(dto, AdPositionPo.class);
            po.setPosStatus(EPosStatus.NORMAL);
            AdPositionPo dbPos = adPositionDao.save(po);

            if (!CollectionUtils.isEmpty(dto.getAdPosSizeListAdd())){

                Set<AdPosSizePo> adPosSizePoSet = new HashSet<>();
                dto.getAdPosSizeListAdd().forEach( adPosSizeDto -> {
                    AdPosSizePo adPosSizePo = ConverterService.convert(adPosSizeDto, AdPosSizePo.class);
                    adPosSizePo.setAdPosId(dbPos.getPosId());
                    adPosSizePoSet.add(adPosSizePo);
                });

                adPosSizeDao.save(adPosSizePoSet);
            }

        } catch (Exception e) {
            LOGGER.error("添加广告位异常", e);
            throw new BizServiceException(EErrorCode.SYSTEM_AD_POS_SAVE_ERROR);
        }
    }

    @Override
    @Transactional
    public void deleteAdPos(Long posId) throws BizServiceException {
        try {
            AdPositionPo po = adPositionDao.findOne(posId);
            if (po != null){
                List<AdInfPo> adList = adInfDao.findByAdStatusAndAdPosId(EAdStatus.NORMAL, posId);
                if (!CollectionUtils.isEmpty(adList)){
                    throw new BizServiceException(EErrorCode.SYSTEM_AD_POS_HAS_ADS_ERROR);
                }

                po.setPosStatus(EPosStatus.DELETE);
                adInfDao.updateAdStatusByPosId(EAdStatus.DELETE.getCode(), posId);
            }
        } catch (Exception e) {
            LOGGER.error("删除广告位异常", e);
            throw e;
        }
    }

    @Override
    @Transactional
    public void updateAdPos(AdPositionDto dto) throws BizServiceException {

        try {
            AdPositionPo po = adPositionDao.findOne(dto.getPosId());
            if (po != null){
                AdPositionPo adPositionPo = new AdPositionPo();
                adPositionPo.setPosId(dto.getPosId());
                List<AdInfPo> adList = adInfDao.findByAdStatusAndAdPosId(EAdStatus.NORMAL, dto.getPosId());
                if (!CollectionUtils.isEmpty(adList)){
                    if (adList.size() > dto.getAdNum()){
                        throw new BizServiceException(EErrorCode.SYSTEM_AD_POS_AD_NUM_ERROR);
                    }
                }

                po.setPosName(dto.getPosName());
                po.setAdType(dto.getAdType());
                po.setPosPlatform(dto.getPosPlatform());
                po.setPosDesc(dto.getPosDesc());
                po.setAdNum(dto.getAdNum());
                po.setBgPicPath(dto.getBgPicPath());
                po.setxAxis(dto.getxAxis());
                po.setyAxis(dto.getyAxis());
                po.setResolutionHeight(dto.getResolutionHeight());
                po.setResolutionWidth(dto.getResolutionWidth());

                if (!CollectionUtils.isEmpty(dto.getAdPosSizeListAdd())){
                    dto.getAdPosSizeListAdd().forEach( adPosSizeDto -> {
                        AdPosSizePo adPosSizePo = ConverterService.convert(adPosSizeDto, AdPosSizePo.class);
                        adPosSizeDao.save(adPosSizePo);
                    });
                }

                if (!CollectionUtils.isEmpty(dto.getAdPosSizeListDel())){
                    List<Long> ids = new ArrayList<>();
                    dto.getAdPosSizeListDel().forEach( adPosSizeDto -> {
                        ids.add(adPosSizeDto.getId());
                    });
                    adPosSizeDao.deleteBatchByIds(ids);
                    adPicDao.deleteBatchByAdPosSizeIds(ids);
                }

                if (!CollectionUtils.isEmpty(dto.getAdPosSizeListUpdate())){
                    dto.getAdPosSizeListUpdate().forEach( adPosSizeDto -> {
                        AdPosSizePo adPosSizePo = adPosSizeDao.findOne(adPosSizeDto.getId());
                        adPosSizePo.setAdPosHeight(adPosSizeDto.getAdPosHeight());
                        adPosSizePo.setAdPosWidth(adPosSizeDto.getAdPosWidth());
                    });
                }

            }else{
                throw new BizServiceException(EErrorCode.SYSTEM_AD_POS_NOT_EXISTS_ERROR);
            }
        } catch (Exception e) {
            LOGGER.error("更新广告位异常", e);
            throw e;
        }

    }

    @Override
    public AdPositionDto getAdPosById(Long posId) throws BizServiceException {
        AdPositionPo adPositionPo = adPositionDao.findOne(posId);
        AdPositionDto retDto = ConverterService.convert(adPositionPo, AdPositionDto.class);
        return retDto;
    }

    @Override
    public List<AdPositionDto> getAdPosList() throws BizServiceException {
        List<AdPositionDto> retList = new ArrayList<>();

        List<AdPositionPo> list = adPositionDao.findAdPosList();
        if (!CollectionUtils.isEmpty(list)){
            list.forEach(po -> {
                AdPositionDto dto = ConverterService.convert(po, AdPositionDto.class);
                retList.add(dto);
            });
        }

        return retList;
    }

    @Override
    public List<AdPosSizeDto> getAdPosSizeList(Long adPosId) throws BizServiceException {
        List<AdPosSizeDto> retList = new ArrayList<>();

        List<AdPosSizePo> list = adPosSizeDao.findByAdPosId(adPosId);
        if (!CollectionUtils.isEmpty(list)){
            list.forEach(po -> {
                AdPosSizeDto dto = ConverterService.convert(po, AdPosSizeDto.class);
                retList.add(dto);
            });
        }
        return retList;
    }

}
