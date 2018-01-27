package com.xinyunlian.jinfu.thirdparty.nbcb.controller;

import com.xinyunlian.jinfu.cashier.api.JinfuCashierConstants;
import com.xinyunlian.jinfu.cashier.api.JinfuCashierSignature;
import com.xinyunlian.jinfu.common.fileStore.FileStoreService;
import com.xinyunlian.jinfu.common.fileStore.enums.EFileType;
import com.xinyunlian.jinfu.common.security.StaticResourceSecurity;
import com.xinyunlian.jinfu.common.util.AppConfigUtil;
import com.xinyunlian.jinfu.common.util.DateHelper;
import com.xinyunlian.jinfu.common.util.FileUtils;
import com.xinyunlian.jinfu.common.util.JsonUtil;
import com.xinyunlian.jinfu.industry.dto.IndustryDto;
import com.xinyunlian.jinfu.industry.service.IndustryService;
import com.xinyunlian.jinfu.picture.dto.PictureDto;
import com.xinyunlian.jinfu.picture.enums.EPictureType;
import com.xinyunlian.jinfu.picture.service.PictureService;
import com.xinyunlian.jinfu.spider.service.RiskUserInfoService;
import com.xinyunlian.jinfu.store.dto.StoreInfDto;
import com.xinyunlian.jinfu.thirdparty.nbcb.dto.NBCBOrderDto;
import com.xinyunlian.jinfu.thirdparty.nbcb.dto.req.*;
import com.xinyunlian.jinfu.thirdparty.nbcb.dto.resp.CommonRespDto;
import com.xinyunlian.jinfu.thirdparty.nbcb.dto.resp.LinkmanDto;
import com.xinyunlian.jinfu.thirdparty.nbcb.dto.resp.NBCBUserInfoRespDto;
import com.xinyunlian.jinfu.thirdparty.nbcb.enums.ENBCBApprStatus;
import com.xinyunlian.jinfu.thirdparty.nbcb.enums.ENBCBCreditStatus;
import com.xinyunlian.jinfu.thirdparty.nbcb.enums.ENBCBReceiveStatus;
import com.xinyunlian.jinfu.thirdparty.nbcb.service.NBCBOrderService;
import com.xinyunlian.jinfu.user.dto.UserExtDto;
import com.xinyunlian.jinfu.user.dto.UserInfoDto;
import com.xinyunlian.jinfu.user.dto.UserLinkmanDto;
import com.xinyunlian.jinfu.user.dto.resp.UserDetailDto;
import com.xinyunlian.jinfu.user.enums.EHouseProperty;
import com.xinyunlian.jinfu.user.enums.EMarryStatus;
import com.xinyunlian.jinfu.user.enums.ERelationship;
import com.xinyunlian.jinfu.user.service.UserExtService;
import com.xinyunlian.jinfu.user.service.UserLinkmanService;
import com.xinyunlian.jinfu.user.service.UserService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;

/**
 * Created by bright on 2017/5/17.
 */
@RestController
@RequestMapping(value = "thirdparty/nbcb")
public class NBCBController {

    public static final Logger LOGGER = LoggerFactory.getLogger(NBCBController.class);

    @Autowired
    private UserExtService userExtService;

    @Autowired
    private NBCBOrderService nbcbOrderService;

    @Autowired
    private UserService userService;

    @Autowired
    private UserLinkmanService userLinkmanService;

    @Autowired
    private PictureService pictureService;

    @Autowired
    private FileStoreService fileStoreService;

    @Autowired
    private RiskUserInfoService riskUserInfoService;

    @Autowired
    private IndustryService industryService;

