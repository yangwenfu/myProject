package com.xinyunlian.jinfu.dealer.signInfo.controller;

import com.xinyunlian.jinfu.common.converter.ConverterService;
import com.xinyunlian.jinfu.common.dto.ResultDto;
import com.xinyunlian.jinfu.common.dto.ResultDtoFactory;
import com.xinyunlian.jinfu.common.security.StaticResourceSecurity;
import com.xinyunlian.jinfu.common.util.AppConfigUtil;
import com.xinyunlian.jinfu.common.util.excel.DataTablesExcelService;
import com.xinyunlian.jinfu.dealer.dto.SignInfoDto;
import com.xinyunlian.jinfu.dealer.dto.SignInfoViewDto;
import com.xinyunlian.jinfu.dealer.dto.SignInfoViewSearchDto;
import com.xinyunlian.jinfu.dealer.service.SignInfoService;
import com.xinyunlian.jinfu.dealer.signInfo.dto.SignInfoReportDto;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections.map.HashedMap;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by menglei on 2017年05月04日.
 */
@Controller
@RequestMapping(value = "dealer/sign")
@RequiresPermissions({"SIGN_MGT"})
public class SignInfoController {

    @Autowired
    private SignInfoService signInfoService;

    /**
     * 签到列表
     *
     * @param signInfoViewSearchDto
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ApiOperation(value = "签到列表")
    public ResultDto<SignInfoViewSearchDto> getPage(SignInfoViewSearchDto signInfoViewSearchDto) {
        SignInfoViewSearchDto page = signInfoService.getSignInfoPage(signInfoViewSearchDto);
        return ResultDtoFactory.toAck("获取成功", page);
    }

    /**
     * 签到详情
     *
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/detail", method = RequestMethod.GET)
    @ApiOperation(value = "签到详情")
    public ResultDto<SignInfoDto> getDetail(Long id) {
        SignInfoDto signInfoDto = signInfoService.getById(id);
        String fileAddr = AppConfigUtil.getConfig("file.addr");
        signInfoDto.setSignInStoreHeader(fileAddr + StaticResourceSecurity.getSecurityURI(signInfoDto.getSignInStoreHeader()));
        signInfoDto.setSignInStoreInner(fileAddr + StaticResourceSecurity.getSecurityURI(signInfoDto.getSignInStoreInner()));
        signInfoDto.setSignOutStoreHeader(fileAddr + StaticResourceSecurity.getSecurityURI(signInfoDto.getSignOutStoreHeader()));
        return ResultDtoFactory.toAck("获取成功", signInfoDto);
    }

    /**
     * 签到导出
     *
     * @param searchDto
     * @return
     */
    @RequestMapping(value = "/export", method = RequestMethod.GET)
    @ApiOperation(value = "签到导出")
    public ModelAndView exportDealer(SignInfoViewSearchDto searchDto) {
        List<SignInfoViewDto> list = signInfoService.getSignInfoReport(searchDto);
        List<SignInfoReportDto> data = new ArrayList<>();
        for (SignInfoViewDto signInfoViewDto : list) {
            SignInfoReportDto signInfoReportDto = ConverterService.convert(signInfoViewDto, SignInfoReportDto.class);
            data.add(signInfoReportDto);
        }
        Map<String, Object> model = new HashedMap();
        model.put("data", data);
        model.put("fileName", "分销员签到记录.xls");
        model.put("tempPath", "/templates/分销员签到记录.xls");
        DataTablesExcelService dataTablesExcelService = new DataTablesExcelService();
        return new ModelAndView(dataTablesExcelService, model);
    }

}
