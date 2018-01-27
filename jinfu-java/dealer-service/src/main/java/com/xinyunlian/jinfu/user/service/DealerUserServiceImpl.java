package com.xinyunlian.jinfu.user.service;

import com.xinyunlian.jinfu.common.cache.constant.CacheType;
import com.xinyunlian.jinfu.common.converter.ConverterService;
import com.xinyunlian.jinfu.common.entity.id.Context;
import com.xinyunlian.jinfu.common.entity.id.IdUtil;
import com.xinyunlian.jinfu.common.enums.EErrorCode;
import com.xinyunlian.jinfu.common.exception.BizServiceException;
import com.xinyunlian.jinfu.common.security.StaticResourceSecurity;
import com.xinyunlian.jinfu.common.util.ApplicationContextUtil;
import com.xinyunlian.jinfu.common.util.EncryptUtil;
import com.xinyunlian.jinfu.dealer.dto.DealerDto;
import com.xinyunlian.jinfu.dealer.seqProducer.PartnerCodeSeqProducer;
import com.xinyunlian.jinfu.user.dao.DealerUserDao;
import com.xinyunlian.jinfu.user.dto.DealerUserDto;
import com.xinyunlian.jinfu.user.dto.DealerUserPwdDto;
import com.xinyunlian.jinfu.user.dto.DealerUserQRInfo;
import com.xinyunlian.jinfu.user.entity.DealerUserPo;
import com.xinyunlian.jinfu.user.enums.EDealerUserStatus;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static java.lang.System.currentTimeMillis;

/**
 * Created by menglei on 2016年08月26日.
 */
