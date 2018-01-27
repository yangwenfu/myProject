package com.xinyunlian.jinfu.finance.controller;

import com.xinyunlian.jinfu.bank.dto.BankCardDto;
import com.xinyunlian.jinfu.bank.dto.BankInfDto;
import com.xinyunlian.jinfu.bank.enums.ECardType;
import com.xinyunlian.jinfu.bank.service.BankService;
import com.xinyunlian.jinfu.common.constant.ResultCode;
import com.xinyunlian.jinfu.common.converter.ConverterService;
import com.xinyunlian.jinfu.common.dto.ResultDto;
import com.xinyunlian.jinfu.common.dto.ResultDtoFactory;
import com.xinyunlian.jinfu.common.exception.BizServiceException;
import com.xinyunlian.jinfu.common.security.SecurityContext;
import com.xinyunlian.jinfu.common.util.AppConfigUtil;
import com.xinyunlian.jinfu.common.util.DateHelper;
import com.xinyunlian.jinfu.common.util.MessageUtil;
import com.xinyunlian.jinfu.dto.UserRealAuthDto;
import com.xinyunlian.jinfu.finaccbankcard.dto.FinAccBankCardDto;
import com.xinyunlian.jinfu.finaccbankcard.dto.FinAccBankCardSearchDto;
import com.xinyunlian.jinfu.finaccbankcard.enums.ECertificateType;
import com.xinyunlian.jinfu.finaccbankcard.enums.EFinOpenAccType;
import com.xinyunlian.jinfu.finaccbankcard.enums.EFinOrg;
import com.xinyunlian.jinfu.finaccbankcard.enums.EFinUserType;
import com.xinyunlian.jinfu.finaccbankcard.service.FinAccBankCardService;
import com.xinyunlian.jinfu.finaccfundprofit.dto.FinAccFundProfitDto;
import com.xinyunlian.jinfu.finaccfundprofit.service.FinAccFundProfitService;
import com.xinyunlian.jinfu.finance.dto.*;
import com.xinyunlian.jinfu.finbank.dto.FinBankDto;
import com.xinyunlian.jinfu.finbank.service.FinBankService;
import com.xinyunlian.jinfu.finfunddetail.dto.FinFundDetailDto;
import com.xinyunlian.jinfu.finfunddetail.service.FinFundDetailService;
import com.xinyunlian.jinfu.finprofithistory.dto.FinProfitHistoryDto;
import com.xinyunlian.jinfu.finprofithistory.dto.FinProfitHistorySearchDto;
import com.xinyunlian.jinfu.finprofithistory.dto.FinProfitHistorySumDto;
import com.xinyunlian.jinfu.finprofithistory.dto.FinProfitHistorySumSearchDto;
import com.xinyunlian.jinfu.finprofithistory.service.FinProfitHistoryService;
import com.xinyunlian.jinfu.fintxhistory.dto.FinTxHistoryDto;
import com.xinyunlian.jinfu.fintxhistory.dto.FinTxHistorySearchDto;
import com.xinyunlian.jinfu.fintxhistory.enums.ERedeemType;
import com.xinyunlian.jinfu.fintxhistory.enums.EShareClass;
import com.xinyunlian.jinfu.fintxhistory.enums.ETxStatus;
import com.xinyunlian.jinfu.fintxhistory.enums.ETxType;
import com.xinyunlian.jinfu.fintxhistory.service.FinTxHistoryService;
import com.xinyunlian.jinfu.service.UserRealAuthService;
import com.xinyunlian.jinfu.user.dto.ClientSaltDto;
import com.xinyunlian.jinfu.user.enums.ESmsSendType;
import com.xinyunlian.jinfu.user.service.ClientSaltService;
import com.xinyunlian.jinfu.user.service.SmsService;
import com.xinyunlian.jinfu.zrfundstx.dto.*;
import com.xinyunlian.jinfu.zrfundstx.enums.EConfirmFlag;
import com.xinyunlian.jinfu.zrfundstx.enums.EReturnCode;
import com.xinyunlian.jinfu.zrfundstx.enums.ESignBizType;
import com.xinyunlian.jinfu.zrfundstx.service.ZrFundsHttpService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.*;

/**
 * Created by dongfangchao on 2016/11/21.
 */
@RestController
@RequestMapping("finance")
public class FinanceController {

    private static final Logger LOGGER = LoggerFactory.getLogger(FinanceController.class);

    @Autowired
    private BankService bankService;
    @Autowired
    private FinAccBankCardService finAccBankCardService;
    @Autowired
    private FinAccFundProfitService finAccFundProfitService;
    @Autowired
    private FinFundDetailService finFundDetailService;
    @Autowired
    private FinProfitHistoryService finProfitHistoryService;
    @Autowired
    private FinTxHistoryService finTxHistoryService;
    @Autowired
    private FinBankService finBankService;
    @Autowired
    private ZrFundsHttpService zrFundsHttpService;
    @Autowired
    private SmsService smsService;
    @Autowired
    private UserRealAuthService userRealAuthService;
    @Autowired
    private ClientSaltService clientSaltService;

    private String zrfundsClientId = AppConfigUtil.getConfig("zrfunds.client.id");

