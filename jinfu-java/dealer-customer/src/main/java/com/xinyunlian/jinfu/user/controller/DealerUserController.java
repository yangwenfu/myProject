package com.xinyunlian.jinfu.user.controller;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.xinyunlian.jinfu.api.dto.ApiBaiduDto;
import com.xinyunlian.jinfu.api.service.ApiBaiduService;
import com.xinyunlian.jinfu.area.dto.SysAreaInfDto;
import com.xinyunlian.jinfu.area.service.SysAreaInfService;
import com.xinyunlian.jinfu.common.constant.ResultCode;
import com.xinyunlian.jinfu.common.converter.ConverterService;
import com.xinyunlian.jinfu.common.dto.ResultDto;
import com.xinyunlian.jinfu.common.dto.ResultDtoFactory;
import com.xinyunlian.jinfu.common.enums.EErrorCode;
import com.xinyunlian.jinfu.common.exception.BizServiceException;
import com.xinyunlian.jinfu.common.fileStore.FileStoreService;
import com.xinyunlian.jinfu.common.fileStore.enums.EFileType;
import com.xinyunlian.jinfu.common.security.SecurityContext;
import com.xinyunlian.jinfu.common.util.*;
import com.xinyunlian.jinfu.common.yitu.YituService;
import com.xinyunlian.jinfu.common.yitu.dto.req.VerifyReqDto;
import com.xinyunlian.jinfu.common.yitu.dto.resp.CertificationDto;
import com.xinyunlian.jinfu.common.yitu.helper.FileHelper;
import com.xinyunlian.jinfu.dealer.dto.*;
import com.xinyunlian.jinfu.dealer.service.*;
import com.xinyunlian.jinfu.exam.dto.ExamDto;
import com.xinyunlian.jinfu.exam.enums.EExamType;
import com.xinyunlian.jinfu.exam.service.ExamService;
import com.xinyunlian.jinfu.exam.service.ExamUserService;
import com.xinyunlian.jinfu.prod.dto.ProductDto;
import com.xinyunlian.jinfu.prod.enums.EProd;
import com.xinyunlian.jinfu.prod.service.ProdService;
import com.xinyunlian.jinfu.push.enums.PushObject;
import com.xinyunlian.jinfu.push.service.PushService;
import com.xinyunlian.jinfu.stats.dto.StatsDetailDto;
import com.xinyunlian.jinfu.stats.service.StatsService;
import com.xinyunlian.jinfu.store.dto.StoreInfDto;
import com.xinyunlian.jinfu.store.dto.req.CertifyDto;
import com.xinyunlian.jinfu.store.service.StoreService;
import com.xinyunlian.jinfu.user.dto.*;
import com.xinyunlian.jinfu.user.service.DealerUserService;
import com.xinyunlian.jinfu.wechat.service.WeChatService;
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
@RequestMapping(value = "user")
public class DealerUserController {
    private static final Logger LOGGER = LoggerFactory.getLogger(DealerUserController.class);

    @Autowired
    private DealerUserOrderService dealerUserOrderService;
    @Autowired
    private DealerUserStoreService dealerUserStoreService;
    @Autowired
    private DealerUserNoteService dealerUserNoteService;
    @Autowired
    private DealerUserService dealerUserService;
    @Autowired
    private PushService pushService;
    @Autowired
    private YituService yituService;
    @Autowired
    private FileStoreService fileStoreService;
    @Autowired
    private StatsService statsService;
    @Autowired
    private ExamService examService;
    @Autowired
    private ExamUserService examUserService;
    @Autowired
    private ApiBaiduService apiBaiduService;
    @Autowired
    private ProdService prodService;
    @Autowired
    private SysAreaInfService sysAreaInfService;
    @Autowired
    private DealerProdService dealerProdService;
    @Autowired
    private StoreService storeService;
    @Autowired
    private DealerService dealerService;
    @Autowired
    private WeChatService weChatService;

