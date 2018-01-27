package com.xinyunlian.jinfu.user.service;

import com.alibaba.fastjson.JSON;
import com.xinyunlian.jinfu.center.dto.CenterUserDto;
import com.xinyunlian.jinfu.common.converter.ConverterService;
import com.xinyunlian.jinfu.common.security.StaticResourceSecurity;
import com.xinyunlian.jinfu.common.util.AppConfigUtil;
import com.xinyunlian.jinfu.common.util.DateHelper;
import com.xinyunlian.jinfu.mq.DestinationDefine;
import com.xinyunlian.jinfu.mq.sender.QueueSender;
import com.xinyunlian.jinfu.picture.dao.PictureDao;
import com.xinyunlian.jinfu.picture.entity.PicturePo;
import com.xinyunlian.jinfu.picture.service.InnerPictureService;
import com.xinyunlian.jinfu.user.dao.UserDao;
import com.xinyunlian.jinfu.user.dao.UserExtDao;
import com.xinyunlian.jinfu.user.dto.UserExtDto;
import com.xinyunlian.jinfu.user.dto.ext.UserExtBaseDto;
import com.xinyunlian.jinfu.user.dto.ext.UserExtIdDto;
import com.xinyunlian.jinfu.user.entity.UserExtPo;
import com.xinyunlian.jinfu.user.entity.UserInfoPo;
import org.eclipse.jetty.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by jll on 2016/9/6.
 */
