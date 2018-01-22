package com.xinyunlian.jinfu.fintxhistory.service;

import com.xinyunlian.jinfu.common.converter.ConverterService;
import com.xinyunlian.jinfu.common.exception.BizServiceException;
import com.xinyunlian.jinfu.common.util.DateHelper;
import com.xinyunlian.jinfu.fintxhistory.dao.FinTxHistoryDao;
import com.xinyunlian.jinfu.fintxhistory.dto.FinTxHistoryDto;
import com.xinyunlian.jinfu.fintxhistory.dto.FinTxHistorySearchDto;
import com.xinyunlian.jinfu.fintxhistory.entity.FinTxHistoryPo;
import com.xinyunlian.jinfu.fintxhistory.enums.ETxType;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by dongfangchao on 2016/11/21.
 */
@Service
public class FinTxHistoryServiceImpl implements FinTxHistoryService {

    @Autowired
    private FinTxHistoryDao finTxHistoryDao;

    @Override
    public FinTxHistorySearchDto getFinTxHistoryPage(FinTxHistorySearchDto searchDto) throws BizServiceException {

        Specification<FinTxHistoryPo> spec = (root, query, cb) -> {
            Predicate predicate = cb.conjunction();
            List<Expression<Boolean>> expressions = predicate.getExpressions();
            if (searchDto != null) {
                if (!StringUtils.isEmpty(searchDto.getUserId())) {
                    expressions.add(cb.like(root.get("userId"), searchDto.getUserId()));
                }
                if (searchDto.getOrderDateFrom() != null) {
                    expressions.add(cb.greaterThanOrEqualTo(root.get("orderDate"), DateHelper.getStartDate(searchDto.getOrderDateFrom())));
                }
                if (searchDto.getOrderDateTo() != null) {
                    expressions.add(cb.lessThanOrEqualTo(root.get("orderDate"), DateHelper.getEndDate(searchDto.getOrderDateTo())));
                }
                if (searchDto.getTxType() != null){
                    expressions.add(cb.equal(root.get("txType"), searchDto.getTxType()));
                }else {
                    expressions.add(cb.in(root.get("txType")).value(Arrays.asList(ETxType.APPLY_PUR, ETxType.REDEEM)));
                }
            }
            return predicate;
        };

        Sort sort = new Sort(Sort.Direction.DESC, "orderDate");
        Pageable pageable = new PageRequest(searchDto.getCurrentPage() - 1, searchDto.getPageSize(), sort);
        Page<FinTxHistoryPo> page = finTxHistoryDao.findAll(spec, pageable);

        List<FinTxHistoryDto> data = new ArrayList<>();
        page.getContent().forEach(po -> {
            FinTxHistoryDto dto = ConverterService.convert(po, FinTxHistoryDto.class);
            data.add(dto);
        });

        searchDto.setList(data);
        searchDto.setTotalPages(page.getTotalPages());
        searchDto.setTotalRecord(page.getTotalElements());
        return searchDto;
    }

    @Override
    @Transactional
    public FinTxHistoryDto addFinTxHistory(FinTxHistoryDto dto) throws BizServiceException {

        FinTxHistoryPo po = ConverterService.convert(dto, FinTxHistoryPo.class);
        po = finTxHistoryDao.save(po);
        dto.setFinTxId(po.getFinTxId());

        return dto;
    }

    @Override
    @Transactional
    public FinTxHistoryDto updateFinTxHistoryStatus(FinTxHistoryDto dto) throws BizServiceException {

        FinTxHistoryPo po = finTxHistoryDao.findOne(dto.getFinTxId());
        po.setTxStatus(dto.getTxStatus());
        if (!StringUtils.isEmpty(dto.getExtTxId())){
            po.setExtTxId(dto.getExtTxId());
        }
        FinTxHistoryDto retDto = ConverterService.convert(po, FinTxHistoryDto.class);

        return retDto;
    }

    @Override
    public List<FinTxHistoryDto> getFinTxHistory(FinTxHistorySearchDto searchDto) throws BizServiceException {
        List<FinTxHistoryDto> retList = new ArrayList<>();

        List<FinTxHistoryPo> list = finTxHistoryDao.findByTxTypeAndTxStatus(searchDto.getTxType(), searchDto.getTxStatus());
        if (!CollectionUtils.isEmpty(list)){
            list.forEach(item -> {
                FinTxHistoryDto dto = ConverterService.convert(item, FinTxHistoryDto.class);
                retList.add(dto);
            });
        }

        return retList;
    }
}
