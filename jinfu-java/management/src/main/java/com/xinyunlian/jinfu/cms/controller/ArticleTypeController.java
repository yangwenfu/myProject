package com.xinyunlian.jinfu.cms.controller;

import com.xinyunlian.jinfu.cms.dto.CmsArticleTypeDto;
import com.xinyunlian.jinfu.cms.service.CmsArticleService;
import com.xinyunlian.jinfu.cms.service.CmsArticleTypeService;
import com.xinyunlian.jinfu.common.dto.ResultDto;
import com.xinyunlian.jinfu.common.dto.ResultDtoFactory;
import com.xinyunlian.jinfu.crm.dto.CrmCallTypeDto;
import com.xinyunlian.jinfu.crm.service.CrmCallTypeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jll on 2016-08-24.
 */
@RestController
@RequestMapping("cms/articleType")
public class ArticleTypeController {
    private static final Logger LOGGER = LoggerFactory.getLogger(ArticleTypeController.class);

    @Autowired
    private CmsArticleService cmsArticleService;
    @Autowired
    private CmsArticleTypeService cmsArticleTypeService;

    /**
     * 查询子目录数据
     * @param articleTypeDto
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public ResultDto<Object> getList(@RequestBody CmsArticleTypeDto articleTypeDto){
        List<CmsArticleTypeDto> list = cmsArticleTypeService.getList(articleTypeDto);
        return ResultDtoFactory.toAck("获取成功", list);
    }

    /**
     * 新增
     * @param cmsArticleTypeDto
     * @return
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ResultDto<String> save(@RequestBody CmsArticleTypeDto cmsArticleTypeDto){
        cmsArticleTypeService.save(cmsArticleTypeDto);
        return ResultDtoFactory.toAck("新增成功");
    }

    /**
     * 更新
     * @param articleTypeDtos
     * @return
     */
    @RequestMapping(value = "/updateList", method = RequestMethod.POST)
    public ResultDto<String> updateList(@RequestBody List<CmsArticleTypeDto> articleTypeDtos){
        if(CollectionUtils.isEmpty(articleTypeDtos)){
            ResultDtoFactory.toNack("更新数据不可为空");
        }
        cmsArticleTypeService.update(articleTypeDtos);
        return ResultDtoFactory.toAck("更新成功");
    }

    /**
     * 删除
     * @param articleTypeId
     * @return
     */
    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public ResultDto<String> delete(@RequestParam Long articleTypeId){

        CmsArticleTypeDto articleTypeDto = new CmsArticleTypeDto();
        articleTypeDto.setParent(articleTypeId.intValue());
        List<CmsArticleTypeDto> cmsArticleTypeDtos = cmsArticleTypeService.getList(articleTypeDto);

        if(!CollectionUtils.isEmpty(cmsArticleTypeDtos)){
            return ResultDtoFactory.toNack("有子级项不可删除");
        }

        Long count = cmsArticleService.countByArticleTypeId(articleTypeId);
        if(count != null && count.longValue() > 0){
            return ResultDtoFactory.toNack("已关联文章不可删除");
        }
        cmsArticleTypeService.delete(articleTypeId);

        return ResultDtoFactory.toAck("删除成功");
    }

}