    /**
     * 检查是否已开户
     * @return
     */
    @RequestMapping(value = "checkOpenAcc", method = RequestMethod.GET)
    public ResultDto<Object> checkOpenAcc(){
        String userId = SecurityContext.getCurrentUserId();

        if(StringUtils.isEmpty(userId)){
            return ResultDtoFactory.toNack("用户未登录");
        }

        //判断是否存在开户信息，不去中融联机查询
        FinAccBankCardSearchDto searchDto = new FinAccBankCardSearchDto();
        searchDto.setUserId(userId);
        searchDto.setFinOrg(EFinOrg.ZRFUNDS);
        List<FinAccBankCardDto> dtoList = finAccBankCardService.getFinAccBankCardList(searchDto);
        if (CollectionUtils.isEmpty(dtoList)){
            return ResultDtoFactory.toNack("用户未开户");
        }

        return ResultDtoFactory.toAck("查询成功");
    }

    /**
     * 检查用户是否有持有资产
     * @return
     */
    @RequestMapping(value = "checkHoldAsset", method = RequestMethod.GET)
    public ResultDto<Object> checkHoldAsset(){

        String userId = SecurityContext.getCurrentUserId();
        if(StringUtils.isEmpty(userId)){
            return ResultDtoFactory.toNack("用户未登录");
        }

        FinAccFundProfitDto searchDto = new FinAccFundProfitDto();
        searchDto.setUserId(userId);
        List<FinAccFundProfitDto> result = finAccFundProfitService.getFinAccProdProfits(searchDto);
        if (CollectionUtils.isEmpty(result)){
            return ResultDtoFactory.toNack("无持有资产");
        }

        return ResultDtoFactory.toAck("查询持有资产成功");
    }

    /**
     * 查询理财产品列表
     * @return
     */
    @RequestMapping(value = "getFinProdDetailList", method = RequestMethod.GET)
    public ResultDto<Object> getFinProdDetailList(){
        List<FinFundDetailDto> result = finFundDetailService.getFinFundDetailList(null);
        return ResultDtoFactory.toAck("查询成功", result);
    }

    /**
     * 查询资产总览：总资产，昨日收益，累计收益
     * @return
     */
    @RequestMapping(value = "getAssetsOverview", method = RequestMethod.GET)
    public ResultDto<Object> getAssetsOverview(){
        Map<String, Object> assetsOverviewMap = new HashMap<>();

        String userId = SecurityContext.getCurrentUserId();
        if(StringUtils.isEmpty(userId)){
            return ResultDtoFactory.toNack("用户未登录");
        }

        FinAccFundProfitDto searchDto1 = new FinAccFundProfitDto();
        searchDto1.setUserId(userId);
        List<FinAccFundProfitDto> result = finAccFundProfitService.getFinAccProdProfits(searchDto1);

        if (CollectionUtils.isEmpty(result)){
            assetsOverviewMap.put("holdAsset", 0);
            assetsOverviewMap.put("totalProfit", 0);
            assetsOverviewMap.put("ytdProfit", 0);
            return ResultDtoFactory.toAck("查询成功", assetsOverviewMap);
        }

        //统计总资产和累计收益
        BigDecimal holdAsset = new BigDecimal("0");
        BigDecimal totalProfit = new BigDecimal("0");
        for (FinAccFundProfitDto dto: result) {
            holdAsset = holdAsset.add(dto.getHoldAsset());
            totalProfit = totalProfit.add(dto.getTotalProfit());
        }
        assetsOverviewMap.put("holdAsset", holdAsset);
        assetsOverviewMap.put("totalProfit", totalProfit);

        //统计昨日收益
        FinProfitHistorySumSearchDto searchDto2 = new FinProfitHistorySumSearchDto();
        searchDto2.setUserId(userId);
        Calendar ytd = Calendar.getInstance();
        searchDto2.setProfitDateFrom(DateHelper.getStartDate(ytd.getTime()));
        searchDto2.setProfitDateTo(DateHelper.getEndDate(ytd.getTime()));
        List<FinProfitHistorySumDto> ytdProfitList = finProfitHistoryService.getProfitHistorySum(searchDto2);
        BigDecimal ytdProfit = new BigDecimal("0");
        if (!CollectionUtils.isEmpty(ytdProfitList)){
            ytdProfit = ytdProfitList.get(0).getProfitAmt();
        }
        assetsOverviewMap.put("ytdProfit",ytdProfit);

        return ResultDtoFactory.toAck("查询成功", assetsOverviewMap);
    }

    /**
     * 分页查看收益详情（查询每日收益总计）
     * @param searchDto
     * @return
     */
    @RequestMapping(value = "getProfitDetailPage", method = RequestMethod.POST)
    public ResultDto<Object> getProfitDetailPage(@RequestBody FinProfitHistorySumSearchDto searchDto){
        String userId = SecurityContext.getCurrentUserId();
        searchDto.setUserId(userId);
        FinProfitHistorySumSearchDto page = finProfitHistoryService.getProfitHistorySumPage(searchDto);
        return ResultDtoFactory.toAck("查询成功", page);
    }

