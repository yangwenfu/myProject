package com.xinyunlian.jinfu.loan.service;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.xinyunlian.jinfu.common.enums.EErrorCode;
import com.xinyunlian.jinfu.common.exception.BizServiceException;
import com.xinyunlian.jinfu.common.security.SecurityContext;
import com.xinyunlian.jinfu.common.util.DateHelper;
import com.xinyunlian.jinfu.common.util.NumberUtil;
import com.xinyunlian.jinfu.contract.dto.BsViewContractReq;
import com.xinyunlian.jinfu.contract.dto.UserContractDto;
import com.xinyunlian.jinfu.contract.enums.ECntrctTmpltType;
import com.xinyunlian.jinfu.contract.service.BestSignService;
import com.xinyunlian.jinfu.contract.service.ContractService;
import com.xinyunlian.jinfu.loan.dto.contract.LoanContractDto;
import com.xinyunlian.jinfu.loan.dto.resp.LoanApplDto;
import com.xinyunlian.jinfu.loan.dto.resp.LoanDtlDto;
import com.xinyunlian.jinfu.product.dto.LoanProductDetailDto;
import com.xinyunlian.jinfu.router.dto.FinanceSourceDto;
import com.xinyunlian.jinfu.router.service.FinanceSourceService;
import com.xinyunlian.jinfu.store.dto.StoreInfDto;
import com.xinyunlian.jinfu.store.service.StoreService;
import com.xinyunlian.jinfu.user.dto.UserInfoDto;
import com.xinyunlian.jinfu.user.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

/**
 * Created by Willwang on 2017/1/6.
 */
@Service
public class PrivateContractService {

    @Autowired
    private UserService userService;

    @Autowired
    private ContractService contractService;

    @Autowired
    private LoanApplService loanApplService;

    @Autowired
    private StoreService storeService;

    @Autowired
    private LoanService loanService;

    @Autowired
    private BestSignService bestSignService;

    @Autowired
    private FinanceSourceService financeSourceService;

    @Value(value = "${local_domain}")
    private String LOCAL_DOMAIN;

    /**
     * 征信授权协议地址
     */
    private String ZXSQ_URL = "/jinfu/web/loan/contract/user/zxsq";

    /**
     * 代扣协议地址
     */
    private String DK_URL = "/jinfu/web/loan/contract/user/dk";

    /**
     * 贷款协议
     */
    private String LOAN_URL = "/jinfu/web/loan/contract/user/loan";

    /**
     * 合同地址
     */
    private String CONTRACT_URL = "/jinfu/web/loan/contract/user/get";

    private static final Logger LOGGER = LoggerFactory.getLogger(PrivateContractService.class);

    /**
     * 获取贷款相关的合同列表
     * @param loanId
     * @return
     */
    public List<LoanContractDto> list(String loanId){
        String userId = SecurityContext.getCurrentUserId();

        LoanDtlDto loan = loanService.get(loanId);

        if(!userId.equals(loan.getUserId())){
            throw new BizServiceException(EErrorCode.LOAN_APPL_NOT_YOURS, "不能访问不是自己的贷款");
        }


        List<LoanContractDto> rs = new ArrayList<>();

        LoanContractDto item;

        FinanceSourceDto financeSourceDto = financeSourceService.findById(loan.getFinanceSourceId());

        switch(financeSourceDto.getType()){
            case OWN:
                item = new LoanContractDto();
                item.setName(ECntrctTmpltType.DKL01001.getText());
                item.setUrl(LOCAL_DOMAIN + DK_URL + "?loanId=" + loanId);
                rs.add(item);

                item = new LoanContractDto();
                item.setUrl(LOCAL_DOMAIN + LOAN_URL + "?loanId=" + loanId);
                switch (loan.getRepayMode()){
                    case MONTH_AVE_CAP_PLUS_INTR:
                        item.setName(ECntrctTmpltType.YDL01001.getText());
                        break;
                    case INTR_PER_DIEM:
                        item.setName(ECntrctTmpltType.YDL01002.getText());
                        break;
                    case MONTH_AVE_CAP_AVG_INTR:
                        item.setName(ECntrctTmpltType.YDL01006.getText());
                        LoanContractDto zxxy = new LoanContractDto();
                        zxxy.setName(ECntrctTmpltType.ZXL01006.getText());
                        zxxy.setUrl(LOCAL_DOMAIN + CONTRACT_URL +  "?bizId=" + loanId.replace("L","")
                                + "&type=" + ECntrctTmpltType.ZXL01006.getCode());
                        rs.add(zxxy);
                        break;
                }
                rs.add(item);

                item = new LoanContractDto();
                item.setName(ECntrctTmpltType.ZXSQ.getText());
                item.setUrl(LOCAL_DOMAIN + ZXSQ_URL);
                rs.add(item);
                break;
            case AITOUZI:
                item = new LoanContractDto();
                item.setName(ECntrctTmpltType.ATZ_LOAN.getText());
                item.setUrl(LOCAL_DOMAIN + PrivateAitouziContractService.ATZ_LOAN_URL + "?applId=" + loan.getApplId());
                rs.add(item);
                break;
            default:
                break;
        }

        return rs;
    }

