package com.xinyunlian.jinfu.yunma.controller;

import com.xinyunlian.jinfu.common.dto.ResultDto;
import com.xinyunlian.jinfu.common.dto.ResultDtoFactory;
import com.xinyunlian.jinfu.common.util.MessageUtil;
import com.xinyunlian.jinfu.yunma.dto.YMRouterAgentDto;
import com.xinyunlian.jinfu.yunma.dto.YMRouterSearchDto;
import com.xinyunlian.jinfu.yunma.service.YMRouterAgentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * Created by King on 2017/1/6.
 */
@Controller
@RequestMapping(value = "yunma/router")
public class YmRouterController {
    private static final Logger LOGGER = LoggerFactory.getLogger(YmRouterController.class);

    @Autowired
    private YMRouterAgentService ymRouterAgentService;


    /**
     * 路由新增
     * @param dto
     * @return
     */
    @RequestMapping(value = "add", method = RequestMethod.POST)
    @ResponseBody
    public ResultDto<Object> add(@RequestBody YMRouterAgentDto dto) {
        ymRouterAgentService.addRouterAgent(dto);
        return ResultDtoFactory.toAck(MessageUtil.getMessage("common.operate.success"));
    }

    /**
     *删除
     * @param id
     * @return
     */
    @RequestMapping(value = "delete", method = RequestMethod.POST)
    @ResponseBody
    public ResultDto<Object> delete(@RequestParam Long id) {
        ymRouterAgentService.delete(id);
        return ResultDtoFactory.toAck(MessageUtil.getMessage("common.operate.success"));
    }

    /**
     *更新
     * @param dto
     * @return
     */
    @RequestMapping(value = "update", method = RequestMethod.POST)
    @ResponseBody
    public ResultDto<Object> update(@RequestBody YMRouterAgentDto dto) {
        ymRouterAgentService.updateRouterAgent(dto);
        return ResultDtoFactory.toAck(MessageUtil.getMessage("common.operate.success"));

    }

    /**
     * 获取路由列表
     * @param searchDto
     * @return
     */
    @RequestMapping(value = "list", method = RequestMethod.POST)
    @ResponseBody
    public ResultDto<Object> getList(@RequestBody YMRouterSearchDto searchDto){
        searchDto = ymRouterAgentService.getRouterPage(searchDto);
        return ResultDtoFactory.toAck(MessageUtil.getMessage("common.operate.success"),searchDto);
    }
}
