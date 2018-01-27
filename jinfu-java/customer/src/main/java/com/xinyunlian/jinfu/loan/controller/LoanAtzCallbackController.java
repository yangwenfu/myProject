package com.xinyunlian.jinfu.loan.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xinyunlian.jinfu.acct.service.AcctReserveService;
import com.xinyunlian.jinfu.common.converter.ConverterService;
import com.xinyunlian.jinfu.common.dto.BasePagingDto;
import com.xinyunlian.jinfu.common.dto.ResultDtoFactory;
import com.xinyunlian.jinfu.common.enums.EErrorCode;
import com.xinyunlian.jinfu.common.enums.PageEnum;
import com.xinyunlian.jinfu.common.exception.BizServiceException;
import com.xinyunlian.jinfu.common.util.EnumHelper;
import com.xinyunlian.jinfu.common.util.JsonUtil;
import com.xinyunlian.jinfu.external.dto.*;
import com.xinyunlian.jinfu.external.dto.resp.ApplyRiskNotify;
import com.xinyunlian.jinfu.external.dto.resp.LoanApplyNotify;
import com.xinyunlian.jinfu.external.dto.resp.LoanRefundsNotify;
import com.xinyunlian.jinfu.external.enums.CustomerType;
import com.xinyunlian.jinfu.external.enums.MerchantType;
import com.xinyunlian.jinfu.external.enums.PaymentOptionType;
import com.xinyunlian.jinfu.external.enums.TargetMarketType;
import com.xinyunlian.jinfu.external.service.*;
import com.xinyunlian.jinfu.loan.dto.req.LoanApplListDto;
import com.xinyunlian.jinfu.loan.dto.resp.LoanApplDto;
import com.xinyunlian.jinfu.loan.enums.EApplOutAuditType;
import com.xinyunlian.jinfu.loan.enums.EApplOutType;
import com.xinyunlian.jinfu.loan.enums.EApplStatus;
import com.xinyunlian.jinfu.loan.enums.ETermType;
import com.xinyunlian.jinfu.loan.service.AtzRandomTool;
import com.xinyunlian.jinfu.loan.service.LoanApplService;
import com.xinyunlian.jinfu.loan.service.LoanService;
import com.xinyunlian.jinfu.repay.ERefundFlag;
import com.xinyunlian.jinfu.repay.dto.ExternalRepayCallbackDto;
import com.xinyunlian.jinfu.repay.service.RepayService;
import com.xinyunlian.jinfu.mq.DestinationDefine;
import com.xinyunlian.jinfu.mq.sender.QueueSender;
import com.xinyunlian.jinfu.router.dto.FinanceSourceDto;
import com.xinyunlian.jinfu.router.dto.FinanceSourceLoanDto;
import com.xinyunlian.jinfu.router.enums.EFinanceSourceType;
import com.xinyunlian.jinfu.router.service.FinanceSourceService;
import com.xinyunlian.jinfu.schedule.dto.ScheduleDto;
import com.xinyunlian.jinfu.schedule.enums.EScheduleStatus;
import com.xinyunlian.jinfu.schedule.service.ScheduleService;
import com.xinyunlian.jinfu.store.dto.StoreInfDto;
import com.xinyunlian.jinfu.store.service.StoreService;
import com.ylfin.eye.service.ApplyRiskDataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by godslhand on 2017/6/20.
 */
@Controller
@RequestMapping(value = "loan/callback/aitz")
public class LoanAtzCallbackController {

    private final Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private ATZApiService atzApiService;
    @Autowired
    private LoanApplOutService loanApplOutService;
    @Autowired
    private LoanApplOutBankCardService loanApplOutBankCardService;
    @Autowired
    LoanApplService loanApplService;
    @Autowired
    LoanService loanService;
    @Autowired
    LoanApplOutAuditService loanApplOutAuditService;

    @Autowired
    ScheduleService scheduleService;

    @Autowired
    private AcctReserveService acctReserveService;

    @Autowired
    ApplyRiskDataService applyRiskDataService;

    @Autowired
    private FinanceSourceService financeSourceService;

    @Autowired
    private LoanOutRiskService loanOutRiskService;

    @Autowired
    private RepayService repayService;

    @Autowired
    private QueueSender queueSender;

    @Autowired
    private StoreService storeService;

