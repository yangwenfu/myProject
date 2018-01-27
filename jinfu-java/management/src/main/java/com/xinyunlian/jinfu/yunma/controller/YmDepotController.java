package com.xinyunlian.jinfu.yunma.controller;

import com.xinyunlian.jinfu.common.dto.ResultDto;
import com.xinyunlian.jinfu.common.dto.ResultDtoFactory;
import com.xinyunlian.jinfu.yunma.dto.*;
import com.xinyunlian.jinfu.yunma.enums.EDepotReceiveStatus;
import com.xinyunlian.jinfu.yunma.enums.EDepotStatus;
import com.xinyunlian.jinfu.yunma.service.YmDepotService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by menglei on 2017/8/29.
 */
@Controller
@RequestMapping(value = "yunma/depot")
public class YmDepotController {
    private static final Logger LOGGER = LoggerFactory.getLogger(YmDepotController.class);

    @Autowired
    private YmDepotService ymDepotService;

    /**
     * 云码库列表
     *
     * @param searchDto
     * @return
     */
    @RequestMapping(value = "list", method = RequestMethod.GET)
    @ResponseBody
    public ResultDto<Object> getList(YmDepotSearchDto searchDto) {
        searchDto = ymDepotService.getDepotPage(searchDto);
        List<YmDepotDto> bindList = ymDepotService.findBind();
        searchDto.setBindCount(bindList.size());
        return ResultDtoFactory.toAck("获取成功", searchDto);
    }

    /**
     * 批量添加云码
     *
     * @param ymBatchSaveDto
     * @return
     */
    @RequestMapping(value = "batchSave", method = RequestMethod.POST)
    @ResponseBody
    public ResultDto<Map<String, Object>> update(@RequestBody YmBatchSaveDto ymBatchSaveDto) {
        if (ymBatchSaveDto.getStartNo() == null || ymBatchSaveDto.getEndNo() == null) {
            return ResultDtoFactory.toNack("开始，截止云码编号不能为空");
        }
        if (String.valueOf(ymBatchSaveDto.getStartNo()).length() != 14 || String.valueOf(ymBatchSaveDto.getEndNo()).length() != 14) {
            return ResultDtoFactory.toNack("开始，截止云码编号长度为14位");
        }
        if (ymBatchSaveDto.getStartNo() <= 0 || ymBatchSaveDto.getEndNo() <= 0) {
            return ResultDtoFactory.toNack("开始，截止云码编号格式不正确");
        }
        Long result = ymBatchSaveDto.getEndNo() - ymBatchSaveDto.getStartNo();
        if (result < 1 || result > 1000) {
            return ResultDtoFactory.toNack("开始，截止云码编号格式不正确");
        }
        List<YmDepotDto> ymDepotDtos = new ArrayList<>();
        List<String> qrCodeNos = new ArrayList<>();
        YmDepotDto ymDepotDto;
        String batchNo = String.valueOf(System.currentTimeMillis()).substring(0, 10);
        for (Long i = ymBatchSaveDto.getStartNo(); i <= ymBatchSaveDto.getEndNo(); i++) {
            ymDepotDto = new YmDepotDto();
            ymDepotDto.setQrCodeNo("ym" + i);
            ymDepotDto.setStatus(EDepotStatus.UNBIND_UNUSE);
            ymDepotDto.setReceiveStatus(EDepotReceiveStatus.UNRECEIVE);
            ymDepotDto.setRemark(ymBatchSaveDto.getRemark());
            ymDepotDto.setBatchNo(batchNo);
            ymDepotDtos.add(ymDepotDto);
            qrCodeNos.add("ym" + i);
        }
        List<YmDepotDto> addedlist = ymDepotService.findByQrCodeNo(qrCodeNos);//已经添加的云码
        List<String> addeds = new ArrayList<>();
        Map<String, Object> resultMap = new HashMap<>();
        for (YmDepotDto dto : addedlist) {
            addeds.add(dto.getQrCodeNo());
        }
        if (!CollectionUtils.isEmpty(addeds)) {
            resultMap.put("addedList", addeds);//已经添加的云码
            return ResultDtoFactory.toNack("已存在已经添加的云码", resultMap);
        }
        ymDepotService.saveBatch(ymDepotDtos);
        //处理已经绑定的云码
        List<YmDepotViewDto> errorList = ymDepotService.findErrorQrCodeNo();
        if (!CollectionUtils.isEmpty(errorList)) {
            List<String> errorQrCodeNos = new ArrayList<>();
            for (YmDepotViewDto ymDepotViewDto : errorList) {
                errorQrCodeNos.add(ymDepotViewDto.getQrCodeNo());
            }
            ymDepotService.updateBatchUsed(errorQrCodeNos);
        }
        return ResultDtoFactory.toAck("操作成功");
    }

    /**
     * 领用云码
     *
     * @param ymDepotSearchDto
     * @return
     */
    @RequestMapping(value = "used", method = RequestMethod.POST)
    @ResponseBody
    public ResultDto<Map<String, Object>> used(@RequestBody YmDepotSearchDto ymDepotSearchDto) {
        if (CollectionUtils.isEmpty(ymDepotSearchDto.getQrCodeNos())) {
            return ResultDtoFactory.toAck("操作成功");
        }
        List<YmDepotDto> addedlist = ymDepotService.findByQrCodeNo(ymDepotSearchDto.getQrCodeNos());//已经添加的云码
        List<String> list = new ArrayList<>();
        for (YmDepotDto ymDepotDto : addedlist) {
            if (EDepotStatus.UNBIND_UNUSE.equals(ymDepotDto.getStatus()) || EDepotStatus.BIND_UNUSE.equals(ymDepotDto.getStatus())) {
                list.add(ymDepotDto.getQrCodeNo());
            }
        }
        if (!CollectionUtils.isEmpty(list)) {
            ymDepotService.updateBatchUsed(list);
        }
        return ResultDtoFactory.toAck("操作成功");
    }
}
