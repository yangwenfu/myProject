package com.xinyunlian.jinfu.shopkeeper.controller;

import com.xinyunlian.jinfu.car.dto.UserCarDto;
import com.xinyunlian.jinfu.car.service.UserCarService;
import com.xinyunlian.jinfu.common.dto.ResultDto;
import com.xinyunlian.jinfu.common.dto.ResultDtoFactory;
import com.xinyunlian.jinfu.common.exception.BizServiceException;
import com.xinyunlian.jinfu.common.security.SecurityContext;
import com.xinyunlian.jinfu.common.util.MessageUtil;
import com.xinyunlian.jinfu.dict.dto.ActivityDictDto;
import com.xinyunlian.jinfu.dict.dto.ScoreDictDto;
import com.xinyunlian.jinfu.dict.service.PointDictService;
import com.xinyunlian.jinfu.house.dto.UserHouseDto;
import com.xinyunlian.jinfu.house.service.UserHouseService;
import com.xinyunlian.jinfu.picture.dto.PictureDto;
import com.xinyunlian.jinfu.picture.enums.EPictureType;
import com.xinyunlian.jinfu.picture.service.PictureService;
import com.xinyunlian.jinfu.point.dto.ScoreContinuityDto;
import com.xinyunlian.jinfu.point.dto.UserScoreChangelogDto;
import com.xinyunlian.jinfu.point.dto.UserScoreSignDto;
import com.xinyunlian.jinfu.point.service.UserScoreService;
import com.xinyunlian.jinfu.shopkeeper.dto.card.CardCheckDto;
import com.xinyunlian.jinfu.shopkeeper.dto.card.CardDto;
import com.xinyunlian.jinfu.shopkeeper.dto.my.AlreadySignInConDto;
import com.xinyunlian.jinfu.shopkeeper.dto.my.SignInContinuityDto;
import com.xinyunlian.jinfu.shopkeeper.dto.my.UserDto;
import com.xinyunlian.jinfu.shopkeeper.service.MyInfoService;
import com.xinyunlian.jinfu.sign.service.UserSignInLogService;
import com.xinyunlian.jinfu.user.dto.UserBankAcctDto;
import com.xinyunlian.jinfu.user.service.UserBankAcctService;
import com.xinyunlian.jinfu.yunma.dto.YMMemberDto;
import com.xinyunlian.jinfu.yunma.service.YMMemberService;
import com.xinyunlian.jinfu.user.dto.UserBankAcctDto;
import com.xinyunlian.jinfu.user.service.UserBankAcctService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * @author jll
 */
@RestController
@RequestMapping(value = "shopkeeper/my")
@Api(description = "掌柜个人信息相关")
public class MyController {
    @Autowired
    private MyInfoService myInfoService;
    @Autowired
    private UserCarService userCarService;
    @Autowired
    private UserHouseService userHouseService;
    @Autowired
    private UserBankAcctService userBankAcctService;
    @Autowired
    private PictureService pictureService;

    @Autowired
    private YMMemberService ymMemberService;

    @Autowired
    private UserSignInLogService userSignInLogService;

    @Autowired
    private UserScoreService userScoreService;

    @Autowired
    private PointDictService pointDictService;

    private static final Logger LOGGER = LoggerFactory.getLogger(MyController.class);

    private static Integer CONTINUITY_DAYS_FIXED = 7;
    private static Long CONTINUITY_SCORE_STEP = 1l;

    /**
     * 掌柜个人信息
     */
    @RequestMapping(value = "/info", method = RequestMethod.GET)
    public Object info() {
        String userId = SecurityContext.getCurrentUserId();
        UserDto userDto = myInfoService.getUser(userId);
        return ResultDtoFactory.toAck(MessageUtil.getMessage("common.operate.success"),userDto);
    }

    /**
     * 卡片完整度
     */
    @RequestMapping(value = "/checkCard", method = RequestMethod.GET)
    public Object checkCard() {
        String userId = SecurityContext.getCurrentUserId();
        CardDto cardDto = myInfoService.getCard(userId);
        CardCheckDto cardCheck = myInfoService.getCardCheck(cardDto);
        return ResultDtoFactory.toAck(MessageUtil.getMessage("common.operate.success"), cardCheck);
    }

