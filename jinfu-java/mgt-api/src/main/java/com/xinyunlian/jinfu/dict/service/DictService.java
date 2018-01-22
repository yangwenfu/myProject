package com.xinyunlian.jinfu.dict.service;

import com.xinyunlian.jinfu.dict.dto.DictionaryItemDto;

import java.util.List;

/**
 * Created by dongfangchao on 2017/1/22/0022.
 */
public interface DictService {

    /**
     * 根据字典类型获取字典数据
     * @param dictTypeCode
     * @return
     */
    List<DictionaryItemDto> getDictItemsByDictType(String dictTypeCode);

}
