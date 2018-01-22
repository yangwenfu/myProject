package com.xinyunlian.jinfu.yunma.controller;

import com.xinyunlian.jinfu.area.dto.SysAreaInfDto;
import com.xinyunlian.jinfu.area.service.SysAreaInfService;
import com.xinyunlian.jinfu.bank.dto.BankCardDto;
import com.xinyunlian.jinfu.bank.dto.CorpBankDto;
import com.xinyunlian.jinfu.bank.service.BankService;
import com.xinyunlian.jinfu.bank.service.CorpBankService;
import com.xinyunlian.jinfu.cashier.dto.BankMappingDto;
import com.xinyunlian.jinfu.cashier.dto.CloudCodeMerchantDto;
import com.xinyunlian.jinfu.cashier.service.BankMappingService;
import com.xinyunlian.jinfu.cashier.service.CloudCodeService;
import com.xinyunlian.jinfu.common.converter.ConverterService;
import com.xinyunlian.jinfu.common.dto.ResultDto;
import com.xinyunlian.jinfu.common.dto.ResultDtoFactory;
import com.xinyunlian.jinfu.common.fileStore.FileStoreService;
import com.xinyunlian.jinfu.common.fileStore.enums.EFileType;
import com.xinyunlian.jinfu.common.security.SecurityContext;
import com.xinyunlian.jinfu.common.security.StaticResourceSecurity;
import com.xinyunlian.jinfu.common.util.AppConfigUtil;
import com.xinyunlian.jinfu.common.util.DateHelper;
import com.xinyunlian.jinfu.common.util.ImageUtils;
import com.xinyunlian.jinfu.common.yitu.YituService;
import com.xinyunlian.jinfu.common.yitu.dto.resp.OCRDto;
import com.xinyunlian.jinfu.dealer.dto.DealerDto;
import com.xinyunlian.jinfu.dealer.dto.DealerProdDto;
import com.xinyunlian.jinfu.dealer.dto.DealerProdSearchDto;
import com.xinyunlian.jinfu.dealer.enums.EDealerStatus;
import com.xinyunlian.jinfu.dealer.enums.EDealerUserLogType;
import com.xinyunlian.jinfu.dealer.service.DealerProdService;
import com.xinyunlian.jinfu.dealer.service.DealerService;
import com.xinyunlian.jinfu.dealer.service.DealerUserLogService;
import com.xinyunlian.jinfu.industry.dto.IndustryDto;
import com.xinyunlian.jinfu.industry.service.IndustryService;
import com.xinyunlian.jinfu.picture.dto.PictureDto;
import com.xinyunlian.jinfu.picture.enums.EPictureType;
import com.xinyunlian.jinfu.picture.service.PictureService;
import com.xinyunlian.jinfu.prod.dto.ProductDto;
import com.xinyunlian.jinfu.prod.enums.EProd;
import com.xinyunlian.jinfu.prod.enums.EShelfPlatform;
import com.xinyunlian.jinfu.prod.service.ProdService;
import com.xinyunlian.jinfu.store.dto.StoreInfDto;
import com.xinyunlian.jinfu.store.service.StoreService;
import com.xinyunlian.jinfu.trade.dto.YmBizDto;
import com.xinyunlian.jinfu.trade.enums.EBizCode;
import com.xinyunlian.jinfu.trade.service.YmBizService;
import com.xinyunlian.jinfu.user.dto.DealerUserDto;
import com.xinyunlian.jinfu.user.dto.UserInfoDto;
import com.xinyunlian.jinfu.user.dto.req.IDCardDto;
import com.xinyunlian.jinfu.user.enums.EDealerUserStatus;
import com.xinyunlian.jinfu.user.service.DealerUserService;
import com.xinyunlian.jinfu.user.service.UserService;
import com.xinyunlian.jinfu.yunma.dto.*;
import com.xinyunlian.jinfu.yunma.enums.EDepotReceiveStatus;
import com.xinyunlian.jinfu.yunma.enums.EDepotStatus;
import com.xinyunlian.jinfu.yunma.service.YMMemberService;
import com.xinyunlian.jinfu.yunma.service.YmDepotService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * Created by menglei on 2017年04月06日.
 */
@Controller
@RequestMapping(value = "yunma")
@Api(description = "云码相关")
public class YunMaController {

    private static final Logger LOGGER = LoggerFactory.getLogger(YunMaController.class);

    @Autowired
    private YMMemberService yMMemberService;
    @Autowired
    private StoreService storeService;
    @Autowired
    private UserService userService;
    @Autowired
    private BankService bankService;
    @Autowired
    private PictureService pictureService;
    @Autowired
    private YituService yituService;
    @Autowired
    private FileStoreService fileStoreService;
    @Autowired
    private BankMappingService bankMappingService;
    @Autowired
    private YmBizService ymBizService;
    @Autowired
    private SysAreaInfService sysAreaInfService;
    @Autowired
    private CloudCodeService cloudCodeService;
    @Autowired
    private ProdService prodService;
    @Autowired
    private DealerProdService dealerProdService;
    @Autowired
    private DealerUserLogService dealerUserLogService;
    @Autowired
    private CorpBankService corpBankService;
    @Autowired
    private IndustryService industryService;
    @Autowired
    private DealerUserService dealerUserService;
    @Autowired
    private DealerService dealerService;
    @Autowired
    private YmDepotService ymDepotService;

