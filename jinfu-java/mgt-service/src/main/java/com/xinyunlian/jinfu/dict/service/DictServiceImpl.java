package com.xinyunlian.jinfu.dict.service;

import com.xinyunlian.jinfu.common.converter.ConverterService;
import com.xinyunlian.jinfu.dict.dao.DictionaryItemDao;
import com.xinyunlian.jinfu.dict.dto.DictionaryItemDto;
import com.xinyunlian.jinfu.dict.entity.DictionaryItemPo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by dongfangchao on 2017/1/22/0022.
 */
@Service
public class DictServiceImpl implements DictService {

    @Autowired
    private DictionaryItemDao dictionaryItemDao;

    @Override
    public List<DictionaryItemDto> getDictItemsByDictType(String dictTypeCode) {
        List<DictionaryItemDto> retList = new ArrayList<>();

        List<DictionaryItemPo> list = dictionaryItemDao.findByDictTypeCode(dictTypeCode);
        if (!CollectionUtils.isEmpty(list)){
            retList = list.stream()
                    .map(item -> ConverterService.convert(item, DictionaryItemDto.class))
                    .collect(Collectors.toList());
        }
        return retList;
    }
}
