package com.xinyunlian.jinfu.yunma.controller;

import com.xinyunlian.jinfu.bank.dto.CorpBankDto;
import com.xinyunlian.jinfu.common.dto.ResultDto;
import com.xinyunlian.jinfu.common.dto.ResultDtoFactory;
import com.xinyunlian.jinfu.common.security.SecurityContext;
import com.xinyunlian.jinfu.yunma.dto.ApplyYunmaDto;
import com.xinyunlian.jinfu.yunma.dto.YMMemberDto;
import com.xinyunlian.jinfu.yunma.dto.YMUserInfoDto;
import com.xinyunlian.jinfu.yunma.dto.YmDepotDto;
import com.xinyunlian.jinfu.yunma.enums.EDepotStatus;
import com.xinyunlian.jinfu.yunma.service.YMMemberService;
import com.xinyunlian.jinfu.yunma.service.YMUserInfoService;
import com.xinyunlian.jinfu.yunma.service.YmDepotService;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by menglei on 2017年08月31日.
 */
@Controller
@RequestMapping(value = "yunma/depot")
public class YmDepotController {

    @Autowired
    private YMUserInfoService yMUserInfoService;
    @Autowired
    private YMMemberService yMMemberService;
    @Autowired
    private YmDepotService ymDepotService;

    /**
     * 申请云码
     *
     * @param applyYunmaDto
     * @return
     */
    @RequestMapping(value = "/applyYunma", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation("申请云码")
    public ResultDto<CorpBankDto> applyYunma(@RequestBody ApplyYunmaDto applyYunmaDto) {
        YMUserInfoDto yMUserInfoDto = yMUserInfoService.findByYmUserId(SecurityContext.getCurrentUserId());
        if (yMUserInfoDto == null || StringUtils.isEmpty(yMUserInfoDto.getUserId())) {
            return ResultDtoFactory.toNack("用户未绑定");//用户没有绑定微信，需先绑定微信
        }
        YMMemberDto yMMemberDto = yMMemberService.getMemberByQrCodeNo(applyYunmaDto.getQrCodeNo());
        if (yMMemberDto == null) {
            return ResultDtoFactory.toNack("云码不存在");
        }
        YmDepotDto dto = ymDepotService.findByQrCodeNo(applyYunmaDto.getQrCodeNo());
        if (dto == null || !EDepotStatus.BIND_UNUSE.equals(dto.getStatus())) {
            return ResultDtoFactory.toNack("该云码无法申请实体码");
        }
        YmDepotDto ymDepotDto = new YmDepotDto();
        ymDepotDto.setMailName(applyYunmaDto.getName());
        ymDepotDto.setMailMobile(applyYunmaDto.getMobile());
        ymDepotDto.setMailAddress(applyYunmaDto.getProvince() + applyYunmaDto.getCity() + applyYunmaDto.getArea() + applyYunmaDto.getAddress());
        ymDepotDto.setQrCodeNo(applyYunmaDto.getQrCodeNo());
        ymDepotService.updateMailInfo(ymDepotDto);
        return ResultDtoFactory.toAck("添加成功");
    }

}
