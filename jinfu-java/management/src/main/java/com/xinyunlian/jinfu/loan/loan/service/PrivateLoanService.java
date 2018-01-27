package com.xinyunlian.jinfu.loan.loan.service;

import com.xinyunlian.jinfu.bank.dto.BankCardDto;
import com.xinyunlian.jinfu.bank.service.BankService;
import com.xinyunlian.jinfu.car.dto.UserCarDto;
import com.xinyunlian.jinfu.car.service.UserCarService;
import com.xinyunlian.jinfu.cashier.dto.LoanPayDto;
import com.xinyunlian.jinfu.cashier.dto.ProcessRetDto;
import com.xinyunlian.jinfu.cashier.service.LoanPayService;
import com.xinyunlian.jinfu.common.converter.ConverterService;
import com.xinyunlian.jinfu.common.dto.ResultDtoFactory;
import com.xinyunlian.jinfu.common.util.*;
import com.xinyunlian.jinfu.contract.dto.UserContractDto;
import com.xinyunlian.jinfu.contract.enums.ECntrctTmpltType;
import com.xinyunlian.jinfu.dealer.service.DealerUserOrderService;
import com.xinyunlian.jinfu.dto.PayRecvReqDto;
import com.xinyunlian.jinfu.dto.PayRecvResultDto;
import com.xinyunlian.jinfu.enums.PayRecvResult;
import com.xinyunlian.jinfu.house.dto.UserHouseDto;
import com.xinyunlian.jinfu.house.service.UserHouseService;
import com.xinyunlian.jinfu.loan.appl.service.PrivateLoanApplService;
import com.xinyunlian.jinfu.loan.dto.LoanApplUserDto;
import com.xinyunlian.jinfu.loan.dto.RepayDtlDto;
import com.xinyunlian.jinfu.loan.dto.req.LoanPayReq;
import com.xinyunlian.jinfu.loan.dto.req.LoanSearchListDto;
import com.xinyunlian.jinfu.loan.dto.resp.LoanApplDto;
import com.xinyunlian.jinfu.loan.dto.resp.LoanDtlDto;
import com.xinyunlian.jinfu.loan.dto.resp.LoanSearchResultDto;
import com.xinyunlian.jinfu.loan.dto.resp.apply.LoanApplyListEachDto;
import com.xinyunlian.jinfu.loan.enums.ELoanStat;
import com.xinyunlian.jinfu.loan.enums.ERepayStatus;
import com.xinyunlian.jinfu.loan.enums.ETransferStat;
import com.xinyunlian.jinfu.loan.service.LoanApplService;
import com.xinyunlian.jinfu.loan.service.LoanApplUserService;
import com.xinyunlian.jinfu.loan.service.LoanQueryService;
import com.xinyunlian.jinfu.loan.service.LoanService;
import com.xinyunlian.jinfu.pay.dto.PayRecvOrdDto;
import com.xinyunlian.jinfu.pay.enums.EOrdStatus;
import com.xinyunlian.jinfu.pay.enums.EPrType;
import com.xinyunlian.jinfu.pay.service.PayRecvOrdService;
import com.xinyunlian.jinfu.repay.service.RepayService;
import com.xinyunlian.jinfu.risk.dto.resp.UserCreditInfoDto;
import com.xinyunlian.jinfu.risk.service.UserCreditService;
import com.xinyunlian.jinfu.router.dto.FinanceSourceDto;
import com.xinyunlian.jinfu.router.enums.EFinanceSourceType;
import com.xinyunlian.jinfu.router.service.FinanceSourceService;
import com.xinyunlian.jinfu.schedule.service.ScheduleService;
import com.xinyunlian.jinfu.service.toDto.PayRecvToDTOService;
import com.xinyunlian.jinfu.spider.dto.resp.RiskUserInfoDto;
import com.xinyunlian.jinfu.spider.service.RiskUserInfoService;
import com.xinyunlian.jinfu.store.dto.StoreInfDto;
import com.xinyunlian.jinfu.store.service.StoreService;
import com.xinyunlian.jinfu.user.dto.UserBankAcctDto;
import com.xinyunlian.jinfu.user.dto.UserBankAcctTrdDto;
import com.xinyunlian.jinfu.user.dto.UserInfoDto;
import com.xinyunlian.jinfu.user.dto.UserLinkmanDto;
import com.xinyunlian.jinfu.user.dto.ext.UserExtAllDto;
import com.xinyunlian.jinfu.user.service.*;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

