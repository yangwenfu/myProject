package com.xinyunlian.jinfu.user.service;

import com.xinyunlian.jinfu.common.exception.BizServiceException;
import com.xinyunlian.jinfu.user.dto.MgtUserDto;
import com.xinyunlian.jinfu.user.dto.MgtUserPwdDto;
import com.xinyunlian.jinfu.user.dto.MgtUserSearchDto;

import java.util.Collection;
import java.util.List;

/**
 * Created by DongFC on 2016-08-23.
 */
public interface MgtUserService {

    /**
     * 创建内部帐号
     *
     * @param mgtUserDto
     */
    void createMgtUserAcct(MgtUserDto mgtUserDto) throws BizServiceException;

    /**
     * 分页查询内部用户列表
     *
     * @param mgtUserSearchDto
     * @return
     */
    MgtUserSearchDto getMgtUserPage(MgtUserSearchDto mgtUserSearchDto);

    /**
     * 根据userId删除内部用户
     * @param userId
     */
    void deleteMgtUserAcct(String userId);

    /**
     * 更新用户状态
     * @param mgtUserDto
     */
    void updateMgtUserStatus(MgtUserDto mgtUserDto) throws BizServiceException;

    /**
     * 设置用户密码
     * @param mgtUserPwdDto
     */
    void updateMgtUserPwd(MgtUserPwdDto mgtUserPwdDto) throws BizServiceException;

    /**
     * 获取指定部门下的员工，不包括子部门下的员工
     * @param deptId
     * @return
     */
    List<MgtUserDto> findUserByDirectDept(Long deptId) throws BizServiceException;

    /**
     * 获取指定部门下的所有员工，包括子部门下的员工
     * @param deptId
     * @return
     * @throws BizServiceException
     */
    List<MgtUserDto> getUserByDept(Long deptId) throws BizServiceException;

    /**
     * 更新用户信息
     * @param userDto
     * @throws BizServiceException
     */
    void updateMgtUser(MgtUserDto userDto) throws BizServiceException;

    /**
     * 获取用户信息
     * @param userId
     * @return
     * @throws BizServiceException
     */
    MgtUserDto getMgtUserInf(String userId) throws BizServiceException;

    /**
     * 根据登录id获得用户信息
     * @param loginId
     * @return
     * @throws BizServiceException
     */
    MgtUserDto getMgtUserInfByLoginId(String loginId) throws BizServiceException;

    /**
     * 根据手机号获取用户
     * @param mobile
     * @return
     * @throws BizServiceException
     */
    MgtUserDto getMgtUserByMobile(String mobile) throws BizServiceException;

    /**
     * 根据一批后台管理用户ID查询
     * @param mgtUserIds
     * @return
     */
    List<MgtUserDto> findByMgtUserIds(Collection<String> mgtUserIds);

    /**
     * 根据后台用户姓名进行模糊查询
     * @param name
     * @return
     */
    List<MgtUserDto> findByNameLike(String name);

    /**
     * 根据后台用户姓名进行模糊查询
     * @param name
     * @return
     */
    List<MgtUserDto> findByNameLikeNotStatus(String name);

    /**
     * 获取未分配渠道人员
     * @param duty
     * @return
     */
    List<MgtUserDto> findNotInChannel(String duty);

    /**
     * 根据职务获取用户
     * @param duty
     * @return
     */
    List<MgtUserDto> findByDuty(String duty);
}