    /**
     * 创建征信授权协议参数
     * @param userInfoDto
     * @return
     */
    public UserContractDto buildContractZXSQ(UserInfoDto userInfoDto) {
        UserContractDto userContractDto = new UserContractDto();
        userContractDto.setTemplateType(ECntrctTmpltType.ZXSQ);
        Map<String, String> model = new HashMap<>();
        userContractDto.setModel(model);
        model.put("signName", userInfoDto.getUserName());
        model.put("idCardNo", userInfoDto.getIdCardNo());
        model.put("signDate", DateHelper.formatDate(new Date(), DateHelper.SIMPLE_DATE_YMD_CN));
        return userContractDto;
    }

    /**
     * 签署征信授权协议
     */
    public void signZxsq() {
        String userId = SecurityContext.getCurrentUserId();
        UserContractDto userContractDto = contractService.getUserContract(userId, ECntrctTmpltType.ZXSQ);
        if (userContractDto == null) {
            UserInfoDto userInfoDto = userService.findUserByUserId(userId);
            contractService.saveContract(buildContractZXSQ(userInfoDto), userInfoDto);
        }
    }

    /**
     * 签署代扣协议
     * @param user
     * @param loan
     * @param product
     */
    public void signDk(UserInfoDto user, LoanDtlDto loan, LoanProductDetailDto product){
        UserContractDto DKL01001 = buildContractDKL01001(user);
        DKL01001.setBizId(loan.getLoanId());
        DKL01001.setProdId(product.getProductId());
        DKL01001.setProdName(product.getProductName());
        contractService.saveContract(DKL01001, user);
    }

    /**
     * 签署贷款协议
     * @param user
     * @param loan
     * @param product
     */
    public void signLoan(UserInfoDto user, LoanDtlDto loan, LoanProductDetailDto product){
        UserContractDto loanContract = buildLoanContract(loan.getApplId());
        loanContract.setProdId(product.getProductId());
        loanContract.setProdName(product.getProductName());
        loanContract.setBizId(loan.getLoanId());
        contractService.saveContract(loanContract, user);
    }

    /**
     * 获取贷款协议
     * @param applId 申请编号
     * @return
     */
    public UserContractDto buildLoanContract(String applId){
        LoanApplDto loanApplDto = loanApplService.get(applId);
        LOGGER.debug("loan apply data,{}", loanApplDto);
        return this.buildLoanContract(loanApplDto);
    }

