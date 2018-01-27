package com.xinyunlian.jinfu.shopkeeper.service;

import com.xinyunlian.jinfu.area.dto.SysAreaInfDto;
import com.xinyunlian.jinfu.cms.dto.ArticleSearchDto;
import com.xinyunlian.jinfu.cms.dto.CmsArticleTypeDto;
import com.xinyunlian.jinfu.cms.enums.EArcPlatform;
import com.xinyunlian.jinfu.cms.service.CmsArticleService;
import com.xinyunlian.jinfu.cms.service.CmsArticleTypeService;
import com.xinyunlian.jinfu.common.converter.ConverterService;
import com.xinyunlian.jinfu.common.security.SecurityContext;
import com.xinyunlian.jinfu.common.util.AppConfigUtil;
import com.xinyunlian.jinfu.common.util.NumberUtil;
import com.xinyunlian.jinfu.prod.dto.ProdIndustryDto;
import com.xinyunlian.jinfu.prod.dto.ProdTypeInfDto;
import com.xinyunlian.jinfu.prod.dto.ProductDto;
import com.xinyunlian.jinfu.prod.enums.EShelfPlatform;
import com.xinyunlian.jinfu.prod.service.ProdService;
import com.xinyunlian.jinfu.prod.service.ProdTypeInfService;
import com.xinyunlian.jinfu.product.dto.LoanProductDetailDto;
import com.xinyunlian.jinfu.product.service.LoanProductService;
import com.xinyunlian.jinfu.shopkeeper.dto.home.ArticleDto;
import com.xinyunlian.jinfu.shopkeeper.dto.home.FloorDto;
import com.xinyunlian.jinfu.shopkeeper.dto.home.HomePageDto;
import com.xinyunlian.jinfu.shopkeeper.dto.home.TemplateDto;
import com.xinyunlian.jinfu.store.dto.StoreInfDto;
import com.xinyunlian.jinfu.store.service.StoreService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by King on 2017/2/16.
 */
@Service
public class PrivateHomeService {
    private static final Logger LOGGER = LoggerFactory.getLogger(PrivateHomeService.class);
    @Autowired
    private ProdTypeInfService prodTypeInfService;
    @Autowired
    private ProdService prodService;
    @Autowired
    private LoanProductService loanProductService;
    @Autowired
    private CmsArticleService articleService;
    @Autowired
    private CmsArticleTypeService articleTypeService;
    @Autowired
    private StoreService storeService;

    public void setProductFloor(HomePageDto homePageDto, EShelfPlatform platform, SysAreaInfDto localArea) {
        List<ProdTypeInfDto> prodTypeInfDtos = prodTypeInfService.getProdTypeList(null);
        if (!CollectionUtils.isEmpty(prodTypeInfDtos)) {
            String userId = SecurityContext.getCurrentUserId();
            List<StoreInfDto> storeInfDtos = null;
            if (!StringUtils.isEmpty(userId)) {
                storeInfDtos = storeService.findByUserId(userId);
            }

            for (ProdTypeInfDto prodTypeInfDto : prodTypeInfDtos) {
                List<ProductDto> productDtos = new ArrayList<>();
                List<ProductDto> productsTemp = prodService
                        .getProdListByTypeAndPlatform(prodTypeInfDto.getProdTypePath(), platform);
                if (!CollectionUtils.isEmpty(productsTemp)) {
                    for (ProductDto productDto : productsTemp) {
                        if (!this.hasInspermit(productDto, storeInfDtos)) {
                            continue;
                        }
                        if (!this.hasAreaPermit(productDto, localArea)) {
                            continue;
                        }
                        productDtos.add(productDto);
                    }
                }

                //一个产品大类属于一个楼层
                FloorDto floorDto = new FloorDto();
                floorDto.setCode(prodTypeInfDto.getProdTypeCode());
                floorDto.setTitle(!StringUtils.isEmpty(prodTypeInfDto.getProdTypeAlias())
                        ? prodTypeInfDto.getProdTypeAlias() : prodTypeInfDto.getProdTypeName());
                if (productDtos.size() >= 2) {
                    floorDto.setHasMore(true);
                }

                //往楼层里塞产品信息
                if (!CollectionUtils.isEmpty(productDtos)) {
                    for (int i = 0; i < productDtos.size(); i++) {
                        if (i >= 2) {
                            break;
                        }
                        productInToFloor(prodTypeInfDto, productDtos.get(i), floorDto);
                    }
                }

                //如果楼层不为空则添加到首页
                if (floorDto.getTemplateDtos().size() > 0) {
                    homePageDto.getFloorDto().add(floorDto);
                }
            }
        }
    }