    /**
     * 查询本周业绩数据
     *
     * @return
     */
    @RequestMapping(value = "/total/get", method = RequestMethod.GET)
    @ResponseBody
    public ResultDto<Object> getTotal() {
        DealerUserDto dealerUserDto = dealerUserService.getDealerUserById(SecurityContext.getCurrentUserId());
        if (dealerUserDto != null && !dealerUserDto.getExamPassed()) {//考试未通过强制退出
            SecurityContext.logout();
            return ResultDtoFactory.toNack("您未通过首考，请重新登录参加考试");
        }
        Date date = new Date();
        String beginTime = DateHelper.getFirstDateOfWeek(DateHelper.formatDate(date, "yyyy-MM-dd"));
        String endTime = DateHelper.getLastDateOfWeek(DateHelper.formatDate(date, "yyyy-MM-dd"));

        DealerUserNoteDto dealerUserNoteDto = new DealerUserNoteDto();
        dealerUserNoteDto.setUserId(SecurityContext.getCurrentUserId());
        dealerUserNoteDto.setBeginTime(beginTime);
        dealerUserNoteDto.setEndTime(endTime);
        long noteCount = dealerUserNoteService.getCount(dealerUserNoteDto);

        DealerUserOrderDto dealerUserOrderDto = new DealerUserOrderDto();
        dealerUserOrderDto.setUserId(SecurityContext.getCurrentUserId());
        dealerUserOrderDto.setBeginTime(beginTime);
        dealerUserOrderDto.setEndTime(endTime);
        long orderCount = dealerUserOrderService.getCount(dealerUserOrderDto);

        DealerUserStoreDto dealerUserStoreDto = new DealerUserStoreDto();
        dealerUserStoreDto.setUserId(SecurityContext.getCurrentUserId());
        dealerUserStoreDto.setBeginTime(beginTime);
        dealerUserStoreDto.setEndTime(endTime);
        long storeCount = dealerUserStoreService.getCount(dealerUserStoreDto);

        DealerUserTotalDto dealerUserTotalDto = new DealerUserTotalDto();
        dealerUserTotalDto.setOrderCount(orderCount);
        dealerUserTotalDto.setStoreCount(storeCount);
        dealerUserTotalDto.setNoteCount(noteCount);
        dealerUserTotalDto.setUnReadCount(pushService.getunreadMessageCountByUserId(SecurityContext.getCurrentUserId(), PushObject.XHB));
        ResultDto<Object> result = new ResultDto<Object>();
        result.setData(dealerUserTotalDto);
        result.setCode(ResultCode.ACK);
        return result;
    }

    /**
     * 查询今日业绩数据
     *
     * @return
     */
    @RequestMapping(value = "/totalToday", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "查询今日业绩数据")
    public ResultDto<StatsDetailDto> getTotalToday() {
        DealerUserDto dealerUserDto = (DealerUserDto) SecurityContext.getCurrentUser().getParamMap().get("dealerUserDto");

        if (!dealerUserDto.getExamPassed()) {//考试未通过强制退出
            SecurityContext.logout();
            return ResultDtoFactory.toNack("您未通过首考，请重新登录参加考试");
        }
        Date date = new Date();
        String beginTime = DateHelper.formatDate(date, "yyyy-MM-dd");
        String endTime = DateHelper.formatDate(date, "yyyy-MM-dd");
        StatsDetailDto statsDetailDto = statsService.getStats(beginTime, endTime, dealerUserDto);
        return ResultDtoFactory.toAck("获取成功", statsDetailDto);
    }

