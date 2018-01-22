package com.xinyunlian.jinfu.point.service;

import com.xinyunlian.jinfu.common.converter.ConverterService;
import com.xinyunlian.jinfu.common.enums.EErrorCode;
import com.xinyunlian.jinfu.common.exception.BizServiceException;
import com.xinyunlian.jinfu.common.util.JsonUtil;
import com.xinyunlian.jinfu.dict.dao.ActivityDictDao;
import com.xinyunlian.jinfu.dict.dao.ScoreDictDao;
import com.xinyunlian.jinfu.dict.entity.ActivityDictPo;
import com.xinyunlian.jinfu.dict.entity.ScoreDictPo;
import com.xinyunlian.jinfu.mq.DestinationDefine;
import com.xinyunlian.jinfu.point.dao.UserScoreChangelogDao;
import com.xinyunlian.jinfu.point.dao.UserScoreboardDao;
import com.xinyunlian.jinfu.point.dto.ScoreContinuityDto;
import com.xinyunlian.jinfu.point.dto.UserScoreChangelogDto;
import com.xinyunlian.jinfu.point.dto.UserScoreSignDto;
import com.xinyunlian.jinfu.point.dto.UserScoreboardDto;
import com.xinyunlian.jinfu.point.entity.UserScoreChangelogPo;
import com.xinyunlian.jinfu.point.entity.UserScoreboardPo;
import com.xinyunlian.jinfu.sign.dao.UserSignInLogDao;
import com.xinyunlian.jinfu.sign.entity.UserSignInLogPo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by dongfangchao on 2017/5/19/0019.
 */
@Service
public class UserScoreServiceImpl implements UserScoreService {

    @Autowired
    private UserScoreboardDao userScoreboardDao;

    @Autowired
    private UserScoreChangelogDao userScoreChangelogDao;

    @Autowired
    private ActivityDictDao activityDictDao;

    @Autowired
    private ScoreDictDao scoreDictDao;

    @Autowired
    private JmsMessagingTemplate jmsMessagingTemplate;

    @Autowired
    private UserSignInLogDao userSignInLogDao;

    private static final Logger LOGGER = LoggerFactory.getLogger(UserScoreServiceImpl.class);

    @Override
    @Transactional
    public void addScore(UserScoreChangelogDto changeDto) throws BizServiceException {

        ActivityDictPo activityDictPo = activityDictDao.findByActivityCode(changeDto.getActivityCode());
        if (activityDictPo == null){
            throw new BizServiceException(EErrorCode.POINT_ACTIVITY_NOT_EXISTS);
        }

        ScoreDictPo scoreCode = scoreDictDao.findByScoreCode(changeDto.getChangedScoreCode());
        if (scoreCode == null){
            throw new BizServiceException(EErrorCode.POINT_CODE_NOT_EXISTS);
        }

        //判断是否重复插入
        UserScoreChangelogPo userScoreChangelogPo =
                userScoreChangelogDao.findByUserIdAndSourceAndTranSeq(changeDto.getUserId(), activityDictPo.getActivityName(), changeDto.getTranSeq());
        if (userScoreChangelogPo != null){
            throw new BizServiceException(EErrorCode.COMM_INTERNAL_ERROR);
        }

        //新增积分变化日志表记录
        UserScoreChangelogPo po = new UserScoreChangelogPo();
        po.setUserId(changeDto.getUserId());
        po.setChangedScore(scoreCode.getScoreValue());
        po.setSource(activityDictPo.getActivityName());
        po.setTranSeq(changeDto.getTranSeq());
        userScoreChangelogDao.save(po);

        //修改积分表用户积分
        UserScoreboardPo userScoreboardPo = userScoreboardDao.findByUserId(changeDto.getUserId());
        if (userScoreboardPo == null){
            UserScoreboardPo tmpScoreboardPo = new UserScoreboardPo();
            tmpScoreboardPo.setUserId(changeDto.getUserId());
            tmpScoreboardPo.setTotalScore(scoreCode.getScoreValue());
            userScoreboardDao.save(tmpScoreboardPo);
        }else {
            long totalScore = userScoreboardPo.getTotalScore()==null?0l:userScoreboardPo.getTotalScore();
            userScoreboardPo.setTotalScore(totalScore + scoreCode.getScoreValue());
        }

    }

    @Override
    public UserScoreboardDto getUserScoreByUserId(String userId) throws BizServiceException {
        UserScoreboardPo po = userScoreboardDao.findByUserId(userId);
        return ConverterService.convert(po, UserScoreboardDto.class);
    }

    @Transactional
    @JmsListener(destination = DestinationDefine.YLFIN_POINT_NOTIFY)
    public void addMqScore(String json){
        if (LOGGER.isDebugEnabled()){
            LOGGER.debug("积分通知消息：{}", json);
        }
        try {
            UserScoreChangelogDto dto = JsonUtil.toObject(UserScoreChangelogDto.class, json);
            addScore(dto);
        } catch (Exception e) {
            LOGGER.error("积分变化处理失败", e);
            throw e;
        }
    }

