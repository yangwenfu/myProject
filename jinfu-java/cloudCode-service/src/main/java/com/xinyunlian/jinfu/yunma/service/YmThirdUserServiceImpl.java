package com.xinyunlian.jinfu.yunma.service;

import com.xinyunlian.jinfu.common.converter.ConverterService;
import com.xinyunlian.jinfu.common.enums.EErrorCode;
import com.xinyunlian.jinfu.common.exception.BizServiceException;
import com.xinyunlian.jinfu.yunma.dao.YmThirdUserDao;
import com.xinyunlian.jinfu.yunma.dto.YmThirdUserDto;
import com.xinyunlian.jinfu.yunma.entity.YmThirdUserPo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by menglei on 2017-05-31.
 */
@Service
public class YmThirdUserServiceImpl implements YmThirdUserService {

    @Autowired
    private YmThirdUserDao ymThirdUserDao;

    @Override
    @Transactional
    public YmThirdUserDto findByUserId(String userId) {
        YmThirdUserPo po = ymThirdUserDao.findByUserId(userId);
        if (po == null) {
            return null;
        }
        YmThirdUserDto dto = ConverterService.convert(po, YmThirdUserDto.class);
        return dto;
    }

    @Override
    @Transactional
    public YmThirdUserDto findByMemberIdAndThirdConfigId(Long memberId, Long thirdConfigId) {
        YmThirdUserPo po = ymThirdUserDao.findByMemberIdAndThirdConfigId(memberId, thirdConfigId);
        if (po == null) {
            return null;
        }
        YmThirdUserDto dto = ConverterService.convert(po, YmThirdUserDto.class);
        return dto;
    }

    @Override
    @Transactional
    public YmThirdUserDto findByMemberId(Long memberId) {
        YmThirdUserPo po = ymThirdUserDao.findByMemberId(memberId);
        if (po == null) {
            return null;
        }
        YmThirdUserDto dto = ConverterService.convert(po, YmThirdUserDto.class);
        return dto;
    }

    @Transactional
    @Override
    public void save(YmThirdUserDto ymThirdUserDto) throws BizServiceException {
        if (ymThirdUserDto != null) {
            YmThirdUserPo po = ymThirdUserDao.findByUserId(ymThirdUserDto.getUserId());
            if (po != null) {
                throw new BizServiceException(EErrorCode.SYSTEM_ID_IS_EXIST, "用户已存在");
            }
            YmThirdUserPo ymThirdUserPo = ConverterService.convert(ymThirdUserDto, YmThirdUserPo.class);
            ymThirdUserDao.save(ymThirdUserPo);
        }
    }

    @Transactional
    @Override
    public void updateMemberId(YmThirdUserDto ymThirdUserDto) throws BizServiceException {
        if (ymThirdUserDto != null) {
            YmThirdUserPo po = ymThirdUserDao.findOne(ymThirdUserDto.getId());
            if (po != null) {
                po.setMemberId(ymThirdUserDto.getMemberId());
                ymThirdUserDao.save(po);
            }
        }
    }

}