    private Boolean signVerified(CommonReqDto req){

        if(!AppConfigUtil.isProdEnv()){
            if(StringUtils.equals(req.getSign(), "PLEASEIGNORESIGN")){
                return Boolean.TRUE;
            }
        }

        Boolean verified = Boolean.FALSE;
        try {
            Map<String, String> params = JsonUtil.toMap(req);
            String sign = params.remove("sign");
            String content = JinfuCashierSignature.getSortedNotEmptyContentFromMap(params);
            verified = JinfuCashierSignature.verifySign(content, sign, AppConfigUtil.getConfig("nbcb.pubkey"), JinfuCashierConstants.DEFAULT_CHARSET, JinfuCashierConstants.SIGN_TYPE_RSA);
        } catch (Exception e) {
            LOGGER.error("验签失败", e);
        }
        return verified;
    }

    private void sign(CommonRespDto resp){
        String key = AppConfigUtil.getConfig("nbcb.privatekey");
        String sign = JinfuCashierSignature.computeSign(JsonUtil.toMap(resp), key, JinfuCashierConstants.DEFAULT_CHARSET, JinfuCashierConstants.SIGN_TYPE_RSA);
        resp.setSign(sign);
    }

    private String processLinkmanRelationship(ERelationship eRelationship){
        switch (eRelationship){
            case COUPLE:
                return "1";
            case PARENT:
                return "2";
            case BROTHER_SISTER:
                return "3";
            case FRIEND:
                return "4";
            case TRANSFER:
                return "5";
            default:
                return "6";
        }
    }

    private String processMarriageStatus(EMarryStatus eMarryStatus){
        switch (eMarryStatus){
            case HAS_BABY:
            case MARRIED:
                return "1";
            case NOT_MARRIED:
                return "0";
            case DIVORCED:
                return "2";
            case SPOUSES_LOSS:
                return "4";
        }
        return "5";
    }

    private String processHouseProperty(EHouseProperty eHouseProperty){
        switch (eHouseProperty){
            case OWN:
                return "10";
            case MORTGAGE:
                return "20";
            case TENANCY:
                return "50";
            default:
                return "60";
        }
    }

