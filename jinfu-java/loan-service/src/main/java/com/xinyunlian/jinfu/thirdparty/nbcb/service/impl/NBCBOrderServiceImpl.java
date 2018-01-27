package com.xinyunlian.jinfu.thirdparty.nbcb.service.impl;

import com.xinyunlian.jinfu.common.converter.ConverterService;
import com.xinyunlian.jinfu.common.enums.EErrorCode;
import com.xinyunlian.jinfu.common.exception.BizServiceException;
import com.xinyunlian.jinfu.common.util.BizUtil;
import com.xinyunlian.jinfu.common.util.DateHelper;
import com.xinyunlian.jinfu.thirdparty.nbcb.dao.NBCBOrderDao;
import com.xinyunlian.jinfu.thirdparty.nbcb.dao.NBCBRegionDao;
import com.xinyunlian.jinfu.thirdparty.nbcb.dto.NBCBOrderDto;
import com.xinyunlian.jinfu.thirdparty.nbcb.dto.NBCBOrderSearchDto;
import com.xinyunlian.jinfu.thirdparty.nbcb.entity.NBCBOrderPo;
import com.xinyunlian.jinfu.thirdparty.nbcb.entity.NBCBRegionPo;
import com.xinyunlian.jinfu.thirdparty.nbcb.enums.ENBCBApprStatus;
import com.xinyunlian.jinfu.thirdparty.nbcb.enums.ENBCBCreditStatus;
import com.xinyunlian.jinfu.thirdparty.nbcb.enums.ENBCBReceiveStatus;
import com.xinyunlian.jinfu.thirdparty.nbcb.service.NBCBOrderService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Created by bright on 2017/5/15.
 */
@Service("nbcbOrderServiceImpl")
public class NBCBOrderServiceImpl implements NBCBOrderService {

    @Autowired
    private NBCBOrderDao nbcbOrderDao;

    @Autowired
    private NBCBRegionDao nbcbRegionDao;

    @Override
    public NBCBOrderDto findByOrderNo(String orderNo) {
        return ConverterService.convert(nbcbOrderDao.findOne(orderNo), NBCBOrderDto.class);
    }

    @Override
    public String createOrder(NBCBOrderDto orderDto) throws BizServiceException {
        if (StringUtils.isNotEmpty(orderDto.getOrderNo())
                && Objects.nonNull(nbcbOrderDao.findOne(orderDto.getOrderNo()))) {
            throw new BizServiceException(EErrorCode.COMM_INTERNAL_ERROR, "订单号已存在");
        }
        NBCBOrderPo nbcbOrderPo = new NBCBOrderPo();
        nbcbOrderPo.setOrderNo(orderDto.getOrderNo());
        nbcbOrderPo.setUserId(orderDto.getUserId());
        nbcbOrderPo = nbcbOrderDao.save(nbcbOrderPo);
        return nbcbOrderPo.getOrderNo();
    }

    @Override
    public void updateOrder(NBCBOrderDto orderDto) throws BizServiceException {
        if (StringUtils.isEmpty(orderDto.getOrderNo())) {
            throw new BizServiceException(EErrorCode.COMM_INTERNAL_ERROR, "订单号为空");
        }
        NBCBOrderPo nbcbOrderPo = nbcbOrderDao.findOne(orderDto.getOrderNo());
        if (Objects.isNull(nbcbOrderPo)) {
            throw new BizServiceException(EErrorCode.COMM_INTERNAL_ERROR, "订单号不存在");
        }
        ConverterService.convert(orderDto, nbcbOrderPo);
        nbcbOrderDao.save(nbcbOrderPo);
    }

    @Override
    public Boolean canApply(String userId) {
        List<NBCBOrderPo> pendingOrders = nbcbOrderDao.findByUserIdAndReceiveStatus(userId, null);
        if (CollectionUtils.isNotEmpty(pendingOrders)) {
            return Boolean.FALSE;
        }
//        List<NBCBOrderPo> successOrders = nbcbOrderDao.findByUserIdAndReceiveStatus(userId, ENBCBReceiveStatus.SUCCESS);

        List<NBCBOrderPo> processingOrders = nbcbOrderDao.findByUserIdAndApprStatus(userId, null);
        if (CollectionUtils.isNotEmpty(processingOrders)) {
            if (processingOrders.stream()
                    .filter(nbcbOrderPo -> nbcbOrderPo.getReceiveStatus() != ENBCBReceiveStatus.FAILED)
                    .count() > 0) {
                return Boolean.FALSE;
            }
        }

        return hasEntryPermission(userId);
    }

