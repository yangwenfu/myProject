package com.xinyunlian.jinfu.contract.controller;

import com.xinyunlian.jinfu.bank.dto.BankCardDto;
import com.xinyunlian.jinfu.bank.service.BankService;
import com.xinyunlian.jinfu.bscontract.dto.YmMemberSignDto;
import com.xinyunlian.jinfu.bscontract.service.YmMemberSignService;
import com.xinyunlian.jinfu.common.dto.ResultDto;
import com.xinyunlian.jinfu.common.dto.ResultDtoFactory;
import com.xinyunlian.jinfu.common.dto.bscontract.*;
import com.xinyunlian.jinfu.common.exception.BizServiceException;
import com.xinyunlian.jinfu.common.fileStore.FileStoreService;
import com.xinyunlian.jinfu.common.fileStore.enums.EFileType;
import com.xinyunlian.jinfu.common.security.SecurityContext;
import com.xinyunlian.jinfu.common.security.StaticResourceSecurity;
import com.xinyunlian.jinfu.common.util.*;
import com.xinyunlian.jinfu.contract.dto.*;
import com.xinyunlian.jinfu.contract.enums.EBsSignType;
import com.xinyunlian.jinfu.contract.enums.ECntrctTmpltType;
import com.xinyunlian.jinfu.contract.service.BestSignService;
import com.xinyunlian.jinfu.contract.service.ContractService;
import com.xinyunlian.jinfu.store.dto.StoreInfDto;
import com.xinyunlian.jinfu.store.service.StoreService;
import com.xinyunlian.jinfu.trade.dto.YmBizDto;
import com.xinyunlian.jinfu.trade.enums.EBizCode;
import com.xinyunlian.jinfu.trade.service.YmBizService;
import com.xinyunlian.jinfu.user.dto.UserExtDto;
import com.xinyunlian.jinfu.user.dto.UserInfoDto;
import com.xinyunlian.jinfu.user.service.UserExtService;
import com.xinyunlian.jinfu.user.service.UserService;
import com.xinyunlian.jinfu.yunma.dto.YMMemberDto;
import com.xinyunlian.jinfu.yunma.dto.YMUserInfoDto;
import com.xinyunlian.jinfu.yunma.dto.YmMemberBizDto;
import com.xinyunlian.jinfu.yunma.service.YMMemberService;
import com.xinyunlian.jinfu.yunma.service.YMUserInfoService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by dongfangchao on 2017/2/22/0022.
 */