    @RequestMapping(value = "/userinfo", method = RequestMethod.POST)
    @ResponseBody
    public NBCBUserInfoRespDto getUserInfos(@RequestBody NBCBUserInfoReqDto req) {
        LOGGER.info("request from nbcb {}", JsonUtil.toJson(req));

        if(!signVerified(req)){
            NBCBUserInfoRespDto resp = new NBCBUserInfoRespDto();
            resp.setRespCode("NACK");
            resp.setRespMessage("验签失败");
            return resp;
        }

        File storeTobaccoPicFile = null,
                storeLicencePicFile = null,
                storeInnerPicFile = null,
                storeHeaderPicFile = null,
                userFrontPicFile = null,
                userBackPicFile = null,
                heldIdCardPicFile = null,
                residenceBookletPicFile = null,
                houseCertificationPicFile = null,
                marryCertificationPicFile = null,
                storeHouseCertificationPicFile = null;

        UserDetailDto userDetail = userService.findUserDetailByUserId(req.getUserId());

        UserInfoDto userInfo = userDetail.getUserDto();
        List<StoreInfDto> stores = userDetail.getStoreInfPoList();
        UserExtDto userExt = userDetail.getUserExtDto();

        List<UserLinkmanDto> linkmen = userLinkmanService.findByUserId(userInfo.getUserId());

        NBCBUserInfoRespDto resp = new NBCBUserInfoRespDto();

        resp.setUserId(userInfo.getUserId());
        resp.setIdNo(userInfo.getIdCardNo());
        if (Objects.nonNull(userExt)) {
            resp.setAddress(userExt.getAddress());
            if (userExt.getMarryStatus() != null) {
                resp.setMarryStatus(processMarriageStatus(userExt.getMarryStatus()));
            }
            if(StringUtils.isNotEmpty(userExt.getPhone())) {
                resp.setPhone(userExt.getPhone());
            } else {
                resp.setPhone(userInfo.getMobile());
            }
            if(Objects.nonNull(userExt.getYituSimilarity())) {
                resp.setSimilarity(String.valueOf(userExt.getYituSimilarity()));
            }
        }

        resp.setHouseProperty(processHouseProperty(userExt.getHouseProperty()));

        if (CollectionUtils.isNotEmpty(linkmen)) {
            List<LinkmanDto> userLinkmanDtos = new ArrayList<>();
            linkmen.forEach(userLinkmanDto -> {
                LinkmanDto linkmanDto = new LinkmanDto(userLinkmanDto.getName(),
                        processLinkmanRelationship(userLinkmanDto.getRelationship()),
                        userLinkmanDto.getMobile());
                userLinkmanDtos.add(linkmanDto);
            });
            resp.setLinkmen(JsonUtil.toJson(userLinkmanDtos));
        }
        resp.setMobileNo(userInfo.getMobile());
        resp.setName(userInfo.getUserName());
        if (CollectionUtils.isNotEmpty(stores)) {
            StoreInfDto store = stores.get(0);
            resp.setStoreName(store.getStoreName());
            resp.setTobaccoCertificationNo(store.getTobaccoCertificateNo());
            PictureDto tobaccoPic = pictureService.get(String.valueOf(store.getStoreId()), EPictureType.STORE_TOBACCO);
            if (Objects.nonNull(tobaccoPic)) {
                storeTobaccoPicFile = fileStoreService.download(tobaccoPic.getPicturePath());
            }
            PictureDto licencePic = pictureService.get(String.valueOf(store.getStoreId()), EPictureType.STORE_LICENCE);
            if (Objects.nonNull(licencePic)) {
                storeLicencePicFile = fileStoreService.download(licencePic.getPicturePath());
            }
            PictureDto innerPic = pictureService.get(String.valueOf(store.getStoreId()), EPictureType.STORE_INNER);
            if (Objects.nonNull(innerPic)) {
                storeInnerPicFile = fileStoreService.download(innerPic.getPicturePath());
            }
            PictureDto storeHeaderPic = pictureService.get(String.valueOf(store.getStoreId()), EPictureType.STORE_HEADER);
            if (Objects.nonNull(storeHeaderPic)) {
                storeHeaderPicFile = fileStoreService.download(storeHeaderPic.getPicturePath());
            }
            PictureDto storeHouseCertificationPic = pictureService.get(String.valueOf(store.getStoreId()), EPictureType.STORE_HOUSE_CERTIFICATE);
            if (Objects.nonNull(storeHouseCertificationPic)) {
                storeHouseCertificationPicFile = fileStoreService.download(storeHouseCertificationPic.getPicturePath());
            }

            String mccCode = store.getIndustryMcc();
            if(StringUtils.isNotEmpty(mccCode)){
                IndustryDto industryDto = industryService.getByMcc(mccCode);
                if(Objects.nonNull(industryDto)){
                    resp.setBizRetailFormat(industryDto.getIndName());
                }
            }

        }

        PictureDto userFrontPic = pictureService.get(userInfo.getUserId(), EPictureType.CARD_FRONT);
        if (Objects.nonNull(userFrontPic)) {
            userFrontPicFile = fileStoreService.download(userFrontPic.getPicturePath());
        }
        PictureDto userBackPic = pictureService.get(userInfo.getUserId(), EPictureType.CARD_BACK);
        if (Objects.nonNull(userBackPic)) {
            userBackPicFile = fileStoreService.download(userBackPic.getPicturePath());
        }

        PictureDto heldIdCardPic = pictureService.get(userInfo.getUserId(), EPictureType.HELD_ID_CARD);
        if (Objects.nonNull(heldIdCardPic)) {
            heldIdCardPicFile = fileStoreService.download(heldIdCardPic.getPicturePath());
        }

        PictureDto residenceBookletPic = pictureService.get(userInfo.getUserId(), EPictureType.RESIDENCE_BOOKLET);
        if (Objects.nonNull(residenceBookletPic)) {
            residenceBookletPicFile = fileStoreService.download(residenceBookletPic.getPicturePath());
        }

        PictureDto houseCertificationPic = pictureService.get(userInfo.getUserId(), EPictureType.HOUSE_CERTIFICATE);
        if (Objects.nonNull(houseCertificationPic)) {
            houseCertificationPicFile = fileStoreService.download(houseCertificationPic.getPicturePath());
        }

        PictureDto marryCertificationPic = pictureService.get(userInfo.getUserId(), EPictureType.MARRY_CERTIFICATE);
        if (Objects.nonNull(marryCertificationPic)) {
            marryCertificationPicFile = fileStoreService.download(marryCertificationPic.getPicturePath());
        }

        String zipFileName = userInfo.getIdCardNo() + "TobaccoPicture";

        String folderName = FileUtils.getTempDirectoryPath() + "/" + zipFileName + "/";

        FileUtils.createDirectory(folderName);
        if (Objects.nonNull(storeTobaccoPicFile)) {
            try {
                FileUtils.copyFile(storeTobaccoPicFile.getAbsoluteFile(), new File(folderName + userInfo.getIdCardNo() + "OtherBusiness-1." + FilenameUtils.getExtension(storeTobaccoPicFile.getName())));
            } catch (IOException e) {
            }
        }
        if (Objects.nonNull(heldIdCardPicFile)) {
            try {
                FileUtils.copyFile(heldIdCardPicFile.getAbsoluteFile(), new File(folderName + userInfo.getIdCardNo() + "OtherBusiness-2." + FilenameUtils.getExtension(heldIdCardPicFile.getName())));
            } catch (IOException e) {
            }
        }
        if (Objects.nonNull(houseCertificationPicFile)) {
            try {
                FileUtils.copyFile(houseCertificationPicFile.getAbsoluteFile(), new File(folderName + userInfo.getIdCardNo() + "PropertyRight-1." + FilenameUtils.getExtension(houseCertificationPicFile.getName())));
            } catch (IOException e) {
            }
        }
        if (Objects.nonNull(storeHouseCertificationPicFile)) {
            try {
                FileUtils.copyFile(storeHouseCertificationPicFile.getAbsoluteFile(), new File(folderName + userInfo.getIdCardNo() + "PropertyRight-2." + FilenameUtils.getExtension(storeHouseCertificationPicFile.getName())));
            } catch (IOException e) {
            }
        }
        if (Objects.nonNull(userFrontPicFile)) {
            try {
                FileUtils.copyFile(userFrontPicFile.getAbsoluteFile(), new File(folderName + userInfo.getIdCardNo() + "-1." + FilenameUtils.getExtension(userFrontPicFile.getName())));
            } catch (IOException e) {
            }
        }
        if (Objects.nonNull(userBackPicFile)) {
            try {
                FileUtils.copyFile(userBackPicFile.getAbsoluteFile(), new File(folderName + userInfo.getIdCardNo() + "-2." + FilenameUtils.getExtension(userBackPicFile.getName())));
            } catch (IOException e) {
            }
        }
        if (Objects.nonNull(residenceBookletPicFile)) {
            try {
                FileUtils.copyFile(residenceBookletPicFile.getAbsoluteFile(), new File(folderName + userInfo.getIdCardNo() + "ProofOfResidence." + FilenameUtils.getExtension(residenceBookletPicFile.getName())));
            } catch (IOException e) {
            }
        }
        if (Objects.nonNull(storeInnerPicFile)) {
            try {
                FileUtils.copyFile(storeInnerPicFile.getAbsoluteFile(), new File(folderName + userInfo.getIdCardNo() + "CreditPhoto-1." + FilenameUtils.getExtension(storeInnerPicFile.getName())));
            } catch (IOException e) {
            }
        }
        if (Objects.nonNull(storeHeaderPicFile)) {
            try {
                FileUtils.copyFile(storeHeaderPicFile.getAbsoluteFile(), new File(folderName + userInfo.getIdCardNo() + "CreditPhoto-2." + FilenameUtils.getExtension(storeHeaderPicFile.getName())));
            } catch (IOException e) {
            }
        }
        if (Objects.nonNull(marryCertificationPicFile)) {
            try {
                FileUtils.copyFile(marryCertificationPicFile.getAbsoluteFile(), new File(folderName + userInfo.getIdCardNo() + "MarriageLines." + FilenameUtils.getExtension(marryCertificationPicFile.getName())));
            } catch (IOException e) {
            }
        }
        if (Objects.nonNull(storeLicencePicFile)) {
            try {
                FileUtils.copyFile(storeLicencePicFile.getAbsoluteFile(), new File(folderName + userInfo.getIdCardNo() + "BusinessLicence." + FilenameUtils.getExtension(storeLicencePicFile.getName())));
            } catch (IOException e) {
            }
        }

        FileUtils.zipFiles(folderName, "", FileUtils.getTempDirectoryPath() + "/" + zipFileName + ".zip");

        try {
            String uploadedFileName = fileStoreService.upload(EFileType.DATA_FILE_PATH, new File(FileUtils.getTempDirectoryPath() + "/" + zipFileName + ".zip"), zipFileName + ".zip");
            resp.setUserPic(AppConfigUtil.getConfig("file.addr") + StaticResourceSecurity.getSecurityURI(uploadedFileName));
            FileUtils.deleteDirectory(folderName);
        } catch (IOException e) {
        }

        Integer age = Calendar.getInstance().get(Calendar.YEAR) - Integer.valueOf(userInfo.getIdCardNo().substring(6, 10));
        resp.setAge(String.valueOf(age));

        resp.setTobaccoRegDate("2015/01/01");

        resp.setPreCreditLine(String.valueOf(getPreCreditLine(userInfo.getUserId())));

        resp.setRespCode("ACK");
        resp.setRespMessage("获取成功");

        sign(resp);

        LOGGER.info("response to nbcb {}", JsonUtil.toJson(resp));
        return resp;
    }