    /**
     * 获取贷款协议
     * @param loanApplDto 申请基础信息
     * @return
     */
    public UserContractDto buildLoanContract(LoanApplDto loanApplDto){
        UserContractDto userContractDto = null;
        UserInfoDto userInfoDto = userService.findUserByUserId(SecurityContext.getCurrentUser().getUserId());
        switch(loanApplDto.getRepayMode()){
            case MONTH_AVE_CAP_PLUS_INTR:
                userContractDto = contractService.getContract(buildContractYDL01001(loanApplDto.getApplId(), userInfoDto));
                break;
            case INTR_PER_DIEM:
                List<StoreInfDto> storeInfDtos = storeService.findByUserId(loanApplDto.getUserId());
                //拿一个店铺信息
                if(storeInfDtos.size() > 0){
                    StoreInfDto storeInfDto = storeInfDtos.get(0);
                    userContractDto = contractService.getContract(buildContractYDL01002(loanApplDto.getApplId(), userInfoDto, storeInfDto));
                }
                break;
            case MONTH_AVE_CAP_AVG_INTR:
                userContractDto = contractService.getContract(buildContractYDL01006(loanApplDto.getApplId(), userInfoDto));
                break;
        }

        LOGGER.debug("build contract data", userContractDto);
        
        return userContractDto;
    }


    /**
     * 生成合同
     * @param bizId
     * @param templateType
     * @return
     */
    public UserContractDto buildLoanContract(String bizId,ECntrctTmpltType templateType){
        UserContractDto userContractDto = null;
        UserInfoDto userInfoDto = userService.findUserByUserId(SecurityContext.getCurrentUser().getUserId());
        switch(templateType){
            case YDL01006:
                //贷款协议
                userContractDto = contractService.getContract(buildContractYDL01006(bizId, userInfoDto));
                break;
            case ZXL01006:
                userContractDto = contractService.getContract(buildContractZXL01006(bizId, userInfoDto));
                break;
            case DKL01001:
                //委托扣款协议
                userContractDto = contractService.getContract(buildContractDKL01001(userInfoDto));
                break;
        }

        LOGGER.debug("build contract data", userContractDto);

        return userContractDto;
    }


    /**
     * 生成云速贷贷款协议
     *
     * @param applId
     * @param userInfoDto
     * @return
     */
    public UserContractDto buildContractYDL01001(String applId, UserInfoDto userInfoDto) {
        UserContractDto userContractDto = new UserContractDto();
        Map<String, String> model = new HashMap<>();
        userContractDto.setModel(model);
        LoanApplDto loanApplDto = loanApplService.get(applId);

        userContractDto.setTemplateType(ECntrctTmpltType.YDL01001);

        model.put("signName", userInfoDto.getUserName());
        model.put("idCardNo", userInfoDto.getIdCardNo());
        model.put("phone", userInfoDto.getMobile());
        model.put("loanAmt", loanApplDto.getApprAmt().toString());
        model.put("loanAmtDX", NumberUtil.toRMB(loanApplDto.getApprAmt().doubleValue()));
        // 还款期限
        StringBuilder dueDate = new StringBuilder();
        dueDate.append(loanApplDto.getApprTermLen())
                .append(loanApplDto.getTermType().getUnit())
                .append("后");
        model.put("dueDate", dueDate.toString());
        //利率
        BigDecimal loanRt = loanApplDto.getIntrRateType().getDay(loanApplDto.getLoanRt())
                .multiply(new BigDecimal(100));
        String loanRtStr = NumberUtil.roundTwo(loanRt).toString() + "%";
        model.put("loanRT", loanRtStr);
        model.put("signDate", DateHelper.formatDate(new Date(), DateHelper.SIMPLE_DATE_YMD_CN));
        return userContractDto;
    }

