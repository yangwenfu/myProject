package com.xinyunlian.jinfu.report.dealer.service;

import com.xinyunlian.jinfu.common.converter.ConverterService;
import com.xinyunlian.jinfu.common.util.DateHelper;
import com.xinyunlian.jinfu.report.dealer.dao.DealerStatsStoreDao;
import com.xinyunlian.jinfu.report.dealer.dto.DealerStatsStoreDto;
import com.xinyunlian.jinfu.report.dealer.dto.DealerStatsStoreSearchDto;
import com.xinyunlian.jinfu.report.dealer.entity.DealerStatsStorePo;
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
public class DealerStatsStoreServiceImpl implements DealerStatsStoreService {

    @Autowired
    protected DealerStatsStoreDao dealerStatsStoreDao;

    @Transactional
    @Override
    public DealerStatsStoreSearchDto getStatsStorePage(DealerStatsStoreSearchDto dealerStatsStoreSearchDto) {
        Specification<DealerStatsStorePo> spec = (root, query, cb) -> {
            Predicate predicate = cb.conjunction();
            List<Expression<Boolean>> expressions = predicate.getExpressions();
            if (null != dealerStatsStoreSearchDto) {
                if (!StringUtils.isEmpty(dealerStatsStoreSearchDto.getDealerId())) {
                    expressions.add(cb.equal(root.get("dealerId"), dealerStatsStoreSearchDto.getDealerId()));
                }
                if (!StringUtils.isEmpty(dealerStatsStoreSearchDto.getUserId())) {
                    expressions.add(cb.equal(root.get("userId"), dealerStatsStoreSearchDto.getUserId()));
                }
                if (StringUtils.isNotEmpty(dealerStatsStoreSearchDto.getLastId())) {
                    expressions.add(cb.lessThanOrEqualTo(root.get("id"), dealerStatsStoreSearchDto.getLastId()));
                }
                if (null != dealerStatsStoreSearchDto.getBeginTime()) {
                    expressions.add(cb.greaterThanOrEqualTo(root.get("createTs"), DateHelper.getStartDate(dealerStatsStoreSearchDto.getBeginTime())));
                }
                if (null != dealerStatsStoreSearchDto.getEndTime()) {
                    expressions.add(cb.lessThanOrEqualTo(root.get("createTs"), DateHelper.getEndDate(dealerStatsStoreSearchDto.getEndTime())));
                }
            }
            return predicate;
        };
        Pageable pageable = new PageRequest(dealerStatsStoreSearchDto.getCurrentPage() - 1, dealerStatsStoreSearchDto.getPageSize(), Sort.Direction.DESC, "id");
        Page<DealerStatsStorePo> page = dealerStatsStoreDao.findAll(spec, pageable);
        List<DealerStatsStoreDto> data = new ArrayList<>();
        page.getContent().forEach(po -> {
            DealerStatsStoreDto dealerStatsStoreDto = ConverterService.convert(po, DealerStatsStoreDto.class);
            data.add(dealerStatsStoreDto);
        });

        dealerStatsStoreSearchDto.setList(data);
        dealerStatsStoreSearchDto.setTotalPages(page.getTotalPages());
        dealerStatsStoreSearchDto.setTotalRecord(page.getTotalElements());

        return dealerStatsStoreSearchDto;
    }

}