    public void productInToFloor(ProdTypeInfDto prodTypeInfDto, ProductDto productDto, FloorDto floorDto) {
        //融资产品
        if ("30".equals(prodTypeInfDto.getProdTypeCode())) {
            TemplateDto templateDto = this.loanProductToTemplate(productDto);
            floorDto.getTemplateDtos().add(templateDto);
        } else {
            TemplateDto templateDto = this.productToTemplate(productDto);
            templateDto.setCode(prodTypeInfDto.getProdTypeCode());
            floorDto.getTemplateDtos().add(templateDto);
        }
    }

    public void productInToFloorForIndustry(ProdTypeInfDto prodTypeInfDto, ProductDto productDto,
                                            FloorDto floorDto, SysAreaInfDto localArea) {
        if (CollectionUtils.isEmpty(productDto.getProdIndustryList())) {
            return;
        }

        String userId = SecurityContext.getCurrentUserId();
        List<StoreInfDto> storeInfDtos = null;
        if (!StringUtils.isEmpty(userId)) {
            storeInfDtos = storeService.findByUserId(userId);
        }

        if (!this.hasInspermit(productDto, storeInfDtos)) {
            return;
        }

        if (!this.hasAreaPermit(productDto, localArea)) {
            return;
        }

        this.productInToFloor(prodTypeInfDto, productDto, floorDto);
    }


    public List<TemplateDto> listProduct(String prodTypeCode, EShelfPlatform platform,
                                         SysAreaInfDto localArea) {
        List<TemplateDto> templateDtos = new ArrayList<>();
        ProdTypeInfDto prodTypeInfDto = prodTypeInfService.getProdTypeByCode(prodTypeCode);
        if (null != prodTypeInfDto) {
            List<ProductDto> productDtos = prodService
                    .getProdListByTypeAndPlatform(prodTypeInfDto.getProdTypePath(), platform);

            if (!CollectionUtils.isEmpty(productDtos)) {
                String userId = SecurityContext.getCurrentUserId();
                List<StoreInfDto> storeInfDtos = null;
                if (!StringUtils.isEmpty(userId)) {
                    storeInfDtos = storeService.findByUserId(userId);
                }

                for (ProductDto productDto : productDtos) {
                    if (!this.hasInspermit(productDto, storeInfDtos)) {
                        continue;
                    }
                    if (!this.hasAreaPermit(productDto, localArea)) {
                        continue;
                    }

                    TemplateDto templateDto = new TemplateDto();
                    if ("30".equals(prodTypeInfDto.getProdTypeCode())) {
                        templateDto = this.loanProductToTemplate(productDto);
                    } else {
                        templateDto.setCode(prodTypeInfDto.getProdTypeCode());
                        templateDto = this.productToTemplate(productDto);
                    }
                    templateDtos.add(templateDto);
                }
            }
        }
        return templateDtos;
    }

    public TemplateDto loanProductToTemplate(ProductDto productDto) {
        //融资产品
        TemplateDto templateDto = new TemplateDto();
        templateDto.setCode("30");
        templateDto.setName(!StringUtils.isEmpty(productDto.getProdAlias())
                ? productDto.getProdAlias() : productDto.getProdName());
        LoanProductDetailDto productDetailDto = loanProductService.getProdDtl(productDto.getProdId());
        if (null != productDetailDto) {
            BigDecimal loanRt = productDetailDto.getRepayMode()
                    .getRate(productDetailDto.getIntrRateType(), productDetailDto.getIntrRate()).multiply(new BigDecimal(100));
            String loanRtStr = NumberUtil.roundTwo(loanRt).toString() + "%";
            //利率
            templateDto.setValue(loanRtStr);
            //利率类型
            templateDto.setDec(productDetailDto.getRepayMode().getRateType().getText());
            //还款方式
            templateDto.setMark(productDetailDto.getRepayMode().getAlias());
            templateDto.setProdId(productDto.getProdId());
            //还款方式code
            templateDto.setProdMode(productDetailDto.getRepayMode().getCode());
            templateDto.setDetailUrl(AppConfigUtil.getConfig("html.addr") + "/html/product.html?id=" + productDto.getProdId());
            if (null != productDto.getProdAppDetailDto()) {
                //产品标题
                if (!StringUtils.isEmpty(productDto.getProdAppDetailDto().getProdTitle())) {
                    templateDto.setValue(productDto.getProdAppDetailDto().getProdTitle());
                }
                //副标题左
                if (!StringUtils.isEmpty(productDto.getProdAppDetailDto().getProdSubTitleLeft())) {
                    templateDto.setDec(productDto.getProdAppDetailDto().getProdSubTitleLeft());
                }
                //促销角标右
                if (!StringUtils.isEmpty(productDto.getProdAppDetailDto().getPromoDesc())) {
                    templateDto.setMark(productDto.getProdAppDetailDto().getPromoDesc());
                }
                templateDto.setTagLeftUrl(productDto.getProdAppDetailDto().getAbsolutePromoTag());
                templateDto.setDecRight(productDto.getProdAppDetailDto().getProdSubTitleRight());
                templateDto.setUrl(productDto.getProdAppDetailDto().getProdUrl());
                templateDto.setPicPath(productDto.getProdAppDetailDto().getAbsoluteBgPicPath());
            }
        }
        return templateDto;
    }

