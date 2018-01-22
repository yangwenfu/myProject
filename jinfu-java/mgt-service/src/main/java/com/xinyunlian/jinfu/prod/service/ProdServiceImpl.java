package com.xinyunlian.jinfu.prod.service;

import com.xinyunlian.jinfu.area.dao.SysAreaInfDao;
import com.xinyunlian.jinfu.area.entity.SysAreaInfPo;
import com.xinyunlian.jinfu.common.converter.ConverterService;
import com.xinyunlian.jinfu.common.enums.EErrorCode;
import com.xinyunlian.jinfu.common.exception.BizServiceException;
import com.xinyunlian.jinfu.common.security.StaticResourceSecurity;
import com.xinyunlian.jinfu.common.util.BizUtil;
import com.xinyunlian.jinfu.prod.dao.*;
import com.xinyunlian.jinfu.prod.dto.*;
import com.xinyunlian.jinfu.prod.entity.*;
import com.xinyunlian.jinfu.prod.enums.EProdAppDetailCfg;
import com.xinyunlian.jinfu.prod.enums.EShelfPlatform;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import java.util.*;

/**
 * Created by DongFC on 2016-08-22.
 */
@Service
public class ProdServiceImpl implements ProdService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProdServiceImpl.class);

    @Autowired
    private ProductDao productDao;

    @Autowired
    private SysAreaInfDao sysAreaInfDao;

    @Autowired
    private ProdAreaDao prodAreaDao;

    @Autowired
    private ProdTypeInfDao prodTypeInfDao;

    @Value("${file.addr}")
    private String fileAddr;

    @Autowired
    private ProdShelfDao prodShelfDao;

    @Autowired
    private ProdUserGroupDao prodUserGroupDao;

    @Autowired
    private ProdAppDetailDao prodAppDetailDao;

    @Autowired
    private ProdIndustryDao prodIndustryDao;

    @Override
    public List<ProductDto> getProdList(ProductSearchDto productSearchDto) throws BizServiceException {

        Specification<ProductPo> spec = (root, query, cb) -> {
            Predicate predicate = cb.conjunction();
            List<Expression<Boolean>> expressions = predicate.getExpressions();
            if (null != productSearchDto) {
                if (!StringUtils.isEmpty(productSearchDto.getProdName())) {
                    expressions.add(cb.like(root.get("prodName"), BizUtil.filterString(productSearchDto.getProdName())));
                }

                if (StringUtils.isEmpty(productSearchDto.getProdTypePath())){
                    StringBuilder prodTypePathBuilder = new StringBuilder();
                    if (!StringUtils.isEmpty(productSearchDto.getProdTypeIdLv1())){
                        prodTypePathBuilder.append(",").append(productSearchDto.getProdTypeIdLv1()).append(",");

                        if (!StringUtils.isEmpty(productSearchDto.getProdTypeIdLv2())){
                            prodTypePathBuilder.append(productSearchDto.getProdTypeIdLv2()).append(",");

                            if (!StringUtils.isEmpty(productSearchDto.getProdTypeIdLv3())){
                                prodTypePathBuilder.append(productSearchDto.getProdTypeIdLv3()).append(",");
                            }
                        }
                    }

                    if (!StringUtils.isEmpty(prodTypePathBuilder.toString())) {
                        expressions.add(cb.like(root.get("prodTypePath"), BizUtil.filterStringRight(prodTypePathBuilder.toString())));
                    }
                }else {
                    expressions.add(cb.like(root.get("prodTypePath"), BizUtil.filterStringRight(productSearchDto.getProdTypePath())));
                }

            }
            return predicate;
        };

        List<ProductPo> list = productDao.findAll(spec);
        List<ProductDto> result = new ArrayList<>();
        if (!CollectionUtils.isEmpty(list)){
            list.forEach(po -> {
                ProductDto dto = ConverterService.convert(po, ProductDto.class);
                if (!StringUtils.isEmpty(dto.getPicPath())){
                    dto.setAbsolutePicPath(fileAddr + StaticResourceSecurity.getSecurityURI(dto.getPicPath()));
                }

                setProdTypeInf(dto);
                result.add(dto);
            });
        }

        return result;
    }

    @Override
    public List<AreaDetailLvlDto> getAreaByProd(String prodId) throws BizServiceException{

        List<AreaDetailLvlDto> retList = new ArrayList<>();

        List<ProdAreaPo> list = prodAreaDao.findByProdId(prodId);
        if (!CollectionUtils.isEmpty(list)){
            list.forEach(po -> {
                String[] pathArray = po.getAreaTreePath().split(",");
                if (pathArray != null && pathArray.length > 0){
                    AreaDetailLvlDto tmp = new AreaDetailLvlDto();
                    try {
                        tmp.setProdAreaId(po.getId());
                        for (int i = 0; i < pathArray.length; i++){
                            switch (i){
                                case 1:
                                    tmp.setProvinceId(Long.parseLong(pathArray[1]));
                                    break;
                                case 2:
                                    tmp.setCityId(Long.parseLong(pathArray[2]));
                                    break;
                                case 3:
                                    tmp.setCountyId(Long.parseLong(pathArray[3]));
                                    break;
                                case 4:
                                    tmp.setStreetId(Long.parseLong(pathArray[4]));
                                    break;
                            }
                        }
                    } catch (Exception e) {
                        LOGGER.error("地区获取异常", e);
                    }

                    retList.add(tmp);
                }
            });
        }

        return retList;
    }

    @Transactional
    @Override
    public void updateProdArea(ProdAreaDto prodAreaDto) throws BizServiceException{

        if (prodAreaDto != null){

            Long id = prodAreaDto.getId();
            if (id != null){
                ProdAreaPo po = prodAreaDao.findOne(id);
                po.setAreaId(prodAreaDto.getAreaId());
                SysAreaInfPo areaInfPo = sysAreaInfDao.findOne(prodAreaDto.getAreaId());
                String areaTreePath = areaInfPo.getTreePath() + areaInfPo.getId() + ",";
                po.setAreaTreePath(areaTreePath);
            }

        }

    }

    @Transactional
    @Override
    public void saveProduct(ProductDto productDto) throws BizServiceException {
        if (productDto != null){
            ProductPo po = productDao.findOne(productDto.getProdId());
            if (po != null){
                throw new BizServiceException(EErrorCode.MGT_PROD_CODE_EXISTS);
            }

            ProductPo productPo = ConverterService.convert(productDto, ProductPo.class);
            StringBuilder stringBuilder = new StringBuilder();
            if (productDto.getProdTypeInfDtoLv1()!=null){
                stringBuilder.append(",").append(productDto.getProdTypeInfDtoLv1().getProdTypeId()).append(",");
            }
            if (productDto.getProdTypeInfDtoLv2()!=null){
                stringBuilder.append(productDto.getProdTypeInfDtoLv2().getProdTypeId()).append(",");
            }
            if (productDto.getProdTypeInfDtoLv3()!=null){
                stringBuilder.append(productDto.getProdTypeInfDtoLv3().getProdTypeId()).append(",");
            }
            productPo.setProdTypePath(stringBuilder.toString());

            //保存APP信息
            ProdAppDetailDto prodAppDetailDto = productDto.getProdAppDetailDto();
            if (prodAppDetailDto != null){
                ProdAppDetailPo prodAppDetailPo = ConverterService.convert(prodAppDetailDto, ProdAppDetailPo.class);
                prodAppDetailPo.setProdId(productDto.getProdId());
                prodAppDetailDao.save(prodAppDetailPo);
            }

            //保存上架平台、用户组和行业
            updateShelfGroupIndustry(productDto);

            productDao.save(productPo);
        }
    }

    @Override
    public List<ProductDto> getProdListByIds(List<String> ids) throws BizServiceException {
        List<ProductDto> result = new ArrayList<>();
        if (!CollectionUtils.isEmpty(ids)){
            List<ProductPo> list = productDao.findByProdIdIn(ids);
            result = convertPoToDto(list);
        }
        return result;
    }

    @Override
    public ProductDto getProdById(String prodId) throws BizServiceException {
        ProductPo po = productDao.findOne(prodId);
        ProductDto dto = ConverterService.convert(po, ProductDto.class);
        if (!StringUtils.isEmpty(dto.getPicPath())){
            dto.setAbsolutePicPath(fileAddr + StaticResourceSecurity.getSecurityURI(dto.getPicPath()));
        }

        setProdTypeInf(dto);
        ProductDto tmpDto = getProductDetail(po.getProdId());
        dto.setProdAppDetailDto(tmpDto.getProdAppDetailDto());
        dto.setProdShelfList(tmpDto.getProdShelfList());
        dto.setProdUserGroupList(tmpDto.getProdUserGroupList());
        dto.setProdIndustryList(tmpDto.getProdIndustryList());

        return dto;
    }

    @Override
    public Boolean checkProdArea(String prodId, Long areaId) throws BizServiceException {
        Boolean ret = false;

        if (!StringUtils.isEmpty(prodId) && areaId != null){

            //先根据地区直接找
            ProdAreaPo po = prodAreaDao.findByProdIdAndAreaId(prodId, areaId);
            if (po!=null){
                ret = true;
            }else{
                SysAreaInfPo areaInfPo = sysAreaInfDao.findOne(areaId);
                if (areaInfPo != null){
                    String areaTreePath = areaInfPo.getTreePath();
                    String[] areaTreePathArray = areaTreePath.split(",");
                    if (areaTreePathArray != null && areaTreePathArray.length > 0){

                        Set<String> pathSet = new HashSet<>();
                        StringBuilder pathBuilder = new StringBuilder(",");
                        for(int i = 1; i< areaTreePathArray.length; i++){
                            pathBuilder.append(areaTreePathArray[i]).append(",");
                            pathSet.add(pathBuilder.toString());
                        }

                        String tmpPath = "," + areaTreePathArray[1] + ",%";
                        List<ProdAreaPo> prodAreaPoList = prodAreaDao.findByProdIdAndAreaTreePathLike(prodId, tmpPath);
                        if (!CollectionUtils.isEmpty(prodAreaPoList)){
                            Optional<ProdAreaPo> optional =
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
    @Transactional
    public void deleteProdArea(Long id) throws BizServiceException {
        prodAreaDao.delete(id);
    }

    @Transactional
    @Override
    public ProdAreaDto saveProdArea(ProdAreaDto prodAreaDto) throws BizServiceException {
        ProdAreaPo po = ConverterService.convert(prodAreaDto, ProdAreaPo.class);
        prodAreaDao.save(po);
        prodAreaDto.setId(po.getId());

        return prodAreaDto;
    }

    @Override
    @Transactional
    public void updateProduct(ProductDto dto) throws BizServiceException {
        ProductPo po = productDao.findOne(dto.getProdId());
        StringBuilder stringBuilder = new StringBuilder();
        if (dto.getProdTypeInfDtoLv1()!=null){
            stringBuilder.append(",").append(dto.getProdTypeInfDtoLv1().getProdTypeId()).append(",");
        }
        if (dto.getProdTypeInfDtoLv2()!=null){
            stringBuilder.append(dto.getProdTypeInfDtoLv2().getProdTypeId()).append(",");
        }
        if (dto.getProdTypeInfDtoLv3()!=null){
            stringBuilder.append(dto.getProdTypeInfDtoLv3().getProdTypeId()).append(",");
        }
        po.setProdTypePath(stringBuilder.toString());

        po.setProdName(dto.getProdName());
        if (!StringUtils.isEmpty(dto.getPicPath())){
            po.setPicPath(dto.getPicPath());
        }
        po.setProvider(dto.getProvider());
        po.setProdAlias(dto.getProdAlias());

        //更新APP信息
        ProdAppDetailDto prodAppDetailDto = dto.getProdAppDetailDto();
        ProdAppDetailPo prodAppDetailPo = prodAppDetailDao.findByProdId(dto.getProdId());
        if (prodAppDetailPo != null && prodAppDetailDto != null){
            prodAppDetailPo.setProdDetail(prodAppDetailDto.getProdDetail());
            prodAppDetailPo.setProdUrl(prodAppDetailDto.getProdUrl());
            prodAppDetailPo.setBgPicPath(prodAppDetailDto.getBgPicPath());
            prodAppDetailPo.setCfgRcmdFlag(prodAppDetailDto.getCfgRcmdFlag());
            prodAppDetailPo.setCfgRcmdOrder(prodAppDetailDto.getCfgRcmdOrder());
            prodAppDetailPo.setCfgNewFlag(prodAppDetailDto.getCfgNewFlag());
            prodAppDetailPo.setCfgNewOrder(prodAppDetailDto.getCfgNewOrder());
            prodAppDetailPo.setCfgHotFlag(prodAppDetailDto.getCfgHotFlag());
            prodAppDetailPo.setCfgHotOrder(prodAppDetailDto.getCfgHotOrder());
            prodAppDetailPo.setPromoTag(prodAppDetailDto.getPromoTag());
            prodAppDetailPo.setPromoDesc(prodAppDetailDto.getPromoDesc());
            prodAppDetailPo.setProdTitle(prodAppDetailDto.getProdTitle());
            prodAppDetailPo.setProdSubTitleLeft(prodAppDetailDto.getProdSubTitleLeft());
            prodAppDetailPo.setProdSubTitleRight(prodAppDetailDto.getProdSubTitleRight());
        }else if (prodAppDetailPo == null && prodAppDetailDto != null){
            ProdAppDetailPo prodAppDetailSavePo = ConverterService.convert(prodAppDetailDto, ProdAppDetailPo.class);
            prodAppDetailSavePo.setProdId(dto.getProdId());
            prodAppDetailDao.save(prodAppDetailSavePo);
        }else if (prodAppDetailPo != null && prodAppDetailDto == null){
            prodAppDetailDao.delete(prodAppDetailPo.getId());
        }

        //更新上架平台、用户组和行业
        updateShelfGroupIndustry(dto);
    }

    @Override
    public List<ProductDto> getProdListByIdsAndPlatform(List<String> ids, EShelfPlatform shelfPlatform) throws BizServiceException {
        List<ProductDto> result = new ArrayList<>();

        List<ProductPo> list;
        if (!CollectionUtils.isEmpty(ids) && shelfPlatform != null){
            list = productDao.findByProdIdAndPlatform(ids, shelfPlatform.getCode());
            result = convertPoToDto(list);
        }

        return result;
    }

    @Override
    public List<ProductDto> getProdListByPlatform(EShelfPlatform shelfPlatform) throws BizServiceException {
        List<ProductPo> list = productDao.findByPlatform(shelfPlatform.getCode());
        List<ProductDto> result = convertPoToDto(list);
        return result;
    }

    @Override
    public List<ProductDto> getProdListByTypeAndPlatform(String prodTypePath, EShelfPlatform shelfPlatform) throws BizServiceException {
        List<ProductPo> productPoList = productDao.findByTypeAndPlatform(BizUtil.filterString(prodTypePath), shelfPlatform.getCode());
        List<ProductDto> retList = convertPoToDto(productPoList);
        return retList;
    }

    @Override
    public List<ProductDto> getProdListByPlatformAndCfg(EShelfPlatform shelfPlatform, EProdAppDetailCfg prodAppDetailCfg,Integer limit) throws BizServiceException {
        List<ProductPo> productPoList = productDao.findByPlatformAndCfg(shelfPlatform.getCode(), prodAppDetailCfg.getCode(),limit);
        List<ProductDto> retList = convertPoToDto(productPoList);
        if(!CollectionUtils.isEmpty(retList)){
            retList.forEach(productDto -> {
                String[] pathIds = productDto.getProdTypePath().split(",");
                ProdTypeInfPo prodTypeInfPo = prodTypeInfDao.findOne(Long.valueOf(pathIds[1]));
                ProdTypeInfDto lv1Dto = ConverterService.convert(prodTypeInfPo, ProdTypeInfDto.class);
                productDto.setProdTypeInfDtoLv1(lv1Dto);
            });
        }
        return retList;
    }

    @Override
    public List<ProductDto> getProdListByIdPlatformInd(List<String> ids, EShelfPlatform shelfPlatform, String indMcc) throws BizServiceException {
        List<ProductDto> result = new ArrayList<>();

        List<ProductPo> list;
        if (!CollectionUtils.isEmpty(ids) && shelfPlatform != null && !StringUtils.isEmpty(indMcc)){
            list = productDao.findByProdIdPlatformInd(ids, shelfPlatform.getCode(), indMcc);
            result = convertPoToDto(list);
        }

        return result;
    }

    @Override
    public List<ProductDto> getProdListByTypePlatformInd(String prodTypePath, EShelfPlatform shelfPlatform, List<String> indMccList) throws BizServiceException {
        List<ProductPo> productPoList = productDao.findByTypePlatformInd(BizUtil.filterString(prodTypePath), shelfPlatform.getCode(), indMccList);
        List<ProductDto> retList = convertPoToDto(productPoList);
        return retList;
    }

    @Override
    public List<ProductDto> getProdListByPlatformCfgInd(EShelfPlatform shelfPlatform, EProdAppDetailCfg prodAppDetailCfg, String indMcc, Integer limit) throws BizServiceException {
        List<ProductPo> productPoList = productDao.findByPlatformCfgInd(shelfPlatform.getCode(), prodAppDetailCfg.getCode(), indMcc, limit);
        List<ProductDto> retList = convertPoToDto(productPoList);
        if(!CollectionUtils.isEmpty(retList)){
            retList.forEach(productDto -> {
                String[] pathIds = productDto.getProdTypePath().split(",");
                ProdTypeInfPo prodTypeInfPo = prodTypeInfDao.findOne(Long.valueOf(pathIds[1]));
                ProdTypeInfDto lv1Dto = ConverterService.convert(prodTypeInfPo, ProdTypeInfDto.class);
                productDto.setProdTypeInfDtoLv1(lv1Dto);
            });
        }
        return retList;
    }

    @Override
    public Boolean checkProdArea(String prodId, Long areaId, String indMcc) throws BizServiceException {
        Boolean ret = checkProdArea(prodId, areaId);
        ProdIndustryPo prodIndustryPo = prodIndustryDao.findByProdIdAndIndMcc(prodId, indMcc);
        if (prodIndustryPo != null && ret){
            return true;
        }else {
            return false;
        }
    }

    /**
     * 全删全增上架平台、用户组和行业
     * @param productDto
     */
    private void updateShelfGroupIndustry(ProductDto productDto){
        //保存上架平台
        prodShelfDao.deleteByProdId(productDto.getProdId());
        if (!CollectionUtils.isEmpty(productDto.getProdShelfList())){
            List<ProdShelfPo> tmpList = new ArrayList<>();
            productDto.getProdShelfList().forEach(prodShelfDto -> {
                ProdShelfPo prodShelfPo = ConverterService.convert(prodShelfDto, ProdShelfPo.class);
                prodShelfPo.setProdId(productDto.getProdId());
                tmpList.add(prodShelfPo);
            });
            prodShelfDao.save(tmpList);
        }

        //保存用户组
        prodUserGroupDao.deleteByProdId(productDto.getProdId());
        if (!CollectionUtils.isEmpty(productDto.getProdUserGroupList())){
            List<ProdUserGroupPo> tmpList = new ArrayList<>();
            productDto.getProdUserGroupList().forEach(prodUserGroupDto -> {
                ProdUserGroupPo prodUserGroupPo = ConverterService.convert(prodUserGroupDto, ProdUserGroupPo.class);
                prodUserGroupPo.setProdId(productDto.getProdId());
                tmpList.add(prodUserGroupPo);
            });
            prodUserGroupDao.save(tmpList);
        }

        //保存行业
        prodIndustryDao.deleteByProdId(productDto.getProdId());
        if (!CollectionUtils.isEmpty(productDto.getProdIndustryList())){
            List<ProdIndustryPo> tmpList = new ArrayList<>();
            productDto.getProdIndustryList().forEach(prodIndustryDto -> {
                ProdIndustryPo prodIndustryPo = ConverterService.convert(prodIndustryDto, ProdIndustryPo.class);
                prodIndustryPo.setProdId(productDto.getProdId());
                tmpList.add(prodIndustryPo);
            });
            prodIndustryDao.save(tmpList);
        }

    }

    private List<ProductDto> convertPoToDto(List<ProductPo> list){
        List<ProductDto> result = new ArrayList<>();
        if (!CollectionUtils.isEmpty(list)){
            list.forEach(po -> {
                ProductDto dto = ConverterService.convert(po, ProductDto.class);
                if (!StringUtils.isEmpty(dto.getPicPath())){
                    String picPath = fileAddr + StaticResourceSecurity.getSecurityURI(dto.getPicPath());
                    dto.setPicPath(picPath);
                    dto.setAbsolutePicPath(picPath);
                }

                setProdTypeInf(dto);
                ProductDto tmpDto = getProductDetail(po.getProdId());
                dto.setProdAppDetailDto(tmpDto.getProdAppDetailDto());
                dto.setProdShelfList(tmpDto.getProdShelfList());
                dto.setProdUserGroupList(tmpDto.getProdUserGroupList());
                dto.setProdIndustryList(tmpDto.getProdIndustryList());

                result.add(dto);
            });
        }
        return result;
    }

    private ProductDto getProductDetail(String prodId){
        ProductDto productDto = new ProductDto();

        //上架平台
        List<ProdShelfPo> prodShelfPoList = prodShelfDao.findByProdId(prodId);
        if (!CollectionUtils.isEmpty(prodShelfPoList)){
            List<ProdShelfDto> prodShelfDtoList = new ArrayList<>();
            prodShelfPoList.forEach( prodShelfPo -> {
                ProdShelfDto prodShelfDto = ConverterService.convert(prodShelfPo, ProdShelfDto.class);
                prodShelfDtoList.add(prodShelfDto);
            });
            productDto.setProdShelfList(prodShelfDtoList);
        }

        //用户组
        List<ProdUserGroupPo> prodUserGroupList = prodUserGroupDao.findByProdId(prodId);
        if (!CollectionUtils.isEmpty(prodUserGroupList)){
            List<ProdUserGroupDto> prodUserGroupDtoList = new ArrayList<>();
            prodUserGroupList.forEach(prodUserGroupPo -> {
                ProdUserGroupDto prodUserGroupDto = ConverterService.convert(prodUserGroupPo, ProdUserGroupDto.class);
                prodUserGroupDtoList.add(prodUserGroupDto);
            });
            productDto.setProdUserGroupList(prodUserGroupDtoList);
        }

        //APP信息
        ProdAppDetailPo prodAppDetailPo = prodAppDetailDao.findByProdId(prodId);
        ProdAppDetailDto prodAppDetailDto = ConverterService.convert(prodAppDetailPo, ProdAppDetailDto.class);
        if (prodAppDetailDto != null){
            if (!StringUtils.isEmpty(prodAppDetailDto.getBgPicPath())){
                prodAppDetailDto.setAbsoluteBgPicPath(fileAddr + StaticResourceSecurity.getSecurityURI(prodAppDetailDto.getBgPicPath()));
            }
            if (!StringUtils.isEmpty(prodAppDetailDto.getPromoTag())){
                prodAppDetailDto.setAbsolutePromoTag(fileAddr + StaticResourceSecurity.getSecurityURI(prodAppDetailDto.getPromoTag()));
            }
        }
        productDto.setProdAppDetailDto(prodAppDetailDto);

        //行业信息
        List<ProdIndustryPo> prodIndustryList = prodIndustryDao.findByProdId(prodId);
        if (!CollectionUtils.isEmpty(prodIndustryList)){
            List<ProdIndustryDto> prodIndustryDtoList = new ArrayList<>();
            prodIndustryList.forEach( item -> {
                ProdIndustryDto dto = ConverterService.convert(item, ProdIndustryDto.class);
                prodIndustryDtoList.add(dto);
            });
            productDto.setProdIndustryList(prodIndustryDtoList);
        }

        return productDto;
    }

    //设置产品类别
    private void setProdTypeInf(ProductDto dto){
        if (!StringUtils.isEmpty(dto.getProdTypePath())){
            String[] pathIds = dto.getProdTypePath().split(",");
            for (int i = 1; i < pathIds.length; i++){
                ProdTypeInfPo prodTypeInfPo = prodTypeInfDao.findOne(Long.valueOf(pathIds[i]));
                ProdTypeInfDto lvDto = ConverterService.convert(prodTypeInfPo, ProdTypeInfDto.class);
                switch (i){
                    case 1:{
                        dto.setProdTypeInfDtoLv1(lvDto);
                        break;
                    }
                    case 2:{
                        dto.setProdTypeInfDtoLv2(lvDto);
                        break;
                    }
                    case 3:{
                        dto.setProdTypeInfDtoLv3(lvDto);
                        break;
                    }
                }
            }
        }
    }

}
