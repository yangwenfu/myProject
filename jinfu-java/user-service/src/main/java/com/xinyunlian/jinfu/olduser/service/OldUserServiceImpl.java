package com.xinyunlian.jinfu.olduser.service;

import com.xinyunlian.jinfu.bank.dto.BankCardDto;
import com.xinyunlian.jinfu.common.converter.ConverterService;
import com.xinyunlian.jinfu.common.util.BizUtil;
import com.xinyunlian.jinfu.olduser.dao.OldUserDao;
import com.xinyunlian.jinfu.olduser.dto.OldUserDto;
import com.xinyunlian.jinfu.olduser.entity.OldUserPo;
import com.xinyunlian.jinfu.store.dao.StoreDao;
import com.xinyunlian.jinfu.store.dto.StoreInfDto;
import com.xinyunlian.jinfu.store.entity.StoreInfPo;
import com.xinyunlian.jinfu.store.enums.EStoreStatus;
import com.xinyunlian.jinfu.user.dto.UserInfoDto;
import com.xinyunlian.jinfu.user.dto.req.UserSearchDto;
import com.xinyunlian.jinfu.user.dto.resp.UserDetailDto;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by DongFC on 2016-11-07.
 */
@Service
public class OldUserServiceImpl implements OldUserService {

    @Autowired
    private OldUserDao oldUserDao;

    @Autowired
    private StoreDao storeDao;

    @Override
    public OldUserDto addOldUser(OldUserDto dto) {

        OldUserPo po = ConverterService.convert(dto, OldUserPo.class);
        po = oldUserDao.save(po);
        ConverterService.convert(po, dto);

        return dto;
    }

    @Override
    public OldUserDto updateMobile(OldUserDto dto) {
        OldUserPo oldUserPo = oldUserDao.findOne(dto.getUserId());
        oldUserPo.setMobile(dto.getMobile());
        oldUserDao.save(oldUserPo);
        ConverterService.convert(oldUserPo, dto);
        return dto;
    }

    @Override
    public OldUserDto findByTobaccoCertificateNo(String tobaccoCertificateNo) {
        OldUserPo oldUserPo = oldUserDao.findByTobaccoCertificateNo(tobaccoCertificateNo);
        if (oldUserPo == null) {
            return null;
        }
        OldUserDto oldUserDto = ConverterService.convert(oldUserPo, OldUserDto.class);
        return oldUserDto;
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetailDto findUserDetailByUserId(String userId) {
        UserDetailDto userDetailDto = new UserDetailDto();
        if(StringUtils.isEmpty(userId)){
            return userDetailDto;
        }
        OldUserPo user = oldUserDao.findOne(userId);

        if(user == null){
            return userDetailDto;
        }

        UserInfoDto userInfoDto = ConverterService.convert(user, UserInfoDto.class);

        userDetailDto.setUserDto(userInfoDto);

        StoreInfPo storeInfPo = storeDao.findByTobaccoCertificateNoAndStatus(user.getTobaccoCertificateNo(),EStoreStatus.NORMAL);

        if (storeInfPo != null) {
            List<StoreInfDto> storeInfDtoList = new ArrayList<>();
            StoreInfDto storeInfDto = ConverterService.convert(storeInfPo, StoreInfDto.class);
            storeInfDtoList.add(storeInfDto);
            userDetailDto.setStoreInfPoList(storeInfDtoList);
        }

        if (!StringUtils.isEmpty(user.getBankCardNo())) {
            List<BankCardDto> bankCardDtoList = new ArrayList<>();
            BankCardDto bankCardDto = ConverterService.convert(user, BankCardDto.class);
            bankCardDtoList.add(bankCardDto);
            userDetailDto.setBankCardPoList(bankCardDtoList);
        }

        return userDetailDto;
    }

    @Override
    @Transactional(readOnly = true)
    public UserSearchDto getOldUserPage(UserSearchDto userSearchDto) {
        Specification<OldUserPo> spec = (Root<OldUserPo> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            Predicate predicate = cb.conjunction();
            List<Expression<Boolean>> expressions = predicate.getExpressions();
            Subquery<Long> subQuery = query.subquery(Long.class);
            Root<StoreInfPo> storeInfPoRoot = subQuery.from(StoreInfPo.class);
            subQuery.select(storeInfPoRoot.get("storeId"));
            Predicate subPredicate = cb.conjunction();
            List<Expression<Boolean>> subExpressions = subPredicate.getExpressions();

            if (null != userSearchDto) {
                if (!StringUtils.isEmpty(userSearchDto.getUserName())) {
                    expressions.add(cb.like(root.get("userName"), BizUtil.filterString(userSearchDto.getUserName())));
                }
                if (!StringUtils.isEmpty(userSearchDto.getMobile())) {
                    expressions.add(cb.like(root.get("mobile"), BizUtil.filterString(userSearchDto.getMobile())));
                }
                if (!StringUtils.isEmpty(userSearchDto.getStoreName())) {
                    subExpressions.add(cb.equal(storeInfPoRoot.get("storeName"), userSearchDto.getStoreName()));
                }
                if (!StringUtils.isEmpty(userSearchDto.getArea())) {
                    subExpressions.add(cb.equal(storeInfPoRoot.get("area"), userSearchDto.getArea()));
                }
                if (!StringUtils.isEmpty(userSearchDto.getCity())) {
                    subExpressions.add(cb.equal(storeInfPoRoot.get("city"), userSearchDto.getCity()));
                }
                if (!StringUtils.isEmpty(userSearchDto.getProvince())) {
                    subExpressions.add(cb.equal(storeInfPoRoot.get("province"), userSearchDto.getProvince()));
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
                if (subExpressions.size() > 0) {
                    subExpressions.add(cb.equal(storeInfPoRoot.get("status"), EStoreStatus.NORMAL));
                    subExpressions.add(cb.equal(root.get("tobaccoCertificateNo"), storeInfPoRoot.get("tobaccoCertificateNo")));
                    expressions.add(cb.exists(subQuery));
                    subQuery.where(subPredicate);
                }

            }
            return predicate;
        };

        Pageable pageable = new PageRequest(userSearchDto.getCurrentPage() - 1,
                userSearchDto.getPageSize());
        Page<OldUserPo> page = oldUserDao.findAll(spec, pageable);

        List<UserInfoDto> data = new ArrayList<>();
        for (OldUserPo po : page.getContent()) {
            UserInfoDto userInfoDto = ConverterService.convert(po, UserInfoDto.class);
            if(!StringUtils.isEmpty(po.getBankCardNo())){
                userInfoDto.setBankAuth(true);
            }else{
                userInfoDto.setBankAuth(false);
            }
            userInfoDto.setStoreAuth(true);
            data.add(userInfoDto);
        }
        userSearchDto.setList(data);
        userSearchDto.setTotalPages(page.getTotalPages());
        userSearchDto.setTotalRecord(page.getTotalElements());
        return userSearchDto;
    }
}
