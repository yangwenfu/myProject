package com.xinyunlian.jinfu.store.controller;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.xinyunlian.jinfu.api.service.ApiBaiduService;
import com.xinyunlian.jinfu.common.converter.ConverterService;
import com.xinyunlian.jinfu.common.dto.ResultDto;
import com.xinyunlian.jinfu.common.dto.ResultDtoFactory;
import com.xinyunlian.jinfu.common.fileStore.FileStoreService;
import com.xinyunlian.jinfu.common.fileStore.enums.EFileType;
import com.xinyunlian.jinfu.common.security.SecurityContext;
import com.xinyunlian.jinfu.common.util.DateHelper;
import com.xinyunlian.jinfu.common.util.ImageUtils;
import com.xinyunlian.jinfu.dealer.dto.*;
import com.xinyunlian.jinfu.dealer.enums.EDealerUserLogType;
import com.xinyunlian.jinfu.dealer.service.*;
import com.xinyunlian.jinfu.industry.dto.IndustryDto;
import com.xinyunlian.jinfu.industry.service.IndustryService;
import com.xinyunlian.jinfu.picture.dto.PictureDto;
import com.xinyunlian.jinfu.picture.enums.EPictureType;
import com.xinyunlian.jinfu.picture.service.PictureService;
import com.xinyunlian.jinfu.store.dto.StoreInfDto;
import com.xinyunlian.jinfu.store.dto.StoreWhiteListDto;
import com.xinyunlian.jinfu.store.dto.StoreWhiteListSearchDto;
import com.xinyunlian.jinfu.store.dto.req.StoreDto;
import com.xinyunlian.jinfu.store.dto.req.StoreInfoSearchDto;
import com.xinyunlian.jinfu.store.dto.req.StoreSearchDto;
import com.xinyunlian.jinfu.store.dto.resp.StoreInfoDto;
import com.xinyunlian.jinfu.store.enums.ELocationSource;
import com.xinyunlian.jinfu.store.enums.EStoreWhiteListStatus;
import com.xinyunlian.jinfu.store.service.StoreService;
import com.xinyunlian.jinfu.store.service.StoreWhiteListService;
import com.xinyunlian.jinfu.user.dto.DealerUserDto;
import com.xinyunlian.jinfu.user.dto.UserInfoDto;
import com.xinyunlian.jinfu.user.enums.ESource;
import com.xinyunlian.jinfu.user.service.DealerUserService;
import com.xinyunlian.jinfu.user.service.UserService;
import com.xinyunlian.jinfu.yunma.dto.YMMemberDto;
import com.xinyunlian.jinfu.yunma.service.YMMemberService;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * Created by menglei on 2016年08月30日.
 */
@Controller
@RequestMapping(value = "store")
public class StoreController {

    private static final Logger LOGGER = LoggerFactory.getLogger(StoreController.class);

    @Autowired
    private StoreService storeService;
    @Autowired
    private FileStoreService fileStoreService;
    @Autowired
    private DealerUserStoreService dealerUserStoreService;
    @Autowired
    private DealerProdService dealerProdService;
    @Autowired
    private DealerUserLogService dealerUserLogService;
    @Autowired
    private UserService userService;
    @Autowired
    private PictureService pictureService;
    @Autowired
    private DealerService dealerService;
    @Autowired
    private ApiBaiduService apiBaiduService;
    @Autowired
    private YMMemberService yMMemberService;
    @Autowired
    private IndustryService industryService;
    @Autowired
    private DealerUserService dealerUserService;
    @Autowired
    private StoreWhiteListService storeWhiteListService;