    /**
     * 分页查询交易记录
     * @param searchDto
     * @return
     */
    @RequestMapping(value = "getTxHistoryPage", method = RequestMethod.POST)
    public ResultDto<Object> getTxHistoryPage(@RequestBody FinTxHistorySearchDto searchDto){
        String userId = SecurityContext.getCurrentUserId();
        searchDto.setUserId(userId);
        FinTxHistorySearchDto page = finTxHistoryService.getFinTxHistoryPage(searchDto);
        return ResultDtoFactory.toAck("查询成功", page);
    }

    /**
     * 查询持有资产
     * @return
     */
    @RequestMapping(value = "getHoldAsset", method = RequestMethod.GET)
    public ResultDto<Object> getHoldAsset(){
        String userId = SecurityContext.getCurrentUserId();

        Calendar ytd = Calendar.getInstance();
        FinProfitHistorySearchDto searchDto = new FinProfitHistorySearchDto();
        searchDto.setUserId(userId);
        searchDto.setProfitDateFrom(DateHelper.getStartDate(ytd.getTime()));
        searchDto.setProfitDateTo(DateHelper.getEndDate(ytd.getTime()));
        List<FinProfitHistoryDto> profitHistoryList = finProfitHistoryService.getProfitHistory(searchDto);

        List<FinFundDetailDto> fundDetailList = finFundDetailService.getFinFundDetailList(null);
        List<FinProfitHistoryDto> retList = new ArrayList<>();
        Map<Long, FinProfitHistoryDto> profitMap = new HashMap<>();
        if (!CollectionUtils.isEmpty(fundDetailList)){

            for (FinFundDetailDto fundDetailDto : fundDetailList) {
                FinProfitHistoryDto historyDto = new FinProfitHistoryDto();
                historyDto.setYield(fundDetailDto.getYield());
                historyDto.setFoundIncome(fundDetailDto.getFoundIncome());
                historyDto.setProdDetailUpdateDate(fundDetailDto.getUpdateDate());
                historyDto.setRedeemDesc(fundDetailDto.getRedeemDesc());
                historyDto.setFinFundName(fundDetailDto.getFinFundName());
                historyDto.setFinFundId(fundDetailDto.getFinFundId());

                profitMap.put(fundDetailDto.getFinFundId(), historyDto);
            }

            for (FinProfitHistoryDto tmpDto : profitHistoryList) {
                if (profitMap.containsKey(tmpDto.getFinFundId())){
                    FinProfitHistoryDto mapDto = profitMap.get(tmpDto.getFinFundId());
                    mapDto.setUserId(tmpDto.getUserId());
                    mapDto.setExtTxAccId(tmpDto.getExtTxAccId());

                    BigDecimal profitAmt = new BigDecimal("0");
                    if (mapDto.getProfitAmt() == null){
                        profitAmt = tmpDto.getProfitAmt();
                    }else {
                        profitAmt = profitAmt.add(mapDto.getProfitAmt()).add(tmpDto.getProfitAmt());
                    }
                    mapDto.setProfitAmt(profitAmt);

                    BigDecimal assetAmt = new BigDecimal("0");
                    if (mapDto.getAssetAmt() == null){
                        assetAmt = tmpDto.getAssetAmt();
                    }else {
                        assetAmt = assetAmt.add(mapDto.getAssetAmt()).add(tmpDto.getAssetAmt());
                    }
                    mapDto.setAssetAmt(assetAmt);

                    BigDecimal totalProfit = new BigDecimal("0");
                    if (mapDto.getTotalProfit() == null){
                        totalProfit = tmpDto.getTotalProfit();
                    }else {
                        totalProfit = totalProfit.add(mapDto.getTotalProfit()).add(tmpDto.getTotalProfit());
                    }
                    mapDto.setTotalProfit(totalProfit);

                    profitMap.put(mapDto.getFinFundId(), mapDto);
                }
            }

            retList.addAll(profitMap.values());
        }

        return ResultDtoFactory.toAck("查询成功", retList);
    }

    /**
     * 获取中融支持银行卡列表
     * @return
     */
    @RequestMapping(value = "getFinBankList", method = RequestMethod.GET)
    public ResultDto<Object> getFinBankList(){
        List<FinBankDto> list = finBankService.getFinBankList(null);
        return ResultDtoFactory.toAck("查询成功", list);
    }

