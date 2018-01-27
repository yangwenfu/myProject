package com.xinyunlian.jinfu.finprofithistory.service;

import com.xinyunlian.jinfu.common.converter.ConverterService;
import com.xinyunlian.jinfu.common.exception.BizServiceException;
import com.xinyunlian.jinfu.common.util.DateHelper;
import com.xinyunlian.jinfu.finprofithistory.dao.FinProfitHistoryDao;
import com.xinyunlian.jinfu.finprofithistory.dao.FinProfitHistorySumDao;
import com.xinyunlian.jinfu.finprofithistory.dto.FinProfitHistoryDto;
import com.xinyunlian.jinfu.finprofithistory.dto.FinProfitHistorySearchDto;
import com.xinyunlian.jinfu.finprofithistory.dto.FinProfitHistorySumDto;
import com.xinyunlian.jinfu.finprofithistory.dto.FinProfitHistorySumSearchDto;
import com.xinyunlian.jinfu.finprofithistory.entity.FinProfitHistoryPo;
import com.xinyunlian.jinfu.finprofithistory.entity.FinProfitHistorySumPo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by dongfangchao on 2016/11/21.
 */
@Service
public class FinProfitHistoryServiceImpl implements FinProfitHistoryService {

    private static final Logger LOGGER = LoggerFactory.getLogger(FinProfitHistoryServiceImpl.class);

    @Autowired
    private FinProfitHistoryDao finProfitHistoryDao;
    @Autowired
    private FinProfitHistorySumDao finProfitHistorySumDao;

    @Override
    public List<FinProfitHistoryDto> getProfitHistory(FinProfitHistorySearchDto searchDto) throws BizServiceException {

        Specification<FinProfitHistoryPo> spec = (root, query, cb) -> {
            Predicate predicate = cb.conjunction();
            List<Expression<Boolean>> expressions = predicate.getExpressions();
            if (searchDto != null) {
                if (!StringUtils.isEmpty(searchDto.getUserId())) {
                    expressions.add(cb.equal(root.get("userId"), searchDto.getUserId()));
                }
                if (searchDto.getProfitDateFrom() != null){
                    expressions.add(cb.greaterThanOrEqualTo(root.get("profitDate"), searchDto.getProfitDateFrom()));
                }
                if (searchDto.getProfitDateTo() != null){
                    expressions.add(cb.lessThanOrEqualTo(root.get("profitDate"), searchDto.getProfitDateTo()));
                }
            }
            return predicate;
        };

        Sort sort = new Sort(Sort.Direction.DESC, "profitDate");
        List<FinProfitHistoryPo> list = finProfitHistoryDao.findAll(spec, sort);
        List<FinProfitHistoryDto> result = new ArrayList<>();
        if (!CollectionUtils.isEmpty(list)){
            list.forEach(po -> {
                FinProfitHistoryDto dto = ConverterService.convert(po, FinProfitHistoryDto.class);
                result.add(dto);
            });
        }

        return result;
    }

    @Override
    public List<FinProfitHistorySumDto> getProfitHistorySum(FinProfitHistorySumSearchDto searchDto) throws BizServiceException {

        Specification<FinProfitHistorySumPo> spec = (root, query, cb) -> {
            Predicate predicate = cb.conjunction();
            List<Expression<Boolean>> expressions = predicate.getExpressions();
            if (searchDto != null) {
                if (searchDto.getProfitDateFrom() != null){
                    expressions.add(cb.greaterThanOrEqualTo(root.get("profitDate"), searchDto.getProfitDateFrom()));
                }
                if (searchDto.getProfitDateTo() != null){
                    expressions.add(cb.lessThanOrEqualTo(root.get("profitDate"), searchDto.getProfitDateTo()));
                }
                if (!StringUtils.isEmpty(searchDto.getUserId())) {
                    expressions.add(cb.equal(root.get("userId"), searchDto.getUserId()));
                }
            }
            return predicate;
        };

        Sort sort = new Sort(Sort.Direction.DESC, "profitDate");
        List<FinProfitHistorySumPo> list = finProfitHistorySumDao.findAll(spec, sort);
        List<FinProfitHistorySumDto> result = new ArrayList<>();
        if (!CollectionUtils.isEmpty(list)){
            list.forEach(po -> {
                FinProfitHistorySumDto dto = ConverterService.convert(po, FinProfitHistorySumDto.class);
                result.add(dto);
            });
        }

        return result;
    }

