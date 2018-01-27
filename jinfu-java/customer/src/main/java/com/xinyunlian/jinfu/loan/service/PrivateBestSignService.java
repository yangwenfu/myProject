package com.xinyunlian.jinfu.loan.service;

import com.xinyunlian.jinfu.common.dto.bscontract.*;
import com.xinyunlian.jinfu.common.enums.EErrorCode;
import com.xinyunlian.jinfu.common.exception.BizServiceException;
import com.xinyunlian.jinfu.common.security.SecurityContext;
import com.xinyunlian.jinfu.common.security.StaticResourceSecurity;
import com.xinyunlian.jinfu.common.util.*;
import com.xinyunlian.jinfu.contract.dto.*;
import com.xinyunlian.jinfu.contract.enums.EBsSignType;
import com.xinyunlian.jinfu.contract.enums.ECntrctTmpltType;
import com.xinyunlian.jinfu.contract.enums.ESignedMark;
import com.xinyunlian.jinfu.contract.service.BestSignService;
import com.xinyunlian.jinfu.contract.service.ContractService;
import com.xinyunlian.jinfu.loan.dto.bestsign.LoanBestSignDto;
import com.xinyunlian.jinfu.loan.dto.resp.LoanApplDto;
import com.xinyunlian.jinfu.user.dto.UserExtDto;
import com.xinyunlian.jinfu.user.dto.UserInfoDto;
import com.xinyunlian.jinfu.user.service.UserExtService;
import com.xinyunlian.jinfu.user.service.UserService;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author willwang
 */
@Service
public class PrivateBestSignService {

    @Autowired
    private BestSignService bestSignService;

    @Autowired
    private UserService userService;

    @Autowired
    private UserExtService userExtService;

    @Autowired
    private ContractService contractService;

    @Autowired
    private LoanApplService loanApplService;

    @Autowired
    private PrivateContractService privateContractService;

    private static final Logger LOGGER = LoggerFactory.getLogger(PrivateBestSignService.class);

    private static final String RET_CODE = "100000";

    /**
     * 所使用的章，默认用小贷公司的章
     */
    private String sealName;

    /**
     * 签署成功后的拦截页面URL
     */
    private String returnUrl;

    @Value(value = "${best.sign.province}")
    private String DEFAULT_PROVINCE;

    @Value(value = "${best.sign.city}")
    private String DEFAULT_CITY;

    @Value(value = "${best.sign.area}")
    private String DEFAULT_AREA;

    @Value(value = "${best.sign.address}")
    private String DEFAULT_ADDRESS;

    @Value(value = "${local_domain}")
    private String LOCAL_DOMAIN;

    public String getSealName() {
        return sealName;
    }

    public void setSealName(String sealName) {
        this.sealName = sealName;
    }

    public String getReturnUrl() {
        return returnUrl;
    }

    public void setReturnUrl(String returnUrl) {
        this.returnUrl = returnUrl;
    }

    /**
     * 获取待签署的合同地址
     */
    public LoanBestSignDto get(String applId, ECntrctTmpltType type){
        String userId = SecurityContext.getCurrentUserId();

        LoanBestSignDto rs = new LoanBestSignDto();

        rs.setApplId(applId);
        rs.setSignUrl("");
        rs.setContractName(type.getText());

        UserContractDto userContractDto = contractService.getUserContractByBizId(userId, type, applId);

        this.registerCA(this.getUser(userId));

        if(userContractDto != null && !StringUtils.isEmpty(userContractDto.getBsSignid())){
            this.checkStatus(userContractDto);
        }

        //不存在同类合同，重新渲染
        if(userContractDto == null){
            userContractDto = this.render(applId, type);
            userContractDto.setBizId(applId);
            userContractDto.setUserId(userId);
            userContractDto.setSignedMark(ESignedMark.INIT);
            UserInfoDto userInfoDto = userService.findUserByUserId(userId);
            userContractDto = contractService.saveContractSync(userContractDto, userInfoDto);
        }

        //合同如果没有发送给上上签，重新发送
        if(StringUtils.isEmpty(userContractDto.getBsSignid())){
            userContractDto = this.send(userContractDto);
        }

        //判定合同签署状态，如果没有盖章，自动盖章
        if(userContractDto.getSignedMark() == ESignedMark.INIT){
            this.signCompany(userContractDto);
        }

        //判定签署状态，如果已经签署，直接返回空
        if(userContractDto.getSignedMark() == ESignedMark.SEAL){
            String url = this.signUser(userContractDto);
            rs.setSignUrl(url);
        }

        rs.setReturnUrl(this.getReturnUrl());

        return rs;
    }

