package com.xinyunlian.jinfu.shopkeeper.controller;

import com.xinyunlian.jinfu.bank.dto.BankCardDto;
import com.xinyunlian.jinfu.bank.service.BankService;
import com.xinyunlian.jinfu.common.converter.ConverterService;
import com.xinyunlian.jinfu.common.dto.ResultDto;
import com.xinyunlian.jinfu.common.dto.ResultDtoFactory;
import com.xinyunlian.jinfu.common.fileStore.FileStoreService;
import com.xinyunlian.jinfu.common.fileStore.enums.EFileType;
import com.xinyunlian.jinfu.common.security.SecurityContext;
import com.xinyunlian.jinfu.common.util.DateHelper;
import com.xinyunlian.jinfu.common.util.ImageUtils;
import com.xinyunlian.jinfu.common.util.MessageUtil;
import com.xinyunlian.jinfu.common.yitu.YituService;
import com.xinyunlian.jinfu.common.yitu.dto.resp.OCRDto;
import com.xinyunlian.jinfu.contract.dto.UserContractDto;
import com.xinyunlian.jinfu.contract.enums.ECntrctTmpltType;
import com.xinyunlian.jinfu.contract.service.ContractService;
import com.xinyunlian.jinfu.mq.DestinationDefine;
import com.xinyunlian.jinfu.mq.sender.QueueSender;
import com.xinyunlian.jinfu.picture.dto.PictureDto;
import com.xinyunlian.jinfu.picture.enums.EPictureType;
import com.xinyunlian.jinfu.picture.service.PictureService;
import com.xinyunlian.jinfu.shopkeeper.dto.card.BaseInfoDto;
import com.xinyunlian.jinfu.shopkeeper.dto.card.IdAuthDto;
import com.xinyunlian.jinfu.user.dto.CertifyDto;
import com.xinyunlian.jinfu.user.dto.UserExtDto;
import com.xinyunlian.jinfu.user.dto.UserInfoDto;
import com.xinyunlian.jinfu.user.dto.req.IDCardDto;
import com.xinyunlian.jinfu.user.service.UserExtService;
import com.xinyunlian.jinfu.user.service.UserService;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.File;
import java.io.IOException;
import java.util.*;

import static com.xinyunlian.jinfu.common.util.DateHelper.SIMPLE_DATE_YMD;


/**
 * Created by King on 2017/2/15.
 */