    /**
     * 判断云码是否已使用 true：已使用，false：未使用
     *
     * @param qrCodeNo
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/verifyQrCode", method = RequestMethod.GET)
    @ApiOperation(value = "判断云码是否已使用 true：已使用，false：未使用")
    public ResultDto<Object> verifyQrCode(@RequestParam String qrCodeNo) {
        if (StringUtils.isEmpty(qrCodeNo)) {
            return ResultDtoFactory.toNack("参数必传");
        }
        YMMemberDto yMMemberDto = yMMemberService.getMemberByQrCodeNo(qrCodeNo);
        Boolean flag = false;
        Long storeId = null;
        if (yMMemberDto != null) {
            flag = true;
            storeId = yMMemberDto.getStoreId();
        }
        Map<String, Object> map = new HashMap<>();
        map.put("used", flag);
        map.put("storeId", storeId);
        return ResultDtoFactory.toAck("获取成功", map);
    }

    /**
     * 根据商户id查询店铺列表
     *
     * @param userId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/storeList", method = RequestMethod.GET)
    @ApiOperation(value = "店铺列表 qrCodeUrl不为空说明已绑码")
    public ResultDto<List<YunMaStoreListDto>> getStoreList(@RequestParam String userId) {
        DealerUserDto dealerUserDto = (DealerUserDto) SecurityContext.getCurrentUser().getParamMap().get("dealerUserDto");
        List<StoreInfDto> storeList = storeService.findByUserId(userId);
        List<YMMemberDto> members = yMMemberService.getMemberListByUserId(userId);//已绑云码店铺

        Map<Long, YMMemberDto> memberMap = new HashMap<>();
        for (YMMemberDto dto : members) {
            memberMap.put(dto.getStoreId(), dto);
        }
        List<YunMaStoreListDto> list = new ArrayList<>();
        for (StoreInfDto dto : storeList) {//云码
            YunMaStoreListDto yunMaStoreListDto = ConverterService.convert(dto, YunMaStoreListDto.class);

            DealerProdDto dealerProdDto = new DealerProdDto();
            dealerProdDto.setDealerId(dealerUserDto.getDealerId());
            dealerProdDto.setProvinceId(dto.getProvinceId());
            dealerProdDto.setCityId(dto.getCityId());
            dealerProdDto.setAreaId(dto.getAreaId());
            dealerProdDto.setStreetId(dto.getStreetId());
            dealerProdDto.setProdId(EProd.P01001.getCode());
            List<DealerProdDto> dealerProdDtoList = dealerProdService.getByDealerIdAndAreaAndProdId(dealerProdDto);
            List<String> prodIdsList = new ArrayList<>();
            for (DealerProdDto dealerProd : dealerProdDtoList) {
                prodIdsList.add(dealerProd.getProdId());
            }
            List<ProductDto> prodList = new ArrayList<>();
            if (!CollectionUtils.isEmpty(prodIdsList)) {
                prodList = prodService.getProdListByIdPlatformInd(prodIdsList, EShelfPlatform.BUDDY, dto.getIndustryMcc());
            }
            if (CollectionUtils.isEmpty(prodList)) {//不在业务授权范围内
                yunMaStoreListDto.setType(2);
            }
            if (memberMap.get(dto.getStoreId()) != null) {//已绑云码
                yunMaStoreListDto.setType(1);
            }
            list.add(yunMaStoreListDto);
        }
        Collections.sort(list, (arg0, arg1) -> arg1.getStoreId().compareTo(arg0.getStoreId()));
        return ResultDtoFactory.toAck("获取成功", list);
    }

    /**
     * 获取资料补全页信息
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getSupply", method = RequestMethod.GET)
    @ApiOperation(value = "获取资料补全页信息")
    public ResultDto<SupplyDto> getSupply(@RequestParam Long storeId) {
        DealerUserDto dealerUserDto = (DealerUserDto) SecurityContext.getCurrentUser().getParamMap().get("dealerUserDto");
        //店铺信息
        StoreInfDto storeInfDto = storeService.findByStoreId(storeId);
        if (storeInfDto == null) {
            return ResultDtoFactory.toNack("店铺不存在");
        }
        UserInfoDto userInfoDto = userService.findUserByUserId(storeInfDto.getUserId());
        if (userInfoDto == null) {
            return ResultDtoFactory.toNack("用户不存在");
        }
        //判断店铺所在的地区是否是业务配置的地区范围内
        Boolean flag = prodService.checkProdArea(EProd.P01001.getCode(), Long.valueOf(storeInfDto.getDistrictId()), storeInfDto.getIndustryMcc());
        if (!flag) {
            return ResultDtoFactory.toNack("该地区尚未开通此业务");
        }
        //该店铺可办业务列表
        List<String> districtIdsList = new ArrayList<>();
        if (StringUtils.isNotEmpty(storeInfDto.getProvinceId())) {
            districtIdsList.add(storeInfDto.getProvinceId());
        }
        if (StringUtils.isNotEmpty(storeInfDto.getCityId())) {
            districtIdsList.add(storeInfDto.getCityId());
        }
        if (StringUtils.isNotEmpty(storeInfDto.getAreaId())) {
            districtIdsList.add(storeInfDto.getAreaId());
        }
        if (StringUtils.isNotEmpty(storeInfDto.getStreetId())) {
            districtIdsList.add(storeInfDto.getStreetId());
        }
        DealerProdSearchDto dealerProdSearchDto = new DealerProdSearchDto();
        dealerProdSearchDto.setDealerId(dealerUserDto.getDealerId());
        dealerProdSearchDto.setProdId(EProd.P01001.getCode());
        dealerProdSearchDto.setDistrictIdsList(districtIdsList);
        dealerProdSearchDto.setExpire(true);
        List<DealerProdDto> dealerProdDtoList = dealerProdService.getDealerProdList(dealerProdSearchDto);
        if (dealerProdDtoList == null || dealerProdDtoList.size() <= 0) {
            return ResultDtoFactory.toNack("该业务不在您的业务范围内");
        }
        SupplyDto supplyDto = new SupplyDto();
        supplyDto.setUserId(userInfoDto.getUserId());
        supplyDto.setUserName(userInfoDto.getUserName());
        supplyDto.setStoreId(storeInfDto.getStoreId());
        supplyDto.setStoreName(storeInfDto.getStoreName());
        //门头照
        PictureDto storeHeaderPicDto = pictureService.get(String.valueOf(storeInfDto.getStoreId()), EPictureType.STORE_HEADER);
        if (storeHeaderPicDto != null) {
            supplyDto.setStoreHeaderPic(AppConfigUtil.getConfig("file.addr") + StaticResourceSecurity.getSecurityURI(storeHeaderPicDto.getPicturePath()));
        }
        //店内照
        PictureDto storeInnerPicDto = pictureService.get(String.valueOf(storeInfDto.getStoreId()), EPictureType.STORE_INNER);
        if (storeInnerPicDto != null) {
            supplyDto.setStoreInnerPic(AppConfigUtil.getConfig("file.addr") + StaticResourceSecurity.getSecurityURI(storeInnerPicDto.getPicturePath()));
        }
        //营业执照
        PictureDto bizLicencePicDto = pictureService.get(String.valueOf(storeInfDto.getStoreId()), EPictureType.STORE_LICENCE);
        if (bizLicencePicDto != null) {
            supplyDto.setBizLicencePic(AppConfigUtil.getConfig("file.addr") + StaticResourceSecurity.getSecurityURI(bizLicencePicDto.getPicturePath()));
        }
        //营业执照开始日期
        supplyDto.setBizEndDate(storeInfDto.getBizEndDate());
        //身份证照
        PictureDto idCardFrontPicDto = pictureService.get(userInfoDto.getUserId(), EPictureType.CARD_FRONT);
        PictureDto idCardBackPicDto = pictureService.get(userInfoDto.getUserId(), EPictureType.CARD_BACK);
        if (idCardFrontPicDto != null && idCardBackPicDto != null) {
            supplyDto.setUploadIdCard(true);
        }
        boolean flags = (supplyDto.getUploadIdCard() && bizLicencePicDto != null && storeHeaderPicDto != null
                && storeInfDto.getBizEndDate() != null && storeInnerPicDto != null);
        if (flags) {
            supplyDto.setCompleteInfo(true);
        }
        return ResultDtoFactory.toAck("获取成功", supplyDto);
    }

    /**
     * 资料补全提交
     *
     * @return
     */
    @RequestMapping(value = "/saveSupply", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "资料补全提交")
    public ResultDto<Object> saveSupply(@RequestBody SupplyDto supplyDto) {
        DealerUserDto dealerUserDto = (DealerUserDto) SecurityContext.getCurrentUser().getParamMap().get("dealerUserDto");

        //店铺信息
        StoreInfDto storeInfDto = storeService.findByStoreId(supplyDto.getStoreId());
        if (storeInfDto == null) {
            return ResultDtoFactory.toNack("店铺不存在");
        }
        UserInfoDto userInfoDto = userService.findUserByUserId(storeInfDto.getUserId());
        if (userInfoDto == null) {
            return ResultDtoFactory.toNack("用户不存在");
        } else if (!userInfoDto.getIdentityAuth()) {
            return ResultDtoFactory.toNack("未实名认证");
        }
        //判断店铺所在的地区是否是业务配置的地区范围内
        Boolean flag = prodService.checkProdArea(EProd.P01001.getCode(), Long.valueOf(storeInfDto.getDistrictId()), storeInfDto.getIndustryMcc());
        if (!flag) {
            return ResultDtoFactory.toNack("该地区尚未开通此业务");
        }
        //该店铺可办业务列表
        List<String> districtIdsList = new ArrayList<>();
        if (StringUtils.isNotEmpty(storeInfDto.getProvinceId())) {
            districtIdsList.add(storeInfDto.getProvinceId());
        }
        if (StringUtils.isNotEmpty(storeInfDto.getCityId())) {
            districtIdsList.add(storeInfDto.getCityId());
        }
        if (StringUtils.isNotEmpty(storeInfDto.getAreaId())) {
            districtIdsList.add(storeInfDto.getAreaId());
        }
        if (StringUtils.isNotEmpty(storeInfDto.getStreetId())) {
            districtIdsList.add(storeInfDto.getStreetId());
        }
        DealerProdSearchDto dealerProdSearchDto = new DealerProdSearchDto();
        dealerProdSearchDto.setDealerId(dealerUserDto.getDealerId());
        dealerProdSearchDto.setProdId(EProd.P01001.getCode());
        dealerProdSearchDto.setDistrictIdsList(districtIdsList);
        dealerProdSearchDto.setExpire(true);
        List<DealerProdDto> dealerProdDtoList = dealerProdService.getDealerProdList(dealerProdSearchDto);
        if (dealerProdDtoList == null || dealerProdDtoList.size() <= 0) {
            return ResultDtoFactory.toNack("该业务不在您的业务范围内");
        }
        //营业执照到期日
        if (storeInfDto.getBizEndDate() == null) {
            if (supplyDto.getBizEndDate() == null) {
                return ResultDtoFactory.toNack("营业执照开始日期必传");
            } else {
                storeInfDto.setBizEndDate(supplyDto.getBizEndDate());
                storeService.updateBizEndDate(storeInfDto);
            }
        }
        //身份证匹配
        PictureDto idCardFrontPicDto = pictureService.get(userInfoDto.getUserId(), EPictureType.CARD_FRONT);
        PictureDto idCardBackPicDto = pictureService.get(userInfoDto.getUserId(), EPictureType.CARD_BACK);
        if (idCardFrontPicDto == null || idCardBackPicDto == null) {
            if (supplyDto.getIdCardFrontPicBase64() == null || supplyDto.getIdCardBackPicBase64() == null) {
                return ResultDtoFactory.toNack("身份证照必传");
            }
            try {
                OCRDto ocrResult = yituService.ocr(supplyDto.getIdCardFrontPicBase64().replace("data:image/png;base64,", StringUtils.EMPTY));
                //比对上传身份证和实名认证身份证信息是否一致
                if (!StringUtils.equals(ocrResult.getName(), userInfoDto.getUserName()) || !StringUtils.equals(ocrResult.getIdCard(), userInfoDto.getIdCardNo())) {
                    return ResultDtoFactory.toNack("上传身份证照片与实名认证身份证信息不一致");
                }
            } catch (Exception e) {
                LOGGER.error(e.getMessage());
                return ResultDtoFactory.toNack("身份证照片识别失败，请重新上传");
            }
        }
        //营业执照
        PictureDto bizLicencePicDto = pictureService.get(String.valueOf(storeInfDto.getStoreId()), EPictureType.STORE_LICENCE);
        if (bizLicencePicDto == null && supplyDto.getBizLicencePicBase64() == null) {
            return ResultDtoFactory.toNack("营业执照必传");
        }
        //门头照
        PictureDto storeHeaderPicDto = pictureService.get(String.valueOf(storeInfDto.getStoreId()), EPictureType.STORE_HEADER);
        if (storeHeaderPicDto == null && supplyDto.getStoreHeaderPicBase64() == null) {
            return ResultDtoFactory.toNack("门头照必传");
        }
        //店内照
        PictureDto storeInnerPicDto = pictureService.get(String.valueOf(storeInfDto.getStoreId()), EPictureType.STORE_INNER);
        if (storeInnerPicDto == null && supplyDto.getStoreInnerPicBase64() == null) {
            return ResultDtoFactory.toNack("店内照必传");
        }
        //上传照片
        if (idCardFrontPicDto == null || idCardBackPicDto == null) {
            //身份证上传
            String idCardPic1Path = null;
            String idCardPic2Path = null;

            IDCardDto idCardDto = new IDCardDto();
            idCardDto.setUserId(userInfoDto.getUserId());
            idCardDto.setUserName(userInfoDto.getUserName());
            idCardDto.setIdCardNo(userInfoDto.getIdCardNo());
            try {
                File idCardPic1File = ImageUtils.GenerateImageByBase64(supplyDto.getIdCardFrontPicBase64());
                idCardPic1Path = fileStoreService.upload(EFileType.USER_INFO_IMG, idCardPic1File, idCardPic1File.getName());

                com.xinyunlian.jinfu.common.yitu.dto.resp.OCRDto ocrDto = yituService.ocr(supplyDto.getIdCardFrontPicBase64(), 1);
                idCardDto.setNationAddress(ocrDto.getAddress());
                idCardDto.setSex(ocrDto.getGender());
                idCardDto.setNation(ocrDto.getNation());
                idCardDto.setBirthDate(DateHelper.getDate(ocrDto.getBirthday(), "yyyy.MM.dd"));
                idCardDto.setCardFrontFilePath(idCardPic1Path);
            } catch (IOException e) {
                LOGGER.error("身份证正面图片上传失败", e);
                return ResultDtoFactory.toNack("实名认证失败");
            } catch (Exception e) {
                LOGGER.error("读取身份证正面ocr错误", e);
                return ResultDtoFactory.toNack("实名认证失败");
            }
            try {
                File idCardPic2File = ImageUtils.GenerateImageByBase64(supplyDto.getIdCardBackPicBase64());
                idCardPic2Path = fileStoreService.upload(EFileType.USER_INFO_IMG, idCardPic2File, idCardPic2File.getName());

                com.xinyunlian.jinfu.common.yitu.dto.resp.OCRDto ocrDto = yituService.ocr(supplyDto.getIdCardBackPicBase64(), 2);
                idCardDto.setIdAuthority(ocrDto.getAgency());
                idCardDto.setIdExpiredBegin(DateHelper.getDate(ocrDto.getValidDateBegin(), DateHelper.SIMPLE_DATE_YMD));
                idCardDto.setIdExpiredEnd(DateHelper.getDate(ocrDto.getValidDateEnd(), DateHelper.SIMPLE_DATE_YMD));
                idCardDto.setCardBackFilePath(idCardPic2Path);
            } catch (IOException e) {
                LOGGER.error("身份证反面图片上传失败", e);
                return ResultDtoFactory.toNack("实名认证失败");
            } catch (Exception e) {
                LOGGER.error("读取身份证反面ocr错误", e);
                return ResultDtoFactory.toNack("实名认证失败");
            }
            userService.saveIDCard(idCardDto);
        }
        if (bizLicencePicDto == null) {
            try {
                PictureDto pictureDto = new PictureDto();
                File bizLicencePicFile = ImageUtils.GenerateImageByBase64(supplyDto.getBizLicencePicBase64());
                String picPath =
                        fileStoreService.upload(EFileType.STORE_INFO_IMG, bizLicencePicFile, bizLicencePicFile.getName());
                pictureDto.setPictureType(EPictureType.STORE_LICENCE);
                pictureDto.setPicturePath(picPath);
                pictureDto.setParentId(storeInfDto.getStoreId() + StringUtils.EMPTY);
                pictureService.savePicture(pictureDto);
            } catch (IOException e) {
                LOGGER.error("营业执照上传失败", e);
                return ResultDtoFactory.toNack("保存失败");
            }
        }
        if (storeHeaderPicDto == null) {
            try {
                PictureDto pictureDto = new PictureDto();
                File storeHeaderPicFile = ImageUtils.GenerateImageByBase64(supplyDto.getStoreHeaderPicBase64());
                String picPath =
                        fileStoreService.upload(EFileType.STORE_INFO_IMG, storeHeaderPicFile, storeHeaderPicFile.getName());
                pictureDto.setPictureType(EPictureType.STORE_HEADER);
                pictureDto.setPicturePath(picPath);
                pictureDto.setParentId(storeInfDto.getStoreId() + StringUtils.EMPTY);
                pictureService.savePicture(pictureDto);
            } catch (IOException e) {
                LOGGER.error("门头照上传失败", e);
                return ResultDtoFactory.toNack("保存失败");
            }
        }
        if (storeInnerPicDto == null) {
            try {
                PictureDto pictureDto = new PictureDto();
                File storeInnerPicFile = ImageUtils.GenerateImageByBase64(supplyDto.getStoreInnerPicBase64());
                String picPath =
                        fileStoreService.upload(EFileType.STORE_INFO_IMG, storeInnerPicFile, storeInnerPicFile.getName());
                pictureDto.setPictureType(EPictureType.STORE_INNER);
                pictureDto.setPicturePath(picPath);
                pictureDto.setParentId(storeInfDto.getStoreId() + StringUtils.EMPTY);
                pictureService.savePicture(pictureDto);
            } catch (IOException e) {
                LOGGER.error("门头照上传失败", e);
                return ResultDtoFactory.toNack("保存失败");
            }
        }
        //身份证
        PictureDto idCardFrontPic = pictureService.get(userInfoDto.getUserId(), EPictureType.CARD_FRONT);
        PictureDto idCardBackPic = pictureService.get(userInfoDto.getUserId(), EPictureType.CARD_BACK);
        if (idCardFrontPic == null || idCardBackPic == null) {
            return ResultDtoFactory.toNack("请先上传身份证照");
        }
        //营业执照
        PictureDto bizLicensePicDto = pictureService.get(String.valueOf(storeInfDto.getStoreId()), EPictureType.STORE_LICENCE);
        if (bizLicensePicDto == null) {
            return ResultDtoFactory.toNack("请先上传营业执照");
        }
        //门头照
        PictureDto bizPlaceSnapshot1PicDto = pictureService.get(String.valueOf(storeInfDto.getStoreId()), EPictureType.STORE_HEADER);
        if (bizPlaceSnapshot1PicDto == null) {
            return ResultDtoFactory.toNack("请先上传门头照");
        }
        //店内照
        PictureDto bizPlaceSnapshot2PicDto = pictureService.get(String.valueOf(storeInfDto.getStoreId()), EPictureType.STORE_INNER);
        if (bizPlaceSnapshot2PicDto == null) {
            return ResultDtoFactory.toNack("请先上传店内照");
        }
        return ResultDtoFactory.toAck("保存成功");
    }

