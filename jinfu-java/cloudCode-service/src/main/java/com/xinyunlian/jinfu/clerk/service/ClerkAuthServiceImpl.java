package com.xinyunlian.jinfu.clerk.service;

import com.xinyunlian.jinfu.clerk.dao.ClerkApplyDao;
import com.xinyunlian.jinfu.clerk.dao.ClerkAuthDao;
import com.xinyunlian.jinfu.clerk.dao.ClerkInfDao;
import com.xinyunlian.jinfu.clerk.dto.ClerkAuthDto;
import com.xinyunlian.jinfu.clerk.dto.ClerkInfDto;
import com.xinyunlian.jinfu.clerk.entity.ClerkApplyPo;
import com.xinyunlian.jinfu.clerk.entity.ClerkAuthPo;
import com.xinyunlian.jinfu.clerk.entity.ClerkInfPo;
import com.xinyunlian.jinfu.clerk.enums.EClerkApplyStatus;
import com.xinyunlian.jinfu.common.converter.ConverterService;
import com.xinyunlian.jinfu.common.enums.EErrorCode;
import com.xinyunlian.jinfu.common.exception.BizServiceException;
import com.xinyunlian.jinfu.common.util.DateHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by menglei on 2016-12-05.
 */
@Service
public class ClerkAuthServiceImpl implements ClerkAuthService {

    @Autowired
    private ClerkApplyDao clerkApplyDao;
    @Autowired
    private ClerkInfDao clerkInfDao;
    @Autowired
    private ClerkAuthDao clerkAuthDao;

    /**
     * 授权注销
     *
     * @param userId
     * @param clerkId
     * @return
     */
    @Override
    @Transactional
    public ClerkInfDto deleteAuth(String userId, String clerkId) throws BizServiceException {
        ClerkInfPo clerkInfPo = clerkInfDao.findOne(clerkId);
        if (clerkInfPo == null) {
            throw new BizServiceException(EErrorCode.CLOUDCODE_CLERK_INF_NOT_FOUND);
        }
        ClerkApplyPo po = clerkApplyDao.findByUserIdAndClerkIdAndStatus(userId, clerkId, EClerkApplyStatus.SUCCESS);
        if (po == null) {
            throw new BizServiceException(EErrorCode.CLOUDCODE_CLERK_APPLY_NOT_FOUND);
        }
        po.setStatus(EClerkApplyStatus.DELETE);
        clerkApplyDao.save(po);
        clerkAuthDao.deleteByuserIdAndClerkId(userId, clerkId);
        ClerkInfDto dto = ConverterService.convert(clerkInfPo, ClerkInfDto.class);
        return dto;
    }

    /**
     * 查询已授权给店员的店铺
     *
     * @param clerkId
     * @return
     */
    @Override
    public List<ClerkAuthDto> findByClerkId(String clerkId) {
        List<ClerkAuthPo> poList = clerkAuthDao.findByClerkId(clerkId);
        List<ClerkAuthDto> dtoList = new ArrayList<>();
        for (ClerkAuthPo po : poList) {
            ClerkAuthDto dto = ConverterService.convert(po, ClerkAuthDto.class);
            dtoList.add(dto);
        }
        return dtoList;
    }

    /**
     * 查询该店铺 店员是否有权限
     *
     * @param clerkId
     * @return
     */
    @Override
    public ClerkAuthDto findByClerkIdAndStoreId(String clerkId, String storeId) {
        ClerkAuthPo po = clerkAuthDao.findByClerkIdAndStoreId(clerkId, storeId);
        if (po == null) {
            return null;
        }
        ClerkAuthDto dto = ConverterService.convert(po, ClerkAuthDto.class);
        return dto;
    }

    /**
     * 查询已授权给店员的店铺
     *
     * @param storeId
     * @return
     */
    @Override
    public List<ClerkAuthDto> findByStoreId(String storeId) {
        List<ClerkAuthPo> poList = clerkAuthDao.findByStoreId(storeId);
        List<ClerkAuthDto> dtoList = new ArrayList<>();
        for (ClerkAuthPo po : poList) {
            ClerkAuthDto dto = ConverterService.convert(po, ClerkAuthDto.class);
            dtoList.add(dto);
        }
        return dtoList;
    }

    /**
     * 根据店主id获取已授权列表
     *
     * @param userId
     * @return
     */
    @Override
    public List<ClerkAuthDto> findByUserId(String userId) {
        List<Object[]> list = clerkAuthDao.findByUserId(userId);
        List<ClerkAuthDto> dtoList = new ArrayList<>();
        ClerkAuthDto clerkAuthDto;
        for (Object[] object : list) {
            clerkAuthDto = new ClerkAuthDto();
            clerkAuthDto.setUserId(object[0].toString());
            clerkAuthDto.setStoreIds(object[1].toString());
            clerkAuthDto.setClerkId(object[2].toString());
            clerkAuthDto.setCreateTime(DateHelper.formatDate(DateHelper.getDate(object[3].toString(), "yyyy-MM-dd hh:mm:ss"), "yyyy.MM.dd HH:mm"));
            clerkAuthDto.setName(object[4].toString());
            clerkAuthDto.setMobile(object[5].toString());
            clerkAuthDto.setOpenId(object[6].toString());
            dtoList.add(clerkAuthDto);
        }
        return dtoList;
    }

}