    /**
     * 渲染爱投资合同
     * @param type
     * @return
     */
    private UserContractDto render(String applId, ECntrctTmpltType type){

        UserContractDto userContractDto = null;

        switch(type){
            case ATZ_LOAN:
                userContractDto = this.buildAtzLoan();
                break;
            case YDL01001:
                userContractDto = privateContractService.buildLoanContract(applId);
                break;
            case YDL01002:
                userContractDto = privateContractService.buildLoanContract(applId);
                break;
        }

        if(userContractDto == null){
            throw new BizServiceException(EErrorCode.LOAN_BEST_SIGN_ERROR, "best sign render error");
        }

        return contractService.getContract(userContractDto);
    }


    /**
     * 生成爱投资个人借款合同
     * @return
     */
    public UserContractDto buildAtzLoan(){
        UserContractDto userContractDto = new UserContractDto();
        Map<String, String> model = new HashMap<>();


        userContractDto.setTemplateType(ECntrctTmpltType.ATZ_LOAN);
        userContractDto.setModel(model);

        return userContractDto;
    }

    /**
     * 获取上上签用户信息，如果不存在则自动完成注册
     *
     * @return
     */
    public UserBestSignDto getUser(String userId) {

        //用户地址信息检测
        UserBestSignDto userBestSignDto = bestSignService.getUserBestSignByUserId(userId);

        if (userBestSignDto != null) {
            return userBestSignDto;
        }

        UserInfoDto userInfoDto = userService.findUserByUserId(userId);

        //上上签注册用户
        BsRegUserReq bsRegUserReq = new BsRegUserReq();
        bsRegUserReq.setEmail(userInfoDto.getEmail());
        bsRegUserReq.setMobile(userInfoDto.getMobile());
        bsRegUserReq.setName(userInfoDto.getUserName());
        String regUserJson;
        try {
            regUserJson = bestSignService.regUser(bsRegUserReq);
        } catch (Exception e) {
            LOGGER.error("best sign user register error", e);
            throw new BizServiceException(EErrorCode.LOAN_BEST_SIGN_ERROR, "best sign user register error");
        }

        RegUserWrap regUserWrap = JsonUtil.toObject(RegUserWrap.class, regUserJson);
        if (!RET_CODE.equals(regUserWrap.getResp().getInfo().getCode())) {
            LOGGER.info("best sign user register response code:{}", regUserWrap.getResp().getInfo().getCode());
            throw new BizServiceException(EErrorCode.LOAN_BEST_SIGN_ERROR, "best sign user register error");
        }

        userBestSignDto = new UserBestSignDto();
        userBestSignDto.setBestSignUid(regUserWrap.getResp().getContent().getUid());
        userBestSignDto.setUserId(userId);
        userBestSignDto.setBestSignCa(false);
        userBestSignDto = bestSignService.addUserBestSign(userBestSignDto);

        return userBestSignDto;
    }