    /**
     * 修改密码
     *
     * @param dealerUserPwdDto
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/updatePwd", method = RequestMethod.POST)
    public ResultDto<String> updatePwd(@RequestBody DealerUserPwdDto dealerUserPwdDto) {
        dealerUserPwdDto.setUserId(SecurityContext.getCurrentUserId());
        dealerUserService.updateDealerUserPwd(dealerUserPwdDto);
        return ResultDtoFactory.toAck("修改密码成功");
    }

    /**
     * 实名认证 带身份证正反面
     *
     * @param certifyDto
     * @return
     */
    @RequestMapping(value = "/certify", method = RequestMethod.POST)
    @ResponseBody
    public ResultDto<Object> certify(@RequestBody @Valid CertifyDto certifyDto, BindingResult result) {

        if (result.hasErrors()) {
            return ResultDtoFactory.toNack(result.getFieldError().getDefaultMessage());
        }

        if (StringUtils.isEmpty(certifyDto.getIdCardPic1Base64()) || StringUtils.isEmpty(certifyDto.getIdCardPic2Base64())) {
            return ResultDtoFactory.toNack("身份证照片必传");
        }
        if (StringUtils.isEmpty(certifyDto.getIdCardNo())) {
            return ResultDtoFactory.toNack("身份证号必填");
        }
        DealerUserDto dealerUserDto = dealerUserService.getDealerUserById(SecurityContext.getCurrentUserId());
        if (dealerUserDto == null) {
            return ResultDtoFactory.toNack("用户不存在");
        }
        if (dealerUserDto.getIdentityAuth() != null && dealerUserDto.getIdentityAuth()) {
            return ResultDtoFactory.toNack("实名认证已通过");
        }

        String linePicPath = null;

        try {
            CertificationDto certificationDto = yituService.certification(dealerUserDto.getName(),
                    certifyDto.getIdCardNo(),
                    certifyDto.getIdCardPic1Base64());

            if (certificationDto.isValid() == false) {
                return ResultDtoFactory.toNack("实名认证未通过!");
            }
        } catch (Exception e) {
            throw new BizServiceException(EErrorCode.USER_IDENTITY_AUTH_ERROR);
        }

        //身份证上传
        String idCardPicFrontPath = null;
        String idCardPicBackPath = null;

        try {
            File idCardPic1File = ImageUtils.GenerateImageByBase64(certifyDto.getIdCardPic1Base64());
            idCardPicFrontPath = fileStoreService.upload(EFileType.DEALER_USER_IMG, idCardPic1File, idCardPic1File.getName());

        } catch (IOException e) {
            LOGGER.error("身份证正面图片上传失败", e);
        }
        try {
            File idCardPic2File = ImageUtils.GenerateImageByBase64(certifyDto.getIdCardPic2Base64());
            idCardPicBackPath = fileStoreService.upload(EFileType.DEALER_USER_IMG, idCardPic2File, idCardPic2File.getName());

        } catch (IOException e) {
            LOGGER.error("身份证反面图片上传失败", e);
        }

        dealerUserDto.setIdCardNo(certifyDto.getIdCardNo());
        dealerUserDto.setIdFrontPic(idCardPicFrontPath);
        dealerUserDto.setIdBackPic(idCardPicBackPath);
        dealerUserDto.setLinePic(linePicPath);

        dealerUserService.idAuth(dealerUserDto);

        return ResultDtoFactory.toAck("实名认证成功");
    }

    /**
     * 大礼包比对接口
     */
    @RequestMapping(value = "/verify", method = RequestMethod.POST)
    @ResponseBody
    public Object verify(@RequestBody VerifyDto data) {
        try {
            String userId = SecurityContext.getCurrentUserId();
            DealerUserDto dealerUserDto = dealerUserService.getDealerUserById(userId);

            //构造对比请求
            VerifyReqDto verifyReqDto = new VerifyReqDto();
            verifyReqDto.setName(dealerUserDto.getName());
            verifyReqDto.setIdCard(dealerUserDto.getIdCardNo());
            verifyReqDto.setImagePacket(data.getVerificationPackage());

            com.xinyunlian.jinfu.common.yitu.dto.resp.CertificationDto certificationDto = yituService.verify(verifyReqDto);

            if (certificationDto.getRtn() != 0) {
                LOGGER.info(String.format("%s verify error,verifyRs:%s", userId));
                return ResultDtoFactory.toNack(MessageUtil.getMessage("user.live.verify.error"));
            }

            List<String> liveImages = certificationDto.getImages();

            if (liveImages.size() <= 0) {
                LOGGER.info(String.format("%s verify error no live image,verifyRs:%s", userId));
                return ResultDtoFactory.toNack(MessageUtil.getMessage("user.live.verify.error"));
            }

            //保存活体照片
            File image = ImageUtils.GenerateImageByBase64(liveImages.get(0));
            String livePicPath = fileStoreService.upload(EFileType.DEALER_USER_IMG, image, image.getName());

            //保存大礼包比对的结果
            dealerUserDto.setLived(true);
            dealerUserDto.setYituIsPass(certificationDto.isPass());
            dealerUserDto.setYituSimilarity(certificationDto.getFinalVerifyScore());
            dealerUserDto.setLivePic(livePicPath);
            if (null != dealerUserDto.getYituSimilarity()
                    &&  dealerUserDto.getYituSimilarity().doubleValue() > 75) {
                dealerUserDto.setPassed(true);
                dealerUserService.lived(dealerUserDto);
            } else {
                dealerUserService.lived(dealerUserDto);
                return ResultDtoFactory.toNack(MessageUtil.getMessage("user.live.verify.not.pass"));
            }


            return ResultDtoFactory.toAck(MessageUtil.getMessage("common.operate.success"));
        } catch (Exception e) {
            LOGGER.warn("user verify error", e);
            return ResultDtoFactory.toNack(MessageUtil.getMessage("user.live.verify.error"));
        }
    }