    /**
     * 获取用户金服已有卡的列表，去除已开户的
     * @return
     */
    @RequestMapping(value = "getUserBankCards", method = RequestMethod.GET)
    public ResultDto<Object> getUserBankCards(){
        String userId = SecurityContext.getCurrentUserId();
        //获取金服中绑定的卡列表
        List<BankCardDto> bankCardDtoList = bankService.findByUserId(userId);
        List<BankCardExtDto> retList = new ArrayList<>();
        Map<String, BankCardExtDto> bankCodeNoMap = new HashMap<>();

        if (!CollectionUtils.isEmpty(bankCardDtoList)){
            for (BankCardDto dto : bankCardDtoList){
                BankCardExtDto extDto = ConverterService.convert(dto, BankCardExtDto.class);
                bankCodeNoMap.put(dto.getBankCode().toUpperCase() + "-" + dto.getBankCardNo(), extDto);
            }

            //判断银行卡是否已经在中融开户
            FinAccBankCardSearchDto searchDto = new FinAccBankCardSearchDto();
            searchDto.setUserId(userId);
            searchDto.setFinOrg(EFinOrg.ZRFUNDS);
            List<FinAccBankCardDto> accBankCardList = finAccBankCardService.getFinAccBankCardList(searchDto);
            if (!CollectionUtils.isEmpty(accBankCardList)){
                for (FinAccBankCardDto accDto : accBankCardList) {
                    if (bankCodeNoMap.containsKey(accDto.getBankShortName() + "-" + accDto.getBankCardNo())){
                        bankCodeNoMap.remove(accDto.getBankShortName() + "-" + accDto.getBankCardNo());
                    }
                }
            }

            if (!bankCodeNoMap.isEmpty()){
                retList.addAll(bankCodeNoMap.values());

                List<FinBankDto> zrfundsBankList = finBankService.getFinBankList(null);
                if (!CollectionUtils.isEmpty(zrfundsBankList)){
                    for (BankCardExtDto extDto : retList){
                        for (FinBankDto finBank : zrfundsBankList) {
                            if (extDto.getBankCode().equalsIgnoreCase(finBank.getBankShortName())){
                                extDto.setZrfundsSupport(true);
                                extDto.setBankCode(finBank.getBankCode());
                                extDto.setBankShortName(finBank.getBankShortName());
                            }
                        }
                    }
                }
            }
        }

        return ResultDtoFactory.toAck("查询成功", retList);
    }

