package com.xinyunlian.jinfu.user.service;

import com.xinyunlian.jinfu.common.converter.ConverterService;
import com.xinyunlian.jinfu.common.enums.EErrorCode;
import com.xinyunlian.jinfu.common.exception.BizServiceException;
import com.xinyunlian.jinfu.common.security.authc.SecurityService;
import com.xinyunlian.jinfu.common.security.authc.dto.UserInfo;
import com.xinyunlian.jinfu.common.util.EncryptUtil;
import com.xinyunlian.jinfu.user.dao.UserInfoDao;
import com.xinyunlian.jinfu.user.dto.UserInfoDto;
import com.xinyunlian.jinfu.user.entity.UserInfoPo;
import com.xinyunlian.jinfu.user.enums.EUserStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by JL on 2016/8/19.
 */
@Service
public class SecurityServiceImpl implements SecurityService {

    @Autowired
    private UserInfoDao userInfoDao;

    @Override
    public UserInfo getUserInfo(String loginId, String password) {
        String encryptPwd = "";
        try {
            encryptPwd = EncryptUtil.encryptMd5(password, password);
        } catch (Exception e) {
            throw new BizServiceException(EErrorCode.USER_PASSWORD_MD5_ERROR);
        }
        UserInfoPo userInfoPo = userInfoDao.findByMobileAndLoginPwdAndStatus(loginId, encryptPwd, EUserStatus.NORMAL);
        if (userInfoPo != null) {
            UserInfo userDto = ConverterService.convert(userInfoPo, UserInfo.class);
            UserInfoDto userInfoDto = ConverterService.convert(userInfoPo, UserInfoDto.class);
            Map<String, Object> paramMap = new HashMap<>();
            paramMap.put("userInfoDto", userInfoDto);
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
