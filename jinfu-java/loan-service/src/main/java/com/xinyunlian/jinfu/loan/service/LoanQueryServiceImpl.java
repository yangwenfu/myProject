package com.xinyunlian.jinfu.loan.service;

import com.xinyunlian.jinfu.appl.enums.ELoanApplSortType;
import com.xinyunlian.jinfu.common.constant.ApplicationConstant;
import com.xinyunlian.jinfu.common.converter.ConverterService;
import com.xinyunlian.jinfu.common.util.AmtUtils;
import com.xinyunlian.jinfu.common.util.BizUtil;
import com.xinyunlian.jinfu.common.util.DateHelper;
import com.xinyunlian.jinfu.loan.dao.LoanApplDao;
import com.xinyunlian.jinfu.loan.dao.LoanDtlDao;
import com.xinyunlian.jinfu.loan.dto.resp.MLoanDetailDto;
import com.xinyunlian.jinfu.loan.dto.req.LoanSearchListDto;
import com.xinyunlian.jinfu.loan.dto.resp.LoanApplDto;
import com.xinyunlian.jinfu.loan.dto.resp.LoanDtlDto;
import com.xinyunlian.jinfu.loan.dto.resp.LoanSearchResultDto;
import com.xinyunlian.jinfu.loan.entity.LoanApplPo;
import com.xinyunlian.jinfu.loan.entity.LoanDtlPo;
import com.xinyunlian.jinfu.loan.enums.ELoanStat;
import com.xinyunlian.jinfu.loan.enums.ETransferStat;
import com.xinyunlian.jinfu.product.dao.CreditProductDao;
import com.xinyunlian.jinfu.product.dao.ProductInfoDao;
import com.xinyunlian.jinfu.product.dto.LoanProductDetailDto;
import com.xinyunlian.jinfu.router.dto.FinanceSourceDto;
import com.xinyunlian.jinfu.router.enums.EFinanceSourceType;
import com.xinyunlian.jinfu.router.service.FinanceSourceService;
import com.xinyunlian.jinfu.schedule.enums.ELoanCustomerStatus;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by JL on 2016/9/28.
 */
@Service
public class LoanQueryServiceImpl implements LoanQueryService {

    @Autowired
    private LoanDtlDao loanDtlDao;

    @Autowired
    private LoanApplDao loanApplDao;

    @Autowired
    private InnerApplService innerApplService;

    @Autowired
    private FinanceSourceService financeSourceService;

