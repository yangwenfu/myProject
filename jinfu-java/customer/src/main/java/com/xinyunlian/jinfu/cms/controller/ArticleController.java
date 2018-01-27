package com.xinyunlian.jinfu.cms.controller;

import com.xinyunlian.jinfu.cms.dto.ArticleAccessDto;
import com.xinyunlian.jinfu.cms.dto.ArticleSearchDto;
import com.xinyunlian.jinfu.cms.dto.CmsArticleDto;
import com.xinyunlian.jinfu.cms.dto.CmsArticleTypeDto;
import com.xinyunlian.jinfu.cms.enums.EArcPlatform;
import com.xinyunlian.jinfu.cms.service.CmsArticleService;
import com.xinyunlian.jinfu.cms.service.CmsArticleTypeService;
import com.xinyunlian.jinfu.common.dto.ResultDto;
import com.xinyunlian.jinfu.common.dto.ResultDtoFactory;
import com.xinyunlian.jinfu.common.util.MessageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by King on 2017/1/22.
 */
@RestController
@RequestMapping("cms/article")
public class ArticleController {
    @Autowired
    private CmsArticleService cmsArticleService;
    @Autowired
    private CmsArticleTypeService cmsArticleTypeService;

    /**
     * 分页获取文章记录
     * @param searchDto
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ResponseBody
    public ResultDto<Object> getUserPage(@RequestBody ArticleSearchDto searchDto){
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
    public ResultDto<Object> getById(@RequestParam Long articleId,EArcPlatform arcPlatform) {
        CmsArticleDto cmsArticleDto = cmsArticleService.get(articleId);
        cmsArticleService.viewArticle(articleId);
        return ResultDtoFactory.toAck(MessageUtil.getMessage("common.operate.success"),cmsArticleDto);
    }

    /**
     * 查询目录数据
     * @param articleTypeDto
     * @return
     */
    @RequestMapping(value = "/dir/list", method = RequestMethod.POST)
    public ResultDto<Object> getDirList(@RequestBody CmsArticleTypeDto articleTypeDto){
        List<CmsArticleTypeDto> list = cmsArticleTypeService.getList(articleTypeDto);
        return ResultDtoFactory.toAck(MessageUtil.getMessage("common.operate.success"), list);
    }

    /**
     * 文章点赞
     * @param articleId
     * @return
     */
    @RequestMapping(value = "/upArticle", method = RequestMethod.GET)
    @ResponseBody
    public ResultDto<Object> upArticle(@RequestParam Long articleId) {
        ArticleAccessDto articleAccessDto = cmsArticleService.upArticle(articleId);
        return ResultDtoFactory.toAck(MessageUtil.getMessage("common.operate.success"),articleAccessDto);
    }

    /**
     * 获取文章阅读数
     * @param articleId
     * @return
     */
    @RequestMapping(value = "/getArticleAccess", method = RequestMethod.GET)
    @ResponseBody
    public ResultDto<Object> getArticleAccess(@RequestParam Long articleId) {
        ArticleAccessDto articleAccessDto = cmsArticleService.getArticleAccess(articleId);
        return ResultDtoFactory.toAck(MessageUtil.getMessage("common.operate.success"),articleAccessDto);
    }

}
