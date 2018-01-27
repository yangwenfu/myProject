package com.xinyunlian.jinfu.user.service;

import com.xinyunlian.jinfu.common.exception.BizServiceException;
import com.xinyunlian.jinfu.user.dto.MgtPermissionDto;
import com.xinyunlian.jinfu.user.dto.MgtRoleDto;
import com.xinyunlian.jinfu.user.dto.MgtRoleSearchDto;

import java.util.List;

/**
 * Created by dongfangchao on 2016/12/6/0006.
 */
public interface MgtAuthorityService {

    /**
     * 分页获取角色列表
     * @param searchDto
     * @return
     * @throws BizServiceException
     */
    MgtRoleSearchDto getRolePage(MgtRoleSearchDto searchDto) throws BizServiceException;

    /**
     * 删除角色
     * @param roleId
     * @throws BizServiceException
     */
    void deleteRoleByRoleId(Long roleId) throws BizServiceException;

    /**
     * 获取权限列表
     * @return
     * @throws BizServiceException
     */
    List<MgtPermissionDto> getPermList() throws BizServiceException;

    /**
     * 获取权限树
     * @return
     * @throws BizServiceException
     */
    MgtPermissionDto getPermTree() throws BizServiceException;

    /**
     * 新增角色
     * @param role
     * @throws BizServiceException
     */
    void addRole(MgtRoleDto role) throws BizServiceException;

    /**
     * 获取所有角色列表
     * @return
     * @throws BizServiceException
     */
    List<MgtRoleDto> getRoleList() throws BizServiceException;

    /**
     * 修改角色
     * @param roleDto
     * @return
     * @throws BizServiceException
     */
    void updateRole(MgtRoleDto roleDto) throws BizServiceException;

    /**
     * 获取角色详情
     * @param roleId
     * @return
     * @throws BizServiceException
     */
    MgtRoleDto getRoleDetail(Long roleId) throws BizServiceException;

}