    /**
     * 获取个人二维码
     *
     * @return
     */
    @RequestMapping(value = "/getQR", method = RequestMethod.GET)
    @ResponseBody
    public ResultDto<Object> getQR() {
        DealerUserDto dealerUserDto = (DealerUserDto) SecurityContext.getCurrentUser().getParamMap().get("dealerUserDto");

        if (dealerUserDto.getIdentityAuth() == null || !dealerUserDto.getIdentityAuth()
                || dealerUserDto.getPassed() == null || !dealerUserDto.getPassed()) {
            return ResultDtoFactory.toNack("用户还未通过认证！");
        }

        DealerUserQRInfo qrInfo = dealerUserService.getQrInfo(dealerUserDto.getUserId());
        qrInfo.setQrUrl(AppConfigUtil.getConfig("domain.url") + "/" + qrInfo.getQrUrl());
        return ResultDtoFactory.toAck(MessageUtil.getMessage("common.operate.success"), qrInfo);
    }


    /**
     * 获取分销员个人信息
     *
     * @return
     */
    @RequestMapping(value = "/getUserInfo", method = RequestMethod.GET)
    @ResponseBody
    public ResultDto<Object> getUserInfo() {
        DealerUserDto dealerUserDto = dealerUserService.getDealerUserById(SecurityContext.getCurrentUserId());
        if (dealerUserDto == null) {
            return ResultDtoFactory.toNack("用户不存在");
        }
        DealerUserDto userResult = new DealerUserDto();
        userResult.setIdentityAuth(dealerUserDto.getIdentityAuth());
        userResult.setName(dealerUserDto.getName());
        userResult.setPassed(dealerUserDto.getPassed());
        return ResultDtoFactory.toAck(MessageUtil.getMessage("common.operate.success"), userResult);
    }