/**
 * Created by JL on 2016/9/29.
 */
@Service
public class PrivateLoanService {

    @Autowired
    private LoanPayService loanPayService;

    @Autowired
    private LoanQueryService loanQueryService;

    @Autowired
    private RepayService repayService;

    @Autowired
    private PrivateLoanApplService privateLoanApplService;

    @Autowired
    private LoanService loanService;

    @Autowired
    private ScheduleService scheduleService;

    @Autowired
    private UserService userService;

    @Autowired
    private FinanceSourceService financeSourceService;

    @Autowired
    private BankService bankService;

    private static final Logger LOGGER = LoggerFactory.getLogger(PrivateLoanService.class);

    /**
     * 贷款列表
     *
     * @param search
     * @return
     */
    public LoanSearchListDto list(LoanSearchListDto search) {
        Set<String> userIds = new HashSet<>();

        Set<String> usernameCondition = privateLoanApplService.getUserUserIds(search.getUserName());
        Set<String> tobaccoCondition = privateLoanApplService.getTobaccoUserIds(search.getTobacco());

        userIds.addAll(usernameCondition);
        userIds.addAll(tobaccoCondition);

        search.setUserIds(userIds);

        LoanSearchListDto rs = loanQueryService.loanList(search);
        List<LoanSearchResultDto> list = rs.getList();

        //补全贷款用户数据
        list = this.completeUser(list);
        //补全结清数据
        list = this.completeRepayDate(list);
        rs.setList(list);

        //补充资金路由信息
        rs = this.completeFinanceSourceType(rs);

        return rs;
    }

    /**
     * 资金路由信息补充
     *
     * @return
     */
    private LoanSearchListDto completeFinanceSourceType(LoanSearchListDto searchListDto) {
        List<LoanSearchResultDto> list = searchListDto.getList();

        if (list.size() <= 0) {
            return searchListDto;
        }

        List<FinanceSourceDto> financeSourceDtos = financeSourceService.getAll();
        Map<Integer, EFinanceSourceType> map = new HashMap<>();
        financeSourceDtos.forEach(financeSourceDto -> map.put(financeSourceDto.getId(), financeSourceDto.getType()));

        list.forEach(item -> {
            if (item.getFinanceSourceId() == null) {
                item.setFinanceSourceType(EFinanceSourceType.OWN);
            } else {
                item.setFinanceSourceType(
                        map.get(item.getFinanceSourceId()) != null ?
                                map.get(item.getFinanceSourceId()) : EFinanceSourceType.OWN
                );
            }
        });

        searchListDto.setList(list);

        return searchListDto;
    }

    /**
     * 补全用户信息
     *
     * @param list
     * @return
     */
    public List<LoanSearchResultDto> completeUser(List<LoanSearchResultDto> list) {

        Set<String> userIds = this.extractUserIds(list);

        List<UserInfoDto> users = new ArrayList<>();

        if (userIds.size() > 0) {
            users = userService.findUserByUserId(userIds);
        }
        Map<String, UserInfoDto> map = new HashMap<>();

        for (UserInfoDto user : users) {
            map.put(user.getUserId(), user);
        }

        for (LoanSearchResultDto item : list) {
            UserInfoDto user = map.get(item.getUserId());
            item.setUserName("");
            item.setMobile("");
            if (user != null) {
                item.setUserName(user.getUserName());
                item.setMobile(user.getMobile());
            }
        }

        return list;
    }

    /**
     * 补全结清日期
     *
     * @param list
     * @return
     */
    public List<LoanSearchResultDto> completeRepayDate(List<LoanSearchResultDto> list) {

        Set<String> loanIds = this.extractLoanIds(list);

        List<RepayDtlDto> repays = repayService.findByLoanIds(loanIds);

        Map<String, List<RepayDtlDto>> map = new HashMap<>();

        for (RepayDtlDto repay : repays) {
            List<RepayDtlDto> ll = map.get(repay.getLoanId());
            if (ll == null) {
                ll = new ArrayList<>();
            }
            ll.add(repay);
            map.put(repay.getLoanId(), ll);
        }

        for (LoanSearchResultDto item : list) {
            item.setRepayDate("");
            if (item.getLoanStat() != ELoanStat.PAID) {
                continue;
            }
            List<RepayDtlDto> repayDtlDtos = map.get(item.getLoanId());
            if (CollectionUtils.isNotEmpty(repayDtlDtos)) {
                item.setRepayDate(this.getRepayDate(repayDtlDtos));
            }
        }

        return list;
    }