    /**
     * 生成云随贷综合业务协议
     * @param applId
     * @param userInfoDto
     * @return
     */
    public UserContractDto buildContractYDL01002(String applId, UserInfoDto userInfoDto, StoreInfDto storeInfDto) {
        UserContractDto userContractDto = new UserContractDto();
        Map<String, String> model = new HashMap<>();
        userContractDto.setModel(model);
        LoanApplDto loanApplDto = loanApplService.get(applId);

        userContractDto.setTemplateType(ECntrctTmpltType.YDL01002);

        model.put("signName", userInfoDto.getUserName());
        model.put("idCardNo", userInfoDto.getIdCardNo());
        model.put("loanAmt", loanApplDto.getApprAmt().toString());
        //合同金额是以万元为单位的
        double loanAmt = NumberUtil.roundTwo(loanApplDto.getApprAmt().divide(BigDecimal.valueOf(10000))).doubleValue();
        model.put("loanAmtDx", NumberUtil.toRMB(loanAmt));
        model.put("bizLicenceNo", storeInfDto.getBizLicence());
        model.put("storeName", storeInfDto.getStoreName());
        model.put("tobaccoCertificateNo", storeInfDto.getTobaccoCertificateNo());
        model.put("address", storeInfDto.getAddress());
        model.put("phone", userInfoDto.getMobile());
        model.put("termLenMonth", Integer.toString(loanApplDto.getTermType().getMonth(loanApplDto.getApprTermLen())));
        model.put("startDate", loanApplDto.getApplDate());
        //利率
        BigDecimal loanRt = loanApplDto.getIntrRateType().getMonth(loanApplDto.getLoanRt())
                .multiply(new BigDecimal(100));
        String loanRtStr = NumberUtil.roundTwo(loanRt).toString() + "%";
        model.put("loanRT", loanRtStr);
        String dueDate = DateHelper.formatDate(
                loanApplDto.getTermType().add(DateHelper.getDate(loanApplDto.getApplDate()), loanApplDto.getApprTermLen())
        );

        model.put("endDate", dueDate);
        model.put("signDate", DateHelper.formatDate(new Date(), DateHelper.SIMPLE_DATE_YMD_CN));
        return userContractDto;
    }

    /**
     * 小烟贷贷款综合协议
     *
     * @param applId
     * @param userInfoDto
     * @return
     */
    public UserContractDto buildContractYDL01006(String applId, UserInfoDto userInfoDto) {
        UserContractDto userContractDto = new UserContractDto();
        Map<String, String> model = new HashMap<>();
        userContractDto.setModel(model);
        LoanApplDto loanApplDto = loanApplService.get(applId);

        userContractDto.setTemplateType(ECntrctTmpltType.YDL01006);

        model.put("signName", userInfoDto.getUserName());
        model.put("idCardNo", userInfoDto.getIdCardNo());
        model.put("mobile", userInfoDto.getMobile());
        model.put("loanAmt", loanApplDto.getApprAmt().toString());
        model.put("loanAmtDX", NumberUtil.toRMB(loanApplDto.getApprAmt().doubleValue()));

        BigDecimal totalAmt = loanApplDto.getApprAmt();
        BigDecimal capital = totalAmt
                .divide(new BigDecimal(loanApplDto.getApprTermLen()),2, BigDecimal.ROUND_HALF_UP);
        BigDecimal interest = totalAmt.multiply(loanApplDto.getLoanRt());
        String monthRepayAmt = NumberUtil.roundTwo(capital.add(interest)).toString();
        model.put("monthRepayAmt", monthRepayAmt);
        model.put("monthRepayAmtDx", NumberUtil.toRMB(monthRepayAmt));
        model.put("termLen", loanApplDto.getApprTermLen().toString());
        LocalDate today = LocalDate.now();
        model.put("dueDay", String.valueOf(today.getDayOfMonth()));

        // 还款期限
        StringBuilder dueDate = new StringBuilder();
        dueDate.append(loanApplDto.getApprTermLen())
                .append(loanApplDto.getTermType().getUnit())
                .append("后");
        model.put("dueDate", dueDate.toString());
        //利率
        BigDecimal loanRt = loanApplDto.getIntrRateType().getMonth(loanApplDto.getLoanRt())
                .multiply(new BigDecimal(100));
        String loanRtStr = NumberUtil.roundTwo(loanRt).toString() + "%";
        model.put("loanRT", loanRtStr);
        model.put("signDate", DateHelper.formatDate(new Date(), DateHelper.SIMPLE_DATE_YMD_CN));
        return userContractDto;
    }

