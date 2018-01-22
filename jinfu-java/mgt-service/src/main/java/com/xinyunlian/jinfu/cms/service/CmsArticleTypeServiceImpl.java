package com.xinyunlian.jinfu.cms.service;

import com.xinyunlian.jinfu.cms.dao.CmsArticleTypeDao;
import com.xinyunlian.jinfu.cms.dto.CmsArticleTypeDto;
import com.xinyunlian.jinfu.cms.entity.CmsArticleTypePo;
import com.xinyunlian.jinfu.common.converter.ConverterService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

/**
 * 文章类别ServiceImpl
 * @author jll
 * @version 
 */

@Service
public class CmsArticleTypeServiceImpl implements CmsArticleTypeService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CmsArticleTypeServiceImpl.class);

	@Autowired
	private CmsArticleTypeDao cmsArticleTypeDao;
    @Value("${file.addr}")
    private String fileAddr;


    @Override
    public CmsArticleTypeDto get(Long id) {
        CmsArticleTypePo po = cmsArticleTypeDao.findOne(id);
        CmsArticleTypeDto dto = ConverterService.convert(po, CmsArticleTypeDto.class);
        if (null != dto && !StringUtils.isEmpty(dto.getPic())){
            dto.setPic(fileAddr + dto.getPic());
        }
        return dto;
    }

    @Transactional
    @Override
    public void save(CmsArticleTypeDto cmsArticleTypeDto) {
        if (null != cmsArticleTypeDto){
            CmsArticleTypePo po = new CmsArticleTypePo();
            if(null != cmsArticleTypeDto.getArticleTypeId()){
                po = cmsArticleTypeDao.findOne(cmsArticleTypeDto.getArticleTypeId());
            }
            if(StringUtils.isEmpty(cmsArticleTypeDto.getPic())){
                cmsArticleTypeDto.setPic(po.getPic());
            }
            ConverterService.convert(cmsArticleTypeDto, po);
            if (po.getParent() != null) {
                CmsArticleTypePo parent = cmsArticleTypeDao.findOne(Long.valueOf(po.getParent()));
                String treePath = parent.getPath() + po.getParent() + ",";
                po.setPath(treePath);
            }else {// create province
                po.setPath(",");
            }
            cmsArticleTypeDao.save(po);
        }
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if (null != id){
            if(!id.equals(1) && !id.equals(2) && !id.equals(3)) {
                cmsArticleTypeDao.delete(id);
            }
        }
    }

    @Transactional
    @Override
    public void update(List<CmsArticleTypeDto> articleTypeDtos) {
        if (!CollectionUtils.isEmpty(articleTypeDtos)){
            articleTypeDtos.forEach(dto -> {
                CmsArticleTypePo po = cmsArticleTypeDao.findOne(dto.getArticleTypeId());
                if(!StringUtils.isEmpty(dto.getName())) {
                    po.setName(dto.getName());
                }
                if(null != dto.getOrders()) {
                    po.setOrders(dto.getOrders());
                }
                if(null != dto.getDisplay()) {
                    po.setDisplay(dto.getDisplay());
                }
            });
        }
    }

    @Override
    public List<CmsArticleTypeDto> getList(CmsArticleTypeDto articleTypeDto) {
        Specification<CmsArticleTypePo> spec = (root, query, cb) -> {
            Predicate predicate = cb.conjunction();
            List<Expression<Boolean>> expressions = predicate.getExpressions();
            if (null != articleTypeDto) {
                if (null != articleTypeDto.getParent()) {
                    expressions.add(cb.equal(root.get("parent"), articleTypeDto.getParent()));
                }else {
                    expressions.add(cb.isNull(root.get("parent")));
                }

                if(null != articleTypeDto.getDisplay()){
                    expressions.add(cb.equal(root.get("display"),articleTypeDto.getDisplay()));
                }
            }

            return predicate;
        };

        List<CmsArticleTypePo> list = cmsArticleTypeDao.findAll(spec,new Sort(Sort.Direction.ASC, "orders"));
        List<CmsArticleTypeDto> result = new ArrayList<>();
        if (!CollectionUtils.isEmpty(list)){
            list.forEach(po -> {
                CmsArticleTypeDto dto = ConverterService.convert(po, CmsArticleTypeDto.class);
                if (!StringUtils.isEmpty(dto.getPic())){
                    dto.setPic(fileAddr + dto.getPic());
                }
                result.add(dto);
            });
        }

        return result;
    }

}
