
package com.xinyunlian.jinfu.loan.controller;

import com.xinyunlian.jinfu.common.dto.ResultDto;
import com.xinyunlian.jinfu.common.dto.ResultDtoFactory;
import com.xinyunlian.jinfu.common.dto.bscontract.*;
import com.xinyunlian.jinfu.common.security.SecurityContext;
import com.xinyunlian.jinfu.common.security.StaticResourceSecurity;
import com.xinyunlian.jinfu.common.util.*;
import com.xinyunlian.jinfu.contract.dto.*;
import com.xinyunlian.jinfu.contract.enums.EBsSignType;
import com.xinyunlian.jinfu.contract.enums.ECntrctTmpltType;
import com.xinyunlian.jinfu.contract.service.BestSignService;
import com.xinyunlian.jinfu.contract.service.ContractService;
import com.xinyunlian.jinfu.loan.dto.contract.LoanContractDto;
import com.xinyunlian.jinfu.loan.dto.resp.LoanApplDto;
import com.xinyunlian.jinfu.loan.service.LoanApplService;
import com.xinyunlian.jinfu.loan.service.PrivateContractService;
import com.xinyunlian.jinfu.product.dto.LoanProductDetailDto;
import com.xinyunlian.jinfu.router.service.FinanceSourceService;
import com.xinyunlian.jinfu.user.dto.UserExtDto;
import com.xinyunlian.jinfu.user.dto.UserInfoDto;
import com.xinyunlian.jinfu.user.service.UserExtService;
import com.xinyunlian.jinfu.user.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @author willwang
 */
@RestController
@RequestMapping(value = "loan/contract")
public class LoanContractController {
    @Autowired
    private UserService userService;

    @Autowired
    private ContractService contractService;

    @Autowired
    private PrivateContractService privateContractService;

    @Autowired
    private LoanApplService loanApplService;

    @Autowired
    private BestSignService bestSignService;

    @Autowired
    private UserExtService userExtService;

    @Autowired
    private FinanceSourceService financeSourceService;

    private static final String RET_CODE = "100000";

    private static final Logger LOGGER = LoggerFactory.getLogger(LoanContractController.class);

    /**
     * 获得用户个人征信客户授权书
     * @return
     */
    @RequestMapping(value = "zxsq", method = RequestMethod.GET)
    public String getContractZXSQ() {
        UserInfoDto userInfoDto = userService.findUserByUserId(SecurityContext.getCurrentUserId());
        return contractService.getContract(privateContractService.buildContractZXSQ(userInfoDto)).getContent();
    }

    /**
     * 获取代扣协议
     * @return
     */
    @RequestMapping(value = "dk", method = RequestMethod.GET)
    public String getContractDKL01001() {
        UserInfoDto userInfoDto = userService.findUserByUserId(SecurityContext.getCurrentUser().getUserId());
        UserContractDto userContractDto = contractService.getContract(privateContractService.buildContractDKL01001(userInfoDto));
        return userContractDto.getContent();
    }

    /**
     * 获取贷款协议
     * @param applId 申请编号
     * @return
     */
    @RequestMapping(value = "loan", method = RequestMethod.GET)
    public String getContractYDL01001(@RequestParam String applId) {
        LoanApplDto loanApplDto = loanApplService.get(applId);
        UserContractDto userContractDto = privateContractService.buildLoanContract(loanApplDto);
        return userContractDto.getContent();
    }

    /**
     * 获取用户签署过的贷款协议
     * @param loanId
     * @return
     */
    @RequestMapping(value = "user/loan", method = RequestMethod.GET)
    public Object getUserLoan(@RequestParam String loanId) {
        return privateContractService.getUserLoan(loanId);
    }

    /**
     * 获取用户签署过的合同
     * @param bizId
     * @return
     */
    @RequestMapping(value = "user/get", method = RequestMethod.GET)
    public Object getUserContract(@RequestParam String bizId, @RequestParam ECntrctTmpltType type) {
        return privateContractService.getUserLoan(bizId, type);
    }

    /**
     * 获取用户签署过的代扣协议
     */
    @RequestMapping(value= "user/dk", method = RequestMethod.GET)
    public String getUserDk(@RequestParam String loanId){
        return privateContractService.getUserDk(loanId);
    }

    @RequestMapping(value = "user/zxsq", method = RequestMethod.GET)
    public String getUserZxsq(){
        return privateContractService.getUserZxsq();
    }

    /**
     * 获取用户签署过的协议列表
     * @param loanId
     * @return
     */
    @RequestMapping(value = "list", method = RequestMethod.GET)
    public ResultDto list(@RequestParam String loanId) {
        List<LoanContractDto> list = privateContractService.list(loanId);
        return ResultDtoFactory.toAck(MessageUtil.getMessage("common.operate.success"), list);
    }

