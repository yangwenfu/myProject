package com.xinyunlian.jinfu.report.dealer.service;

import com.xinyunlian.jinfu.common.converter.ConverterService;
import com.xinyunlian.jinfu.common.util.DateHelper;
import com.xinyunlian.jinfu.report.dealer.dao.DealerStatsOrderDao;
import com.xinyunlian.jinfu.report.dealer.dto.DealerStatsMonthOrderDto;
import com.xinyunlian.jinfu.report.dealer.dto.DealerStatsOrderDto;
import com.xinyunlian.jinfu.report.dealer.dto.DealerStatsOrderSearchDto;
import com.xinyunlian.jinfu.report.dealer.entity.DealerStatsOrderPo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by bright on 2016/11/29.
 */
@Service
public class DealerStatsOrderServiceImpl implements DealerStatsOrderService {

    @Autowired
    private DealerStatsOrderDao dealerStatsOrderDao;

    @Transactional
    @Override
    public DealerStatsOrderSearchDto getStatsOrderPage(DealerStatsOrderSearchDto dealerStatsOrderSearchDto) {
        Specification<DealerStatsOrderPo> spec = (root, query, cb) -> {
            Predicate predicate = cb.conjunction();
            List<Expression<Boolean>> expressions = predicate.getExpressions();
            if (null != dealerStatsOrderSearchDto) {
                if (!StringUtils.isEmpty(dealerStatsOrderSearchDto.getProdId())) {
                    expressions.add(cb.equal(root.get("prodId"), dealerStatsOrderSearchDto.getProdId()));
                }
                if (!StringUtils.isEmpty(dealerStatsOrderSearchDto.getDealerId())) {
                    expressions.add(cb.equal(root.get("dealerId"), dealerStatsOrderSearchDto.getDealerId()));
                }
                if (!StringUtils.isEmpty(dealerStatsOrderSearchDto.getUserId())) {
                    expressions.add(cb.equal(root.get("userId"), dealerStatsOrderSearchDto.getUserId()));
                }
                if (StringUtils.isNotEmpty(dealerStatsOrderSearchDto.getLastId())) {
                    expressions.add(cb.lessThanOrEqualTo(root.get("id"), dealerStatsOrderSearchDto.getLastId()));
                }
                if (null != dealerStatsOrderSearchDto.getBeginTime()) {
                    expressions.add(cb.greaterThanOrEqualTo(root.get("createTs"), DateHelper.getStartDate(dealerStatsOrderSearchDto.getBeginTime())));
                }
                if (null != dealerStatsOrderSearchDto.getEndTime()) {
                    expressions.add(cb.lessThanOrEqualTo(root.get("createTs"), DateHelper.getEndDate(dealerStatsOrderSearchDto.getEndTime())));
                }
            }
            return predicate;
        };
        Pageable pageable = new PageRequest(dealerStatsOrderSearchDto.getCurrentPage() - 1, dealerStatsOrderSearchDto.getPageSize(), Sort.Direction.DESC, "id");
        Page<DealerStatsOrderPo> page = dealerStatsOrderDao.findAll(spec, pageable);
        List<DealerStatsOrderDto> data = new ArrayList<>();
        page.getContent().forEach(po -> {
            DealerStatsOrderDto dealerStatsOrderDto = ConverterService.convert(po, DealerStatsOrderDto.class);
            data.add(dealerStatsOrderDto);
        });

        dealerStatsOrderSearchDto.setList(data);
        dealerStatsOrderSearchDto.setTotalPages(page.getTotalPages());
        dealerStatsOrderSearchDto.setTotalRecord(page.getTotalElements());

        return dealerStatsOrderSearchDto;
    }

    /**
     * 按分销员id 统计月订单
     * @param userId
     * @return
     */
    @Transactional
    @Override
    public List<DealerStatsMonthOrderDto> getMonthByUserId(String userId, List<String> months) {
        List<DealerStatsMonthOrderDto> list = new ArrayList<>();
        List<Object[]> statsList = dealerStatsOrderDao.findMonthByUserId(userId, months);
        for (Object[] object : statsList) {
            //插入打款流水表
            DealerStatsMonthOrderDto dealerStatsMonthOrderDto = new DealerStatsMonthOrderDto();
            dealerStatsMonthOrderDto.setMonthDate(object[0].toString());
            dealerStatsMonthOrderDto.setProdId(object[1].toString());
            dealerStatsMonthOrderDto.setProdName(object[2].toString());
            dealerStatsMonthOrderDto.setSuccessCount(Long.valueOf(object[3].toString()));
            dealerStatsMonthOrderDto.setAllCount(Long.valueOf(object[4].toString()));

            list.add(dealerStatsMonthOrderDto);
        }
        return list;
    }

    /**
     * 按分销商id 统计月订单
     * @param dealerId
     * @return
     */
    @Transactional
    @Override
    public List<DealerStatsMonthOrderDto> getMonthByDealerId(String dealerId, List<String> months) {
        List<DealerStatsMonthOrderDto> list = new ArrayList<>();
        List<Object[]> statsList = dealerStatsOrderDao.findMonthByDealerId(dealerId, months);
        for (Object[] object : statsList) {
            //插入打款流水表
            DealerStatsMonthOrderDto dealerStatsMonthOrderDto = new DealerStatsMonthOrderDto();
            dealerStatsMonthOrderDto.setMonthDate(object[0].toString());
            dealerStatsMonthOrderDto.setProdId(object[1].toString());
            dealerStatsMonthOrderDto.setProdName(object[2].toString());
            dealerStatsMonthOrderDto.setSuccessCount(Long.valueOf(object[3].toString()));
            dealerStatsMonthOrderDto.setAllCount(Long.valueOf(object[4].toString()));

            list.add(dealerStatsMonthOrderDto);
        }
        return list;
    }

}
