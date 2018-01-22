package com.xinyunlian.jinfu.api.controller;

import com.xinyunlian.jinfu.ad.dto.AdFrontDto;
import com.xinyunlian.jinfu.ad.service.AdService;
import com.xinyunlian.jinfu.common.dto.ResultDto;
import com.xinyunlian.jinfu.common.dto.ResultDtoFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by menglei on 2016年12月13日.
 */
@RestController
@RequestMapping(value = "open-api/system")
public class ApiSystemController {

    @Autowired
    private AdService adService;

    /**
     * 广告列表
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/ad/list", method = RequestMethod.GET)
    public ResultDto<List<AdFrontDto>> getAdList(AdFrontDto adFrontDto) {
        List<AdFrontDto> list = adService.getAdFront(adFrontDto.getAdPosId(), adFrontDto.getAdPosWidth(), adFrontDto.getAdPosHeight());
        return ResultDtoFactory.toAck("获取成功", list);
    }

}