    /**
     * 贷款咨询与服务协议
     *
     * @param applId
     * @param userInfoDto
     * @return
     */
    public UserContractDto buildContractZXL01006(String applId, UserInfoDto userInfoDto) {
        UserContractDto userContractDto = new UserContractDto();
        Map<String, String> model = new HashMap<>();
        userContractDto.setModel(model);
        LoanApplDto loanApplDto = loanApplService.get(applId);

        userContractDto.setTemplateType(ECntrctTmpltType.ZXL01006);

        model.put("signName", userInfoDto.getUserName());
        model.put("idCardNo", userInfoDto.getIdCardNo());
        model.put("mobile", userInfoDto.getMobile());
        model.put("loanAmt", loanApplDto.getApprAmt().toString());
        model.put("loanAmtDX", NumberUtil.toRMB(loanApplDto.getApprAmt().doubleValue()));
        model.put("termLen", loanApplDto.getApprTermLen().toString());
        //利率
        BigDecimal loanRt = loanApplDto.getIntrRateType().getDay(loanApplDto.getLoanRt())
                .multiply(new BigDecimal(100));
        String loanRtStr = NumberUtil.roundTwo(loanRt).toString() + "%";
        model.put("loanRT", loanRtStr);


        if(loanApplDto.getServiceFeeRt() == null){
            loanApplDto.setServiceFeeRt(BigDecimal.ZERO);
        }

       ;
        String serviceFeeMonthRtStr = NumberUtil.roundTwo(loanApplDto.getServiceFeeMonthRt()
                .multiply(new BigDecimal(100))).toString();

        BigDecimal serviceFee = NumberUtil.roundTwo(loanApplDto.getApprAmt()
                .multiply(loanApplDto.getServiceFeeRt()));
        model.put("serviceFeeMonthRt",serviceFeeMonthRtStr);
        if(serviceFee.compareTo(BigDecimal.ZERO) > 0 ) {
            model.put("serviceFee", serviceFee.toString());
            model.put("serviceFeeDx", NumberUtil.toRMB(serviceFee.toString()));
        }else{
            model.put("serviceFee", "");
            model.put("serviceFeeDx", "");
        }

        if(loanApplDto.getServiceFeeMonthRt() == null){
            loanApplDto.setServiceFeeMonthRt(BigDecimal.ZERO);
        }

        BigDecimal serviceFeeMonth = NumberUtil.roundTwo(loanApplDto.getApprAmt()
                .multiply(loanApplDto.getServiceFeeMonthRt())
                .multiply(BigDecimal.valueOf(loanApplDto.getApprTermLen())));
        if(serviceFeeMonth.compareTo(BigDecimal.ZERO) > 0) {
            model.put("serviceFeeMonth", serviceFeeMonth.toString());
            model.put("serviceFeeMonthDx", NumberUtil.toRMB(serviceFeeMonth.toString()));
        }else{
            model.put("serviceFeeMonth", "");
            model.put("serviceFeeMonthDx","");
        }

        model.put("signDate", DateHelper.formatDate(new Date(), DateHelper.SIMPLE_DATE_YMD_CN));
        return userContractDto;
    }

    /**
     * 获取用户签署过的合同
     * @param bizId
     * @param type
     * @return
     */
    public Object getUserLoan(String bizId,ECntrctTmpltType type){

        UserContractDto bestSignContract = contractService.getUserContractByBizId(SecurityContext.getCurrentUserId(), type, bizId);

        if(bestSignContract != null && StringUtils.isNotEmpty(bestSignContract.getBsSignid())){
            try {
                //存在的话，则获取上上签的合同
                BsViewContractReq req = new BsViewContractReq();
                req.setFsdid(bestSignContract.getBsSignid());
                req.setDocid(bestSignContract.getBsDocid());
                String contractUrl = bestSignService.viewContract(req);
                return new ModelAndView("redirect:" + contractUrl);
            } catch (Exception e) {
                LOGGER.warn("获取上上签合同失败", e);
            }
        }

        return "";
    }

