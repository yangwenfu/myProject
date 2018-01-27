package com.xinyunlian.jinfu.qrcode.service;

import com.xinyunlian.jinfu.common.exception.BizServiceException;
import com.xinyunlian.jinfu.qrcode.dto.*;

import java.util.List;

/**
 * Created by menglei on 2017年03月08日.
 */
public interface ProdCodeService {

    /**
     * 翻页查询商品码
     *
     * @param prodCodeSearchDto
     * @return
     */
    ProdCodeSearchDto getProdCodePage(ProdCodeSearchDto prodCodeSearchDto);

    /**
     * 视图查询商品码
     *
     * @param searchDto
     * @return
     */
    ProdCodeViewSearchDto getProdCodeViewPage(ProdCodeViewSearchDto searchDto);

    /**
     * 导出商品码
     *
     * @param prodCodeSearchDto
     * @return
     */
    List<ProdCodeDto> getProdCodeExportList(ProdCodeSearchDto prodCodeSearchDto);

    /**
     * 批量生成商品码
     *
     * @param count
     * @return
     */
    Boolean addBathProdCode(Integer count) throws BizServiceException;

    /**
     * 获取商品码详情
     *
     * @param prodCodeId
     * @return
     */
    ProdCodeDto getOne(Long prodCodeId);

    /**
     * 通过商品码url查商品码
     *
     * @param qrCodeUrl
     * @return
     */
    ProdCodeDto getByQrCodeUrl(String qrCodeUrl);

    /**
     * 根据外部订单号查商品码
     *
     * @param orderId
     * @return
     */
    List<ProdCodeDto> getListByOrderId(Long orderId);

    /**
     * 获取商品码详情
     *
     * @param prodCodeId
     * @return
     */
    ProdCodeViewDto getViewOne(Long prodCodeId);

    /**
     * 删除商品码
     *
     * @param prodCodeId
     * @throws BizServiceException
     */
    void deleteProdCode(Long prodCodeId) throws BizServiceException;

    /**
     * 冻结解冻商品码
     *
     * @param prodCodeId
     * @throws BizServiceException
     */
    void frozenProdCode(Long prodCodeId) throws BizServiceException;

    /**
     * 绑定商品码
     *
     * @param prodCodeDto
     * @throws BizServiceException
     */
    void bindProdCode(ProdCodeDto prodCodeDto) throws BizServiceException;

    /**
     * 商品码状态更新为已售
     *
     * @param prodCodeId
     * @throws BizServiceException
     */
    void updateStatusSold(Long prodCodeId, String orderNo) throws BizServiceException;

    /**
     * 根据订单列表查订单已绑码数
     *
     * @param orderIds
     * @return
     */
    List<ProdCodeCountDto> getCountByOrderId(List<Long> orderIds);

}
