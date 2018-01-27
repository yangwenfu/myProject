package com.xinyunlian.jinfu.prodcode.controller;

import com.xinyunlian.jinfu.common.dto.ResultDto;
import com.xinyunlian.jinfu.common.dto.ResultDtoFactory;
import com.xinyunlian.jinfu.qrcode.dto.ProdCodeSearchDto;
import com.xinyunlian.jinfu.qrcode.service.ProdCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by menglei on 2017年03月14日.
 */
public class ProdCodeController {

    @Autowired
    private ProdCodeService prodCodeService;

    /**
     * 分页查询商品码
     *
     * @param prodCodeSearchDto
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ResultDto<Object> getProdCodePage(ProdCodeSearchDto prodCodeSearchDto) {
        ProdCodeSearchDto page = prodCodeService.getProdCodePage(prodCodeSearchDto);
        return ResultDtoFactory.toAck("获取成功", page);
    }

}