    /**
     * 获取首页信息
     *
     * @return
     */
    @RequestMapping(value = "/getHomeInfo", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation("获取首页信息")
    public ResultDto<Object> getHomeInfo(HomeInfoDto homeInfoDto) {
        DealerUserDto dealerUserDto = (DealerUserDto) SecurityContext.getCurrentUser().getParamMap().get("dealerUserDto");

        Long unreadCount = pushService.getunreadMessageCountByUserId(SecurityContext.getCurrentUserId(), PushObject.XHB);//推送消息数
        Boolean showExam = false;//考试显示
        ExamDto examDto = new ExamDto();
        examDto.setType(EExamType.ROUTINE);
        Date date = new Date();
        examDto.setStartTime(date);
        examDto.setEndTime(date);
        List<ExamDto> list = examService.getValidExamList(examDto);
        if (CollectionUtils.isNotEmpty(list)) {
            showExam = true;
        }
        List<ProductDto> prodList = new ArrayList<>();//首页业务代办
        ProductDto productDto;
        //保骉车险显示
        if (StringUtils.isNotEmpty(homeInfoDto.getLogLng()) && StringUtils.isNotEmpty(homeInfoDto.getLogLat())) {//没坐标不显示
            ApiBaiduDto apiBaiduDto = apiBaiduService.getArea(homeInfoDto.getLogLat() + "," + homeInfoDto.getLogLng());
            SysAreaInfDto sysAreaInfDto = new SysAreaInfDto();
            if (apiBaiduDto != null) {
                sysAreaInfDto = sysAreaInfService.getSysAreaByGbCode(apiBaiduDto.getGbCode());
                if (sysAreaInfDto == null) {
                    LOGGER.info("baidu gbcode error:" + JsonUtil.toJson(apiBaiduDto));
                }
            }
            Boolean flag = false;
            List<String> districtIdsList = new ArrayList<>();
            List<String> areaList = new ArrayList<>();
            if (sysAreaInfDto != null) {
                StoreInfDto storeInfDto = storeService.findByStoreId(100869L);//默认店铺id100869
                areaList = Arrays.asList(sysAreaInfDto.getTreePath().split(","));
                districtIdsList.add(String.valueOf(sysAreaInfDto.getId()));
                if (storeInfDto != null) {
                    flag = prodService.checkProdArea(EProd.S01002.getCode(), sysAreaInfDto.getId(), storeInfDto.getIndustryMcc());//产品地区行业授权
                }
            }
            for (String areaId : areaList) {
                if (StringUtils.isNotEmpty(areaId)) {
                    districtIdsList.add(areaId);
                }
            }
            if (flag) {//如产品地区行业授权通过，判断分销授地区行业授权
                if (CollectionUtils.isNotEmpty(districtIdsList)) {
                    DealerProdSearchDto dealerProdSearchDto = new DealerProdSearchDto();
                    dealerProdSearchDto.setDealerId(dealerUserDto.getDealerId());
                    dealerProdSearchDto.setProdId(EProd.S01002.getCode());
                    dealerProdSearchDto.setDistrictIdsList(districtIdsList);
                    dealerProdSearchDto.setExpire(true);
                    List<DealerProdDto> dealerProdDtoList = dealerProdService.getDealerProdList(dealerProdSearchDto);//分销授地区行业授权
                    if (CollectionUtils.isNotEmpty(dealerProdDtoList)) {
                        productDto = prodService.getProdById(EProd.S01002.getCode());
                        prodList.add(productDto);
                    }
                }
            }
        }
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("unreadCount", unreadCount);
        resultMap.put("showExam", showExam);
        resultMap.put("prodList", prodList);
        return ResultDtoFactory.toAck("获取成功", resultMap);
    }

    @ResponseBody
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ApiOperation(value = "当前分销商所有分销员")
    public ResultDto<List<DealerUserInfoDto>> getList() {
        DealerUserDto dealerUserDto = dealerUserService.getDealerUserById(SecurityContext.getCurrentUserId());
        if (dealerUserDto == null) {
            return ResultDtoFactory.toNack("分销员不存在");
        } else if (!dealerUserDto.getAdmin()) {
            return ResultDtoFactory.toNack("当前分销员不是管理员");
        }
        List<DealerUserDto> dealerUserList = dealerUserService.findDealerUsersByDealerId(dealerUserDto.getDealerId());
        List<DealerUserInfoDto> list = new ArrayList<>();
        for (DealerUserDto dto : dealerUserList) {
            DealerUserInfoDto dealerUserInfoDto = ConverterService.convert(dto, DealerUserInfoDto.class);
            list.add(dealerUserInfoDto);
        }
        return ResultDtoFactory.toAck("获取成功", list);
    }

    @ResponseBody
    @RequestMapping(value = "/subscribeQrCode", method = RequestMethod.GET)
    @ApiOperation(value = "微信关注二维码")
    public ResultDto<Map<String, String>> getSubscribeQrCode(@RequestParam String wechatType) {
        Map<String, String> resultMap = new HashMap<>();
        if ("YLJF".equals(wechatType)) {
            resultMap = weChatService.getQrcodeInfo(SecurityContext.getCurrentUserId());
        }
        return ResultDtoFactory.toAck("获取成功", resultMap);
    }

}