    /**
     * 小贷卡片完整度
     */
    @ApiOperation(value = "小贷卡片完整度")
    @RequestMapping(value = "/checkLoanCard", method = RequestMethod.GET)
    public Object checkLoanCard() {
        String userId = SecurityContext.getCurrentUserId();
        CardDto cardDto = myInfoService.getCard(userId);
        CardCheckDto cardCheck = myInfoService.getCardCheck(cardDto);

        //是否有车图片
        List<UserCarDto> userCarDtos = userCarService.list(userId);
        if(!CollectionUtils.isEmpty(userCarDtos)){
            cardCheck.setCarId(userCarDtos.get(0).getId().toString());
            List<PictureDto> pictureDtos = pictureService.list(cardCheck.getCarId());
            if(!CollectionUtils.isEmpty(pictureDtos)){
                cardCheck.setHasCar(true);
            }
        }

        //是否有房图片
        List<UserHouseDto> userHouseDtos = userHouseService.list(userId);
        if(!CollectionUtils.isEmpty(userHouseDtos)){
            cardCheck.setHouseId(userHouseDtos.get(0).getId().toString());
            List<PictureDto> pictureDtos = pictureService.list(cardCheck.getHouseId());
            if(!CollectionUtils.isEmpty(pictureDtos)){
                cardCheck.setHasHouse(true);
            }
        }

        //是否有流水图片
        List<UserBankAcctDto> userBankAcctDtos = userBankAcctService.list(userId);
        if(!CollectionUtils.isEmpty(userBankAcctDtos)){
            cardCheck.setBankAccId(userBankAcctDtos.get(0).getBankAccountId().toString());
            List<PictureDto> pictureDtos = pictureService.list(cardCheck.getBankAccId());
            if(!CollectionUtils.isEmpty(pictureDtos)){
                cardCheck.setHasBankAcc(true);
            }
        }

        return ResultDtoFactory.toAck(MessageUtil.getMessage("common.operate.success"), cardCheck);
    }

    /**
     * 上传房车银行流水图片
     * @param pictureDtos
     * @param type
     * @return
     */
    @ApiOperation(value = "上传房车银行流水图片,type:car,house,bankAcc")
    @RequestMapping(value = "/uploadPicture/{type}", method = RequestMethod.POST)
    @ResponseBody
    public Object uploadPicture(@RequestBody List<PictureDto> pictureDtos, @PathVariable String type){
        if(!CollectionUtils.isEmpty(pictureDtos)){
            String userId = SecurityContext.getCurrentUserId();
            String parentId = null;
            if(StringUtils.isEmpty(pictureDtos.get(0).getParentId())){
                if("car".equals(type)){
                    List<UserCarDto> userCarDtos = userCarService.list(userId);
                    if(CollectionUtils.isEmpty(userCarDtos)) {
                        UserCarDto userCarDto = new UserCarDto();
                        userCarDto.setUserId(userId);
                        userCarDto = userCarService.save(userCarDto);
                        parentId = userCarDto.getId().toString();
                    }else{
                        parentId = userCarDtos.get(0).getId().toString();
                    }
                    pictureService.deleteByParentId(parentId,EPictureType.CAR_DRIVING_LICENSE.getCode());
                    pictureService.deleteByParentId(parentId,EPictureType.CAR_REGISTER_CERTIFICATE.getCode());
                }else if("house".equals(type)){
                    List<UserHouseDto> userHouseDtos = userHouseService.list(userId);
                    if(CollectionUtils.isEmpty(userHouseDtos)) {
                        UserHouseDto userHouseDto = new UserHouseDto();
                        userHouseDto.setUserId(userId);
                        userHouseDto = userHouseService.save(userHouseDto);
                        parentId = userHouseDto.getId().toString();
                    }else{
                        parentId = userHouseDtos.get(0).getId().toString();
                    }
                    pictureService.deleteByParentId(parentId,EPictureType.HOUSE_PROPERTY.getCode());
                }else if("bankAcc".equals(type)){
                    List<UserBankAcctDto> userBankAcctDtos = userBankAcctService.list(userId);
                    if(CollectionUtils.isEmpty(userBankAcctDtos)) {
                        UserBankAcctDto userBankAcctDto = new UserBankAcctDto();
                        userBankAcctDto.setUserId(userId);
                        userBankAcctDto = userBankAcctService.save(userBankAcctDto);
                        parentId = userBankAcctDto.getBankAccountId().toString();
                    }else{
                        parentId = userBankAcctDtos.get(0).getBankAccountId().toString();
                    }
                    pictureService.deleteByParentId(parentId,EPictureType.BANK_TRADE.getCode());
                }
            }
            for (PictureDto pictureDto: pictureDtos) {
                pictureDto.setParentId(parentId);
                if("bankAcc".equals(type)){
                    pictureDto.setPictureType(EPictureType.BANK_TRADE);
                }
                pictureService.savePicture(pictureDto);
            }
        }
        return ResultDtoFactory.toAck(MessageUtil.getMessage("common.operate.success"));
    }