    private Boolean hasEntryPermission(String userId) {

        List<NBCBOrderPo> actOrders = nbcbOrderDao.findByUserIdAndCreditStatus(userId, ENBCBCreditStatus.NORMAL);
        if (CollectionUtils.isNotEmpty(actOrders)) {
            return Boolean.FALSE;
        }

        List<NBCBOrderPo> processingOrders = nbcbOrderDao.findByUserIdAndApprStatus(userId, ENBCBApprStatus.PROCESSING);
        if (CollectionUtils.isNotEmpty(processingOrders)) {
            return Boolean.FALSE;
        }


        List<NBCBOrderPo> rejectedOrders = nbcbOrderDao.findByUserIdAndApprStatus(userId, ENBCBApprStatus.REJECTED);

        if (CollectionUtils.isNotEmpty(rejectedOrders)) {
            CollectionUtils.filter(rejectedOrders, object -> {
                NBCBOrderPo po = (NBCBOrderPo) object;
                if (DateHelper.betweenDays(po.getModifyTs(), new Date()) > 30) {
                    return false;
                } else {
                    return true;
                }
            });
            if (CollectionUtils.isNotEmpty(rejectedOrders)) {
                return Boolean.FALSE;
            }
        }

        return Boolean.TRUE;
    }

    @Override
    public Boolean hasEntryPermission(String userId, List<String> cityIds) {
        if(CollectionUtils.isEmpty(cityIds)){
            return Boolean.FALSE;
        }
        List<NBCBRegionPo> all = nbcbRegionDao.findAllByEnabledIsTrue();
        List<String> allowCityIds = all.stream()
                .map(nbcbRegionPo -> nbcbRegionPo.getCityId())
                .collect(Collectors.toList());
        if (!CollectionUtils.containsAny(allowCityIds, cityIds)) {
            return Boolean.FALSE;
        }

        return hasEntryPermission(userId);
    }

    @Override
    public Boolean areaCovered(List<String> cityIds){
        if(CollectionUtils.isEmpty(cityIds)){
            return Boolean.FALSE;
        }
        List<NBCBRegionPo> all = nbcbRegionDao.findAllByEnabledIsTrue();
        List<String> allowCityIds = all.stream()
                .map(nbcbRegionPo -> nbcbRegionPo.getCityId())
                .collect(Collectors.toList());
        if (!CollectionUtils.containsAny(allowCityIds, cityIds)) {
            return Boolean.FALSE;
        }
        return Boolean.TRUE;
    }

    @Override
    public List<String> getAllAppliedUserId() {
        return nbcbOrderDao.findUserIds();
    }

    @Override
    public NBCBOrderSearchDto getPage(NBCBOrderSearchDto searchDto) throws BizServiceException {

        Specification<NBCBOrderPo> spec = (root, query, cb) -> {
            Predicate predicate = cb.conjunction();
            List<Expression<Boolean>> expressions = predicate.getExpressions();
            if (searchDto != null) {
                if (!org.springframework.util.StringUtils.isEmpty(searchDto.getUserId())) {
                    expressions.add(cb.equal(root.get("userId"), searchDto.getUserId()));
                }
            }
            return predicate;
        };

        Sort sort = new Sort(Sort.Direction.DESC, "modifyTs");
        Pageable pageable = new PageRequest(searchDto.getCurrentPage() - 1, searchDto.getPageSize(), sort);
        Page<NBCBOrderPo> page = nbcbOrderDao.findAll(spec, pageable);

        List<NBCBOrderDto> data = page.getContent().stream()
                .map(item -> ConverterService.convert(item, NBCBOrderDto.class))
                .collect(Collectors.toList());

        searchDto.setList(data);
        searchDto.setTotalPages(page.getTotalPages());
        searchDto.setTotalRecord(page.getTotalElements());
        return searchDto;
    }
}
