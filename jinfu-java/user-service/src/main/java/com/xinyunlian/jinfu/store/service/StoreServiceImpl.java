package com.xinyunlian.jinfu.store.service;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.xinyunlian.jinfu.center.dto.CenterStoreDto;
import com.xinyunlian.jinfu.common.converter.ConverterService;
import com.xinyunlian.jinfu.common.enums.EErrorCode;
import com.xinyunlian.jinfu.common.exception.BizServiceException;
import com.xinyunlian.jinfu.common.security.StaticResourceSecurity;
import com.xinyunlian.jinfu.common.util.*;
import com.xinyunlian.jinfu.mq.DestinationDefine;
import com.xinyunlian.jinfu.mq.sender.QueueSender;
import com.xinyunlian.jinfu.picture.dao.PictureDao;
import com.xinyunlian.jinfu.picture.entity.PicturePo;
import com.xinyunlian.jinfu.picture.enums.EPictureType;
import com.xinyunlian.jinfu.picture.service.InnerPictureService;
import com.xinyunlian.jinfu.store.dao.StoreDao;
import com.xinyunlian.jinfu.store.dto.StoreInfDto;
import com.xinyunlian.jinfu.store.dto.req.StoreSearchDto;
import com.xinyunlian.jinfu.store.entity.StoreInfPo;
import com.xinyunlian.jinfu.store.enums.EDelReason;
import com.xinyunlian.jinfu.store.enums.EEstate;
import com.xinyunlian.jinfu.store.enums.ELocationSource;
import com.xinyunlian.jinfu.store.enums.EStoreStatus;
import com.xinyunlian.jinfu.system.dao.SysConfigDao;
import com.xinyunlian.jinfu.system.entity.SysConfigPo;
import com.xinyunlian.jinfu.system.enums.ESysConfigType;
import com.xinyunlian.jinfu.user.dao.UserDao;
import com.xinyunlian.jinfu.user.dao.UserLabelDao;
import com.xinyunlian.jinfu.user.dto.UserLabelDto;
import com.xinyunlian.jinfu.user.entity.UserInfoPo;
import com.xinyunlian.jinfu.user.enums.ELabelType;
import com.xinyunlian.jinfu.user.enums.ERelationship;
import com.xinyunlian.jinfu.user.enums.ESource;
import com.xinyunlian.jinfu.user.enums.EUserStatus;
import com.xinyunlian.jinfu.user.service.UserLabelService;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.persistence.criteria.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * 用户ServiceImpl
 *
 * @author KimLL
 */

@Service
public class StoreServiceImpl implements StoreService, StoreJob {

    private static final Logger LOGGER = LoggerFactory.getLogger(StoreServiceImpl.class);

    @Autowired
    private StoreDao storeDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private InnerPictureService innerPictureService;
    @Autowired
    private PictureDao pictureDao;
    @Autowired
    private UserLabelDao userLabelDao;
    @Autowired
    private QueueSender queueSender;
    @Autowired
    private UserLabelService userLabelService;
    @Autowired
    private SysConfigDao sysConfigDao;
    @Autowired
    private StoreScoreStrategy storeScoreStrategy;
    @Autowired
    @Qualifier("jmsQueueTemplate")
    private JmsTemplate jmsTemplate;

    @Override
    @Transactional
    public StoreInfDto saveStore(StoreInfDto storeInfDto) throws BizServiceException {
        if (null != storeInfDto.getStoreId()) {
            throw new BizServiceException(EErrorCode.ID_IS_NOT_NULL);
        }

        storeInfDto.setAddress(StringUtils.trimToNull(storeInfDto.getAddress()));
        storeInfDto.setTobaccoCertificateNo(StringUtils.trimToNull(storeInfDto.getTobaccoCertificateNo()));
        storeInfDto.setStoreName(StringUtils.trimToNull(storeInfDto.getStoreName()));
        //没有坐标来源，默认地址转换
        if (storeInfDto.getLocationSource() == null) {
            storeInfDto.setLocationSource(ELocationSource.CONVERT);
        }

        if (!StringUtils.isEmpty(storeInfDto.getTobaccoCertificateNo())) {
            StoreInfPo store = storeDao.findByTobaccoCertificateNoAndStatus(storeInfDto.getTobaccoCertificateNo(), EStoreStatus.NORMAL);
            if (store != null) {
                throw new BizServiceException(EErrorCode.TOBACCO_CERTIFICATE_NO_IS_EXIST);
            }
        }
        StoreInfPo storeInfPo = ConverterService.convert(storeInfDto, StoreInfPo.class);
        storeInfPo.setStatus(EStoreStatus.NORMAL);
        storeInfPo.setFullAddress(storeInfPo.getStreet() + storeInfPo.getAddress());

        storeInfPo.setIndustryMcc("5227");
        UserInfoPo userInfoPo = userDao.findOne(storeInfPo.getUserId());
        storeInfPo.setUuid(userInfoPo.getUuid());

        int score = storeScoreStrategy.computeScore(storeInfPo);
        storeInfPo.setScore(score);

        storeInfPo = storeDao.save(storeInfPo);
        userInfoPo.setStoreAuth(true);
        userDao.save(userInfoPo);

        UserLabelDto userLabelDto = new UserLabelDto();
        userLabelDto.setUserId(storeInfPo.getUserId());
        userLabelDto.setLabelType(ELabelType.MCC5227);
        userLabelService.save(userLabelDto);

        storeInfDto.setStoreId(storeInfPo.getStoreId());

        //发送新增店铺推送到会员中心通知
        this.addStoreToCenter(storeInfPo,userInfoPo.getUuid());

        return storeInfDto;
    }

