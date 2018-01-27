import com.xinyunlian.jinfu.common.util.DateHelper;
import com.xinyunlian.jinfu.common.util.EncryptUtil;
import com.xinyunlian.jinfu.common.util.JsonUtil;
import com.xinyunlian.jinfu.finaccbankcard.enums.ECertificateType;
import com.xinyunlian.jinfu.finaccbankcard.enums.EFinOrg;
import com.xinyunlian.jinfu.finaccbankcard.enums.EFinUserType;
import com.xinyunlian.jinfu.finaccfundprofit.dto.FinAccFundProfitDto;
import com.xinyunlian.jinfu.finaccfundprofit.service.FinAccFundProfitService;
import com.xinyunlian.jinfu.finfunddetail.dto.FinFundDetailDto;
import com.xinyunlian.jinfu.finfunddetail.service.FinFundDetailService;
import com.xinyunlian.jinfu.finprofithistory.dto.FinProfitHistoryDto;
import com.xinyunlian.jinfu.finprofithistory.service.FinProfitHistoryService;
import com.xinyunlian.jinfu.fintxhistory.dto.FinTxHistoryDto;
import com.xinyunlian.jinfu.fintxhistory.enums.ERedeemType;
import com.xinyunlian.jinfu.fintxhistory.enums.EShareClass;
import com.xinyunlian.jinfu.fintxhistory.enums.ETxStatus;
import com.xinyunlian.jinfu.fintxhistory.enums.ETxType;
import com.xinyunlian.jinfu.fintxhistory.service.FinTxHistoryService;
import com.xinyunlian.jinfu.zrfundstx.dto.*;
import com.xinyunlian.jinfu.zrfundstx.enums.EConfirmFlag;
import com.xinyunlian.jinfu.zrfundstx.enums.EReturnCode;
import com.xinyunlian.jinfu.zrfundstx.enums.ESignBizType;
import com.xinyunlian.jinfu.zrfundstx.service.ZrFundsHttpService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.CollectionUtils;

import javax.xml.transform.stream.StreamSource;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by dongfangchao on 2016/12/22/0022.
 */
