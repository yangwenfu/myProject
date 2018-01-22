package com.xinyunlian.jinfu.store.service;

import com.xinyunlian.jinfu.common.converter.ConverterService;
import com.xinyunlian.jinfu.store.dao.FranchiseDao;
import com.xinyunlian.jinfu.store.dto.FranchiseDto;
import com.xinyunlian.jinfu.store.entity.FranchisePo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by menglei on 2017年03月24日.
 */
@Service
public class FranchiseServiceImpl implements FranchiseService {

    @Autowired
    private FranchiseDao franchiseDao;

    @Override
    @Transactional
    public FranchiseDto getByStoreId(Long storeId) {
        FranchisePo po = franchiseDao.findByStoreId(storeId);
        if (po == null) {
            return null;
        }
        FranchiseDto dto = ConverterService.convert(po, FranchiseDto.class);
        return dto;
    }

    @Transactional
    @Override
    public List<FranchiseDto> getByStoreIds(List<Long> storeIds) {
        List<FranchisePo> poList = franchiseDao.findByStoreIdIn(storeIds);
        List<FranchiseDto> list = new ArrayList<>();
        for (FranchisePo po : poList) {
            FranchiseDto dto = ConverterService.convert(po, FranchiseDto.class);
            list.add(dto);
        }
        return list;
    }

}