    @Override
    @Transactional
    public StoreInfDto save(StoreInfDto storeInfDto) throws BizServiceException {
        if (null != storeInfDto.getStoreId()) {
            throw new BizServiceException(EErrorCode.ID_IS_NOT_NULL);
        }

        storeInfDto.setAddress(StringUtils.trimToNull(storeInfDto.getAddress()));
        storeInfDto.setTobaccoCertificateNo(StringUtils.trimToNull(storeInfDto.getTobaccoCertificateNo()));
        storeInfDto.setStoreName(StringUtils.trimToNull(storeInfDto.getStoreName()));
        //没有坐标来源，默认地址转换
        if (storeInfDto.getLocationSource() == null) {
            storeInfDto.setLocationSource(ELocationSource.CONVERT);
        }

        StoreInfPo storeInfPo = ConverterService.convert(storeInfDto, StoreInfPo.class);
        storeInfPo.setStatus(EStoreStatus.NORMAL);
        if(StringUtils.isEmpty(storeInfPo.getStreet())) {
            storeInfPo.setFullAddress(storeInfPo.getAddress());
        }else{
            storeInfPo.setFullAddress(storeInfPo.getStreet() + storeInfPo.getAddress());
        }
        if(StringUtils.isEmpty(storeInfPo.getIndustryMcc())){
            storeInfPo.setIndustryMcc("5227");
        }

        UserInfoPo userInfoPo = userDao.findOne(storeInfPo.getUserId());
        storeInfPo.setUuid(userInfoPo.getUuid());

        int score = storeScoreStrategy.computeScore(storeInfPo);
        storeInfPo.setScore(score);

        storeInfPo = storeDao.save(storeInfPo);

        UserLabelDto userLabelDto = new UserLabelDto();
        userLabelDto.setUserId(storeInfPo.getUserId());
        userLabelDto.setLabelType(ELabelType.valueOf("MCC" + storeInfPo.getIndustryMcc()));
        userLabelService.save(userLabelDto);

        storeInfDto.setStoreId(storeInfPo.getStoreId());
        return storeInfDto;
    }

    @Override
    @Transactional
    public void deleteStore(Long storeId) {
        StoreInfPo storeInfPo = storeDao.findOne(storeId);
        List<StoreInfPo> storeInfPos = storeDao.findByUserIdAndStatus(storeInfPo.getUserId(),EStoreStatus.NORMAL);
        if(storeInfPos.size() == 1){
            userLabelDao.deleteByUserIdAndLabelType(storeInfPo.getUserId(),ELabelType.valueOf("MCC" + storeInfPos.get(0).getIndustryMcc()));
        }
        storeInfPo.setStatus(EStoreStatus.DELETE);
        storeDao.save(storeInfPo);
    }

    @Override
    @Transactional
    public void deleteStore(Long storeId, EDelReason reason) {
        StoreInfPo storeInfPo = storeDao.findOne(storeId);
        List<StoreInfPo> storeInfPos = storeDao.findByUserIdAndStatus(storeInfPo.getUserId(),EStoreStatus.NORMAL);
        if(storeInfPos.size() == 1){
            userLabelDao.deleteByUserIdAndLabelType(storeInfPo.getUserId(),ELabelType.valueOf("MCC" + storeInfPos.get(0).getIndustryMcc()));
        }
        storeInfPo.setStatus(EStoreStatus.DELETE);
        storeInfPo.setDelReason(reason);
        storeDao.save(storeInfPo);
    }

    @Override
    public StoreInfDto findByStoreId(Long storeId) {
        StoreInfPo storeInfPo = storeDao.findOne(storeId);
        if (storeInfPo == null) {
            return null;
        }
        StoreInfDto storeInfDto = ConverterService.convert(storeInfPo, StoreInfDto.class);
        if("5227".equals(storeInfDto.getIndustryMcc())) {
            storeInfDto.setLicence(storeInfDto.getTobaccoCertificateNo());
        }
        this.getStorePic(storeInfDto);
        return storeInfDto;
    }

    @Override
    public List<StoreInfDto> findByDistrictIds(List<String> ids) {
        List<StoreInfPo> storeInfPos = storeDao.findByDistrictIds(ids);
        List<StoreInfDto> storeInfDtos = new ArrayList<>();
        storeInfPos.forEach(storeInfPo -> {
            StoreInfDto storeInfDto = ConverterService.convert(storeInfPo, StoreInfDto.class);
            storeInfDtos.add(storeInfDto);
        });

        return storeInfDtos;
    }

    @Override
    public List<StoreInfDto> findByStoreIds(List<Long> ids) {
        List<StoreInfPo> storeInfPos = storeDao.findByStoreIds(ids);
        List<StoreInfDto> storeInfDtos = new ArrayList<>();
        storeInfPos.forEach(storeInfPo -> {
            StoreInfDto storeInfDto = ConverterService.convert(storeInfPo, StoreInfDto.class);
            storeInfDtos.add(storeInfDto);
        });

        return storeInfDtos;
    }

    @Override
    public List<StoreInfDto> findByStoreNameLike(String storeName) {
        List<StoreInfDto> rs = new ArrayList<>();
        List<StoreInfPo> storeInfPos = storeDao.findByStoreNameLike(BizUtil.filterStringRight(storeName));
        if(CollectionUtils.isEmpty(storeInfPos)){
            return rs;
        }
        return ConverterService.convertToList(storeInfPos, StoreInfDto.class);
    }

    @Override
    public List<StoreInfDto> findByUserIds(Set<String> userIds) {
        List<StoreInfDto> storeInfDtos = new ArrayList<>();

        if(userIds.size() > 0){
            List<StoreInfPo> storeInfPos = storeDao.findByUserIds(userIds);
            storeInfPos.forEach(storeInfPo -> {
                StoreInfDto storeInfDto = ConverterService.convert(storeInfPo, StoreInfDto.class);
                storeInfDtos.add(storeInfDto);
            });
        }

        return storeInfDtos;
    }

    public List<StoreInfDto> findStoreIdByDistrictIds(List<String> ids) {
        List<Object[]> longList = storeDao.findStoreIdByDistrictIds(ids);
        List<StoreInfDto> storeInfDtos = new ArrayList<>();
        if (longList != null) {
            longList.forEach(obj -> {
                StoreInfDto storeInfDto = new StoreInfDto();
                storeInfDto.setStoreId(Long.valueOf(obj[0].toString()));
                storeInfDto.setUserId(obj[1].toString());
                storeInfDtos.add(storeInfDto);
            });
        }
        return storeInfDtos;
    }

