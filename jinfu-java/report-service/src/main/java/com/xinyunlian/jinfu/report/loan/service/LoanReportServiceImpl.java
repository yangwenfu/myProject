package com.xinyunlian.jinfu.report.loan.service;

import com.xinyunlian.jinfu.common.converter.ConverterService;
import com.xinyunlian.jinfu.common.util.BizUtil;
import com.xinyunlian.jinfu.common.util.DateHelper;
import com.xinyunlian.jinfu.report.loan.dao.LoanDtlDao;
import com.xinyunlian.jinfu.report.loan.dao.RepayDtlDao;
import com.xinyunlian.jinfu.report.loan.dao.RepayScheduleDao;
import com.xinyunlian.jinfu.report.loan.dto.*;
import com.xinyunlian.jinfu.report.loan.entity.LoanDtlPo;
import com.xinyunlian.jinfu.report.loan.entity.RepayDtlPo;
import com.xinyunlian.jinfu.report.loan.entity.RepaySchedulePo;
import com.xinyunlian.jinfu.report.loan.enums.ELoanStat;
import com.xinyunlian.jinfu.report.loan.enums.EReportType;
import com.xinyunlian.jinfu.report.loan.enums.EScheduleStatus;
import com.xinyunlian.jinfu.report.loan.enums.ETransMode;
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
import java.util.ArrayList;
import java.util.List;

/**
 * Created by dell on 2016/11/7.
 */
@Service
public class LoanReportServiceImpl implements LoanReportService {

    @Autowired
    private LoanDtlDao loanDtlDao;

    @Autowired
    private RepayDtlDao repayDtlDao;

    @Autowired
    private RepayScheduleDao repayScheduleDao;

    @Override
    public LoanDtlSearchDto getLoanDtlReport(LoanDtlSearchDto searchDto, EReportType reportType) {
        Specification<LoanDtlPo> spec = (root, query, cb) -> {
            Predicate predicate = cb.conjunction();
            List<Expression<Boolean>> expressions = predicate.getExpressions();
            if(searchDto != null){
                if(StringUtils.isNotBlank(searchDto.getLoanId())){
                    expressions.add(cb.like(root.get("loanId"), BizUtil.filterStringRight(searchDto.getLoanId())));
                }
                if(StringUtils.isNotBlank(searchDto.getProductName())){
                    expressions.add(cb.like(root.get("loanName"), BizUtil.filterStringRight(searchDto.getProductName())));
                }
                if(searchDto.getLoanStatus() != null && searchDto.getLoanStatus() != ELoanStat.ALL){
                    expressions.add(cb.equal(root.get("loanStat"), searchDto.getLoanStatus()));
                }
                if(StringUtils.isNotBlank(searchDto.getTransferStartDate())){
                    expressions.add(cb.greaterThanOrEqualTo(root.get("transferDate"), DateHelper.getStartDate(searchDto.getTransferStartDate())));
                }
                if(StringUtils.isNotBlank(searchDto.getTransferEndDate())){
                    expressions.add(cb.lessThanOrEqualTo(root.get("transferDate"), DateHelper.getEndDate(searchDto.getTransferEndDate())));
                }
            }
            return predicate;
        };

        List<LoanDtlPo> data = new ArrayList<>();
        if(EReportType.HTML == reportType){
            Pageable pageable = new PageRequest(searchDto.getCurrentPage() - 1, searchDto.getPageSize(), Sort.Direction.ASC, "transferDate");
            Page<LoanDtlPo> page = loanDtlDao.findAll(spec, pageable);
            data = page.getContent();
            searchDto.setTotalPages(page.getTotalPages());
            searchDto.setTotalRecord(page.getTotalElements());
        } else if(EReportType.EXCEL == reportType){
            data = loanDtlDao.findAll(spec);
        }

        List<LoanDtlDto> dtos = new ArrayList<>();
        data.forEach(loanDtlPo -> {
            LoanDtlDto loanDtlDto = ConverterService.convert(loanDtlPo, LoanDtlDto.class);
            dtos.add(loanDtlDto);
        });

        searchDto.setList(dtos);

        return searchDto;
    }