    /**
     * 商户进件绑码
     *
     * @return
     */
    @RequestMapping(value = "/saveSupplyMember", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "商户进件绑码")
    public ResultDto<Object> saveSupplyMember(@RequestBody BindMemberDto bindMemberDto) {
        DealerUserDto dealerUserDto = (DealerUserDto) SecurityContext.getCurrentUser().getParamMap().get("dealerUserDto");

        if (StringUtils.isEmpty(bindMemberDto.getQrCodeNo()) || StringUtils.isEmpty(bindMemberDto.getQrCodeUrl())) {
            return ResultDtoFactory.toNack("qrCodeNo和qrCodeUrl不能为空");
        }
        YMMemberDto yMMemberDto = yMMemberService.getMemberByQrCodeNo(bindMemberDto.getQrCodeNo());
        if (yMMemberDto != null) {
            return ResultDtoFactory.toNack("该云码已绑定");
        }
        //店铺信息
        StoreInfDto storeInfDto = storeService.findByStoreId(bindMemberDto.getStoreId());
        if (storeInfDto == null) {
            return ResultDtoFactory.toNack("店铺不存在");
        }
        IndustryDto industryDto = industryService.getByMcc(storeInfDto.getIndustryMcc());
        if (industryDto == null) {
            return ResultDtoFactory.toNack("行业不存在");
        }
        UserInfoDto userInfoDto = userService.findUserByUserId(storeInfDto.getUserId());
        if (userInfoDto == null) {
            return ResultDtoFactory.toNack("用户不存在");
        } else if (!userInfoDto.getIdentityAuth()) {
            return ResultDtoFactory.toNack("未实名认证");
        }
        //判断店铺所在的地区是否是业务配置的地区范围内
        Boolean flag = prodService.checkProdArea(EProd.P01001.getCode(), Long.valueOf(storeInfDto.getDistrictId()), storeInfDto.getIndustryMcc());
        if (!flag) {
            return ResultDtoFactory.toNack("该地区尚未开通此业务");
        }
        //该店铺可办业务列表
        List<String> districtIdsList = new ArrayList<>();
        if (StringUtils.isNotEmpty(storeInfDto.getProvinceId())) {
            districtIdsList.add(storeInfDto.getProvinceId());
        }
        if (StringUtils.isNotEmpty(storeInfDto.getCityId())) {
            districtIdsList.add(storeInfDto.getCityId());
        }
        if (StringUtils.isNotEmpty(storeInfDto.getAreaId())) {
            districtIdsList.add(storeInfDto.getAreaId());
        }
        if (StringUtils.isNotEmpty(storeInfDto.getStreetId())) {
            districtIdsList.add(storeInfDto.getStreetId());
        }
        DealerProdSearchDto dealerProdSearchDto = new DealerProdSearchDto();
        dealerProdSearchDto.setDealerId(dealerUserDto.getDealerId());
        dealerProdSearchDto.setProdId(EProd.P01001.getCode());
        dealerProdSearchDto.setDistrictIdsList(districtIdsList);
        dealerProdSearchDto.setExpire(true);
        List<DealerProdDto> dealerProdDtoList = dealerProdService.getDealerProdList(dealerProdSearchDto);
        if (dealerProdDtoList == null || dealerProdDtoList.size() <= 0) {
            return ResultDtoFactory.toNack("该业务不在您的业务范围内");
        }
        //结算卡判断
        BankCardDto bankCardDto = new BankCardDto();
        if (bindMemberDto.getBankCardType() == null || bindMemberDto.getBankCardType() == 1) {
            bankCardDto = bankService.getBankCard(bindMemberDto.getBankCardId());
            if (bankCardDto == null) {
                return ResultDtoFactory.toNack("银行卡不存在");
            }
            bankCardDto.setSubbranchName(bankCardDto.getBankName());
            bankCardDto.setBankCardName(userInfoDto.getUserName());
        } else if (bindMemberDto.getBankCardType() == 2) {
            CorpBankDto corpBankDto = corpBankService.getCorpBankById(bindMemberDto.getBankCardId());
            if (corpBankDto == null) {
                return ResultDtoFactory.toNack("对公账号卡不存在");
            }
            bankCardDto.setBankName(corpBankDto.getOpeningBank());
            bankCardDto.setSubbranchName(corpBankDto.getBankBranch());
            bankCardDto.setBankCardNo(corpBankDto.getAccount());
            bankCardDto.setMobileNo(userInfoDto.getMobile());
            bankCardDto.setBankCardId(corpBankDto.getId());
            bankCardDto.setBankCode(corpBankDto.getBankShortName());
            bankCardDto.setBankCardName(corpBankDto.getAcctName());
        }
        BankMappingDto bankMappingDto = bankMappingService.getBankMappingByShortName(bankCardDto.getBankCode());
        if (bankMappingDto == null) {
            return ResultDtoFactory.toNack("该银行不支持");
        }
        //结算卡图片判断
        EPictureType bankPictureType = EPictureType.BANK_CARD_FRONT;
        if (bindMemberDto.getBankCardType() != null && bindMemberDto.getBankCardType() == 2) {//对公账号
            bankPictureType = EPictureType.ACCOUNT_LICENCE;
        }
        //结算卡照
        PictureDto bankCardPic = pictureService.get(String.valueOf(bindMemberDto.getBankCardId()), bankPictureType);
        if (bankCardPic == null && bindMemberDto.getBankCardPicBase64() == null) {
            return ResultDtoFactory.toNack("结算卡照必传");
        }
        //结算卡照
        if (bankCardPic == null) {
            try {
                PictureDto pictureDto = new PictureDto();
                File bankCardPicFile = ImageUtils.GenerateImageByBase64(bindMemberDto.getBankCardPicBase64());
                String picPath =
                        fileStoreService.upload(EFileType.BANK_CARD_IMG, bankCardPicFile, bankCardPicFile.getName());
                pictureDto.setPictureType(bankPictureType);
                pictureDto.setPicturePath(picPath);
                pictureDto.setParentId(bindMemberDto.getBankCardId() + StringUtils.EMPTY);
                pictureService.savePicture(pictureDto);
            } catch (IOException e) {
                LOGGER.error("结算卡照上传失败", e);
                return ResultDtoFactory.toNack("保存失败");
            }
        }
        //身份证
        PictureDto idCardFrontPic = pictureService.get(userInfoDto.getUserId(), EPictureType.CARD_FRONT);
        PictureDto idCardBackPic = pictureService.get(userInfoDto.getUserId(), EPictureType.CARD_BACK);
        if (idCardFrontPic == null || idCardBackPic == null) {
            return ResultDtoFactory.toNack("请先上传身份证照");
        }
        //门头照
        PictureDto storeHeaderPicDto = pictureService.get(String.valueOf(storeInfDto.getStoreId()), EPictureType.STORE_HEADER);
        if (storeHeaderPicDto == null) {
            return ResultDtoFactory.toNack("门头照必传");
        }
        //门头照
        PictureDto storeInnerPicDto = pictureService.get(String.valueOf(storeInfDto.getStoreId()), EPictureType.STORE_INNER);
        if (storeInnerPicDto == null) {
            return ResultDtoFactory.toNack("店内照必传");
        }
        //营业执照
        PictureDto bizLicencePicDto = pictureService.get(String.valueOf(storeInfDto.getStoreId()), EPictureType.STORE_LICENCE);
        if (bizLicencePicDto == null) {
            return ResultDtoFactory.toNack("营业执照必传");
        }
        //结算卡照
        PictureDto bankCardPicDto = pictureService.get(String.valueOf(bindMemberDto.getBankCardId()), bankPictureType);
        if (bankCardPicDto == null) {
            return ResultDtoFactory.toNack("结算卡照必传");
        }
        //营业执照到期日
        if (storeInfDto.getBizEndDate() == null) {
            return ResultDtoFactory.toNack("营业执照到期日必传");
        }
        //店铺进件
        List<YmBizDto> bizList = ymBizService.findAll();
        SysAreaInfDto provinceArea = sysAreaInfService.getSysAreaInfById(Long.valueOf(storeInfDto.getProvinceId()));
        SysAreaInfDto cityArea = sysAreaInfService.getSysAreaInfById(Long.valueOf(storeInfDto.getCityId()));
        SysAreaInfDto areaArea = sysAreaInfService.getSysAreaInfById(Long.valueOf(storeInfDto.getAreaId()));
        CloudCodeMerchantDto cloudCodeMerchantDto = new CloudCodeMerchantDto();
        cloudCodeMerchantDto.setPartnerId(AppConfigUtil.getConfig("cashier.partnerid"));//partnerid
        cloudCodeMerchantDto.setOutMerchantId(String.valueOf(storeInfDto.getStoreId()));//storeId
        //cloudCodeMerchantDto.setEmail();//空
        cloudCodeMerchantDto.setMerchantName(storeInfDto.getStoreName());//店铺名
        //cloudCodeMerchantDto.setMerchantShortName();//不传
        cloudCodeMerchantDto.setAddress(storeInfDto.getProvince() + storeInfDto.getCity());
        cloudCodeMerchantDto.setLegalPerson(userInfoDto.getUserName());//姓名
        cloudCodeMerchantDto.setLegalPersonID(userInfoDto.getIdCardNo());//身份证
        cloudCodeMerchantDto.setFirstName(userInfoDto.getUserName());//姓名
        cloudCodeMerchantDto.setCellPhone(userInfoDto.getMobile());//手机
        cloudCodeMerchantDto.setBankCode(bankMappingDto.getBankCnapsCode());
        cloudCodeMerchantDto.setBankName(bankCardDto.getBankName());
        cloudCodeMerchantDto.setSubbranchName(bankCardDto.getSubbranchName());//支行名 空
        cloudCodeMerchantDto.setBankProvinceCode(provinceArea.getGbCode());//省 code
        cloudCodeMerchantDto.setBankProvince(storeInfDto.getProvince());//
        cloudCodeMerchantDto.setBankCityCode(cityArea.getGbCode());
        cloudCodeMerchantDto.setBankCity(storeInfDto.getCity());
        cloudCodeMerchantDto.setCardName(bankCardDto.getBankCardName());//银行卡姓名
        cloudCodeMerchantDto.setIdNo(userInfoDto.getIdCardNo());//身份证
        cloudCodeMerchantDto.setCardNo(bankCardDto.getBankCardNo());
        cloudCodeMerchantDto.setBankCardPhone(bankCardDto.getMobileNo());//预留手机号
        for (YmBizDto ymBizDto : bizList) {
            if (EBizCode.ALLIPAY.equals(ymBizDto.getCode())) {
                cloudCodeMerchantDto.setAlipayRate(ymBizDto.getRate());
            } else if (EBizCode.WECHAT.equals(ymBizDto.getCode())) {
                cloudCodeMerchantDto.setWechatRate(ymBizDto.getRate());
            } else if (EBizCode.JDPAY.equals(ymBizDto.getCode())) {
                cloudCodeMerchantDto.setJdpayRate(ymBizDto.getRate());
            } else if (EBizCode.BESTPAY.equals(ymBizDto.getCode())) {
                cloudCodeMerchantDto.setBestpayRate(ymBizDto.getRate());
            } else if (EBizCode.QQPAY.equals(ymBizDto.getCode())) {
                cloudCodeMerchantDto.setQqpayRate(ymBizDto.getRate());
            } else if (EBizCode.BAIDUPAY.equals(ymBizDto.getCode())) {
                cloudCodeMerchantDto.setBaidupayRate(ymBizDto.getRate());
            }
        }
        cloudCodeMerchantDto.setSettleDays(Integer.valueOf(bizList.get(0).getSettlement().getCode()));//t+1=1 d+0=0
        cloudCodeMerchantDto.setBizLicenseCode(storeInfDto.getBizLicence());
        cloudCodeMerchantDto.setLegalPersonIDImgFront(AppConfigUtil.getConfig("file.addr") + StaticResourceSecurity.getSecurityURI(idCardFrontPic.getPicturePath()));//身份证
        cloudCodeMerchantDto.setLegalPersonIDImgBack(AppConfigUtil.getConfig("file.addr") + StaticResourceSecurity.getSecurityURI(idCardBackPic.getPicturePath()));
        cloudCodeMerchantDto.setBizLicenseImage(AppConfigUtil.getConfig("file.addr") + StaticResourceSecurity.getSecurityURI(bizLicencePicDto.getPicturePath()));//营业执照
        cloudCodeMerchantDto.setBizPlaceSnapshot1Image(AppConfigUtil.getConfig("file.addr") + StaticResourceSecurity.getSecurityURI(storeHeaderPicDto.getPicturePath()));//门头照
        cloudCodeMerchantDto.setBizPlaceSnapshot2Image(AppConfigUtil.getConfig("file.addr") + StaticResourceSecurity.getSecurityURI(storeInnerPicDto.getPicturePath()));//店内照
        cloudCodeMerchantDto.setHoldCardImage(AppConfigUtil.getConfig("file.addr") + StaticResourceSecurity.getSecurityURI(idCardFrontPic.getPicturePath()));//手持身份证
        cloudCodeMerchantDto.setBankCardImage(AppConfigUtil.getConfig("file.addr") + StaticResourceSecurity.getSecurityURI(bankCardPicDto.getPicturePath()));//银行卡照
        cloudCodeMerchantDto.setLicenseDate(DateHelper.formatDate(storeInfDto.getBizEndDate()));//营业执照到期日
        cloudCodeMerchantDto.setAreaCode(areaArea.getGbCode());
        cloudCodeMerchantDto.setProvinceCode(provinceArea.getGbCode());
        cloudCodeMerchantDto.setCityCode(cityArea.getGbCode());
        cloudCodeMerchantDto.setProvince(storeInfDto.getProvince());
        cloudCodeMerchantDto.setCity(storeInfDto.getCity());
        cloudCodeMerchantDto.setArea(storeInfDto.getArea());
//        cloudCodeMerchantDto.setBizPlaceSnapshot2Image(AppConfigUtil.getConfig("file.addr") + StaticResourceSecurity.getSecurityURI(ymMemberSignDto.getFirstPageFilePath()));//合同
//        cloudCodeMerchantDto.setBizPlaceSnapshot3Image(AppConfigUtil.getConfig("file.addr") + StaticResourceSecurity.getSecurityURI(ymMemberSignDto.getLastPageFilePath()));//合同
        //cloudCodeMerchantDto.setOtherCertImage();
        cloudCodeMerchantDto.setAppId(AppConfigUtil.getConfig("cashier.appid"));
        cloudCodeMerchantDto.setBusinessCatagory1(industryDto.getIndType());
        cloudCodeMerchantDto.setBusinessCatagory2(industryDto.getIndDesc());
        cloudCodeMerchantDto.setMcc(industryDto.getMcc());
        if (bindMemberDto.getBankCardType() != null && bindMemberDto.getBankCardType() == 2) {
            cloudCodeMerchantDto.setPublickAccount(true);
        }
        String memberNo = cloudCodeService.saveMerchant(cloudCodeMerchantDto);
        //云码店铺添加处理
        YMMemberDto dto = new YMMemberDto();
        dto.setStoreId(storeInfDto.getStoreId());
        dto.setUserId(storeInfDto.getUserId());
        dto.setMemberNo(memberNo);
        dto.setQrcodeNo(bindMemberDto.getQrCodeNo());
        dto.setQrcodeUrl(bindMemberDto.getQrCodeUrl());
        if (bindMemberDto.getBankCardType() != null && bindMemberDto.getBankCardType() == 2) {//对公账号
            dto.setCorpBankId(bankCardDto.getBankCardId());
        } else {
            dto.setBankCardId(bankCardDto.getBankCardId());
        }
        dto.setSettlement(bizList.get(0).getSettlement());
        dto.setActivate(false);
        yMMemberService.addMember(dto);
        //更新码库状态
        YmDepotDto ymDepotDto = new YmDepotDto();
        ymDepotDto.setQrCodeNo(dto.getQrcodeNo());
        ymDepotDto.setStatus(EDepotStatus.BIND_USE);
        ymDepotDto.setReceiveStatus(EDepotReceiveStatus.RECEIVE);
        ymDepotService.updateStatusAndReceiveStatus(ymDepotDto);
        //插入分销员操作日志
        dealerUserLogService.createDealerUserLog(dealerUserDto, bindMemberDto.getLogLng(), bindMemberDto.getLogLat(), bindMemberDto.getLogAddress(),
                storeInfDto.getUserId(), storeInfDto.getStoreId() + StringUtils.EMPTY, "业务办理:开通云码", EDealerUserLogType.ORDER);
        return ResultDtoFactory.toAck("资料提交中，请稍等片刻");
    }

}
