package com.xinyunlian.jinfu.dealer.service;

import com.xinyunlian.jinfu.common.converter.ConverterService;
import com.xinyunlian.jinfu.common.exception.BizServiceException;
import com.xinyunlian.jinfu.common.util.DateHelper;
import com.xinyunlian.jinfu.dealer.dao.DealerUserOrderDao;
import com.xinyunlian.jinfu.dealer.dto.DealerDto;
import com.xinyunlian.jinfu.dealer.dto.DealerUserOrderDto;
import com.xinyunlian.jinfu.dealer.dto.DealerUserOrderSearchDto;
import com.xinyunlian.jinfu.dealer.entity.DealerPo;
import com.xinyunlian.jinfu.dealer.entity.DealerUserOrderPo;
import com.xinyunlian.jinfu.dealer.enums.EDealerUserOrderStatus;
import com.xinyunlian.jinfu.user.dao.DealerUserDao;
import com.xinyunlian.jinfu.user.dto.DealerUserDto;
import com.xinyunlian.jinfu.user.entity.DealerUserPo;
import org.apache.commons.collections.CollectionUtils;
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
 * Created by menglei on 2016年08月30日.
 */
@Service
public class DealerUserOrderServiceImpl implements DealerUserOrderService {

    @Autowired
    private DealerUserOrderDao dealerUserOrderDao;
    @Autowired
    private DealerUserDao dealerUserDao;

    @Override
    public long getCount(DealerUserOrderDto dealerUserOrderDto) {
        Specification<DealerUserOrderPo> spec = (Root<DealerUserOrderPo> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            Predicate predicate = cb.conjunction();
            List<Expression<Boolean>> expressions = predicate.getExpressions();
            if (null != dealerUserOrderDto) {
                if (!StringUtils.isEmpty(dealerUserOrderDto.getUserId())) {
                    expressions.add(cb.equal(root.get("userId"), dealerUserOrderDto.getUserId()));
                }
                if (null != dealerUserOrderDto.getBeginTime()) {
                    expressions.add(cb.greaterThanOrEqualTo(root.get("createTs"), DateHelper.getStartDate(dealerUserOrderDto.getBeginTime())));
                }
                if (null != dealerUserOrderDto.getEndTime()) {
                    expressions.add(cb.lessThanOrEqualTo(root.get("createTs"), DateHelper.getEndDate(dealerUserOrderDto.getEndTime())));
                }
                expressions.add(cb.equal(root.get("status"), EDealerUserOrderStatus.SUCCESS));
            }
            return predicate;
        };
        return dealerUserOrderDao.count(spec);
    }

