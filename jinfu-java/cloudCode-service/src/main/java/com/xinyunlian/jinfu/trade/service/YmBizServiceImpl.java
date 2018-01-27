package com.xinyunlian.jinfu.trade.service;

import com.xinyunlian.jinfu.common.converter.ConverterService;
import com.xinyunlian.jinfu.trade.dao.YmBizDao;
import com.xinyunlian.jinfu.trade.dto.YmBizDto;
import com.xinyunlian.jinfu.trade.entity.YmBizPo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 云码业务配置ServiceImpl
 * @author jll
 * @version 
 */

@Service
public class YmBizServiceImpl implements YmBizService {

    private static final Logger LOGGER = LoggerFactory.getLogger(YmBizServiceImpl.class);

	@Autowired
	private YmBizDao ymBizDao;

	public List<YmBizDto> findAll(){
		List<YmBizDto> ymBizDtos = new ArrayList<>();
		List<YmBizPo> ymBizPos = ymBizDao.findAll();
		if(!CollectionUtils.isEmpty(ymBizPos)){
			ymBizPos.forEach(ymBizPo -> {
				YmBizDto ymBizDto = ConverterService.convert(ymBizPo,YmBizDto.class);
				ymBizDtos.add(ymBizDto);
			});
		}
		return ymBizDtos;
	}

	@Transactional
	public void update(List<YmBizDto> ymBizDtos){
        if(!CollectionUtils.isEmpty(ymBizDtos)){
            ymBizDtos.forEach(ymBizDto -> {
                if(null != ymBizDto.getId()){
                    YmBizPo po = ymBizDao.findOne(ymBizDto.getId());
                    if(po.getCode() == ymBizDto.getCode()){
                        po.setRate(ymBizDto.getRate());
                        po.setSettlement(ymBizDto.getSettlement());
                    }
                }
            });
        }
	}
}