@Service
public class UserExtServiceImpl implements UserExtService {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserExtServiceImpl.class);
    @Autowired
    private UserExtDao userExtDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private PictureDao pictureDao;

    @Autowired
    private InnerPictureService pictureService;

    @Autowired
    private QueueSender queueSender;

    @Override
    @Transactional
    public void saveUserExt(UserExtDto userExtDto) {
        UserExtPo userExtPo = userExtDao.findOne(userExtDto.getUserId());
        if (userExtPo == null) {
            userExtPo = new UserExtPo();
        }

        userExtPo.setUserId(userExtDto.getUserId());

        if(StringUtil.isNotBlank(userExtDto.getProvince())){
            userExtPo.setProvince(userExtDto.getProvince());
        }

        if(StringUtil.isNotBlank(userExtDto.getCity())){
            userExtPo.setCity(userExtDto.getCity());
        }

        if(StringUtil.isNotBlank(userExtDto.getArea())){
            userExtPo.setArea(userExtDto.getArea());
        }

        if(StringUtil.isNotBlank(userExtDto.getStreet())){
            userExtPo.setStreet(userExtDto.getStreet());
        }

        if(StringUtil.isNotBlank(userExtDto.getAddress())){
            userExtPo.setAddress(userExtDto.getAddress());
        }

        if(StringUtil.isNotBlank(userExtDto.getPhone())){
            userExtPo.setPhone(userExtDto.getPhone());
        }

        if(null != userExtDto.getMarryStatus()){
            userExtPo.setMarryStatus(userExtDto.getMarryStatus());
        }

        if(null != userExtDto.getHasHouse()){
            userExtPo.setHasHouse(userExtDto.getHasHouse());
        }

        if(null != userExtDto.getHasOutHouse()){
            userExtPo.setHasOutHouse(userExtDto.getHasOutHouse());
        }

        if(null != userExtDto.getLived()){
            userExtPo.setLived(userExtDto.getLived());
        }

        if(null != userExtDto.getTobaccoAuth()){
            userExtPo.setTobaccoAuth(userExtDto.getTobaccoAuth());
        }

        if(null != userExtDto.getYituPass()){
            userExtPo.setYituPass(userExtDto.getYituPass());
        }

        if(null != userExtDto.getYituSimilarity()){
            userExtPo.setYituSimilarity(userExtDto.getYituSimilarity());
        }

        if(null != userExtDto.getHouseProperty()){
            userExtPo.setHouseProperty(userExtDto.getHouseProperty());
        }
        if(null != userExtDto.getProvinceId()){
            userExtPo.setProvinceId(userExtDto.getProvinceId());
        }

        if(null != userExtDto.getCityId()){
            userExtPo.setCityId(userExtDto.getCityId());
        }

        if(null != userExtDto.getAreaId()){
            userExtPo.setAreaId(userExtDto.getAreaId());
        }

        if(null != userExtDto.getStreetId()){
            userExtPo.setStreetId(userExtDto.getStreetId());
        }

        userExtDao.save(userExtPo);
        UserInfoPo userInfoPo = userDao.findOne(userExtDto.getUserId());
        this.updateUserToCenter(userExtPo,userInfoPo);
    }

    @Override
    public UserExtDto findUserByUserId(String userId) {
        UserExtDto userExtDto = new UserExtDto();
        UserInfoPo userInfoPo = userDao.findOne(userId);
        userExtDto.setUserName(userInfoPo.getUserName());
        userExtDto.setIdCardNo(userInfoPo.getIdCardNo());
        userExtDto.setIdentityAuth(userInfoPo.getIdentityAuth());
        UserExtPo userExtPo = userExtDao.findOne(userId);
        if (userExtPo != null) {
            ConverterService.convert(userExtPo, userExtDto);
        }
        List<PicturePo> picturePos = pictureDao.findByParentId(userId);
        if (picturePos != null) {
            for (PicturePo p :
                    picturePos) {
                switch (p.getPictureType()) {
                    case CARD_FRONT:
                        userExtDto.setIdCardFrontPicId(p.getPictureId());
                        userExtDto.setIdCardFrontPic(AppConfigUtil.getFilePath() + StaticResourceSecurity.getSecurityURI(p.getPicturePath()));
                        break;
                    case CARD_BACK:
                        userExtDto.setIdCardBackPicId(p.getPictureId());
                        userExtDto.setIdCardBackPic(AppConfigUtil.getFilePath() + StaticResourceSecurity.getSecurityURI(p.getPicturePath()));
                        break;
                    case HELD_ID_CARD:
                        userExtDto.setHeldIdCardPicId(p.getPictureId());
                        userExtDto.setHeldIdCardPic(AppConfigUtil.getFilePath() + StaticResourceSecurity.getSecurityURI(p.getPicturePath()));
                        break;
                    case HOUSE_CERTIFICATE:
                        userExtDto.setHouseCertificatePicId(p.getPictureId());
                        userExtDto.setHouseCertificatePic(AppConfigUtil.getFilePath() + StaticResourceSecurity.getSecurityURI(p.getPicturePath()));
                        break;
                    case MARRY_CERTIFICATE:
                        userExtDto.setMarryCertificatePicId(p.getPictureId());
                        userExtDto.setMarryCertificatePic(AppConfigUtil.getFilePath() + StaticResourceSecurity.getSecurityURI(p.getPicturePath()));
                        break;
                    case RESIDENCE_BOOKLET:
                        userExtDto.setResidenceBookletPicId(p.getPictureId());
                        userExtDto.setResidenceBookletPic(AppConfigUtil.getFilePath() + StaticResourceSecurity.getSecurityURI(p.getPicturePath()));
                        break;
                    case HOUSEHOLDER_BOOKLET:
                        userExtDto.setHouseholderBookletPicId(p.getPictureId());
                        userExtDto.setHouseholderBookletPic(AppConfigUtil.getFilePath() + StaticResourceSecurity.getSecurityURI(p.getPicturePath()));
                        break;
                }

            }
        }
        return userExtDto;
    }

    @Override
    @Transactional
    public void saveUserExtPart(UserExtIdDto extIdDto) {
        UserExtPo userExtPo = userExtDao.findOne(extIdDto.getUserId());
        if (userExtPo == null) {
            userExtPo = new UserExtPo();
        }
        ConverterService.convert(extIdDto,userExtPo);
        userExtDao.save(userExtPo);

        UserInfoPo userInfoPo = userDao.findOne(userExtPo.getUserId());
        this.updateUserToCenter(userExtPo,userInfoPo);
    }

    @Override
    public UserExtIdDto getUserExtPart(UserExtIdDto extIdDto) {
        UserInfoPo userInfoPo = userDao.findOne(extIdDto.getUserId());
        UserExtPo userExtPo = userExtDao.findOne(extIdDto.getUserId());

        ConverterService.convert(userExtPo, extIdDto);
        if(extIdDto instanceof UserExtBaseDto) {
            UserExtBaseDto userExtBaseDto = (UserExtBaseDto)extIdDto;
            userExtBaseDto.setUserName(userInfoPo.getUserName());
            userExtBaseDto.setIdCardNo(userInfoPo.getIdCardNo());
            userExtBaseDto.setIdentityAuthDate(userInfoPo.getIdentityAuthDate());
            userExtBaseDto.setMobile(userInfoPo.getMobile());

            List<PicturePo> picturePos = pictureDao.findByParentId(extIdDto.getUserId());
            if (picturePos != null) {
                for (PicturePo p :
                        picturePos) {
                    switch (p.getPictureType()) {
                        case CARD_FRONT:
                            userExtBaseDto.setIdCardFrontPicId(p.getPictureId());
                            userExtBaseDto.setIdCardFrontPic(AppConfigUtil.getFilePath() + StaticResourceSecurity.getSecurityURI(p.getPicturePath()));
                            break;
                        case CARD_BACK:
                            userExtBaseDto.setIdCardBackPicId(p.getPictureId());
                            userExtBaseDto.setIdCardBackPic(AppConfigUtil.getFilePath() + StaticResourceSecurity.getSecurityURI(p.getPicturePath()));
                            break;
                        case HELD_ID_CARD:
                            userExtBaseDto.setHeldIdCardPicId(p.getPictureId());
                            userExtBaseDto.setHeldIdCardPic(AppConfigUtil.getFilePath() + StaticResourceSecurity.getSecurityURI(p.getPicturePath()));
                            break;
                        case HOUSE_CERTIFICATE:
                            userExtBaseDto.setHouseCertificatePicId(p.getPictureId());
                            userExtBaseDto.setHouseCertificatePic(AppConfigUtil.getFilePath() + StaticResourceSecurity.getSecurityURI(p.getPicturePath()));
                            break;
                        case MARRY_CERTIFICATE:
                            userExtBaseDto.setMarryCertificatePicId(p.getPictureId());
                            userExtBaseDto.setMarryCertificatePic(AppConfigUtil.getFilePath() + StaticResourceSecurity.getSecurityURI(p.getPicturePath()));
                            break;
                        case RESIDENCE_BOOKLET:
                            userExtBaseDto.setResidenceBookletPicId(p.getPictureId());
                            userExtBaseDto.setResidenceBookletPic(AppConfigUtil.getFilePath() + StaticResourceSecurity.getSecurityURI(p.getPicturePath()));
                            break;
                        case HOUSEHOLDER_BOOKLET:
                            userExtBaseDto.setHouseholderBookletPicId(p.getPictureId());
                            userExtBaseDto.setHouseholderBookletPic(AppConfigUtil.getFilePath() + StaticResourceSecurity.getSecurityURI(p.getPicturePath()));
                            break;
                    }

                }
            }

            extIdDto = userExtBaseDto;

        }
        return extIdDto;
    }

    @Override
    @Transactional
    public void updateTobaccoAuth(UserExtDto userExtDto) {
        UserExtPo userExtPo = userExtDao.findOne(userExtDto.getUserId());
        if (userExtPo != null) {
            userExtPo.setTobaccoAuth(userExtDto.getTobaccoAuth());
            userExtDao.save(userExtPo);
        }
    }

    private void updateUserToCenter(UserExtPo userExtPo,UserInfoPo userInfoPo){
        try{
            CenterUserDto centerUserDto = new CenterUserDto();
            centerUserDto.setUserId(userInfoPo.getUserId());
            if(userInfoPo.getIdentityAuth()) {
                centerUserDto.setIdentityAuth(1);
            }else{
                centerUserDto.setIdentityAuth(0);
            }
            centerUserDto.setMobile(userInfoPo.getMobile());
            centerUserDto.setUuid(userInfoPo.getUuid());
            if("男".equals(userExtPo.getSex())){
                centerUserDto.setGender(1);
            }else if("女".equals(userExtPo.getSex())){
                centerUserDto.setGender(2);
            }
            centerUserDto.setProvince(userExtPo.getProvince());
            centerUserDto.setCity(userExtPo.getCity());
            centerUserDto.setArea(userExtPo.getArea());
            centerUserDto.setStreet(userExtPo.getStreet());
            centerUserDto.setHomeAddress(userExtPo.getAddress());
            centerUserDto.setAreaId(userExtPo.getAreaId());
            centerUserDto.setStreetId(userExtPo.getStreetId());
            centerUserDto.setBrithday(DateHelper.formatDate(userExtPo.getBirthDate(),"yyyy-MM-dd HH:mm:ss"));
            centerUserDto.setIdCardStart(DateHelper.formatDate(userExtPo.getIdExpiredBegin(),"yyyy-MM-dd HH:mm:ss"));
            centerUserDto.setIdCardEnd(DateHelper.formatDate(userExtPo.getIdExpiredEnd(),"yyyy-MM-dd HH:mm:ss"));
            queueSender.send(DestinationDefine.ADD_USER_TO_CENTER, JSON.toJSONString(centerUserDto));
        }catch (Exception e) {
            LOGGER.error("发送centerMQ失败", e);
        }
    }
}