    /**
     * 添加店铺
     *
     * @param storeDto
     * @return
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    public ResultDto<Object> save(@RequestBody @Valid StoreDto storeDto, BindingResult result) {
        if (result.hasErrors()) {
            return ResultDtoFactory.toNack(result.getFieldError().getDefaultMessage());
        }
        DealerUserDto dealerUserDto = (DealerUserDto) SecurityContext.getCurrentUser().getParamMap().get("dealerUserDto");
        //烟草证号判断
        if (!storeDto.getTobaccoCertificateNo().matches("[0-9]{12}")) {
            return ResultDtoFactory.toNack("请输入正确的烟草证号");
        }
        StoreInfDto storeInfDto = ConverterService.convert(storeDto, StoreInfDto.class);
        storeInfDto.setSource(ESource.DEALER);
        //有坐标的直接插入坐标
        if (StringUtils.isNotEmpty(storeDto.getLogLat()) && StringUtils.isNotEmpty(storeDto.getLogLng())) {
            storeInfDto.setLat(storeDto.getLogLat());
            storeInfDto.setLng(storeDto.getLogLng());
            storeInfDto.setLocationSource(ELocationSource.LOCATION);
        }
        StoreInfDto storeInf = storeService.saveStore(storeInfDto);
        apiBaiduService.updatePoint(storeInf);
        //图片处理
        PictureDto pictureDto = new PictureDto();
        pictureDto.setParentId(storeInf.getStoreId() + StringUtils.EMPTY);
        if (storeDto.getTobaccoCertificatePicBase64() != null) {
            try {
                File tobaccoCertificateFile = ImageUtils.GenerateImageByBase64(storeDto.getTobaccoCertificatePicBase64());
                String picPath =
                        fileStoreService.upload(EFileType.STORE_INFO_IMG, tobaccoCertificateFile, tobaccoCertificateFile.getName());
                pictureDto.setPictureType(EPictureType.STORE_TOBACCO);
                pictureDto.setPicturePath(picPath);
                pictureService.savePicture(pictureDto);
            } catch (IOException e) {
                LOGGER.error("烟草许可证图片上传失败", e);
                return ResultDtoFactory.toNack("添加店铺失败");
            }
        }
        if (storeDto.getBizLicencePicBase64() != null) {
            try {
                File bizLicencePicFile = ImageUtils.GenerateImageByBase64(storeDto.getBizLicencePicBase64());
                String picPath =
                        fileStoreService.upload(EFileType.STORE_INFO_IMG, bizLicencePicFile, bizLicencePicFile.getName());
                pictureDto.setPictureType(EPictureType.STORE_LICENCE);
                pictureDto.setPicturePath(picPath);
                pictureService.savePicture(pictureDto);
            } catch (IOException e) {
                LOGGER.error("营业执照图片上传失败", e);
                return ResultDtoFactory.toNack("添加店铺失败");
            }
        }
        //插入分销员店铺授权关系（如分销员录入的店铺不在该分销授权范围则该店铺不属于该分销）
        List<String> districtIdsList = new ArrayList<>();
        if (StringUtils.isNotEmpty(storeInf.getProvinceId())) {
            districtIdsList.add(storeInf.getProvinceId());
        }
        if (StringUtils.isNotEmpty(storeInf.getCityId())) {
            districtIdsList.add(storeInf.getCityId());
        }
        if (StringUtils.isNotEmpty(storeInf.getAreaId())) {
            districtIdsList.add(storeInf.getAreaId());
        }
        if (StringUtils.isNotEmpty(storeInf.getStreetId())) {
            districtIdsList.add(storeInf.getStreetId());
        }
        DealerProdSearchDto dealerProdSearchDto = new DealerProdSearchDto();
        dealerProdSearchDto.setDistrictIdsList(districtIdsList);
        dealerProdSearchDto.setDealerId(dealerUserDto.getDealerId());
        List<DealerProdDto> dealerProdDtoList = dealerProdService.getDealerProdList(dealerProdSearchDto);
        if (dealerProdDtoList != null && dealerProdDtoList.size() > 0) {
            DealerUserStoreDto dealerUserStoreDto = new DealerUserStoreDto();
            dealerUserStoreDto.setDealerId(dealerUserDto.getDealerId());
            dealerUserStoreDto.setStoreId(storeInf.getStoreId() + StringUtils.EMPTY);
            dealerUserStoreDto.setUserId(dealerUserDto.getUserId());
            dealerUserStoreService.createDealer(dealerUserStoreDto);
        }
        //插入分销员操作日志
        dealerUserLogService.createDealerUserLog(dealerUserDto, storeDto.getLogLng(), storeDto.getLogLat(), storeDto.getLogAddress(),
                storeInf.getUserId(), storeInf.getStoreId() + StringUtils.EMPTY, "店铺添加", EDealerUserLogType.ADDSTORE);
        return ResultDtoFactory.toAck("添加成功");
    }

    /**
     * 添加店铺
     *
     * @param storeDto
     * @return
     */
    @RequestMapping(value = "/v2/save", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "行业店铺添加")
    public ResultDto<Object> v2Save(@RequestBody @Valid StoreDto storeDto, BindingResult result) {
        if (result.hasErrors()) {
            return ResultDtoFactory.toNack(result.getFieldError().getDefaultMessage());
        }
        DealerUserDto dealerUserDto = (DealerUserDto) SecurityContext.getCurrentUser().getParamMap().get("dealerUserDto");
        //判断商户是否有店铺，新增店铺行业是否和原店铺行业相同
        List<StoreInfDto> storeList = storeService.findByUserId(storeDto.getUserId());
        if (!CollectionUtils.isEmpty(storeList)) {
            if (!storeList.get(0).getIndustryMcc().equals(storeDto.getIndustryMcc())) {
                return ResultDtoFactory.toNack("行业必须和已有店铺相同");
            }
        }
        StoreInfDto storeInfDto = ConverterService.convert(storeDto, StoreInfDto.class);
        storeInfDto.setSource(ESource.DEALER);
        //有坐标的直接插入坐标
        if (StringUtils.isNotEmpty(storeDto.getLogLat()) && StringUtils.isNotEmpty(storeDto.getLogLng())) {
            storeInfDto.setLat(storeDto.getLogLat());
            storeInfDto.setLng(storeDto.getLogLng());
            storeInfDto.setLocationSource(ELocationSource.LOCATION);
        }
        StoreInfDto storeInf = storeService.saveSupportAll(storeInfDto);
        apiBaiduService.updatePoint(storeInf);
        //图片处理
        PictureDto pictureDto = new PictureDto();
        pictureDto.setParentId(storeInf.getStoreId() + StringUtils.EMPTY);
        if (storeDto.getLicencePicBase64() != null) {
            try {
                File licenceFile = ImageUtils.GenerateImageByBase64(storeDto.getLicencePicBase64());
                String picPath = fileStoreService.upload(EFileType.STORE_INFO_IMG, licenceFile, licenceFile.getName());
                EPictureType type = EPictureType.STORE_TOBACCO;
                if (!"5227".equals(storeDto.getIndustryMcc())) {
                    type = EPictureType.STORE_NO_TOBACCO_LICENCE;
                }
                pictureDto.setPictureType(type);
                pictureDto.setPicturePath(picPath);
                pictureService.savePicture(pictureDto);
            } catch (IOException e) {
                LOGGER.error("许可证图片上传失败", e);
                return ResultDtoFactory.toNack("添加店铺失败");
            }
        }
        if (storeDto.getBizLicencePicBase64() != null) {
            try {
                File bizLicencePicFile = ImageUtils.GenerateImageByBase64(storeDto.getBizLicencePicBase64());
                String picPath = fileStoreService.upload(EFileType.STORE_INFO_IMG, bizLicencePicFile, bizLicencePicFile.getName());
                pictureDto.setPictureType(EPictureType.STORE_LICENCE);
                pictureDto.setPicturePath(picPath);
                pictureService.savePicture(pictureDto);
            } catch (IOException e) {
                LOGGER.error("营业执照图片上传失败", e);
                return ResultDtoFactory.toNack("添加店铺失败");
            }
        }
        //插入分销员店铺授权关系（如分销员录入的店铺不在该分销授权范围则该店铺不属于该分销）
        List<String> districtIdsList = new ArrayList<>();
        if (StringUtils.isNotEmpty(storeInf.getProvinceId())) {
            districtIdsList.add(storeInf.getProvinceId());
        }
        if (StringUtils.isNotEmpty(storeInf.getCityId())) {
            districtIdsList.add(storeInf.getCityId());
        }
        if (StringUtils.isNotEmpty(storeInf.getAreaId())) {
            districtIdsList.add(storeInf.getAreaId());
        }
        if (StringUtils.isNotEmpty(storeInf.getStreetId())) {
            districtIdsList.add(storeInf.getStreetId());
        }
        DealerProdSearchDto dealerProdSearchDto = new DealerProdSearchDto();
        dealerProdSearchDto.setDistrictIdsList(districtIdsList);
        dealerProdSearchDto.setDealerId(dealerUserDto.getDealerId());
        List<DealerProdDto> dealerProdDtoList = dealerProdService.getDealerProdList(dealerProdSearchDto);
        if (dealerProdDtoList != null && dealerProdDtoList.size() > 0) {
            DealerUserStoreDto dealerUserStoreDto = new DealerUserStoreDto();
            dealerUserStoreDto.setDealerId(dealerUserDto.getDealerId());
            dealerUserStoreDto.setStoreId(storeInf.getStoreId() + StringUtils.EMPTY);
            dealerUserStoreDto.setUserId(dealerUserDto.getUserId());
            dealerUserStoreService.createDealer(dealerUserStoreDto);
        }
        //插入分销员操作日志
        dealerUserLogService.createDealerUserLog(dealerUserDto, storeDto.getLogLng(), storeDto.getLogLat(), storeDto.getLogAddress(),
                storeInf.getUserId(), storeInf.getStoreId() + StringUtils.EMPTY, "店铺添加", EDealerUserLogType.ADDSTORE);
        return ResultDtoFactory.toAck("添加成功");
    }