    @Override
    @Transactional
    public void updateStore(StoreInfDto storeInfDto) throws BizServiceException {
        StoreInfPo storeInfPo = storeDao.findOne(storeInfDto.getStoreId());
        if (storeInfPo != null) {
            if (storeInfPo == null) {
                throw new BizServiceException(EErrorCode.STORE_NOT_EXIST);
            }

            storeInfDto.setAddress(StringUtils.trimToNull(storeInfDto.getAddress()));
            storeInfDto.setTobaccoCertificateNo(StringUtils.trimToNull(storeInfDto.getTobaccoCertificateNo()));
            storeInfDto.setStoreName(StringUtils.trimToNull(storeInfDto.getStoreName()));

           /* if (StringUtils.isNotBlank(storeInfDto.getTobaccoCertificateNo())) {
                StoreInfPo newStore = storeDao.findByTobaccoCertificateNoAndStatus(storeInfDto.getTobaccoCertificateNo(), EStoreStatus.NORMAL);
                if (newStore != null && !storeInfPo.getStoreId().equals(newStore.getStoreId())) {
                    throw new BizServiceException(EErrorCode.TOBACCO_CERTIFICATE_NO_IS_EXIST);
                }
                storeInfPo.setTobaccoCertificateNo(storeInfDto.getTobaccoCertificateNo());
            }*/


            if (StringUtils.isBlank(storeInfPo.getProvince())) {
                storeInfPo.setProvince(storeInfDto.getProvince());
            }

            //特殊约定，为云码解绑用
            if (StringUtils.isNotBlank(storeInfDto.getQrCodeUrl())) {
                if("NULL".equals(storeInfDto.getQrCodeUrl())){
                    storeInfPo.setQrCodeUrl(null);
                }else{
                    storeInfPo.setQrCodeUrl(storeInfDto.getQrCodeUrl());
                }
            }

            if (StringUtils.isNotBlank(storeInfDto.getBankCardNo())) {
                if("NULL".equals(storeInfDto.getBankCardNo())){
                    storeInfPo.setBankCardNo(null);
                }else {
                    storeInfPo.setBankCardNo(storeInfDto.getBankCardNo());
                }
            }

            if (StringUtils.isBlank(storeInfPo.getCity())) {
                storeInfPo.setCity(storeInfDto.getCity());
            }

            if (StringUtils.isBlank(storeInfPo.getArea())) {
                storeInfPo.setArea(storeInfDto.getArea());
            }
            if (StringUtils.isBlank(storeInfPo.getAddress())) {
                storeInfPo.setAddress(storeInfDto.getAddress());
                storeInfPo.setFullAddress(storeInfPo.getStreet() + storeInfPo.getAddress());
            }
            if (StringUtils.isBlank(storeInfPo.getAreaId())) {
                storeInfPo.setAreaId(storeInfDto.getAreaId());
            }
            if (StringUtils.isBlank(storeInfPo.getBizLicence())) {
                storeInfPo.setBizLicence(storeInfDto.getBizLicence());
            }
            if (null != storeInfDto.getBizEndDate()) {
                storeInfPo.setBizEndDate(storeInfDto.getBizEndDate());
            }
            if (StringUtils.isBlank(storeInfPo.getStoreName())) {
                storeInfPo.setStoreName(storeInfDto.getStoreName());
            }
            if (StringUtils.isBlank(storeInfPo.getStreet())) {
                storeInfPo.setStreet(storeInfDto.getStreet());
                storeInfPo.setFullAddress(storeInfPo.getStreet() + storeInfPo.getAddress());
            }

            if (null != storeInfDto.getTobaccoEndDate()) {
                storeInfPo.setTobaccoEndDate(storeInfDto.getTobaccoEndDate());
            }

            if (StringUtils.isBlank(storeInfPo.getAreaId())) {
                storeInfPo.setAreaId(storeInfDto.getAreaId());
            }
            if (StringUtils.isBlank(storeInfPo.getProvinceId())) {
                storeInfPo.setProvinceId(storeInfDto.getProvinceId());
            }
            if (StringUtils.isBlank(storeInfPo.getCityId())) {
                storeInfPo.setCityId(storeInfDto.getCityId());
            }
            if (StringUtils.isBlank(storeInfPo.getStreetId())) {
                storeInfPo.setStreetId(storeInfDto.getStreetId());
            }
            if (StringUtils.isBlank(storeInfPo.getDistrictId())) {
                storeInfPo.setDistrictId(storeInfDto.getDistrictId());
            }
            if (null != storeInfDto.getRelationship()) {
                storeInfPo.setRelationship(storeInfDto.getRelationship());
            }
            if (null != storeInfDto.getRegisterDate()) {
                storeInfPo.setRegisterDate(storeInfDto.getRegisterDate());
            }
            if (null != storeInfDto.getStartDate()) {
                storeInfPo.setStartDate(storeInfDto.getStartDate());
            }
            if (null != storeInfDto.getEmployeeNum() && !storeInfDto.getEmployeeNum().equals(0)) {
                storeInfPo.setEmployeeNum(storeInfDto.getEmployeeNum());
            }
            if (null != storeInfDto.getBusinessArea() && !storeInfDto.getBusinessArea().equals(BigDecimal.ZERO)) {
                storeInfPo.setBusinessArea(storeInfDto.getBusinessArea());
            }
            if (null != storeInfDto.getEstate()) {
                storeInfPo.setEstate(storeInfDto.getEstate());
            }
            if (null != storeInfDto.getMonthSales() && !storeInfDto.getMonthSales().equals(BigDecimal.ZERO)) {
                storeInfPo.setMonthSales(storeInfDto.getMonthSales());
            }
            if (null != storeInfDto.getMonthTobaccoBook() && !storeInfDto.getMonthTobaccoBook().equals(BigDecimal.ZERO)) {
                storeInfPo.setMonthTobaccoBook(storeInfDto.getMonthTobaccoBook());
            }
            if (null != storeInfDto.getBusinessLocation()) {
                storeInfPo.setBusinessLocation(storeInfDto.getBusinessLocation());
            }
            if (StringUtils.isNotBlank(storeInfDto.getTel())) {
                storeInfPo.setTel(storeInfDto.getTel());
            }
            if (null != storeInfDto.getStoreType()) {
                storeInfPo.setStoreType(storeInfDto.getStoreType());
            }
            if (null != storeInfDto.getIsNormal()) {
                storeInfPo.setIsNormal(storeInfDto.getIsNormal());
            }
            if (StringUtils.isNotEmpty(storeInfDto.getLng())) {
                storeInfPo.setLng(storeInfDto.getLng());
            }
            if (StringUtils.isNotEmpty(storeInfDto.getLat())) {
                storeInfPo.setLat(storeInfDto.getLat());
            }
            if (null != storeInfDto.getMonthSalesApprove()) {
                storeInfPo.setMonthSalesApprove(storeInfDto.getMonthSalesApprove());
            }
        }

        int score = storeScoreStrategy.computeScore(storeInfPo);
        storeInfPo.setScore(score);

        storeDao.save(storeInfPo);

        //发送更新店铺推送到会员中心通知
        this.updateStoreToCenter(storeInfPo);
    }