    @Override
    public RepaySchdSearchDto getRepaySchdReport(RepaySchdSearchDto searchDto, EReportType reportType) {
        Specification<RepaySchedulePo> spec = (root, query, cb) -> {
            Predicate predicate = cb.conjunction();
            List<Expression<Boolean>> expressions = predicate.getExpressions();
            if(searchDto != null){
                if(StringUtils.isNotBlank(searchDto.getLoanId())){
                    expressions.add(cb.like(root.get("loanId"), BizUtil.filterStringRight(searchDto.getLoanId())));
                }
                if(StringUtils.isNotBlank(searchDto.getProductName())){
                    expressions.add(cb.like(root.get("loanName"), BizUtil.filterStringRight(searchDto.getProductName())));
                }
                if(searchDto.getRepayStatus() != null && searchDto.getRepayStatus() != EScheduleStatus.ALL){
                    expressions.add(cb.equal(root.get("scheduleStatus"), searchDto.getRepayStatus()));
                }
                if(StringUtils.isNotBlank(searchDto.getDueDateStart())){
                    expressions.add(cb.greaterThanOrEqualTo(root.get("dueDate"), searchDto.getDueDateStart()));
                }
                if(StringUtils.isNotBlank(searchDto.getDueDateEnd())){
                    expressions.add(cb.lessThanOrEqualTo(root.get("dueDate"), searchDto.getDueDateEnd()));
                }
            }
            return predicate;
        };

        List<RepaySchedulePo> data = new ArrayList<>();
        if(EReportType.HTML == reportType){
            Pageable pageable = new PageRequest(searchDto.getCurrentPage() - 1, searchDto.getPageSize(), Sort.Direction.ASC, "dueDate");
            Page<RepaySchedulePo> page = repayScheduleDao.findAll(spec, pageable);
            data = page.getContent();
            searchDto.setTotalPages(page.getTotalPages());
            searchDto.setTotalRecord(page.getTotalElements());
        } else if(EReportType.EXCEL == reportType){
            data = repayScheduleDao.findAll(spec);
        }

        List<RepayScheduleDto> dtos = new ArrayList<>();
        data.forEach(repaySchedulePo -> {
            RepayScheduleDto repayScheduleDto = ConverterService.convert(repaySchedulePo, RepayScheduleDto.class);
            dtos.add(repayScheduleDto);
        });

        searchDto.setList(dtos);

        return searchDto;
    }

    @Override
    public RepayDtlSearchDto getRepayDtlReport(RepayDtlSearchDto searchDto, EReportType reportType) {
        Specification<RepayDtlPo> spec = (root, query, cb) -> {
            Predicate predicate = cb.conjunction();
            List<Expression<Boolean>> expressions = predicate.getExpressions();
            if(searchDto != null){
                if(StringUtils.isNotBlank(searchDto.getLoanId())){
                    expressions.add(cb.like(root.get("loanId"), BizUtil.filterStringRight(searchDto.getLoanId())));
                }
                if(StringUtils.isNotBlank(searchDto.getProductName())){
                    expressions.add(cb.like(root.get("loanName"), BizUtil.filterStringRight(searchDto.getProductName())));
                }
                if(searchDto.getTransMode() != null && searchDto.getTransMode() != ETransMode.ALL){
                    expressions.add(cb.equal(root.get("transMode"), searchDto.getTransMode()));
                }
                if(StringUtils.isNotBlank(searchDto.getRepayStartDate())){
                    expressions.add(cb.greaterThanOrEqualTo(root.get("repayDate"), searchDto.getRepayStartDate()));
                }
                if(StringUtils.isNotBlank(searchDto.getRepayEndDate())){
                    expressions.add(cb.lessThanOrEqualTo(root.get("repayDate"), searchDto.getRepayEndDate()));
                }
            }
            return predicate;
        };

        List<RepayDtlPo> data = new ArrayList<>();
        if(EReportType.HTML == reportType){
            Pageable pageable = new PageRequest(searchDto.getCurrentPage() - 1, searchDto.getPageSize(), Sort.Direction.ASC, "repayDate");
            Page<RepayDtlPo> page = repayDtlDao.findAll(spec, pageable);
            data = page.getContent();
            searchDto.setTotalPages(page.getTotalPages());
            searchDto.setTotalRecord(page.getTotalElements());
        } else if(EReportType.EXCEL == reportType){
            data = repayDtlDao.findAll(spec);
        }

        List<RepayDtlDto> dtos = new ArrayList<>();
        data.forEach(RepayDtlPo -> {
            RepayDtlDto repayDtlDto = ConverterService.convert(RepayDtlPo, RepayDtlDto.class);
            LoanMCouponDto loanMCouponDto = new LoanMCouponDto();
            repayDtlDto.setCouponDesc(RepayDtlPo.getCouponDesc());
            repayDtlDto.setCouponPrice(RepayDtlPo.getCouponPrice());
            dtos.add(repayDtlDto);
        });

        searchDto.setList(dtos);

        return searchDto;
    }
}
