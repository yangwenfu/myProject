package com.xinyunlian.jinfu.yunma.service;

import com.xinyunlian.jinfu.clerk.dao.ClerkApplyDao;
import com.xinyunlian.jinfu.clerk.dao.ClerkAuthDao;
import com.xinyunlian.jinfu.common.converter.ConverterService;
import com.xinyunlian.jinfu.common.enums.EErrorCode;
import com.xinyunlian.jinfu.common.exception.BizServiceException;
import com.xinyunlian.jinfu.yunma.dao.YMMemberDao;
import com.xinyunlian.jinfu.yunma.dao.YMUserInfoDao;
import com.xinyunlian.jinfu.yunma.dto.YMUserInfoDto;
import com.xinyunlian.jinfu.yunma.entity.YMUserInfoPo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by menglei on 2017-01-04.
 */
@Service(value = "yMUserInfoServiceImpl")
public class YMUserInfoServiceImpl implements YMUserInfoService {

    @Autowired
    private YMUserInfoDao yMUserInfoDao;
    @Autowired
    private YMMemberDao ymMemberDao;
    @Autowired
    private ClerkAuthDao clerkAuthDao;
    @Autowired
    private ClerkApplyDao clerkApplyDao;

    @Override
    @Transactional
    public YMUserInfoDto findByOpenId(String openId) {
        YMUserInfoPo po = yMUserInfoDao.findByOpenId(openId);
        if (po == null) {
            return null;
        }
        YMUserInfoDto dto = ConverterService.convert(po, YMUserInfoDto.class);
        return dto;
    }

    @Override
    @Transactional
    public YMUserInfoDto findByUserId(String userId) {
        YMUserInfoPo po = yMUserInfoDao.findByUserId(userId);
        if (po == null) {
            return null;
        }
        YMUserInfoDto dto = ConverterService.convert(po, YMUserInfoDto.class);
        return dto;
    }

    @Override
    @Transactional
    public YMUserInfoDto findByYmUserId(String ymUserId) {
        YMUserInfoPo po = yMUserInfoDao.findByYmUserId(ymUserId);
        if (po == null) {
            return null;
        }
        YMUserInfoDto dto = ConverterService.convert(po, YMUserInfoDto.class);
        return dto;
    }

    @Override
    @Transactional
    public YMUserInfoDto addUserInfo(YMUserInfoDto dto) throws BizServiceException {
        YMUserInfoPo po = ConverterService.convert(dto, YMUserInfoPo.class);
        yMUserInfoDao.save(po);
        dto.setYmUserId(po.getYmUserId());
        return dto;
    }

    @Override
    @Transactional
    public void updateUserInfo(YMUserInfoDto dto) throws BizServiceException {
        YMUserInfoPo po = yMUserInfoDao.findByYmUserId(dto.getYmUserId());
        if (po == null) {
            throw new BizServiceException(EErrorCode.USER_NOT_EXIST);
        }
        po.setUserId(dto.getUserId());
        yMUserInfoDao.save(po);
    }

    @Override
    @Transactional
    public void deleteByUserId(String userId) {
        clerkApplyDao.deleteByUserId(userId);
        clerkAuthDao.deleteByUserId(userId);
        ymMemberDao.deleteByUserId(userId);
        yMUserInfoDao.deleteByUserId(userId);
    }

}
