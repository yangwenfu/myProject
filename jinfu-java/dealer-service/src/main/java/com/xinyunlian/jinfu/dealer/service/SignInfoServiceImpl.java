package com.xinyunlian.jinfu.dealer.service;

import com.xinyunlian.jinfu.common.converter.ConverterService;
import com.xinyunlian.jinfu.common.exception.BizServiceException;
import com.xinyunlian.jinfu.common.util.DateHelper;
import com.xinyunlian.jinfu.dealer.dao.SignInfoDao;
import com.xinyunlian.jinfu.dealer.dao.SignInfoViewDao;
import com.xinyunlian.jinfu.dealer.dto.*;
import com.xinyunlian.jinfu.dealer.entity.SignInfoPo;
import com.xinyunlian.jinfu.dealer.entity.SignInfoViewPo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by menglei on 2017年05月03日.
 */
@Service
public class SignInfoServiceImpl implements SignInfoService {

    @Autowired
    private SignInfoDao signInfoDao;
    @Autowired
    private SignInfoViewDao signInfoViewDao;

    @Transactional
    @Override
    public SignInfoViewSearchDto getSignInfoPage(SignInfoViewSearchDto signInfoViewSearchDto) {

        Specification<SignInfoViewPo> spec = (root, query, cb) -> {

            Predicate predicate = cb.conjunction();
            List<Expression<Boolean>> expressions = predicate.getExpressions();
            if (null != signInfoViewSearchDto) {
                if (!StringUtils.isEmpty(signInfoViewSearchDto.getDealerId())) {
                    expressions.add(cb.equal(root.get("dealerId"), signInfoViewSearchDto.getDealerId()));
                }
                if (!StringUtils.isEmpty(signInfoViewSearchDto.getUserId())) {
                    expressions.add(cb.equal(root.get("userId"), signInfoViewSearchDto.getUserId()));
                }
                if (StringUtils.isNotEmpty(signInfoViewSearchDto.getLastId())) {
                    expressions.add(cb.lessThanOrEqualTo(root.get("id"), signInfoViewSearchDto.getLastId()));
                }
                if (StringUtils.isNotEmpty(signInfoViewSearchDto.getUserName())) {
                    expressions.add(cb.like(root.get("userName"), "%" + signInfoViewSearchDto.getUserName() + "%"));
                }
                if (StringUtils.isNotEmpty(signInfoViewSearchDto.getStoreName())) {
                    expressions.add(cb.like(root.get("storeName"), "%" + signInfoViewSearchDto.getStoreName() + "%"));
                }
                if (StringUtils.isNotEmpty(signInfoViewSearchDto.getMobile())) {
                    expressions.add(cb.like(root.get("mobile"), signInfoViewSearchDto.getMobile() + "%"));
                }
                if (StringUtils.isNotEmpty(signInfoViewSearchDto.getDealerName())) {
                    expressions.add(cb.like(root.get("dealerName"), "%" + signInfoViewSearchDto.getDealerName() + "%"));
                }
                if (StringUtils.isNotEmpty(signInfoViewSearchDto.getStartTime())) {
                    expressions.add(cb.greaterThanOrEqualTo(root.get("signInTime"), DateHelper.getStartDate(signInfoViewSearchDto.getStartTime())));
                }
                if (StringUtils.isNotEmpty(signInfoViewSearchDto.getEndTime())) {
                    expressions.add(cb.lessThanOrEqualTo(root.get("signInTime"), DateHelper.getEndDate(signInfoViewSearchDto.getEndTime())));
                }
//                if (StringUtils.isEmpty(signInfoViewSearchDto.getStartTime()) && StringUtils.isEmpty(signInfoViewSearchDto.getEndTime())) {
//                    Date date = new Date(new Date().getTime() - 24 * 60 * 60 * 1000);
//                    expressions.add(cb.greaterThanOrEqualTo(root.get("signInTime"), DateHelper.getStartDate(date)));
//                    expressions.add(cb.lessThanOrEqualTo(root.get("signInTime"), DateHelper.getEndDate(date)));
//                }
            }
            return predicate;
        };

        Pageable pageable = new PageRequest(signInfoViewSearchDto.getCurrentPage() - 1, signInfoViewSearchDto.getPageSize(), Sort.Direction.DESC, "id");
        Page<SignInfoViewPo> page = signInfoViewDao.findAll(spec, pageable);
        List<SignInfoViewDto> data = new ArrayList<>();
        page.getContent().forEach(po -> {
            SignInfoViewDto signInfoViewDto = ConverterService.convert(po, SignInfoViewDto.class);
            data.add(signInfoViewDto);
        });

        signInfoViewSearchDto.setList(data);
        signInfoViewSearchDto.setTotalPages(page.getTotalPages());
        signInfoViewSearchDto.setTotalRecord(page.getTotalElements());

        return signInfoViewSearchDto;
    }

