package com.xinyunlian.jinfu.loan.service;

import com.xinyunlian.jinfu.common.dto.bscontract.*;
import com.xinyunlian.jinfu.common.enums.EErrorCode;
import com.xinyunlian.jinfu.common.exception.BizServiceException;
import com.xinyunlian.jinfu.common.security.SecurityContext;
import com.xinyunlian.jinfu.common.security.StaticResourceSecurity;
import com.xinyunlian.jinfu.common.util.AppConfigUtil;
import com.xinyunlian.jinfu.common.util.JsonUtil;
import com.xinyunlian.jinfu.common.util.OkHttpUtil;
import com.xinyunlian.jinfu.common.util.RandomUtil;
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
public class PrivateXYDService {

    @Autowired
    private BestSignService bestSignService;

    @Autowired
    private UserService userService;

    @Autowired
    private ContractService contractService;

    @Autowired
    private LoanApplService loanApplService;

    @Autowired
    private PrivateContractService privateContractService;
    @Autowired
    private PrivateBestSignService privateBestSignService;

    private static final Logger LOGGER = LoggerFactory.getLogger(PrivateXYDService.class);

    private static final String RET_CODE = "100000";

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

    public String getReturnUrl() {
        return returnUrl;
    }

    public void setReturnUrl(String returnUrl) {
        this.returnUrl = returnUrl;
    }


    /**
     * 获取待签署的合同地址
     */
    public LoanBestSignDto get(String applId, ECntrctTmpltType type) {
        privateBestSignService.setReturnUrl(this.getReturnUrl());
        String userId = SecurityContext.getCurrentUserId();

        LoanBestSignDto rs = new LoanBestSignDto();

        rs.setApplId(applId);
        rs.setSignUrl("");
        rs.setContractName(type.getText());

        UserContractDto userContractDto = contractService.getUserContractByBizId(userId, type, applId);

        privateBestSignService.registerCA(privateBestSignService.getUser(userId));

        if (userContractDto != null && !StringUtils.isEmpty(userContractDto.getBsSignid())) {
            privateBestSignService.checkStatus(userContractDto);
        }

        //不存在同类合同，重新渲染
        if (userContractDto == null) {
            userContractDto = this.render(applId, type);
            userContractDto.setBizId(applId);
            userContractDto.setUserId(userId);
            userContractDto.setSignedMark(ESignedMark.INIT);
            UserInfoDto userInfoDto = userService.findUserByUserId(userId);
            userContractDto = contractService.saveContractSync(userContractDto, userInfoDto);
        }

        List<ContractBestCompanyDto> bestCompanyDtos = bestSignService.getBestCompany(userContractDto.getTemplateType(),EBsSignType.ENTERPRISE);


        //合同如果没有发送给上上签，重新发送
        if (StringUtils.isEmpty(userContractDto.getBsSignid())) {
            userContractDto = this.send(userContractDto,bestCompanyDtos);
        }

        List<ContractBestSignCfgDto> autoSignCfgList = bestSignService.getCntrctBsCfg(userContractDto.getTemplateType(), EBsSignType.ENTERPRISE);
        if (CollectionUtils.isEmpty(autoSignCfgList)) {
            throw new BizServiceException(EErrorCode.LOAN_BEST_SIGN_ERROR, "best sign position is error");
        }
        //平台章配置信息
        List<ContractBestSignCfgDto> autoSignCfgListPlatform = new ArrayList<>();
        //第三方章配置信息
        List<ContractBestSignCfgDto> autoSignCfgListThird = new ArrayList<>();

        for (ContractBestSignCfgDto contractBestSignCfgDto : autoSignCfgList) {
            if (!StringUtils.isEmpty(contractBestSignCfgDto.getSealName())) {
                autoSignCfgListThird.add(contractBestSignCfgDto);
            } else {
                autoSignCfgListPlatform.add(contractBestSignCfgDto);
            }
        }

        //判定合同签署状态，如果没有盖章，自动盖章
        if (userContractDto.getSignedMark() == ESignedMark.INIT) {
            this.signCompanyPlatform(userContractDto,autoSignCfgListPlatform);
        }

        //判定签署状态，如果已经签署，直接返回空
        if (userContractDto.getSignedMark() == ESignedMark.SEAL) {
            if (CollectionUtils.isEmpty(bestCompanyDtos)) {
                String url = privateBestSignService.signUser(userContractDto);
                rs.setSignUrl(url);
            }else{
                //第三方公章自动签名
                this.signCompanyThird(userContractDto,bestCompanyDtos,autoSignCfgListThird);
            }
        }

        if (userContractDto.getSignedMark() == ESignedMark.THIRD_PARTY) {
            String url = privateBestSignService.signUser(userContractDto);
            rs.setSignUrl(url);
        }

        rs.setReturnUrl(this.getReturnUrl());

        return rs;
    }