    /**
     * 开户
     * @param newAcc
     * @return
     */
    @RequestMapping(value = "openAcc", method = RequestMethod.POST)
    public ResultDto<Object> openAcc(@RequestBody FinAccBankCardExtDto newAcc){
        String userId = SecurityContext.getCurrentUserId();

        //新增卡，先去金服绑卡
        if (EFinOpenAccType.NORMAL_BANK_CARD_BIND.getCode().equals(newAcc.getFinOpenAccType().getCode())){

            List<BankInfDto> bankInfDtos = bankService.findByBankCode(newAcc.getBankShortName());
            Long bankId = null;
            if (!CollectionUtils.isEmpty(bankInfDtos)){

                //金服绑卡出现异常不能影响中融开户，两部分互相独立
                try {
                    bankId = bankInfDtos.get(0).getBankId();

                    BankCardDto bankCardDto = new BankCardDto();
                    bankCardDto.setIdCardNo(newAcc.getIdCardNo());
                    bankCardDto.setBankCardNo(newAcc.getBankCardNo());
                    bankCardDto.setBankCardName(newAcc.getUserRealName());
                    bankCardDto.setMobileNo(newAcc.getReserveMobile());
                    bankCardDto.setVerifyCode(newAcc.getVerifyCode());
                    bankCardDto.setUserId(userId);
                    bankCardDto.setBankId(bankId);
                    bankCardDto.setCardType(ECardType.DEBIT);

                    boolean flag = smsService.confirmVerifyCode(bankCardDto.getMobileNo(), bankCardDto.getVerifyCode(), ESmsSendType.BANKCARD);
                    if(!flag){
                        return ResultDtoFactory.toNack("验证码错误");
                    }
                    UserRealAuthDto userRealAuthDto = new UserRealAuthDto();
                    userRealAuthDto.setIdCardNo(bankCardDto.getIdCardNo());
                    userRealAuthDto.setBankCardNo(bankCardDto.getBankCardNo());
                    userRealAuthDto.setName(bankCardDto.getBankCardName());
                    userRealAuthDto.setPhone(bankCardDto.getMobileNo());
                    boolean real = userRealAuthService.realAuth(userRealAuthDto);
                    if(real == false){
                        LOGGER.error(MessageUtil.getMessage("user.certify.fail"));
                        return ResultDtoFactory.toAck("金服认证失败", MessageUtil.getMessage("user.certify.fail"));
                    }
                    bankService.saveBankCard(bankCardDto);
                    smsService.clearVerifyCode(bankCardDto.getMobileNo(), ESmsSendType.BANKCARD);
                } catch (Exception e) {
                    LOGGER.error("金服绑卡失败",e);
                }
            }
        }

        ClientSaltDto saltDto = clientSaltService.getClientSalt(zrfundsClientId);

        //银行后台签约申请
        FinTxHistoryDto signApplyHistoryDto = new FinTxHistoryDto();
        signApplyHistoryDto.setUserId(userId);
        signApplyHistoryDto.setOrderDate(new Date());
        signApplyHistoryDto.setTxType(ETxType.BANK_SIGN_APPLY);
        signApplyHistoryDto.setTxStatus(ETxStatus.INPROCESS);
        signApplyHistoryDto = finTxHistoryService.addFinTxHistory(signApplyHistoryDto);

        BankSignApplyReq signApplyReq =new BankSignApplyReq();
        signApplyReq.setApplicationNo(signApplyHistoryDto.getFinTxId());
        signApplyReq.setAccoreqSerial(signApplyHistoryDto.getFinTxId());
        signApplyReq.setClearingAgencyCode(newAcc.getBankCode());
        signApplyReq.setAcctNameOfInvestorInClearingAgency(newAcc.getUserRealName());
        signApplyReq.setAcctNoOfInvestorInClearingAgency(newAcc.getBankCardNo());
        signApplyReq.setCertificateType(ECertificateType.ID_CARD_NO.getText());
        signApplyReq.setCertificateNo(newAcc.getIdCardNo());
        signApplyReq.setSignBizType(ESignBizType.BIND_CARD.getText());
        signApplyReq.setMobileTelNo(newAcc.getReserveMobile());
        BankSignApplyResp bankSignApplyResp = zrFundsHttpService.applyBankSign(signApplyReq, saltDto.getSalt());
        if (bankSignApplyResp == null){
            updateTxHistoryStatus(signApplyHistoryDto.getFinTxId(), null, ETxStatus.FAILURE);
            return ResultDtoFactory.toNack("银行签约失败");
        }
        if (!EReturnCode.SUCCESS.getText().equals(bankSignApplyResp.getReturnCode())){
            updateTxHistoryStatus(signApplyHistoryDto.getFinTxId(), null, ETxStatus.FAILURE);
            return ResultDtoFactory.toNack("银行签约失败," + bankSignApplyResp.getReturnMsg());
        }
        updateTxHistoryStatus(signApplyHistoryDto.getFinTxId(), null, ETxStatus.SUCCESS);

        //中融开户

        //插入交易历史表
        FinTxHistoryDto finTxHistoryDto = new FinTxHistoryDto();
        finTxHistoryDto.setUserId(userId);
        finTxHistoryDto.setOrderDate(new Date());
        finTxHistoryDto.setTxType(ETxType.OPEN_ACC);
        finTxHistoryDto.setTxStatus(ETxStatus.INPROCESS);
        finTxHistoryDto = finTxHistoryService.addFinTxHistory(finTxHistoryDto);

        //生成金服的理财账号
        FinAccBankCardDto finAccBankCardDto = new FinAccBankCardDto();
        finAccBankCardDto.setExtTxAccId(String.valueOf(Calendar.getInstance().getTimeInMillis()));
        finAccBankCardDto.setFinOrg(EFinOrg.ZRFUNDS);
        FinAccBankCardDto dbFinAcc = finAccBankCardService.addFinAccBankCard(finAccBankCardDto);

        NormOpenAccReq reqDto = new NormOpenAccReq();
        reqDto.setApplicationNo(finTxHistoryDto.getFinTxId());
        reqDto.setClearingAgencyCode(newAcc.getBankCode());
        reqDto.setAcctNameOfInvestorInClearingAgency(newAcc.getUserRealName());
        reqDto.setAcctNoOfInvestorInClearingAgency(newAcc.getBankCardNo());
        reqDto.setInvestorName(newAcc.getUserRealName());
        reqDto.setCertificateType(ECertificateType.ID_CARD_NO.getText());
        reqDto.setCertificateNo(newAcc.getIdCardNo());
        reqDto.setMobileTelNo(newAcc.getReserveMobile());
        reqDto.setUserType(EFinUserType.SUPER_CASH.getText());
        reqDto.setOtherUserId(dbFinAcc.getId());

        NormOpenAccResp resp = zrFundsHttpService.openAcc(reqDto, saltDto.getSalt());
        if (resp == null){
            updateTxHistoryStatus(finTxHistoryDto.getFinTxId(), null, ETxStatus.FAILURE);
            finAccBankCardService.deleteFinAccBankCard(dbFinAcc.getId());
            return ResultDtoFactory.toNack("开户失败");
        }
        if (!EReturnCode.SUCCESS.getText().equals(resp.getReturnCode())){
            updateTxHistoryStatus(finTxHistoryDto.getFinTxId(), resp.getAppSheetSerialNo(), ETxStatus.FAILURE);
            finAccBankCardService.deleteFinAccBankCard(dbFinAcc.getId());
            return ResultDtoFactory.toNack("开户失败," + resp.getReturnMsg());
        }

        updateTxHistoryStatus(finTxHistoryDto.getFinTxId(), resp.getAppSheetSerialNo(), ETxStatus.SUCCESS);

        newAcc.setUserId(userId);
        newAcc.setExtTxAccId(resp.getTransactionAccountID());
        newAcc.setId(dbFinAcc.getId());
        newAcc.setFinOrg(EFinOrg.ZRFUNDS);
        finAccBankCardService.updateFinAccBankCard(newAcc);

        return ResultDtoFactory.toAck("开户成功");
    }

