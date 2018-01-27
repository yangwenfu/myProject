package com.xinyunlian.jinfu.order.service;

import com.xinyunlian.jinfu.common.converter.ConverterService;
import com.xinyunlian.jinfu.order.dao.ThirdOrderProdDao;
import com.xinyunlian.jinfu.order.dto.ThirdOrderProdCountDto;
import com.xinyunlian.jinfu.order.dto.ThirdOrderProdDto;
import com.xinyunlian.jinfu.order.entity.ThirdOrderProdPo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by menglei on 2017年03月08日.
 */
@Service
public class ThirdOrderProdServiceImpl implements ThirdOrderProdService {

    @Autowired
    private ThirdOrderProdDao thirdOrderProdDao;

    @Transactional
    @Override
    public List<ThirdOrderProdDto> getListByOrderId(Long orderId) {
        List<ThirdOrderProdPo> poList = thirdOrderProdDao.findByOrderId(orderId);
        List<ThirdOrderProdDto> list = new ArrayList<>();
        for (ThirdOrderProdPo po : poList) {
            ThirdOrderProdDto dto = ConverterService.convert(po, ThirdOrderProdDto.class);
            list.add(dto);
        }
        return list;
    }

    @Transactional
    @Override
    public ThirdOrderProdDto getOne(Long orderProdId) {
        ThirdOrderProdPo po = thirdOrderProdDao.findOne(orderProdId);
        if (po == null) {
            return null;
        }
        ThirdOrderProdDto dto = ConverterService.convert(po, ThirdOrderProdDto.class);
        return dto;
    }

    /**
     * 根据订单列表查订单可绑码数
     *
     * @param orderIds
     * @return
     */
    @Override
    public List<ThirdOrderProdCountDto> getCountByOrderId(List<Long> orderIds) {
        List<Object[]> list = thirdOrderProdDao.findCountByOrderId(orderIds);
        List<ThirdOrderProdCountDto> dtoList = new ArrayList<>();
        ThirdOrderProdCountDto thirdOrderProdCountDto;
        for (Object[] object : list) {
            thirdOrderProdCountDto = new ThirdOrderProdCountDto();
            thirdOrderProdCountDto.setOrderId(Long.valueOf(object[0].toString()));
            thirdOrderProdCountDto.setBindCount(Integer.valueOf(object[1].toString()));
            dtoList.add(thirdOrderProdCountDto);
        }
        return dtoList;
    }

}