@Controller
@RequestMapping(value = "shopkeeper/user")
public class UserInfoController {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserInfoController.class);
    @Autowired
    private UserExtService userExtService;
    @Autowired
    private UserService userService;
    @Autowired
    private FileStoreService fileStoreService;
    @Autowired
    private YituService yituService;
    @Autowired
    private BankService bankService;
    @Autowired
    private PictureService pictureService;
    @Autowired
    private ContractService contractService;
    @Autowired
    private QueueSender queueSender;

    /**
     * 获得身份认证信息
     *
     * @return
     */
    @RequestMapping(value = "/idAuth/info", method = RequestMethod.GET)
    @ResponseBody
    public ResultDto<Object> getIdAuthInfo() {
        String userId = SecurityContext.getCurrentUserId();
        UserExtDto userExtDto = userExtService.findUserByUserId(userId);
        UserInfoDto userInfoDto = userService.findUserByUserId(userId);
        IdAuthDto idAuthDto = ConverterService.convert(userExtDto,IdAuthDto.class);
        idAuthDto.setUserName(userInfoDto.getUserName());
        idAuthDto.setIdCardNo(userInfoDto.getIdCardNo());
        return ResultDtoFactory.toAck(MessageUtil.getMessage("common.operate.success"),idAuthDto);
    }

    /**
     * 获得基本信息
     *
     * @return
     */
    @RequestMapping(value = "/base/info", method = RequestMethod.GET)
    @ResponseBody
    public ResultDto<Object> getBaseInfo() {
        String userId = SecurityContext.getCurrentUserId();
        UserExtDto userExtDto = userExtService.findUserByUserId(userId);
        BaseInfoDto baseInfoDto = ConverterService.convert(userExtDto,BaseInfoDto.class);
        return ResultDtoFactory.toAck(MessageUtil.getMessage("common.operate.success"),baseInfoDto);
    }

    /**
     * 实名认证 带身份证正反面
     * @param certifyDto
     * @return
     */
    @RequestMapping(value = "/certify", method = RequestMethod.POST)
    @ResponseBody
    public ResultDto<Object> certifyBase(@RequestBody CertifyDto certifyDto) {
        return this.certify(certifyDto);
    }

    @RequestMapping(value = "/planA/certify", method = RequestMethod.POST)
    @ResponseBody
    public ResultDto<Object> certifyPlanA(@RequestBody CertifyDto certifyDto) {
        if(!StringUtils.isEmpty(certifyDto.getHeldIdCardBase64())) {
            try {
                File heldIdCardFile = ImageUtils.GenerateImageByBase64(certifyDto.getHeldIdCardBase64());
                String heldIdCardPath = fileStoreService.upload(EFileType.USER_INFO_IMG, heldIdCardFile, heldIdCardFile.getName());
                List<PictureDto> pictureDtos = new ArrayList<>();
                PictureDto pictureDto = new PictureDto();
                pictureDto.setParentId(SecurityContext.getCurrentUserId());
                pictureDto.setPictureType(EPictureType.HELD_ID_CARD);
                pictureDto.setPicturePath(heldIdCardPath);
                pictureDtos.add(pictureDto);
                pictureService.savePicture(pictureDtos);

            } catch (IOException e) {
                LOGGER.error("手持身份证图片上传失败", e);
            } catch (Exception e) {
                LOGGER.error("未知错误", e);
            }
        }

        if(!StringUtils.isEmpty(certifyDto.getIdCardFrontBase64())) {
            ResultDto<Object> resultDto = this.certify(certifyDto);
            if(!"ACK".equals(resultDto.getCode())){
                return resultDto;
            }
        }
        queueSender.send(DestinationDefine.REAL_AUTH_EVENT, SecurityContext.getCurrentUserId());
        return ResultDtoFactory.toAck("实名认证成功");
    }


    private ResultDto<Object> certify(CertifyDto certifyDto){
        if (StringUtils.isEmpty(certifyDto.getIdCardFrontBase64()) || StringUtils.isEmpty(certifyDto.getIdCardBackBase64())) {
            return ResultDtoFactory.toNack("身份证照片必传");
        }
        if (StringUtils.isEmpty(certifyDto.getUserName()) || StringUtils.isEmpty(certifyDto.getIdCardNo())) {
            return ResultDtoFactory.toNack("姓名和身份证号必填");
        }
        UserInfoDto userInfoDto = userService.findUserByUserId(SecurityContext.getCurrentUserId());
        if (userInfoDto == null) {
            return ResultDtoFactory.toNack("用户不存在");
        }
        if (userInfoDto.getIdentityAuth()) {
            return ResultDtoFactory.toNack("实名认证已通过");
        }
        List<BankCardDto> bankCardDtos = bankService.findByUserId(SecurityContext.getCurrentUserId());
        if(!CollectionUtils.isEmpty(bankCardDtos)){
            if(!bankCardDtos.get(0).getBankCardName().equals(certifyDto.getUserName())
                    || !bankCardDtos.get(0).getIdCardNo().equals(certifyDto.getIdCardNo())){
                return ResultDtoFactory.toNack("您提供的实名信息与银行卡的所属人不符，请检查您的实名信息或编辑您的银行卡。");
            }
        }
        //身份证上传
        String idCardPic1Path = null;
        String idCardPic2Path = null;

        IDCardDto idCardDto = new IDCardDto();
        idCardDto.setUserId(userInfoDto.getUserId());
        idCardDto.setUserName(certifyDto.getUserName());
        idCardDto.setIdCardNo(certifyDto.getIdCardNo());

        try {
            File idCardPic1File = ImageUtils.GenerateImageByBase64(certifyDto.getIdCardFrontBase64());
            idCardPic1Path = fileStoreService.upload(EFileType.USER_INFO_IMG, idCardPic1File, idCardPic1File.getName());

            OCRDto ocrDto = yituService.ocr(certifyDto.getIdCardFrontBase64(),1);
            idCardDto.setNationAddress(ocrDto.getAddress());
            idCardDto.setSex(ocrDto.getGender());
            idCardDto.setNation(ocrDto.getNation());
            idCardDto.setBirthDate(DateHelper.getDate(ocrDto.getBirthday(),"yyyy.MM.dd"));
            idCardDto.setCardFrontFilePath(idCardPic1Path);
        } catch (IOException e) {
            LOGGER.error("身份证正面图片上传失败", e);
        } catch (Exception e) {
            LOGGER.error("读取身份证正面ocr错误",e);
        }
        try {
            File idCardPic2File = ImageUtils.GenerateImageByBase64(certifyDto.getIdCardBackBase64());
            idCardPic2Path = fileStoreService.upload(EFileType.USER_INFO_IMG, idCardPic2File, idCardPic2File.getName());

            com.xinyunlian.jinfu.common.yitu.dto.resp.OCRDto ocrDto = yituService.ocr(certifyDto.getIdCardBackBase64(),2);
            idCardDto.setIdAuthority(ocrDto.getAgency());
            idCardDto.setIdExpiredBegin(DateHelper.getDate(ocrDto.getValidDateBegin(),SIMPLE_DATE_YMD));
            idCardDto.setIdExpiredEnd(DateHelper.getDate(ocrDto.getValidDateEnd(),SIMPLE_DATE_YMD));
            idCardDto.setCardBackFilePath(idCardPic2Path);
        } catch (IOException e) {
            LOGGER.error("身份证反面图片上传失败", e);
        } catch (Exception e) {
            LOGGER.error("读取身份证反面ocr错误",e);
        }

        boolean flag = userService.certifyWithCard(idCardDto);
        if (!flag) {
            return ResultDtoFactory.toNack("实名认证失败");
        }

        return ResultDtoFactory.toAck("实名认证成功");
    }

    @ApiOperation(value = "获取用户征信查询授权")
    @RequestMapping(value = "user/zxcx", method = RequestMethod.GET)
    @ResponseBody
    public String getUserZxcx(){
        UserInfoDto userInfoDto = userService.findUserByUserId(SecurityContext.getCurrentUserId());

        UserContractDto userContractDto = new UserContractDto();
        userContractDto.setTemplateType(ECntrctTmpltType.ZXCX);
        Map<String, String> model = new HashMap<>();
        userContractDto.setModel(model);
        model.put("signName", userInfoDto.getUserName());
        model.put("idCardNo", userInfoDto.getIdCardNo());
        model.put("signDate", DateHelper.formatDate(new Date(), DateHelper.SIMPLE_DATE_YMD_CN));

        return contractService.getContract(userContractDto).getContent();
    }

}
