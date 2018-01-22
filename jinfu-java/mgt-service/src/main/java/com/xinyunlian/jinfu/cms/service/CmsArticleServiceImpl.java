package com.xinyunlian.jinfu.cms.service;

import com.xinyunlian.jinfu.cms.dao.CmsArticleDao;
import com.xinyunlian.jinfu.cms.dao.CmsArticleTypeDao;
import com.xinyunlian.jinfu.cms.dto.ArticleAccessDto;
import com.xinyunlian.jinfu.cms.dto.ArticleSearchDto;
import com.xinyunlian.jinfu.cms.dto.CmsArticleDto;
import com.xinyunlian.jinfu.cms.entity.CmsArticlePo;
import com.xinyunlian.jinfu.cms.entity.CmsArticleTypePo;
import com.xinyunlian.jinfu.cms.enums.EArcPlatform;
import com.xinyunlian.jinfu.common.converter.ConverterService;
import com.xinyunlian.jinfu.common.util.BizUtil;
import com.xinyunlian.jinfu.common.util.EnumHelper;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 文章表ServiceImpl
 *
 * @author jll
 */

@Service
public class CmsArticleServiceImpl implements CmsArticleService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CmsArticleServiceImpl.class);

    @Autowired
    private CmsArticleDao cmsArticleDao;
    @Autowired
    private CmsArticleTypeDao cmsArticleTypeDao;
    @Value("${file.addr}")
    private String fileAddr;

    @Transactional
    @Override
    public CmsArticleDto save(CmsArticleDto cmsArticleDto) {
        if (null != cmsArticleDto) {
            CmsArticlePo po = new CmsArticlePo();
            po.setViewsCount(0);
            po.setUpsCount(0);
            if(null != cmsArticleDto.getArticleId()){
                po = cmsArticleDao.findOne(cmsArticleDto.getArticleId());
            }
            ConverterService.convert(cmsArticleDto, po);
            if(!CollectionUtils.isEmpty(cmsArticleDto.getArcPlatform())) {
                StringBuilder platforms = new StringBuilder();
                for (EArcPlatform platform : cmsArticleDto.getArcPlatform()) {
                    platforms.append(platform.getCode());
                    platforms.append(",");
                }
                po.setArcPlatform(platforms.toString());
            }
            cmsArticleDao.save(po);
            cmsArticleDto.setArticleId(po.getArticleId());
        }
        return cmsArticleDto;
    }

    @Transactional
    @Override
    public void update(List<CmsArticleDto> cmsArticleDtos) {
        if (!CollectionUtils.isEmpty(cmsArticleDtos)){
            cmsArticleDtos.forEach(dto -> {
                CmsArticlePo po = cmsArticleDao.findOne(dto.getArticleId());
                if(null != dto.getOrders()) {
                    po.setOrders(dto.getOrders());
                }
            });
        }
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if (null != id) {
            cmsArticleDao.delete(id);
        }
    }

    @Override
    public CmsArticleDto get(Long id) {
        CmsArticlePo po = cmsArticleDao.findOne(id);
        CmsArticleDto dto = ConverterService.convert(po, CmsArticleDto.class);
        if (!StringUtils.isEmpty(dto.getBigPic())){
            dto.setBigPicFull(fileAddr + dto.getBigPic());
        }
        if (!StringUtils.isEmpty(dto.getCoverPic())){
            dto.setCoverPicFull(fileAddr + dto.getCoverPic());
        }
        if(null != po.getArcPlatform()) {
            String platforms = po.getArcPlatform();
            List<EArcPlatform> platformList = new ArrayList<>();
            String[] platformCodes = platforms.split(",");
            for (String platformCode : platformCodes) {
                EArcPlatform platform = EnumHelper.translate(EArcPlatform.class, platformCode);
                if (platform != null) {
                    platformList.add(platform);
                }
            }
            dto.setArcPlatform(platformList);
        }

        CmsArticleTypePo cmsArticleTypePo = cmsArticleTypeDao.findOne(po.getArticleTypeId());
        if(null != cmsArticleTypePo){
            dto.setArticleTypeName(cmsArticleTypePo.getName());
        }
        return dto;
    }

    @Override
    @Transactional(readOnly = true)
    public ArticleSearchDto getPage(ArticleSearchDto searchDto) {
        Specification<CmsArticlePo> spec = (Root<CmsArticlePo> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            Predicate predicate = cb.conjunction();
            List<Expression<Boolean>> expressions = predicate.getExpressions();
            if (null != searchDto) {
                if (!StringUtils.isEmpty(searchDto.getTitle())) {
                    expressions.add(cb.like(root.get("title"), BizUtil.filterString(searchDto.getTitle())));
                }
                if (!StringUtils.isEmpty(searchDto.getArticleTypeTree())) {
                    expressions.add(cb.like(root.get("articleTypeTree"), BizUtil.filterString(searchDto.getArticleTypeTree())));
                }
                if (null != searchDto.getArticleTypeId()) {
                    expressions.add(cb.in(root.get("articleTypeId")).value(searchDto.getArticleTypeId()));
                }
                if (null != searchDto.getTop()) {
                    expressions.add(cb.equal(root.get("top"),searchDto.getTop()));
                }
                if (null != searchDto.getRecommend()) {
                    expressions.add(cb.equal(root.get("recommend"),searchDto.getRecommend()));
                }
                if(null != searchDto.getArcPlatform()){
                    expressions.add(cb.like(root.get("arcPlatform"), BizUtil.filterString(searchDto.getArcPlatform().getCode())));
                }
            }
            return predicate;
        };

        Sort sort = new Sort(Direction.DESC, "top")
                .and(new Sort(Sort.Direction.ASC, "orders"))
                .and(new Sort(Sort.Direction.DESC, "createTs"));
        Pageable pageable = new PageRequest(searchDto.getCurrentPage() - 1,
                searchDto.getPageSize(), sort);
        Page<CmsArticlePo> page = cmsArticleDao.findAll(spec, pageable);
        List<CmsArticleDto> data = new ArrayList<>();
        for (CmsArticlePo po : page.getContent()) {
            CmsArticleDto dto = ConverterService.convert(po, CmsArticleDto.class);
            dto.setAccessUpsCount(po.getUpsCount());
            dto.setAccessViewsCount(po.getViewsCount());
            if (!StringUtils.isEmpty(dto.getBigPic())){
                dto.setBigPicFull(fileAddr + dto.getBigPic());
            }
            if (!StringUtils.isEmpty(dto.getCoverPic())){
                dto.setCoverPicFull(fileAddr + dto.getCoverPic());
            }
            CmsArticleTypePo cmsArticleTypePo = cmsArticleTypeDao.findOne(po.getArticleTypeId());
            if(null != cmsArticleTypePo){
                dto.setArticleTypeName(cmsArticleTypePo.getName());
            }
            data.add(dto);
        }
        searchDto.setList(data);
        searchDto.setTotalPages(page.getTotalPages());
        searchDto.setTotalRecord(page.getTotalElements());
        return searchDto;
    }

    @Override
    public Long countByArticleTypeId(Long articleTypeId){
        return cmsArticleDao.countByArticleTypeId(articleTypeId);
    }

    @Override
    @Transactional(readOnly = true)
    public ArticleAccessDto getArticleAccess(Long id){
        CmsArticlePo po = cmsArticleDao.findOne(id);
        ArticleAccessDto dto = ConverterService.convert(po, ArticleAccessDto.class);
        return dto;
    }

    @Override
    @Transactional()
    public ArticleAccessDto upArticle(Long id){
        CmsArticlePo po = cmsArticleDao.findOne(id);
        po.setUpsCount(po.getUpsCount() + 1);
        ArticleAccessDto dto = ConverterService.convert(po, ArticleAccessDto.class);
        cmsArticleDao.save(po);
        return dto;
    }

    @Override
    @Transactional()
    public void viewArticle(Long id){
        CmsArticlePo po = cmsArticleDao.findOne(id);
        po.setViewsCount(po.getViewsCount() + 1);
        cmsArticleDao.save(po);
    }

}
