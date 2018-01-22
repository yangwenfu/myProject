package com.xinyunlian.jinfu.dict.service;

import com.xinyunlian.jinfu.common.converter.ConverterService;
import com.xinyunlian.jinfu.common.exception.BizServiceException;
import com.xinyunlian.jinfu.dict.dao.ActivityDictDao;
import com.xinyunlian.jinfu.dict.dao.ScoreDictDao;
import com.xinyunlian.jinfu.dict.dto.ActivityDictDto;
import com.xinyunlian.jinfu.dict.dto.ScoreDictDto;
import com.xinyunlian.jinfu.dict.entity.ActivityDictPo;
import com.xinyunlian.jinfu.dict.entity.ScoreDictPo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by dongfangchao on 2017/7/3/0003.
 */
@Service
public class PointDictServiceImpl implements PointDictService {

    @Autowired
    private ActivityDictDao activityDictDao;

    @Autowired
    private ScoreDictDao scoreDictDao;

    @Override
    public ActivityDictDto checkActivityExists(String activityCode) throws BizServiceException {
        ActivityDictPo activityDictPo = activityDictDao.findByActivityCode(activityCode);
        return ConverterService.convert(activityDictPo, ActivityDictDto.class);
    }

    @Override
    public ScoreDictDto checkScoreExists(String scoreCode) throws BizServiceException {
        ScoreDictPo scoreDictPo = scoreDictDao.findByScoreCode(scoreCode);
        return ConverterService.convert(scoreDictPo, ScoreDictDto.class);
    }
}