@RunWith(SpringJUnit4ClassRunner.class) // 整合
@ContextConfiguration(locations="classpath:jinfu.spring.xml") // 加载配置
public class ZrFundsHttpServiceTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(ZrFundsHttpServiceTest.class);

    @Autowired
    private ZrFundsHttpService zrFundsHttpService;

    @Autowired
    private FinTxHistoryService finTxHistoryService;

    @Autowired
    private FinFundDetailService finFundDetailService;

    @Autowired
    private FinProfitHistoryService finProfitHistoryService;

    @Autowired
    private FinAccFundProfitService finAccFundProfitService;

    private String salt;
    private String fundCode;
    private Long fundId;
    private String userId;
    private String transactionAccountID;

    @Before
    public void before(){
        salt = "8db4a013a8b515349c307f1e448ce836";
        fundCode = "003075";
        fundId = 1l;
        userId = "UC0000001340";
        transactionAccountID = "00999000000007019";
    }

    @Test
    public void applySign(){
        //银行后台签约申请
        FinTxHistoryDto signApplyHistoryDto = new FinTxHistoryDto();
        signApplyHistoryDto.setUserId("UC0000000405");
        signApplyHistoryDto.setOrderDate(new Date());
        signApplyHistoryDto.setTxType(ETxType.BANK_SIGN_APPLY);
        signApplyHistoryDto.setTxStatus(ETxStatus.INPROCESS);
        signApplyHistoryDto = finTxHistoryService.addFinTxHistory(signApplyHistoryDto);

        BankSignApplyReq signApplyReq =new BankSignApplyReq();
        signApplyReq.setApplicationNo(signApplyHistoryDto.getFinTxId());
        signApplyReq.setAccoreqSerial(signApplyHistoryDto.getFinTxId());
        signApplyReq.setClearingAgencyCode("002");
        signApplyReq.setAcctNameOfInvestorInClearingAgency("金服测试用户1");
        signApplyReq.setAcctNoOfInvestorInClearingAgency("6222080200004977639");
        signApplyReq.setCertificateType(ECertificateType.ID_CARD_NO.getText());
        signApplyReq.setCertificateNo("330423199011127386");
        signApplyReq.setSignBizType(ESignBizType.BIND_CARD.getText());
        signApplyReq.setMobileTelNo("18610160982");

        System.out.println("第一步组装的对象：");
        System.out.println(JsonUtil.toJson(signApplyReq));

        BankSignApplyResp bankSignApplyResp = zrFundsHttpService.applyBankSign(signApplyReq, salt);
        if (bankSignApplyResp == null){
            updateTxHistoryStatus(signApplyHistoryDto.getFinTxId(), null, ETxStatus.FAILURE);
            System.out.println("银行签约失败");
            return;
        }
        if (!EReturnCode.SUCCESS.getText().equals(bankSignApplyResp.getReturnCode())){
            updateTxHistoryStatus(signApplyHistoryDto.getFinTxId(), null, ETxStatus.FAILURE);
            System.out.println("银行签约失败");
            return;
        }

        updateTxHistoryStatus(signApplyHistoryDto.getFinTxId(), null, ETxStatus.SUCCESS);
    }

    private void updateTxHistoryStatus(String finTxId, Object extTxId, ETxStatus txStatus){
        FinTxHistoryDto historyDto = new FinTxHistoryDto();
        historyDto.setFinTxId(finTxId);
        historyDto.setTxStatus(txStatus);
        if (extTxId != null){
            historyDto.setExtTxId(extTxId.toString());
        }
        finTxHistoryService.updateFinTxHistoryStatus(historyDto);
    }

    @Test
    public void md5(){
        try {
            String applicationNo = "111";
            String src = "AccoreqSerial="+applicationNo+"&AcctNameOfInvestorInClearingAgency=黄金超&AcctNoOfInvestorInClearingAgency=6258597678765456&ApplicationNo="+applicationNo+"&CertificateType=0&ClearingAgencyCode=002&SignBizType=2";
            String mySign = EncryptUtil.encryptMd5(src + "&key=8db4a013a8b515349c307f1e448ce836");
            System.out.println(mySign);
        } catch (Exception e) {
            LOGGER.error("md5异常", e);
        }
    }

    @Test
    public void xmlToObj(){
        try {
            String xml = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
                    "\n" +
                    "<Message> \n" +
                    "  <Responsebody> \n" +
                    "    <Response> \n" +
                    "      <Version>1.0.0</Version>  \n" +
                    "      <MerchantId>stdid</MerchantId>  \n" +
                    "      <DistributorCode>stdcode</DistributorCode>  \n" +
                    "      <BusinType>transResultQueryOrder</BusinType>  \n" +
                    "      <ApplicationNo>1221321321</ApplicationNo>  \n" +
                    "      <TotalRecord>1</TotalRecord>  \n" +
                    "      <ReturnCode>0000</ReturnCode>  \n" +
                    "      <ReturnMsg>成功</ReturnMsg>  \n" +
                    "      <Extension/>  \n" +
                    "      <assetList> \n" +
                    "        <asset> \n" +
                    "          <FundCode>1010</FundCode>  \n" +
                    "          <TotalFundVol>2000</TotalFundVol>  \n" +
                    "          <FundDayIncome>50</FundDayIncome>  \n" +
                    "          <AvailableVol>100</AvailableVol> \n" +
                    "        </asset>  \n" +
                    "        <asset> \n" +
                    "          <FundCode>1020</FundCode>  \n" +
                    "          <TotalFundVol>2500</TotalFundVol>  \n" +
                    "          <FundDayIncome>150</FundDayIncome>  \n" +
                    "          <AvailableVol>120</AvailableVol> \n" +
                    "        </asset> \n" +
                    "      </assetList> \n" +
                    "    </Response> \n" +
                    "  </Responsebody>  \n" +
                    "  <Signature>93EBA24619073A9D385549B9B55B58EC</Signature> \n" +
                    "</Message>\n";
            InputStream is = new ByteArrayInputStream(xml.getBytes("UTF-8"));
            StreamSource streamSource = new StreamSource(is);
            Jaxb2Marshaller unmarshaller = new Jaxb2Marshaller();
            unmarshaller.setClassesToBeBound(SuperCashQueryShareRespMsg.class);
            SuperCashQueryShareRespMsg msg = (SuperCashQueryShareRespMsg)unmarshaller.unmarshal(streamSource);
            System.out.println(JsonUtil.toJson(msg));
        } catch (IOException e) {
            LOGGER.error("xml解析异常", e);
        }
    }

    @Test
    public void openAcc(){
        FinTxHistoryDto finTxHistoryDto = new FinTxHistoryDto();
        finTxHistoryDto.setUserId("UC0000000405");
        finTxHistoryDto.setOrderDate(new Date());
        finTxHistoryDto.setTxType(ETxType.OPEN_ACC);
        finTxHistoryDto.setTxStatus(ETxStatus.INPROCESS);
        finTxHistoryDto = finTxHistoryService.addFinTxHistory(finTxHistoryDto);

        NormOpenAccReq reqDto = new NormOpenAccReq();
        reqDto.setApplicationNo(finTxHistoryDto.getFinTxId());
        reqDto.setClearingAgencyCode("002");
        reqDto.setAcctNameOfInvestorInClearingAgency("黄金超");
        reqDto.setAcctNoOfInvestorInClearingAgency("6222080200004977639");
        reqDto.setInvestorName("黄金超");
        reqDto.setCertificateType(ECertificateType.ID_CARD_NO.getText());
        reqDto.setCertificateNo("330423199011127386");
        reqDto.setMobileTelNo("18610160982");
        reqDto.setUserType(EFinUserType.SUPER_CASH.getText());
        reqDto.setOtherUserId("UC0000000405");

        NormOpenAccResp resp = zrFundsHttpService.openAcc(reqDto, salt);
        if (resp == null){
            updateTxHistoryStatus(finTxHistoryDto.getFinTxId(), null, ETxStatus.FAILURE);
            System.out.println("开户失败");
            return;
        }
        if (!EReturnCode.SUCCESS.getText().equals(resp.getReturnCode())){
            updateTxHistoryStatus(finTxHistoryDto.getFinTxId(), resp.getAppSheetSerialNo(), ETxStatus.FAILURE);
            System.out.println("开户失败");
            return;
        }

        updateTxHistoryStatus(finTxHistoryDto.getFinTxId(), resp.getAppSheetSerialNo(), ETxStatus.SUCCESS);
    }

    @Test
    public void applyPurchase(){
        String salt = "8db4a013a8b515349c307f1e448ce836";

        //插入交易历史表
        FinTxHistoryDto finTxHistoryDto = new FinTxHistoryDto();
        finTxHistoryDto.setUserId("UC0000000405");
        finTxHistoryDto.setOrderDate(new Date());
        finTxHistoryDto.setTxType(ETxType.APPLY_PUR);
        finTxHistoryDto.setFinFundId(fundId);
        finTxHistoryDto.setTxFee(new BigDecimal("50000.5"));
        finTxHistoryDto.setTxStatus(ETxStatus.INPROCESS);

        finTxHistoryDto = finTxHistoryService.addFinTxHistory(finTxHistoryDto);

        //申购
        NormApplyPurReq reqDto = new NormApplyPurReq();
        reqDto.setTransactionAccountID("00999000000008005");
        reqDto.setApplicationNo(finTxHistoryDto.getFinTxId());
        reqDto.setFundCode(fundCode);
        reqDto.setApplicationAmount(new BigDecimal("50000.5"));

        NormApplyPurResp normApplyPurResp = zrFundsHttpService.applyPurchase(reqDto, salt);
        //申购失败
        if (normApplyPurResp == null){
            updateTxHistoryStatus(finTxHistoryDto.getFinTxId(), null, ETxStatus.FAILURE);
            System.out.println("申购失败");
            return;
        }
        if (!EReturnCode.SUCCESS.getText().equals(normApplyPurResp.getReturnCode())){
            updateTxHistoryStatus(finTxHistoryDto.getFinTxId(), normApplyPurResp.getAppSheetSerialNo(), ETxStatus.FAILURE);
            System.out.println("申购失败");
            return;
        }

        //申购成功
        updateTxHistoryStatus(finTxHistoryDto.getFinTxId(), normApplyPurResp.getAppSheetSerialNo(), ETxStatus.SUCCESS);
    }

    @Test
    public void redeem(){
        //插入交易历史表
        FinTxHistoryDto finTxHistoryDto = new FinTxHistoryDto();
        finTxHistoryDto.setUserId("UC0000000405");
        finTxHistoryDto.setOrderDate(new Date());
        finTxHistoryDto.setTxType(ETxType.REDEEM);
        finTxHistoryDto.setFinFundId(fundId);
        finTxHistoryDto.setTxStatus(ETxStatus.INPROCESS);
        finTxHistoryDto = finTxHistoryService.addFinTxHistory(finTxHistoryDto);
        String finTxId = finTxHistoryDto.getFinTxId();

        ERedeemType redeemType = ERedeemType.REDEEM_REAL_TIME;

        //根据赎回类型进行赎回
        if (ERedeemType.REDEEM_NORMAL == redeemType){
            System.out.println("普通赎回开始");

            RedeemNormReq reqDto = new RedeemNormReq();
            reqDto.setTransactionAccountID("10000122122");
            reqDto.setApplicationNo(finTxId);
            reqDto.setFundCode(fundCode);
            reqDto.setApplicationVol(new BigDecimal("3000.5"));

            RedeemNormResp resp = zrFundsHttpService.redeemNormal(reqDto, salt);
            if (resp == null){
                updateTxHistoryStatus(finTxId, null, ETxStatus.FAILURE);
                System.out.println("普通赎回失败");
                return;
            }
            if (!EReturnCode.SUCCESS.getText().equals(resp.getReturnCode())){
                updateTxHistoryStatus(finTxHistoryDto.getFinTxId(), null, ETxStatus.FAILURE);
                System.out.println("普通赎回失败");
                return;
            }
            updateTxHistoryStatus(finTxId, resp.getAppSheetSerialNo(), ETxStatus.SUCCESS);

        }else if (ERedeemType.REDEEM_REAL_TIME == redeemType){
            System.out.println("实时赎回开始");

            RedeemRealTimeReq reqDto = new RedeemRealTimeReq();
            reqDto.setTransactionAccountID("10000122122");
            reqDto.setApplicationNo(finTxId);
            reqDto.setFundCode(fundCode);
            reqDto.setApplicationVol(new BigDecimal("3000.5"));
            reqDto.setShareClass(EShareClass.BEFORE_PAID.getValue());

            RedeemRealTimeResp resp = zrFundsHttpService.redeemRealTime(reqDto, salt);
            if (resp == null){
                updateTxHistoryStatus(finTxId, null, ETxStatus.FAILURE);
                System.out.println("实时赎回失败");
                return;
            }
            if (!EReturnCode.SUCCESS.getText().equals(resp.getReturnCode())){
                updateTxHistoryStatus(finTxHistoryDto.getFinTxId(), null, ETxStatus.FAILURE);
                System.out.println("实时赎回失败");
                return;
            }
            updateTxHistoryStatus(finTxId, resp.getAppSheetSerialNo(), ETxStatus.SUCCESS);
        }
    }

    @Test
    public void querySuperCashShare(){
        FinTxHistoryDto finTxHistoryDto = new FinTxHistoryDto();
        finTxHistoryDto.setUserId("UC0000001340");
        finTxHistoryDto.setOrderDate(new Date());
        finTxHistoryDto.setTxType(ETxType.QUERY);
        finTxHistoryDto.setFinFundId(fundId);
        finTxHistoryDto.setTxStatus(ETxStatus.INPROCESS);

        finTxHistoryDto = finTxHistoryService.addFinTxHistory(finTxHistoryDto);

        SuperCashQueryShareReq scShareReq = new SuperCashQueryShareReq();
        scShareReq.setTransactionAccountID("00999000000007019");
        scShareReq.setApplicationNo(finTxHistoryDto.getFinTxId());
        scShareReq.setFundCode(fundCode);

        SuperCashQueryShareResp superCashQueryShareResp = zrFundsHttpService.querySuperCashShare(scShareReq, salt);
        if (superCashQueryShareResp == null){
            updateTxHistoryStatus(finTxHistoryDto.getFinTxId(), null, ETxStatus.FAILURE);
            System.out.println("份额查询失败");
            return;
        }
        if (!EReturnCode.SUCCESS.getText().equals(superCashQueryShareResp.getReturnCode())){
            updateTxHistoryStatus(finTxHistoryDto.getFinTxId(), null, ETxStatus.FAILURE);
            System.out.println("份额查询失败");
            return;
        }

        updateTxHistoryStatus(finTxHistoryDto.getFinTxId(), null, ETxStatus.SUCCESS);
    }

    @Test
    public void queryQuotation(){
        List<FinFundDetailDto> fundDetailList = finFundDetailService.getFinFundDetailList(null);

        if (!CollectionUtils.isEmpty(fundDetailList)){
            for (FinFundDetailDto fundDetailDto:fundDetailList){
                //插入交易历史表
                FinTxHistoryDto finTxHistoryDto = new FinTxHistoryDto();
                finTxHistoryDto.setUserId("system");
                finTxHistoryDto.setOrderDate(new Date());
                finTxHistoryDto.setFinFundId(fundDetailDto.getFinFundId());
                finTxHistoryDto.setTxType(ETxType.SYC_QN);
                finTxHistoryDto.setTxStatus(ETxStatus.INPROCESS);
                finTxHistoryDto = finTxHistoryService.addFinTxHistory(finTxHistoryDto);
                String finTxId = finTxHistoryDto.getFinTxId();

                SuperCashQnReq req = new SuperCashQnReq();
                req.setApplicationNo(finTxId);
                req.setFundCode(fundDetailDto.getFinFundCode());

                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
                Calendar ytd = Calendar.getInstance();
                ytd.add(Calendar.DATE, -1);
                Long ytdLong = Long.parseLong(sdf.format(ytd.getTime()));
                req.setBeginDate(ytdLong);
                req.setEndDate(ytdLong);

                SuperCashQnResp resp = zrFundsHttpService.queryQuotation(req, salt);

                if (resp == null){
                    updateTxHistoryStatus(finTxHistoryDto.getFinTxId(), null, ETxStatus.FAILURE);
                    LOGGER.error("行情查询失败");
                    return;
                }
                if (!EReturnCode.SUCCESS.getText().equals(resp.getReturnCode())){
                    updateTxHistoryStatus(finTxHistoryDto.getFinTxId(), null, ETxStatus.FAILURE);
                    LOGGER.error("行情查询失败," + resp.getReturnMsg());
                    return;
                }

                updateTxHistoryStatus(finTxHistoryDto.getFinTxId(), null, ETxStatus.SUCCESS);

                if (!CollectionUtils.isEmpty(resp.getList())){
                    SuperCashQnSubResp subResp = resp.getList().get(0);
                    FinFundDetailDto dto = new FinFundDetailDto();
                    dto.setFinFundCode(subResp.getFundCode());
                    dto.setFinOrg(EFinOrg.ZRFUNDS);
                    dto.setYield(subResp.getYield());
                    dto.setFoundIncome(subResp.getFundIncome());
                    Long updateDate = subResp.getUpdateDate();
                    Date date = DateHelper.getDate(updateDate.toString(), "yyyyMMdd");
                    dto.setUpdateDate(date);

                    finFundDetailService.updateFinFundDetail(dto);
                }
            }
        }
    }

    @Test
    public void querySuperCashTrade(){
        SuperCashTradeQueryReq reqDto = new SuperCashTradeQueryReq();
        reqDto.setTransactionAccountID("00999000000008602");
        reqDto.setApplicationNo("202010333496949003");
        SuperCashTradeQueryResp resp = zrFundsHttpService.querySuperCashTrade(reqDto, salt);

        if (resp == null){
            System.out.println("超级现金宝交易结果查询失败");
            return;
        }
        if (!EReturnCode.SUCCESS.getText().equals(resp.getReturnCode())){
            System.out.println("超级现金宝交易结果查询失败," + resp.getReturnMsg());
            return;
        }

        if (!CollectionUtils.isEmpty(resp.getList())){
            SuperCashTradeQuerySubResp subResp = resp.getList().get(0);
            if (subResp.getConfirmFlag() == EConfirmFlag.CONFIRM_SUCCESS.getFlag()
                    || subResp.getConfirmFlag() == EConfirmFlag.REALTIME_CONFIRM_SUCCESS.getFlag()){
                System.out.println("扣款成功");
                return;
            }
        }
    }

    @Test
    public void updateProfit(){
        //查询超级现金宝份额
        FinTxHistoryDto finTxHistoryDto = new FinTxHistoryDto();
        finTxHistoryDto.setUserId(userId);
        finTxHistoryDto.setOrderDate(new Date());
        finTxHistoryDto.setTxType(ETxType.QUERY);
        finTxHistoryDto.setFinFundId(fundId);
        finTxHistoryDto.setTxStatus(ETxStatus.INPROCESS);

        finTxHistoryDto = finTxHistoryService.addFinTxHistory(finTxHistoryDto);

        SuperCashQueryShareReq scShareReq = new SuperCashQueryShareReq();
        scShareReq.setTransactionAccountID(transactionAccountID);
        scShareReq.setApplicationNo(finTxHistoryDto.getFinTxId());
        scShareReq.setFundCode(fundCode);

        SuperCashQueryShareResp superCashQueryShareResp = zrFundsHttpService.querySuperCashShare(scShareReq, salt);
        if (superCashQueryShareResp == null){
            updateTxHistoryStatus(finTxHistoryDto.getFinTxId(), null, ETxStatus.FAILURE);
            System.out.println("超级现金宝份额查询失败");
            return;
        }
        if (!EReturnCode.SUCCESS.getText().equals(superCashQueryShareResp.getReturnCode())){
            updateTxHistoryStatus(finTxHistoryDto.getFinTxId(), null, ETxStatus.FAILURE);
            System.out.println("超级现金宝份额查询失败," + superCashQueryShareResp.getReturnMsg());
            return;
        }

        updateTxHistoryStatus(finTxHistoryDto.getFinTxId(), null, ETxStatus.SUCCESS);

        if (!CollectionUtils.isEmpty(superCashQueryShareResp.getList())){
            SuperCashQueryShareSubResp subResp = superCashQueryShareResp.getList().get(0);
            Date profitDate = DateHelper.getStartDate(new Date());
            //更新收益记录
            FinProfitHistoryDto finProfitHistoryDto = new FinProfitHistoryDto();
            finProfitHistoryDto.setUserId(userId);
            finProfitHistoryDto.setExtTxAccId(transactionAccountID);
            finProfitHistoryDto.setFinFundId(fundId);
            finProfitHistoryDto.setProfitDate(profitDate);
            finProfitHistoryDto.setAssetAmt(subResp.getTotalFundVol());
            finProfitHistoryDto.setTotalProfit(subResp.getTotalIncome());
            finProfitHistoryDto.setProfitAmt(subResp.getFundDayIncome());
            finProfitHistoryDto.setFinOrg(EFinOrg.ZRFUNDS);

            finProfitHistoryService.updateProfitHistory(finProfitHistoryDto);
            finProfitHistoryService.updateProfitHistorySummary(userId, profitDate);

            FinAccFundProfitDto profitDto = new FinAccFundProfitDto();
            profitDto.setUserId(userId);
            profitDto.setFinOrg(EFinOrg.ZRFUNDS);
            profitDto.setExtTxAccId(transactionAccountID);
            profitDto.setFinFundId(fundId);
            finAccFundProfitService.updateFinAccProdProfit(profitDto);
        }
    }

}
