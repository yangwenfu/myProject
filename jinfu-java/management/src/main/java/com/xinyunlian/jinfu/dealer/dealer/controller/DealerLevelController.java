package com.xinyunlian.jinfu.dealer.dealer.controller;

import com.xinyunlian.jinfu.common.dto.ResultDto;
import com.xinyunlian.jinfu.common.dto.ResultDtoFactory;
import com.xinyunlian.jinfu.dealer.dto.DealerLevelDto;
import com.xinyunlian.jinfu.dealer.service.DealerLevelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by menglei on 2016年09月05日.
 */
@Controller
@RequestMapping(value = "dealer/level")
public class DealerLevelController {

    @Autowired
    private DealerLevelService dealerLevelService;

    /**
     * 查询分销商级别列表
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ResultDto<List<DealerLevelDto>> getLevelList() {
        List<DealerLevelDto> list = dealerLevelService.getDealerLevelList();
        return ResultDtoFactory.toAck("获取成功", list);
    }

}
