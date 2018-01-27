package com.xinyunlian.jinfu.dealer.service;

import com.xinyunlian.jinfu.common.converter.ConverterService;
import com.xinyunlian.jinfu.common.util.DateHelper;
import com.xinyunlian.jinfu.dealer.dao.DealerUserStoreDao;
import com.xinyunlian.jinfu.dealer.dto.DealerDto;
import com.xinyunlian.jinfu.dealer.dto.DealerUserStoreDto;
import com.xinyunlian.jinfu.dealer.entity.DealerUserStorePo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by menglei on 2016年08月30日.
 */
@Service
public class DealerUserStoreServiceImpl implements DealerUserStoreService {

    @Autowired
    private DealerUserStoreDao dealerUserStoreDao;

    @Override
    public long getCount(DealerUserStoreDto dealerUserStoreDto) {
        Specification<DealerUserStorePo> spec = (Root<DealerUserStorePo> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            Predicate predicate = cb.conjunction();
            List<Expression<Boolean>> expressions = predicate.getExpressions();
            if (null != dealerUserStoreDto) {
                if (StringUtils.isNotEmpty(dealerUserStoreDto.getUserId())) {
                    expressions.add(cb.equal(root.get("userId"), dealerUserStoreDto.getUserId()));
                }
                if (StringUtils.isNotEmpty(dealerUserStoreDto.getStoreId())) {
                    expressions.add(cb.equal(root.get("storeId"), dealerUserStoreDto.getStoreId()));
                }
                if (StringUtils.isNotEmpty(dealerUserStoreDto.getBeginTime())) {
                    expressions.add(cb.greaterThanOrEqualTo(root.get("createTs"), DateHelper.getStartDate(dealerUserStoreDto.getBeginTime())));
                }
                if (StringUtils.isNotEmpty(dealerUserStoreDto.getEndTime())) {
                    expressions.add(cb.lessThanOrEqualTo(root.get("createTs"), DateHelper.getEndDate(dealerUserStoreDto.getEndTime())));
                }
            }
            return predicate;
        };
        return dealerUserStoreDao.count(spec);
    }

    @Transactional
    @Override
    public List<DealerUserStoreDto> getListByStoreId(String storeId) {
        List<DealerUserStorePo> list = dealerUserStoreDao.findByStoreId(storeId);
        List<DealerUserStoreDto> result = new ArrayList<>();
        if (!CollectionUtils.isEmpty(list)) {
            for (DealerUserStorePo po : list) {
                DealerUserStoreDto dto = ConverterService.convert(po, DealerUserStoreDto.class);
                if (po.getDealerPo() != null) {
                    DealerDto dealerDto = ConverterService.convert(po.getDealerPo(), DealerDto.class);
                    dto.setDealerDto(dealerDto);
                }
                result.add(dto);
            }
        }
        return result;
    }

    @Override
    public List<DealerUserStoreDto> getStoreListByUserId(DealerUserStoreDto dealerUserStoreDto) {
        Specification<DealerUserStorePo> spec = (Root<DealerUserStorePo> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            Predicate predicate = cb.conjunction();
            List<Expression<Boolean>> expressions = predicate.getExpressions();
            if (null != dealerUserStoreDto) {
                if (StringUtils.isNotEmpty(dealerUserStoreDto.getDealerId())) {
                    expressions.add(cb.equal(root.get("dealerId"), dealerUserStoreDto.getDealerId()));
                }
                if (StringUtils.isNotEmpty(dealerUserStoreDto.getUserId())) {
                    expressions.add(cb.equal(root.get("userId"), dealerUserStoreDto.getUserId()));
                }
                if (StringUtils.isNotEmpty(dealerUserStoreDto.getBeginTime())) {
                    expressions.add(cb.greaterThanOrEqualTo(root.get("createTs"), DateHelper.getStartDate(dealerUserStoreDto.getBeginTime())));
                }
                if (StringUtils.isNotEmpty(dealerUserStoreDto.getEndTime())) {
                    expressions.add(cb.lessThanOrEqualTo(root.get("createTs"), DateHelper.getEndDate(dealerUserStoreDto.getEndTime())));
                }
            }
            return predicate;
        };
        List<DealerUserStorePo> list = dealerUserStoreDao.findAll(spec);
        List<DealerUserStoreDto> result = new ArrayList<>();
        for (DealerUserStorePo po : list) {
            DealerUserStoreDto dto = ConverterService.convert(po, DealerUserStoreDto.class);
            result.add(dto);
        }
        return result;
    }

    @Transactional
    @Override
    public void createDealer(DealerUserStoreDto dealerUserStoreDto) {
        if (dealerUserStoreDto != null) {
            DealerUserStorePo dealerUserStorePo = ConverterService.convert(dealerUserStoreDto, DealerUserStorePo.class);
            dealerUserStoreDao.save(dealerUserStorePo);
        }
    }

}