    /**
     * 获得中融已绑卡的银行卡
     * @return
     */
    @RequestMapping(value = "getOpenAccBankCards", method = RequestMethod.GET)
    public ResultDto<Object> getOpenAccBankCards(){
        String userId = SecurityContext.getCurrentUserId();
        FinAccBankCardSearchDto searchDto = new FinAccBankCardSearchDto();
        searchDto.setUserId(userId);
        searchDto.setFinOrg(EFinOrg.ZRFUNDS);
        List<FinAccBankCardDto> accBankCardList = finAccBankCardService.getFinAccBankCardList(searchDto);
        List<FinBankDto> finBankDtos = finBankService.getFinBankList(null);

        List<FinAccBankCardDto> retList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(accBankCardList) && !CollectionUtils.isEmpty(finBankDtos)){
            for (FinAccBankCardDto finAccBank : accBankCardList){
                for (FinBankDto finBankDto : finBankDtos){
                    if (finAccBank.getBankShortName().equals(finBankDto.getBankShortName())){
                        finAccBank.setBankName(finBankDto.getBankName());
                        finAccBank.setDailyLimit(finBankDto.getDailyLimit());
                        finAccBank.setSingleOrderLimit(finBankDto.getSingleOrderLimit());
                        finAccBank.setBankLogo(finBankDto.getBankLogo());

                        retList.add(finAccBank);
                    }
                }
            }
        }