    /**
     * "获取房车等图片信息
     * @param type
     * @return
     */
    @ApiOperation(value = "获取房车等图片信息,type:car,house,bankAcc")
    @RequestMapping(value = "/getPictures/{type}", method = RequestMethod.GET)
    @ResponseBody
    public Object getPictures(@PathVariable String type){
        String userId = SecurityContext.getCurrentUserId();
        List<PictureDto> pictureDtos = new ArrayList<>();

        if("car".equals(type)){
            List<UserCarDto> userCarDtos = userCarService.list(userId);
            if(!CollectionUtils.isEmpty(userCarDtos)) {
                List<PictureDto> pictureDtosTemp = pictureService.list(userCarDtos.get(0).getId().toString());
                for (PictureDto pictureDto : pictureDtosTemp){
                    if(pictureDto.getPictureType() == EPictureType.CAR_DRIVING_LICENSE ||
                            pictureDto.getPictureType() == EPictureType.CAR_REGISTER_CERTIFICATE){
                        pictureDtos.add(pictureDto);
                    }
                }
            }
        }else if("house".equals(type)){
            List<UserHouseDto> userHouseDtos = userHouseService.list(userId);
            if(!CollectionUtils.isEmpty(userHouseDtos)) {
                pictureDtos = pictureService.list(userHouseDtos.get(0).getId().toString()
                        , EPictureType.HOUSE_PROPERTY);
            }
        }else if("bankAcc".equals(type)){
            List<UserBankAcctDto> userBankAcctDtos = userBankAcctService.list(userId);
            if(!CollectionUtils.isEmpty(userBankAcctDtos)) {
                pictureDtos = pictureService.list(userBankAcctDtos.get(0).getBankAccountId().toString()
                        , EPictureType.BANK_TRADE);
            }
        }
        return ResultDtoFactory.toAck(MessageUtil.getMessage("common.operate.success"),pictureDtos);
    }

    /**
     * 卡片是否全部完整
     */
    @ApiOperation(value = "检查卡片内容是否全部完整")
    @RequestMapping(value = "/checkCardFull", method = RequestMethod.GET)
    public Object checkCardFull() {
        String userId = SecurityContext.getCurrentUserId();
        CardDto cardDto = myInfoService.getCard(userId);
        CardCheckDto cardCheck = myInfoService.getCardCheck(cardDto);
        if(cardCheck.isIdAuthIsFull() && cardCheck.isRiskAuthIsFull() && cardCheck.isLinkmanIsFull()
                && cardCheck.isStoreExtIsFull() && cardCheck.isStoreIsFull() && cardCheck.isBankAuthIsFull()
                && cardCheck.isBaseIsFull()){
            return ResultDtoFactory.toAck(MessageUtil.getMessage("common.operate.success"));
        }

        return ResultDtoFactory.toNack("卡片信息未完善");
    }

    /**
     * 检查用户是不是云码用户
     * @return
     */
    @RequestMapping(value = "/checkYmmember", method = RequestMethod.GET)
    public ResultDto checkYmmember(){
        String userId = SecurityContext.getCurrentUserId();
        List<YMMemberDto> list = ymMemberService.getMemberListByUserId(userId);
        if (!CollectionUtils.isEmpty(list)){
            return ResultDtoFactory.toAck(MessageUtil.getMessage("common.operate.success"));
        }
        return ResultDtoFactory.toNack("您还不是云码用户，请开通云码后参与。");
    }