    @ResponseBody
    @RequestMapping(value = "", method = {RequestMethod.POST})
    public Object aitzCallBack(HttpServletRequest request, HttpServletResponse response) throws Exception {
        RequsetInfo<Object> requsetInfo = new RequsetInfo<>();
        try {

            String requestStr = receivePostJson(request);
            logger.info("接收到的数据：{}", requestStr);

            if (!atzApiService.checkSign(requestStr)) {
                throw new BizServiceException(EErrorCode.COMM_INTERNAL_ERROR, "签名错误");
            }


            JSONObject result = JSONObject.parseObject(requestStr);
            if (result == null || result.getJSONObject("params") == null)
                throw new RuntimeException("非法的接口参数");

            JSONObject params = result.getJSONObject("params");
            String method = result.getString("method");
            requsetInfo.setMethod(method);
            if ("loanApplyResultNotification".equals(method)) {
                loanApplyResultNotification(params);
            } else if ("paymentNotification".equals(method)) {
                paymentNotification(params);
            } else if ("bindBankCardResultNotification".equals(method)) {
                bindBankCardResultNotification(params);
            } else if ("getYunLianRiskControllerData".equals(method)) {
                getYunLianRiskControllerData(params, requsetInfo);
            } else if ("loanRefundResultNotification".equals(method)) {
                loanRefundResultNotification(params);
            } else {
                logger.info("未知接口参数： " + method);
            }
            requsetInfo.setStatusCode(200);
        } catch (Exception e) {
            requsetInfo.setStatusCode(500);
            requsetInfo.setErrMsg("系统异常！");
            logger.error("爱投资回调处理异常", e);
        }
        String returnMsg = atzApiService.signResponese(requsetInfo);
        logger.info("返回数据:{}", returnMsg);
        return returnMsg;
    }


    /**
     * 贷款申请结果通知
     *
     * @param result
     */
    private void loanApplyResultNotification(JSONObject result) {
        LoanApplyNotify loanApplyNotify = getParams(LoanApplyNotify.class, result);

        LoanApplOutDto outDto= getSafeLoanApplDto(loanApplyNotify.getLoanId());
        String applId = outDto.getApplId();

        LoanApplDto loanApplDto = loanApplService.get(applId);
        LoanApplOutAuditDto loanApplOutAuditDto = new LoanApplOutAuditDto();
        loanApplOutAuditDto.setApplId(applId);


        boolean isSuccess = loanApplyNotify.getResult() == LoanApplyNotify.RESULT_SUCCCESS;
        loanApplOutAuditDto = convert2LoanApplOutAuditDto(loanApplOutAuditDto, loanApplyNotify);
        loanApplOutAuditDto.setAuditType(isSuccess?EApplOutAuditType.SUCCESS:EApplOutAuditType.REJECT);

        if(loanApplOutAuditService.findByApplId(applId)!=null){
            loanApplOutAuditService.save(loanApplOutAuditDto);
            logger.error("爱投资重复审核,内部编号applyId:{} 外部loanId:{}",applId,loanApplyNotify.getLoanId());
            return;
        }


        loanApplOutAuditService.save(loanApplOutAuditDto);
        if (isSuccess) {
            List<ScheduleDto> scheduleDtos = conver2ScheduleDtos(loanApplyNotify.getRefunds(), applId);
            loanApplDto.setApplStatus(EApplStatus.SUCCEED);
            Integer apprTermLen = 0;
            switch(loanApplDto.getTermType()){
                case DAY:
                    apprTermLen = loanApplyNotify.getLoanTerm();
                    break;
                case MONTH:
                    apprTermLen = ETermType.DAY.getMonth(loanApplyNotify.getLoanTerm());
                    break;
                default:
                    break;
            }

            loanApplDto.setApprTermLen(apprTermLen);
            loanApplDto.setApprAmt(BigDecimal.valueOf(loanApplyNotify.getLoanAmount()));
            sendMQ(outDto,loanApplyNotify.getLoanAmount());
            // 保存还款计划
            scheduleService.saveList(scheduleDtos);
        } else {
            //loanApplDto.setApplStatus(EApplStatus.REJECT);

            //释放申请时占用的额度
            //acctReserveService.unReserve(loanApplDto.getCreditLineRsrvId());
            sendMQ(outDto,0D);
            //爱投资拒绝后自动切换路由到自有资金

            List<StoreInfDto> stores = storeService.findByUserId(loanApplDto.getUserId());
            StoreInfDto store = null;
            if(org.apache.commons.collections.CollectionUtils.isNotEmpty(stores)){
                store = stores.get(0);
            }

            FinanceSourceLoanDto financeSourceLoanDto = new FinanceSourceLoanDto();

            financeSourceLoanDto.setApplId(loanApplDto.getApplId());
            financeSourceLoanDto.setProdId(loanApplDto.getProductId());
            financeSourceLoanDto.setLoanAmt(loanApplDto.getApprAmt());
            if(store != null){
                financeSourceLoanDto.setProvinceId(Integer.parseInt(store.getProvinceId()));
                financeSourceLoanDto.setCityId(Integer.parseInt(store.getCityId()));
            }

            FinanceSourceDto financeSourceDto = financeSourceService.getOwn(financeSourceLoanDto);

            if(financeSourceDto != null){
                loanApplDto.setFinanceSourceId(Integer.parseInt(Long.toString(financeSourceDto.getId())));
            }
            
            loanApplService.save(loanApplDto);
        }
        //贷款申请结果变更
        loanApplService.save(loanApplDto);
        // 审核记录LoanApplOutAuditService.save

    }


