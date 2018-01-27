package com.xinyunlian.jinfu.api.validate.dto;

import com.xinyunlian.jinfu.api.dto.OrderProdOpenApiDto;
import com.xinyunlian.jinfu.api.dto.ProdCodeOpenApiDto;
import com.xinyunlian.jinfu.api.dto.ThirdOrderOpenApiDto;
import com.xinyunlian.jinfu.api.validate.util.SignUtil;
import com.xinyunlian.jinfu.common.util.DateHelper;
import com.xinyunlian.jinfu.common.util.HttpUtil;
import com.xinyunlian.jinfu.common.util.JsonUtil;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * UserController Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>���� 30, 2016</pre>
 */
public class OpenApiControllerTest {
    @Autowired
    private String URL = "http://localhost:8084/jinfu-uplus/web/open-api";

    @Before
    public void before() throws Exception {
    }

    @After
    public void after() throws Exception {
    }

    @Test
    public void testSaveThirdOrder() throws Exception {
        String appId = "xsm";

        ThirdOrderOpenApiDto thirdOrderOpenApiDto = new ThirdOrderOpenApiDto();
        thirdOrderOpenApiDto.setOrderNo("20170324163908");
        thirdOrderOpenApiDto.setStoreId(99822L);
        thirdOrderOpenApiDto.setOrderTime(DateHelper.getDate("2017-03-29"));
        thirdOrderOpenApiDto.setPlatform("云联金服");
        thirdOrderOpenApiDto.setStorageMode("入库");
        thirdOrderOpenApiDto.setStorageTime(DateHelper.getDate("2017-03-29"));
        thirdOrderOpenApiDto.setSupplier("新云联");
        List<OrderProdOpenApiDto> orderProdList = new ArrayList<>();
        OrderProdOpenApiDto orderProdOpenApiDto = new OrderProdOpenApiDto();
        orderProdOpenApiDto.setSku("111222");
        orderProdOpenApiDto.setProdName("红酒");
        orderProdOpenApiDto.setBoxCount("1箱");
        orderProdOpenApiDto.setProdCount("12瓶");
        orderProdOpenApiDto.setBindCount(12L);
        orderProdList.add(orderProdOpenApiDto);
        thirdOrderOpenApiDto.setOrderProdList(orderProdList);
        thirdOrderOpenApiDto.setAppId(appId);
        thirdOrderOpenApiDto.setSign(SignUtil.createSign(thirdOrderOpenApiDto, "gEV9GhV413vDlDtc"));

        String result = HttpUtil.doPost(URL + "/thirdOrder/save", "application/json", JsonUtil.toJson(thirdOrderOpenApiDto));
        System.out.println(result);

    }

    @Test
    public void testUpdateProdCode() throws Exception {
        String appId = "xsm";

        ProdCodeOpenApiDto prodCodeOpenApiDto = new ProdCodeOpenApiDto();
        prodCodeOpenApiDto.setStoreId(99822L);
        prodCodeOpenApiDto.setOrderNo("12313143");
        prodCodeOpenApiDto.setQrCodeUrl("http://qrcode.ylfin.com/product/170323000002/c35");
        prodCodeOpenApiDto.setAppId(appId);
        prodCodeOpenApiDto.setSign(SignUtil.createSign(prodCodeOpenApiDto, "gEV9GhV413vDlDtc"));

        String result = HttpUtil.doPost(URL + "/prodCode/update", "application/json", JsonUtil.toJson(prodCodeOpenApiDto));
        System.out.println(result);

    }

    @Test
    public void testGetProdCode() throws Exception {
        String appId = "xsm";

        ProdCodeOpenApiDto prodCodeOpenApiDto = new ProdCodeOpenApiDto();
        prodCodeOpenApiDto.setStoreId(99822L);
        prodCodeOpenApiDto.setQrCodeUrl("http://qrcode.ylfin.com/product/170323000004/a07");
        prodCodeOpenApiDto.setAppId(appId);
        prodCodeOpenApiDto.setSign(SignUtil.createSign(prodCodeOpenApiDto, "gEV9GhV413vDlDtc"));

        String result = HttpUtil.doPost(URL + "/prodCode", "application/json", JsonUtil.toJson(prodCodeOpenApiDto));
        System.out.println(result);

    }

}