@Service
public class DealerUserServiceImpl implements DealerUserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(DealerUserServiceImpl.class);

    private final static String DEALER_USER_QR_CODE_REDEIS= "DEALER_USER_QR_CODE_";
    @Value("${file.addr}")
    private String fileAddr;

    @Value("${qrcode.type}")
    private String qrcodeType;

    @Value("${qrcode.key}")
    private String qrcodeKey;

    @Autowired
    private DealerUserDao dealerUserDao;
    @Autowired
    private RedisCacheManager redisCacheManager;

    @Override
    @Transactional(readOnly = true)
    public DealerUserDto getDealerUserById(String userId) {
        DealerUserDto dealerUserDto = null;
        if (userId != null) {
            DealerUserPo dealerUserPo = dealerUserDao.findOne(userId);
            if (dealerUserPo != null && dealerUserPo.getStatus() != EDealerUserStatus.DELETE) {
                dealerUserDto = ConverterService.convert(dealerUserPo, DealerUserDto.class);
                if (!StringUtils.isEmpty(dealerUserDto.getLinePic())){
                    dealerUserDto.setLinePic(fileAddr + StaticResourceSecurity.getSecurityURI(dealerUserDto.getLinePic()));
                }
                if (!StringUtils.isEmpty(dealerUserDto.getLivePic())){
                    dealerUserDto.setLivePic(fileAddr + StaticResourceSecurity.getSecurityURI(dealerUserDto.getLivePic()));
                }
                if (dealerUserPo.getDealerPo() != null) {
                    DealerDto dealerDto = ConverterService.convert(dealerUserPo.getDealerPo(), DealerDto.class);
                    dealerUserDto.setDealerDto(dealerDto);
                }
            }
        }
        return dealerUserDto;
    }

    /**
     * 查询分销员列表
     *
     * @param dealerUserDto
     * @return
     */
    @Override
    public List<DealerUserDto> getDealerUserList(DealerUserDto dealerUserDto) {
        Specification<DealerUserPo> spec = (Root<DealerUserPo> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            Predicate predicate = cb.conjunction();
            List<Expression<Boolean>> expressions = predicate.getExpressions();
            if (null != dealerUserDto) {
                if (StringUtils.isNotEmpty(dealerUserDto.getDealerId())) {
                    expressions.add(cb.equal(root.get("dealerId"), dealerUserDto.getDealerId()));
                }
                if (StringUtils.isNotEmpty(dealerUserDto.getName())) {
                    expressions.add(cb.like(root.get("name"), "%" + dealerUserDto.getName() + "%"));
                }
                if (StringUtils.isNotEmpty(dealerUserDto.getMobile())) {
                    expressions.add(cb.like(root.get("mobile"), dealerUserDto.getMobile() + "%"));
                }
                if (StringUtils.isNotEmpty(dealerUserDto.getGroupId())) {
                    expressions.add(cb.equal(root.get("groupId"), dealerUserDto.getGroupId()));
                }
                if (dealerUserDto.getExamPassed() != null) {
                    expressions.add(cb.equal(root.get("examPassed"), dealerUserDto.getExamPassed()));
                }
                if (dealerUserDto.getAdmin() != null) {
                    expressions.add(cb.equal(root.get("isAdmin"), dealerUserDto.getAdmin()));
                }
                if ((EDealerUserStatus.FROZEN).equals(dealerUserDto.getStatus())) {
                    expressions.add(cb.equal(root.get("status"), EDealerUserStatus.FROZEN));
                } else if ((EDealerUserStatus.NORMAL).equals(dealerUserDto.getStatus())) {
                    expressions.add(cb.equal(root.get("status"), EDealerUserStatus.NORMAL));
                } else {
                    expressions.add(cb.notEqual(root.get("status"), EDealerUserStatus.DELETE));
                }
            }
            return predicate;
        };
        Sort sore = new Sort(Sort.Direction.DESC, "userId");
        List<DealerUserPo> list = dealerUserDao.findAll(spec, sore);
        List<DealerUserDto> result = new ArrayList<>();
        if (!CollectionUtils.isEmpty(list)) {
            for (DealerUserPo po : list) {
                DealerUserDto dto = ConverterService.convert(po, DealerUserDto.class);
                result.add(dto);
            }
        }
        return result;
    }

    @Override
    public DealerUserDto findDealerUserByMobile(String mobile) {
        DealerUserPo user = dealerUserDao.findByMobile(mobile);
        return ConverterService.convert(user, DealerUserDto.class);
    }

    @Override
    public List<DealerUserDto> findDealerUsersByDealerId(String dealerId) {
        List<DealerUserDto> rs = new ArrayList<>();
        List<DealerUserPo> list = dealerUserDao.findByDealerId(dealerId);
        for (DealerUserPo dealerUserPo : list) {
            rs.add(ConverterService.convert(dealerUserPo, DealerUserDto.class));
        }
        return rs;
    }

    @Transactional
    @Override
    public void createDealerUser(DealerUserDto dealerUserDto) throws BizServiceException {
        if (dealerUserDto != null) {
            DealerUserPo po = ConverterService.convert(dealerUserDto, DealerUserPo.class);
            String encryptPwd = StringUtils.EMPTY;
            try {
                String password = EncryptUtil.encryptMd5("111111");
                encryptPwd = EncryptUtil.encryptMd5(password, password);
            } catch (Exception e) {
                LOGGER.error("MD5加密失败", e);
                throw new BizServiceException(EErrorCode.DEALER_USER_PASSWORD_ENCRYPT_ERROR, "用户密码加密失败", e);
            }
            po.setPassword(encryptPwd);
            po.setStatus(EDealerUserStatus.NORMAL);
            po.setExamPassed(false);
            if (dealerUserDto.getAdmin() != null) {
                po.setAdmin(dealerUserDto.getAdmin());
            } else {
                po.setAdmin(false);
            }
            dealerUserDao.save(po);
        }
    }

    @Transactional
    @Override
    public void updateDealerUser(DealerUserDto dealerUserDto) throws BizServiceException {
        if (dealerUserDto != null) {
            DealerUserPo dealerUserPo = dealerUserDao.findOne(dealerUserDto.getUserId());
            if (dealerUserPo == null || dealerUserPo.getStatus() == EDealerUserStatus.DELETE) {
                throw new BizServiceException(EErrorCode.DEALER_USER_NOT_FOUND);
            }
            if(dealerUserPo.getIdentityAuth() == null || !dealerUserPo.getIdentityAuth()) {
                dealerUserPo.setName(dealerUserDto.getName());
                dealerUserPo.setIdCardNo(dealerUserDto.getIdCardNo());
            }
            if(dealerUserPo.getPassed() == null || !dealerUserPo.getPassed()){
                dealerUserPo.setPassed(dealerUserDto.getPassed());
            }
            if (dealerUserDto.getAdmin() != null) {
                dealerUserPo.setAdmin(dealerUserDto.getAdmin());
            }

            dealerUserDao.save(dealerUserPo);
        }
    }

    @Transactional
    @Override
    public void idAuth(DealerUserDto dealerUserDto) throws BizServiceException {
        if (dealerUserDto != null) {
            DealerUserPo dealerUserPo = dealerUserDao.findOne(dealerUserDto.getUserId());
            if (dealerUserPo == null || dealerUserPo.getStatus() == EDealerUserStatus.DELETE) {
                throw new BizServiceException(EErrorCode.DEALER_USER_NOT_FOUND);
            }
            dealerUserPo.setIdBackPic(dealerUserDto.getIdBackPic());
            dealerUserPo.setIdFrontPic(dealerUserDto.getIdFrontPic());
            dealerUserPo.setLinePic(dealerUserDto.getLinePic());
            dealerUserPo.setIdCardNo(dealerUserDto.getIdCardNo());
            dealerUserPo.setIdentityAuth(true);
            dealerUserPo.setIdentityAuthDate(new Date());
            dealerUserPo.setName(dealerUserDto.getName());
            String mobile = dealerUserPo.getMobile();
            dealerUserPo.setQrCode(this.getQrCode(mobile.substring(mobile.length()-4,mobile.length())));
            try {
                String qrCodeKey = EncryptUtil.encryptMd5(qrcodeType + "&" + dealerUserPo.getQrCode() + "&" + qrcodeKey);
                dealerUserPo.setQrCodeKey(qrCodeKey.substring(3,6));
            } catch (Exception e) {
                LOGGER.error("MD5加密失败", e);
                throw new BizServiceException(EErrorCode.USER_PASSWORD_MD5_ERROR);
            }
            dealerUserDao.save(dealerUserPo);
        }
    }

    @Transactional
    @Override
    public void lived(DealerUserDto dealerUserDto) throws BizServiceException {
        if (dealerUserDto != null) {
            DealerUserPo dealerUserPo = dealerUserDao.findOne(dealerUserDto.getUserId());
            if (dealerUserPo == null || dealerUserPo.getStatus() == EDealerUserStatus.DELETE) {
                throw new BizServiceException(EErrorCode.DEALER_USER_NOT_FOUND);
            }

            dealerUserPo.setLived(true);
            dealerUserPo.setYituIsPass(dealerUserDto.getYituIsPass());
            dealerUserPo.setYituSimilarity(dealerUserDto.getYituSimilarity());
            dealerUserPo.setLivePic(dealerUserDto.getLivePic());
            if(null != dealerUserDto.getYituSimilarity() && dealerUserDto.getYituSimilarity() > 75){
                dealerUserPo.setPassed(true);
            }
            dealerUserDao.save(dealerUserPo);
        }
    }

    @Transactional
    @Override
    public void updateDealerUserGroup(DealerUserDto dealerUserDto) throws BizServiceException {
        if (dealerUserDto != null) {
            DealerUserPo dealerUserPo = dealerUserDao.findOne(dealerUserDto.getUserId());
            if (dealerUserPo == null || dealerUserPo.getStatus() == EDealerUserStatus.DELETE) {
                throw new BizServiceException(EErrorCode.DEALER_USER_NOT_FOUND);
            }
            dealerUserPo.setGroupId(dealerUserDto.getGroupId());
            dealerUserDao.save(dealerUserPo);
        }
    }

    @Transactional
    @Override
    public void updateDealerUserPwd(DealerUserPwdDto dealerUserPwdDto) throws BizServiceException {
        if (dealerUserPwdDto != null) {
            DealerUserPo dealerUserPo = dealerUserDao.findOne(dealerUserPwdDto.getUserId());
            if (dealerUserPo == null || dealerUserPo.getStatus() == EDealerUserStatus.DELETE) {
                throw new BizServiceException(EErrorCode.DEALER_USER_NOT_FOUND);
            }
            String encryptPwd = StringUtils.EMPTY;
            String encryptNewPwd = StringUtils.EMPTY;
            if (StringUtils.isEmpty(dealerUserPwdDto.getNewPassword()) && StringUtils.isEmpty(dealerUserPwdDto.getOldPassword())) {
                //重置密码
                try {
                    String password = EncryptUtil.encryptMd5("111111");
                    encryptNewPwd = EncryptUtil.encryptMd5(password, password);
                } catch (Exception e) {
                    LOGGER.error("MD5加密失败", e);
                    throw new BizServiceException(EErrorCode.DEALER_USER_PASSWORD_ENCRYPT_ERROR);
                }
            } else {
                //修改密码
                try {
                    encryptPwd = EncryptUtil.encryptMd5(dealerUserPwdDto.getOldPassword(), dealerUserPwdDto.getOldPassword());
                    encryptNewPwd = EncryptUtil.encryptMd5(dealerUserPwdDto.getNewPassword(), dealerUserPwdDto.getNewPassword());
                } catch (Exception e) {
                    LOGGER.error("MD5加密失败", e);
                    throw new BizServiceException(EErrorCode.DEALER_USER_PASSWORD_ENCRYPT_ERROR);
                }
                if (!encryptPwd.equals(dealerUserPo.getPassword())) {
                    throw new BizServiceException(EErrorCode.DEALER_USER_PASSWORD_ERROR);
                }
            }
            dealerUserPo.setPassword(encryptNewPwd);
            dealerUserPo.setLastMntTs(new Date());
            dealerUserDao.save(dealerUserPo);
        }
    }

    @Transactional
    @Override
    public void deleteDealerUser(String userId) throws BizServiceException {
        DealerUserPo dealerUserPo = dealerUserDao.findOne(userId);
        if (dealerUserPo == null || dealerUserPo.getStatus() == EDealerUserStatus.DELETE) {
            throw new BizServiceException(EErrorCode.DEALER_USER_NOT_FOUND);
        }
        dealerUserPo.setMobile("delete" + currentTimeMillis() + "-" + dealerUserPo.getMobile());
        dealerUserPo.setStatus(EDealerUserStatus.DELETE);
        dealerUserDao.save(dealerUserPo);
    }

    @Override
    public List<DealerUserDto> getDealerUserIdsByAddressIDS(List<String> list) {
        List<DealerUserDto> userDtos= new ArrayList<>();

        List<Object[]> userPos= dealerUserDao.findUsersWithAddressIds(list);
        if (userPos != null) {
            userPos.forEach(obj -> {
                DealerUserDto storeInfDto = new DealerUserDto();
                storeInfDto.setUserId(obj[0].toString());
                storeInfDto.setMobile(obj[1].toString());
                userDtos.add(storeInfDto);
            });
        }

        return userDtos;
    }

    @Override
    public List<DealerUserDto> getAllDealerUsers() {
        List<DealerUserDto> userDtos= new ArrayList<>();

        List<Object[]> userPos= dealerUserDao.findAllDealerUsers();
        if (userPos != null) {
            userPos.forEach(obj -> {
                DealerUserDto storeInfDto = new DealerUserDto();
                storeInfDto.setUserId(obj[0].toString());
                storeInfDto.setMobile(obj[1].toString());
                userDtos.add(storeInfDto);
            });
        }
        return userDtos;
    }

    @Override
    public List<DealerUserDto> findByDealerUserIds(List<String> dealerUserIds) {
        List<DealerUserDto> rs = new ArrayList<>();

        if (!CollectionUtils.isEmpty(dealerUserIds)){
            List<DealerUserPo> list = dealerUserDao.findByUserIdIn(dealerUserIds);
            for (DealerUserPo dealerUserPo : list) {
                rs.add(ConverterService.convert(dealerUserPo, DealerUserDto.class));
            }
        }

        return rs;
    }

    @Override
    public List<DealerUserDto> findByNameLike(String name) {
        List<DealerUserDto> rs = new ArrayList<>();
        List<DealerUserPo> list = dealerUserDao.findByNameLike(name);
        for (DealerUserPo dealerUserPo : list) {
            rs.add(ConverterService.convert(dealerUserPo, DealerUserDto.class));
        }
        return rs;
    }

    private String getQrCode(String mobile){
        Context context = new Context("DEALER_USER_QR_CODE");
        context.setParam(mobile);
        //二维码规则：生成时间6位（年月日）+业务员手机号后四位+2位自增
        return IdUtil.produce(ApplicationContextUtil.getBean(PartnerCodeSeqProducer.class), context);
    }

    public DealerUserQRInfo getQrInfo(String userId){
        DealerUserQRInfo qrInfo = new DealerUserQRInfo();
        DealerUserPo dealerUserPo = dealerUserDao.findOne(userId);

        if(!StringUtils.isEmpty(dealerUserPo.getQrCode())) {
            long curTime = System.currentTimeMillis();
            qrInfo.setName(dealerUserPo.getName());
            qrInfo.setQrKey(dealerUserPo.getQrCodeKey());
            qrInfo.setQrCode(dealerUserPo.getQrCode());
            qrInfo.setQrType(qrcodeType);
            qrInfo.setCreateTime(String.valueOf(curTime));
            qrInfo.setUserId(dealerUserPo.getUserId());
            qrInfo.setLivePic(dealerUserPo.getLivePic());
            qrInfo.setMobile(dealerUserPo.getMobile());
            String qrUrl = qrcodeType + "/" + dealerUserPo.getQrCode()
                    + "/" + qrInfo.getQrKey() + "/" + curTime;
            redisCacheManager.getCache(CacheType.PARTNER_QR_CODE)
                    .put(DEALER_USER_QR_CODE_REDEIS + dealerUserPo.getQrCode(), qrInfo);
            qrInfo.setQrUrl(qrUrl);
        }
        return qrInfo;
    }

    @Override
    public DealerUserQRInfo checkQrInfo(DealerUserQRInfo qrInfo) {
        qrInfo.setQrStatus(false);

        DealerUserQRInfo qrInfoRedis  = redisCacheManager.getCache(CacheType.PARTNER_QR_CODE)
                .get(DEALER_USER_QR_CODE_REDEIS + qrInfo.getQrCode(),DealerUserQRInfo.class);
        if(qrInfoRedis == null){
            return qrInfo;
        }

        if(StringUtils.equals(qrInfoRedis.getQrCode(), qrInfo.getQrCode()) &&
                StringUtils.equals(qrInfoRedis.getQrKey(), qrInfo.getQrKey()) &&
                StringUtils.equals(qrInfoRedis.getCreateTime(), qrInfo.getCreateTime())){
            qrInfo.setQrStatus(true);
            qrInfo.setName(qrInfoRedis.getName());
            qrInfo.setMobile(qrInfoRedis.getMobile());
            qrInfo.setLivePic(fileAddr + StaticResourceSecurity.getSecurityURI(qrInfoRedis.getLivePic()));
        }
        return qrInfo;
    }

    @Override
    public List<DealerUserDto> findByMobiles(List<String> mobiles) {
        List<DealerUserDto> rs = new ArrayList<>();

        if(!CollectionUtils.isEmpty(mobiles)){
            List<DealerUserPo> list = dealerUserDao.findByMobileIn(mobiles);
            for (DealerUserPo dealerUserPo : list) {
                rs.add(ConverterService.convert(dealerUserPo, DealerUserDto.class));
            }
        }
        return rs;
    }

    @Transactional
    @Override
    public void createDealerUsers(List<DealerUserDto> dealerUserDtos) throws BizServiceException {
        if (!CollectionUtils.isEmpty(dealerUserDtos)) {
            List<DealerUserPo> list = new ArrayList<>();
            for (DealerUserDto dealerUserDto : dealerUserDtos) {
                DealerUserPo po = ConverterService.convert(dealerUserDto, DealerUserPo.class);
                String encryptPwd = StringUtils.EMPTY;
                try {
                    String password = EncryptUtil.encryptMd5("111111");
                    encryptPwd = EncryptUtil.encryptMd5(password, password);
                } catch (Exception e) {
                    throw new BizServiceException(EErrorCode.DEALER_USER_PASSWORD_ENCRYPT_ERROR, "用户密码加密失败", e);
                }
                po.setPassword(encryptPwd);
                po.setStatus(EDealerUserStatus.NORMAL);
                po.setExamPassed(false);
                po.setAdmin(false);
                list.add(po);
            }
            if (!CollectionUtils.isEmpty(list)) {
                dealerUserDao.save(list);
            }
        }
    }

    @Transactional
    @Override
    public void updateExamPass(String userId) throws BizServiceException {
        DealerUserPo dealerUserPo = dealerUserDao.findOne(userId);
        if (!dealerUserPo.getExamPassed()) {
            dealerUserPo.setExamPassed(true);
            dealerUserDao.save(dealerUserPo);
        }
    }

    @Transactional
    @Override
    public void updateFrozen(String userId) {
        DealerUserPo dealerUserPo = dealerUserDao.findOne(userId);
        if (dealerUserPo != null && !dealerUserPo.getStatus().equals(EDealerUserStatus.DELETE)) {
            if (dealerUserPo.getStatus().equals(EDealerUserStatus.FROZEN)) {
                dealerUserPo.setStatus(EDealerUserStatus.NORMAL);
            } else if (dealerUserPo.getStatus().equals(EDealerUserStatus.NORMAL)) {
                dealerUserPo.setStatus(EDealerUserStatus.FROZEN);
            }
            dealerUserDao.save(dealerUserPo);
        }
    }
}
