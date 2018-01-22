package com.xinyunlian.jinfu.user.service;

import com.alibaba.fastjson.JSON;
import com.xinyunlian.jinfu.bank.dao.BankCardDao;
import com.xinyunlian.jinfu.bank.dao.BankInfDao;
import com.xinyunlian.jinfu.bank.dto.BankCardDto;
import com.xinyunlian.jinfu.bank.entity.BankCardPo;
import com.xinyunlian.jinfu.center.dto.CenterUserDto;
import com.xinyunlian.jinfu.common.converter.ConverterService;
import com.xinyunlian.jinfu.common.enums.EErrorCode;
import com.xinyunlian.jinfu.common.exception.BizServiceException;
import com.xinyunlian.jinfu.common.fileStore.FileStoreService;
import com.xinyunlian.jinfu.common.util.BizUtil;
import com.xinyunlian.jinfu.common.util.DateHelper;
import com.xinyunlian.jinfu.common.util.EncryptUtil;
import com.xinyunlian.jinfu.common.util.RandomUtil;
import com.xinyunlian.jinfu.common.yitu.YituService;
import com.xinyunlian.jinfu.common.yitu.dto.resp.CertificationDto;
import com.xinyunlian.jinfu.common.yitu.helper.FileHelper;
import com.xinyunlian.jinfu.mq.DestinationDefine;
import com.xinyunlian.jinfu.mq.sender.QueueSender;
import com.xinyunlian.jinfu.picture.dao.PictureDao;
import com.xinyunlian.jinfu.picture.entity.PicturePo;
import com.xinyunlian.jinfu.picture.enums.EPictureType;
import com.xinyunlian.jinfu.store.dao.StoreDao;
import com.xinyunlian.jinfu.store.dto.StoreInfDto;
import com.xinyunlian.jinfu.store.entity.StoreInfPo;
import com.xinyunlian.jinfu.store.enums.EStoreStatus;
import com.xinyunlian.jinfu.user.dao.UserDao;
import com.xinyunlian.jinfu.user.dao.UserExtDao;
import com.xinyunlian.jinfu.user.dto.UserExtDto;
import com.xinyunlian.jinfu.user.dto.UserInfoDto;
import com.xinyunlian.jinfu.user.dto.req.CertifyInfoDto;
import com.xinyunlian.jinfu.user.dto.req.IDCardDto;
import com.xinyunlian.jinfu.user.dto.req.PasswordDto;
import com.xinyunlian.jinfu.user.dto.req.UserSearchDto;
import com.xinyunlian.jinfu.user.dto.resp.UserDetailDto;
import com.xinyunlian.jinfu.user.entity.UserExtPo;
import com.xinyunlian.jinfu.user.entity.UserInfoPo;
import com.xinyunlian.jinfu.user.enums.ESource;
import com.xinyunlian.jinfu.user.enums.EUserStatus;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.*;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * 用户ServiceImpl
 *
 * @author KimLL
 */