    @Override
    public List<StoreInfDto> findByUserId(String userId) {
        List<StoreInfPo> storeInfPoList = storeDao.findByUserIdAndStatus(userId, EStoreStatus.NORMAL);
        List<StoreInfDto> storeInfDtoList = new ArrayList<>();
        for (StoreInfPo storeInfPo : storeInfPoList) {
            StoreInfDto storeInfDto = ConverterService.convert(storeInfPo, StoreInfDto.class);
            if("5227".equals(storeInfDto.getIndustryMcc())) {
                storeInfDto.setLicence(storeInfDto.getTobaccoCertificateNo());
            }
            this.getStorePic(storeInfDto);
            storeInfDtoList.add(storeInfDto);
        }
        return storeInfDtoList;
    }

    public void updateUserId(StoreInfDto storeInfDto) {
        StoreInfPo storeInfPo = storeDao.findOne(storeInfDto.getStoreId());
        storeInfPo.setUserId(storeInfDto.getUserId());

        if (!StringUtils.isEmpty(storeInfDto.getAreaId())) {
            storeInfPo.setAreaId(storeInfDto.getAreaId());
        }
        if (!StringUtils.isEmpty(storeInfDto.getProvinceId())) {
            storeInfPo.setProvinceId(storeInfDto.getProvinceId());
        }
        if (!StringUtils.isEmpty(storeInfDto.getCityId())) {
            storeInfPo.setCityId(storeInfDto.getCityId());
        }
        if (!StringUtils.isEmpty(storeInfDto.getStreetId())) {
            storeInfPo.setStreetId(storeInfDto.getStreetId());
        }
        if (!StringUtils.isEmpty(storeInfDto.getDistrictId())) {
            storeInfPo.setDistrictId(storeInfDto.getDistrictId());
        }
        if (!StringUtils.isEmpty(storeInfDto.getProvince())) {
            storeInfPo.setProvince(storeInfDto.getProvince());
        }
        if (!StringUtils.isEmpty(storeInfDto.getCity())) {
            storeInfPo.setCity(storeInfDto.getCity());
        }

        if (!StringUtils.isEmpty(storeInfDto.getArea())) {
            storeInfPo.setArea(storeInfDto.getArea());
        }
        if (!StringUtils.isEmpty(storeInfDto.getStreet())) {
            storeInfPo.setStreet(storeInfDto.getStreet());
            storeInfPo.setFullAddress(storeInfPo.getStreet() + storeInfPo.getAddress());
        }

        int score = storeScoreStrategy.computeScore(storeInfPo);
        storeInfPo.setScore(score);

        storeDao.save(storeInfPo);
    }

    @Override
    @Transactional(readOnly = true)
    public StoreSearchDto getStorePage(StoreSearchDto storeInfSearchDto) {
        Specification<StoreInfPo> spec = (Root<StoreInfPo> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            Predicate predicate = cb.conjunction();
            List<Expression<Boolean>> expressions = predicate.getExpressions();
            if (null != storeInfSearchDto) {
                if (!StringUtils.isEmpty(storeInfSearchDto.getUserName())) {
                    expressions.add(cb.like(root.<UserInfoPo>get("userInfoPo").get("userName"), BizUtil.filterString(storeInfSearchDto.getUserName())));
                }
                if (!StringUtils.isEmpty(storeInfSearchDto.getMobile())) {
                    expressions.add(cb.like(root.<UserInfoPo>get("userInfoPo").get("mobile"), BizUtil.filterStringRight(storeInfSearchDto.getMobile())));
                }
                if (!StringUtils.isEmpty(storeInfSearchDto.getArea())) {
                    expressions.add(cb.equal(root.get("area"), storeInfSearchDto.getArea()));
                }
                if (!StringUtils.isEmpty(storeInfSearchDto.getCity())) {
                    expressions.add(cb.equal(root.get("city"), storeInfSearchDto.getCity()));
                }
                if (!StringUtils.isEmpty(storeInfSearchDto.getProvince())) {
                    expressions.add(cb.equal(root.get("province"), storeInfSearchDto.getProvince()));
                }
                if (!StringUtils.isEmpty(storeInfSearchDto.getStreet())) {
                    expressions.add(cb.like(root.get("street"), BizUtil.filterStringRight(storeInfSearchDto.getStreet())));
                }
                if (!StringUtils.isEmpty(storeInfSearchDto.getFullAddress())) {
                    expressions.add(cb.like(root.get("fullAddress"), BizUtil.filterString(storeInfSearchDto.getFullAddress())));
                }
                if (null != storeInfSearchDto.getSource()) {
                    expressions.add(cb.equal(root.get("source"), storeInfSearchDto.getSource()));
                }
                if (!StringUtils.isEmpty(storeInfSearchDto.getTobaccoCertificateNo())) {
                    expressions.add(cb.like(root.get("tobaccoCertificateNo"), BizUtil.filterString(storeInfSearchDto.getTobaccoCertificateNo())));
                }
                if (!StringUtils.isEmpty(storeInfSearchDto.getStoreName())) {
                    expressions.add(cb.like(root.get("storeName"), BizUtil.filterString(storeInfSearchDto.getStoreName())));
                }
                if (!StringUtils.isEmpty(storeInfSearchDto.getIndustryMcc())) {
                    expressions.add(cb.equal(root.get("industryMcc"), storeInfSearchDto.getIndustryMcc()));
                }

                if (null != storeInfSearchDto.getDistrictIds()) {
                    if (storeInfSearchDto.getDistrictIds().size() > 0) {
                        expressions.add(cb.in(root.get("districtId")).value(storeInfSearchDto.getDistrictIds()));
                    }
                }

                if (!StringUtils.isEmpty(storeInfSearchDto.getCreateStartDate())) {
                    expressions.add(cb.greaterThanOrEqualTo(root.get("createTs"), DateHelper.getStartDate(storeInfSearchDto.getCreateStartDate())));
                }
                if (!StringUtils.isEmpty(storeInfSearchDto.getCreateEndDate())) {
                    expressions.add(cb.lessThanOrEqualTo(root.get("createTs"), DateHelper.getEndDate(storeInfSearchDto.getCreateEndDate())));
                }

                if (null != storeInfSearchDto.getStoreIds()) {
                    if (storeInfSearchDto.getStoreIds().size() > 0) {
                        expressions.add(cb.in(root.get("storeId")).value(storeInfSearchDto.getStoreIds()));
                    }
                }
                if(!CollectionUtils.isEmpty(storeInfSearchDto.getAreaIds())){
                    expressions.add(cb.or(cb.in(root.get("provinceId")).value(storeInfSearchDto.getAreaIds()),
                            cb.in(root.get("cityId")).value(storeInfSearchDto.getAreaIds()),
                            cb.in(root.get("areaId")).value(storeInfSearchDto.getAreaIds()),
                            cb.in(root.get("streetId")).value(storeInfSearchDto.getAreaIds())));
                }

                expressions.add(cb.equal(root.<UserInfoPo>get("userInfoPo").get("status"), EUserStatus.NORMAL));
                expressions.add(cb.equal(root.get("status"), EStoreStatus.NORMAL));

            }
            return predicate;
        };

        Pageable pageable = new PageRequest(storeInfSearchDto.getCurrentPage() - 1,
                storeInfSearchDto.getPageSize(), Direction.DESC, "storeId");
        Page<StoreInfPo> page = storeDao.findAll(spec, pageable);

        List<StoreSearchDto> data = new ArrayList<>();
        for (StoreInfPo po : page.getContent()) {
            StoreSearchDto storeInfDto = ConverterService.convert(po, StoreSearchDto.class);
            storeInfDto.setMobile(po.getUserInfoPo().getMobile());
            storeInfDto.setUserName(po.getUserInfoPo().getUserName());
            data.add(storeInfDto);
        }
        storeInfSearchDto.setList(data);
        storeInfSearchDto.setTotalPages(page.getTotalPages());
        storeInfSearchDto.setTotalRecord(page.getTotalElements());
        return storeInfSearchDto;
    }


