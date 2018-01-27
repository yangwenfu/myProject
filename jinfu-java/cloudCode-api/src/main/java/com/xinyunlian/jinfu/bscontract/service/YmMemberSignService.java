package com.xinyunlian.jinfu.bscontract.service;

import com.xinyunlian.jinfu.api.dto.MemberNoDto;
import com.xinyunlian.jinfu.bscontract.dto.YmMemberSignDto;
import com.xinyunlian.jinfu.common.exception.BizServiceException;

/**
 * Created by dongfangchao on 2017/2/22/0022.
 */
public interface YmMemberSignService {

    /**
     * 根据id获取签名信息
     * @param id
     * @return
     */
    YmMemberSignDto getYmMemberSignById(Long id);

    /**
     * 根据店铺id获取签名信息
     * @param storeId
     * @return
     */
    YmMemberSignDto getYmMemberSignByStoreId(Long storeId);

    /**
     * 根据二维码获取签名信息
     * @param qrcodeNo
     * @return
     */
    YmMemberSignDto getYmMemberSignByQrcodeNo(String qrcodeNo);

    /**
     * 保存签名信息
     * @param ymMemberSignDto
     * @throws BizServiceException
     */
    YmMemberSignDto saveYmMemberSign(YmMemberSignDto ymMemberSignDto) throws BizServiceException;

    /**
     * 更新签名状态
     * @param qrcodeNo
     * @throws BizServiceException
     */
    void updateYmMemberSignStatus(String qrcodeNo) throws BizServiceException;

    /**
     * 更新合同路径
     * @param id
     * @param firstPageFilePath
     * @param lastPageFilePath
     * @throws BizServiceException
     */
    void updateYmMemberSignFilePath(Long id, String firstPageFilePath, String lastPageFilePath) throws BizServiceException;

    /**
     * 删除云码签名记录
     * @param id
     * @throws BizServiceException
     */
    void deleteById(Long id) throws BizServiceException;

    /**
     * 根据店铺id删除
     * @param storeId
     * @throws MemberNoDto.Biz
     */
    void deleteByStoreId(Long storeId) throws BizServiceException;

    /**
     * 更新签名状态
     * @param id
     * @throws BizServiceException
     */
    void updateYmMemberSignStatusById(Long id) throws BizServiceException;

}
