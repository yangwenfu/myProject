package com.xinyunlian.jinfu.clerk.service;

import com.xinyunlian.jinfu.clerk.dao.ClerkApplyDao;
import com.xinyunlian.jinfu.clerk.dao.ClerkAuthDao;
import com.xinyunlian.jinfu.clerk.dao.ClerkInfDao;
import com.xinyunlian.jinfu.clerk.dto.ClerkInfDto;
import com.xinyunlian.jinfu.clerk.entity.ClerkInfPo;
import com.xinyunlian.jinfu.common.converter.ConverterService;
import com.xinyunlian.jinfu.common.exception.BizServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by menglei on 2016-12-05.
 */
@Service
public class ClerkServiceImpl implements ClerkService {

    @Autowired
    private ClerkInfDao clerkInfDao;
    @Autowired
    private ClerkApplyDao clerkApplyDao;
    @Autowired
    private ClerkAuthDao clerkAuthDao;

    @Override
    @Transactional
    public ClerkInfDto addClerk(ClerkInfDto dto) throws BizServiceException {
        ClerkInfPo po = clerkInfDao.findByMobile(dto.getMobile());
        if (po != null) {//更新
            po.setName(dto.getName());
        } else {//新增
            po = ConverterService.convert(dto, ClerkInfPo.class);
        }
        clerkInfDao.save(po);
        dto.setClerkId(po.getClerkId());
        return dto;
    }

    @Override
    @Transactional
    public ClerkInfDto findByOpenId(String openId) {
        ClerkInfPo po = clerkInfDao.findByOpenId(openId);
        if (po == null) {
            return null;
        }
        ClerkInfDto dto = ConverterService.convert(po, ClerkInfDto.class);
        return dto;
    }

    /**
     * 根据clerk
     * @param clerkIds
     * @return
     */
    @Override
    public List<ClerkInfDto> findByClerkIds(List<String> clerkIds) {
        List<ClerkInfPo> poList = clerkInfDao.findByClerkIds(clerkIds);
        List<ClerkInfDto> dtoList = new ArrayList<>();
        for (ClerkInfPo po : poList) {
            ClerkInfDto dto = ConverterService.convert(po, ClerkInfDto.class);
            dtoList.add(dto);
        }
        return dtoList;
    }

    @Override
    @Transactional
    public ClerkInfDto findByMobile(String mobile) {
        ClerkInfPo po = clerkInfDao.findByMobile(mobile);
        if (po == null) {
            return null;
        }
        ClerkInfDto dto = ConverterService.convert(po, ClerkInfDto.class);
        return dto;
    }

    @Override
    @Transactional
    public void deleteClerk(String clerkId) {
        clerkInfDao.delete(clerkId);
        clerkApplyDao.deleteByClerkId(clerkId);
        clerkAuthDao.deleteByClerkId(clerkId);
    }

}
