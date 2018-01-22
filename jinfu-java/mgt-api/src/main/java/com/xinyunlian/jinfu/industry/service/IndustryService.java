package com.xinyunlian.jinfu.industry.service;

import com.xinyunlian.jinfu.common.exception.BizServiceException;
import com.xinyunlian.jinfu.industry.dto.IndustryDto;

import java.util.List;

/**
 * Created by dongfangchao on 2017/5/17/0017.
 */
public interface IndustryService {

    List<IndustryDto> getAllIndustry() throws BizServiceException;

    IndustryDto getByMcc(String mcc) throws BizServiceException;

}
