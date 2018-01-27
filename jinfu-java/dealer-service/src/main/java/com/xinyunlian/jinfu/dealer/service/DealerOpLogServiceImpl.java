package com.xinyunlian.jinfu.dealer.service;

import com.xinyunlian.jinfu.common.converter.ConverterService;
import com.xinyunlian.jinfu.dealer.dao.DealerOpLogDao;
import com.xinyunlian.jinfu.dealer.dto.DealerOpLogDto;
import com.xinyunlian.jinfu.dealer.entity.DealerOpLogPo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by menglei on 2017年05月09日.
 */
@Service
public class DealerOpLogServiceImpl implements DealerOpLogService {

    @Autowired
    private DealerOpLogDao dealerOpLogDao;

    @Override
    public List<DealerOpLogDto> getByDealerId(String dealerId) {
        List<DealerOpLogPo> list = dealerOpLogDao.findByDealerIdOrderByIdDesc(dealerId);

        List<DealerOpLogDto> result = new ArrayList<>();
        for (DealerOpLogPo po : list) {
            DealerOpLogDto dto = ConverterService.convert(po, DealerOpLogDto.class);
            result.add(dto);
        }
        return result;
    }

    @Transactional
    @Override
    public void createDealerOpLog(DealerOpLogDto dealerOpLogDto) {
        if (dealerOpLogDto != null) {
            DealerOpLogPo dealerOpLogPo = ConverterService.convert(dealerOpLogDto, DealerOpLogPo.class);
            dealerOpLogDao.save(dealerOpLogPo);
        }
    }

    @Override
    public DealerOpLogDto getById(Long id) {
        DealerOpLogPo po = dealerOpLogDao.findOne(id);
        if(null == po){
            return null;
        }
        DealerOpLogDto dto = ConverterService.convert(po, DealerOpLogDto.class);
        return dto;
    }

}