    private BigDecimal getPreCreditLine(String userId){
        BigDecimal preCredit = BigDecimal.ZERO;
        BigDecimal tobaccoOrderAmount = riskUserInfoService.getYearlyOrderAmout(userId);

        preCredit = tobaccoOrderAmount.divide(BigDecimal.valueOf(4), BigDecimal.ROUND_HALF_UP);

        UserExtDto userExtDto = userExtService.findUserByUserId(userId);

        Boolean hasHourseProperty =  (userExtDto.getHouseProperty() == EHouseProperty.OWN ||
                userExtDto.getHouseProperty() == EHouseProperty.MORTGAGE);

        if(hasHourseProperty){
            preCredit = preCredit.add(BigDecimal.valueOf(50000));
        }

        preCredit = preCredit.divide(BigDecimal.valueOf(10000), BigDecimal.ROUND_HALF_UP)
                .setScale(0, BigDecimal.ROUND_HALF_UP).multiply(BigDecimal.valueOf(10000));
        return preCredit;
    }

    @RequestMapping(value = "/receive", method = RequestMethod.POST)
    @ResponseBody
    public CommonRespDto updateReceiveStatus(@RequestBody NBCBOrderReceiveStatusUpdateReqDto req) {
        LOGGER.info("request from nbcb {}", JsonUtil.toJson(req));

        if(!signVerified(req)){
            CommonRespDto resp = new CommonRespDto();
            resp.setRespCode("NACK");
            resp.setRespMessage("验签失败");
            return resp;
        }

        NBCBOrderDto order = nbcbOrderService.findByOrderNo(req.getOrderNo());
        if(Objects.nonNull(order)) {
            if (StringUtils.equals(req.getOrderStatus(), "1")) {
                order.setReceiveStatus(ENBCBReceiveStatus.SUCCESS);
            } else if (StringUtils.equals(req.getOrderStatus(), "2")) {
                order.setReceiveStatus(ENBCBReceiveStatus.FAILED);
            }
            nbcbOrderService.updateOrder(order);
        } else {
            LOGGER.error("宁波银行订单不存在，订单号:{}", req.getOrderNo());
        }

        CommonRespDto resp = new CommonRespDto();
        resp.setRespCode("ACK");
        resp.setRespMessage("更新成功");
        sign(resp);

        LOGGER.info("response to nbcb {}", JsonUtil.toJson(resp));
        return resp;
    }