    /**
     * 云码用户签到
     * @param qrcodeUrl
     * @return
     */
    /*@RequestMapping(value = "/signIn", method = RequestMethod.POST)
    public ResultDto signIn(String qrcodeUrl){

        try {
            String userId = SecurityContext.getCurrentUserId();
            if (!StringUtils.isEmpty(qrcodeUrl)){
                String qrcode = qrcodeUrl.substring(qrcodeUrl.lastIndexOf("/") + 1);
                YMMemberDto ymMemberDto = ymMemberService.getMemberByQrCodeNo(qrcode);
                if (ymMemberDto != null){
                    if (!userId.equals(ymMemberDto.getUserId())){
                        return ResultDtoFactory.toNack("您的实名认证信息与该云码牌持有人信息不一致！为确保云码的安全使用，请立即联系客服 400-801-9906");
                    }

                    Boolean signIn = userSignInLogService.checkSignIn(userId, Calendar.getInstance().getTime());
                    if (signIn){
                        return ResultDtoFactory.toCustom("ALREADY_SIGN_IN", "已签到，请不要重复签到", null);
                    }
                    userSignInLogService.signIn(userId, Calendar.getInstance().getTime());
                    return ResultDtoFactory.toAck("签到成功");
                }else if (ymMemberDto == null){
                    return ResultDtoFactory.toNack("签到失败，云码用户信息不存在");
                }
            }
            return ResultDtoFactory.toNack("签到失败，云码不能为空");
        } catch (BizServiceException e) {
            LOGGER.error("签到失败", e);
            return ResultDtoFactory.toNack("签到失败");
        }
    }*/

    /**
     * 日常签到
     * @param qrcodeUrl
     * @return
     */
    @RequestMapping(value = "/signInDaily", method = RequestMethod.POST)
    public ResultDto signInDaily(String qrcodeUrl){
        try {
            String userId = SecurityContext.getCurrentUserId();
            if (!StringUtils.isEmpty(qrcodeUrl)){
                String qrcode = qrcodeUrl.substring(qrcodeUrl.lastIndexOf("/") + 1);
                YMMemberDto ymMemberDto = ymMemberService.getMemberByQrCodeNo(qrcode);
                if (ymMemberDto != null){
                    if (!userId.equals(ymMemberDto.getUserId())){
                        return ResultDtoFactory.toNack("您的实名认证信息与该云码牌持有人信息不一致！为确保云码的安全使用，请立即联系客服 400-801-9906");
                    }

                    Boolean signIn = userSignInLogService.checkSignIn(userId, Calendar.getInstance().getTime());
                    if (signIn){
                        return ResultDtoFactory.toCustom("ALREADY_SIGN_IN", "已签到，请不要重复签到", null);
                    }
                    userSignInLogService.signIn(userId, Calendar.getInstance().getTime());

                    UserScoreChangelogDto scoreChangelogDto = new UserScoreChangelogDto();
                    scoreChangelogDto.setChangedScoreCode("P_ONE");
                    scoreChangelogDto.setActivityCode("DAILY_SIGN_IN");
                    scoreChangelogDto.setUserId(userId);
                    scoreChangelogDto.setTranSeq(String.valueOf(System.currentTimeMillis()));
                    userScoreService.addScore(scoreChangelogDto);

                    return ResultDtoFactory.toAck("签到成功");
                }else if (ymMemberDto == null){
                    return ResultDtoFactory.toNack("签到失败，云码用户信息不存在");
                }
            }
            return ResultDtoFactory.toNack("签到失败，云码不能为空");
        } catch (BizServiceException e) {
            LOGGER.error("签到失败", e);
            return ResultDtoFactory.toNack("签到失败");
        }
    }

