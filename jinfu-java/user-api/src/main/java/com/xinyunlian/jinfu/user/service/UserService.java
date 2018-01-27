package com.xinyunlian.jinfu.user.service;

import com.xinyunlian.jinfu.center.dto.CenterUserDto;
import com.xinyunlian.jinfu.common.exception.BizServiceException;
import com.xinyunlian.jinfu.store.dto.StoreInfDto;
import com.xinyunlian.jinfu.user.dto.UserInfoDto;
import com.xinyunlian.jinfu.user.dto.req.CertifyInfoDto;
import com.xinyunlian.jinfu.user.dto.req.IDCardDto;
import com.xinyunlian.jinfu.user.dto.req.PasswordDto;
import com.xinyunlian.jinfu.user.dto.req.UserSearchDto;
import com.xinyunlian.jinfu.user.dto.resp.UserDetailDto;

import java.util.List;

/**
 * 用户Service
 *
 * @author KimLL
 */

public interface UserService {
    /**
     * 获得用户信息
     *
     * @param userId
     * @return
     */
    UserInfoDto findUserByUserId(String userId) throws BizServiceException;


    /**
     * 获得用户信息
     *
     * @param userIds
     * @return
     */
    List<UserInfoDto> findUserByUserId(Iterable<String> userIds);

    /**
     * 通过手机号获取用户信息
     *
     * @param mobile
     * @return
     */
    UserInfoDto findUserByMobile(String mobile);

    /**
     * 姓名模糊查询
     * @param userName
     * @return
     */
    List<UserInfoDto> findByUserNameLike(String userName);

    /**
     * 身份证模糊查询
     * @param idCardNo
     * @return
     */
    List<UserInfoDto> findByIdCardNoLike(String idCardNo);

    /**
     * 获取用户详细信息，包括拥有的小店和绑定的银行卡
     *
     * @param userId
     * @return
     */
    UserDetailDto findUserDetailByUserId(String userId);

    /**
     * 保存用户信息
     *
     * @param userInfoDto
     */
    UserInfoDto saveUser(UserInfoDto userInfoDto) throws BizServiceException;

    /**
     * 更新用户信息
     *
     * @param userInfoDto
     */
    void updateUser(UserInfoDto userInfoDto) throws BizServiceException;

    /**
     * 更新店铺状态
     * @param userId
     * @throws BizServiceException
     */
    void updateStoreAuth(String userId) throws BizServiceException;

    /**
     * 更新用户冻结状态
     * @param userId
     * @param cashierFrozen
     * @param opid
     */
    void updateCashierFrozen(String userId, Boolean cashierFrozen, String opid);

    /**
     * 修改密码
     *
     * @param passwordDto
     */
    void updatePassword(PasswordDto passwordDto) throws BizServiceException;

    void updateDealPassword(PasswordDto passwordDto) throws BizServiceException;

    /**
     * 四要素验证
     *
     * @param certifyInfoDto
     * @throws BizServiceException
     */
    boolean certify(CertifyInfoDto certifyInfoDto) throws BizServiceException;

    /**
     * 实名认证第二版需要上传身份证正反面
     * @param idCardDto
     * @return
     * @throws BizServiceException
     */
    boolean certifyWithCard(IDCardDto idCardDto) throws BizServiceException;

    /**
     * 保存身份证正反面并保存ocr信息
     * @param idCardDto
     */
    void saveIDCard(IDCardDto idCardDto);

    /**
     * 更改实名认证状态
     * @param certifyInfoDto
     */
    void updateIdentityAuth(CertifyInfoDto certifyInfoDto);

    /**
     * 用户复杂查询
     *
     * @param userSearchDto
     * @return
     */
    UserSearchDto getUserPage(UserSearchDto userSearchDto);


    /**
     * 根据烟草证号获得用户
     *
     * @param tobaccoNo
     * @return
     */
    UserInfoDto getUserInfoByTobaccoNo(String tobaccoNo);

    Boolean verifyDealPassword(String userId, String dealPassword);

    /**
     * 注册用户和店铺
     *
     * @param userInfoDto
     * @param storeInfDto
     * @return
     */
    void saveUserAndStore(UserInfoDto userInfoDto, StoreInfDto storeInfDto) throws BizServiceException;

    /**
     * 更新分销员id
     * @param userId
     * @param dealerUserId
     */
    void updateDealerUserId(String userId,String dealerUserId);

    /**
     * 获取支付密码
     * @param mobile
     * @return
     * @throws BizServiceException
     */
    String getDealPwd(String mobile) throws BizServiceException;

    /**
     * 会员中心数据插入
     * @param centerUserDto
     * @return
     * @throws BizServiceException
     */
    String saveFromUserCenter(CenterUserDto centerUserDto) throws BizServiceException;
}