    /**
     * 自动注册CA，如果已经注册过了不会重复注册
     */
    public void registerCA(UserBestSignDto userBestSignDto) {
        if (userBestSignDto.getBestSignCa()) {
            return;
        }

        UserInfoDto userInfoDto = userService.findUserByUserId(userBestSignDto.getUserId());
        UserExtDto userExtDto = userExtService.findUserByUserId(userBestSignDto.getUserId());

        String province, city, area, address;
        if (userExtDto == null || this.anyIsEmpty(userExtDto)) {
            province = DEFAULT_PROVINCE;
            city = DEFAULT_CITY;
            area = DEFAULT_AREA;
            address = DEFAULT_ADDRESS;
        } else {
            province = userExtDto.getProvince();
            city = userExtDto.getCity();
            area = userExtDto.getArea();
            address = userExtDto.getAddress();
        }

        //上上签申请个人CA证书
        BsCertApplReq bsCertApplReq = new BsCertApplReq();
        bsCertApplReq.setName(userInfoDto.getUserName());
        bsCertApplReq.setPassword(RandomUtil.getNumberStr(10));
        bsCertApplReq.setMobile(userInfoDto.getMobile());
        bsCertApplReq.setIdentity(userInfoDto.getIdCardNo());
        String addressBuilder = province + city + area + address;
        bsCertApplReq.setAddress(addressBuilder);
        bsCertApplReq.setProvince(province);
        bsCertApplReq.setCity(city);
        String cerApplJson;
        try {
            cerApplJson = bestSignService.certificateApply(bsCertApplReq);
        } catch (Exception e) {
            LOGGER.error("best sign user register CA error", e);
            throw new BizServiceException(EErrorCode.LOAN_BEST_SIGN_ERROR, "best sign user register CA error");
        }
        CerApplWrap cerApplWrap = JsonUtil.toObject(CerApplWrap.class, cerApplJson);
        if (!cerApplWrap.getResult()) {
            LOGGER.info("best sign user register CA error, msg:{}", cerApplWrap.getMsg());
            throw new BizServiceException(EErrorCode.LOAN_BEST_SIGN_ERROR,
                    String.format("best sign user register CA error, msg:%s", cerApplWrap.getMsg()));
        }

        userBestSignDto.setBestSignCa(true);
        bestSignService.updateUserBestSign(userBestSignDto);
    }

    /**
     * 上上签签约完成度检查
     * @param userContractDto
     */
    public void checkStatus(UserContractDto userContractDto){
        //检查上上签真实的签署状态
        ContractInfoWrap contractInfoWrap;
        try {
            String response = bestSignService.contractInfo(userContractDto.getBsSignid());
            contractInfoWrap = JsonUtil.toObject(ContractInfoWrap.class, response);
        }catch(Exception e){
            LOGGER.error("best sign check status error", e);
            throw new BizServiceException(EErrorCode.LOAN_BEST_SIGN_ERROR, "best sign check status error");
        }

        if (contractInfoWrap != null && RET_CODE.equals(contractInfoWrap.getResp().getInfo().getCode())){
            if (ContractInfoConstant.CONTRACT_FINISHED.equals(contractInfoWrap.getResp().getContent().getContractinfo().getStatus())){
                this.signed(userContractDto.getBsSignid());
                throw new BizServiceException(EErrorCode.LOAN_CONTRACT_FINISHED, "best sign finished");
            }
        }
    }

    public void signed(String signId) throws BizServiceException{
        UserContractDto userContractDto = contractService.getUserContractBySignId(signId);
        this.signed(RET_CODE, signId, userContractDto.getBizId());
    }

