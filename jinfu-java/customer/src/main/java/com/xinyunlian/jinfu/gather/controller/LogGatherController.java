package com.xinyunlian.jinfu.gather.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.xinyunlian.jinfu.common.dto.ResultDto;
import com.xinyunlian.jinfu.common.dto.ResultDtoFactory;
import com.xinyunlian.jinfu.common.util.JsonUtil;
import com.xinyunlian.jinfu.gather.dto.AppLogDto;

/**
 * @author Sephy
 * @since: 2017-05-16
 */
@RestController
public class LogGatherController {

	private static final Logger LOGGER = LoggerFactory.getLogger(LogGatherController.class);

	@RequestMapping(value = "/log", method = RequestMethod.POST)
	public ResultDto<Void> postLogData(@RequestBody AppLogDto logDto) {
		LOGGER.info(JsonUtil.toJson(logDto));
        return ResultDtoFactory.toAck("成功");
	}
}