    /**
     * 跳转上上签合同签约
     * @param applyId
     * @return
     */
    @RequestMapping(value = "/bestSignContract", method = RequestMethod.GET)
    public Object bestSignContract(String applyId, Boolean pc){

        String userId = SecurityContext.getCurrentUser().getUserId();

        LoanProductDetailDto product = loanApplService.getProduct(applyId);
        UserContractDto loanContract = this.getLoanContract(applyId);
        loanContract.setProdId(product.getProductId());
        loanContract.setProdName(product.getProductName());
        loanContract.setBizId(applyId);

        //检查签约状态，未签约，则去上上签查询合同状态
        LoanApplDto loanApplyDto = loanApplService.get(applyId);

        if (loanApplyDto.getSigned() == null || !loanApplyDto.getSigned()){
            UserContractDto userContractDto = contractService.getUserContractByBizId(userId, loanContract.getTemplateType(), applyId);
            if (userContractDto != null && !StringUtils.isEmpty(userContractDto.getBsSignid())){
                try {
                    String contractJson = bestSignService.contractInfo(userContractDto.getBsSignid());
                    ContractInfoWrap contractInfoWrap = JsonUtil.toObject(ContractInfoWrap.class, contractJson);
                    if (RET_CODE.equals(contractInfoWrap.getResp().getInfo().getCode())){
                        if (ContractInfoConstant.CONTRACT_FINISHED.equals(contractInfoWrap.getResp().getContent().getContractinfo().getStatus())){
                            loanApplService.updateSigned(applyId);
                            return ResultDtoFactory.toAck("查询成功", ContractInfoConstant.CONTRACT_SUCCESS);
                        }
                    }else {
                        return ResultDtoFactory.toNack("查询合同信息失败");
                    }
                } catch (Exception e) {
                    LOGGER.error("查询合同信息异常", e);
                    return ResultDtoFactory.toNack("查询合同信息异常");
                }
            }
        }

        try {

            //用户地址信息检测
            UserBestSignDto userBestSignDto = bestSignService.getUserBestSignByUserId(userId);
            UserInfoDto userInfoDto = userService.findUserByUserId(userId);
            UserExtDto userExtDto = userExtService.findUserByUserId(userId);
            String province = AppConfigUtil.getConfig("best.sign.province");
            String city = AppConfigUtil.getConfig("best.sign.city");
            String area = AppConfigUtil.getConfig("best.sign.area");
            String address = AppConfigUtil.getConfig("best.sign.address");
            if (userExtDto == null && StringUtils.isEmpty(userExtDto.getProvince()) && StringUtils.isEmpty(userExtDto.getCity())
                    && StringUtils.isEmpty(userExtDto.getArea()) && StringUtils.isEmpty(userExtDto.getAddress())){
                province = userExtDto.getProvince();
                city = userExtDto.getCity();
                area = userExtDto.getArea();
                address = userExtDto.getAddress();
            }

            if (userBestSignDto == null){

                //上上签注册用户
                BsRegUserReq bsRegUserReq = new BsRegUserReq();
                bsRegUserReq.setEmail(userInfoDto.getEmail());
                bsRegUserReq.setMobile(userInfoDto.getMobile());
                bsRegUserReq.setName(userInfoDto.getUserName());
                String regUserJson = bestSignService.regUser(bsRegUserReq);

                RegUserWrap regUserWrap = JsonUtil.toObject(RegUserWrap.class, regUserJson);
                if (!RET_CODE.equals(regUserWrap.getResp().getInfo().getCode())){
                    return ResultDtoFactory.toNack("上上签用户注册失败");
                }

                UserBestSignDto addUserBestSignDto = new UserBestSignDto();
                addUserBestSignDto.setBestSignUid(regUserWrap.getResp().getContent().getUid());
                addUserBestSignDto.setUserId(userId);
                addUserBestSignDto.setBestSignCa(false);
                bestSignService.addUserBestSign(addUserBestSignDto);

            }

            if (userBestSignDto == null || !userBestSignDto.getBestSignCa()){
                UserBestSignDto dbUserBestSign = bestSignService.getUserBestSignByUserId(userId);

                //上上签申请个人CA证书
                BsCertApplReq bsCertApplReq = new BsCertApplReq();
                bsCertApplReq.setName(userInfoDto.getUserName());
                bsCertApplReq.setPassword(RandomUtil.getNumberStr(10));
                bsCertApplReq.setMobile(userInfoDto.getMobile());
                bsCertApplReq.setIdentity(userInfoDto.getIdCardNo());

                StringBuilder addressBuilder = new StringBuilder();
                addressBuilder.append(province)
                        .append(city)
                        .append(area)
                        .append(address);
                bsCertApplReq.setAddress(addressBuilder.toString());
                bsCertApplReq.setProvince(province);
                bsCertApplReq.setCity(city);

                String cerApplJson = bestSignService.certificateApply(bsCertApplReq);
                CerApplWrap cerApplWrap = JsonUtil.toObject(CerApplWrap.class, cerApplJson);
                if (!cerApplWrap.getResult()){
                    return ResultDtoFactory.toNack("上上签用户申请CA证书失败");
                }

                dbUserBestSign.setBestSignCa(true);

                bestSignService.updateUserBestSign(dbUserBestSign);
            }

            //判断合同是否存在
            UserContractDto userContractDto = contractService.getUserContractByBizId(userId, loanContract.getTemplateType(), applyId);
            String signId = null;
            if (userContractDto == null){
                userContractDto = contractService.saveContract2(loanContract, userInfoDto);
            }
            if (StringUtils.isEmpty(userContractDto.getBsSignid())){
                //判断是否需要重新发送本地pdf合同
                byte[] fileData = OkHttpUtil.getBytes(AppConfigUtil.getConfig("file.addr") + StaticResourceSecurity.getSecurityURI(userContractDto.getFilePath()), true);

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

                String sendContractJson = bestSignService.sjdsendcontractdocUpload(bsSjdSendReq);
                SendContractWrap sendContractWrap = JsonUtil.toObject(SendContractWrap.class, sendContractJson);

                if (!RET_CODE.equals(sendContractWrap.getResp().getInfo().getCode())){
                    return ResultDtoFactory.toNack("合同发送失败");
                }

                List<SendContractCntInfoResp> contInfoList = sendContractWrap.getResp().getContent().getContlist();
                if (CollectionUtils.isEmpty(contInfoList)) {
                    return ResultDtoFactory.toNack("上上签合同返回失败");
                }

                //更新合同上上签相关信息
                SendContractContInfo contInfo = contInfoList.get(0).getInfo();
                UserContractDto userContractDtoUpdate = new UserContractDto();
                userContractDtoUpdate.setBsDocid(contInfo.getDocid());
                userContractDtoUpdate.setBsVatecode(contInfo.getVatecode());
                userContractDtoUpdate.setBsSignid(contInfo.getSignid());
                contractService.updateBsContractInf(userContractDtoUpdate, userContractDto.getContractId());

                signId = contInfo.getSignid();

                //多处公司盖章
                BsAutoSignFoppReq autoSignFoppReq = new BsAutoSignFoppReq();
                autoSignFoppReq.setSignid(signId);

                List<ContractBestSignCfgDto> autoSignCfgList = bestSignService.getCntrctBsCfg(loanContract.getTemplateType(), EBsSignType.ENTERPRISE);
                if (CollectionUtils.isEmpty(autoSignCfgList)){
                    return ResultDtoFactory.toNack("上上签合同公司盖章配置信息缺失");
                }
                autoSignFoppReq.setBestSignCfgList(autoSignCfgList);
                String autoSignFoppJson = bestSignService.autoSignFopp(autoSignFoppReq);
                AutoSignFoppWrap autoSignFoppWrap = JsonUtil.toObject(AutoSignFoppWrap.class, autoSignFoppJson);
                if (!RET_CODE.equals(autoSignFoppWrap.getResp().getInfo().getCode())){
                    return ResultDtoFactory.toNack("上上签合同公司盖章失败");
                }
            }else {
                signId = userContractDto.getBsSignid();
            }

            //多处手动签名
            ManualSignReq manualSignReq = new ManualSignReq();
            manualSignReq.setPc(false);
            manualSignReq.setSignId(signId);
            manualSignReq.setEmail(userInfoDto.getMobile());
            manualSignReq.setReturnUrl(AppConfigUtil.getConfig("best.sign.returnurl"));

            List<ContractBestSignCfgDto> manualSignCfgList = bestSignService.getCntrctBsCfg(loanContract.getTemplateType(), EBsSignType.PERSONAL);
            if (CollectionUtils.isEmpty(manualSignCfgList)){
                return ResultDtoFactory.toNack("上上签合同个人签名配置信息缺失");
            }
            manualSignReq.setBestSignCfgList(manualSignCfgList);
            manualSignReq.setPc(pc);

            String signimageJson = bestSignService.getSignPageSignimagePc(manualSignReq);
            if (StringUtils.isEmpty(signimageJson)){
                return ResultDtoFactory.toNack("获取跳转链接失败");
            }

            LOGGER.debug("返回跳转上上签地址");
            LOGGER.debug(signimageJson);
            return ResultDtoFactory.toAck("成功", signimageJson);
        } catch (Exception e) {
            LOGGER.error("跳转上上签异常",e);
            return ResultDtoFactory.toNack("跳转上上签异常");
        }

    }

    private UserContractDto getLoanContract(String applId){
        LoanApplDto loanApplDto = loanApplService.get(applId);
        return privateContractService.buildLoanContract(loanApplDto);
    }

}
