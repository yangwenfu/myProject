package com.xinyunlian.jinfu.calc.service;

import com.google.common.collect.Lists;
import com.xinyunlian.jinfu.calc.dto.LoanCalcDto;
import com.xinyunlian.jinfu.common.exception.BizServiceException;
import com.xinyunlian.jinfu.schedule.dao.ScheduleDao;
import com.xinyunlian.jinfu.schedule.entity.SchedulePo;
import com.xinyunlian.jinfu.schedule.enums.EScheduleStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by jl0629 on 2017/09/06.
 */
@Service
public class LoanAvgCapAvgIntrCalcServiceImpl implements LoanAvgCapAvgIntrCalcService {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoanAvgCapAvgIntrCalcServiceImpl.class);

    @Autowired
    private ScheduleDao scheduleDao;

//    @Autowired
//    private OverdueInfoDao overdueInfoDao;

    @Override
    public LoanCalcDto repay(String userId, String loanId) throws BizServiceException {
        List<SchedulePo> schedulePos = scheduleDao.findByLoanIdAndScheduleStatusInOrderBySeqNoAsc(loanId,
                Lists.newArrayList(EScheduleStatus.NOTYET, EScheduleStatus.OVERDUE));
        //TODO 剔除未到期计划
        return buildLoanCalc(loanId, schedulePos);
    }

    @Override
    public LoanCalcDto repayAll(String userId, String loanId) throws BizServiceException {
        List<SchedulePo> schedulePos = scheduleDao.findByLoanIdAndScheduleStatusInOrderBySeqNoAsc(loanId,
                Lists.newArrayList(EScheduleStatus.NOTYET, EScheduleStatus.OVERDUE));
        return buildLoanCalc(loanId, schedulePos);
    }

    private LoanCalcDto buildLoanCalc(String loanId, List<SchedulePo> schedulePos) {
        //还款计划计算
        BigDecimal capital = BigDecimal.ZERO;
        BigDecimal interest = BigDecimal.ZERO;
        BigDecimal fee = BigDecimal.ZERO;
        for (SchedulePo schedulePo : schedulePos) {
            capital.add(schedulePo.getShouldCapital());
            fee.add(schedulePo.getShouldFee());
            interest.add(schedulePo.getShouldInterest());
        }
        //罚息计算
        BigDecimal fine = BigDecimal.ZERO;
        int days = 0;
//        OverdueInfoPo overdueInfoPo = overdueInfoDao.findByLoanIdAndOverdueInfoStatus(loanId, EOverdueInfoStatus.NORMAL);
//        if (overdueInfoPo != null) {
//            days = DateHelper.betweenDaysNew(DateHelper.getDate(overdueInfoPo.getStartDate()), new Date());
//            fine = overdueInfoPo.getOverdueAmt().multiply(overdueInfoPo.getFineRt()).multiply(new BigDecimal(days)).subtract(overdueInfoPo.getRemitAmt());
//        }
        LoanCalcDto loanCalcDto = new LoanCalcDto();
        loanCalcDto.setCapital(capital);
        loanCalcDto.setInterest(interest);
        loanCalcDto.setFee(fee);
        loanCalcDto.setFine(fine);
        loanCalcDto.setFineDays(days);
        return loanCalcDto;
    }
}
