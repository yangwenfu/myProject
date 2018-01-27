package com.xinyunlian.jinfu.yunma.service;

import com.xinyunlian.jinfu.common.converter.ConverterService;
import com.xinyunlian.jinfu.common.enums.EErrorCode;
import com.xinyunlian.jinfu.common.exception.BizServiceException;
import com.xinyunlian.jinfu.common.util.BizUtil;
import com.xinyunlian.jinfu.yunma.dao.YMProdAreaDao;
import com.xinyunlian.jinfu.yunma.dao.YMProductDao;
import com.xinyunlian.jinfu.yunma.dto.YMProdAreaDto;
import com.xinyunlian.jinfu.yunma.dto.YMProductDto;
import com.xinyunlian.jinfu.yunma.dto.YmAreaDetailDto;
import com.xinyunlian.jinfu.yunma.entity.YMProdAreaPo;
import com.xinyunlian.jinfu.yunma.entity.YMProductPo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import java.util.*;

/**
 * Created by menglei on 2017-01-05.
 */
@Service(value = "yMProdServiceImpl")
public class YMProdServiceImpl implements YMProdService {

    @Autowired
    private YMProductDao yMProductDao;

    @Autowired
    private YMProdAreaDao yMProdAreaDao;

    @Override
    public YMProductDto getProduct(String prodId){
        YMProductPo po = yMProductDao.findOne(prodId);
        YMProductDto dto = ConverterService.convert(po, YMProductDto.class);
        return dto;
    }

    @Override
    @Transactional
    public void updateProduct(YMProductDto dto){
        if(null != dto && !StringUtils.isEmpty(dto.getProdId())) {
            YMProductPo po = yMProductDao.findOne(dto.getProdId());
            po.setProdName(dto.getProdName());
        }
    }

    @Transactional
    @Override
    public void saveProd(YMProductDto productDto) throws BizServiceException{
        if (productDto != null) {
            YMProductPo po = yMProductDao.findOne(productDto.getProdId());
            if (po != null) {
                throw new BizServiceException(EErrorCode.SYSTEM_ID_IS_EXIST, "产品编号已存在");
            }
            YMProductPo ymProductPo = ConverterService.convert(productDto, YMProductPo.class);
            yMProductDao.save(ymProductPo);
        }
    }

    @Override
    public List<YMProductDto> getProdList(YMProductDto yMProductDto) throws BizServiceException {

        Specification<YMProductPo> spec = (root, query, cb) -> {
            Predicate predicate = cb.conjunction();
            List<Expression<Boolean>> expressions = predicate.getExpressions();
            if (null != yMProductDto) {
                if (!StringUtils.isEmpty(yMProductDto.getProdName())) {
                    expressions.add(cb.like(root.get("prodName"), BizUtil.filterString(yMProductDto.getProdName())));
                }
            }
            return predicate;
        };
        List<YMProductPo> list = yMProductDao.findAll(spec,new Sort(Sort.Direction.DESC, "createTs"));
        List<YMProductDto> result = new ArrayList<>();
        if (!CollectionUtils.isEmpty(list)) {
            list.forEach(po -> {
                YMProductDto dto = ConverterService.convert(po, YMProductDto.class);
                result.add(dto);
            });
        }
        return result;
    }

    @Override
    public Boolean checkProdArea(String prodId, Long areaId, String areaTreePath) throws BizServiceException {
        Boolean ret = false;
        if (!StringUtils.isEmpty(prodId) && areaId != null) {

            YMProdAreaPo po = yMProdAreaDao.findByProdIdAndAreaId(prodId, -1L);
            if(null != po){
                ret = true;
            }
            //先根据地区直接找
            po = yMProdAreaDao.findByProdIdAndAreaId(prodId, areaId);
            if (po != null) {
                ret = true;
            } else {
                if (StringUtils.isNotEmpty(areaTreePath)) {
                    String[] areaTreePathArray = areaTreePath.split(",");
                    if (areaTreePathArray != null && areaTreePathArray.length > 0) {
                        Set<String> pathSet = new HashSet<>();
                        StringBuilder pathBuilder = new StringBuilder(",");
                        for (int i = 1; i < areaTreePathArray.length; i++) {
                            pathBuilder.append(areaTreePathArray[i]).append(",");
                            pathSet.add(pathBuilder.toString());
                        }
                        String tmpPath = "," + areaTreePathArray[1] + ",%";
                        List<YMProdAreaPo> prodAreaPoList = yMProdAreaDao.findByProdIdAndAreaTreePathLike(prodId, tmpPath);
                        if (!CollectionUtils.isEmpty(prodAreaPoList)) {
                            Optional<YMProdAreaPo> optional =
                                    prodAreaPoList.stream().filter(item -> pathSet.contains(item.getAreaTreePath())).findFirst();
                            ret = optional.isPresent();
                        }
                    }
                }
            }
        }
        return ret;
    }

    @Override
    public List<YmAreaDetailDto> getAreaByProd(String prodId){

        List<YmAreaDetailDto> retList = new ArrayList<>();

        List<YMProdAreaPo> list = yMProdAreaDao.findByProdId(prodId);
        if (!CollectionUtils.isEmpty(list)){
            list.forEach(po -> {
                String[] pathArray = po.getAreaTreePath().split(",");
                if (pathArray != null && pathArray.length > 0){
                    YmAreaDetailDto tmp = new YmAreaDetailDto();
                    tmp.setProdAreaId(po.getId());
                    tmp.setProvinceId(Long.parseLong(pathArray[1]));
                    tmp.setCityId(Long.parseLong(pathArray[2]));
                    tmp.setCountyId(Long.parseLong(pathArray[3]));
                    tmp.setStreetId(Long.parseLong(pathArray[4]));
                    retList.add(tmp);
                }
            });
        }

        return retList;
    }

    @Transactional
    @Override
    public YMProdAreaDto saveProdArea(YMProdAreaDto prodAreaDto){
        YMProdAreaPo po = ConverterService.convert(prodAreaDto, YMProdAreaPo.class);
        yMProdAreaDao.save(po);
        prodAreaDto.setId(po.getId());
        return prodAreaDto;
    }

    @Transactional
    @Override
    public List<YMProdAreaDto> saveProdAreaList(List<YMProdAreaDto> prodAreaDtos){
        if(!CollectionUtils.isEmpty(prodAreaDtos)) {
            prodAreaDtos.forEach(ymProdAreaDto -> {
                YMProdAreaPo po = ConverterService.convert(ymProdAreaDto, YMProdAreaPo.class);
                yMProdAreaDao.save(po);
                ymProdAreaDto.setId(po.getId());
            });
        }
        return prodAreaDtos;
    }

    @Override
    @Transactional
    public void deleteProdArea(Long id){
        yMProdAreaDao.delete(id);
    }

}