    /**
     * 根据还款记录列表寻找最近一次成功的记录
     *
     * @param repays
     * @return
     */
    private String getRepayDate(List<RepayDtlDto> repays) {
        for (RepayDtlDto repay : repays) {
            if (repay.getStatus() == ERepayStatus.SUCCESS) {
                return DateHelper.formatDate(repay.getRepayDateTime(), DateHelper.SIMPLE_DATE_YMD_CN);
            }
        }
        return "";
    }

    /**
     * 从查询列表中提取userId
     *
     * @param list
     * @return
     */
    private Set<String> extractUserIds(List<LoanSearchResultDto> list) {
        Set<String> rs = new HashSet<>();
        for (LoanSearchResultDto item : list) {
            if (StringUtils.isNotEmpty(item.getUserId())) {
                rs.add(item.getUserId());
            }
        }
        return rs;
    }

    /**
     * 从查询列表中提取loanIds
     *
     * @param list
     * @return
     */
    private Set<String> extractLoanIds(List<LoanSearchResultDto> list) {
        Set<String> rs = new HashSet<>();
        for (LoanSearchResultDto item : list) {
            if (StringUtils.isNotEmpty(item.getLoanId())) {
                rs.add(item.getLoanId());
            }
        }
        return rs;
    }

    /**
     * 会真实发起交易
     *
     * @param loanDtlDto
     */
    public void pay(LoanDtlDto loanDtlDto) {
        BankCardDto bankCardDto = bankService.getBankCard(loanDtlDto.getBankCardId());
        UserInfoDto userInfoDto = userService.findUserByUserId(loanDtlDto.getUserId());

        LoanPayReq loanPayReq = ConverterService.convert(bankCardDto, LoanPayReq.class);
        loanPayReq.setLoanId(loanDtlDto.getLoanId());
        loanPayReq.setUserMobile(userInfoDto.getMobile());
        loanPayReq.setPrType(EPrType.PAY);
        loanPayReq.setBankCardMobile(bankCardDto.getMobileNo());
        loanPayReq.setLoanDtlDto(loanDtlDto);
        loanService.pay(loanPayReq);
    }

    /**
     * 虚拟交易，只会更改状态，不会进行交易
     */
    public void dummyPay(LoanDtlDto loanDtlDto) {
        BankCardDto bankCardDto = bankService.getBankCard(loanDtlDto.getBankCardId());
        UserInfoDto userInfoDto = userService.findUserByUserId(loanDtlDto.getUserId());

        LoanPayReq loanPayReq = ConverterService.convert(bankCardDto, LoanPayReq.class);
        loanPayReq.setLoanId(loanDtlDto.getLoanId());
        loanPayReq.setUserMobile(userInfoDto.getMobile());
        loanPayReq.setPrType(EPrType.DUMMY_PAY);
        loanPayReq.setBankCardMobile(bankCardDto.getMobileNo());
        loanPayReq.setLoanDtlDto(loanDtlDto);
        PayRecvOrdDto payRecvOrdDto = loanService.pay(loanPayReq);

        PayRecvResult payRecvResult = PayRecvResult.SUCCESS;

        payRecvOrdDto.setOrdStatus(convertOrdStatus(payRecvResult));
        payRecvOrdDto.setRetCode(payRecvResult.getRetCode());
        payRecvOrdDto.setTrxAmt(loanDtlDto.getLoanAmt());
        payRecvOrdDto.setRetMsg(payRecvResult.getRetMsg());

        loanDtlDto = loanService.payCallback(loanDtlDto, payRecvOrdDto);

        if (loanDtlDto.getTransferStat() == ETransferStat.SUCCESS) {
            scheduleService.generate(loanDtlDto.getUserId(), loanDtlDto.getApplId());
            loanService.sendPayOkMessage(loanPayReq, loanDtlDto);
        }
    }

    private EOrdStatus convertOrdStatus(PayRecvResult result) {
        if (result == PayRecvResult.SUCCESS) {
            return EOrdStatus.SUCCESS;
        } else if (result == PayRecvResult.FAILED) {
            return EOrdStatus.FAILED;
        }

        return EOrdStatus.PROCESS;
    }

}
