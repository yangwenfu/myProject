package com.xinyunlian.jinfu.clerk.service;

import com.xinyunlian.jinfu.clerk.dao.ClerkApplyDao;
import com.xinyunlian.jinfu.clerk.dao.ClerkAuthDao;
import com.xinyunlian.jinfu.clerk.dao.ClerkInfDao;
import com.xinyunlian.jinfu.clerk.dto.ClerkApplyDto;
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
public class ClerkApplyServiceImpl implements ClerkApplyService {

    @Autowired
    private ClerkApplyDao clerkApplyDao;
    @Autowired
    private ClerkInfDao clerkInfDao;
    @Autowired
    private ClerkAuthDao clerkAuthDao;

    @Override
    @Transactional
    public void addApply(ClerkApplyDto dto) throws BizServiceException {
        ClerkApplyPo po = ConverterService.convert(dto, ClerkApplyPo.class);
        clerkApplyDao.save(po);
    }

    /**
     * 申请中列表
     * @param userId
     * @return
     */
    @Override
    public List<ClerkApplyDto> findByUserId(String userId) {
        List<Object[]> list = clerkApplyDao.findByUserId(userId);
        List<ClerkApplyDto> dtoList = new ArrayList<>();
        ClerkApplyDto clerkApplyDto;
        for (Object[] object : list) {
            clerkApplyDto = new ClerkApplyDto();
            clerkApplyDto.setApplyId(Long.valueOf(object[0].toString()));
            clerkApplyDto.setUserId(object[1].toString());
            clerkApplyDto.setClerkId(object[2].toString());
            clerkApplyDto.setStatus(EClerkApplyStatus.valueOf(object[3].toString()));
            clerkApplyDto.setCreateTime(DateHelper.formatDate(DateHelper.getDate(object[4].toString(), "yyyy-MM-dd hh:mm:ss"), "yyyy.MM.dd HH:mm"));
            clerkApplyDto.setName(object[5].toString());
            clerkApplyDto.setMobile(object[6].toString());
            clerkApplyDto.setOpenId(object[7].toString());
            dtoList.add(clerkApplyDto);
        }
        return dtoList;
    }

    /**
     * 是否有申请中，申请通过的数据
     * @param clerkId
     * @return
     */
    @Override
    public List<ClerkApplyDto> findByClerkId(String clerkId) {
        List<ClerkApplyPo> poList = clerkApplyDao.findByClerkId(clerkId);
        List<ClerkApplyDto> dtoList = new ArrayList<>();
        for (ClerkApplyPo po : poList) {
            ClerkApplyDto dto = ConverterService.convert(po, ClerkApplyDto.class);
            dto.setCreateTime(DateHelper.formatDate(po.getCreateTs(), "yyyy.MM.dd HH:mm"));
            dto.setUpdateTime(DateHelper.formatDate(po.getLastMntTs(), "yyyy.MM.dd HH:mm"));
            dtoList.add(dto);
        }
        return dtoList;
    }

    /**
     * 申请拒绝
     * @param userId
     * @param clerkId
     * @return
     */
    @Override
    @Transactional
    public ClerkInfDto applyRefuse(String userId, String clerkId) throws BizServiceException {
        ClerkInfPo clerkInfPo = clerkInfDao.findOne(clerkId);
        if (clerkInfPo == null) {
            throw new BizServiceException(EErrorCode.CLOUDCODE_CLERK_INF_NOT_FOUND);
        }
        ClerkApplyPo po = clerkApplyDao.findByUserIdAndClerkIdAndStatus(userId, clerkId, EClerkApplyStatus.APPLY);
        if (po == null) {
            throw new BizServiceException(EErrorCode.CLOUDCODE_CLERK_APPLY_NOT_FOUND);
        }
        po.setStatus(EClerkApplyStatus.FAILED);
        clerkApplyDao.save(po);
        ClerkInfDto dto = ConverterService.convert(clerkInfPo, ClerkInfDto.class);
        return dto;
    }

    /**
     * 申请通过
     * @param userId
     * @param clerkId
     * @return
     */
    @Override
    @Transactional
    public ClerkInfDto applyPass(String userId, String clerkId, String storeIds) throws BizServiceException {
        ClerkInfPo clerkInfPo = clerkInfDao.findOne(clerkId);
        if (clerkInfPo == null) {
            throw new BizServiceException(EErrorCode.CLOUDCODE_CLERK_INF_NOT_FOUND);
        }
        ClerkApplyPo po = clerkApplyDao.findByUserIdAndClerkIdAndStatus(userId, clerkId, EClerkApplyStatus.APPLY);
        if (po == null) {
            throw new BizServiceException(EErrorCode.CLOUDCODE_CLERK_APPLY_NOT_FOUND);
        }
        po.setStatus(EClerkApplyStatus.SUCCESS);
        clerkApplyDao.save(po);
        List<ClerkAuthPo> list = new ArrayList<>();
        ClerkAuthPo clerkAuthPo;
        for (String storeId : storeIds.split(",")) {
            clerkAuthPo = new ClerkAuthPo();
            clerkAuthPo.setUserId(userId);
            clerkAuthPo.setStoreId(storeId);
            clerkAuthPo.setClerkId(clerkId);
            list.add(clerkAuthPo);
        }
        clerkAuthDao.save(list);
        ClerkInfDto dto = ConverterService.convert(clerkInfPo, ClerkInfDto.class);
        return dto;
    }

}
