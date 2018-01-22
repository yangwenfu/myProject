package com.xinyunlian.jinfu.gather.controller;

import com.xinyunlian.jinfu.common.dto.ResultDto;
import com.xinyunlian.jinfu.common.dto.ResultDtoFactory;
import com.xinyunlian.jinfu.common.security.SecurityContext;
import com.xinyunlian.jinfu.common.util.JsonUtil;
import com.xinyunlian.jinfu.common.util.ThreadContextUtils;
import com.xinyunlian.jinfu.gather.dto.AppDownloadGaDto;
import com.xinyunlian.jinfu.gather.dto.GaWrapDto;
import org.joda.time.LocalDateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * 云联掌柜数据采集 Created by dongfangchao on 2017/3/15/0015.
 */
@RestController
@RequestMapping("/ga")
public class GaController {

	private static final Logger LOGGER = LoggerFactory.getLogger(GaController.class);

	/**
	 * 普通采集, 什么都不做, 由 filter 实现采集
	 *
	 * @return
	 */
	@RequestMapping(value = "")
	public ResultDto<Void> generalGather() {
		return ResultDtoFactory.toAck("成功");
	}

	/**
	 * 短信采集
	 * 
	 * @param ga
	 * @return
	 */
	@RequestMapping(value = "message", method = RequestMethod.POST)
	public ResultDto gaMessage(@RequestBody GaWrapDto ga, HttpServletRequest request) {
		gather(ga, "gaMessage", request);
		return ResultDtoFactory.toAck("成功");
	}

	/**
	 * 通讯录采集
	 * 
	 * @param ga
	 * @return
	 */
	@RequestMapping(value = "addressBook", method = RequestMethod.POST)
	public ResultDto gaAddressBook(@RequestBody GaWrapDto ga, HttpServletRequest request) {
		gather(ga, "gaAddressBook", request);
		return ResultDtoFactory.toAck("成功");
	}

	/**
	 * 通话记录采集
	 * 
	 * @param ga
	 * @return
	 */
	@RequestMapping(value = "callHistory", method = RequestMethod.POST)
	public ResultDto gaCallHistory(@RequestBody GaWrapDto ga, HttpServletRequest request) {
		gather(ga, "gaCallHistory", request);
		return ResultDtoFactory.toAck("成功");
	}

	/**
	 * APP下载信息采集
	 * 
	 * @param ga
	 * @return
	 */
	@RequestMapping(value = "appDownload", method = RequestMethod.POST)
	public ResultDto gaAppDownload(@RequestBody AppDownloadGaDto ga, HttpServletRequest request) {
		ga.setGatherType("gaAppDownload");
		ga.setGatherTs(LocalDateTime.now().toString("yyyy-MM-dd HH:mm:ss"));
		gatherSingle(ga);
		return ResultDtoFactory.toAck("成功");
	}

	/**
	 * APP信息采集
	 * 
	 * @param ga
	 * @return
	 */
	@RequestMapping(value = "appInfo", method = RequestMethod.POST)
	public ResultDto gaAppInfo(@RequestBody GaWrapDto ga, HttpServletRequest request) {
		gather(ga, "gaAppInfo", request);
		return ResultDtoFactory.toAck("成功");
	}

	private void gather(GaWrapDto ga, String biz, HttpServletRequest request) {
        Map<String, Object> map = ThreadContextUtils.getGaMap();
        Map<String, Object> gaMap = new HashMap<>(map);
		gaMap.put("gatherType", biz);
		gaMap.put("gatherTs", LocalDateTime.now().toString("yyyy-MM-dd HH:mm:ss"));
		gaMap.put("gatherDetail", ga);
        String traceId = ThreadContextUtils.getTraceId();
        String userId = SecurityContext.getCurrentUserId();
        if (userId == null) {
            userId = request.getHeader("ga-userid");
        }
        putToMap(gaMap, "traceId", traceId);
        putToMap(gaMap, "userId", userId);
		LOGGER.info(JsonUtil.toJson(gaMap));
	}
	
	private static final void putToMap(Map<String, Object> map, String key, Object value) {
	    if (value != null) {
	        map.put(key, value);
        }
    }

	private void gatherSingle(Object ga) {
		LOGGER.info(JsonUtil.toJson(ga));
	}

}