    @Override
    @Transactional
    public StoreInfDto saveAndUpdateStore(StoreInfDto storeInfDto) {
        if (storeInfDto.getStoreId() == null) {
            saveStore(storeInfDto);
            //saveStorePic(storeInfDto);
        } else {
            updateStore(storeInfDto);
            //saveStorePic(storeInfDto);
        }
        return storeInfDto;
    }

    @Override
    @Transactional
    public StoreInfDto saveSupportAll(StoreInfDto storeInfDto) throws BizServiceException{
        if("5227".equals(storeInfDto.getIndustryMcc())
                || StringUtils.isEmpty(storeInfDto.getIndustryMcc())){
            if(!StringUtils.isEmpty(storeInfDto.getLicence())) {
                storeInfDto.setTobaccoCertificateNo(storeInfDto.getLicence());
            }
            this.saveAndUpdateStore(storeInfDto);
        }else {
            if (storeInfDto.getStoreId() == null) {
                this.save(storeInfDto);
            }else {
                this.updateStore(storeInfDto);
            }

        }
        return storeInfDto;
    }

    private void saveStorePic(StoreInfDto storeInfDto) {
        innerPictureService.updatePicture(storeInfDto.getStoreLicencePicId(), storeInfDto.getStoreId().toString(), EPictureType.STORE_LICENCE);
        innerPictureService.updatePicture(storeInfDto.getStoreTobaccoPicId(), storeInfDto.getStoreId().toString(), EPictureType.STORE_TOBACCO);
        innerPictureService.updatePicture(storeInfDto.getStoreHeaderPicId(), storeInfDto.getStoreId().toString(), EPictureType.STORE_HEADER);
        innerPictureService.updatePicture(storeInfDto.getStoreInnerPicId(), storeInfDto.getStoreId().toString(), EPictureType.STORE_INNER);
        innerPictureService.updatePicture(storeInfDto.getStoreOutsidePicId(), storeInfDto.getStoreId().toString(), EPictureType.STORE_OUTSIDE);
        innerPictureService.updatePicture(storeInfDto.getStoreHouseCertificatePicId(), storeInfDto.getStoreId().toString(), EPictureType.STORE_HOUSE_CERTIFICATE);
    }

