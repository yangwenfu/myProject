package com.xinyunlian.jinfu.backdoor.controller;

import com.xinyunlian.jinfu.bank.dto.BankCardDto;
import com.xinyunlian.jinfu.bank.service.BankService;
import com.xinyunlian.jinfu.common.security.SecurityContext;
import com.xinyunlian.jinfu.common.util.AppConfigUtil;
import com.xinyunlian.jinfu.common.util.JsonUtil;
import com.xinyunlian.jinfu.common.util.OkHttpUtil;
import com.xinyunlian.jinfu.dto.RealAuthResponseDto;
import com.xinyunlian.jinfu.dto.UserRealAuthDto;
import com.xinyunlian.jinfu.external.dto.RequsetInfo;
import com.xinyunlian.jinfu.external.dto.resp.LoanRefundsNotify;
import com.xinyunlian.jinfu.external.service.ATZApiService;
import com.xinyunlian.jinfu.loan.dto.req.LoanSearchListDto;
import com.xinyunlian.jinfu.loan.dto.resp.LoanDtlDto;
import com.xinyunlian.jinfu.loan.dto.resp.LoanSearchResultDto;
import com.xinyunlian.jinfu.loan.service.LoanQueryService;
import com.xinyunlian.jinfu.loan.service.LoanService;
import com.xinyunlian.jinfu.repay.service.RepayService;
import com.xinyunlian.jinfu.service.UserRealAuthService;
import com.xinyunlian.jinfu.user.dto.UserInfoDto;
import com.xinyunlian.jinfu.user.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.*;

/**
 * @author willwang
 *         专门用来查询一些后台数据，解决线上问题用的
 */
@RestController
@RequestMapping("backdoor")
public class BackDoorController {

    private static final Logger LOGGER = LoggerFactory.getLogger(BackDoorController.class);


    @Autowired
    private UserRealAuthService realAuthService;

    @Autowired
    private UserService userService;

    @Autowired
    private BankService bankService;

    @Autowired
    private LoanQueryService loanQueryService;

    @Autowired
    private LoanService loanService;

    @Autowired
    private ATZApiService atzApiService;

    @Autowired
    private RepayService repayService;

    /**
     * 实名认证验证
     *
     * @return
     */
    @RequestMapping(value = "/realauth", method = RequestMethod.GET)
    public String realAuth(@RequestParam String name, @RequestParam String idCardNo,
                           @RequestParam String mobile, @RequestParam String bankCardNo, HttpServletResponse response) {

        String mgtUserId = SecurityContext.getCurrentOperatorId();

        LOGGER.info("user:{},method:realauth,name:{},idCardNo:{},mobile:{}, bankCardNo:{}", mgtUserId, name, idCardNo, mobile, bankCardNo);

        UserRealAuthDto userRealAuthDto = new UserRealAuthDto();
        userRealAuthDto.setName(name);
        userRealAuthDto.setIdCardNo(idCardNo);
        userRealAuthDto.setPhone(mobile);
        userRealAuthDto.setBankCardNo(bankCardNo);
        RealAuthResponseDto realAuthResponseDto = realAuthService.realAuthWithResponse(userRealAuthDto);

        LOGGER.info("realAuthResponseDto:{}", realAuthResponseDto);

        this.print(response, realAuthResponseDto.toString());
        return null;
    }

    /**
     * 获取用户所有银行卡,贷款
     */
    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public String user(@RequestParam String mobile, HttpServletResponse response){
        StringBuilder sb = new StringBuilder();


        String mgtUserId = SecurityContext.getCurrentOperatorId();

        LOGGER.info("user:{},method:user,,mobile:{}", mgtUserId, mobile);

        sb.append("用户信息\n");
        UserInfoDto userInfoDto = userService.findUserByMobile(mobile);
        sb.append(userInfoDto).append("\n");

        sb.append("银行卡信息\n");
        List<BankCardDto> bankCardDtos = bankService.findByUserId(userInfoDto.getUserId());
        if(!bankCardDtos.isEmpty()){
            for (BankCardDto bankCardDto : bankCardDtos) {
                sb.append("卡编号:").append(bankCardDto.getBankCardId()).append(",卡号:").append(bankCardDto.getBankCardNo()).append("\n");
            }
        }

        sb.append("贷款信息\n");
        LoanSearchListDto searchDto = new LoanSearchListDto();
        Set<String> userIds = new HashSet<>();
        userIds.add(userInfoDto.getUserId());
        searchDto.setUserIds(userIds);
        searchDto = loanQueryService.loanList(searchDto);

        List<LoanSearchResultDto> rsList = searchDto.getList();

        for (LoanSearchResultDto loanSearchResultDto : rsList) {
            LoanDtlDto loanDtlDto = loanService.get(loanSearchResultDto.getLoanId());
            sb.append("贷款单号:").append(loanDtlDto.getLoanId()).append(",代扣卡编号:").append(loanDtlDto.getBankCardId()).append("\n");
        }

        this.print(response, sb.toString());
        return null;
    }

