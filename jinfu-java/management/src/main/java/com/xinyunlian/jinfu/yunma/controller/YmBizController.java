package com.xinyunlian.jinfu.yunma.controller;

import com.xinyunlian.jinfu.common.dto.ResultDto;
import com.xinyunlian.jinfu.common.dto.ResultDtoFactory;
import com.xinyunlian.jinfu.common.util.MessageUtil;
import com.xinyunlian.jinfu.trade.dto.YmBizDto;
import com.xinyunlian.jinfu.trade.service.YmBizService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by King on 2017/1/6.
 */
@Controller
@RequestMapping(value = "yunma/biz")
public class YmBizController {
    private static final Logger LOGGER = LoggerFactory.getLogger(YmBizController.class);

    @Autowired
    private YmBizService bizService;


    /**
     * 获取默认费率配置
     * @return
     */
    @RequestMapping(value = "list", method = RequestMethod.POST)
    @ResponseBody
    public ResultDto<Object> getList() {
        List<YmBizDto> bizDtos = bizService.findAll();
        return ResultDtoFactory.toAck(MessageUtil.getMessage("common.operate.success"), bizDtos);
    }

    /**
     * 修改默认费率配置
     * @param ymBizDtos
     * @return
     */
    @RequestMapping(value = "update", method = RequestMethod.POST)
    @ResponseBody
    public ResultDto<Object> update(@RequestBody List<YmBizDto> ymBizDtos) {
        bizService.update(ymBizDtos);
        return ResultDtoFactory.toAck(MessageUtil.getMessage("common.operate.success"));

    }
}
