package com.xinyunlian.jinfu.dealer.dealer.controller;

import com.xinyunlian.jinfu.common.converter.ConverterService;
import com.xinyunlian.jinfu.common.dto.ResultDto;
import com.xinyunlian.jinfu.common.dto.ResultDtoFactory;
import com.xinyunlian.jinfu.dealer.dealer.dto.resp.AuthProdDto;
import com.xinyunlian.jinfu.dealer.dto.DealerProdDto;
import com.xinyunlian.jinfu.dealer.dto.DealerProdSearchDto;
import com.xinyunlian.jinfu.dealer.service.DealerProdService;
import com.xinyunlian.jinfu.prod.dto.ProductDto;
import com.xinyunlian.jinfu.prod.dto.ProductSearchDto;
import com.xinyunlian.jinfu.prod.service.ProdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by menglei on 2016年09月05日.
 */
@Controller
@RequestMapping(value = "dealer/dealerProd")
public class DealerProdController {

    @Autowired
    private DealerProdService dealerProdService;
    @Autowired
    private ProdService prodService;

    /**
     * 查询已授权业务列表
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ResultDto<List<AuthProdDto>> getDealerProdList(DealerProdSearchDto dealerProdSearchDto) {
        List<DealerProdDto> dealerProdList = dealerProdService.getDealerProdList(dealerProdSearchDto);
        List<ProductDto> prodList = prodService.getProdList(new ProductSearchDto());
        List<AuthProdDto> authProdListTemp = new ArrayList<>();
        for (DealerProdDto dealerProdDto : dealerProdList) {
            AuthProdDto authProdDto = ConverterService.convert(dealerProdDto, AuthProdDto.class);
            prodList.stream().filter(productDto -> dealerProdDto.getProdId().equals(productDto.getProdId())).forEach(authProdDto::setProductDto);
            authProdListTemp.add(authProdDto);
        }
        Map<String, AuthProdDto> authProdMap = new HashMap<>();
        List<ProductDto> productList;
        for (AuthProdDto authProdDto : authProdListTemp) {
            productList = new ArrayList<>();
            if (authProdMap.get(authProdDto.getDistrictId()) != null) {
                productList = authProdMap.get(authProdDto.getDistrictId()).getProductList();
            }
            productList.add(authProdDto.getProductDto());
            authProdDto.setProductList(productList);
            authProdDto.setId(null);
            authProdDto.setProductDto(null);
            authProdMap.put(authProdDto.getDistrictId(), authProdDto);
        }
        List<AuthProdDto> authProdList = authProdMap.keySet().stream().map(authProdMap::get).collect(Collectors.toList());
        return ResultDtoFactory.toAck("获取成功", authProdList);
    }

}
