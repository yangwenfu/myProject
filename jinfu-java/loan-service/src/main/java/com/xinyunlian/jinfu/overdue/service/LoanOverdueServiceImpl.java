package com.xinyunlian.jinfu.overdue.service;

import com.xinyunlian.jinfu.common.converter.ConverterService;
import com.xinyunlian.jinfu.common.enums.EErrorCode;
import com.xinyunlian.jinfu.common.exception.BizServiceException;
import com.xinyunlian.jinfu.common.util.AmtUtils;
import com.xinyunlian.jinfu.common.util.LoanUtils;
import com.xinyunlian.jinfu.loan.dao.LoanDtlDao;
import com.xinyunlian.jinfu.loan.dto.resp.LoanDtlDto;
import com.xinyunlian.jinfu.loan.entity.LoanDtlPo;
import com.xinyunlian.jinfu.loan.enums.ELoanStat;
import com.xinyunlian.jinfu.loan.enums.ERepayMode;
import com.xinyunlian.jinfu.loan.service.InnerApplService;
import com.xinyunlian.jinfu.overdue.dto.OverdueMonthDetailDto;
import com.xinyunlian.jinfu.overdue.dto.OverdueMonthPreviewDto;
import com.xinyunlian.jinfu.product.dto.LoanProductDetailDto;
import com.xinyunlian.jinfu.schedule.dto.ScheduleDto;
import com.xinyunlian.jinfu.schedule.enums.EScheduleStatus;
import com.xinyunlian.jinfu.schedule.service.InnerScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Willwang on 2017/1/11.
 */
@Service
public class LoanOverdueServiceImpl implements LoanOverdueService{

    @Autowired
    private InnerApplService innerApplService;

    @Autowired
    private LoanDtlDao loanDtlDao;

    @Autowired
    private InnerScheduleService innerScheduleService;

    @Autowired
    private InnerLoanOverdueService innerLoanOverdueService;

    /**
     * 等额本息逾期情况
     * @param loanId
     * @return
     * @throws BizServiceException
     */
    @Override
    public List<OverdueMonthDetailDto> month(String loanId) throws BizServiceException {
        return innerLoanOverdueService.month(loanId);
    }

    /**
     * 等额本息逾期还款预览
     * @param loanId
     * @param amt
     * @param date 还款日
     * @return
     * @throws BizServiceException
     */
    @Override
    public List<OverdueMonthPreviewDto> monthPreview(String loanId, BigDecimal amt, Date date) throws BizServiceException {
        return innerLoanOverdueService.monthPreview(loanId, amt, date);
    }

    @Override
    public BigDecimal all(String loanId) throws BizServiceException {
        BigDecimal sum = BigDecimal.ZERO;

        Object[] rs = this.checkAndGet(loanId, ERepayMode.ALL);
        LoanDtlDto loan = (LoanDtlDto) rs[0];

        switch(loan.getRepayMode()){
            case INTR_PER_DIEM:
                sum = AmtUtils.positive(loan.getLoanAmt().subtract(loan.getRepayedAmt()));
                break;
            case MONTH_AVE_CAP_PLUS_INTR:
                List<OverdueMonthDetailDto> list = this.month(loanId);
                for (OverdueMonthDetailDto overdueMonthDetailDto : list) {
                    sum = sum.add(overdueMonthDetailDto.getShould());
                }
                break;
            default:
                break;
        }

        return sum;
    }

    @Override
    public List<LoanDtlDto> listOverdues(int currentPage, int pageSize) {

        Specification<LoanDtlPo> spec = (root, query, cb) -> {
            Predicate predicate = cb.conjunction();
            List<Expression<Boolean>> expressions = predicate.getExpressions();
            expressions.add(cb.equal(root.get("loanStat"), ELoanStat.OVERDUE));
            return predicate;
        };

        Pageable pageable = new PageRequest(currentPage - 1, pageSize, Sort.Direction.DESC, "loanId");
        Page<LoanDtlPo> page = loanDtlDao.findAll(spec, pageable);

        if(page.getContent() != null){
            return ConverterService.convertToList(page.getContent(), LoanDtlDto.class);
        }

        return new ArrayList<>();
    }

    /**
     * 数据校验并返回贷款和产品信息, [0]对应贷款, [1]对应产品
     * @param loanId
     * @param repayMode
     * @return
     * @throws BizServiceException
     */
    private Object[] checkAndGet(String loanId, ERepayMode repayMode) throws BizServiceException {

        Object[] rs = new Object[2];

        LoanDtlDto loan = ConverterService.convert(loanDtlDao.findOne(loanId), LoanDtlDto.class);

        if(loan == null){
            throw new BizServiceException(EErrorCode.TECH_DATA_INVALID, String.format("%s 贷款不存在", loanId));
        }

        if(repayMode != ERepayMode.ALL && loan.getRepayMode() != repayMode){
            throw new BizServiceException(EErrorCode.TECH_DATA_INVALID, String.format("%s 还款方式错误", loanId));
        }

        LoanProductDetailDto product = innerApplService.getProduct(loan.getApplId());

        rs[0] = loan;
        rs[1] = product;

        return rs;
    }
}
