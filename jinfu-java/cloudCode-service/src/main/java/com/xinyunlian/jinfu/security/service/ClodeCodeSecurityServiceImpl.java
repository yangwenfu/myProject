package com.xinyunlian.jinfu.security.service;

import com.xinyunlian.jinfu.common.converter.ConverterService;
import com.xinyunlian.jinfu.common.security.authc.SecurityService;
import com.xinyunlian.jinfu.common.security.authc.dto.UserInfo;
import com.xinyunlian.jinfu.yunma.dao.YMUserInfoDao;
import com.xinyunlian.jinfu.yunma.dto.YMUserInfoDto;
import com.xinyunlian.jinfu.yunma.entity.YMUserInfoPo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by menglei on 2017/01/14.
 */
@Service
public class ClodeCodeSecurityServiceImpl implements SecurityService {

    @Autowired
    private YMUserInfoDao yMUserInfoDao;

    @Override
    public UserInfo getUserInfo(String ymUserId, String openId) {
        YMUserInfoPo yMUserInfoPo = yMUserInfoDao.findByYmUserIdAndOpenId(ymUserId, openId);
        if (yMUserInfoPo != null) {
            UserInfo userDto = ConverterService.convert(yMUserInfoPo, UserInfo.class);
            userDto.setUserId(yMUserInfoPo.getYmUserId());
            YMUserInfoDto yMUserInfoDto = ConverterService.convert(yMUserInfoPo, YMUserInfoDto.class);
            Map<String, Object> paramMap = new HashMap<>();
            paramMap.put("ymUserInfoDto", yMUserInfoDto);
            userDto.setParamMap(paramMap);
            return userDto;
        }
        return null;
    }

    @Override
    public UserInfo getRolePermission(UserInfo userInfo) {
        return userInfo;
    }
}