    /**
     * 上上签合同发送，如果合同在上上签处已经存在，不会重复发送
     * @param userContractDto
     */
    private UserContractDto send(UserContractDto userContractDto){

        if(userContractDto == null){
            throw new BizServiceException(EErrorCode.LOAN_BEST_SIGN_ERROR, "best sign send contract error");
        }

        if(!StringUtils.isEmpty(userContractDto.getBsSignid())){
            return userContractDto;
        }

        byte[] fileData;
        try {
            fileData = OkHttpUtil.getBytes(AppConfigUtil.getConfig("file.addr") + StaticResourceSecurity.getSecurityURI(userContractDto.getFilePath()), true);
        } catch (IOException e) {
            throw new BizServiceException(EErrorCode.LOAN_BEST_SIGN_ERROR, "best sign get contract bytes error");
        }

        UserInfoDto userInfoDto = userService.findUserByUserId(userContractDto.getUserId());

        BsSjdSendReq bsSjdSendReq = new BsSjdSendReq();
        String filename = userContractDto.getFilePath();
        bsSjdSendReq.setFilename(filename.substring(filename.lastIndexOf("/") + 1, filename.length()));
        bsSjdSendReq.setFileData(fileData);

        List<BsReceiveUser> receiveUserList = new ArrayList<>();
        BsReceiveUser customer = new BsReceiveUser();
        customer.setName(userInfoDto.getUserName());
        customer.setMobile(userInfoDto.getMobile());
        customer.setEmail(userInfoDto.getEmail());
        receiveUserList.add(customer);
        bsSjdSendReq.setReceiveUserList(receiveUserList);
        bsSjdSendReq.setSelfSign(true);

        String sendContractJson = null;
        try {
            sendContractJson = bestSignService.sjdsendcontractdocUpload(bsSjdSendReq);
        } catch (Exception e) {
            LOGGER.error("best sign send contract error", e);
            throw new BizServiceException(EErrorCode.LOAN_BEST_SIGN_ERROR, "best sign send contract error");
        }
        SendContractWrap sendContractWrap = JsonUtil.toObject(SendContractWrap.class, sendContractJson);

        if (!RET_CODE.equals(sendContractWrap.getResp().getInfo().getCode())) {
            LOGGER.info("best sign send contract response code:{}", sendContractWrap.getResp().getInfo().getCode());
            throw new BizServiceException(EErrorCode.LOAN_BEST_SIGN_ERROR, "best sign send contract response code");
        }

        List<SendContractCntInfoResp> contInfoList = sendContractWrap.getResp().getContent().getContlist();
        if (CollectionUtils.isEmpty(contInfoList)) {
            LOGGER.info("best sign contract is empty");
            throw new BizServiceException(EErrorCode.LOAN_BEST_SIGN_ERROR, "best sign contract is empty");
        }

        //更新合同上上签相关信息
        SendContractContInfo contInfo = contInfoList.get(0).getInfo();
        userContractDto.setBsDocid(contInfo.getDocid());
        userContractDto.setBsVatecode(contInfo.getVatecode());
        userContractDto.setBsSignid(contInfo.getSignid());

        return contractService.updateBsContractInf(userContractDto, userContractDto.getContractId());
    }

    private boolean anyIsEmpty(UserExtDto userExtDto) {
        return StringUtils.isEmpty(userExtDto.getProvince())
                || StringUtils.isEmpty(userExtDto.getCity())
                || StringUtils.isEmpty(userExtDto.getArea())
                || StringUtils.isEmpty(userExtDto.getAddress());
    }

    /**
     * 更新签约状态
     * @param signId
     */
    public void signed(String code, String signId, String applId) throws BizServiceException{
        if(!RET_CODE.equals(code)){
            throw new BizServiceException(EErrorCode.LOAN_BEST_SIGN_ERROR, "签约状态更新失败");
        }

        LoanApplDto loanApplDto = loanApplService.get(applId);
        loanApplDto.setSigned(true);
        loanApplService.save(loanApplDto);

        contractService.updateSignedMark(signId, ESignedMark.FIRST_SIGN);
    }