    @Override
    public FinProfitHistorySumSearchDto getProfitHistorySumPage(FinProfitHistorySumSearchDto searchDto) throws BizServiceException {

        Specification<FinProfitHistorySumPo> spec = (root, query, cb) -> {
            Predicate predicate = cb.conjunction();
            List<Expression<Boolean>> expressions = predicate.getExpressions();
            if (searchDto != null) {
                if (!StringUtils.isEmpty(searchDto.getUserId())) {
                    expressions.add(cb.equal(root.get("userId"), searchDto.getUserId()));
                }
                if (searchDto.getProfitDateFrom() != null){
                    Date from = DateHelper.getStartDate(searchDto.getProfitDateFrom());
                    expressions.add(cb.greaterThanOrEqualTo(root.get("profitDate"), from));
                }
                if (searchDto.getProfitDateTo() != null){
                    Date to = DateHelper.getEndDate(searchDto.getProfitDateTo());
                    expressions.add(cb.lessThanOrEqualTo(root.get("profitDate"), to));
                }
            }
            return predicate;
        };

        Sort sort = new Sort(Sort.Direction.DESC, "profitDate");
        Pageable pageable = new PageRequest(searchDto.getCurrentPage() - 1, searchDto.getPageSize(), sort);
        Page<FinProfitHistorySumPo> page = finProfitHistorySumDao.findAll(spec, pageable);

        List<FinProfitHistorySumDto> data = new ArrayList<>();
        page.getContent().forEach(po -> {
            FinProfitHistorySumDto dto = ConverterService.convert(po, FinProfitHistorySumDto.class);
            data.add(dto);
        });

        searchDto.setList(data);
        searchDto.setTotalPages(page.getTotalPages());
        searchDto.setTotalRecord(page.getTotalElements());
        return searchDto;
    }

    @Override
    @Transactional
    public void updateProfitHistory(FinProfitHistoryDto searchDto) throws BizServiceException {

        Date profitDate = DateHelper.getStartDate(searchDto.getProfitDate());
        FinProfitHistoryPo profitHistoryPo =
                finProfitHistoryDao.findSpecOne(searchDto.getUserId(), searchDto.getExtTxAccId(), searchDto.getFinFundId(), profitDate);

        if (profitHistoryPo != null){
            profitHistoryPo.setAssetAmt(searchDto.getAssetAmt());
            profitHistoryPo.setProfitAmt(searchDto.getProfitAmt());
            profitHistoryPo.setTotalProfit(searchDto.getTotalProfit());
        }else {
            profitHistoryPo = ConverterService.convert(searchDto, FinProfitHistoryPo.class);
            profitHistoryPo.setProfitDate(profitDate);
            finProfitHistoryDao.save(profitHistoryPo);
        }

    }

    @Override
    @Transactional
    public void updateProfitHistorySummary(String userId, Date profitDate) throws BizServiceException {
        Date profitStartDate = DateHelper.getStartDate(profitDate);

        FinProfitHistorySumPo po =
                finProfitHistorySumDao.findByUserIdAndProfitDate(userId, profitStartDate);

        List<FinProfitHistoryPo> list = finProfitHistoryDao.findByUserIdAndProfitDate(userId, profitStartDate);
        BigDecimal profitAmt = new BigDecimal("0");
        BigDecimal assetAmt = new BigDecimal("0");
        if (!CollectionUtils.isEmpty(list)){
            for (FinProfitHistoryPo tmpPo:list) {
                profitAmt = profitAmt.add(tmpPo.getProfitAmt());
                assetAmt = assetAmt.add(tmpPo.getAssetAmt());
            }
        }

        if (po != null){
            po.setAssetAmt(assetAmt);
            po.setProfitAmt(profitAmt);
        }else {
            po = new FinProfitHistorySumPo();
            po.setUserId(userId);
            po.setProfitDate(profitStartDate);
            po.setAssetAmt(assetAmt);
            po.setProfitAmt(profitAmt);
            finProfitHistorySumDao.save(po);
        }

    }


}