    /**
     * 根据店铺id查询店铺
     *
     * @param storeId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getByStoreId", method = RequestMethod.GET)
    public ResultDto<StoreInfoDto> getStoreByStoreId(@RequestParam String storeId) {
        DealerUserDto dealerUserDto = (DealerUserDto) SecurityContext.getCurrentUser().getParamMap().get("dealerUserDto");

        DealerDto dealerDto = dealerService.getDealerById(dealerUserDto.getDealerId());
        if (dealerDto == null) {
            return ResultDtoFactory.toNack("分销商不存在");
        }

        StoreInfDto storeInfDto = storeService.findByStoreId(Long.valueOf(storeId));
        if (storeInfDto == null) {
            return ResultDtoFactory.toNack("该店铺不存在");
        }
        //查询已授权业务列表
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
        dealerProdSearchDto.setExpire(true);//需判断业务过期时间
        dealerProdSearchDto.setDistrictIdsList(districtIdsList);
        List<DealerProdDto> dealerProdList = dealerProdService.getDealerProdList(dealerProdSearchDto);
        if (dealerProdList == null || dealerProdList.size() <= 0) {
            //没有查到授权地区直接返回空
            return ResultDtoFactory.toNack("该店铺不在您的业务范围内");
        }
        StoreInfoDto storeInfoDto = ConverterService.convert(storeInfDto, StoreInfoDto.class);
        UserInfoDto userInfoDto = userService.findUserByUserId(storeInfDto.getUserId());
        storeInfoDto.setUserInfoDto(userInfoDto);
        //判断该店铺是否已绑定云码 null=未绑定
        YMMemberDto yMMemberDto = yMMemberService.getMemberByStoreId(storeInfoDto.getStoreId());
        storeInfoDto.setQrCodeUrl(null);
        if (yMMemberDto != null) {
            storeInfoDto.setQrCodeUrl(yMMemberDto.getQrcodeUrl());
        }

        //遍历行业名称
        List<IndustryDto> industryList = industryService.getAllIndustry();
        Map<String, String> industryMap = new HashMap<>();
        for (IndustryDto industryDto : industryList) {
            industryMap.put(industryDto.getMcc(), industryDto.getIndName());
        }
        storeInfoDto.setIndustryName(industryMap.get(storeInfoDto.getIndustryMcc()));
        return ResultDtoFactory.toAck("获取成功", storeInfoDto);
    }

    /**
     * 根据商户id查询店铺列表
     *
     * @param userId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/listByUserId", method = RequestMethod.GET)
    public ResultDto<List<StoreInfDto>> getStoreListByUserId(@RequestParam String userId) {
        List<StoreInfDto> list = storeService.findByUserId(userId);
        return ResultDtoFactory.toAck("获取成功", list);
    }

    /**
     * 查询店铺列表
     *
     * @param storeInfoSearchDto
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ResultDto<StoreSearchDto> getStoreList(StoreInfoSearchDto storeInfoSearchDto) {
        DealerUserDto dealerUserDto = (DealerUserDto) SecurityContext.getCurrentUser().getParamMap().get("dealerUserDto");
        DealerDto dealerDto = dealerService.getDealerById(dealerUserDto.getDealerId());
        if (dealerDto == null) {
            return ResultDtoFactory.toNack("分销商不存在");
        }

        StoreSearchDto storePage = new StoreSearchDto();
        //查询已授权业务列表
        Date date = new Date();
        Date dateBefor = DateHelper.getDate(dealerDto.getBeginTime());
        Date dateAfter = DateHelper.getDate(dealerDto.getEndTime());
        if (!(date.before(dateAfter) && date.after(dateBefor))) {
            //分销商业务授权过期返回空
            return ResultDtoFactory.toAck("获取成功", storePage);
        }

        //根据分销商id查已授权的地区最子级districtId
        DealerProdSearchDto dealerProdSearchDto = new DealerProdSearchDto();
        dealerProdSearchDto.setDealerId(dealerUserDto.getDealerId());
        List<DealerProdDto> dealerProdList = dealerProdService.getDealerProdList(dealerProdSearchDto);
        if (dealerProdList == null || dealerProdList.size() <= 0) {
            return ResultDtoFactory.toAck("获取成功", storePage);
        }
        List<String> districtIds = new ArrayList<>();
        for (DealerProdDto dealerProdDto : dealerProdList) {
            districtIds.add(dealerProdDto.getDistrictId() + StringUtils.EMPTY);
        }
        if (districtIds == null && districtIds.size() <= 0) {
            //没有查到授权关系直接返回空
            return ResultDtoFactory.toAck("获取成功", storePage);
        }
        //需要请求的参数
        StoreSearchDto storeSearch = new StoreSearchDto();
        storeSearch.setAreaIds(districtIds);
        storeSearch.setPageSize(10);
        storeSearch.setCurrentPage(storeInfoSearchDto.getCurrentPage());
        storeSearch.setTotalRecord(storeInfoSearchDto.getTotalRecord());
        storeSearch.setTotalPages(storeInfoSearchDto.getTotalPages());

        if (StringUtils.isNotEmpty(storeInfoSearchDto.getUserName())) {
            //根据商户姓名查店铺列表
            storeSearch.setUserName(storeInfoSearchDto.getUserName());
        } else if (StringUtils.isNotEmpty(storeInfoSearchDto.getMobile())) {
            //根据商户手机号查店铺列表
            storeSearch.setMobile(storeInfoSearchDto.getMobile());
        } else if (StringUtils.isNotEmpty(storeInfoSearchDto.getStoreName())) {
            //根据店铺名称查店铺列表
            storeSearch.setStoreName(storeInfoSearchDto.getStoreName());
        } else if (StringUtils.isNotEmpty(storeInfoSearchDto.getFullAddress())) {
            //根据详细地址查店铺列表
            storeSearch.setFullAddress(storeInfoSearchDto.getFullAddress());
        } else if (StringUtils.isNotEmpty(storeInfoSearchDto.getTobaccoCertificateNo())) {
            //根据烟草证号查店铺列表
            storeSearch.setTobaccoCertificateNo(storeInfoSearchDto.getTobaccoCertificateNo());
        }
        StoreSearchDto storeSearchPage = storeService.getStorePage(storeSearch);

        //遍历行业名称
        List<IndustryDto> industryList = industryService.getAllIndustry();
        Map<String, String> industryMap = new HashMap<>();
        for (IndustryDto industryDto : industryList) {
            industryMap.put(industryDto.getMcc(), industryDto.getIndName());
        }
        List<StoreSearchDto> list = new ArrayList<>();
        for (StoreSearchDto storeSearchDto : storeSearchPage.getList()) {
            storeSearchDto.setIndustryName(industryMap.get(storeSearchDto.getIndustryMcc()));
            list.add(storeSearchDto);
        }
        storePage.setList(list);
        storePage.setTotalPages(storeSearchPage.getTotalPages());
        storePage.setTotalRecord(storeSearchPage.getTotalRecord());
        storePage.setCurrentPage(storeSearchPage.getCurrentPage());
        return ResultDtoFactory.toAck("获取成功", storePage);
    }

    /**
     * 附近的店铺列表，从近到远
     *
     * @param storeInfoSearchDto city lng lat
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/nearbyList", method = RequestMethod.GET)
    public ResultDto<StoreSearchDto> getNearbyStoreList(StoreInfoSearchDto storeInfoSearchDto) {
        DealerUserDto dealerUserDto = (DealerUserDto) SecurityContext.getCurrentUser().getParamMap().get("dealerUserDto");
        DealerDto dealerDto = dealerService.getDealerById(dealerUserDto.getDealerId());
        if (dealerDto == null) {
            return ResultDtoFactory.toNack("分销商不存在");
        }

        StoreSearchDto storePage = new StoreSearchDto();
        if (StringUtils.isEmpty(storeInfoSearchDto.getCity()) || StringUtils.isEmpty(storeInfoSearchDto.getLng())
                || StringUtils.isEmpty(storeInfoSearchDto.getLat())) {
            //必传参数没有传直接返回空
            return ResultDtoFactory.toAck("获取成功", storePage);
        }
        //查询已授权业务列表
        Date date = new Date();
        Date dateBefor = DateHelper.getDate(dealerDto.getBeginTime());
        Date dateAfter = DateHelper.getDate(dealerDto.getEndTime());
        if (!(date.before(dateAfter) && date.after(dateBefor))) {
            //分销商业务授权过期返回空
            return ResultDtoFactory.toAck("获取成功", storePage);
        }

        //根据分销商id查已授权的地区最子级districtId
        DealerProdSearchDto dealerProdSearchDto = new DealerProdSearchDto();
        dealerProdSearchDto.setDealerId(dealerUserDto.getDealerId());
        List<DealerProdDto> dealerProdList = dealerProdService.getDealerProdList(dealerProdSearchDto);
        if (dealerProdList == null || dealerProdList.size() <= 0) {
            return ResultDtoFactory.toAck("获取成功", storePage);
        }
        List<String> areaIds = new ArrayList<>();
        for (DealerProdDto dealerProdDto : dealerProdList) {
            areaIds.add(dealerProdDto.getDistrictId() + StringUtils.EMPTY);
        }
        if (areaIds == null && areaIds.size() <= 0) {
            //没有查到授权关系直接返回空
            return ResultDtoFactory.toAck("获取成功", storePage);
        }
        //需要请求的参数
        StoreSearchDto storeSearch = new StoreSearchDto();
        storeSearch.setAreaIds(areaIds);
        storeSearch.setPageSize(10);
        storeSearch.setCurrentPage(storeInfoSearchDto.getCurrentPage());
        storeSearch.setTotalRecord(storeInfoSearchDto.getTotalRecord());
        storeSearch.setTotalPages(storeInfoSearchDto.getTotalPages());

        storeSearch.setCity(storeInfoSearchDto.getCity());
        storeSearch.setLng(storeInfoSearchDto.getLng());
        storeSearch.setLat(storeInfoSearchDto.getLat());

        StoreSearchDto storeSearchPage = storeService.getStorePointPage(storeSearch);

        //遍历行业名称
        List<IndustryDto> industryList = industryService.getAllIndustry();
        Map<String, String> industryMap = new HashMap<>();
        for (IndustryDto industryDto : industryList) {
            industryMap.put(industryDto.getMcc(), industryDto.getIndName());
        }
        List<StoreSearchDto> list = new ArrayList<>();
        for (StoreSearchDto storeSearchDto : storeSearchPage.getList()) {
            storeSearchDto.setIndustryName(industryMap.get(storeSearchDto.getIndustryMcc()));
            list.add(storeSearchDto);
        }
        storePage.setList(list);
        storePage.setTotalPages(storeSearchPage.getTotalPages());
        storePage.setTotalRecord(storeSearchPage.getTotalRecord());
        storePage.setCurrentPage(storeSearchPage.getCurrentPage());
        return ResultDtoFactory.toAck("获取成功", storePage);
    }

    /**
     * 白名单店铺列表
     *
     * @param storeInfoSearchDto
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/whiteByList", method = RequestMethod.GET)
    @ApiOperation("白名单店铺列表")
    public ResultDto<StoreSearchDto> getWhiteByStoreList(StoreInfoSearchDto storeInfoSearchDto) {
        DealerUserDto dealerUserDto = (DealerUserDto) SecurityContext.getCurrentUser().getParamMap().get("dealerUserDto");
        DealerDto dealerDto = dealerService.getDealerById(dealerUserDto.getDealerId());
        if (dealerDto == null) {
            return ResultDtoFactory.toNack("分销商不存在");
        }

        StoreSearchDto storePage = new StoreSearchDto();
        //查询已授权业务列表
        Date date = new Date();
        Date dateBefor = DateHelper.getDate(dealerDto.getBeginTime());
        Date dateAfter = DateHelper.getDate(dealerDto.getEndTime());
        if (!(date.before(dateAfter) && date.after(dateBefor))) {
            //分销商业务授权过期返回空
            return ResultDtoFactory.toAck("获取成功", storePage);
        }
        //需要请求的参数
        StoreWhiteListSearchDto storeWhiteListSearch = new StoreWhiteListSearchDto();
        storeWhiteListSearch.setDealerId(dealerUserDto.getDealerId());
        storeWhiteListSearch.setUserId(SecurityContext.getCurrentUserId());
        storeWhiteListSearch.setNotRemark(true);
        storeWhiteListSearch.setPageSize(10);
        storeWhiteListSearch.setCurrentPage(storeInfoSearchDto.getCurrentPage());
        storeWhiteListSearch.setTotalRecord(storeInfoSearchDto.getTotalRecord());
        storeWhiteListSearch.setTotalPages(storeInfoSearchDto.getTotalPages());

        StoreWhiteListSearchDto storeWhiteListSearchPage = storeWhiteListService.getStorePage(storeWhiteListSearch);

        List<StoreSearchDto> list = new ArrayList<>();
        for (StoreWhiteListDto storeWhiteListDto : storeWhiteListSearchPage.getList()) {
            StoreSearchDto storeSearchDto = ConverterService.convert(storeWhiteListDto, StoreSearchDto.class);
            storeSearchDto.setIndustryName("烟草店");
            if (StringUtils.isEmpty(storeSearchDto.getUserName())) {
                storeSearchDto.setUserName("--");
            }
            if (StringUtils.isEmpty(storeWhiteListDto.getPhone())) {
                storeSearchDto.setMobile("--");
            } else {
                storeSearchDto.setMobile(storeWhiteListDto.getPhone());
            }
            storeSearchDto.setStoreId(storeWhiteListDto.getId());
            list.add(storeSearchDto);
        }
        storePage.setList(list);
        storePage.setTotalPages(storeWhiteListSearchPage.getTotalPages());
        storePage.setTotalRecord(storeWhiteListSearchPage.getTotalRecord());
        storePage.setCurrentPage(storeWhiteListSearchPage.getCurrentPage());
        return ResultDtoFactory.toAck("获取成功", storePage);
    }

}
