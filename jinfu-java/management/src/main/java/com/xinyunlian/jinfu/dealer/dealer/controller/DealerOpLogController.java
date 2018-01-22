package com.xinyunlian.jinfu.dealer.dealer.controller;

import com.alibaba.fastjson.JSONObject;
import com.xinyunlian.jinfu.common.dto.ResultDto;
import com.xinyunlian.jinfu.common.dto.ResultDtoFactory;
import com.xinyunlian.jinfu.common.security.StaticResourceSecurity;
import com.xinyunlian.jinfu.common.util.AppConfigUtil;
import com.xinyunlian.jinfu.dealer.dealer.dto.resp.DealerDetailDto;
import com.xinyunlian.jinfu.dealer.dealer.dto.resp.DealerOpLogDetailDto;
import com.xinyunlian.jinfu.dealer.dto.DealerOpLogDto;
import com.xinyunlian.jinfu.dealer.service.DealerOpLogService;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by menglei on 2017年05月10日.
 */
@Controller
@RequestMapping(value = "dealer/opLog")
@RequiresPermissions({"DT_OP_LOG"})
public class DealerOpLogController {

    @Autowired
    private DealerOpLogService dealerOpLogService;

    /**
     * 分销商操作日志
     *
     * @param dealerId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ApiOperation(value = "分销商操作日志")
    public ResultDto<List<DealerOpLogDto>> list(@RequestParam String dealerId) {
        List<DealerOpLogDto> list = dealerOpLogService.getByDealerId(dealerId);
        return ResultDtoFactory.toAck("获取成功", list);
    }

    /**
     * 分销商修改详情
     *
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/detail", method = RequestMethod.GET)
    @ApiOperation(value = "分销商修改详情")
    public ResultDto<DealerOpLogDetailDto> detail(@RequestParam Long id) {
        DealerOpLogDetailDto dealerOpLogDetailDto = new DealerOpLogDetailDto();

        DealerOpLogDto dealerOpLogDto = dealerOpLogService.getById(id);
        if (dealerOpLogDto == null) {
            return ResultDtoFactory.toNack("日志不存在");
        }
        String fileAddr = AppConfigUtil.getConfig("file.addr");
        if (StringUtils.isNotEmpty(dealerOpLogDto.getFrontBase())) {
            DealerDetailDto oldDealerDetailDto = JSONObject.parseObject(dealerOpLogDto.getFrontBase(), DealerDetailDto.class);
            if (StringUtils.isNotEmpty(oldDealerDetailDto.getDealerExtraDto().getBizLicencePic())) {
                oldDealerDetailDto.getDealerExtraDto().setBizLicencePic(fileAddr + StaticResourceSecurity.getSecurityURI(oldDealerDetailDto.getDealerExtraDto().getBizLicencePic()));
            }
            if (StringUtils.isNotEmpty(oldDealerDetailDto.getDealerExtraDto().getIdCardNoPic1())) {
                oldDealerDetailDto.getDealerExtraDto().setIdCardNoPic1(fileAddr + StaticResourceSecurity.getSecurityURI(oldDealerDetailDto.getDealerExtraDto().getIdCardNoPic1()));
            }
            if (StringUtils.isNotEmpty(oldDealerDetailDto.getDealerExtraDto().getIdCardNoPic2())) {
                oldDealerDetailDto.getDealerExtraDto().setIdCardNoPic2(fileAddr + StaticResourceSecurity.getSecurityURI(oldDealerDetailDto.getDealerExtraDto().getIdCardNoPic2()));
            }
            if (StringUtils.isNotEmpty(oldDealerDetailDto.getDealerExtraDto().getAccountLicencePic())) {
                oldDealerDetailDto.getDealerExtraDto().setAccountLicencePic(fileAddr + StaticResourceSecurity.getSecurityURI(oldDealerDetailDto.getDealerExtraDto().getAccountLicencePic()));
            }
            dealerOpLogDetailDto.setDealerDetailFront(oldDealerDetailDto);
        }
        if (StringUtils.isNotEmpty(dealerOpLogDto.getAfterBase())) {
            DealerDetailDto dealerDetailDto = JSONObject.parseObject(dealerOpLogDto.getAfterBase(), DealerDetailDto.class);
            if (StringUtils.isNotEmpty(dealerDetailDto.getDealerExtraDto().getBizLicencePic())) {
                dealerDetailDto.getDealerExtraDto().setBizLicencePic(fileAddr + StaticResourceSecurity.getSecurityURI(dealerDetailDto.getDealerExtraDto().getBizLicencePic()));
            }
            if (StringUtils.isNotEmpty(dealerDetailDto.getDealerExtraDto().getIdCardNoPic1())) {
                dealerDetailDto.getDealerExtraDto().setIdCardNoPic1(fileAddr + StaticResourceSecurity.getSecurityURI(dealerDetailDto.getDealerExtraDto().getIdCardNoPic1()));
            }
            if (StringUtils.isNotEmpty(dealerDetailDto.getDealerExtraDto().getIdCardNoPic2())) {
                dealerDetailDto.getDealerExtraDto().setIdCardNoPic2(fileAddr + StaticResourceSecurity.getSecurityURI(dealerDetailDto.getDealerExtraDto().getIdCardNoPic2()));
            }
            if (StringUtils.isNotEmpty(dealerDetailDto.getDealerExtraDto().getAccountLicencePic())) {
                dealerDetailDto.getDealerExtraDto().setAccountLicencePic(fileAddr + StaticResourceSecurity.getSecurityURI(dealerDetailDto.getDealerExtraDto().getAccountLicencePic()));
            }
            dealerOpLogDetailDto.setDealerDetailAfter(dealerDetailDto);
        }
        return ResultDtoFactory.toAck("获取成功", dealerOpLogDetailDto);
    }
}
