package com.xinyunlian.jinfu.security.service;

import com.xinyunlian.jinfu.common.converter.ConverterService;
import com.xinyunlian.jinfu.common.enums.EErrorCode;
import com.xinyunlian.jinfu.common.exception.BizServiceException;
import com.xinyunlian.jinfu.common.security.authc.SecurityService;
import com.xinyunlian.jinfu.common.security.authc.dto.UserInfo;
import com.xinyunlian.jinfu.common.util.EncryptUtil;
import com.xinyunlian.jinfu.common.util.JsonUtil;
import com.xinyunlian.jinfu.user.dao.MgtPermissionDao;
import com.xinyunlian.jinfu.user.dao.MgtRoleDao;
import com.xinyunlian.jinfu.user.dao.MgtUserDao;
import com.xinyunlian.jinfu.user.entity.MgtUserPo;
import com.xinyunlian.jinfu.user.enums.EMgtUserStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.*;

/**
 * Created by DongFC on 2016-08-30.
 */
@Service
public class MgtSecurityServiceImpl implements SecurityService {

    @Autowired
    private MgtUserDao mgtUserDao;

    @Autowired
    private MgtRoleDao mgtRoleDao;

    @Autowired
    private MgtPermissionDao mgtPermissionDao;

    private static final Logger LOGGER = LoggerFactory.getLogger(MgtSecurityServiceImpl.class);

    @Override
    public UserInfo getUserInfo(String loginId, String password) {

        UserInfo userInfo = null;

        try {
            //db case not sensitive makes me to code below
            MgtUserPo mgtUserPo = mgtUserDao.findByLoginId(loginId);
            if (mgtUserPo == null){
                return null;
            }

            String encryptPwd = EncryptUtil.encryptMd5(password, mgtUserPo.getLoginId());
            MgtUserPo po = mgtUserDao.findByLoginIdAndPasswordAndStatus(loginId, encryptPwd, EMgtUserStatus.NORMAL);
            if (po != null){
                userInfo = ConverterService.convert(po, UserInfo.class);
            }
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
            LOGGER.error("md5加密异常", e);
            throw new BizServiceException(EErrorCode.USER_PASSWORD_MD5_ERROR);
        }

        return userInfo;
    }

    @Override
    public UserInfo getRolePermission(UserInfo userInfo) {
        try {
            Set<String> roleCodes = getRoleCodes(userInfo.getUserId());
            userInfo.setRoleCodes(roleCodes);
            Set<String> permissionCodes = getPermissionCodes(userInfo.getUserId());
            userInfo.setPermissionCodes(permissionCodes);

            LOGGER.debug("mgtSecurityService：" + JsonUtil.toJson(userInfo));
        } catch (Exception e) {
            LOGGER.error("获取权限异常", e);
            throw new BizServiceException(EErrorCode.SYSTEM_USER_GET_PERM_ERROR);
        }

        return userInfo;
    }

    protected Set<String> getRoleCodes(String userId) throws Exception{
        Set<String> roleCodesSet = new LinkedHashSet<>();
        try {
            List<String> roleNamesList = mgtRoleDao.findRoleCodeByUser(userId);
            if (!CollectionUtils.isEmpty(roleNamesList)){
                roleCodesSet.addAll(roleNamesList);
            }
        } catch (Exception e){
            throw e;
        }
        return roleCodesSet;
    }

    protected Set<String> getPermissionCodes(String userId) throws Exception {
        Set<String> permissionsSet = new LinkedHashSet<>();
        try {
            List<String> permissionsList = mgtPermissionDao.findPermssionCodeByUser(userId);

            if (!CollectionUtils.isEmpty(permissionsList)){
                permissionsSet.addAll(permissionsList);
            }
        } catch (Exception e){
            throw e;
        }

        return permissionsSet;
    }

}
