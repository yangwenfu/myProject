package com.xinyunlian.jinfu.order.service;

import com.xinyunlian.jinfu.common.converter.ConverterService;
import com.xinyunlian.jinfu.common.exception.BizServiceException;
import com.xinyunlian.jinfu.common.util.BizUtil;
import com.xinyunlian.jinfu.order.dao.ThirdOrderInfDao;
import com.xinyunlian.jinfu.order.dao.ThirdOrderProdDao;
import com.xinyunlian.jinfu.order.dto.ThirdOrderInfDto;
import com.xinyunlian.jinfu.order.dto.ThirdOrderInfSearchDto;
import com.xinyunlian.jinfu.order.dto.ThirdOrderProdDto;
import com.xinyunlian.jinfu.order.entity.ThirdOrderInfPo;
import com.xinyunlian.jinfu.order.entity.ThirdOrderProdPo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by menglei on 2017年03月08日.
 */
@Service
public class ThirdOrderInfServiceImpl implements ThirdOrderInfService {

    @Autowired
    private ThirdOrderInfDao thirdOrderInfDao;
    @Autowired
    private ThirdOrderProdDao thirdOrderProdDao;

    @Override
    @Transactional(readOnly = true)
    public ThirdOrderInfSearchDto getThirdOrderInfPage(ThirdOrderInfSearchDto searchDto) {
        Specification<ThirdOrderInfPo> spec = (Root<ThirdOrderInfPo> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            Predicate predicate = cb.conjunction();
            List<Expression<Boolean>> expressions = predicate.getExpressions();
            if (null != searchDto) {
                if (!StringUtils.isEmpty(searchDto.getOrderNo())) {
                    expressions.add(cb.like(root.get("orderNo"), BizUtil.filterString(searchDto.getOrderNo())));
                }
                if (null != searchDto.getStoreId()) {
                    expressions.add(cb.equal(root.get("storeId"), searchDto.getStoreId()));
                }
            }
            return predicate;
        };

        Pageable pageable = new PageRequest(searchDto.getCurrentPage() - 1,
                searchDto.getPageSize(), Sort.Direction.DESC, "orderTime");
        Page<ThirdOrderInfPo> page = thirdOrderInfDao.findAll(spec, pageable);

        List<ThirdOrderInfDto> data = new ArrayList<>();
        for (ThirdOrderInfPo po : page.getContent()) {
            ThirdOrderInfDto dto = ConverterService.convert(po, ThirdOrderInfDto.class);
            data.add(dto);
        }
        searchDto.setList(data);
        searchDto.setTotalPages(page.getTotalPages());
        searchDto.setTotalRecord(page.getTotalElements());
        return searchDto;
    }

    @Transactional
    @Override
    public ThirdOrderInfDto getOne(Long orderId) {
        ThirdOrderInfPo po = thirdOrderInfDao.findOne(orderId);
        if (po == null) {
            return null;
        }
        ThirdOrderInfDto dto = ConverterService.convert(po, ThirdOrderInfDto.class);
        return dto;
    }

    @Transactional
    @Override
    public ThirdOrderInfDto getByOrderNo(String orderNo) {
        ThirdOrderInfPo po = thirdOrderInfDao.findByOrderNo(orderNo);
        if (po == null) {
            return null;
        }
        ThirdOrderInfDto dto = ConverterService.convert(po, ThirdOrderInfDto.class);
        return dto;
    }

    @Transactional
    @Override
    public void addOrderAndProd(ThirdOrderInfDto thirdOrderInfDto, List<ThirdOrderProdDto> orderProdList) throws BizServiceException {
        if (thirdOrderInfDto == null || orderProdList.isEmpty()) {
            return;
        }
        ThirdOrderInfPo thirdOrderInfPo = ConverterService.convert(thirdOrderInfDto, ThirdOrderInfPo.class);
        thirdOrderInfDao.save(thirdOrderInfPo);

        List<ThirdOrderProdPo> prodPoList = new ArrayList<>();
        for (ThirdOrderProdDto thirdOrderProdDto : orderProdList) {
            ThirdOrderProdPo thirdOrderProdPo = ConverterService.convert(thirdOrderProdDto, ThirdOrderProdPo.class);
            thirdOrderProdPo.setOrderId(thirdOrderInfPo.getOrderId());
            prodPoList.add(thirdOrderProdPo);
        }
        thirdOrderProdDao.save(prodPoList);
    }

}