    @Override
    public LoanSearchListDto loanList(LoanSearchListDto search) {
        Specification<LoanDtlPo> spec = (Root<LoanDtlPo> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            Predicate predicate = cb.conjunction();
            List<Expression<Boolean>> expressions = predicate.getExpressions();
            if (null != search) {
                if (StringUtils.isNotBlank(search.getLoanId())) {
                    expressions.add(cb.like(root.get("loanId"), BizUtil.filterStringRight(search.getLoanId())));
                }
                if (StringUtils.isNotBlank(search.getApplId())) {
                    expressions.add(cb.like(root.get("applId"), BizUtil.filterStringRight(search.getApplId())));
                }
                if (StringUtils.isNotBlank(search.getLoanName())) {
                    expressions.add(cb.like(root.get("loanName"), BizUtil.filterStringRight(search.getLoanName())));
                }

                if (StringUtils.isNotBlank(search.getProdId()) && !"ALL".equals(search.getProdId())) {
                    expressions.add(cb.like(root.get("prodId"), search.getProdId()));
                }

                if (StringUtils.isNotBlank(search.getLoanStartDate())) {
                    expressions.add(cb.greaterThanOrEqualTo(root.get("createTs"), DateHelper.getStartDate(search.getLoanStartDate())));
                }
                if (StringUtils.isNotBlank(search.getLoanEndDate())) {
                    expressions.add(cb.lessThanOrEqualTo(root.get("createTs"), DateHelper.getEndDate(search.getLoanEndDate())));
                }

                if(StringUtils.isNotEmpty(search.getTransferStart())){
                    expressions.add(cb.greaterThanOrEqualTo(root.get("transferDate"), DateHelper.getStartDate(search.getTransferStart())));
                }

                if (StringUtils.isNotBlank(search.getTransferEnd())) {
                    expressions.add(cb.lessThanOrEqualTo(root.get("transferDate"), DateHelper.getEndDate(search.getTransferEnd())));
                }

                if(search.getLoanStat() != null && search.getLoanStat() != ELoanStat.ALL){
                    expressions.add(cb.equal(root.get("loanStat"), search.getLoanStat()));
                }

                if(search.getStatus() != null && search.getStatus() != ELoanCustomerStatus.ALL){
                    if(search.getStatus() == ELoanCustomerStatus.PAID){
                        expressions.add(cb.equal(root.get("loanStat"), ELoanStat.PAID));
                    }
                    if(search.getStatus() == ELoanCustomerStatus.OVERDUE){
                        expressions.add(cb.equal(root.get("loanStat"), ELoanStat.OVERDUE));
                    }
                    if(search.getStatus() == ELoanCustomerStatus.USE){
                        expressions.add(cb.equal(root.get("loanStat"), ELoanStat.NORMAL));
                        expressions.add(cb.equal(root.get("transferStat"), ETransferStat.SUCCESS));
                    }
                    if(search.getStatus() == ELoanCustomerStatus.PROCESS){
                        expressions.add(cb.equal(root.get("loanStat"), ELoanStat.NORMAL));
                        expressions.add(cb.or(cb.equal(root.get("transferStat"), ETransferStat.PROCESS), cb.isNull(root.get("transferStat"))));
                    }
                }


                if(search.getFinanceSourceType() != null && search.getFinanceSourceType() != EFinanceSourceType.ALL){
                    List<FinanceSourceDto> financeSourceDtos = financeSourceService.getAll();
                    Map<EFinanceSourceType, FinanceSourceDto> map = new HashMap<>();
                    financeSourceDtos.forEach(financeSourceDto -> map.put(financeSourceDto.getType(), financeSourceDto));

                    FinanceSourceDto item = map.get(search.getFinanceSourceType());

                    if(search.getFinanceSourceType() == EFinanceSourceType.OWN){
                        if(item != null){
                            expressions.add(cb.or(cb.equal(root.get("financeSourceId"), item.getId()), cb.isNull(root.get("financeSourceId"))));
                        }else{
                            expressions.add(cb.isNull(root.get("financeSourceId")));
                        }
                    }else{
                        if(item != null){
                            expressions.add(cb.equal(root.get("financeSourceId"), item.getId()));
                        }
                    }
                }

                if(search.getTransferStat() != null && search.getTransferStat() != ETransferStat.ALL){
                    expressions.add(cb.equal(root.get("transferStat"), search.getTransferStat()));
                }

                if (!search.getUserIds().isEmpty()) {
                    expressions.add(root.get("userId").in(search.getUserIds()));
                }
            }
            return predicate;
        };

        //根据获贷时间倒序排列
        Sort sort = new Sort(Sort.Direction.ASC, "transferDate");

        if (search.getSortType() == null || search.getSortType() == ELoanApplSortType.ASC) {
            sort = new Sort(Sort.Direction.ASC, "transferDate");
        } else if (search.getSortType() == ELoanApplSortType.DESC) {
            sort = new Sort(Sort.Direction.DESC, "transferDate");
        }

        Pageable pageable = new PageRequest(search.getCurrentPage() - 1, search.getPageSize(), sort);
        Page<LoanDtlPo> page = loanDtlDao.findAll(spec, pageable);

        List<LoanSearchResultDto> data = new ArrayList<>();
        for (LoanDtlPo po : page.getContent()) {
            LoanSearchResultDto loanSearchResultDto = ConverterService.convert(po, LoanSearchResultDto.class);
            loanSearchResultDto.setUnit(po.getTermType().getUnit());
            loanSearchResultDto.setPeriod(po.getTermLen());
            loanSearchResultDto.setRate(po.getLoanRt());
            LoanApplPo loanApplPo = loanApplDao.findOne(po.getApplId());

            loanSearchResultDto.setFinanceSourceId(po.getFinanceSourceId());

            String applDate = "";
            if(loanApplPo != null){
                applDate = DateHelper.formatDate(loanApplPo.getCreateTs(), ApplicationConstant.TIMESTAMP_FORMAT);
            }
            loanSearchResultDto.setApplDate(applDate);

            ELoanCustomerStatus status = innerApplService.getStatus(
                    ConverterService.convert(po, LoanDtlDto.class),
                    ConverterService.convert(loanApplPo, LoanApplDto.class)
            );

            loanSearchResultDto.setStatus(status);

            //剩余本金
            BigDecimal surplus = AmtUtils.max(po.getLoanAmt().subtract(po.getRepayedAmt()), BigDecimal.ZERO);
            loanSearchResultDto.setSurplus(surplus);

            //获贷时间
            loanSearchResultDto.setTransferDate(DateHelper.formatDate(
                po.getTransferDate(), ApplicationConstant.DATE_FORMAT
            ));

            data.add(loanSearchResultDto);
        }
        search.setList(data);
        search.setTotalPages(page.getTotalPages());
        search.setTotalRecord(page.getTotalElements());
        search.setPageSize(search.getPageSize());
        return search;
    }

    @Override
    public MLoanDetailDto loanDetail(String loanId) {

        MLoanDetailDto mLoanDetailDto = new MLoanDetailDto();

        LoanDtlPo loanDtlPo = loanDtlDao.findOne(loanId);

        if (loanDtlPo != null) {
            mLoanDetailDto.setLoan(ConverterService.convert(loanDtlPo, LoanDtlDto.class));
            LoanProductDetailDto product = innerApplService.getProduct(loanDtlPo.getApplId());
            mLoanDetailDto.setProduct(product);
        }

        return mLoanDetailDto;
    }
}
