package com.xinyunlian.jinfu.virtual.controller;

import com.xinyunlian.jinfu.area.dto.SysAreaInfDto;
import com.xinyunlian.jinfu.area.service.SysAreaInfService;
import com.xinyunlian.jinfu.common.cache.constant.CacheType;
import com.xinyunlian.jinfu.common.converter.ConverterService;
import com.xinyunlian.jinfu.common.dto.ResultDto;
import com.xinyunlian.jinfu.common.dto.ResultDtoFactory;
import com.xinyunlian.jinfu.common.security.SecurityContext;
import com.xinyunlian.jinfu.common.util.excel.DataTablesExcelService;
import com.xinyunlian.jinfu.user.service.MgtUserService;
import com.xinyunlian.jinfu.virtual.dto.VirtualTboRecordDto;
import com.xinyunlian.jinfu.report.virtual.dto.VirtualTboSearchDto;
import com.xinyunlian.jinfu.virtual.dto.VirtualTobaccoDto;
import com.xinyunlian.jinfu.virtual.enums.ETakeStatus;
import com.xinyunlian.jinfu.report.virtual.service.VirtualTboReportService;
import com.xinyunlian.jinfu.virtual.service.VirtualTobaccoService;
import org.apache.commons.collections.map.HashedMap;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.*;

/**
 * Created by jll on 2016-12-7.
 */
@RestController
@RequestMapping("virtual/tobacco")
@RequiresPermissions({"V_TOBACC_CER_POOL"})
public class VirtualTobaccoController {
    private final static String VIRTUAL_TOBACCO_REDEIS_SUFFIX = "VirtualTobacco_";
    @Autowired
    private VirtualTobaccoService virtualTobaccoService;
    @Autowired
    private RedisCacheManager redisCacheManager;
    @Autowired
    private VirtualTboReportService virtualTboReportService;
    @Autowired
    private SysAreaInfService sysAreaInfService;
    @Autowired
    private MgtUserService mgtUserService;

    /**
     * 领用虚拟烟草证
     * @param virtualTobaccoDto
     * @param num
     * @return
     */
    @RequestMapping(value = "/take", method = RequestMethod.POST)
    public ResultDto<Map<String,List<VirtualTobaccoDto>>> take(@RequestBody VirtualTobaccoDto virtualTobaccoDto, @RequestParam int num) {
        SysAreaInfDto sysAreaInfDto = sysAreaInfService.getSysAreaInfById(virtualTobaccoDto.getAreaId());
        virtualTobaccoDto.setAreaCode(sysAreaInfDto.getGbCode());
        virtualTobaccoDto.setTreePath(sysAreaInfDto.getTreePath());
        virtualTobaccoDto.setAssignPerson(SecurityContext.getCurrentUserId());
        List<VirtualTobaccoDto> virtualTobaccoDtos = virtualTobaccoService.take(virtualTobaccoDto,num);
        String uuid = UUID.randomUUID().toString();
        redisCacheManager.getCache(CacheType.DEFAULT).put(VIRTUAL_TOBACCO_REDEIS_SUFFIX + uuid,virtualTobaccoDtos);
        Map<String,List<VirtualTobaccoDto>> map = new HashMap<>();
        map.put(uuid,virtualTobaccoDtos);
        return ResultDtoFactory.toAck("获取成功",map);
    }

    @RequestMapping(value = "/down", method = RequestMethod.GET)
    public ModelAndView down(@RequestParam String uuid) {
        List<VirtualTobaccoDto> virtualTobaccoDtos = redisCacheManager
                .getCache(CacheType.DEFAULT).get(VIRTUAL_TOBACCO_REDEIS_SUFFIX + uuid,List.class);
        List<VirtualTboRecordDto> records = new ArrayList<>();
        virtualTobaccoDtos.forEach(virtualTobaccoDto -> {
            VirtualTboRecordDto record = ConverterService.convert(virtualTobaccoDto, VirtualTboRecordDto.class);
            records.add(record);
        });
        Map<String, Object> model = new HashedMap();
        model.put("data", records);
        model.put("fileName","烟草证号.xls");
        model.put("tempPath","/templates/虚拟烟草证模板.xls");
        DataTablesExcelService dataTablesExcelService = new DataTablesExcelService();
        redisCacheManager.getCache(CacheType.DEFAULT).evict(VIRTUAL_TOBACCO_REDEIS_SUFFIX + uuid);
        return new ModelAndView(dataTablesExcelService, model);
    }


    /**
     * 获取领用虚拟烟草证个数
     * @return
     */
    @RequestMapping(value = "/countTaked", method = RequestMethod.GET)
    public ResultDto<Long> countByTaked() {
        Long count = virtualTobaccoService.countByTakeStatus(ETakeStatus.TAKED);
        return ResultDtoFactory.toAck("获取成功",count);
    }

    /**
     * 获取已使用虚拟烟草证个数
     * @return
     */
    @RequestMapping(value = "/countUsed", method = RequestMethod.GET)
    public ResultDto<Long> countUsed() {
        Long count = virtualTobaccoService.countUsed();
        return ResultDtoFactory.toAck("获取成功",count);
    }

    /**
     * 分页查询
     * @param virtualTboSearchDto
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ResultDto<VirtualTboSearchDto> getPage(VirtualTboSearchDto virtualTboSearchDto){
        VirtualTboSearchDto dto = virtualTboReportService.getPage(virtualTboSearchDto);
        return ResultDtoFactory.toAck("获取成功", dto);
    }

}
