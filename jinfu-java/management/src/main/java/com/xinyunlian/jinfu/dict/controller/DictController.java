package com.xinyunlian.jinfu.dict.controller;

import com.xinyunlian.jinfu.common.dto.ResultDto;
import com.xinyunlian.jinfu.common.dto.ResultDtoFactory;
import com.xinyunlian.jinfu.dict.dto.DictionaryItemDto;
import com.xinyunlian.jinfu.dict.service.DictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by dongfangchao on 2017/1/22/0022.
 */
@RestController
@RequestMapping("dict")
public class DictController {

    @Autowired
    private DictService dictService;

    /**
     * 根据字典类型code查询字典数据
     * @param dictTypeCode
     * @return
     */
    @RequestMapping(value = "getDictItemsByDictType", method = RequestMethod.GET)
    public ResultDto<Object> getDictItemsByDictType(String dictTypeCode){
        List<DictionaryItemDto> list = dictService.getDictItemsByDictType(dictTypeCode);
        return ResultDtoFactory.toAck("查询成功", list);
    }

}