    @Transactional
    @Override
    public List<DealerUserOrderDto> getDealerUserOrderList(DealerUserOrderDto dealerUserOrderDto) {
        Specification<DealerUserOrderPo> spec = (root, query, cb) -> {
            Predicate predicate = cb.conjunction();
            List<Expression<Boolean>> expressions = predicate.getExpressions();
            if (null != dealerUserOrderDto) {
                if (StringUtils.isNotEmpty(dealerUserOrderDto.getDealerName())) {
                    expressions.add(cb.like(root.<DealerPo>get("dealerPo").get("dealerName"), "%" + dealerUserOrderDto.getDealerName() + "%"));
                }
                if (StringUtils.isNotEmpty(dealerUserOrderDto.getProvinceId())) {
                    expressions.add(cb.equal(root.<DealerPo>get("dealerPo").get("provinceId"), dealerUserOrderDto.getProvinceId()));
                }
                if (StringUtils.isNotEmpty(dealerUserOrderDto.getCityId())) {
                    expressions.add(cb.equal(root.<DealerPo>get("dealerPo").get("cityId"), dealerUserOrderDto.getCityId()));
                }
                if (StringUtils.isNotEmpty(dealerUserOrderDto.getAreaId())) {
                    expressions.add(cb.equal(root.<DealerPo>get("dealerPo").get("areaId"), dealerUserOrderDto.getAreaId()));
                }
                if (StringUtils.isNotEmpty(dealerUserOrderDto.getStreetId())) {
                    expressions.add(cb.equal(root.<DealerPo>get("dealerPo").get("streetId"), dealerUserOrderDto.getStreetId()));
                }
                if (StringUtils.isNotEmpty(dealerUserOrderDto.getUserName())) {
                    expressions.add(cb.equal(root.<DealerUserPo>get("dealerUserPo").get("name"), dealerUserOrderDto.getUserName()));
                }
                if (StringUtils.isNotEmpty(dealerUserOrderDto.getProdId())) {
                    expressions.add(cb.equal(root.get("prodId"), dealerUserOrderDto.getProdId()));
                }
                if (dealerUserOrderDto.getOrderNoList() != null && dealerUserOrderDto.getOrderNoList().size() > 0) {
                    expressions.add(cb.in(root.get("orderNo")).value(dealerUserOrderDto.getOrderNoList()));
                }
            }
            return predicate;
        };
        List<DealerUserOrderPo> list = dealerUserOrderDao.findAll(spec);
        List<DealerUserOrderDto> result = new ArrayList<>();
        if (!CollectionUtils.isEmpty(list)) {
            for (DealerUserOrderPo po : list) {
                DealerUserOrderDto dto = ConverterService.convert(po, DealerUserOrderDto.class);
                if (po.getDealerPo() != null) {
                    DealerDto dealerDto = ConverterService.convert(po.getDealerPo(), DealerDto.class);
                    dto.setDealerDto(dealerDto);
                }
                if (po.getDealerUserPo() != null) {
                    DealerUserDto dealerUserDto = ConverterService.convert(po.getDealerUserPo(), DealerUserDto.class);
                    dto.setDealerUserDto(dealerUserDto);
                }
                result.add(dto);
            }
        }
        return result;
    }

    /**
     * 按时间和分销员id查所有订单
     * @param dealerUserOrderDto
     * @return
     */
    @Override
    public List<DealerUserOrderDto> getOrderListByUserId(DealerUserOrderDto dealerUserOrderDto) {
        Specification<DealerUserOrderPo> spec = (Root<DealerUserOrderPo> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            Predicate predicate = cb.conjunction();
            List<Expression<Boolean>> expressions = predicate.getExpressions();
            if (null != dealerUserOrderDto) {
                if (!StringUtils.isEmpty(dealerUserOrderDto.getDealerId())) {
                    expressions.add(cb.equal(root.get("dealerId"), dealerUserOrderDto.getDealerId()));
                }
                if (!StringUtils.isEmpty(dealerUserOrderDto.getUserId())) {
                    expressions.add(cb.equal(root.get("userId"), dealerUserOrderDto.getUserId()));
                }
                if (null != dealerUserOrderDto.getBeginTime()) {
                    expressions.add(cb.greaterThanOrEqualTo(root.get("createTs"), DateHelper.getStartDate(dealerUserOrderDto.getBeginTime())));
                }
                if (null != dealerUserOrderDto.getEndTime()) {
                    expressions.add(cb.lessThanOrEqualTo(root.get("createTs"), DateHelper.getEndDate(dealerUserOrderDto.getEndTime())));
                }
            }
            return predicate;
        };
        List<DealerUserOrderPo> list = dealerUserOrderDao.findAll(spec);
        List<DealerUserOrderDto> result = new ArrayList<>();
        for (DealerUserOrderPo po : list) {
            DealerUserOrderDto dto = ConverterService.convert(po, DealerUserOrderDto.class);
            result.add(dto);
        }
        return result;
    }