    /**
     * 贷款放款完成通知
     *
     * @return
     */
    private void paymentNotification(JSONObject params) {
        String outLoanId = params.getString("loanId");
        Double amount = params.getDouble("amount");
        String date = params.getString("date");
        LoanApplOutDto loanApplOutDto =getSafeLoanApplDto(outLoanId);
        String applyId =loanApplOutDto.getApplId();
        loanService.updateTransferSuccessByApplId(applyId);


        //调用爱投资获取最新还款计划
        try {
           List<RefundDto> refundDtos= JSON.parseArray(params.getJSONArray("refunds").toJSONString(), RefundDto.class);
           List<ScheduleDto> scheduleDtos = conver2ScheduleDtos(refundDtos,applyId);
           scheduleService.saveList(scheduleDtos);
        } catch (Exception e) {
            logger.error("更新爱投资还款计划出错",e);
        }
    }


    /**
     * 成功绑卡结果通知
     *
     * @return
     */

    private void bindBankCardResultNotification(JSONObject result) {
        String idNo = result.getString("idNo");
        String cardNumber = result.getString("cardNumber");
        // 3. 保存银行卡
        loanApplOutBankCardService.save(idNo, cardNumber);
    }


    /**
     * 获取用户风控信息
     *
     * @return
     */

    public void getYunLianRiskControllerData(JSONObject result, RequsetInfo requsetInfo) {
        try {
            String userId = result.getString("userIdResource");

            BasePagingDto basePagingDto = new BasePagingDto();
            basePagingDto.setCurrentPage(1);
            basePagingDto.setPageSize(1);


            LoanApplListDto list = loanApplService.list(userId, basePagingDto);
            String applyId = "";

            if (list != null && !CollectionUtils.isEmpty(list.getList())) {
                applyId = list.getList().get(0).getApplId();
            } else
                return;
            LoanOutRiskDto loanOutRiskDto = loanOutRiskService.findByUserIdAndApplyId(userId, applyId);
            if (loanOutRiskDto != null) { //使用已有的dto
                requsetInfo.setParams(ConverterService.convert(loanOutRiskDto, ApplyRiskNotify.class));
            } else {
                ApplyRiskDto dto = new ApplyRiskDto();
                try {
                    logger.debug("调用风控的applyId:{}",applyId);
                    String riskStr = applyRiskDataService.findByApplyId(applyId);
                    logger.debug("{} 风控数据：{}", applyId, riskStr);
                    dto = JSON.parseObject(riskStr, ApplyRiskDto.class);
                } catch (Exception e) {
                   logger.error("风控数据获取出错",e);
                }
                ApplyRiskNotify applyRiskNotify = getDefaultVal(dto);
                requsetInfo.setParams(applyRiskNotify);
                loanOutRiskDto =ConverterService.convert(applyRiskNotify, LoanOutRiskDto.class);
                loanOutRiskDto.setUserId(userId);
                loanOutRiskDto.setApplyId(applyId);
                loanOutRiskDto.setType(EApplOutType.AITOUZI);
                loanOutRiskService.save(loanOutRiskDto);
            }

        } catch (Exception e) {
            logger.error("风控数据调用出错", e);
        }
    }


    /**
     * 通知资金路由
     * @param outDto
     * @param amount
     */
    private void sendMQ(LoanApplOutDto outDto, Double amount){
        try {
            BigDecimal dAmount = BigDecimal.valueOf(amount);
            LoanRecordMqDto dot = new LoanRecordMqDto(outDto.getApplId(),dAmount, EFinanceSourceType.AITOUZI.getCode());
            queueSender.send(DestinationDefine.LOAN_APPLY_OUT_RESULT, JsonUtil.toJson(dot));
        } catch (Exception e) {
            logger.error("额度消息发送失败",e);
        }
    }

    private String changeDateFormat(String data) {
        if (data == null)
            return data;
        try {

            Date dudate = new SimpleDateFormat("yyyyMMdd").parse(data);

            return new SimpleDateFormat("yyyy-MM-dd").format(dudate);

        } catch (ParseException e) {
            return null;
        }

    }