    /**
     * 渲染爱投资合同
     *
     * @param type
     * @return
     */
    private UserContractDto render(String applId, ECntrctTmpltType type) {

        UserContractDto userContractDto = null;

        switch (type) {
            case ATZ_LOAN:
                userContractDto = privateBestSignService.buildAtzLoan();
                break;
            case YDL01001:
                userContractDto = privateContractService.buildLoanContract(applId);
                break;
            case YDL01002:
                userContractDto = privateContractService.buildLoanContract(applId);
                break;
            case YDL01006:
                //贷款协议
                userContractDto = privateContractService.buildLoanContract(applId, type);
                break;
            case ZXL01006:
                userContractDto = privateContractService.buildLoanContract(applId, type);
                break;
            case DKL01001:
                //委托扣款协议
                userContractDto = privateContractService.buildLoanContract(applId, type);
                break;
        }

        if (userContractDto == null) {
            throw new BizServiceException(EErrorCode.LOAN_BEST_SIGN_ERROR, "best sign render error");
        }

        return contractService.getContract(userContractDto);
    }

    /**
     * 上上签合同发送，如果合同在上上签处已经存在，不会重复发送
     *
     * @param userContractDto
     */
    private UserContractDto send(UserContractDto userContractDto, List<ContractBestCompanyDto> bestCompanyDtos) {

        if (userContractDto == null) {
            throw new BizServiceException(EErrorCode.LOAN_BEST_SIGN_ERROR, "best sign send contract error");
        }

        if (!StringUtils.isEmpty(userContractDto.getBsSignid())) {
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

        if(!CollectionUtils.isEmpty(bestCompanyDtos)){
            bestCompanyDtos.forEach(contractBestCompanyDto -> {
                BsReceiveUser company = new BsReceiveUser();
                company.setName(contractBestCompanyDto.getName());
                company.setMobile(contractBestCompanyDto.getMobile());
                company.setEmail(contractBestCompanyDto.getEmail());
                company.setPersonal(false);
                receiveUserList.add(company);
            });
        }
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


    /**
     * 更新签约状态
     *
     * @param signId
     */
    public void signed(String code, String signId, String applId) throws BizServiceException {
        if (!RET_CODE.equals(code)) {
            throw new BizServiceException(EErrorCode.LOAN_BEST_SIGN_ERROR, "签约状态更新失败");
        }

        UserContractDto userContractDto = contractService.getUserContractBySignId(signId);

        if(userContractDto.getTemplateType() == ECntrctTmpltType.ZXL01006){
            loanApplService.updateSigned(applId);
        }

        contractService.updateSignedMark(signId, ESignedMark.FIRST_SIGN);
    }

    /**
     * 平台公司公盖章
     *
     * @param userContractDto
     */
    private void signCompanyPlatform(UserContractDto userContractDto, List<ContractBestSignCfgDto> autoSignCfgListPlatform) {

        privateBestSignService.check(userContractDto);

        //平台公章自动签名
        BsAutoSignFoppReq request = new BsAutoSignFoppReq();
        if(userContractDto.getTemplateType() == ECntrctTmpltType.ZXL01006){
            request.setSealname("云联金服公章");
        }
        request.setSignid(userContractDto.getBsSignid());
        request.setBestSignCfgList(autoSignCfgListPlatform);
        this.autoSignFopp(request);

        userContractDto.setSignedMark(ESignedMark.SEAL);
        contractService.updateSignedMark(userContractDto.getBsSignid(), ESignedMark.SEAL);
    }

    /**
     * 第三方公司盖章
     *
     * @param userContractDto
     */
    private void signCompanyThird(UserContractDto userContractDto,List<ContractBestCompanyDto> bestCompanyDtos,
                                  List<ContractBestSignCfgDto> autoSignCfgListThird) {
        privateBestSignService.check(userContractDto);

        BsAutoSignFoppReq request = new BsAutoSignFoppReq();
        request.setSignid(userContractDto.getBsSignid());
        request.setBestSignCfgList(autoSignCfgListThird);
        request.setEmail(bestCompanyDtos.get(0).getEmail());
        request.setSealname(autoSignCfgListThird.get(0).getSealName());
        this.autoSignFopp(request);

        userContractDto.setSignedMark(ESignedMark.THIRD_PARTY);
        contractService.updateSignedMark(userContractDto.getBsSignid(), ESignedMark.THIRD_PARTY);
    }

    /**
     * 自动签名请求
     *
     * @param request
     */
    private void autoSignFopp(BsAutoSignFoppReq request) {
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
    }

}