@RestController
@RequestMapping(value = "contract")
public class ContractController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ContractController.class);

    private static final String RET_CODE = "100000";

    @Autowired
    private BestSignService bestSignService;

    @Autowired
    private UserExtService userExtService;

    @Autowired
    private UserService userService;

    @Autowired
    private YmMemberSignService ymMemberSignService;

    @Autowired
    private ContractService contractService;

    @Autowired
    private StoreService storeService;

    @Autowired
    private YMMemberService yMMemberService;

    @Autowired
    private BankService bankService;

    @Autowired
    private YMUserInfoService yMUserInfoService;

    @Autowired
    private FileStoreService fileStoreService;

    @Autowired
    private YmBizService ymBizService;

    /**
     * 跳转上上签合同签约，新用户签约
     * @param qrCodeNo
     * @return
     */
    @RequestMapping(value = "/bestSignNewUser", method = RequestMethod.GET)
    public Object bestSignNewUser(String qrCodeNo, Long storeId, Long bankCardId, Boolean pc, String returnUrl){

        StoreInfDto storeInfDto = storeService.findByStoreId(storeId);
        if (storeInfDto == null) {
            return ResultDtoFactory.toNack("店铺不存在");
        }
        BankCardDto bankCardDto = bankService.getBankCard(bankCardId);
        String ymUserId = SecurityContext.getCurrentUser().getUserId();
        YMUserInfoDto ymUserInfoDto =yMUserInfoService.findByYmUserId(ymUserId);
        String userId = ymUserInfoDto.getUserId();
        if (bankCardDto == null) {
            return ResultDtoFactory.toNack("银行卡不存在");
        } else if (!userId.equals(bankCardDto.getUserId())) {
            return ResultDtoFactory.toNack("该银行卡不是当前用户");
        }
        //查费率
        List<YmBizDto> bizList = ymBizService.findAll();
        if (bizList.isEmpty()) {
            return ResultDtoFactory.toNack("系统费率未设置");
        }

        YmMemberSignDto ymMemberSignDto = ymMemberSignService.getYmMemberSignByQrcodeNo(qrCodeNo);
        if (ymMemberSignDto == null){
            ymMemberSignDto = ymMemberSignService.getYmMemberSignByStoreId(storeId);
            if (ymMemberSignDto == null){
                YmMemberSignDto saveDto = new YmMemberSignDto();
                saveDto.setQrcodeNo(qrCodeNo);
                saveDto.setStoreId(storeId);
                saveDto.setSigned(false);
                ymMemberSignDto = ymMemberSignService.saveYmMemberSign(saveDto);
            }else if (ymMemberSignDto.getSigned() == null || !ymMemberSignDto.getSigned()){
                ResultDto resultDto = checkSigned(ymMemberSignDto, userId);
                if (resultDto != null){
                    return resultDto;
                }
            }
        }else if (ymMemberSignDto.getSigned() == null || !ymMemberSignDto.getSigned()){
            ResultDto resultDto = checkSigned(ymMemberSignDto, userId);
            if (resultDto != null){
                return resultDto;
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
            if (userExtDto != null && StringUtils.isNotEmpty(userExtDto.getProvince()) && StringUtils.isNotEmpty(userExtDto.getCity())
                    && StringUtils.isNotEmpty(userExtDto.getArea()) && StringUtils.isNotEmpty(userExtDto.getAddress())){
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

            UserContractDto userContract = getTemplateData( storeInfDto, userInfoDto, bankCardDto, ymMemberSignDto.getId().toString(), bizList.get(1).getRate().toString(), bizList.get(0).getRate().toString());

            //判断合同是否存在
            UserContractDto userContractDto = contractService.getUserContractByBizId(userId, userContract.getTemplateType(), ymMemberSignDto.getId().toString());
            String signId;
            if (userContractDto == null){
                userContractDto = contractService.saveContract2(userContract, userInfoDto);
            }
            if (StringUtils.isEmpty(userContractDto.getBsSignid())){
                //判断是否需要重新发送本地pdf合同
                byte[] fileData = OkHttpUtil.getBytes(AppConfigUtil.getConfig("file.addr") + StaticResourceSecurity.getSecurityURI(userContractDto.getFilePath()), true);

                BsSjdSendReq bsSjdSendReq = new BsSjdSendReq();
                String filename = userContractDto.getFilePath();
                bsSjdSendReq.setFilename(filename.substring(filename.lastIndexOf('/') + 1, filename.length()));
                bsSjdSendReq.setFileData(fileData);

                List<BsReceiveUser> receiveUserList = new ArrayList<>();
                BsReceiveUser customer = new BsReceiveUser();
                customer.setName(userInfoDto.getUserName());
                customer.setMobile(userInfoDto.getMobile());
                customer.setEmail(userInfoDto.getEmail());
                receiveUserList.add(customer);
                bsSjdSendReq.setReceiveUserList(receiveUserList);
                bsSjdSendReq.setSelfSign(false);

                String sendContractJson = bestSignService.sjdsendcontractdocUpload(bsSjdSendReq);
                SendContractWrap sendContractWrap = JsonUtil.toObject(SendContractWrap.class, sendContractJson);

                if (!RET_CODE.equals(sendContractWrap.getResp().getInfo().getCode())){
                    rollbackContract(userContractDto.getContractId(), ymMemberSignDto.getId());
                    return ResultDtoFactory.toNack("合同发送失败");
                }

                List<SendContractCntInfoResp> contInfoList = sendContractWrap.getResp().getContent().getContlist();
                if (CollectionUtils.isEmpty(contInfoList)) {
                    rollbackContract(userContractDto.getContractId(), ymMemberSignDto.getId());
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
            }else {
                signId = userContractDto.getBsSignid();
            }

            //多处手动签名
            ManualSignReq manualSignReq = new ManualSignReq();
            manualSignReq.setPc(true);
            manualSignReq.setSignId(signId);
            manualSignReq.setEmail(userInfoDto.getMobile());
            manualSignReq.setReturnUrl(returnUrl);

            List<ContractBestSignCfgDto> manualSignCfgList = bestSignService.getCntrctBsCfg(userContract.getTemplateType(), EBsSignType.PERSONAL);
            if (CollectionUtils.isEmpty(manualSignCfgList)){
                rollbackContract(userContractDto.getContractId(), ymMemberSignDto.getId());
                return ResultDtoFactory.toNack("上上签合同个人签名配置信息缺失");
            }
            manualSignReq.setBestSignCfgList(manualSignCfgList);
            manualSignReq.setPc(pc);

            String signimageJson = bestSignService.getSignPageSignimagePc(manualSignReq);
            if (StringUtils.isEmpty(signimageJson)){
                rollbackContract(userContractDto.getContractId(), ymMemberSignDto.getId());
                return ResultDtoFactory.toNack("获取跳转链接失败");
            }

            return ResultDtoFactory.toAck("成功", signimageJson);
        } catch (Exception e) {
            LOGGER.error("跳转上上签异常",e);
            return ResultDtoFactory.toNack("跳转上上签异常");
        }

    }

    /**
     * 跳转上上签合同签约，老用户签约
     * @param qrCodeNo
     * @return
     */
    @RequestMapping(value = "/bestSignOldUser", method = RequestMethod.GET)
    public Object bestSignOldUser(String qrCodeNo, Boolean pc, String returnUrl){

        YMMemberDto memberDto = yMMemberService.getMemberByQrCodeNo(qrCodeNo);
        if (memberDto == null) {
            return ResultDtoFactory.toNack("该云码未绑定店铺");
        }
        StoreInfDto storeInfDto = storeService.findByStoreId(memberDto.getStoreId());
        if (storeInfDto == null) {
            return ResultDtoFactory.toNack("店铺不存在");
        }
        BankCardDto bankCardDto = bankService.getBankCard(memberDto.getBankCardId());
        String ymUserId = SecurityContext.getCurrentUser().getUserId();
        YMUserInfoDto ymUserInfoDto =yMUserInfoService.findByYmUserId(ymUserId);
        String userId = ymUserInfoDto.getUserId();
        if (bankCardDto == null) {
            return ResultDtoFactory.toNack("银行卡不存在");
        } else if (!userId.equals(bankCardDto.getUserId())) {
            return ResultDtoFactory.toNack("该银行卡不是当前用户");
        }
        //查费率
        YmMemberBizDto alipayBiz = yMMemberService.getMemberBizByMemberNoAndBizCode(memberDto.getMemberNo(), EBizCode.ALLIPAY);
        YmMemberBizDto wechatBiz = yMMemberService.getMemberBizByMemberNoAndBizCode(memberDto.getMemberNo(), EBizCode.WECHAT);
        if (alipayBiz == null || wechatBiz == null) {
            return ResultDtoFactory.toNack("该店铺费率未设置");
        }

        YmMemberSignDto ymMemberSignDto = ymMemberSignService.getYmMemberSignByQrcodeNo(qrCodeNo);
        if (ymMemberSignDto == null){
            ymMemberSignDto = ymMemberSignService.getYmMemberSignByStoreId(memberDto.getStoreId());
            if (ymMemberSignDto == null){
                YmMemberSignDto saveDto = new YmMemberSignDto();
                saveDto.setQrcodeNo(qrCodeNo);
                saveDto.setStoreId(memberDto.getStoreId());
                saveDto.setSigned(false);
                ymMemberSignDto = ymMemberSignService.saveYmMemberSign(saveDto);
            }else if (ymMemberSignDto.getSigned() == null || !ymMemberSignDto.getSigned()){
                ResultDto resultDto = checkSigned(ymMemberSignDto, userId);
                if (resultDto != null){
                    return resultDto;
                }
            }
        }else if (ymMemberSignDto.getSigned() == null || !ymMemberSignDto.getSigned()){
            ResultDto resultDto = checkSigned(ymMemberSignDto, userId);
            if (resultDto != null){
                return resultDto;
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
            if (userExtDto != null && StringUtils.isNotEmpty(userExtDto.getProvince()) && StringUtils.isNotEmpty(userExtDto.getCity())
                    && StringUtils.isNotEmpty(userExtDto.getArea()) && StringUtils.isNotEmpty(userExtDto.getAddress())){
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

            UserContractDto userContract = getTemplateData( storeInfDto, userInfoDto, bankCardDto, ymMemberSignDto.getId().toString(), alipayBiz.getRate().toString(), wechatBiz.getRate().toString());

            //判断合同是否存在
            UserContractDto userContractDto = contractService.getUserContractByBizId(userId, userContract.getTemplateType(), ymMemberSignDto.getId().toString());
            String signId;
            if (userContractDto == null){
                userContractDto = contractService.saveContract2(userContract, userInfoDto);
            }
            if (StringUtils.isEmpty(userContractDto.getBsSignid())){
                //判断是否需要重新发送本地pdf合同
                byte[] fileData = OkHttpUtil.getBytes(AppConfigUtil.getConfig("file.addr") + StaticResourceSecurity.getSecurityURI(userContractDto.getFilePath()), true);

                BsSjdSendReq bsSjdSendReq = new BsSjdSendReq();
                String filename = userContractDto.getFilePath();
                bsSjdSendReq.setFilename(filename.substring(filename.lastIndexOf('/') + 1, filename.length()));
                bsSjdSendReq.setFileData(fileData);

                List<BsReceiveUser> receiveUserList = new ArrayList<>();
                BsReceiveUser customer = new BsReceiveUser();
                customer.setName(userInfoDto.getUserName());
                customer.setMobile(userInfoDto.getMobile());
                customer.setEmail(userInfoDto.getEmail());
                receiveUserList.add(customer);
                bsSjdSendReq.setReceiveUserList(receiveUserList);
                bsSjdSendReq.setSelfSign(false);

                String sendContractJson = bestSignService.sjdsendcontractdocUpload(bsSjdSendReq);
                SendContractWrap sendContractWrap = JsonUtil.toObject(SendContractWrap.class, sendContractJson);

                if (!RET_CODE.equals(sendContractWrap.getResp().getInfo().getCode())){
                    rollbackContract(userContractDto.getContractId(), ymMemberSignDto.getId());
                    return ResultDtoFactory.toNack("合同发送失败");
                }

                List<SendContractCntInfoResp> contInfoList = sendContractWrap.getResp().getContent().getContlist();
                if (CollectionUtils.isEmpty(contInfoList)) {
                    rollbackContract(userContractDto.getContractId(), ymMemberSignDto.getId());
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
            }else {
                signId = userContractDto.getBsSignid();
            }

            //多处手动签名
            ManualSignReq manualSignReq = new ManualSignReq();
            manualSignReq.setPc(true);
            manualSignReq.setSignId(signId);
            manualSignReq.setEmail(userInfoDto.getMobile());
            manualSignReq.setReturnUrl(returnUrl);

            List<ContractBestSignCfgDto> manualSignCfgList = bestSignService.getCntrctBsCfg(userContract.getTemplateType(), EBsSignType.PERSONAL);
            if (CollectionUtils.isEmpty(manualSignCfgList)){
                rollbackContract(userContractDto.getContractId(), ymMemberSignDto.getId());
                return ResultDtoFactory.toNack("上上签合同个人签名配置信息缺失");
            }
            manualSignReq.setBestSignCfgList(manualSignCfgList);
            manualSignReq.setPc(pc);

            String signimageJson = bestSignService.getSignPageSignimagePc(manualSignReq);
            if (StringUtils.isEmpty(signimageJson)){
                rollbackContract(userContractDto.getContractId(), ymMemberSignDto.getId());
                return ResultDtoFactory.toNack("获取跳转链接失败");
            }

            return ResultDtoFactory.toAck("成功", signimageJson);
        } catch (Exception e) {
            LOGGER.error("跳转上上签异常",e);
            return ResultDtoFactory.toNack("跳转上上签异常");
        }

    }

    /**
     * 更新店铺签约状态
     * @param signID
     * @return
     */
    @RequestMapping(value = "signedContract", method = RequestMethod.GET)
    public ResultDto<Object> updateLoanApplSigned(String signID){
        return updateSignContract(signID);
    }

    /**
     * 合同预览
     * @param tmpltType
     * @param ymMemberSignId
     * @return
     */
    @RequestMapping(value = "viewContract", method = RequestMethod.GET)
    public ResultDto<Object> viewContract(ECntrctTmpltType tmpltType, Long ymMemberSignId){
        String userId = SecurityContext.getCurrentUser().getUserId();

        try {
            String json = invokeBsViewContract(userId, tmpltType, ymMemberSignId.toString());
            if (StringUtils.isEmpty(json)){
                return ResultDtoFactory.toNack("失败");
            }
            return ResultDtoFactory.toAck("成功", json);
        } catch (Exception e) {
            LOGGER.error("合同预览异常",e);
            return ResultDtoFactory.toNack("合同预览异常");
        }
    }

    private String invokeBsViewContract(String userId, ECntrctTmpltType tmpltType, String bizId) {
        UserContractDto contractDto = contractService.getUserContractByBizId(userId, tmpltType, bizId);

        if (contractDto != null){
            BsViewContractReq req = new BsViewContractReq();
            req.setFsdid(contractDto.getBsSignid());
            req.setDocid(contractDto.getBsDocid());
            try {
                return bestSignService.viewContract(req);
            } catch (Exception e) {
                LOGGER.error("合同预览异常", e);
                throw new BizServiceException();
            }
        }

        return null;
    }

    private UserContractDto getTemplateData(StoreInfDto storeInfDto, UserInfoDto userInfoDto, BankCardDto bankCardDto, String bizId, String alipayBizRate, String wechatBizRate){
        UserContractDto contractDto = new UserContractDto();
        contractDto.setUserId(userInfoDto.getUserId());
        contractDto.setSignName(userInfoDto.getUserName());
        contractDto.setTemplateType(ECntrctTmpltType.YM01001);
        Map<String, String> model = new HashMap<>();
        model.put("storeName", storeInfDto.getStoreName());
        model.put("bizLicence", storeInfDto.getBizLicence());
        model.put("address", storeInfDto.getProvince() + storeInfDto.getCity() + storeInfDto.getArea() + storeInfDto.getStreet());
        model.put("userName", userInfoDto.getUserName());
        model.put("mobile", userInfoDto.getMobile());
        model.put("email", userInfoDto.getMobile() + "@163.com");
        model.put("bankCardName", bankCardDto.getBankCardName());
        model.put("bankName", bankCardDto.getBankName());
        model.put("bankCardNo", bankCardDto.getBankCardNo());
        model.put("idCardNo", userInfoDto.getIdCardNo());
        model.put("alipayBizRate", alipayBizRate + "%");
        model.put("wechatBizRate", wechatBizRate + "%");
        contractDto.setModel(model);
        contractDto.setBizId(bizId);
        return contractDto;
    }

    private void rollbackContract(String contId, Long ymMemberSignId){
        ymMemberSignService.deleteById(ymMemberSignId);
        contractService.deleteUserContractByContId(contId);
    }

    private ResultDto checkSigned(YmMemberSignDto ymMemberSignDto, String userId){
        //检查签约状态，未签约，则去上上签查询合同状态
        UserContractDto userContractDto = contractService.getUserContractByBizId(userId, ECntrctTmpltType.YM01001, String.valueOf(ymMemberSignDto.getId()));
        if (userContractDto != null && !StringUtils.isEmpty(userContractDto.getBsSignid())){
            try {
                String contractJson = bestSignService.contractInfo(userContractDto.getBsSignid());
                ContractInfoWrap contractInfoWrap = JsonUtil.toObject(ContractInfoWrap.class, contractJson);
                if (RET_CODE.equals(contractInfoWrap.getResp().getInfo().getCode())){
                    if (ContractInfoConstant.CONTRACT_FINISHED.equals(contractInfoWrap.getResp().getContent().getContractinfo().getStatus())){
                        ResultDto resultDto = updateSignContract(userContractDto.getBsSignid());
                        if (!"ACK".equalsIgnoreCase(resultDto.getCode())){
                            return resultDto;
                        }
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
        return null;
    }

    private ResultDto updateSignContract(String signID){
        UserContractDto contractDto = contractService.getUserContractBySignId(signID);
        if (contractDto == null){
            return ResultDtoFactory.toNack("找不到合同信息");
        }
        YmMemberSignDto ymMemberSignDto = ymMemberSignService.getYmMemberSignById(Long.parseLong(contractDto.getBizId()));
        if (ymMemberSignDto == null){
            return ResultDtoFactory.toNack("找不到店铺签约信息");
        }
        try {
            ymMemberSignService.updateYmMemberSignStatusById(ymMemberSignDto.getId());
        } catch (BizServiceException e) {
            rollbackContract(contractDto.getContractId(), ymMemberSignDto.getId());
            LOGGER.error("更新签约状态失败", e);
            return ResultDtoFactory.toNack("更新签约状态失败");
        }
        try {
            byte[] pdfBytes = bestSignService.contractDownloadMobile(signID);
            String firstPageFilePath = fileStoreService.upload(EFileType.USER_CONTRACT,
                    ToImageUtils.pdfToImg(pdfBytes, ToImageUtils.firstPage),
                    contractDto.getContractId() + "-fp-bs.jpg");
            String lastPageFilePath = fileStoreService.upload(EFileType.USER_CONTRACT,
                    ToImageUtils.pdfToImgLastPage(pdfBytes),
                    contractDto.getContractId() + "-lp-bs.jpg");

            ymMemberSignService.updateYmMemberSignFilePath(ymMemberSignDto.getId(), firstPageFilePath, lastPageFilePath);
        } catch (Exception e) {
            rollbackContract(contractDto.getContractId(), ymMemberSignDto.getId());
            LOGGER.error("下载合同失败", e);
            return ResultDtoFactory.toNack("下载合同失败");
        }
        return ResultDtoFactory.toAck("成功");
    }

}
