package com.xinyunlian.jinfu.user.service;

import com.xinyunlian.jinfu.common.converter.ConverterService;
import com.xinyunlian.jinfu.common.enums.EErrorCode;
import com.xinyunlian.jinfu.common.exception.BizServiceException;
import com.xinyunlian.jinfu.common.util.BizUtil;
import com.xinyunlian.jinfu.common.util.EncryptUtil;
import com.xinyunlian.jinfu.user.dao.*;
import com.xinyunlian.jinfu.user.dto.MgtRoleDto;
import com.xinyunlian.jinfu.user.dto.MgtUserDto;
import com.xinyunlian.jinfu.user.dto.MgtUserPwdDto;
import com.xinyunlian.jinfu.user.dto.MgtUserSearchDto;
import com.xinyunlian.jinfu.user.entity.*;
import com.xinyunlian.jinfu.user.enums.EMgtUserStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by DongFC on 2016-08-23.
 */
@Service
public class MgtUserServiceImpl implements MgtUserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MgtUserServiceImpl.class);

    @Autowired
    private MgtUserDao mgtUserDao;

    @Autowired
    private MgtUserRoleDao mgtUserRoleDao;

    @Autowired
    private MgtDeptDao mgtDeptDao;

    @Autowired
    private MgtDeptUserDao mgtDeptUserDao;

    @Autowired
    private MgtRoleDao mgtRoleDao;

    @Transactional
    @Override
    public void createMgtUserAcct(MgtUserDto mgtUserDto) throws BizServiceException{

        if (mgtUserDto != null){
            if (!StringUtils.isEmpty(mgtUserDto.getLoginId())){
                MgtUserPo existUser = mgtUserDao.findByLoginIdAndStatusNot(mgtUserDto.getLoginId(), EMgtUserStatus.DELETE);
                if (existUser==null){
                    String encrypPwd = "";
                    try {
                        encrypPwd = EncryptUtil.encryptMd5(mgtUserDto.getPassword(), mgtUserDto.getLoginId());
                    } catch (UnsupportedEncodingException | NoSuchAlgorithmException e) {
                        LOGGER.error("md5加密异常", e);
                        throw new BizServiceException(EErrorCode.SYSTEM_USER_PASSWORD_ENCRYPT_ERROR);
                    }
                    MgtUserPo mgtUserPo = ConverterService.convert(mgtUserDto, MgtUserPo.class);
                    mgtUserPo.setPassword(encrypPwd);
                    mgtUserPo.setStatus(EMgtUserStatus.NORMAL);
                    mgtUserDao.save(mgtUserPo);

                    //保存角色
                    if (!CollectionUtils.isEmpty(mgtUserDto.getRoleOwnedList())){
                        List<MgtUserRolePo> urList = new ArrayList<>();
                        mgtUserDto.getRoleOwnedList().forEach( role -> {
                            MgtUserRolePo ur = new MgtUserRolePo();
                            ur.setUserId(mgtUserPo.getUserId());
                            ur.setRoleId(role.getRoleId());
                            urList.add(ur);
                        });
                        mgtUserRoleDao.save(urList);
                    }

                    //保存部门
                    if (mgtUserDto.getMgtDeptDto() != null){
                        MgtDeptUserPo deptUserPo = new MgtDeptUserPo();
                        deptUserPo.setUserId(mgtUserPo.getUserId());
                        deptUserPo.setDeptId(mgtUserDto.getMgtDeptDto().getDeptId());
                        deptUserPo.setDeptTreePath(mgtUserDto.getMgtDeptDto().getDeptTreePath());
                        mgtDeptUserDao.save(deptUserPo);
                    }


                }
            }
        }

    }

    @Override
    public MgtUserSearchDto getMgtUserPage(MgtUserSearchDto mgtUserSearchDto) {

        Specification<MgtUserPo> spec = (root, query, cb) -> {
            Predicate predicate = cb.conjunction();
            List<Expression<Boolean>> expressions = predicate.getExpressions();
            if (null != mgtUserSearchDto) {
                if (!StringUtils.isEmpty(mgtUserSearchDto.getLoginId())) {
                    expressions.add(cb.like(root.get("loginId"), BizUtil.filterString(mgtUserSearchDto.getLoginId())));
                }
                if (!StringUtils.isEmpty(mgtUserSearchDto.getName())) {
                    expressions.add(cb.like(root.get("name"), BizUtil.filterString(mgtUserSearchDto.getName())));
                }
                if (mgtUserSearchDto.getStatus() != null){
                    expressions.add(cb.equal(root.get("status"), mgtUserSearchDto.getStatus()));
                }else {
                    expressions.add(cb.notEqual(root.get("status"), EMgtUserStatus.DELETE));
                }
                if (!CollectionUtils.isEmpty(mgtUserSearchDto.getUserIdList())){
                    expressions.add(cb.in(root.get("userId")).value(mgtUserSearchDto.getUserIdList()));
                }
                if (!StringUtils.isEmpty(mgtUserSearchDto.getFuzzyMatch())){
                    expressions.add(cb.or(cb.like(root.get("loginId"), BizUtil.filterString(mgtUserSearchDto.getFuzzyMatch())),
                            cb.like(root.get("name"), BizUtil.filterString(mgtUserSearchDto.getFuzzyMatch())),
                            cb.like(root.get("mobile"), BizUtil.filterString(mgtUserSearchDto.getFuzzyMatch()))
                            ));
                }
            }
            return predicate;
        };

        Pageable pageable = new PageRequest(mgtUserSearchDto.getCurrentPage() - 1, mgtUserSearchDto.getPageSize());
        Page<MgtUserPo> page = mgtUserDao.findAll(spec, pageable);

        List<MgtUserDto> data = new ArrayList<>();
        page.getContent().forEach(po -> {
            MgtUserDto mgtUserDto = ConverterService.convert(po, MgtUserDto.class);
            data.add(mgtUserDto);
        });

        mgtUserSearchDto.setList(data);
        mgtUserSearchDto.setTotalPages(page.getTotalPages());
        mgtUserSearchDto.setTotalRecord(page.getTotalElements());
        return mgtUserSearchDto;
    }

    @Transactional
    @Override
    public void deleteMgtUserAcct(String userId) {
        if (!StringUtils.isEmpty(userId)){
            mgtUserRoleDao.deleteByUserId(userId);
            mgtDeptUserDao.deleteByUserId(userId);
            MgtUserPo existUser = mgtUserDao.findOne(userId);
            existUser.setStatus(EMgtUserStatus.DELETE);
        }
    }

    @Transactional
    @Override
    public void updateMgtUserStatus(MgtUserDto mgtUserDto) throws BizServiceException{
        if (mgtUserDto!=null){
            if (!StringUtils.isEmpty(mgtUserDto.getUserId()) && mgtUserDto.getStatus() != null){
                MgtUserPo existUser = mgtUserDao.findOne(mgtUserDto.getUserId());
                if (EMgtUserStatus.DELETE != existUser.getStatus()){
                    existUser.setStatus(mgtUserDto.getStatus());
                }else {
                    throw new BizServiceException(EErrorCode.SYSTEM_USER_STATUS_ERROR);
                }
            }
        }
    }

    @Transactional
    @Override
    public void updateMgtUserPwd(MgtUserPwdDto mgtUserPwdDto) throws BizServiceException{

        if (mgtUserPwdDto != null){
            if (!StringUtils.isEmpty(mgtUserPwdDto.getUserId()) && !StringUtils.isEmpty(mgtUserPwdDto.getNewPassword())){
                MgtUserPo existUser = mgtUserDao.findOne(mgtUserPwdDto.getUserId());

                //判断用户状态
                if (EMgtUserStatus.DELETE == existUser.getStatus()){
                    throw new BizServiceException(EErrorCode.SYSTEM_USER_STATUS_ERROR);
                }

                //判断是否是用户自己修改密码，并校验
                if (!StringUtils.isEmpty(mgtUserPwdDto.getOldPassword())){

                    //判断用户状态
                    if (EMgtUserStatus.FROZEN.getCode().equals(existUser.getStatus().getCode())){
                        throw new BizServiceException(EErrorCode.SYSTEM_USER_STATUS_ERROR);
                    }

                    try {
                        String encrypPwd = EncryptUtil.encryptMd5(mgtUserPwdDto.getOldPassword(), existUser.getLoginId());
                        if (!encrypPwd.equals(existUser.getPassword())){
                            throw new BizServiceException(EErrorCode.SYSTEM_USER_OLD_PASSWORD_ERROR);
                        }
                    }catch (BizServiceException e){
                        LOGGER.error("md5加密异常", e);
                        throw e;
                    }catch (UnsupportedEncodingException | NoSuchAlgorithmException e) {
                        LOGGER.error("md5加密异常", e);
                        throw new BizServiceException(EErrorCode.SYSTEM_USER_PASSWORD_ENCRYPT_ERROR);
                    }
                }

                try {
                    String encrypPwd = EncryptUtil.encryptMd5(mgtUserPwdDto.getNewPassword(), existUser.getLoginId());
                    existUser.setPassword(encrypPwd);
                } catch (UnsupportedEncodingException | NoSuchAlgorithmException e) {
                    LOGGER.error("md5加密异常", e);
                    throw new BizServiceException(EErrorCode.SYSTEM_USER_PASSWORD_ENCRYPT_ERROR);
                }
            }
        }

    }

    @Override
    public List<MgtUserDto> findUserByDirectDept(Long deptId) throws BizServiceException {
        List<MgtUserDto> retList = new ArrayList<>();

        List<MgtUserPo> list = mgtUserDao.findUserByDirectDept(deptId);
        if (!CollectionUtils.isEmpty(list)){
            list.forEach(po -> {
                MgtUserDto dto = ConverterService.convert(po, MgtUserDto.class);
                retList.add(dto);
            });
        }
        return retList;
    }

    @Override
    public List<MgtUserDto> getUserByDept(Long deptId) throws BizServiceException{
        List<MgtUserDto> retList = new ArrayList<>();

        MgtDeptPo dept = mgtDeptDao.findOne(deptId);
        List<MgtUserPo> list = mgtUserDao.findUserByDept(dept.getDeptTreePath());
        if (!CollectionUtils.isEmpty(list)){
            list.forEach(user -> {
                MgtUserDto dto = ConverterService.convert(user, MgtUserDto.class);
                retList.add(dto);
            });
        }

        return retList;
    }

    @Override
    @Transactional
    public void updateMgtUser(MgtUserDto userDto) throws BizServiceException {

        MgtUserPo userPo = mgtUserDao.findOne(userDto.getUserId());
        userPo.setLoginId(userDto.getLoginId());
        userPo.setName(userDto.getName());
        userPo.setMobile(userDto.getMobile());

        //密码修改功能暂时不用
        /*String encrypPwd = "";
        try {
            encrypPwd = EncryptUtil.encryptMd5(userDto.getPassword(), userDto.getLoginId());
        } catch (UnsupportedEncodingException | NoSuchAlgorithmException e) {
            throw new BizServiceException(EErrorCode.SYSTEM_USER_PASSWORD_ENCRYPT_ERROR);
        }
        userPo.setPassword(encrypPwd);*/

        userPo.setEmail(userDto.getEmail());
        userPo.setDuty(userDto.getDuty());

        mgtUserRoleDao.deleteByUserId(userDto.getUserId());
        if (!CollectionUtils.isEmpty(userDto.getRoleOwnedList())){

            List<MgtUserRolePo> urList = new ArrayList<>();
            userDto.getRoleOwnedList().forEach( role -> {
                MgtUserRolePo ur = new MgtUserRolePo();
                ur.setRoleId(role.getRoleId());
                ur.setUserId(userPo.getUserId());
                urList.add(ur);
            });

            mgtUserRoleDao.save(urList);
        }
    }

    @Override
    public MgtUserDto getMgtUserInf(String userId) throws BizServiceException {
        MgtUserDto userDto = null;

        MgtUserPo userPo = mgtUserDao.findOne(userId);
        if (userPo != null){
            userDto = ConverterService.convert(userPo, MgtUserDto.class);

            List<MgtRolePo> ownedRoles = mgtRoleDao.findByUser(userId);
            List<MgtRoleDto> ownedRoleDtos = convertRolePo2Dto(ownedRoles);
            userDto.setRoleOwnedList(ownedRoleDtos);

            List<MgtRolePo> notHaveRoles = mgtRoleDao.findByUserNotHave(userId);
            List<MgtRoleDto> notHaveRolesDtos = convertRolePo2Dto(notHaveRoles);
            userDto.setRoleNotHaveList(notHaveRolesDtos);
        }

        return userDto;
    }

    @Override
    public MgtUserDto getMgtUserInfByLoginId(String loginId) throws BizServiceException {
        MgtUserPo po = mgtUserDao.findByLoginId(loginId);
        MgtUserDto dto = ConverterService.convert(po, MgtUserDto.class);
        return dto;
    }

    @Override
    public MgtUserDto getMgtUserByMobile(String mobile) throws BizServiceException {
        MgtUserPo po = mgtUserDao.findByMobile(mobile);
        MgtUserDto dto = ConverterService.convert(po, MgtUserDto.class);
        return dto;
    }

    @Override
    public List<MgtUserDto> findByMgtUserIds(Collection<String> mgtUserIds) {
        List<MgtUserDto> rs = new ArrayList<>();

        if(!CollectionUtils.isEmpty(mgtUserIds)){
            List<MgtUserPo> list = mgtUserDao.findByMgtUserIds(mgtUserIds);
            for (MgtUserPo mgtUserPo : list) {
                rs.add(ConverterService.convert(mgtUserPo, MgtUserDto.class));
            }
        }

        return rs;
    }

    @Override
    public List<MgtUserDto> findByNameLike(String name) {
        List<MgtUserDto> rs = new ArrayList<>();
        List<MgtUserPo> list = mgtUserDao.findByNameLike(BizUtil.filterString(name));
        if(CollectionUtils.isEmpty(list)){
            return rs;
        }
        list.forEach(item ->{
            if(EMgtUserStatus.NORMAL.equals(item.getStatus())){
                rs.add(ConverterService.convert(item, MgtUserDto.class));
            }
        });

        return rs;
    }

    @Override
    public List<MgtUserDto> findByNameLikeNotStatus(String name) {
        List<MgtUserDto> rs = new ArrayList<>();
        List<MgtUserPo> list = mgtUserDao.findByNameLike(BizUtil.filterString(name));
        if(CollectionUtils.isEmpty(list)){
            return rs;
        }
        list.forEach(item ->{
            rs.add(ConverterService.convert(item, MgtUserDto.class));
        });

        return rs;
    }

    private List<MgtRoleDto> convertRolePo2Dto(List<MgtRolePo> poList){
        if (!CollectionUtils.isEmpty(poList)){
            List<MgtRoleDto> dtoList = new ArrayList<>();
            poList.forEach( role -> {
                MgtRoleDto dto = ConverterService.convert(role, MgtRoleDto.class);
                dtoList.add(dto);
            });
            return dtoList;
        }
        return null;
    }

    @Override
    public List<MgtUserDto> findNotInChannel(String duty) {
        List<MgtUserDto> rs = new ArrayList<>();

        List<MgtUserPo> list = mgtUserDao.findNotInChannel(duty);
        if(!CollectionUtils.isEmpty(list)) {
            for (MgtUserPo mgtUserPo : list) {
                rs.add(ConverterService.convert(mgtUserPo, MgtUserDto.class));
            }
        }

        return rs;
    }

    @Override
    public List<MgtUserDto> findByDuty(String duty) {
        List<MgtUserDto> rs = new ArrayList<>();

        List<MgtUserPo> list = mgtUserDao.findByDuty(duty);
        if(!CollectionUtils.isEmpty(list)) {
            for (MgtUserPo mgtUserPo : list) {
                rs.add(ConverterService.convert(mgtUserPo, MgtUserDto.class));
            }
        }

        return rs;
    }

}
