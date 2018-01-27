package com.xinyunlian.jinfu.user.service;

import com.xinyunlian.jinfu.common.exception.BizServiceException;
import com.xinyunlian.jinfu.user.dto.DealerUserDto;
import com.xinyunlian.jinfu.user.dto.DealerUserPwdDto;
import com.xinyunlian.jinfu.user.dto.DealerUserQRInfo;

import java.util.List;

/**
 * Created by menglei on 2016年08月26日.
 */
public interface DealerUserService {

    DealerUserDto getDealerUserById(String userId);

    DealerUserDto findDealerUserByMobile(String mobile);

    List<DealerUserDto> findDealerUsersByDealerId(String dealerId);

    void createDealerUser(DealerUserDto dealerUserDto) throws BizServiceException;

    void updateDealerUser(DealerUserDto dealerUserDto) throws BizServiceException;

    void idAuth(DealerUserDto dealerUserDto) throws BizServiceException;

    void lived(DealerUserDto dealerUserDto) throws BizServiceException;

    void deleteDealerUser(String userId) throws BizServiceException;

    void updateDealerUserGroup(DealerUserDto dealerUserDto) throws BizServiceException;

    void updateDealerUserPwd(DealerUserPwdDto dealerUserPwdDto) throws BizServiceException;

    List<DealerUserDto> getDealerUserList(DealerUserDto dealerUserDto);

    List<DealerUserDto> getDealerUserIdsByAddressIDS(List<String> list);

    List<DealerUserDto> getAllDealerUsers();

    List<DealerUserDto> findByNameLike(String name);

    /**
     * 根据一批分销人员编号查找
     * @param dealerUserIds
     * @return
     */
    List<DealerUserDto> findByDealerUserIds(List<String> dealerUserIds);

    /**
     * 生成分销员二维码
     * @param userId
     * @return
     */
    DealerUserQRInfo getQrInfo(String userId);

    /**
     * 验证分销员二维码
     * @param qrInfo
     * @return
     */
    DealerUserQRInfo checkQrInfo(DealerUserQRInfo qrInfo);

    /**
     * 根据手机号查询对应分销员
     * @param mobiles
     * @return
     */
    List<DealerUserDto> findByMobiles(List<String> mobiles);

    /**
     * 批量添加分销员
     * @param dealerUserDtos
     * @throws BizServiceException
     */
    void createDealerUsers(List<DealerUserDto> dealerUserDtos) throws BizServiceException;

    /**
     * 更新首考状态
     * @param userId
     * @throws BizServiceException
     */
    void updateExamPass(String userId) throws BizServiceException;

    /**
     * 冻结解冻状态
     * @param userId
     */
    void updateFrozen(String userId);

}