    @Transactional
    @Override
    public DealerUserOrderSearchDto getOrderPage(DealerUserOrderSearchDto dealerUserOrderSearchDto) {
        Specification<DealerUserOrderPo> spec = (root, query, cb) -> {
            Predicate predicate = cb.conjunction();
            List<Expression<Boolean>> expressions = predicate.getExpressions();
            if (null != dealerUserOrderSearchDto) {
                if (!StringUtils.isEmpty(dealerUserOrderSearchDto.getUserId())) {
                    expressions.add(cb.equal(root.get("userId"), dealerUserOrderSearchDto.getUserId()));
                }
                if (!StringUtils.isEmpty(dealerUserOrderSearchDto.getProdId())) {
                    expressions.add(cb.equal(root.get("prodId"), dealerUserOrderSearchDto.getProdId()));
                }
                if (null != dealerUserOrderSearchDto.getBeginTime()) {
                    expressions.add(cb.greaterThanOrEqualTo(root.get("createTs"), DateHelper.getStartDate(dealerUserOrderSearchDto.getBeginTime())));
                }
                if (null != dealerUserOrderSearchDto.getEndTime()) {
                    expressions.add(cb.lessThanOrEqualTo(root.get("createTs"), DateHelper.getEndDate(dealerUserOrderSearchDto.getEndTime())));
                }
            }
            return predicate;
        };
        Pageable pageable = new PageRequest(dealerUserOrderSearchDto.getCurrentPage() - 1, dealerUserOrderSearchDto.getPageSize(), Sort.Direction.DESC, "id");
        Page<DealerUserOrderPo> page = dealerUserOrderDao.findAll(spec, pageable);
        List<DealerUserOrderDto> data = new ArrayList<>();
        page.getContent().forEach(po -> {
            DealerUserOrderDto dealerUserOrderDto = ConverterService.convert(po, DealerUserOrderDto.class);
            dealerUserOrderDto.setCreateTime(DateHelper.formatDate(po.getCreateTs(), "yyyy.MM.dd HH:mm"));
            data.add(dealerUserOrderDto);
        });

        dealerUserOrderSearchDto.setList(data);
        dealerUserOrderSearchDto.setTotalPages(page.getTotalPages());
        dealerUserOrderSearchDto.setTotalRecord(page.getTotalElements());

        return dealerUserOrderSearchDto;
    }

    @Transactional
    @Override
    public DealerUserOrderDto createDealerUserOrder(DealerUserOrderDto dealerUserOrderDto) {
        if (dealerUserOrderDto != null) {
            if (StringUtils.isNotEmpty(dealerUserOrderDto.getMobile())) {
                DealerUserPo dealerUserPo = dealerUserDao.findByMobile(dealerUserOrderDto.getMobile());
                if (dealerUserPo != null) {
                    dealerUserOrderDto.setDealerId(dealerUserPo.getDealerId());
                    dealerUserOrderDto.setUserId(dealerUserPo.getUserId());
                }
            }
            if (StringUtils.isNotEmpty(dealerUserOrderDto.getDealerId()) && StringUtils.isNotEmpty(dealerUserOrderDto.getUserId())) {
                DealerUserOrderPo dealerUserOrderPo = ConverterService.convert(dealerUserOrderDto, DealerUserOrderPo.class);
                dealerUserOrderPo.setStatus(EDealerUserOrderStatus.PROCESS);
                dealerUserOrderDao.save(dealerUserOrderPo);
                return dealerUserOrderDto;
            }
        }
        return null;
    }

    /**
     * 传 orderNo,status
     * @param dealerUserOrderDto
     */
    @Transactional
    @Override
    public void updateOrderStatus(DealerUserOrderDto dealerUserOrderDto) {
        DealerUserOrderPo dealerUserOrderPo = dealerUserOrderDao.findByOrderNo(dealerUserOrderDto.getOrderNo());
        if (dealerUserOrderPo != null) {
            dealerUserOrderPo.setStatus(dealerUserOrderDto.getStatus());
            dealerUserOrderDao.save(dealerUserOrderPo);
        }
    }

    /**
     * 订单超过7天状态处理为error
     */
    @Transactional
    @Override
    public void updateExpireOrder() {
        dealerUserOrderDao.updateExpireOrder();
    }

}