    /**
     * 连续签到
     * @param signInContinuityDto
     * @return
     */
    @PostMapping(value = "/signInContinuity")
    public ResultDto signInContinuity(@RequestBody @Valid SignInContinuityDto signInContinuityDto, BindingResult result){
        try {
            if (result.hasErrors()){
                return ResultDtoFactory.toNack(result.getFieldError().getDefaultMessage());
            }

            String userId = SecurityContext.getCurrentUserId();
            if (!StringUtils.isEmpty(signInContinuityDto.getQrcodeUrl())){
                String qrcode = signInContinuityDto.getQrcodeUrl().substring(signInContinuityDto.getQrcodeUrl().lastIndexOf("/") + 1);
                YMMemberDto ymMemberDto = ymMemberService.getMemberByQrCodeNo(qrcode);
                if (ymMemberDto != null){
                    if (!userId.equals(ymMemberDto.getUserId())){
                        return ResultDtoFactory.toNack("您的实名认证信息与该云码牌持有人信息不一致！为确保云码的安全使用，请立即联系客服 400-801-9906");
                    }

                    ActivityDictDto activityDictDto = pointDictService.checkActivityExists(signInContinuityDto.getActivityCode());
                    if (activityDictDto == null){
                        return ResultDtoFactory.toNack("该活动不存在");
                    }

                    ScoreDictDto pOne = pointDictService.checkScoreExists(signInContinuityDto.getInitScoreCode());
                    if (pOne == null){
                        return ResultDtoFactory.toNack("积分信息不存在");
                    }

                    ScoreContinuityDto scoreContinuityDto = new ScoreContinuityDto();
                    scoreContinuityDto.setContinuityDaysFixed(CONTINUITY_DAYS_FIXED);
                    scoreContinuityDto.setSource(activityDictDto.getActivityName());
                    scoreContinuityDto.setStart(pOne.getScoreValue());
                    scoreContinuityDto.setStep(CONTINUITY_SCORE_STEP);
                    scoreContinuityDto.setUserId(userId);
                    scoreContinuityDto.setTranSeq(String.valueOf(System.currentTimeMillis()));

                    Boolean signIn = userSignInLogService.checkSignIn(userId, Calendar.getInstance().getTime());
                    UserScoreSignDto scoreSign = userScoreService.getScoreSign(scoreContinuityDto);
                    if (signIn){
                        return ResultDtoFactory.toCustom("ALREADY_SIGN_IN", "已签到，请不要重复签到", scoreSign);
                    }

                    //签到
                    userSignInLogService.signIn(userId, Calendar.getInstance().getTime());
                    scoreSign = userScoreService.getScoreSign(scoreContinuityDto);

                    //添加积分
                    userScoreService.addScoreContinuity(scoreContinuityDto);

                    return ResultDtoFactory.toAck("签到成功", scoreSign);
                }else if (ymMemberDto == null){
                    return ResultDtoFactory.toNack("签到失败，云码用户信息不存在");
                }
            }
            return ResultDtoFactory.toNack("签到失败，云码不能为空");
        } catch (BizServiceException e) {
            LOGGER.error("签到失败", e);
            return ResultDtoFactory.toNack("签到失败");
        }
    }

    /**
     * 连续签到已签到
     * @param alreadySignInConDto
     * @return
     */
    @PostMapping(value = "/alreadySignInCon")
    public ResultDto alreadySignInCon(@RequestBody @Valid AlreadySignInConDto alreadySignInConDto, BindingResult result){
        try {
            if (result.hasErrors()){
                return ResultDtoFactory.toNack(result.getFieldError().getDefaultMessage());
            }

            String userId = SecurityContext.getCurrentUserId();

            ActivityDictDto activityDictDto = pointDictService.checkActivityExists(alreadySignInConDto.getActivityCode());
            if (activityDictDto == null){
                return ResultDtoFactory.toNack("该活动不存在");
            }

            ScoreDictDto pOne = pointDictService.checkScoreExists(alreadySignInConDto.getInitScoreCode());
            if (pOne == null){
                return ResultDtoFactory.toNack("积分信息不存在");
            }

            ScoreContinuityDto scoreContinuityDto = new ScoreContinuityDto();
            scoreContinuityDto.setContinuityDaysFixed(CONTINUITY_DAYS_FIXED);
            scoreContinuityDto.setSource(activityDictDto.getActivityName());
            scoreContinuityDto.setStart(pOne.getScoreValue());
            scoreContinuityDto.setStep(CONTINUITY_SCORE_STEP);
            scoreContinuityDto.setUserId(userId);
            scoreContinuityDto.setTranSeq(String.valueOf(System.currentTimeMillis()));

            Boolean signIn = userSignInLogService.checkSignIn(userId, Calendar.getInstance().getTime());
            UserScoreSignDto scoreSign = userScoreService.getScoreSign(scoreContinuityDto);
            if (signIn){
                return ResultDtoFactory.toAckData(scoreSign);
            }else {
                return ResultDtoFactory.toNack("签到信息不存在！");
            }
        } catch (BizServiceException e) {
            LOGGER.error("签到失败", e);
            return ResultDtoFactory.toNack("签到失败");
        }
    }

    /**
     * 获取当前服务器时间
     * @return
     */
    @GetMapping("/getServerTimestamp")
    public ResultDto getServerTimestamp(){
        return ResultDtoFactory.toAckData(Calendar.getInstance().getTime());
    }

}