    /**
     * 获取用户签署过的贷款协议
     * @param loanId
     * @return
     */
    public Object getUserLoan(String loanId){
        LoanDtlDto loan = loanService.get(loanId);

        if(loan == null){
            return "";
        }

        ECntrctTmpltType tmpltType = null;
        switch(loan.getRepayMode()){
            case MONTH_AVE_CAP_PLUS_INTR:
                tmpltType = ECntrctTmpltType.YDL01001;
                break;
            case INTR_PER_DIEM:
                tmpltType = ECntrctTmpltType.YDL01002;
                break;
            case MONTH_AVE_CAP_AVG_INTR:
                tmpltType = ECntrctTmpltType.YDL01006;
                break;
        }

        UserContractDto bestSignContract = contractService.getUserContractByBizId(SecurityContext.getCurrentUserId(), tmpltType, loan.getApplId());

        if(bestSignContract != null && StringUtils.isNotEmpty(bestSignContract.getBsSignid())){
            try {
                //存在的话，则获取上上签的合同
                BsViewContractReq req = new BsViewContractReq();
                req.setFsdid(bestSignContract.getBsSignid());
                req.setDocid(bestSignContract.getBsDocid());
                String contractUrl = bestSignService.viewContract(req);
                return new ModelAndView("redirect:" + contractUrl);
            } catch (Exception e) {
                LOGGER.warn("获取上上签合同失败", e);
            }
        }else{
            UserContractDto oldContract = contractService.getUserContractByBizId(SecurityContext.getCurrentUserId(), tmpltType, loanId);
            if(oldContract != null){
                return oldContract.getContent();
            }
        }

        return "";
    }

    /**
     * 获取用户已经签署的代扣协议
     * @param loanId
     * @return
     */
    public String getUserDk(String loanId){
        UserContractDto userContractDto = contractService.getUserContractByBizId(SecurityContext.getCurrentUserId(), ECntrctTmpltType.DKL01001, loanId);
        if(userContractDto != null){
            return userContractDto.getContent();
        }
        return "";
    }

    /**
     * 获取用户已经签署过的征信授权协议
     * @return
     */
    public String getUserZxsq(){
        UserContractDto userContractDto = contractService.getUserContract(SecurityContext.getCurrentUserId(), ECntrctTmpltType.ZXSQ);
        if(userContractDto != null){
            return userContractDto.getContent();
        }
        return "";
    }

    /**
     * 生成委托扣款协议
     *
     * @param userInfoDto
     * @return
     */
    public UserContractDto buildContractDKL01001(UserInfoDto userInfoDto) {
        UserContractDto userContractDto = new UserContractDto();
        userContractDto.setTemplateType(ECntrctTmpltType.DKL01001);
        Map<String, String> model = new HashMap<>();
        userContractDto.setModel(model);
        model.put("signName", userInfoDto.getUserName());
        model.put("signDate", DateHelper.formatDate(new Date(), DateHelper.SIMPLE_DATE_YMD_CN));
        return userContractDto;
    }

    public static void main(String[] args) {

        LoanApplDto loanApplDto = new LoanApplDto();
        loanApplDto.setApprAmt(new BigDecimal(2222.00));
        loanApplDto.setServiceFeeMonthRt(new BigDecimal(0.03000000));
        loanApplDto.setServiceFeeRt(new BigDecimal(0.02000000));
        loanApplDto.setApprTermLen(6);
        loanApplDto.setLoanRt(new BigDecimal(0.03));
        if(loanApplDto.getServiceFeeRt() == null){
            loanApplDto.setServiceFeeRt(BigDecimal.ZERO);
        }
        String serviceFee = NumberUtil.roundTwo(loanApplDto.getApprAmt()
                .multiply(loanApplDto.getServiceFeeRt())).toString();

        System.out.println(loanApplDto.getServiceFeeRt().toString());
        System.out.println(serviceFee);
        System.out.println(NumberUtil.toRMB(serviceFee));

        BigDecimal totalAmt = loanApplDto.getApprAmt();
        BigDecimal capital = totalAmt
                .divide(new BigDecimal(loanApplDto.getApprTermLen()),2, BigDecimal.ROUND_HALF_UP);
        BigDecimal interest = totalAmt.multiply(loanApplDto.getLoanRt());
        String monthRepayAmt = NumberUtil.roundTwo(capital.add(interest)).toString();
     /*   String serviceFee = NumberUtil.roundTwo(loanApplDto.getApprAmt()
                .multiply(loanApplDto.getServiceFeeRt())).toString();
        model.put("serviceFeeRt",loanApplDto.getServiceFeeRt().toString());
        model.put("serviceFee",serviceFee);
        model.put("serviceFeeDx", NumberUtil.toRMB(serviceFee));*/

    }
}