@Service
public class UserServiceImpl implements UserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserDao userDao;
    @Autowired
    private BankCardDao bankCardDao;
    @Autowired
    private BankInfDao bankInfDao;
    @Autowired
    private UserExtDao userExtDao;
    @Autowired
    private FileStoreService fileStoreService;
    @Autowired
    private PictureDao pictureDao;
    @Autowired
    private StoreDao storeDao;
    @Autowired
    private YituService yituService;
    @Autowired
    private QueueSender queueSender;

    @Override
    public UserInfoDto findUserByUserId(String userId) throws BizServiceException{
        UserInfoPo user = userDao.findOne(userId);
        if (user == null) {
            throw new BizServiceException(EErrorCode.USER_NOT_EXIST,"用户不存在");
        }
        UserInfoDto userInfoDto = ConverterService.convert(user, UserInfoDto.class);
        if(user.getDealPwd() != null){
            userInfoDto.setDealPassword("true");
        }
        return userInfoDto;
    }

    @Override
    public List<UserInfoDto> findUserByUserId(Iterable<String> userIds) {
        List<UserInfoPo> userInfos = userDao.findByUserIdIn(userIds);
        List<UserInfoDto> userInfoDtos = new ArrayList<>();
        userInfos.forEach(user ->
                userInfoDtos.add(ConverterService.convert(user, UserInfoDto.class))
            );
        return userInfoDtos;
    }

    @Override
    public UserInfoDto findUserByMobile(String mobile) {
        UserInfoPo user = userDao.findByMobile(mobile);
        return ConverterService.convert(user, UserInfoDto.class);
    }

    @Override
    public List<UserInfoDto> findByUserNameLike(String userName) {
        List<UserInfoPo> userInfoPos = userDao.findByUserNameLike(BizUtil.filterStringRight(userName));
        if (userInfoPos == null) {
            return null;
        }
        List<UserInfoDto> userInfoDtos = new ArrayList<>();
        userInfoPos.forEach(userInfoPo -> {
            UserInfoDto userInfoDto = ConverterService.convert(userInfoPo, UserInfoDto.class);
            userInfoDtos.add(userInfoDto);
        });
        return userInfoDtos;
    }

    @Override
    public List<UserInfoDto> findByIdCardNoLike(String idCardNo) {
        List<UserInfoDto> rs = new ArrayList<>();
        List<UserInfoPo> userInfoPos = userDao.findByIdCardNoLike(BizUtil.filterString(idCardNo));
        if(CollectionUtils.isEmpty(userInfoPos)){
            return rs;
        }
        return ConverterService.convertToList(userInfoPos, UserInfoDto.class);
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetailDto findUserDetailByUserId(String userId) {
        UserDetailDto userDetailDto = new UserDetailDto();
        UserInfoPo user = userDao.findOne(userId);
        UserExtPo userExtPo = userExtDao.findOne(userId);

        UserInfoDto userInfoDto = ConverterService.convert(user, UserInfoDto.class);
        UserExtDto userExtDto = ConverterService.convert(userExtPo, UserExtDto.class);

        userDetailDto.setUserDto(userInfoDto);
        userDetailDto.setUserExtDto(userExtDto);

        if (user.getStoreInfPoList() != null) {
            List<StoreInfDto> storeInfDtoList = new ArrayList<>();
            for (StoreInfPo storeInfPo : user.getStoreInfPoList()) {
                if(storeInfPo.getStatus() != EStoreStatus.DELETE) {
                    StoreInfDto storeInfDto = ConverterService.convert(storeInfPo, StoreInfDto.class);
                    storeInfDtoList.add(storeInfDto);
                }
            }
            userDetailDto.setStoreInfPoList(storeInfDtoList);
        }

        if (user.getBankCardPoList() != null) {
            List<BankCardDto> bankCardDtoList = new ArrayList<>();
            for (BankCardPo bankCardPo : user.getBankCardPoList()) {
                BankCardDto bankCardDto = ConverterService.convert(bankCardPo, BankCardDto.class);
                bankCardDtoList.add(bankCardDto);
            }
            userDetailDto.setBankCardPoList(bankCardDtoList);
        }

        return userDetailDto;
    }

    @Override
    @Transactional
    public UserInfoDto saveUser(UserInfoDto userInfoDto) throws BizServiceException {
        UserInfoPo userInfoPo = ConverterService.convert(userInfoDto, UserInfoPo.class);
        if (!StringUtils.isEmpty(userInfoDto.getUserId())) {
            throw new BizServiceException(EErrorCode.ID_IS_NOT_NULL);
        }
        userInfoPo.setLoginPwd(userInfoDto.getLoginPassword());
        String encryptPwd;
        try {
            if(StringUtils.isEmpty(userInfoDto.getLoginPassword())){
                userInfoDto.setLoginPassword(RandomUtil.getNumberStr(8));
            }
            encryptPwd = EncryptUtil.encryptMd5(userInfoDto.getLoginPassword(), userInfoDto.getLoginPassword());
        } catch (Exception e) {
            throw new BizServiceException(EErrorCode.USER_PASSWORD_MD5_ERROR);
        }
        userInfoPo.setLoginPwd(encryptPwd);
        userInfoPo.setStatus(EUserStatus.NORMAL);
        userInfoPo.setIdentityAuth(false);
        userInfoPo.setStoreAuth(false);
        userInfoPo.setCashierFrozen(false);
        userInfoPo.setDealerUserId(userInfoDto.getDealerUserId());
        userDao.save(userInfoPo);
        userInfoDto.setUserId(userInfoPo.getUserId());

        //发送新增用户推送到会员中心通知
        this.addUserToCenter(userInfoPo);

        return userInfoDto;

    }

    @Override
    @Transactional
    public void updateUser(UserInfoDto userInfoDto) throws BizServiceException {
        UserInfoPo user = userDao.findOne(userInfoDto.getUserId());
        if (user == null) {
            throw new BizServiceException(EErrorCode.USER_NOT_EXIST);
        }
        if (StringUtils.isNotBlank(userInfoDto.getEmail())) {
            user.setEmail(userInfoDto.getEmail());
        }
        if (StringUtils.isNotBlank(userInfoDto.getIdCardNo())) {
            user.setIdCardNo(userInfoDto.getIdCardNo());
        }
        if (StringUtils.isNotBlank(userInfoDto.getMobile())) {
            user.setMobile(userInfoDto.getMobile());
        }
        if (StringUtils.isNotBlank(userInfoDto.getUserName())) {
            user.setUserName(userInfoDto.getUserName());
        }

        userDao.save(user);

        //发送更新用户推送到会员中心通知
        this.updateUserCenter(user);

    }

    @Override
    @Transactional
    public void updateStoreAuth(String userId) throws BizServiceException {
        //用户不存在
        UserInfoPo userInfoPo = userDao.findOne(userId);
        if (userInfoPo == null) {
            throw new BizServiceException(EErrorCode.USER_NOT_EXIST);
        }

        userInfoPo.setStoreAuth(true);
        userDao.save(userInfoPo);
    }

    @Override
    @Transactional
    public void updateCashierFrozen(String userId, Boolean cashierFrozen, String opid) {
        UserInfoPo userInfoPo = userDao.findOne(userId);
        if (userInfoPo == null) {
            throw new BizServiceException(EErrorCode.USER_NOT_EXIST);
        }
        if (!userInfoPo.getCashierFrozen().equals(cashierFrozen)) {
            userInfoPo.setCashierFrozen(cashierFrozen);
            userInfoPo.setLastMntTs(new Date());
            userInfoPo.setLastMntOpId(opid);
        }
    }

    @Override
    @Transactional
    public void updatePassword(PasswordDto passwordDto) throws BizServiceException {
        UserInfoPo user = userDao.findOne(passwordDto.getUserId());
        String encryptPwd;
        try {
            encryptPwd = EncryptUtil.encryptMd5(passwordDto.getNewPassword(), passwordDto.getNewPassword());
        } catch (Exception e) {
            throw new BizServiceException(EErrorCode.USER_PASSWORD_MD5_ERROR);
        }

        if(StringUtils.equals(encryptPwd,user.getDealPwd())){
            throw new BizServiceException(EErrorCode.USER_PASSWORD_AND_DEAL_PASSWORD_IS_SAME,"交易密码和登入密码不可相等");
        }

        user.setLoginPwd(encryptPwd);
        user.setLastMntTs(new Date());
        userDao.save(user);
    }

    @Override
    @Transactional
    public void updateDealPassword(PasswordDto passwordDto) throws BizServiceException {
        UserInfoPo user = userDao.findOne(passwordDto.getUserId());
        String encryptPwd;
        try {
            encryptPwd = EncryptUtil.encryptMd5(passwordDto.getDealPassword(), passwordDto.getDealPassword());
        } catch (Exception e) {
            throw new BizServiceException(EErrorCode.USER_PASSWORD_MD5_ERROR);
        }

        if(StringUtils.equals(encryptPwd,user.getLoginPwd())){
            throw new BizServiceException(EErrorCode.USER_PASSWORD_AND_DEAL_PASSWORD_IS_SAME,"交易密码和登入密码不可相等");
        }
        user.setDealPwd(encryptPwd);
        user.setLastMntTs(new Date());
        userDao.save(user);
    }

    @Override
    @Transactional
    public boolean certify(CertifyInfoDto certifyInfoDto) throws BizServiceException {
        UserInfoPo userInfoPo = userDao.findOne(certifyInfoDto.getUserId());
        if (userInfoPo == null) {
            throw new BizServiceException(EErrorCode.USER_NOT_EXIST);
        }

        if (StringUtils.isEmpty(certifyInfoDto.getIdCardNo())
                || StringUtils.isEmpty(certifyInfoDto.getUserName())) {
            throw new BizServiceException(EErrorCode.CERTIFY_INFO_IS_NOT_ALL);
        }

        String cardFrontBase64;
        try {
            List<PicturePo> picturePos =  pictureDao.findByParentIdAndPictureType(userInfoPo.getUserId(),EPictureType.CARD_FRONT);
            if(CollectionUtils.isEmpty(picturePos)){
                LOGGER.error("身份证正面图片不存在");
                throw new BizServiceException(EErrorCode.USER_IDENTITY_AUTH_ERROR);
            }
            String frontFilePath = picturePos.get(0).getPicturePath();
            File file = fileStoreService.download(frontFilePath.substring(0,frontFilePath.lastIndexOf("/")),
                    frontFilePath.substring(frontFilePath.lastIndexOf("/"),frontFilePath.length()));
            cardFrontBase64 = FileHelper.getBase64FromInputStream(new FileInputStream(file));

        } catch (Exception e) {
            LOGGER.error("读取身份证正面错误");
            throw new BizServiceException(EErrorCode.USER_IDENTITY_AUTH_ERROR);
        }

        CertificationDto certificationDto;
        try {
            certificationDto = yituService.certification(certifyInfoDto.getUserName(),
                    certifyInfoDto.getIdCardNo(),cardFrontBase64);
        } catch (Exception e) {
            LOGGER.error("yitu certification error");
            throw new BizServiceException(EErrorCode.USER_IDENTITY_AUTH_ERROR);
        }

        if(certificationDto.isValid() == false){
            throw new BizServiceException(EErrorCode.USER_IDENTITY_AUTH_ERROR);
        }

        userInfoPo.setIdCardNo(certifyInfoDto.getIdCardNo().toUpperCase());
        userInfoPo.setUserName(certifyInfoDto.getUserName());
        userInfoPo.setIdentityAuth(true);
        userInfoPo.setIdentityAuthDate(new Date());

        userDao.save(userInfoPo);

        //发送更新用户推送到会员中心通知
        this.updateUserCenter(userInfoPo);

        return true;
    }

    @Override
    @Transactional
    public boolean certifyWithCard(IDCardDto idCardDto) throws BizServiceException {
        this.saveIDCard(idCardDto);

        CertifyInfoDto certifyInfoDto = new CertifyInfoDto();
        certifyInfoDto.setUserId(idCardDto.getUserId());
        certifyInfoDto.setIdCardNo(idCardDto.getIdCardNo());
        certifyInfoDto.setUserName(idCardDto.getUserName());

        return this.certify(certifyInfoDto);

    }

    @Override
    @Transactional
    public void saveIDCard(IDCardDto idCardDto){
        List<PicturePo> picturePos = new ArrayList<>();
        PicturePo cardFront = new PicturePo();
        cardFront.setPictureType(EPictureType.CARD_FRONT);
        cardFront.setPicturePath(idCardDto.getCardFrontFilePath());
        cardFront.setParentId(idCardDto.getUserId());
        picturePos.add(cardFront);

        if (StringUtils.isNotEmpty(idCardDto.getCardBackFilePath())) {
            PicturePo cardBack = new PicturePo();
            cardBack.setPictureType(EPictureType.CARD_BACK);
            cardBack.setPicturePath(idCardDto.getCardBackFilePath());
            cardBack.setParentId(idCardDto.getUserId());
            picturePos.add(cardBack);
        }

        UserExtPo userExtPo = userExtDao.findOne(idCardDto.getUserId());
        if(null == userExtPo){
            userExtPo = new UserExtPo();
            userExtPo.setUserId(idCardDto.getUserId());
        }

        userExtPo.setNationAddress(idCardDto.getNationAddress());
        userExtPo.setSex(idCardDto.getSex());
        userExtPo.setNation(idCardDto.getNation());
        userExtPo.setBirthDate(idCardDto.getBirthDate());
        userExtPo.setIdAuthority(idCardDto.getIdAuthority());
        userExtPo.setIdExpiredBegin(idCardDto.getIdExpiredBegin());
        userExtPo.setIdExpiredEnd(idCardDto.getIdExpiredEnd());

        pictureDao.save(picturePos);
        userExtDao.save(userExtPo);

        UserInfoPo userInfoPo = userDao.findOne(idCardDto.getUserId());

        this.updateUserCenter(userInfoPo,userExtPo);

    }

    @Override
    @Transactional
    public void updateIdentityAuth(CertifyInfoDto certifyInfoDto) throws BizServiceException {
        //用户不存在
        UserInfoPo userInfoPo = userDao.findOne(certifyInfoDto.getUserId());
        if (userInfoPo == null) {
            throw new BizServiceException(EErrorCode.USER_NOT_EXIST);
        }

        if (StringUtils.isEmpty(certifyInfoDto.getIdCardNo())
                || StringUtils.isEmpty(certifyInfoDto.getUserName())) {
            throw new BizServiceException(EErrorCode.CERTIFY_INFO_IS_NOT_ALL);
        }

        userInfoPo.setIdCardNo(certifyInfoDto.getIdCardNo());
        userInfoPo.setUserName(certifyInfoDto.getUserName());
        userInfoPo.setIdentityAuth(true);
        userInfoPo.setIdentityAuthDate(new Date());
        userDao.save(userInfoPo);
    }

    @Override
    @Transactional(readOnly = true)
    public UserSearchDto getUserPage(UserSearchDto userSearchDto) {
        Specification<UserInfoPo> spec = (Root<UserInfoPo> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            Predicate predicate = cb.conjunction();
            List<Expression<Boolean>> expressions = predicate.getExpressions();
//            ListJoin<UserInfoPo,StoreInfPo> userJoin = root.join(root.getModel().getList("storeInfPoList",StoreInfPo.class), JoinType.LEFT);
            Subquery<Long> subQuery = query.subquery(Long.class);
            Root<StoreInfPo> storeInfPoRoot = subQuery.from(StoreInfPo.class);
            subQuery.select(storeInfPoRoot.get("storeId"));
            Predicate subPredicate = cb.conjunction();
            List<Expression<Boolean>> subExpressions = subPredicate.getExpressions();

            if (null != userSearchDto) {
                if (!StringUtils.isEmpty(userSearchDto.getIdCardNo())) {
                    expressions.add(cb.like(root.get("idCardNo"), BizUtil.filterStringRight(userSearchDto.getIdCardNo())));
                }
                if (!StringUtils.isEmpty(userSearchDto.getUserName())) {
                    expressions.add(cb.like(root.get("userName"), BizUtil.filterString(userSearchDto.getUserName())));
                }
                if (!StringUtils.isEmpty(userSearchDto.getMobile())) {
                    expressions.add(cb.like(root.get("mobile"), BizUtil.filterString(userSearchDto.getMobile())));
                }
                if (!StringUtils.isEmpty(userSearchDto.getEmail())) {
                    expressions.add(cb.like(root.get("email"), BizUtil.filterStringRight(userSearchDto.getEmail())));
                }
                if (null != userSearchDto.getUserSource()) {
                    expressions.add(cb.equal(root.get("source"), userSearchDto.getUserSource()));
                }
                if (null != userSearchDto.getStoreSource()) {
                    expressions.add(cb.equal(storeInfPoRoot.get("source"), userSearchDto.getStoreSource()));
                }
                if (!StringUtils.isEmpty(userSearchDto.getStoreName())) {
                    //expressions.add(cb.equal(userJoin.get("area"), userSearchDto.getArea()));
                    subExpressions.add(cb.equal(storeInfPoRoot.get("storeName"), userSearchDto.getStoreName()));
                    //expressions.add(cb.exists(subQuery));
                }
                if (!StringUtils.isEmpty(userSearchDto.getArea())) {
                    //expressions.add(cb.equal(userJoin.get("area"), userSearchDto.getArea()));
                    subExpressions.add(cb.equal(storeInfPoRoot.get("area"), userSearchDto.getArea()));
                    //expressions.add(cb.exists(subQuery));
                }
                if (!StringUtils.isEmpty(userSearchDto.getCity())) {
                    //expressions.add(cb.equal(userJoin.get("city"), userSearchDto.getCity()));
                    subExpressions.add(cb.equal(storeInfPoRoot.get("city"), userSearchDto.getCity()));
                    //expressions.add(cb.exists(subQuery));
                }
                if (!StringUtils.isEmpty(userSearchDto.getProvince())) {
                    //expressions.add(cb.equal(userJoin.get("province"), userSearchDto.getProvince()));
                    subExpressions.add(cb.equal(storeInfPoRoot.get("province"), userSearchDto.getProvince()));
                    //expressions.add(cb.exists(subQuery));
                }
                if (!StringUtils.isEmpty(userSearchDto.getStreet())) {
                    subExpressions.add(cb.equal(storeInfPoRoot.get("street"), userSearchDto.getStreet()));
                }
                if (!StringUtils.isEmpty(userSearchDto.getProvinceId())) {
                    subExpressions.add(cb.equal(storeInfPoRoot.get("provinceId"), userSearchDto.getProvinceId()));
                }
                if (!StringUtils.isEmpty(userSearchDto.getCityId())) {
                    subExpressions.add(cb.equal(storeInfPoRoot.get("cityId"), userSearchDto.getCityId()));
                }
                if (!StringUtils.isEmpty(userSearchDto.getAreaId())) {
                    subExpressions.add(cb.equal(storeInfPoRoot.get("areaId"), userSearchDto.getAreaId()));
                }
                if (!StringUtils.isEmpty(userSearchDto.getStreetId())) {
                    subExpressions.add(cb.equal(storeInfPoRoot.get("streetId"), userSearchDto.getStreetId()));
                }
                if (!StringUtils.isEmpty(userSearchDto.getTobaccoCertificateNo())) {
                    subExpressions.add(cb.equal(storeInfPoRoot.get("tobaccoCertificateNo"), userSearchDto.getTobaccoCertificateNo()));
                }
                if (null != userSearchDto.getIdentityAuth()) {
                    expressions.add(cb.equal(root.get("identityAuth"), userSearchDto.getIdentityAuth()));
                }
                if (null != userSearchDto.getStoreAuth()) {
                    expressions.add(cb.equal(root.get("storeAuth"), userSearchDto.getStoreAuth()));
                }
                if (null != userSearchDto.getBankAuth()) {
                    expressions.add(cb.equal(root.get("bankAuth"), userSearchDto.getBankAuth()));
                }
                if (!StringUtils.isEmpty(userSearchDto.getRegisterStartDate())) {
                    expressions.add(cb.greaterThanOrEqualTo(root.get("createTs"), DateHelper.getStartDate(userSearchDto.getRegisterStartDate())));
                }
                if (!StringUtils.isEmpty(userSearchDto.getRegisterEndDate())) {
                    expressions.add(cb.lessThanOrEqualTo(root.get("createTs"), DateHelper.getEndDate(userSearchDto.getRegisterEndDate())));
                }
                if (!StringUtils.isEmpty(userSearchDto.getIdAuthStartDate())) {
                    expressions.add(cb.greaterThanOrEqualTo(root.get("identityAuthDate"),
                            DateHelper.getStartDate(userSearchDto.getIdAuthStartDate())));
                }
                if (!StringUtils.isEmpty(userSearchDto.getIdAuthEndDate())) {
                    expressions.add(cb.lessThanOrEqualTo(root.get("identityAuthDate"),
                            DateHelper.getEndDate(userSearchDto.getIdAuthEndDate())));
                }
                if (!StringUtils.isEmpty(userSearchDto.getLastMntStartDate())) {
                    expressions.add(cb.greaterThanOrEqualTo(root.get("lastMntTs"),
                            DateHelper.getStartDate(userSearchDto.getLastMntStartDate())));
                }
                if (userSearchDto.getCashierFrozen() != null) {
                    expressions.add(cb.equal(root.get("cashierFrozen"), userSearchDto.getCashierFrozen()));
                }

                expressions.add(cb.equal(root.get("status"), EUserStatus.NORMAL));
                if (subExpressions.size() > 0) {
                    subExpressions.add(cb.equal(storeInfPoRoot.get("status"), EStoreStatus.NORMAL));
                    subExpressions.add(cb.equal(root.get("userId"), storeInfPoRoot.get("userId")));
                    expressions.add(cb.exists(subQuery));
                    subQuery.where(subPredicate);
                }

            }
            return predicate;
        };

        Pageable pageable = new PageRequest(userSearchDto.getCurrentPage() - 1,
                userSearchDto.getPageSize(),Direction.DESC,"createTs");
        Page<UserInfoPo> page = userDao.findAll(spec, pageable);

        List<UserInfoDto> data = new ArrayList<>();
        for (UserInfoPo po : page.getContent()) {
            UserInfoDto userInfoDto = ConverterService.convert(po, UserInfoDto.class);
            data.add(userInfoDto);
        }
        userSearchDto.setList(data);
        userSearchDto.setTotalPages(page.getTotalPages());
        userSearchDto.setTotalRecord(page.getTotalElements());
        return userSearchDto;
    }

    @Override
    public UserInfoDto getUserInfoByTobaccoNo(String tobaccoNo) {
        UserInfoPo userInfoPo = userDao.findByTobaccoNo(tobaccoNo);
        if (userInfoPo != null)
            return ConverterService.convert(userInfoPo, UserInfoDto.class);
        return null;
    }

    @Override
    public Boolean verifyDealPassword(String userId, String dealPassword) {
        UserInfoPo userInfoPo = userDao.findOne(userId);
        if (userInfoPo == null) {
            throw new BizServiceException(EErrorCode.USER_NOT_EXIST);
        }

        if(StringUtils.isEmpty(userInfoPo.getDealPwd())){
            return Boolean.FALSE;
        }

        Boolean matched = Boolean.FALSE;
        try {
            matched = userInfoPo.getDealPwd().equals(EncryptUtil.encryptMd5(dealPassword, dealPassword));
        } catch (Exception e) {
            throw new BizServiceException(EErrorCode.USER_PASSWORD_MD5_ERROR);
        }

        return matched;
    }

    @Transactional
    public void saveUserAndStore(UserInfoDto userInfoDto, StoreInfDto storeInfDto) throws BizServiceException {
        //用户注册
        UserInfoPo userInfoPo = ConverterService.convert(userInfoDto, UserInfoPo.class);
        if (!StringUtils.isEmpty(userInfoDto.getUserId())) {
            throw new BizServiceException(EErrorCode.ID_IS_NOT_NULL);
        }
        userInfoPo.setLoginPwd(userInfoDto.getLoginPassword());
        String encryptPwd;
        try {
            encryptPwd = EncryptUtil.encryptMd5(userInfoDto.getLoginPassword(), userInfoDto.getLoginPassword());
        } catch (Exception e) {
            throw new BizServiceException(EErrorCode.USER_PASSWORD_MD5_ERROR);
        }
        userInfoPo.setLoginPwd(encryptPwd);
        userInfoPo.setStatus(EUserStatus.NORMAL);
        userDao.save(userInfoPo);
        //店铺注册
        if (null != storeInfDto.getStoreId()) {
            throw new BizServiceException(EErrorCode.ID_IS_NOT_NULL);
        }
        storeInfDto.setUserId(userInfoPo.getUserId());
        storeInfDto.setAddress(StringUtils.trimToNull(storeInfDto.getAddress()));
        storeInfDto.setTobaccoCertificateNo(StringUtils.trimToNull(storeInfDto.getTobaccoCertificateNo()));
        storeInfDto.setStoreName(StringUtils.trimToNull(storeInfDto.getStoreName()));
        if (!StringUtils.isEmpty(storeInfDto.getTobaccoCertificateNo())) {
            StoreInfPo store = storeDao.findByTobaccoCertificateNoAndStatus(storeInfDto.getTobaccoCertificateNo(), EStoreStatus.NORMAL);
            if (store != null) {
                throw new BizServiceException(EErrorCode.TOBACCO_CERTIFICATE_NO_IS_EXIST);
            }
        }
        StoreInfPo storeInfPo = ConverterService.convert(storeInfDto, StoreInfPo.class);
        storeInfPo.setStatus(EStoreStatus.NORMAL);
        storeInfPo.setFullAddress(storeInfPo.getStreet() + storeInfPo.getAddress());
        storeDao.save(storeInfPo);
    }

    @Override
    @Transactional
    public void updateDealerUserId(String userId,String dealerUserId) {
        UserInfoPo userInfoPo = userDao.findOne(userId);
        userInfoPo.setDealerUserId(dealerUserId);
        userDao.save(userInfoPo);
    }

    @Override
    public String getDealPwd(String userId) throws BizServiceException {
        UserInfoPo po = userDao.findOne(userId);
        if (Objects.nonNull(po)){
            return po.getDealPwd();
        }
        return null;
    }

    private void addUserToCenter(UserInfoPo userInfoPo){
        try {
            CenterUserDto centerUserDto = new CenterUserDto();
            centerUserDto.setUserId(userInfoPo.getUserId());
            centerUserDto.setMobile(userInfoPo.getMobile());
            centerUserDto.setUsername(userInfoPo.getMobile());
            queueSender.send(DestinationDefine.ADD_USER_TO_CENTER, JSON.toJSONString(centerUserDto));
        }catch (Exception e){
            LOGGER.error("发送centerMQ失败",e);
        }
    }

    @Override
    @Transactional
    public String saveFromUserCenter(CenterUserDto centerUserDto) throws BizServiceException {
        UserInfoPo userInfoPo = userDao.findByUuid(centerUserDto.getUuid());
        if(userInfoPo != null && userInfoPo.getIdentityAuth()){
            UserExtPo userExtPo = userExtDao.findOne(userInfoPo.getUserId());
            if(null == userExtPo) {
                this.updateUserCenter(userInfoPo);
            }else{
                this.updateUserCenter(userInfoPo,userExtPo);
            }
            return "用户已实名";
        }

        if(userInfoPo == null) {
            //新增的用户
            UserInfoPo userInfoPoTemp = userDao.findByMobile(centerUserDto.getMobile());
            if(userInfoPoTemp != null){
                return "该用户已存在";
            }
            userInfoPo = new UserInfoPo();
            String encryptPwd;
            try {
                encryptPwd = EncryptUtil.encryptMd5(RandomUtil.getNumberStr(6), RandomUtil.getNumberStr(6));
            } catch (Exception e) {
                throw new BizServiceException(EErrorCode.USER_PASSWORD_MD5_ERROR);
            }
            userInfoPo.setLoginPwd(encryptPwd);

            userInfoPo.setUuid(centerUserDto.getUuid());
            userInfoPo.setIdCardNo(centerUserDto.getIdCardNumber());
            userInfoPo.setUserName(centerUserDto.getRealName());
            userInfoPo.setMobile(centerUserDto.getMobile());
            userInfoPo.setStatus(EUserStatus.NORMAL);
            userInfoPo.setBankAuth(false);
            userInfoPo.setIdentityAuth(false);
            userInfoPo.setStoreAuth(false);
            userInfoPo.setCashierFrozen(false);
            userInfoPo.setSource(ESource.THIRD_PARTY);
            userDao.save(userInfoPo);
        }

        UserExtPo userExtPo = userExtDao.findOne(userInfoPo.getUserId());
        if(userExtPo == null){
            userExtPo = new UserExtPo();
        }

      /*  if(StringUtils.isEmpty(centerUserDto.getUuid())) {
            if(centerUserDto.getGender() != null) {
                if (centerUserDto.getGender() == 2) {
                    userExtPo.setSex("男");
                } else if (centerUserDto.getGender() == 1) {
                    userExtPo.setSex("女");
                }
            }
            userExtPo.setBirthDate(DateHelper.getDate(centerUserDto.getBrithday(), "yyyy-MM-dd HH:mm:ss"));
            userExtPo.setIdExpiredBegin(DateHelper.getDate(centerUserDto.getIdCardStart(), "yyyy-MM-dd HH:mm:ss"));
            userExtPo.setIdExpiredEnd(DateHelper.getDate(centerUserDto.getIdCardEnd(), "yyyy-MM-dd HH:mm:ss"));
        }*/

        userExtPo.setUserId(userInfoPo.getUserId());
        userExtPo.setProvince(centerUserDto.getProvince());
        userExtPo.setCity(centerUserDto.getCity());
        userExtPo.setArea(centerUserDto.getArea());
        //userExtPo.setStreet(centerUserDto.getStreet());
        userExtPo.setProvinceId(centerUserDto.getProvinceId());
        userExtPo.setCityId(centerUserDto.getCityId());
        userExtPo.setAreaId(centerUserDto.getAreaId());
        //userExtPo.setStreetId(centerUserDto.getStreetId());
        userExtPo.setAddress(centerUserDto.getHomeAddress());


        userExtDao.save(userExtPo);

        return null;

    }

    private void updateUserCenter(UserInfoPo userInfoPo){
        try{
            CenterUserDto centerUserDto = new CenterUserDto();
            centerUserDto.setUserId(userInfoPo.getUserId());
            centerUserDto.setUuid(userInfoPo.getUuid());
            centerUserDto.setMobile(userInfoPo.getMobile());
            centerUserDto.setUsername(userInfoPo.getMobile());
            centerUserDto.setIdCardNumber(userInfoPo.getIdCardNo());
            centerUserDto.setRealName(userInfoPo.getUserName());
            if(userInfoPo.getIdentityAuth()) {
                centerUserDto.setIdentityAuth(1);
            }else{
                centerUserDto.setIdentityAuth(0);
            }
            centerUserDto.setIdentityAuthDate(DateHelper.formatDate(userInfoPo.getIdentityAuthDate(),"yyyy-MM-dd HH:mm:ss"));

            LOGGER.info("发送centerMQ" + JSON.toJSONString(centerUserDto));
            queueSender.send(DestinationDefine.ADD_USER_TO_CENTER, JSON.toJSONString(centerUserDto));
        }catch (Exception e) {
            LOGGER.error("发送centerMQ失败", e);
        }
    }

    private void updateUserCenter(UserInfoPo userInfoPo,UserExtPo userExtPo){
        try{
            CenterUserDto centerUserDto = new CenterUserDto();
            centerUserDto.setUserId(userInfoPo.getUserId());
            centerUserDto.setUuid(userInfoPo.getUuid());
            centerUserDto.setUsername(userInfoPo.getMobile());
            centerUserDto.setIdCardNumber(userInfoPo.getIdCardNo());
            centerUserDto.setRealName(userInfoPo.getUserName());
            centerUserDto.setMobile(userInfoPo.getMobile());
            centerUserDto.setIdentityAuthDate(DateHelper.formatDate(userInfoPo.getIdentityAuthDate(),"yyyy-MM-dd HH:mm:ss"));
            if(userInfoPo.getIdentityAuth()) {
                centerUserDto.setIdentityAuth(1);
            }else{
                centerUserDto.setIdentityAuth(0);
            }

            if("男".equals(userExtPo.getSex())){
                centerUserDto.setGender(2);
            }else if("女".equals(userExtPo.getSex())){
                centerUserDto.setGender(1);
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
            LOGGER.info("发送centerMQ" + JSON.toJSONString(centerUserDto));
            queueSender.send(DestinationDefine.ADD_USER_TO_CENTER, JSON.toJSONString(centerUserDto));
        }catch (Exception e) {
            LOGGER.error("发送centerMQ失败", e);
        }
    }

}
