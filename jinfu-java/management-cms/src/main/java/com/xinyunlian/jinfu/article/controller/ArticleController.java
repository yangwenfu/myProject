package com.xinyunlian.jinfu.article.controller;

import com.xinyunlian.jinfu.cms.dto.ArticleSearchDto;
import com.xinyunlian.jinfu.cms.dto.CmsArticleDto;
import com.xinyunlian.jinfu.cms.service.CmsArticleService;
import com.xinyunlian.jinfu.common.dto.ResultDto;
import com.xinyunlian.jinfu.common.dto.ResultDtoFactory;
import com.xinyunlian.jinfu.common.util.MessageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by King on 2017/1/22.
 */
@RestController
@RequestMapping("article")
public class ArticleController {
    @Autowired
    private CmsArticleService cmsArticleService;
    /**
     * 保存
     *
     * @param cmsArticleDto
     * @return
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    public ResultDto<Object> save(@RequestBody CmsArticleDto cmsArticleDto) {
        cmsArticleService.save(cmsArticleDto);
        return ResultDtoFactory.toAck(MessageUtil.getMessage("common.operate.success"));
    }

    /**
     * 更新
     * @param articleDtos
     * @return
     */
    @RequestMapping(value = "/updateList", method = RequestMethod.POST)
    public ResultDto<String> updateList(@RequestBody List<CmsArticleDto> articleDtos){
        if(CollectionUtils.isEmpty(articleDtos)){
            ResultDtoFactory.toNack("更新数据不可为空");
        }
        cmsArticleService.update(articleDtos);
        return ResultDtoFactory.toAck("更新成功");
    }


    /**
     * 分页获取文章记录
     * @param searchDto
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public ResultDto<Object> getUserPage(ArticleSearchDto searchDto){
        ArticleSearchDto dto = cmsArticleService.getPage(searchDto);
        return ResultDtoFactory.toAck(MessageUtil.getMessage("common.operate.success"), dto);
    }

    /**
     * 获取文章详情
     * @param articleId
     * @return
     */
    @RequestMapping(value = "/getByID", method = RequestMethod.GET)
    @ResponseBody
    public ResultDto<Object> getById(@RequestParam Long articleId) {
        CmsArticleDto cmsArticleDto = cmsArticleService.get(articleId);
        return ResultDtoFactory.toAck(MessageUtil.getMessage("common.operate.success"),cmsArticleDto);
    }

    /**
     * 删除
     * @param articleIds
     * @return
     */
    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public ResultDto<String> delete(@RequestParam String articleIds){
        String[] articleIdsArr = articleIds.split(",");

        for(String callTypeId : articleIdsArr){
            cmsArticleService.delete(Long.valueOf(callTypeId));
        }

        return ResultDtoFactory.toAck("删除成功");
    }
}