    private List<ScheduleDto> conver2ScheduleDtos(List<RefundDto> refundDtos, String applId) {
        String loanId = "L" + applId;//生成系统的loanId;
        List<ScheduleDto> scheduleDtos = new ArrayList<>();
        if (CollectionUtils.isEmpty(refundDtos))
            return scheduleDtos;
        for (RefundDto refundDto : refundDtos) {
            ScheduleDto scheduleDto = new ScheduleDto();
            scheduleDto.setDueDate(changeDateFormat(refundDto.getDueDate()));
            scheduleDto.setAcctNo("0");
            scheduleDto.setScheduleStatus(EScheduleStatus.NOTYET);
            scheduleDto.setSeqNo(Integer.valueOf(refundDto.getPeriodNumber()));
            if (refundDto.getDueInterest() != null)
                scheduleDto.setShouldInterest(new BigDecimal(refundDto.getDueInterest()));
            if (refundDto.getDueCapital() != null)
                scheduleDto.setShouldCapital(new BigDecimal(refundDto.getDueCapital()));
            scheduleDto.setScheduleId(loanId + "_" + scheduleDto.getSeqNo());  //系统规则
            scheduleDto.setLoanId(loanId);
            scheduleDtos.add(scheduleDto);
        }
        return scheduleDtos;
    }

    private LoanApplOutAuditDto convert2LoanApplOutAuditDto(LoanApplOutAuditDto loanApplOutAuditDto, LoanApplyNotify loanApplyNotify) {

        if(loanApplyNotify.getLoanTerm()!=null){
            loanApplOutAuditDto.setTermLen(loanApplyNotify.getLoanTerm().toString());
        }
        PaymentOptionType paymentOptionType = convertPayment(loanApplyNotify.getPaymentOption());
        if (paymentOptionType != null) {
            loanApplOutAuditDto.setPaymentOption(paymentOptionType);
        }
        loanApplOutAuditDto.setType(EApplOutType.AITOUZI);
        if (loanApplyNotify.getLoanAmount() != null)
            loanApplOutAuditDto.setLoanAmt(new BigDecimal(String.valueOf(loanApplyNotify.getLoanAmount())));
        if (loanApplyNotify.getCommissions() != null)
            loanApplOutAuditDto.setCommissions(new BigDecimal(String.valueOf(loanApplyNotify.getCommissions())));
        loanApplOutAuditDto.setReason(loanApplyNotify.getReason());
        return loanApplOutAuditDto;
    }

    private PaymentOptionType convertPayment(Integer num) {
        for (PaymentOptionType paymentOptionType : PaymentOptionType.values()) {
            if (paymentOptionType.getCode().equals(String.valueOf(num))) {
                return paymentOptionType;
            }
        }
        return null;
    }


    private ApplyRiskNotify getDefaultVal(ApplyRiskDto dto) {
        ApplyRiskNotify notify = new ApplyRiskNotify();
        if (dto == null){
            dto = new ApplyRiskDto();
        }
         notify.setMerchantLevel(getSafeInteger(dto.getBizInfo01(),
                 new Random().nextInt(9) + 6));//６到１４之间的数字
        notify.setMerchantType(getSafeEnums(dto.getBizInfo04(), MerchantType.shop, MerchantType.class));
        notify.setTargetMarketType(getSafeEnums(dto.getBizInfo05(), TargetMarketType.village, TargetMarketType.class));
        notify.setCustomerType(getSafeEnums(dto.getBizInfo06(), CustomerType.retail, CustomerType.class));
        notify.setOperationYears(getSafeInteger(dto.getBizInfo07(), 1));
        notify.setSameOwnerFlag(Boolean.valueOf(dto.getBizInfo12()) ? 1 : 2);
        notify.setSameArressFlag(Boolean.valueOf(dto.getBizInfo14()) ? 1 : 2);
        notify.setSameContactFlag(Boolean.valueOf(dto.getBizInfo13()) ? 1 : 2);
        notify.setHas2WeekOrderData(Boolean.valueOf(dto.getBizInfo26())? 1 : 2);
        notify.setActiveMonthsInLastYear(
                AtzRandomTool.randomMothLastYear(dto.getBizInfo08()));

        notify.setActiveMonthsInLastQuarter(AtzRandomTool.randomInteger(2, 3));

        notify.setAverageMonthlyAmount(AtzRandomTool.randomMonthAmout(dto.getBizInfo09()));

        notify.setVariance(AtzRandomTool.randomVariance(dto.getBizInfo10()));

        notify.setActiveDegree(AtzRandomTool.randomVariance(dto.getBizInfo11()));


        return notify;
    }