    @RequestMapping(value = "/changecard", method = RequestMethod.GET)
    public String changeCard(@RequestParam String loanId, @RequestParam String bankCardNo, HttpServletResponse response){
        String changeCardTemplate = "update jinfu_loan_pro.fp_loan_dtl a set a.BANK_CARD_ID = {0} where a.LOAN_ID = {1};";
        String rs = MessageFormat.format(changeCardTemplate, bankCardNo, "'" + loanId + "'");
        this.print(response, rs);
        return null;
    }

    @RequestMapping(value = "transferFailed", method = RequestMethod.GET)
    public String transferFailed(@RequestParam String applId, @RequestParam BigDecimal capital, HttpServletResponse response){

        String template = "-- 使用额度还原\n" +
                "update jinfu_loan_pro.ac_acct a set a.CREDIT_LINE_USED = a.CREDIT_LINE_USED - {2}\n" +
                "where a.ACCT_NO = (select b.ACCT_NO from jinfu_loan_pro.fp_loan_dtl b where b.LOAN_ID = {1});\n" +
                "-- 删除贷款信息\n" +
                "delete from jinfu_loan_pro.fp_loan_dtl where LOAN_ID = {1};\n" +
                "-- 删除还款计划\n" +
                "delete from jinfu_loan_pro.fp_repay_schd where LOAN_ID = {1};\n" +
                "-- 删除流水\n" +
                "delete from jinfu_loan_pro.ac_pay_recv_ord  where BIZ_ID = {1};\n" +
                "-- 申请信息还原\n" +
                "update jinfu_loan_pro.fp_loan_appl a set a.APPL_STATUS = '02' where a.APPL_ID = {0};\n" +
                "-- 删除活动信息\n" +
                "delete from jinfu_loan_pro.fp_loan_promo where loan_id = {1};";

        String param1 = "'" + applId + "'";
        String param2 = "'L" + applId + "'";

        String rs = MessageFormat.format(template, param1, param2, capital);
        this.print(response, rs);
        return null;
    }


    private void print(HttpServletResponse response, String message){
        response.setContentType("text/html;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        try {
            response.getWriter().print(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping(value = "/atz/test/callback", method = RequestMethod.POST)
    public String testCallback(HttpServletResponse response, @RequestBody LoanRefundsNotify notify) throws IOException {

        String domain = "http://yltest.xylpay.com/jinfu/web/";
        String url = "loan/callback/aitz";

        String channelId = "34";
        String method = "loanRefundResultNotification";
        String ver = "2.0";
        String signType = "MD5";

        RequsetInfo requsetInfo = new RequsetInfo();

        requsetInfo.setChannelId(channelId);
        requsetInfo.setMethod(method);
        requsetInfo.setParams(notify);
        requsetInfo.setSignType(signType);
        requsetInfo.setVer(ver);
        String json = atzApiService.signResponese(requsetInfo);

        if(AppConfigUtil.isDevEnv()){
            domain = "http://172.21.83.134/jinfu/web/";
        }

        url = domain + url;

        String result = "非测试环境，无法执行";
        if(AppConfigUtil.isUnProdEnv()){
            result = OkHttpUtil.postBytes(url, json.getBytes(), false);
        }

        this.print(response, result);
        return null;
    }

    @RequestMapping(value = "/atz/test/repay", method = RequestMethod.GET)
    public void testRepay(HttpServletResponse response, @RequestParam String loanId){
        repayService.externalInAdvance(loanId);
        this.print(response, "SUCCESS");
    }

}