    public TemplateDto productToTemplate(ProductDto productDto) {
        //其他产品
        TemplateDto templateDto = new TemplateDto();
        templateDto.setName(!StringUtils.isEmpty(productDto.getProdAlias())
                ? productDto.getProdAlias() : productDto.getProdName());

        if (null != productDto.getProdAppDetailDto()) {
            //产品标题
            templateDto.setValue(productDto.getProdAppDetailDto().getProdTitle());
            //副标题左
            templateDto.setDec(productDto.getProdAppDetailDto().getProdSubTitleLeft());
            //促销角标右
            templateDto.setMark(productDto.getProdAppDetailDto().getPromoDesc());
            templateDto.setTagLeftUrl(productDto.getProdAppDetailDto().getAbsolutePromoTag());
            templateDto.setDecRight(productDto.getProdAppDetailDto().getProdSubTitleRight());
            templateDto.setProdId(productDto.getProdId());
            templateDto.setUrl(productDto.getProdAppDetailDto().getProdUrl());
            templateDto.setPicPath(productDto.getProdAppDetailDto().getAbsoluteBgPicPath());
            templateDto.setDetailUrl(AppConfigUtil.getConfig("html.addr") + "/html/product.html?id=" + productDto.getProdId());
        }
        return templateDto;
    }

    public void setArticle(HomePageDto homePageDto) {
        ArticleSearchDto searchDto = new ArticleSearchDto();
        //获取文章信息 感兴趣文章的大类id需要事先约定好
        searchDto.setArticleTypeTree(",6,");
        searchDto.setArcPlatform(EArcPlatform.YLZG);
        searchDto.setPageSize(5);
        searchDto = articleService.getPage(searchDto);

        if (!CollectionUtils.isEmpty(searchDto.getList())) {
            searchDto.getList().forEach(cmsArticleDto -> {
                ArticleDto articleDto = ConverterService.convert(cmsArticleDto, ArticleDto.class);
                articleDto.setCoverPic(cmsArticleDto.getCoverPicFull());
                CmsArticleTypeDto articleTypeDto = articleTypeService.get(cmsArticleDto.getArticleTypeId());
                if (null != articleTypeDto) {
                    articleDto.setArticleTypeName(articleTypeDto.getName());
                    articleDto.setUrl(AppConfigUtil.getConfig("html.addr")
                            + "/html/article.html?id=" + cmsArticleDto.getArticleId()
                            + "&typeName=" + articleTypeDto.getName());
                }

                homePageDto.getArticleDtos().add(articleDto);
            });
        }
    }

    /**
     * 根据店铺行业判断是否显示产品
     *
     * @param storeInfDtos
     * @return
     */
    private boolean hasInspermit(ProductDto productDto, List<StoreInfDto> storeInfDtos) {
        //产品没有配置行业，则不显示
        if (CollectionUtils.isEmpty(productDto.getProdIndustryList())) {
            return false;
        }

        //店铺有但不在产品行业内则不显示
        if (!CollectionUtils.isEmpty(storeInfDtos)) {
            boolean flag = false;
            for (ProdIndustryDto prodIndustryDto : productDto.getProdIndustryList()) {
                if (prodIndustryDto.getIndMcc().equals(storeInfDtos.get(0).getIndustryMcc())) {
                    flag = true;
                    break;
                }
            }
            if (!flag) {
                return false;
            }
        }
        return true;
    }

    private boolean hasAreaPermit(ProductDto productDto, SysAreaInfDto localArea) {
        return prodService.checkProdArea(productDto.getProdId(), localArea.getId());
    }

}
