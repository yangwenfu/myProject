package com.xinyunlian.jinfu.ad.controller;

import com.xinyunlian.jinfu.ad.dto.AdPosSearchDto;
import com.xinyunlian.jinfu.ad.dto.AdPosSizeDto;
import com.xinyunlian.jinfu.ad.dto.AdPositionDto;
import com.xinyunlian.jinfu.ad.service.AdPosService;
import com.xinyunlian.jinfu.common.dto.ResultDto;
import com.xinyunlian.jinfu.common.dto.ResultDtoFactory;
import com.xinyunlian.jinfu.common.exception.BizServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

/**
 * Created by dongfangchao on 2016/12/21/0021.
 */
@RestController
@RequestMapping("adPos")
public class AdPosController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AdPosController.class);

    @Autowired
    private AdPosService adPosService;

    /**
     * 分页获取广告位
     * @param searchDto
     * @return
     */
    @RequestMapping(value = "/page", method = RequestMethod.GET)
    public ResultDto<AdPosSearchDto> getAdPosPage(@ApiIgnore AdPosSearchDto searchDto){
        AdPosSearchDto page = adPosService.getAdPositionPage(searchDto);
        return ResultDtoFactory.toAck("获取成功", page);
    }

    /**
     * 新增广告位
     * @param dto
     * @return
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResultDto<Object> addAdPos(@RequestBody @ApiIgnore AdPositionDto dto){
        try {
            adPosService.saveAdPos(dto);
            return ResultDtoFactory.toAck("新增成功");
        } catch (BizServiceException e) {
            LOGGER.error("新增广告位失败", e);
            return ResultDtoFactory.toNack(e.getError());
        }
    }

    /**
     * 删除广告位
     * @param posId
     * @return
     */
    @RequestMapping(value = "/{posId}", method = RequestMethod.DELETE)
    public ResultDto<Object> delete(@PathVariable Long posId){
        try {
            adPosService.deleteAdPos(posId);
            return ResultDtoFactory.toAck("删除成功");
        } catch (BizServiceException e) {
            LOGGER.error("删除广告位异常", e);
            return ResultDtoFactory.toNack(e.getError());
        }
    }

    /**
     * 更新广告位
     * @param dto
     * @return
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ResultDto<Object> update(@RequestBody @ApiIgnore AdPositionDto dto){
        try {
            adPosService.updateAdPos(dto);
            return ResultDtoFactory.toAck("更新成功");
        } catch (BizServiceException e) {
            LOGGER.error("更新广告位失败", e);
            return ResultDtoFactory.toNack(e.getError());
        }
    }

    /**
     * 批量删除
     * @param posIds
     * @return
     */
    @RequestMapping(value = "/deleteBatch", method = RequestMethod.POST)
    public ResultDto<Object> deleteBatch(@RequestBody Long[] posIds){
        try {
            if (posIds == null || posIds.length == 0){
                return ResultDtoFactory.toNack("待删除广告id不能为空");
            }

            for (Long posId: posIds) {
                adPosService.deleteAdPos(posId);
            }
            return ResultDtoFactory.toAck("删除成功");
        } catch (BizServiceException e) {
            LOGGER.error("删除广告位异常", e);
            return ResultDtoFactory.toNack(e.getError());
        }
    }

    /**
     * 获取所有正常的广告位列表
     * @return
     */
    @RequestMapping(value = "/getAdPosList", method = RequestMethod.GET)
    public ResultDto<Object> getAdPosList(){
        List<AdPositionDto> list = adPosService.getAdPosList();
        return ResultDtoFactory.toAck("查询成功", list);
    }

    /**
     * 根据广告位id获取广告尺寸列表
     * @param adPosId
     * @return
     */
    @RequestMapping(value = "/getAdPosSizeList", method = RequestMethod.GET)
    public ResultDto<Object> getAdPosSizeList(Long adPosId){
        List<AdPosSizeDto> retList = adPosService.getAdPosSizeList(adPosId);
        return ResultDtoFactory.toAck("查询成功", retList);
    }

}
