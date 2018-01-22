package com.xinyunlian.jinfu.point.service;

import com.xinyunlian.jinfu.common.exception.BizServiceException;
import com.xinyunlian.jinfu.point.dto.ScoreContinuityDto;
import com.xinyunlian.jinfu.point.dto.UserScoreChangelogDto;
import com.xinyunlian.jinfu.point.dto.UserScoreSignDto;
import com.xinyunlian.jinfu.point.dto.UserScoreboardDto;

/**
 * Created by dongfangchao on 2017/5/19/0019.
 */
public interface UserScoreService {

    /**
     * 新增积分变动记录
     * @param scoreChangelogDto
     * @throws BizServiceException
     */
    void addScore(UserScoreChangelogDto scoreChangelogDto) throws BizServiceException;

    /**
     * 获取用户积分
     * @param userId
     * @return
     * @throws BizServiceException
     */
    UserScoreboardDto getUserScoreByUserId(String userId) throws BizServiceException;

    /**
     * 新增积分变动记录消费者
     * @param json
     */
    void addMqScore(String json);

    /**
     * 根据连续签到的天数计算增加的积分
     * @param scoreContinuityDto
     * @throws BizServiceException
     */
    void addScoreContinuity(ScoreContinuityDto scoreContinuityDto) throws BizServiceException;

    /**
     * 获取已签到信息
     * @param scoreContinuityDto
     * @return
     * @throws BizServiceException
     */
    UserScoreSignDto getScoreSign(ScoreContinuityDto scoreContinuityDto) throws BizServiceException;
}