    private Integer getSafeEnums(String str, PageEnum pageEnum, Class clazz) {
        PageEnum pEnum = (PageEnum) translate(clazz, str);
        String code = pEnum == null ? pageEnum.getCode() : pEnum.getCode();
        return Integer.valueOf(code);
    }

    private Integer getSafeInteger(String str, int defaultValue) {
        try {
          return   Integer.valueOf(str);
        } catch (Exception e) {
            return defaultValue;
        }
    }


    private  <T extends Enum<T>> T translate(Class<T> clazz, String text) {
        if(text == null) {
            return null;
        } else {
            try {
                Method m = clazz.getDeclaredMethod("getText", new Class[0]);
                Iterator var3 = EnumHelper.inspectConstants(clazz).iterator();

                while(var3.hasNext()) {
                    T t = (T)var3.next();
                    if(text.equals(m.invoke(t, new Object[0]))) {
                        return t;
                    }
                }
            } catch (Exception var5) {
                logger.error("failed to translate code {} into object of type {}", text, clazz);
            }

            return null;
        }
    }
    /**
     * 还款结果通知
     *
     * @return
     */

    public void loanRefundResultNotification(JSONObject result) {

        LoanRefundsNotify notify = getParams(LoanRefundsNotify.class, result);
        String applyId = getSafeLoanApplDto(notify.getLoanId()).getApplId();
        ExternalRepayCallbackDto callbackDto=convert2ReplyDto(notify);
        callbackDto.setApplId(applyId);
        callbackDto.setPeriod(notify.getPeriodNumber());
        repayService.externalRepayCallback(callbackDto);
    }

    private ExternalRepayCallbackDto convert2ReplyDto(LoanRefundsNotify notify) {
            ExternalRepayCallbackDto callbackDto = new ExternalRepayCallbackDto();
            callbackDto.setResult(notify.getResult()==notify.RESULT_SUCCCESS?true:false);
            callbackDto.setReason(notify.getReason());
            callbackDto.setPeriod(notify.getPeriodNumber());
            callbackDto.setDate(notify.getDate());
            if(notify.getAmount()!=null)
                callbackDto.setAmount(BigDecimal.valueOf(notify.getAmount()));
            if(notify.getRefundCapital()!=null)
                callbackDto.setCapital(BigDecimal.valueOf(notify.getRefundCapital()));
            if(notify.getRefundInterest()!=null)
                callbackDto.setInterest(BigDecimal.valueOf(notify.getRefundInterest()));
            if(notify.getRefundCommission()!=null)
                callbackDto.setFee(BigDecimal.valueOf(notify.getRefundCommission()));
            if(notify.getRefundDefaultInterest()!=null)
                callbackDto.setFine(BigDecimal.valueOf(notify.getRefundDefaultInterest()));
            callbackDto.setRefundFlag(EnumHelper.translate(ERefundFlag.class,String.valueOf(notify.getRefundFlag())));
            callbackDto.setRefundType(notify.getRefundType());
            return callbackDto;
    }


    /**
     * 根据外部loanId 获取小贷系统的申请对象
     *
     * @param loanId
     * @return
     */
    private LoanApplOutDto getSafeLoanApplDto(String outloanId) {
        LoanApplOutDto loanApplDto = loanApplOutService.findByOutTradeNoAndType(outloanId, EApplOutType.AITOUZI);
        if (loanApplDto == null)
            throw new BizServiceException(EErrorCode.COMM_INTERNAL_ERROR, "小贷系统未找到对应的贷款申请编号");
        return loanApplDto;
    }


    /**
     * @param clazz      JSON需要转换的参数PO
     * @param jsonObject 请求的request Json字符串
     * @return 返回参数PO对象
     */
    public static <T> T getParams(Class<T> clazz, JSONObject jsonObject) {

        String params = jsonObject.toJSONString();

        params = params.replace("\"null\"", "null").replace("\"\"", "null");

        return JSON.parseObject(params, clazz);
    }

    /***
     * 从request流中读取参数
     * @param request
     * @return
     * @throws
     * @throws ClassNotFoundException
     */
    public static String receivePostJson(HttpServletRequest request) throws Exception {
        // 读取请求内容
        BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()));
        String line = null;
        StringBuilder sb = new StringBuilder();
        while ((line = br.readLine()) != null) {
            sb.append(line);
        }
        // 将资料解码
        String reqBody = sb.toString();
        return URLDecoder.decode(reqBody, "UTF-8");
    }


}
