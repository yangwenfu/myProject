package com.xinyunlian.jinfu.center.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xinyunlian.jinfu.area.dto.SysAreaInfDto;
import com.xinyunlian.jinfu.area.service.SysAreaInfService;
import com.xinyunlian.jinfu.center.dto.CenterUserDto;
import com.xinyunlian.jinfu.mq.DestinationDefine;
import com.xinyunlian.jinfu.user.dao.UserDao;
import com.xinyunlian.jinfu.user.entity.UserInfoPo;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by King on 2017/5/11.
 */
@Service
public class CenterUserServiceImpl implements CenterUserService{
    private static Logger LOGGER = LoggerFactory.getLogger(CenterUserService.class);

    @Autowired
    private CenterClientService centerClientService;
    @Autowired
    private UserDao userDao;
    @Autowired
    private SysAreaInfService sysAreaInfService;

    @Override
    public CenterUserDto findByUuid(String uuid) {
        return null;
    }

    @Override
    @Transactional
    @JmsListener(destination = DestinationDefine.ADD_USER_TO_CENTER)
    public void addUserFromMQ(String centerUser) {
        try {
            CenterUserDto centerUserDto = JSONObject.parseObject(centerUser, CenterUserDto.class);

            if(centerUserDto.getAreaId() != null) {
                SysAreaInfDto sysAreaInfDto = sysAreaInfService.getSysAreaInfById(centerUserDto.getAreaId());
                centerUserDto.setAreaGbCode(sysAreaInfDto.getGbCode());
            }
            if(centerUserDto.getStreetId() != null) {
                SysAreaInfDto sysAreaInfDto = sysAreaInfService.getSysAreaInfById(centerUserDto.getStreetId());
                centerUserDto.setStreetGbCode(sysAreaInfDto.getGbCode());
            }

            String req = JSON.toJSONString(centerUserDto);
            LOGGER.info("request body ;{}", req);
            if(!StringUtils.isEmpty(centerUserDto.getUuid())) {
                String ret = centerClientService.put("user/" + centerUserDto.getUuid(), req);
                LOGGER.info("repose ;{}", ret);
            }else{
                String ret = centerClientService.post("user", req);
                LOGGER.info("repose ;{}", ret);

                JSONObject jsonObject = JSONObject.parseObject(ret);
                if ("1".equals(jsonObject.getString("status"))) {
                    UserInfoPo userInfoPo = userDao.findOne(centerUserDto.getUserId());
                    userInfoPo.setUuid(jsonObject.getString("data"));
                    userDao.save(userInfoPo);
                }
            }
        }catch (Exception e) {
            LOGGER.error("推送用户新增数据出错",e);
            throw e;
        }
    }

}
