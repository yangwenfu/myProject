package com.xinyunlian.jinfu.dealer.service;

import com.xinyunlian.jinfu.common.converter.ConverterService;
import com.xinyunlian.jinfu.dealer.dao.DealerDao;
import com.xinyunlian.jinfu.dealer.dao.DealerProdDao;
import com.xinyunlian.jinfu.dealer.dto.DealerDto;
import com.xinyunlian.jinfu.dealer.dto.DealerProdDto;
import com.xinyunlian.jinfu.dealer.dto.DealerProdSearchDto;
import com.xinyunlian.jinfu.dealer.entity.DealerPo;
import com.xinyunlian.jinfu.dealer.entity.DealerProdPo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.persistence.criteria.*;
import java.util.*;

/**
 * Created by menglei on 2016年08月31日.
 */
@Service
public class DealerProdServiceImpl implements DealerProdService {

    @Autowired
    private DealerProdDao dealerProdDao;
    @Autowired
    private DealerDao dealerDao;

    /**
     * 查询授权业务列表
     *
     * @param dealerProdSearchDto
     * @return
     */
    @Override
    public List<DealerProdDto> getDealerProdList(DealerProdSearchDto dealerProdSearchDto) {
        Specification<DealerProdPo> spec = (Root<DealerProdPo> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            Predicate predicate = cb.conjunction();
            List<Expression<Boolean>> expressions = predicate.getExpressions();
            if (null != dealerProdSearchDto) {
                if (dealerProdSearchDto.getDealerId() != null) {
                    expressions.add(cb.equal(root.<DealerPo>get("dealerPo").get("dealerId"), dealerProdSearchDto.getDealerId()));
                }
                if (StringUtils.isNotEmpty(dealerProdSearchDto.getDealerName())) {
                    expressions.add(cb.equal(root.<DealerPo>get("dealerPo").get("dealerName"), dealerProdSearchDto.getDealerName()));
                }
                if (dealerProdSearchDto.getProdId() != null) {
                    expressions.add(cb.equal(root.get("prodId"), dealerProdSearchDto.getProdId()));
                }
                if (dealerProdSearchDto.getProvinceId() != null) {
                    expressions.add(cb.equal(root.get("provinceId"), dealerProdSearchDto.getProvinceId()));
                }
                if (dealerProdSearchDto.getCityId() != null) {
                    expressions.add(cb.equal(root.get("cityId"), dealerProdSearchDto.getCityId()));
                }
                if (dealerProdSearchDto.getAreaId() != null) {
                    expressions.add(cb.equal(root.get("areaId"), dealerProdSearchDto.getAreaId()));
                }
                if (dealerProdSearchDto.getStreetId() != null) {
                    expressions.add(cb.equal(root.get("streetId"), dealerProdSearchDto.getStreetId()));
                }
                if (dealerProdSearchDto.getExpire() != null && dealerProdSearchDto.getExpire()) {
                    Date date = new Date();
                    expressions.add(cb.lessThanOrEqualTo(root.<DealerPo>get("dealerPo").get("beginTime"), date));
                    expressions.add(cb.greaterThanOrEqualTo(root.<DealerPo>get("dealerPo").get("endTime"), date));
                }
                if (dealerProdSearchDto.getDistrictIdsList() != null && dealerProdSearchDto.getDistrictIdsList().size() > 0) {
                    expressions.add(cb.in(root.get("districtId")).value(dealerProdSearchDto.getDistrictIdsList()));
                }
            }
            return predicate;
        };
        List<DealerProdPo> list = dealerProdDao.findAll(spec);
        List<DealerProdDto> result = new ArrayList<>();
        if (!CollectionUtils.isEmpty(list)) {
            Map<String, String> dealerIdsMap = new HashMap<>();
            for (DealerProdPo po : list) {
                dealerIdsMap.put(po.getDealerId(), po.getDealerId());
            }
            List<String> dealerIds = new ArrayList<>(dealerIdsMap.keySet());
            Map<String, DealerDto> dealerMap = new HashMap<>();
            if (dealerIds.size() > 0) {
                List<DealerPo> dealers = dealerDao.findByDealerIdIn(dealerIds);
                for (DealerPo dealerPo : dealers) {
                    DealerDto dealerDto = ConverterService.convert(dealerPo, DealerDto.class);
                    dealerMap.put(dealerPo.getDealerId() ,dealerDto);
                }
            }
            for (DealerProdPo po : list) {
                DealerProdDto dto = ConverterService.convert(po, DealerProdDto.class);
                if (dealerMap.get(po.getDealerId()) != null) {
                    dto.setDealerDto(dealerMap.get(po.getDealerId()));
                }
                result.add(dto);
            }
        }
        return result;
    }

    @Override
    public List<DealerProdDto> getByDealerIdAndArea(DealerProdDto dealerProdDto) {
        List<DealerProdPo> list = dealerProdDao.findByDealerIdAndArea(dealerProdDto.getDealerId(), dealerProdDto.getProvinceId(),
                dealerProdDto.getCityId(), dealerProdDto.getAreaId(), dealerProdDto.getStreetId());

        List<DealerProdDto> result = new ArrayList<>();
        for (DealerProdPo po : list) {
            DealerProdDto dto = ConverterService.convert(po, DealerProdDto.class);
            result.add(dto);
        }
        return result;
    }

    @Override
    public List<DealerProdDto> getByDealerIdAndAreaAndProdId(DealerProdDto dealerProdDto) {
        List<DealerProdPo> list = dealerProdDao.findByDealerIdAndAreaAndProdId(dealerProdDto.getDealerId(), dealerProdDto.getProvinceId(),
                dealerProdDto.getCityId(), dealerProdDto.getAreaId(), dealerProdDto.getStreetId(), dealerProdDto.getProdId());

        List<DealerProdDto> result = new ArrayList<>();
        for (DealerProdPo po : list) {
            DealerProdDto dto = ConverterService.convert(po, DealerProdDto.class);
            result.add(dto);
        }
        return result;
    }

}
