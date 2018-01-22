package com.xinyunlian.jinfu;

import com.xinyunlian.jinfu.common.util.DateHelper;
import com.xinyunlian.jinfu.common.util.JsonUtil;
import com.xinyunlian.jinfu.dict.dto.ActivityDictDto;
import com.xinyunlian.jinfu.dict.dto.ScoreDictDto;
import com.xinyunlian.jinfu.dict.service.PointDictService;
import com.xinyunlian.jinfu.mq.DestinationDefine;
import com.xinyunlian.jinfu.point.dao.UserScoreChangelogDao;
import com.xinyunlian.jinfu.point.dto.ScoreContinuityDto;
import com.xinyunlian.jinfu.point.dto.UserScoreSignDto;
import com.xinyunlian.jinfu.point.entity.UserScoreChangelogPo;
import com.xinyunlian.jinfu.point.service.UserScoreService;
import com.xinyunlian.jinfu.sign.service.UserSignInLogService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by dongfangchao on 2017/7/3/0003.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:standard-code-dao-test.xml"})
public class PointServiceTest {

    @Autowired
    private PointDictService pointDictService;

    @Autowired
    private UserSignInLogService userSignInLogService;

    @Autowired
    private UserScoreService userScoreService;

    @Autowired
    private UserScoreChangelogDao userScoreChangelogDao;

    private String activityCode;

    private String scoreCode;

    private String userId;

    private Integer CONTINUITY_DAYS_FIXED;

    private Long CONTINUITY_SCORE_STEP;

    @Autowired
    private JmsMessagingTemplate jmsMessagingTemplate;

    @Before
    public void init(){
        activityCode = "CON_SEVEN_DAYS_SIGN_IN";
        scoreCode="P_ONE";
        userId = "UC0000001560";
        CONTINUITY_DAYS_FIXED = 7;
        CONTINUITY_SCORE_STEP = 1l;
    }

    @Test
    public void signInContinuity(){
        ActivityDictDto activityDictDto = pointDictService.checkActivityExists(activityCode);
        if (activityDictDto == null){
            System.out.println("该活动不存在");
            return;
        }

        ScoreDictDto pOne = pointDictService.checkScoreExists(scoreCode);
        if (pOne == null){
            System.out.println("积分信息不存在");
            return;
        }

        ScoreContinuityDto scoreContinuityDto = new ScoreContinuityDto();
        scoreContinuityDto.setContinuityDaysFixed(CONTINUITY_DAYS_FIXED);
        scoreContinuityDto.setSource(activityDictDto.getActivityName());
        scoreContinuityDto.setStart(pOne.getScoreValue());
        scoreContinuityDto.setStep(CONTINUITY_SCORE_STEP);
        scoreContinuityDto.setUserId(userId);
        scoreContinuityDto.setTranSeq(String.valueOf(System.currentTimeMillis()));

        Boolean signIn = userSignInLogService.checkSignIn(userId, Calendar.getInstance().getTime());
        UserScoreSignDto scoreSign = null;
        if (signIn){
            scoreSign = userScoreService.getScoreSign(scoreContinuityDto);
            System.out.println("已签到，请不要重复签到");
            System.out.println(JsonUtil.toJson(scoreSign));
            return;
        }

        //签到
        userSignInLogService.signIn(userId, Calendar.getInstance().getTime());
        scoreSign = userScoreService.getScoreSign(scoreContinuityDto);

        //添加积分
        userScoreService.addScoreContinuity(scoreContinuityDto);

        System.out.println(JsonUtil.toJson(scoreSign));
    }

    @Test
    public void findContinuityDays(){

        Date endDate = DateHelper.getStartDate(Calendar.getInstance().getTime());
        Date startDate = DateHelper.add(endDate, Calendar.DATE, CONTINUITY_DAYS_FIXED * (-1));
        startDate = DateHelper.getStartDate(startDate);

        List<UserScoreChangelogPo> continuityDays = userScoreChangelogDao.findContinuityDays(endDate, startDate, "连续七日签到");
        System.out.println(JsonUtil.toJson(continuityDays));
    }

    @Test
    public void getScoreSign(){
        ScoreContinuityDto scoreContinuityDto = new ScoreContinuityDto();
        scoreContinuityDto.setContinuityDaysFixed(CONTINUITY_DAYS_FIXED);
        scoreContinuityDto.setSource("连续七日签到");
        scoreContinuityDto.setStart(1l);
        scoreContinuityDto.setStep(CONTINUITY_SCORE_STEP);
        scoreContinuityDto.setUserId(userId);
        scoreContinuityDto.setTranSeq(String.valueOf(System.currentTimeMillis()));
        UserScoreSignDto scoreSign = userScoreService.getScoreSign(scoreContinuityDto);
        System.out.println(JsonUtil.toJson(scoreSign));
    }

    @Test
    public void sendMsg(){
        Map<String, String> jsonMap = new HashMap<>();
        jsonMap.put("userId", "121342134");
        jsonMap.put("txSeq", String.valueOf(System.currentTimeMillis()));
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyyMMdd");
        jsonMap.put("signDate", sdf2.format(Calendar.getInstance().getTime()));
        String json = JsonUtil.toJson(jsonMap);
        jmsMessagingTemplate.convertAndSend(DestinationDefine.SIGN_IN_FULL_NOTIFY, json);
    }

}



