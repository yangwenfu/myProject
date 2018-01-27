package com.xinyunlian.jinfu.user.service;

import com.xinyunlian.jinfu.common.converter.ConverterService;
import com.xinyunlian.jinfu.common.enums.EErrorCode;
import com.xinyunlian.jinfu.common.exception.BizServiceException;
import com.xinyunlian.jinfu.common.security.authc.SecurityService;
import com.xinyunlian.jinfu.common.security.authc.dto.UserInfo;
import com.xinyunlian.jinfu.common.util.EncryptUtil;
import com.xinyunlian.jinfu.dealer.dto.DealerDto;
import com.xinyunlian.jinfu.user.dao.DealerUserDao;
import com.xinyunlian.jinfu.user.dto.DealerUserDto;
import com.xinyunlian.jinfu.user.entity.DealerUserPo;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by JL on 2016/8/25.
 */
@Service
public class CustomerSecurityServiceImpl implements SecurityService{

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerSecurityServiceImpl.class);

    @Autowired
    private DealerUserDao dealerUserDao;

    @Override
    @Transactional(readOnly = true)
    public UserInfo getUserInfo(String mobile, String password) {
        String encryptPwd = StringUtils.EMPTY;
        try {
            encryptPwd = EncryptUtil.encryptMd5(password, password);
        } catch (Exception e) {
            LOGGER.error("MD5加密失败", e);
            throw new BizServiceException(EErrorCode.DEALER_USER_PASSWORD_ENCRYPT_ERROR);
        }
        DealerUserPo dealerUserPo = dealerUserDao.findByMobileAndPassword(mobile, encryptPwd);
        if (dealerUserPo != null) {
            UserInfo userDto = ConverterService.convert(dealerUserPo, UserInfo.class);
            DealerUserDto dealerUserDto = ConverterService.convert(dealerUserPo, DealerUserDto.class);
            //dealerUserDto.setExamPassed(true);//这里暂时将分销员考试全设通过
            if (dealerUserPo.getDealerPo() != null) {
                DealerDto dealerDto = ConverterService.convert(dealerUserPo.getDealerPo(), DealerDto.class);
                dealerUserDto.setDealerDto(dealerDto);
            }
            Map<String, Object> paramMap = new HashMap<>();
            paramMap.put("dealerUserDto", dealerUserDto);
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
