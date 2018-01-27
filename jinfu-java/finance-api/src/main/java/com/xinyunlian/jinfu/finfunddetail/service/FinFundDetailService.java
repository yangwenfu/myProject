package com.xinyunlian.jinfu.finfunddetail.service;

import com.xinyunlian.jinfu.finfunddetail.dto.FinFundDetailDto;

import java.util.List;

/**
 * Created by dongfangchao on 2016/11/21.
 */
public interface FinFundDetailService {

    /**
     * 查询理财产品列表
     * @param searchDto
     * @return
     */
    List<FinFundDetailDto> getFinFundDetailList(FinFundDetailDto searchDto);

    /**
     * 根据产品id获取产品详情
     * @param fundId
     * @return
     */
    FinFundDetailDto getFinFundDetailById(Long fundId);

    /**
     * 新增基金详情
     * @param dto
     */
    void updateFinFundDetail(FinFundDetailDto dto);

}