    private void getStorePic(StoreInfDto storeInfDto) {
        List<PicturePo> picturePos = pictureDao.findByParentId(storeInfDto.getStoreId().toString());
        for (PicturePo p :
                picturePos) {
            switch (p.getPictureType()) {
                case STORE_LICENCE:
                    storeInfDto.setStoreLicencePicId(p.getPictureId());
                    storeInfDto.setStoreLicencePic(AppConfigUtil.getFilePath() + StaticResourceSecurity.getSecurityURI(p.getPicturePath()));
                    break;
                case STORE_TOBACCO:
                    storeInfDto.setStoreTobaccoPicId(p.getPictureId());
                    storeInfDto.setStoreTobaccoPic(AppConfigUtil.getFilePath() + StaticResourceSecurity.getSecurityURI(p.getPicturePath()));
                    storeInfDto.setLicencePicId(p.getPictureId());
                    storeInfDto.setLicencePic(AppConfigUtil.getFilePath() + StaticResourceSecurity.getSecurityURI(p.getPicturePath()));
                    break;
                case STORE_INNER:
                    storeInfDto.setStoreInnerPicId(p.getPictureId());
                    storeInfDto.setStoreInnerPic(AppConfigUtil.getFilePath() + StaticResourceSecurity.getSecurityURI(p.getPicturePath()));
                    break;
                case STORE_OUTSIDE:
                    storeInfDto.setStoreOutsidePicId(p.getPictureId());
                    storeInfDto.setStoreOutsidePic(AppConfigUtil.getFilePath() + StaticResourceSecurity.getSecurityURI(p.getPicturePath()));
                    break;
                case STORE_HOUSE_CERTIFICATE:
                    storeInfDto.setStoreHouseCertificatePicId(p.getPictureId());
                    storeInfDto.setStoreHouseCertificatePic(AppConfigUtil.getFilePath() + StaticResourceSecurity.getSecurityURI(p.getPicturePath()));
                    break;
                case STORE_HEADER:
                    storeInfDto.setStoreHeaderPicId(p.getPictureId());
                    storeInfDto.setStoreHeaderPic(AppConfigUtil.getFilePath() + StaticResourceSecurity.getSecurityURI(p.getPicturePath()));
                    break;
                case MARRY_CERTIFICATE:
                    storeInfDto.setMarryCertificatePicId(p.getPictureId());
                    storeInfDto.setMarryCertificatePic(AppConfigUtil.getFilePath() + StaticResourceSecurity.getSecurityURI(p.getPicturePath()));
                    break;
                case RESIDENCE_BOOKLET:
                    storeInfDto.setResidenceBookletPicId(p.getPictureId());
                    storeInfDto.setResidenceBookletPic(AppConfigUtil.getFilePath() + StaticResourceSecurity.getSecurityURI(p.getPicturePath()));
                    break;
                case STORE_NO_TOBACCO_LICENCE:
                    storeInfDto.setLicencePicId(p.getPictureId());
                    storeInfDto.setLicencePic(AppConfigUtil.getFilePath() + StaticResourceSecurity.getSecurityURI(p.getPicturePath()));
                    break;
            }

        }

        List<PicturePo> marryPics = pictureDao.findByParentIdAndPictureType(storeInfDto.getUserId().toString(),EPictureType.MARRY_CERTIFICATE);
        if(!CollectionUtils.isEmpty(marryPics)){
            storeInfDto.setMarryCertificatePicId(marryPics.get(0).getPictureId());
            storeInfDto.setMarryCertificatePic(AppConfigUtil.getFilePath()
                    + StaticResourceSecurity.getSecurityURI(marryPics.get(0).getPicturePath()));
        }
    }

    @Override
    @Transactional(readOnly = true)
    public StoreSearchDto getStorePointPage(StoreSearchDto storeInfSearchDto) {
        Integer currentPage = storeInfSearchDto.getCurrentPage();
        Integer pageSize = storeInfSearchDto.getPageSize();

        //动态配置地址评分
        SysConfigPo sysConfigPo = sysConfigDao.findByType(ESysConfigType.STORE_ADDRESS);
        Integer score = 0;
        if (sysConfigPo != null) {
            score = sysConfigPo.getScore();
        }

        Integer finalScore = score;
        Specification<StoreInfPo> spec = (Root<StoreInfPo> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            Predicate predicate = cb.conjunction();
            List<Expression<Boolean>> expressions = predicate.getExpressions();
            if(!CollectionUtils.isEmpty(storeInfSearchDto.getAreaIds())){
                expressions.add(cb.or(cb.in(root.get("provinceId")).value(storeInfSearchDto.getAreaIds()),
                        cb.in(root.get("cityId")).value(storeInfSearchDto.getAreaIds()),
                        cb.in(root.get("areaId")).value(storeInfSearchDto.getAreaIds()),
                        cb.in(root.get("streetId")).value(storeInfSearchDto.getAreaIds())));
            }
            if (StringUtils.isNotEmpty(storeInfSearchDto.getCity())) {
                expressions.add(cb.equal(root.get("city"), storeInfSearchDto.getCity()));
            }
            expressions.add(cb.equal(root.get("status"), EStoreStatus.NORMAL));
            expressions.add(cb.isNotNull(root.get("userId")));
            expressions.add(cb.isNotNull(root.get("lng")));
            expressions.add(cb.isNotNull(root.get("lat")));
            expressions.add(cb.greaterThanOrEqualTo(root.get("score"), finalScore));
            return predicate;
        };
        Long totalRecord = storeDao.count(spec);

        Integer totalPages = pageSize == 0 ? 1 : (int) Math.ceil((double) totalRecord / (double) pageSize);
        Integer startRow = pageSize * (currentPage - 1);

        List<StoreInfPo> list = storeDao.findByAreaIdsAndCity(storeInfSearchDto.getAreaIds(), storeInfSearchDto.getCity(),
                storeInfSearchDto.getLng(), storeInfSearchDto.getLat(), startRow, pageSize, score);

        List<StoreSearchDto> data = new ArrayList<>();
        for (StoreInfPo po : list) {
            StoreSearchDto storeInfDto = ConverterService.convert(po, StoreSearchDto.class);
            storeInfDto.setMobile(po.getUserInfoPo().getMobile());
            storeInfDto.setUserName(po.getUserInfoPo().getUserName());
            data.add(storeInfDto);
        }
        storeInfSearchDto.setList(data);
        storeInfSearchDto.setTotalPages(totalPages);
        storeInfSearchDto.setTotalRecord(totalRecord);
        return storeInfSearchDto;
    }

    @Override
    public List<StoreInfDto> findByNotPoint() {
        Specification<StoreInfPo> spec = (Root<StoreInfPo> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            Predicate predicate = cb.conjunction();
            List<Expression<Boolean>> expressions = predicate.getExpressions();
            expressions.add(cb.isNull(root.get("lng")));
            expressions.add(cb.isNull(root.get("lat")));
            return predicate;
        };
        List<StoreInfPo> storeInfPos = storeDao.findAll(spec);
//        List<StoreInfPo> storeInfPos = storeDao.findAll();
        List<StoreInfDto> storeInfDtos = new ArrayList<>();
        storeInfPos.forEach(storeInfPo -> {
            StoreInfDto storeInfDto = ConverterService.convert(storeInfPo, StoreInfDto.class);
            storeInfDtos.add(storeInfDto);
        });
        return storeInfDtos;
    }

    @Override
    public StoreInfDto findByTobaccoCertificateNo(String tobaccoCertificateNo){
        StoreInfPo storeInfPo = storeDao.findByTobaccoCertificateNoAndStatus(tobaccoCertificateNo,EStoreStatus.NORMAL);
        if (storeInfPo == null) {
            return null;
        }
        StoreInfDto storeInfDto = ConverterService.convert(storeInfPo, StoreInfDto.class);
        return storeInfDto;
    }