    @RequestMapping(value = "/loans", method = RequestMethod.POST)
    @ResponseBody
    public CommonRespDto updateApprStatus(@RequestBody NBCBLoanListReqDto req){
        LOGGER.info("request from nbcb {}", JsonUtil.toJson(req));

        if(!signVerified(req)){
            CommonRespDto resp = new CommonRespDto();
            resp.setRespCode("NACK");
            resp.setRespMessage("验签失败");
            return resp;
        }

        List<NBCBLoanDtlDto> loanDtls = JsonUtil.toObject(List.class, NBCBLoanDtlDto.class, req.getLoans());
        loanDtls.forEach(nbcbLoanDtlDto -> {
            String orderNo = nbcbLoanDtlDto.getOrderNo();
            NBCBOrderDto order = nbcbOrderService.findByOrderNo(orderNo);
            if(Objects.isNull(order)){
                LOGGER.error("宁波银行订单不存在，订单号:{}", orderNo);
                return;
            }
            if(nbcbLoanDtlDto.getCreditStatus() != null) {
                switch (nbcbLoanDtlDto.getCreditStatus()) {
                    case "1":
                        order.setCreditStatus(ENBCBCreditStatus.NORMAL);
                        break;
                    case "9":
                        order.setCreditStatus(ENBCBCreditStatus.EXPIRED);
                        break;
                }
            }
            /*switch (nbcbLoanDtlDto.getLoanStatus()){
                case "1":
                    order.setLoanStatus(ENBCBLoanStatus.PAIDOFF);
                    break;
                case "2":
                    order.setLoanStatus(ENBCBLoanStatus.NOMRAL);
                    break;
                case "3":
                    order.setLoanStatus(ENBCBLoanStatus.OVERDUE);
                    break;
            }*/
            if(nbcbLoanDtlDto.getOrderStatus() != null) {
                switch (nbcbLoanDtlDto.getOrderStatus()) {
                    case "1":
                        order.setApprStatus(ENBCBApprStatus.APPROVED);
                        break;
                    case "2":
                        order.setApprStatus(ENBCBApprStatus.PROCESSING);
                        break;
                    case "9":
                        order.setApprStatus(ENBCBApprStatus.REJECTED);
                        break;
                }
            }
            if(StringUtils.isNotEmpty(nbcbLoanDtlDto.getCreditDeadLine())){
                order.setCreditDeadLine(DateHelper.getDate(nbcbLoanDtlDto.getCreditDeadLine(), "yyyy/MM/dd"));
            }
            if(StringUtils.isNotEmpty(nbcbLoanDtlDto.getCredit())){
                order.setCredit(new BigDecimal(nbcbLoanDtlDto.getCredit()));
            }
            if(StringUtils.isNotEmpty(nbcbLoanDtlDto.getLoanRemaining())){
                order.setLoanRemaining(new BigDecimal(nbcbLoanDtlDto.getLoanRemaining()));
            }
            /*if(StringUtils.isNotEmpty(nbcbLoanDtlDto.getLoanRemainingAvg())){
                order.setLoanRemainingAvg(new BigDecimal(nbcbLoanDtlDto.getLoanRemainingAvg()));
            }*/
            if(StringUtils.isNotEmpty(nbcbLoanDtlDto.getModifyTs())){
                order.setModifyTs(DateHelper.getDate(nbcbLoanDtlDto.getModifyTs(),"yyyy/MM/dd"));
            }
            nbcbOrderService.updateOrder(order);
        });
        CommonRespDto resp = new CommonRespDto();
        resp.setRespCode("ACK");
        resp.setRespMessage("更新成功");

        sign(resp);

        LOGGER.info("response to nbcb {}", JsonUtil.toJson(resp));
        return resp;
    }

}
