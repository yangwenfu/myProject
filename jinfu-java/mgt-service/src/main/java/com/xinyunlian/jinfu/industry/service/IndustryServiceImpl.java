package com.xinyunlian.jinfu.industry.service;

import com.xinyunlian.jinfu.common.converter.ConverterService;
import com.xinyunlian.jinfu.common.exception.BizServiceException;
import com.xinyunlian.jinfu.industry.dao.IndustryDao;
import com.xinyunlian.jinfu.industry.dto.IndustryDto;
import com.xinyunlian.jinfu.industry.entity.IndustryPo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dongfangchao on 2017/5/17/0017.
 */
@Service
public class IndustryServiceImpl implements IndustryService {

    @Autowired
    private IndustryDao industryDao;

    @Override
    public List<IndustryDto> getAllIndustry() throws BizServiceException {
        List<IndustryDto> retList = new ArrayList<>();

        List<IndustryPo> list = industryDao.findAll();
        if (!CollectionUtils.isEmpty(list)){
            list.forEach(item -> {
                IndustryDto dto = ConverterService.convert(item, IndustryDto.class);
                retList.add(dto);
            });
        }
        return retList;
    }

    @Override
    public IndustryDto getByMcc(String mcc) throws BizServiceException {
        IndustryPo po = industryDao.findByMcc(mcc);
        return ConverterService.convert(po, IndustryDto.class);
    }
}