    @Override
    public List<StoreInfDto> findByTobaccoCertificateNoLike(String tobaccoCertificateNo) {
        List<StoreInfDto> rs = new ArrayList<>();
        List<StoreInfPo> list = storeDao.findByStatusAndTobaccoCertificateNoLike(
            EStoreStatus.NORMAL, BizUtil.filterString(tobaccoCertificateNo)
        );
        list.forEach(item -> rs.add(ConverterService.convert(item, StoreInfDto.class)));
        return rs;
    }

    @Override
    public List<String> findUseridsByAddressIDs(List<String> list) {
        List<String> infPos = storeDao.findUsersWithAddressIds(list);
        return infPos;
    }

    @Override
    public List<String> findAllUsers() {
        List<String> storeInfPos = storeDao.findAllUsersByStore();
        return storeInfPos;
    }

    public void updateBizEndDate(StoreInfDto storeInfDto) {
        StoreInfPo storeInfPo = storeDao.findOne(storeInfDto.getStoreId());
        if (storeInfPo.getBizEndDate() == null) {
            storeInfPo.setBizEndDate(storeInfDto.getBizEndDate());
            storeDao.save(storeInfPo);
        }
    }

    private void addStoreToCenter(StoreInfPo storeInfPo,String userUUID){
        try{
            //发送新增店铺推送到会员中心通知
            CenterStoreDto centerStoreDto = new CenterStoreDto();
            centerStoreDto.setStoreId(storeInfPo.getStoreId());
            centerStoreDto.setUserUUID(userUUID);
            centerStoreDto.setStoreName(storeInfPo.getStoreName());
            centerStoreDto.setProvince(storeInfPo.getProvince());
            centerStoreDto.setCity(storeInfPo.getCity());
            centerStoreDto.setArea(storeInfPo.getArea());
            centerStoreDto.setStreet(storeInfPo.getStreet());
            centerStoreDto.setAreaId(Long.valueOf(storeInfPo.getAreaId()));
            centerStoreDto.setStreetId(Long.valueOf(storeInfPo.getStreetId()));
            centerStoreDto.setStoreAddress(storeInfPo.getAddress());
            centerStoreDto.setBusinessLicence(storeInfPo.getBizLicence());
            centerStoreDto.setTobaccoLicence(storeInfPo.getTobaccoCertificateNo());
            queueSender.send(DestinationDefine.ADD_STORE_TO_CENTER, JSON.toJSONString(centerStoreDto));
        }catch (Exception e) {
            LOGGER.error("发送centerMQ失败", e);
        }
    }

    private void updateStoreToCenter(StoreInfPo storeInfPo){
        try{
            CenterStoreDto centerStoreDto = new CenterStoreDto();

            centerStoreDto.setStoreId(storeInfPo.getStoreId());
            centerStoreDto.setStoreName(storeInfPo.getStoreName());
            centerStoreDto.setProvince(storeInfPo.getProvince());
            centerStoreDto.setCity(storeInfPo.getCity());
            centerStoreDto.setArea(storeInfPo.getArea());
            centerStoreDto.setStreet(storeInfPo.getStreet());
            centerStoreDto.setAreaId(Long.valueOf(storeInfPo.getAreaId()));
            centerStoreDto.setStreetId(Long.valueOf(storeInfPo.getStreetId()));
            centerStoreDto.setStoreAddress(storeInfPo.getAddress());
            centerStoreDto.setBusinessLicence(storeInfPo.getBizLicence());
            centerStoreDto.setTobaccoLicence(storeInfPo.getTobaccoCertificateNo());

            centerStoreDto.setUserUUID(storeInfPo.getUuid());
            centerStoreDto.setUuid(storeInfPo.getSuid());
            centerStoreDto.setStorePhone(storeInfPo.getTel());
            centerStoreDto.setBusinessLicence(storeInfPo.getBizLicence());
            if(storeInfPo.getBusinessArea() != null) {
                centerStoreDto.setBusinessArea(storeInfPo.getBusinessArea().doubleValue());
            }

            centerStoreDto.setBusinessEndDate(DateHelper.formatDate(storeInfPo.getBizEndDate(),"yyyy-MM-dd HH:mm:ss"));
            centerStoreDto.setTobaccoEndDate(DateHelper.formatDate(storeInfPo.getTobaccoEndDate(),"yyyy-MM-dd HH:mm:ss"));
            if(null != storeInfPo.getBusinessArea()) {
                centerStoreDto.setBusinessArea(storeInfPo.getBusinessArea().doubleValue());
            }
            if(null != storeInfPo.getEstate()) {
                centerStoreDto.setEstate(Integer.valueOf(storeInfPo.getEstate().getCode()));
            }
            if(null != storeInfPo.getRelationship()) {
                centerStoreDto.setRelationship(storeInfPo.getRelationship().getCode());
            }
            centerStoreDto.setEmployeeNum(storeInfPo.getEmployeeNum());

            queueSender.send(DestinationDefine.ADD_STORE_TO_CENTER, JSON.toJSONString(centerStoreDto));
        }catch (Exception e) {
            LOGGER.error("发送centerMQ失败", e);
        }
    }

    @Override
    @Transactional
    public void updateStoreScore(Long storeId) {
        StoreInfPo po = storeDao.findOne(storeId);
        if (po != null) {
            int score = storeScoreStrategy.computeScore(po);
            po.setScore(score);
            storeDao.save(po);
        }
    }

    @Override
    @JmsListener(destination = DestinationDefine.STORE_UPDATED_NOTIFY)
    public void storeUpdateListener(String json) {
        try {
            List<Long> list = JsonUtil.toObject(List.class, Long.class, json);
            for (Long storeId : list) {
                try {
                    updateStoreScore(storeId);
                } catch (Exception e) {
                    LOGGER.error(String.format("更新店铺 %d 分数失败", storeId), e);
                }
            }
        } catch (Exception e) {
            LOGGER.error("店铺更新消息消费失败", e);
        }
    }

    @Override
    public void updateAllStoreScore() {
        long total = storeDao.count();
        int pageSize = 1000;
        long count = 0;
        Long startId = NumberUtils.LONG_ZERO;
        while (count < total) {
            List<Object> list = storeDao.findStoreIds(startId, new PageRequest(0, pageSize));
            String json = JsonUtil.toJson(list);
            jmsTemplate.convertAndSend(DestinationDefine.STORE_UPDATED_NOTIFY, json);
            if (!list.isEmpty()) {
                startId = (Long) list.get(list.size() - 1);
            }
            count += pageSize;
        }
    }