    /**
     * 公司盖章
     * @param userContractDto
     */
    private void signCompany(UserContractDto userContractDto){

        this.check(userContractDto);

        List<ContractBestSignCfgDto> autoSignCfgList = bestSignService.getCntrctBsCfg(userContractDto.getTemplateType(), EBsSignType.ENTERPRISE);
        if (CollectionUtils.isEmpty(autoSignCfgList)) {
            throw new BizServiceException(EErrorCode.LOAN_BEST_SIGN_ERROR, "best sign position is error");
        }

        BsAutoSignFoppReq request = new BsAutoSignFoppReq();
        request.setSignid(userContractDto.getBsSignid());
        request.setBestSignCfgList(autoSignCfgList);
        request.setSealname(this.getSealName());

        String autoSignFoppJson;
        try {
            autoSignFoppJson = bestSignService.autoSignFopp(request);
        } catch (Exception e) {
            LOGGER.error("best sign company sign error", e);
            throw new BizServiceException(EErrorCode.LOAN_BEST_SIGN_ERROR, "best sign company sign error", e);
        }
        AutoSignFoppWrap autoSignFoppWrap = JsonUtil.toObject(AutoSignFoppWrap.class, autoSignFoppJson);
        if (!RET_CODE.equals(autoSignFoppWrap.getResp().getInfo().getCode())) {
            LOGGER.info("best sign company sign error, code:{}, msg:{}"
                    , autoSignFoppWrap.getResp().getInfo().getCode()
                    , autoSignFoppWrap.getResp().getInfo().getMessage());
            throw new BizServiceException(EErrorCode.LOAN_BEST_SIGN_ERROR, "best sign company sign error");
        }

        userContractDto.setSignedMark(ESignedMark.SEAL);

        contractService.updateSignedMark(userContractDto.getBsSignid(), ESignedMark.SEAL);
    }


    /**
     * 用户手动签署
     * @param userContractDto
     * @return
     */
    public String signUser(UserContractDto userContractDto){

        this.check(userContractDto);

        UserInfoDto userInfoDto = userService.findUserByUserId(userContractDto.getUserId());

        ManualSignReq manualSignReq = new ManualSignReq();
        manualSignReq.setPc(false);
        manualSignReq.setSignId(userContractDto.getBsSignid());
        manualSignReq.setEmail(userInfoDto.getMobile());
        manualSignReq.setReturnUrl(LOCAL_DOMAIN + this.getReturnUrl());

        List<ContractBestSignCfgDto> manualSignCfgList = bestSignService.getCntrctBsCfg(userContractDto.getTemplateType(), EBsSignType.PERSONAL);
        if (CollectionUtils.isEmpty(manualSignCfgList)) {
            throw new BizServiceException(EErrorCode.LOAN_BEST_SIGN_ERROR, "best sign position is error");
        }
        manualSignReq.setBestSignCfgList(manualSignCfgList);

        String url;
        try {
            url = bestSignService.getSignPageSignimagePc(manualSignReq);
        } catch (Exception e) {
            LOGGER.error("best sign user sign error", e);
            throw new BizServiceException(EErrorCode.LOAN_BEST_SIGN_ERROR, "best sign user sign error", e);
        }

        LOGGER.debug("best sign url:{}", url);

        if (StringUtils.isEmpty(url)) {
            throw new BizServiceException(EErrorCode.LOAN_BEST_SIGN_ERROR, "best sign get sign url error");
        }

        return url;
    }

    public Object viewContract(String applId, ECntrctTmpltType tmpltType){

        String userId = SecurityContext.getCurrentUserId();

        UserContractDto userContractDto = contractService.getUserContractByBizId(userId, tmpltType, applId);

        if(userContractDto == null){
            throw new BizServiceException(EErrorCode.TECH_DATA_INVALID, "contract not exists");
        }

        if(StringUtils.isEmpty(userContractDto.getBsSignid())){
            return userContractDto.getContent();
        }

        //存在的话，则获取上上签的合同
        BsViewContractReq req = new BsViewContractReq();
        req.setFsdid(userContractDto.getBsSignid());
        req.setDocid(userContractDto.getBsDocid());
        String contractUrl = null;
        try {
            contractUrl = bestSignService.viewContract(req);
        } catch (Exception e) {
            LOGGER.error("best sign view contract error", e);
        }
        return new ModelAndView("redirect:" + contractUrl);
    }

    public void check(UserContractDto userContractDto){
        if(userContractDto == null || StringUtils.isEmpty(userContractDto.getBsSignid())){
            throw new BizServiceException(EErrorCode.LOAN_BEST_SIGN_ERROR, "contract best sign id is empty");
        }

        if(StringUtils.isEmpty(this.getReturnUrl())){
            throw new BizServiceException(EErrorCode.LOAN_BEST_SIGN_ERROR, "return url is empty");
        }
    }

}
