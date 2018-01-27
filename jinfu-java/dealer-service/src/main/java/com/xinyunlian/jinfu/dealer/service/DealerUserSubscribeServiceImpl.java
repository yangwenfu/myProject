package com.xinyunlian.jinfu.dealer.service;

import com.xinyunlian.jinfu.common.converter.ConverterService;
import com.xinyunlian.jinfu.common.util.DateHelper;
import com.xinyunlian.jinfu.dealer.dao.DealerUserSubscribeDao;
import com.xinyunlian.jinfu.dealer.dao.DealerUserSubscribeViewDao;
import com.xinyunlian.jinfu.dealer.dto.*;
import com.xinyunlian.jinfu.dealer.entity.DealerUserSubscribePo;
import com.xinyunlian.jinfu.dealer.entity.DealerUserSubscribeViewPo;
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
 * Created by menglei on 2016年09月02日.
 */
@Service
public class DealerUserSubscribeServiceImpl implements DealerUserSubscribeService {

    @Autowired
    private DealerUserSubscribeDao dealerUserSubscribeDao;
    @Autowired
    private DealerUserSubscribeViewDao dealerUserSubscribeViewDao;

    @Override
    @Transactional
    public DealerUserSubscribeSearchDto getPage(DealerUserSubscribeSearchDto dealerUserSubscribeSearchDto) {

        Specification<DealerUserSubscribeViewPo> spec = (root, query, cb) -> {
            Predicate predicate = cb.conjunction();
            List<Expression<Boolean>> expressions = predicate.getExpressions();
            if (dealerUserSubscribeSearchDto != null) {
                if (StringUtils.isNotEmpty(dealerUserSubscribeSearchDto.getBeginTime())) {
                    expressions.add(cb.greaterThanOrEqualTo(root.get("createTs"), DateHelper.getStartDate(dealerUserSubscribeSearchDto.getBeginTime())));
                }
                if (StringUtils.isNotEmpty(dealerUserSubscribeSearchDto.getEndTime())) {
                    expressions.add(cb.lessThanOrEqualTo(root.get("createTs"), DateHelper.getEndDate(dealerUserSubscribeSearchDto.getEndTime())));
                }
                if (StringUtils.isNotEmpty(dealerUserSubscribeSearchDto.getUserName())) {
                    expressions.add(cb.like(root.get("userName"), "%" + dealerUserSubscribeSearchDto.getUserName()+ "%"));
                }
                if (StringUtils.isNotEmpty(dealerUserSubscribeSearchDto.getMobile())) {
                    expressions.add(cb.like(root.get("mobile"), dealerUserSubscribeSearchDto.getMobile() + "%"));
                }
                if (StringUtils.isNotEmpty(dealerUserSubscribeSearchDto.getDealerName())) {
                    expressions.add(cb.like(root.get("dealerName"), "%" + dealerUserSubscribeSearchDto.getDealerName() + "%"));
                }
                if (StringUtils.isNotEmpty(dealerUserSubscribeSearchDto.getBelongName())) {
                    expressions.add(cb.like(root.get("belongName"), "%" + dealerUserSubscribeSearchDto.getBelongName() + "%"));
                }
            }
            return predicate;
        };

        Pageable pageable = new PageRequest(dealerUserSubscribeSearchDto.getCurrentPage() - 1, dealerUserSubscribeSearchDto.getPageSize(), Sort.Direction.DESC, "id");
        Page<DealerUserSubscribeViewPo> page = dealerUserSubscribeViewDao.findAll(spec, pageable);

        List<DealerUserSubscribeDto> data = new ArrayList<>();
        page.getContent().forEach(po -> {
            DealerUserSubscribeDto dealerUserSubscribeDto = ConverterService.convert(po, DealerUserSubscribeDto.class);
            dealerUserSubscribeDto.setCreateTime(DateHelper.formatDate(po.getCreateTs(), "yyyy-MM-dd HH:mm:ss"));
            data.add(dealerUserSubscribeDto);
        });

        dealerUserSubscribeSearchDto.setList(data);
        dealerUserSubscribeSearchDto.setTotalPages(page.getTotalPages());
        dealerUserSubscribeSearchDto.setTotalRecord(page.getTotalElements());

        return dealerUserSubscribeSearchDto;
    }

    @Override
    @Transactional
    public List<DealerUserSubscribeDto> getReport(DealerUserSubscribeSearchDto dealerUserSubscribeSearchDto) {

        Specification<DealerUserSubscribeViewPo> spec = (root, query, cb) -> {
            Predicate predicate = cb.conjunction();
            List<Expression<Boolean>> expressions = predicate.getExpressions();
            if (dealerUserSubscribeSearchDto != null) {
                if (StringUtils.isNotEmpty(dealerUserSubscribeSearchDto.getBeginTime())) {
                    expressions.add(cb.greaterThanOrEqualTo(root.get("createTs"), DateHelper.getStartDate(dealerUserSubscribeSearchDto.getBeginTime())));
                }
                if (StringUtils.isNotEmpty(dealerUserSubscribeSearchDto.getEndTime())) {
                    expressions.add(cb.lessThanOrEqualTo(root.get("createTs"), DateHelper.getEndDate(dealerUserSubscribeSearchDto.getEndTime())));
                }
                if (StringUtils.isNotEmpty(dealerUserSubscribeSearchDto.getUserName())) {
                    expressions.add(cb.like(root.get("userName"), "%" + dealerUserSubscribeSearchDto.getUserName()+ "%"));
                }
                if (StringUtils.isNotEmpty(dealerUserSubscribeSearchDto.getMobile())) {
                    expressions.add(cb.like(root.get("mobile"), dealerUserSubscribeSearchDto.getMobile() + "%"));
                }
                if (StringUtils.isNotEmpty(dealerUserSubscribeSearchDto.getDealerName())) {
                    expressions.add(cb.like(root.get("dealerName"), "%" + dealerUserSubscribeSearchDto.getDealerName() + "%"));
                }
                if (StringUtils.isNotEmpty(dealerUserSubscribeSearchDto.getBelongName())) {
                    expressions.add(cb.like(root.get("belongName"), "%" + dealerUserSubscribeSearchDto.getBelongName() + "%"));
                }
            }
            return predicate;
        };

        List<DealerUserSubscribeViewPo> list = dealerUserSubscribeViewDao.findAll(spec);
        List<DealerUserSubscribeDto> data = new ArrayList<>();
        list.forEach(po -> {
            DealerUserSubscribeDto dealerUserSubscribeDto = ConverterService.convert(po, DealerUserSubscribeDto.class);
            dealerUserSubscribeDto.setCreateTime(DateHelper.formatDate(po.getCreateTs(), "yyyy-MM-dd HH:mm:ss"));
            data.add(dealerUserSubscribeDto);
        });

        return data;
    }

    @Transactional
    @Override
    public void createDealerUserSubscribe(DealerUserSubscribeDto dealerUserSubscribeDto) {
        if (dealerUserSubscribeDto != null) {
            DealerUserSubscribePo dealerUserSubscribePo = dealerUserSubscribeDao.findByOpenIdAndWechatType(dealerUserSubscribeDto.getOpenId(), dealerUserSubscribeDto.getWechatType());
            if (dealerUserSubscribePo == null) {
                DealerUserSubscribePo po = ConverterService.convert(dealerUserSubscribeDto, DealerUserSubscribePo.class);
                dealerUserSubscribeDao.save(po);
            }
        }
    }

}
