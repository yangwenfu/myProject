package com.xinyunlian.jinfu.loan;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.xinyunlian.jinfu.bank.dto.BankCardDto;
import com.xinyunlian.jinfu.bank.service.BankService;
import com.xinyunlian.jinfu.common.sms.SmsUtil;
import com.xinyunlian.jinfu.loan.dto.resp.LoanDtlDto;
import com.xinyunlian.jinfu.loan.service.LoanService;
import com.xinyunlian.jinfu.router.dto.FinanceSourceDto;
import com.xinyunlian.jinfu.router.enums.EFinanceSourceType;
import com.xinyunlian.jinfu.router.service.FinanceSourceService;
import com.xinyunlian.jinfu.schedule.dto.ScheduleDto;
import com.xinyunlian.jinfu.schedule.enums.EScheduleStatus;
import com.xinyunlian.jinfu.schedule.service.ScheduleService;
import com.xinyunlian.jinfu.user.dto.UserInfoDto;
import com.xinyunlian.jinfu.user.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by JL on 2016/7/25.
 */
@Component
public class RepaySmsJob {
    private final static Logger LOGGER = LoggerFactory.getLogger(RepaySmsJob.class);

    @Autowired
    private ScheduleService scheduleService;

    @Autowired
    private BankService bankService;

    @Autowired
    private LoanService loanService;

    @Autowired
    private UserService userService;

    @Autowired
    private FinanceSourceService financeSourceService;

    /**
     * execute at 09:00
     */
    @Scheduled(cron = "0 0 9 * * ?")
    public void repaySms() {

        LOGGER.info("job:repaySms,begin");

        List<String> dueDates = new ArrayList<>();
        dueDates.add(LocalDate.now().toString());
        dueDates.add(LocalDate.now().plusDays(2).toString());
        List<ScheduleDto> list = scheduleService.findByStatusAndDueDate(EScheduleStatus.NOTYET, dueDates);
        for (ScheduleDto scheduleDto : list) {
            try {
                Map<String, String> params = new HashMap<>();
                params.put("company", "云联金服");
                params.put("dueDate", scheduleDto.getDueDate());
                params.put("totalAmt", scheduleDto.getShouldCapital().add(scheduleDto.getShouldInterest()).toString());
                params.put("intrAmt", scheduleDto.getShouldInterest().toString());
                LoanDtlDto loanDtlDto = loanService.get(scheduleDto.getLoanId());

                Integer financeSourceId = loanDtlDto.getFinanceSourceId() == null ? 1 : loanDtlDto.getFinanceSourceId();
                FinanceSourceDto financeSourceDto = financeSourceService.findById(financeSourceId);
                if(financeSourceDto.getType() != EFinanceSourceType.OWN){
                    LOGGER.info("job:repaySms,loan_id:{} is not own", loanDtlDto.getLoanId());
                    continue;
                }

                BankCardDto bankCardDto = bankService.getBankCard(loanDtlDto.getBankCardId());
                String bankCardNo = bankCardDto.getBankCardNo();
                params.put("bankCardNo", bankCardNo.substring(bankCardNo.length() - 4, bankCardNo.length()));

                UserInfoDto userInfoDto = userService.findUserByUserId(loanDtlDto.getUserId());
                if(userInfoDto == null || StringUtils.isEmpty(userInfoDto.getMobile())){
                    return;
                }

                SmsUtil.send("135", params, userInfoDto.getMobile());
                LOGGER.info("job:repaySms,schedule_id:{}", scheduleDto.getScheduleId());
            } catch (Exception e) {
                LOGGER.error("还款计划{}提醒短信发送失败", scheduleDto.getScheduleId(), e);
            }
        }

        LOGGER.info("job:repaySms,end");
    }


}