        return ResultDtoFactory.toAck("查询成功", accBankCardList);
    }

    /**
     * 申购
     * @param applyPurchaseDto
     * @return
     */
    @RequestMapping(value = "applyPurchase", method = RequestMethod.POST)
    public ResultDto<Object> applyPurchase(@RequestBody @Valid ApplyPurchaseDto applyPurchaseDto, BindingResult result){

        if (result.hasErrors()){
            return ResultDtoFactory.toNack(result.getFieldError().getDefaultMessage());
        }

        String userId = SecurityContext.getCurrentUserId();
        FinAccBankCardDto finAccBankCardDto =
                finAccBankCardService.getFinAccBankCardByBankCardNoUserId(applyPurchaseDto.getBankCardNo(), userId, EFinOrg.ZRFUNDS);
        if (finAccBankCardDto == null){
            return ResultDtoFactory.toNack("理财账户信息不存在");
        }

        FinFundDetailDto dto = finFundDetailService.getFinFundDetailById(applyPurchaseDto.getFundId());
        if (dto == null){
            return ResultDtoFactory.toNack("基金信息不存在");
        }

        //插入交易历史表
        FinTxHistoryDto finTxHistoryDto = new FinTxHistoryDto();
        finTxHistoryDto.setUserId(userId);
        finTxHistoryDto.setOrderDate(new Date());
        finTxHistoryDto.setTxType(ETxType.APPLY_PUR);
        finTxHistoryDto.setFinFundId(dto.getFinFundId());
        finTxHistoryDto.setTxFee(applyPurchaseDto.getPurchase());
        finTxHistoryDto.setTxStatus(ETxStatus.INPROCESS);
        finTxHistoryDto.setTxFee(applyPurchaseDto.getPurchase());
        finTxHistoryDto.setExtTxAccId(finAccBankCardDto.getExtTxAccId());

        finTxHistoryDto = finTxHistoryService.addFinTxHistory(finTxHistoryDto);

        //申购
        NormApplyPurReq reqDto = new NormApplyPurReq();
        reqDto.setTransactionAccountID(finAccBankCardDto.getExtTxAccId());
        reqDto.setApplicationNo(finTxHistoryDto.getFinTxId());
        reqDto.setFundCode(dto.getFinFundCode());
        reqDto.setApplicationAmount(applyPurchaseDto.getPurchase());

        ClientSaltDto saltDto = clientSaltService.getClientSalt(zrfundsClientId);
        NormApplyPurResp normApplyPurResp = zrFundsHttpService.applyPurchase(reqDto, saltDto.getSalt());
        //申购失败
        if (normApplyPurResp == null){
            updateTxHistoryStatus(finTxHistoryDto.getFinTxId(), null, ETxStatus.FAILURE);
            return ResultDtoFactory.toNack("申购失败");
        }
        if (!EReturnCode.SUCCESS.getText().equals(normApplyPurResp.getReturnCode())){
            updateTxHistoryStatus(finTxHistoryDto.getFinTxId(), normApplyPurResp.getAppSheetSerialNo(), ETxStatus.FAILURE);
            return ResultDtoFactory.toNack("申购失败," + normApplyPurResp.getReturnMsg());
        }

        //申购成功，后台轮询交易结果后更新交易状态
        updateTxHistoryStatus(finTxHistoryDto.getFinTxId(), normApplyPurResp.getAppSheetSerialNo(), ETxStatus.INPROCESS);

        SuperCashTradeQueryDto queryDto = new SuperCashTradeQueryDto();
        queryDto.setFinFundId(dto.getFinFundId());
        queryDto.setApplicationNo(finTxHistoryDto.getFinTxId());
        queryDto.setTransactionAccountID(finAccBankCardDto.getExtTxAccId());

        return ResultDtoFactory.toAck("申购成功，份额确认中...", queryDto);
    }

    /**
     * 赎回
     * @param redeemDto
     * @return
     */
    @RequestMapping(value = "redeem", method = RequestMethod.POST)
    public ResultDto<Object> redeem(@RequestBody @Valid RedeemDto redeemDto, BindingResult result){

        if (result.hasErrors()){
            return ResultDtoFactory.toNack(result.getFieldError().getDefaultMessage());
        }

        String userId = SecurityContext.getCurrentUserId();
        FinAccBankCardDto finAccBankCardDto =
                finAccBankCardService.getFinAccBankCardByBankCardNoUserId(redeemDto.getBankCardNo(), userId, EFinOrg.ZRFUNDS);
        if (finAccBankCardDto == null){
            return ResultDtoFactory.toNack("理财账户信息不存在");
        }
        String txAccId = finAccBankCardDto.getExtTxAccId();

        FinFundDetailDto dto = finFundDetailService.getFinFundDetailById(redeemDto.getFundId());
        if (dto == null){
            return ResultDtoFactory.toNack("基金信息不存在");
        }

        //插入交易历史表
        FinTxHistoryDto finTxHistoryDto = new FinTxHistoryDto();
        finTxHistoryDto.setUserId(userId);
        finTxHistoryDto.setOrderDate(new Date());
        finTxHistoryDto.setTxType(ETxType.REDEEM);
        finTxHistoryDto.setFinFundId(redeemDto.getFundId());
        finTxHistoryDto.setTxStatus(ETxStatus.INPROCESS);
        finTxHistoryDto.setTxFee(redeemDto.getRedeemAmt());
        finTxHistoryDto.setExtTxAccId(txAccId);
        finTxHistoryDto = finTxHistoryService.addFinTxHistory(finTxHistoryDto);
        String finTxId = finTxHistoryDto.getFinTxId();

        ClientSaltDto saltDto = clientSaltService.getClientSalt(zrfundsClientId);

        //根据赎回类型进行赎回
        if (ERedeemType.REDEEM_NORMAL == redeemDto.getRedeemType()){
            RedeemNormReq reqDto = new RedeemNormReq();
            reqDto.setTransactionAccountID(txAccId);
            reqDto.setApplicationNo(finTxId);
            reqDto.setFundCode(dto.getFinFundCode());
            reqDto.setApplicationVol(redeemDto.getRedeemAmt());

            RedeemNormResp resp = zrFundsHttpService.redeemNormal(reqDto, saltDto.getSalt());
            if (resp == null){
                updateTxHistoryStatus(finTxId, null, ETxStatus.FAILURE);
                return ResultDtoFactory.toNack("普通赎回失败");
            }
            if (!EReturnCode.SUCCESS.getText().equals(resp.getReturnCode())){
                updateTxHistoryStatus(finTxId, null, ETxStatus.FAILURE);
                return ResultDtoFactory.toNack("普通赎回失败，" + resp.getReturnMsg());
            }

        }else if (ERedeemType.REDEEM_REAL_TIME == redeemDto.getRedeemType()){
            RedeemRealTimeReq reqDto = new RedeemRealTimeReq();
            reqDto.setTransactionAccountID(txAccId);
            reqDto.setApplicationNo(finTxId);
            reqDto.setFundCode(dto.getFinFundCode());
            reqDto.setApplicationVol(redeemDto.getRedeemAmt());
            reqDto.setShareClass(EShareClass.BEFORE_PAID.getValue());

            RedeemRealTimeResp resp = zrFundsHttpService.redeemRealTime(reqDto, saltDto.getSalt());
            if (resp == null){
                updateTxHistoryStatus(finTxId, null, ETxStatus.FAILURE);
                return ResultDtoFactory.toNack("实时赎回失败");
            }
            if (!EReturnCode.SUCCESS.getText().equals(resp.getReturnCode())){
                updateTxHistoryStatus(finTxId, null, ETxStatus.FAILURE);
                return ResultDtoFactory.toNack("实时赎回失败," + resp.getReturnMsg());
            }
        }

        return ResultDtoFactory.toAck("赎回处理中...");
    }

    /**
     * 查询超级现金宝交易结果
     * @param queryDto
     * @param result
     * @return
     */
    @RequestMapping(value = "querySuperCashTrade", method = RequestMethod.POST)
    public ResultDto<Object> querySuperCashTrade(@RequestBody @Valid SuperCashTradeQueryDto queryDto, BindingResult result){
        if (result.hasErrors()){
            return ResultDtoFactory.toNack(result.getFieldError().getDefaultMessage());
        }

        String userId = SecurityContext.getCurrentUserId();

        ClientSaltDto saltDto = clientSaltService.getClientSalt(zrfundsClientId);

        SuperCashTradeQueryReq reqDto = new SuperCashTradeQueryReq();
        reqDto.setTransactionAccountID(queryDto.getTransactionAccountID());
        reqDto.setApplicationNo(queryDto.getApplicationNo());
        SuperCashTradeQueryResp resp = zrFundsHttpService.querySuperCashTrade(reqDto, saltDto.getSalt());

        if (resp == null){
            updateTxHistoryStatus(queryDto.getApplicationNo(), null, ETxStatus.FAILURE);
            return ResultDtoFactory.toNack("超级现金宝交易结果查询失败");
        }
        if (!EReturnCode.SUCCESS.getText().equals(resp.getReturnCode())){
            updateTxHistoryStatus(queryDto.getApplicationNo(), null, ETxStatus.FAILURE);
            return ResultDtoFactory.toNack("超级现金宝交易结果查询失败," + resp.getReturnMsg());
        }

        if (!CollectionUtils.isEmpty(resp.getList())){
            SuperCashTradeQuerySubResp subResp = resp.getList().get(0);
            if (subResp.getConfirmFlag() == EConfirmFlag.CONFIRM_SUCCESS.getFlag()
                    || subResp.getConfirmFlag() == EConfirmFlag.REALTIME_CONFIRM_SUCCESS.getFlag()){

                FinFundDetailDto dto = finFundDetailService.getFinFundDetailById(queryDto.getFinFundId());
                if (dto == null){
                    return ResultDtoFactory.toNack("基金信息不存在");
                }

                updateTxHistoryStatus(queryDto.getApplicationNo(), null, ETxStatus.SUCCESS);
                updateProfit(userId, queryDto.getFinFundId(), dto.getFinFundCode(), queryDto.getTransactionAccountID(), "超级现金宝");

                return ResultDtoFactory.toAck("扣款成功");
            }else if (subResp.getConfirmFlag() == EConfirmFlag.CONFIRM_FAILURE.getFlag()){
                updateTxHistoryStatus(queryDto.getApplicationNo(), null, ETxStatus.FAILURE);
            }
        }

        return ResultDtoFactory.toNack("超级现金宝交易结果查询失败");
    }

    /**
     * 根据第三方理财账号查询持有资产信息
     * @param extTxAccId
     * @return
     */
    @RequestMapping(value = "queryFinAccHoldAsset", method = RequestMethod.GET)
    public ResultDto<Object> queryFinAccHoldAsset(String extTxAccId){
        try {
            FinAccFundProfitDto dto = finAccFundProfitService.getFinAccHoldAsset(extTxAccId);
            if (dto == null){
                return ResultDtoFactory.toNack("不存在指定的理财账号");
            }
            return ResultDtoFactory.toAck("查询成功", dto);
        } catch (BizServiceException e) {
            return ResultDtoFactory.toNack("查询失败", e.getError());
        }
    }

    /**
     * 检查用户输入的卡号是不是用户在金服的已有卡
     * @param bankCardNo
     * @return
     */
    @GetMapping(value = "/checkBankCardExist")
    public ResultDto checkBankCardExist(String bankCardNo){
        String userId = SecurityContext.getCurrentUserId();
        BankCardDto bankCardDto = bankService.getBankCard(userId, bankCardNo);
        if (bankCardDto != null){
            return ResultDtoFactory.toAck();
        }else {
            return ResultDtoFactory.toNack("");
        }
    }

    /**
     * 更新收益信息
     * @param userId
     * @param finFundId
     * @param fundCode
     * @param extTxAccId
     * @param desc
     * @return
     */
    private ResultDto<Object> updateProfit(String userId, Long finFundId, String fundCode, String extTxAccId, String desc){
        //查询超级现金宝份额
        FinTxHistoryDto finTxHistoryDto = new FinTxHistoryDto();
        finTxHistoryDto.setUserId(userId);
        finTxHistoryDto.setOrderDate(new Date());
        finTxHistoryDto.setTxType(ETxType.QUERY);
        finTxHistoryDto.setFinFundId(finFundId);
        finTxHistoryDto.setTxStatus(ETxStatus.INPROCESS);

        finTxHistoryDto = finTxHistoryService.addFinTxHistory(finTxHistoryDto);

        SuperCashQueryShareReq scShareReq = new SuperCashQueryShareReq();
        scShareReq.setTransactionAccountID(extTxAccId);
        scShareReq.setApplicationNo(finTxHistoryDto.getFinTxId());
        scShareReq.setFundCode(fundCode);

        ClientSaltDto saltDto = clientSaltService.getClientSalt(zrfundsClientId);
        SuperCashQueryShareResp superCashQueryShareResp = zrFundsHttpService.querySuperCashShare(scShareReq, saltDto.getSalt());
        if (superCashQueryShareResp == null){
            updateTxHistoryStatus(finTxHistoryDto.getFinTxId(), null, ETxStatus.FAILURE);
            return ResultDtoFactory.toNack(desc + "份额查询失败");
        }
        if (!EReturnCode.SUCCESS.getText().equals(superCashQueryShareResp.getReturnCode())){
            updateTxHistoryStatus(finTxHistoryDto.getFinTxId(), null, ETxStatus.FAILURE);
            return ResultDtoFactory.toNack(desc + "份额查询失败," + superCashQueryShareResp.getReturnMsg());
        }

        updateTxHistoryStatus(finTxHistoryDto.getFinTxId(), null, ETxStatus.SUCCESS);

        if (!CollectionUtils.isEmpty(superCashQueryShareResp.getList())){
            SuperCashQueryShareSubResp subResp = superCashQueryShareResp.getList().get(0);
            Date profitDate = DateHelper.getStartDate(new Date());
            //更新收益记录
            FinProfitHistoryDto finProfitHistoryDto = new FinProfitHistoryDto();
            finProfitHistoryDto.setUserId(userId);
            finProfitHistoryDto.setExtTxAccId(extTxAccId);
            finProfitHistoryDto.setFinFundId(finFundId);
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
            profitDto.setExtTxAccId(extTxAccId);
            profitDto.setFinFundId(finFundId);
            finAccFundProfitService.updateFinAccProdProfit(profitDto);
        }

        return ResultDtoFactory.toAck("成功");
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

}
