package com.xinyunlian.jinfu.yunma.service;

import com.xinyunlian.jinfu.common.exception.BizServiceException;
import com.xinyunlian.jinfu.trade.enums.EBizCode;
import com.xinyunlian.jinfu.yunma.dto.MemberIntoDto;
import com.xinyunlian.jinfu.yunma.dto.YMMemberDto;
import com.xinyunlian.jinfu.yunma.dto.YMMemberSearchDto;
import com.xinyunlian.jinfu.yunma.dto.YmMemberBizDto;
import com.xinyunlian.jinfu.yunma.enums.EMemberAuditStatus;
import com.xinyunlian.jinfu.yunma.enums.EMemberStatus;

import java.util.List;

/**
 * 云码商铺Service
 * @author jll
 * @version
 */

public interface YMMemberService {
    /**
     * 通过id获取商铺信息
     * @param id
     * @return
     */
    YMMemberDto get(Long id);

    /**
     * 改变商户状态启用或停用
     * @param id
     * @param status
     */
    void alterStatus(Long id,EMemberStatus status);

    /**
     *更改银行卡
     * @param id
     * @param bankCardId
     */
    void alterBankCard(Long id,Long bankCardId);
    /**
     * 解绑云码
     * @param id
     */
    void unbind (Long id);

    void unbindByStoreId (Long storeId);

    /**
     * 商户列表分页查询(通过视图)
     * @param searchDto
     * @return
     */
    YMMemberSearchDto getMemberViewPage(YMMemberSearchDto searchDto);
    /**
     *商户列表分页查询
     * @param searchDto
     * @return
     */
    YMMemberSearchDto getMemberPage(YMMemberSearchDto searchDto);

    /**
     * 导出数据
     * @param searchDto
     * @return
     */
    List<YMMemberSearchDto> getMemberExportList(YMMemberSearchDto searchDto);

    /**
     * 添加店铺
     * @param dto
     * @return
     * @throws BizServiceException
     */
    void addMember(YMMemberDto dto) throws BizServiceException;

    /**
     * 修改店铺费率
     * @param memberBizDtos
     */
    void updateMemberBiz(List<YmMemberBizDto> memberBizDtos);

    void updateMemberAuditStatus(YMMemberDto dto);

    /**
     * 监听MQ自动更新进件状态
     * @param json
     */
    void updateIntoStatusFromMQ(String json);

    /**
     * 未审核店铺补全资料用
     * @param dto
     */
    void updateMemberNoAndAuditStatus(YMMemberDto dto);

    YMMemberDto getMemberByStoreId(Long storeId);

    List<YMMemberDto> getMemberListByUserId(String userId);

    YMMemberDto getMemberByQrCodeNo(String qrCodeNo);

    YmMemberBizDto getMemberBizByMemberNoAndBizCode(String memberNo, EBizCode bizCode);

    List<YMMemberDto> getMemberByStoreIds(List<Long> ids);

    YMMemberDto getMemberByMemberNo(String memberNo);

    List<YMMemberDto> getMemberListByUserIdAndMemberAuditStatus(String userId, EMemberAuditStatus memberAuditStatus);

    YMMemberDto getMemberByQrCodeNoAndUserId(String qrCodeNo, String userId);

    void updateMemberActivate(YMMemberDto dto);

    /**
     * 查询云码店铺
     * @param searchDto
     * @return
     */
    List<YMMemberDto> getMemberList(YMMemberSearchDto searchDto);


    List<YMMemberDto> findByBankCardId(Long bankCardId);

    void memberIntoToCenter(MemberIntoDto memberIntoDto);

    /**
     * 更新推荐人
     * @param qrCodeNo
     * @param dealerUserId
     */
    void updateMemberDealerUserId(String qrCodeNo, String dealerUserId);

}
