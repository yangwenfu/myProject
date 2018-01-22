package com.xinyunlian.jinfu.report.allloan.service;

import com.xinyunlian.jinfu.common.converter.ConverterService;
import com.xinyunlian.jinfu.common.util.BizUtil;
import com.xinyunlian.jinfu.common.util.DateHelper;
import com.xinyunlian.jinfu.report.allloan.dao.LoanDtlAllDao;
import com.xinyunlian.jinfu.report.allloan.dao.RepayDtlAllDao;
import com.xinyunlian.jinfu.report.allloan.dao.RepayScheduleAllDao;
import com.xinyunlian.jinfu.report.allloan.dto.*;
import com.xinyunlian.jinfu.report.allloan.entity.LoanDtlAllPo;
import com.xinyunlian.jinfu.report.allloan.entity.RepayDtlAllPo;
import com.xinyunlian.jinfu.report.allloan.entity.RepayScheduleAllPo;
import com.xinyunlian.jinfu.report.allloan.enums.ELoanStat;
import com.xinyunlian.jinfu.report.allloan.enums.EReportType;
import com.xinyunlian.jinfu.report.allloan.enums.EScheduleStatus;
import com.xinyunlian.jinfu.report.allloan.enums.ETransMode;
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
public class AllLoanReportServiceImpl implements AllLoanReportService {

    @Autowired
    private LoanDtlAllDao loanDtlAllDao;

    @Autowired
    private RepayDtlAllDao repayDtlAllDao;

    @Autowired
    private RepayScheduleAllDao repayScheduleAllDao;

    @Override
    public LoanDtlSearchDto getLoanDtlReport(LoanDtlSearchDto searchDto, EReportType reportType) {
        Specification<LoanDtlAllPo> spec = (root, query, cb) -> {
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

        List<LoanDtlAllPo> data = new ArrayList<>();
        if(EReportType.HTML == reportType){
            Pageable pageable = new PageRequest(searchDto.getCurrentPage() - 1, searchDto.getPageSize(), Sort.Direction.ASC, "transferDate");
            Page<LoanDtlAllPo> page = loanDtlAllDao.findAll(spec, pageable);
            data = page.getContent();
            searchDto.setTotalPages(page.getTotalPages());
            searchDto.setTotalRecord(page.getTotalElements());
        } else if(EReportType.EXCEL == reportType){
            data = loanDtlAllDao.findAll(spec);
        }

        List<LoanDtlDto> dtos = new ArrayList<>();
        data.forEach(loanDtlAllPo -> {
            LoanDtlDto loanDtlDto = ConverterService.convert(loanDtlAllPo, LoanDtlDto.class);
            dtos.add(loanDtlDto);
        });

        searchDto.setList(dtos);

        return searchDto;
    }

    @Override
    public RepaySchdSearchDto getRepaySchdReport(RepaySchdSearchDto searchDto, EReportType reportType) {
        Specification<RepayScheduleAllPo> spec = (root, query, cb) -> {
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

        List<RepayScheduleAllPo> data = new ArrayList<>();
        if(EReportType.HTML == reportType){
            Pageable pageable = new PageRequest(searchDto.getCurrentPage() - 1, searchDto.getPageSize(), Sort.Direction.ASC, "dueDate");
            Page<RepayScheduleAllPo> page = repayScheduleAllDao.findAll(spec, pageable);
            data = page.getContent();
            searchDto.setTotalPages(page.getTotalPages());
            searchDto.setTotalRecord(page.getTotalElements());
        } else if(EReportType.EXCEL == reportType){
            data = repayScheduleAllDao.findAll(spec);
        }

        List<RepayScheduleDto> dtos = new ArrayList<>();
        data.forEach(repayScheduleAllPo -> {
            RepayScheduleDto repayScheduleDto = ConverterService.convert(repayScheduleAllPo, RepayScheduleDto.class);
            dtos.add(repayScheduleDto);
        });

        searchDto.setList(dtos);

        return searchDto;
    }

    @Override
    public RepayDtlSearchDto getRepayDtlReport(RepayDtlSearchDto searchDto, EReportType reportType) {
        Specification<RepayDtlAllPo> spec = (root, query, cb) -> {
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

        List<RepayDtlAllPo> data = new ArrayList<>();
        if(EReportType.HTML == reportType){
            Pageable pageable = new PageRequest(searchDto.getCurrentPage() - 1, searchDto.getPageSize(), Sort.Direction.ASC, "repayDate");
            Page<RepayDtlAllPo> page = repayDtlAllDao.findAll(spec, pageable);
            data = page.getContent();
            searchDto.setTotalPages(page.getTotalPages());
            searchDto.setTotalRecord(page.getTotalElements());
        } else if(EReportType.EXCEL == reportType){
            data = repayDtlAllDao.findAll(spec);
        }

        List<RepayDtlDto> dtos = new ArrayList<>();
        data.forEach(RepayDtlAllPo -> {
            RepayDtlDto repayDtlDto = ConverterService.convert(RepayDtlAllPo, RepayDtlDto.class);
            LoanMCouponDto loanMCouponDto = new LoanMCouponDto();
            repayDtlDto.setCouponDesc(RepayDtlAllPo.getCouponDesc());
            repayDtlDto.setCouponPrice(RepayDtlAllPo.getCouponPrice());
            dtos.add(repayDtlDto);
        });

        searchDto.setList(dtos);

        return searchDto;
    }
}