    private List<Long> getAllStoreId() {
        long total = storeDao.count();
        int pageSize = 10000;
        long count = 0;
        List<Long> ids = Lists.newArrayList();
        Long startId = NumberUtils.LONG_ZERO;
        while (count < total) {
            List<Object> list = storeDao.findStoreIds(startId, new PageRequest(0, pageSize));
            for (Object object : list) {
                ids.add((Long) object);
            }
            if (!ids.isEmpty()) {
                startId = ids.get(ids.size() - 1);
            }
            count += pageSize;
        }
        return ids;
    }

    @Override
    @Transactional
    public String saveFromUserCenter(CenterStoreDto centerStoreDto) throws BizServiceException {
        StoreInfPo storeInfPo = storeDao.findBySuid(centerStoreDto.getUuid());
        if (storeInfPo == null) {
            storeInfPo = new StoreInfPo();
            StoreInfPo storeTemp = storeDao.findByTobaccoCertificateNoAndStatus(centerStoreDto.getTobaccoLicence(), EStoreStatus.NORMAL);
            if (storeTemp != null) {
                return "烟草证号已存在";
            }
            if(StringUtils.isEmpty(centerStoreDto.getUserUUID())){
                return "该店铺的用户UUID不存在";
            }
            UserInfoPo userTemp = userDao.findByUuid(centerStoreDto.getUserUUID());
            if (userTemp == null) {
                return "该店铺的用户不存在";
            }
            storeInfPo.setUserId(userTemp.getUserId());
            storeInfPo.setTobaccoCertificateNo(centerStoreDto.getTobaccoLicence());
            storeInfPo.setIndustryMcc("5227");
            storeInfPo.setSource(ESource.THIRD_PARTY);
            storeInfPo.setStatus(EStoreStatus.NORMAL);
        } else {
            if (!storeInfPo.getTobaccoCertificateNo().equals(centerStoreDto.getTobaccoLicence())) {
                return "烟草证号不一致";
            }
        }
        storeInfPo.setSuid(centerStoreDto.getUuid());
        storeInfPo.setUuid(centerStoreDto.getUserUUID());
        if (!StringUtils.isEmpty(centerStoreDto.getStoreName())) {
            storeInfPo.setStoreName(centerStoreDto.getStoreName());
        }
        if (!StringUtils.isEmpty(centerStoreDto.getProvince())) {
            storeInfPo.setProvince(centerStoreDto.getProvince());
        }
        if (!StringUtils.isEmpty(centerStoreDto.getCity())) {
            storeInfPo.setCity(centerStoreDto.getCity());
        }
        if (!StringUtils.isEmpty(centerStoreDto.getArea())) {
            storeInfPo.setArea(centerStoreDto.getArea());
        }
        if (null != centerStoreDto.getProvinceId()) {
            storeInfPo.setProvinceId(centerStoreDto.getProvinceId() + "");
        }
        if (null != centerStoreDto.getCityId()) {
            storeInfPo.setCityId(centerStoreDto.getCityId() + "");
        }
        if (null != centerStoreDto.getAreaId()) {
            storeInfPo.setAreaId(centerStoreDto.getAreaId() + "");
            storeInfPo.setDistrictId(centerStoreDto.getAreaId() + "");
        }
        if (null != centerStoreDto.getStreetId()) {
            storeInfPo.setStreetId(centerStoreDto.getStreetId() + "");
            storeInfPo.setDistrictId(centerStoreDto.getStreetId() + "");
            if (!StringUtils.isEmpty(centerStoreDto.getStreet())) {
                storeInfPo.setStreet(centerStoreDto.getStreet());
            }
        }
        if (!StringUtils.isEmpty(centerStoreDto.getStoreAddress())) {
            storeInfPo.setAddress(centerStoreDto.getStoreAddress());
        }
        if (!StringUtils.isEmpty(centerStoreDto.getBusinessLicence())) {
            storeInfPo.setBizLicence(centerStoreDto.getBusinessLicence());
        }
        if (null != centerStoreDto.getEmployeeNum()) {
            storeInfPo.setEmployeeNum(centerStoreDto.getEmployeeNum());
        }
        if (!StringUtils.isEmpty(centerStoreDto.getStorePhone())) {
            storeInfPo.setTel(centerStoreDto.getStorePhone());
        }
        if (!StringUtils.isEmpty(centerStoreDto.getBusinessLicence())) {
            storeInfPo.setBizLicence(centerStoreDto.getBusinessLicence());
        }
        if (centerStoreDto.getBusinessArea() != null) {
            storeInfPo.setBusinessArea(BigDecimal.valueOf(centerStoreDto.getBusinessArea()));
        }
        if(centerStoreDto.getBusinessEndDate() != null) {
            storeInfPo.setBizEndDate(DateHelper.getDate(centerStoreDto.getBusinessEndDate(), "yyyy-MM-dd HH:mm:ss"));
        }
        if(centerStoreDto.getTobaccoEndDate() != null) {
            storeInfPo.setTobaccoEndDate(DateHelper.getDate(centerStoreDto.getTobaccoEndDate(), "yyyy-MM-dd HH:mm:ss"));
        }
        if(null != centerStoreDto.getBusinessArea()) {
            storeInfPo.setBusinessArea(BigDecimal.valueOf(centerStoreDto.getBusinessArea()));
        }
        if(null != centerStoreDto.getEstate()) {
            storeInfPo.setEstate(EnumHelper.translate(EEstate.class, centerStoreDto.getEstate().toString()));
        }
        if(null != centerStoreDto.getRelationship()) {
            storeInfPo.setRelationship(EnumHelper.translate(ERelationship.class,centerStoreDto.getRelationship()));
        }

        if(!StringUtils.isEmpty(centerStoreDto.getStreet())) {
            storeInfPo.setFullAddress(centerStoreDto.getStreet() + centerStoreDto.getStoreAddress());
        }else{
            storeInfPo.setFullAddress(centerStoreDto.getStoreAddress());
        }

        int score = storeScoreStrategy.computeScore(storeInfPo);
        storeInfPo.setScore(score);

        storeDao.save(storeInfPo);
        return null;
    }
}
