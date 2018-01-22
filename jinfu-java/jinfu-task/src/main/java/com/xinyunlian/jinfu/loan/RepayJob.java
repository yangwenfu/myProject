package com.xinyunlian.jinfu.loan;

import com.xinyunlian.jinfu.bank.service.BankService;
import com.xinyunlian.jinfu.cashier.dto.LoanPayDto;
import com.xinyunlian.jinfu.cashier.service.LoanPayService;
import com.xinyunlian.jinfu.common.util.AppConfigUtil;
import com.xinyunlian.jinfu.common.util.JsonUtil;
import com.xinyunlian.jinfu.common.util.NumberUtil;
import com.xinyunlian.jinfu.loan.dto.RepayDtlDto;
import com.xinyunlian.jinfu.loan.dto.resp.LoanDtlDto;
import com.xinyunlian.jinfu.loan.enums.ERepayMode;
import com.xinyunlian.jinfu.loan.enums.ERepayType;
import com.xinyunlian.jinfu.loan.service.LoanApplService;
import com.xinyunlian.jinfu.loan.service.LoanService;
import com.xinyunlian.jinfu.pay.enums.EPrType;
import com.xinyunlian.jinfu.pay.service.PayRecvOrdService;
import com.xinyunlian.jinfu.product.dto.LoanProductDetailDto;
import com.xinyunlian.jinfu.repay.dto.req.RepayReqDto;
import com.xinyunlian.jinfu.repay.service.RepayService;
import com.xinyunlian.jinfu.router.dto.FinanceSourceDto;
import com.xinyunlian.jinfu.router.enums.EFinanceSourceType;
import com.xinyunlian.jinfu.router.service.FinanceSourceService;
import com.xinyunlian.jinfu.schedule.dto.ScheduleDto;
import com.xinyunlian.jinfu.schedule.service.ScheduleService;
import com.xinyunlian.jinfu.user.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author willwang
 */
@Component
public class RepayJob {

    @Autowired
    private ScheduleService scheduleService;

    @Autowired
    private LoanService loanService;

    @Autowired
    private LoanPayService loanPayService;

    @Autowired
    private RepayService repayService;

    @Autowired
    private LoanApplService loanApplService;

    @Autowired
    private FinanceSourceService financeSourceService;

    private final static Logger LOGGER = LoggerFactory.getLogger(RepayJob.class);

    /**
     * 第一次：10：00,第二次：14：00,第三次：17：00,第四次：22：00
     */
    @Scheduled(cron = "0 0 10,14,17,22 * * ?")
    public void autoRepay() {
        List<ScheduleDto> schedules = scheduleService.getRepayList();

        LOGGER.info("{} schedules auto repay starting", schedules.size());

        for (ScheduleDto schedule : schedules) {

            try{
                LoanDtlDto loanDtlDto = loanService.get(schedule.getLoanId());

                Integer financeSourceId = loanDtlDto.getFinanceSourceId() == null ? 1 : loanDtlDto.getFinanceSourceId();
                FinanceSourceDto financeSourceDto = financeSourceService.findById(financeSourceId);
                if(financeSourceDto.getType() != EFinanceSourceType.OWN){
                    LOGGER.info("job:auto_repay,loan_id:{} is not own", loanDtlDto.getLoanId());
                    continue;
                }


                LoanProductDetailDto product = loanApplService.getProduct(loanDtlDto.getApplId());
                RepayReqDto request = new RepayReqDto();
                request.setLoanId(loanDtlDto.getLoanId());
                request.setPeriod(schedule.getSeqNo());

                ERepayType repayType = null;
                if(product.getRepayMode() == ERepayMode.MONTH_AVE_CAP_PLUS_INTR){
                    repayType = ERepayType.PERIOD;
                }else if(product.getRepayMode() == ERepayMode.INTR_PER_DIEM){
                    repayType = ERepayType.DAY;
                }
                request.setRepayType(repayType);

                repayService.withhold(null, EPrType.RECEIVE, request);

                LOGGER.info(String.format("loan_receive:scheduleId:%s", schedule.getScheduleId()));
            }catch(Exception e){
                LOGGER.warn(String.format("%s auto repay error", schedule.getScheduleId()), e);
            }

            LOGGER.info("auto repay end");
        }
    }
}
