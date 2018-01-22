package com.xinyunlian.jinfu.dict.service;

import com.xinyunlian.jinfu.common.exception.BizServiceException;
import com.xinyunlian.jinfu.dict.dto.ActivityDictDto;
import com.xinyunlian.jinfu.dict.dto.ScoreDictDto;

/**
 * Created by dongfangchao on 2017/7/3/0003.
 */
public interface PointDictService {

    /**
     * 检查活动是否存在
     * @param activityCode
     * @return 存在，直接返回对应活动信息；不存在，返回null
     * @throws BizServiceException
     */
    ActivityDictDto checkActivityExists(String activityCode) throws BizServiceException;

    /**
     * 检查对应分数是否存在
     * @param scoreCode
     * @return 存在，直接返回对应积分信息；不存在，返回null
     * @throws BizServiceException
     */
    ScoreDictDto checkScoreExists(String scoreCode) throws BizServiceException;

}