    @Transactional
    @Override
    public List<SignInfoViewDto> getSignInfoReport(SignInfoViewSearchDto signInfoViewSearchDto) {

        Specification<SignInfoViewPo> spec = (root, query, cb) -> {

            Predicate predicate = cb.conjunction();
            List<Expression<Boolean>> expressions = predicate.getExpressions();
            if (null != signInfoViewSearchDto) {
                if (StringUtils.isNotEmpty(signInfoViewSearchDto.getUserName())) {
                    expressions.add(cb.like(root.get("userName"), "%" + signInfoViewSearchDto.getUserName() + "%"));
                }
                if (StringUtils.isNotEmpty(signInfoViewSearchDto.getStoreName())) {
                    expressions.add(cb.like(root.get("storeName"), "%" + signInfoViewSearchDto.getStoreName() + "%"));
                }
                if (StringUtils.isNotEmpty(signInfoViewSearchDto.getMobile())) {
                    expressions.add(cb.like(root.get("mobile"), signInfoViewSearchDto.getMobile() + "%"));
                }
                if (StringUtils.isNotEmpty(signInfoViewSearchDto.getDealerName())) {
                    expressions.add(cb.like(root.get("dealerName"), "%" + signInfoViewSearchDto.getDealerName() + "%"));
                }
                if (StringUtils.isNotEmpty(signInfoViewSearchDto.getStartTime())) {
                    expressions.add(cb.greaterThanOrEqualTo(root.get("signInTime"), DateHelper.getStartDate(signInfoViewSearchDto.getStartTime())));
                }
                if (StringUtils.isNotEmpty(signInfoViewSearchDto.getEndTime())) {
                    expressions.add(cb.lessThanOrEqualTo(root.get("signInTime"), DateHelper.getEndDate(signInfoViewSearchDto.getEndTime())));
                }
//                if (StringUtils.isEmpty(signInfoViewSearchDto.getStartTime()) && StringUtils.isEmpty(signInfoViewSearchDto.getEndTime())) {
//                    Date date = new Date(new Date().getTime() - 24 * 60 * 60 * 1000);
//                    expressions.add(cb.greaterThanOrEqualTo(root.get("signInTime"), DateHelper.getStartDate(date)));
//                    expressions.add(cb.lessThanOrEqualTo(root.get("signInTime"), DateHelper.getEndDate(date)));
//                }
            }
            return predicate;
        };

        List<SignInfoViewPo> list = signInfoViewDao.findAll(spec);
        List<SignInfoViewDto> data = new ArrayList<>();
        list.forEach(po -> {
            SignInfoViewDto signInfoViewDto = ConverterService.convert(po, SignInfoViewDto.class);
            data.add(signInfoViewDto);
        });

        return data;
    }

    @Transactional
    @Override
    public void createSignIn(SignInfoDto signInfoDto) throws BizServiceException {
        if (signInfoDto != null) {
            SignInfoPo signInfoPo = ConverterService.convert(signInfoDto, SignInfoPo.class);
            signInfoDao.save(signInfoPo);
        }
    }

    @Transactional
    @Override
    public void updateSignOut(SignInfoDto signInfoDto) throws BizServiceException {
        if (signInfoDto != null) {
            SignInfoPo signInfoPo = signInfoDao.findOne(signInfoDto.getId());
            signInfoPo.setSignOutStoreHeader(signInfoDto.getSignOutStoreHeader());
            signInfoPo.setSignOutLat(signInfoDto.getSignOutLat());
            signInfoPo.setSignOutLng(signInfoDto.getSignOutLng());
            signInfoPo.setSignOutAddress(signInfoDto.getSignOutAddress());
            signInfoPo.setSignOutTime(signInfoDto.getSignOutTime());
            signInfoPo.setDistanceTime(signInfoDto.getDistanceTime());
            signInfoDao.save(signInfoPo);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public SignInfoDto getByUserIdAndStoreId(String userId, Long storeId) {
        SignInfoPo signInfoPo = signInfoDao.findByUserIdAndStoreId(userId, storeId);
        if (signInfoPo == null) {
            return null;
        }
        SignInfoDto signInfoDto = ConverterService.convert(signInfoPo, SignInfoDto.class);
        return signInfoDto;
    }

    @Override
    @Transactional(readOnly = true)
    public SignInfoDto getById(Long id) {
        SignInfoPo signInfoPo = signInfoDao.findOne(id);
        if (signInfoPo == null) {
            return null;
        }
        SignInfoDto signInfoDto = ConverterService.convert(signInfoPo, SignInfoDto.class);
        return signInfoDto;
    }

    @Transactional
    @Override
    public List<SignInfoViewDto> getSignInfoList(SignInfoViewSearchDto signInfoViewSearchDto) {

        Specification<SignInfoViewPo> spec = (root, query, cb) -> {

            Predicate predicate = cb.conjunction();
            List<Expression<Boolean>> expressions = predicate.getExpressions();
            if (null != signInfoViewSearchDto) {
                if (StringUtils.isNotEmpty(signInfoViewSearchDto.getStartTime())) {
                    expressions.add(cb.greaterThanOrEqualTo(root.get("signInTime"), DateHelper.getStartDate(signInfoViewSearchDto.getStartTime())));
                }
                if (StringUtils.isNotEmpty(signInfoViewSearchDto.getEndTime())) {
                    expressions.add(cb.lessThanOrEqualTo(root.get("signInTime"), DateHelper.getEndDate(signInfoViewSearchDto.getEndTime())));
                }
                if (StringUtils.isNotEmpty(signInfoViewSearchDto.getDealerId())) {
                    expressions.add(cb.equal(root.get("dealerId"), signInfoViewSearchDto.getDealerId()));
                }
                if (StringUtils.isNotEmpty(signInfoViewSearchDto.getUserId())) {
                    expressions.add(cb.equal(root.get("userId"), signInfoViewSearchDto.getUserId()));
                }
            }
            return predicate;
        };

        List<SignInfoViewPo> list = signInfoViewDao.findAll(spec);
        List<SignInfoViewDto> data = new ArrayList<>();
        list.forEach(po -> {
            SignInfoViewDto signInfoViewDto = ConverterService.convert(po, SignInfoViewDto.class);
            data.add(signInfoViewDto);
        });

        return data;
    }

}
