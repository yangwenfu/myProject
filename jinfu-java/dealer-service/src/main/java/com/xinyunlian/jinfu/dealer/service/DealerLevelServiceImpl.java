package com.xinyunlian.jinfu.dealer.service;

import com.xinyunlian.jinfu.common.converter.ConverterService;
import com.xinyunlian.jinfu.dealer.dao.DealerLevelDao;
import com.xinyunlian.jinfu.dealer.dto.DealerLevelDto;
import com.xinyunlian.jinfu.dealer.entity.DealerLevelPo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by menglei on 2016年08月26日.
 */
@Service
public class DealerLevelServiceImpl implements DealerLevelService {

    @Autowired
    private DealerLevelDao dealerLevelDao;

    @Override
    public List<DealerLevelDto> getDealerLevelList() {
        List<DealerLevelPo> list = dealerLevelDao.findAll();
        List<DealerLevelDto> result = new ArrayList<>();
        if (!CollectionUtils.isEmpty(list)) {
            for (DealerLevelPo po : list) {
                DealerLevelDto dto = ConverterService.convert(po, DealerLevelDto.class);
                result.add(dto);
            }
        }
        return result;
    }

}