    @Override
    @Transactional
    public void addScoreContinuity(ScoreContinuityDto scoreContinuityDto) throws BizServiceException {

        //根据连续签到的日期，计算要增加的积分
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        UserSignInLogPo signInDate =
                userSignInLogDao.findByUserIdAndSignInDate(scoreContinuityDto.getUserId(), sdf.format(Calendar.getInstance().getTime()));

        Long changedScore = 0l;
        if (signInDate == null){
            throw new BizServiceException(EErrorCode.SIGN_IN_INFO_MISSING);
        }else {
            if (signInDate.getConDays()%scoreContinuityDto.getContinuityDaysFixed() == 0){//签到次数已满，有抽奖机会，推消息给大转盘
                Map<String, String> jsonMap = new HashMap<>();
                jsonMap.put("userId", scoreContinuityDto.getUserId());
                jsonMap.put("txSeq", scoreContinuityDto.getTranSeq());
                SimpleDateFormat sdf2 = new SimpleDateFormat("yyyyMMdd");
                jsonMap.put("signDate", sdf2.format(Calendar.getInstance().getTime()));
                String json = JsonUtil.toJson(jsonMap);
                jmsMessagingTemplate.convertAndSend(DestinationDefine.SIGN_IN_FULL_NOTIFY, json);
                changedScore = scoreContinuityDto.getStart()
                        + scoreContinuityDto.getStep() * (scoreContinuityDto.getContinuityDaysFixed() - 1);
            }else {
                changedScore = scoreContinuityDto.getStart()
                        + scoreContinuityDto.getStep() * (signInDate.getConDays()%scoreContinuityDto.getContinuityDaysFixed() - 1);
            }
        }

        //判断是否重复插入
        UserScoreChangelogPo userScoreChangelogPo =
                userScoreChangelogDao.findByUserIdAndSourceAndTranSeq(scoreContinuityDto.getUserId(), scoreContinuityDto.getSource(), scoreContinuityDto.getTranSeq());
        if (userScoreChangelogPo != null){
            throw new BizServiceException(EErrorCode.POINT_DUPLICATE_RECORD_INSERT);
        }

        //新增积分变化日志表记录
        UserScoreChangelogPo po = new UserScoreChangelogPo();
        po.setUserId(scoreContinuityDto.getUserId());
        po.setChangedScore(changedScore);
        po.setSource(scoreContinuityDto.getSource());
        po.setTranSeq(scoreContinuityDto.getTranSeq());
        userScoreChangelogDao.save(po);

        //修改积分表用户积分
        UserScoreboardPo userScoreboardPo = userScoreboardDao.findByUserId(scoreContinuityDto.getUserId());
        if (userScoreboardPo == null){
            UserScoreboardPo tmpScoreboardPo = new UserScoreboardPo();
            tmpScoreboardPo.setUserId(scoreContinuityDto.getUserId());
            tmpScoreboardPo.setTotalScore(changedScore);
            userScoreboardDao.save(tmpScoreboardPo);
        }else {
            long totalScore = userScoreboardPo.getTotalScore()==null?0l:userScoreboardPo.getTotalScore();
            userScoreboardPo.setTotalScore(totalScore + changedScore);
        }
    }

    @Override
    public UserScoreSignDto getScoreSign(ScoreContinuityDto scoreContinuityDto) throws BizServiceException {
        //根据连续签到的日期，计算要增加的积分
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        UserSignInLogPo signInDate =
                userSignInLogDao.findByUserIdAndSignInDate(scoreContinuityDto.getUserId(), sdf.format(Calendar.getInstance().getTime()));

        Long continuityDays = 0l;
        if (signInDate != null && signInDate.getConDays() != null){
            continuityDays = signInDate.getConDays();
        }
        Long remainder = continuityDays % scoreContinuityDto.getContinuityDaysFixed();
        Long continuityDaysOfCycle = remainder == 0?scoreContinuityDto.getContinuityDaysFixed():remainder;

        //计算明天可以获得的积分
        Long days = (continuityDaysOfCycle + 1)%scoreContinuityDto.getContinuityDaysFixed();
        days = days==0?scoreContinuityDto.getContinuityDaysFixed():days;
        Long tomorrowScore = scoreContinuityDto.getStart() + scoreContinuityDto.getStep() * (days - 1);

        UserScoreSignDto userScoreSignDto = new UserScoreSignDto();
        userScoreSignDto.setContinuityDays(continuityDays);
        userScoreSignDto.setContinuityDaysOfCycle(continuityDaysOfCycle);
        userScoreSignDto.setTomorrowScore(tomorrowScore);
        return userScoreSignDto;
    }
}
