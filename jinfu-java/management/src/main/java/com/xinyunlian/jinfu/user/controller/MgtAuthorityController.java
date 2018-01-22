package com.xinyunlian.jinfu.user.controller;

import com.xinyunlian.jinfu.common.dto.ResultDto;
import com.xinyunlian.jinfu.common.dto.ResultDtoFactory;
import com.xinyunlian.jinfu.common.exception.BizServiceException;
import com.xinyunlian.jinfu.user.dto.MgtPermissionDto;
import com.xinyunlian.jinfu.user.dto.MgtRoleDto;
import com.xinyunlian.jinfu.user.dto.MgtRoleSearchDto;
import com.xinyunlian.jinfu.user.service.MgtAuthorityService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 权限配置控制器
 * Created by dongfangchao on 2016/12/6/0006.
 */
@RestController
@RequestMapping("mgtAuthority")
@RequiresPermissions({"ROLE_PERM"})
public class MgtAuthorityController {

    private static final Logger LOGGER = LoggerFactory.getLogger(MgtAuthorityController.class);

    @Autowired
    private MgtAuthorityService mgtAuthorityService;

    /**
     * 分页查询角色列表
     * @param searchDto
     * @return
     */
    @RequestMapping(value = "/role/page", method = RequestMethod.GET)
    public ResultDto<Object> getRolePage(MgtRoleSearchDto searchDto){
        searchDto = mgtAuthorityService.getRolePage(searchDto);
        return ResultDtoFactory.toAck("查询成功", searchDto);
    }

    /**
     * 删除角色
     * @param roleId
     * @return
     */
    @RequestMapping(value = "/role/{roleId}", method = RequestMethod.DELETE)
    public ResultDto<Object> deleteRole(@PathVariable Long roleId){
        try {
            mgtAuthorityService.deleteRoleByRoleId(roleId);
            return ResultDtoFactory.toAck("删除成功");
        } catch (BizServiceException e) {
            return ResultDtoFactory.toNack(e.getError());
        }
    }

    /**
     * 获取权限树
     * @return
     */
    @RequestMapping(value = "/perm/tree", method = RequestMethod.GET)
    public ResultDto<Object> getPerms(){
        MgtPermissionDto permRoot = mgtAuthorityService.getPermTree();
        return ResultDtoFactory.toAck("权限树获取成功", permRoot);
    }

    /**
     * 新增角色
     * @param mgtRoleDto
     * @return
     */
    @RequestMapping(value = "/role/add", method = RequestMethod.PUT)
    public ResultDto<Object> addRole(@RequestBody MgtRoleDto mgtRoleDto){
        try {
            mgtAuthorityService.addRole(mgtRoleDto);
            return ResultDtoFactory.toAck("添加角色成功");
        } catch (BizServiceException e) {
            return ResultDtoFactory.toNack(e.getError());
        }
    }

    /**
     * 获取所有角色列表
     * @return
     */
    @RequestMapping(value = "/role/list", method = RequestMethod.GET)
    public ResultDto<Object> getRoleList(){
        List<MgtRoleDto> list = mgtAuthorityService.getRoleList();
        return ResultDtoFactory.toAck("查询成功", list);
    }

    /**
     * 更新角色
     * @param mgtRoleDto
     * @return
     */
    @RequestMapping(value = "/role/modify", method = RequestMethod.POST)
    public ResultDto<Object> modifyRole(@RequestBody MgtRoleDto mgtRoleDto){
        try {
            mgtAuthorityService.updateRole(mgtRoleDto);
            return ResultDtoFactory.toAck("更新成功");
        } catch (BizServiceException e) {
            return ResultDtoFactory.toNack(e.getError());
        }
    }

    /**
     * 获取角色详情
     * @param roleId
     * @return
     */
    @RequestMapping(value = "/role/{roleId}", method = RequestMethod.GET)
    public ResultDto<Object> getRoleDetail(@PathVariable Long roleId){
        MgtRoleDto dto = mgtAuthorityService.getRoleDetail(roleId);
        if (dto != null){
            return ResultDtoFactory.toAck("查询成功", dto);
        }
        return ResultDtoFactory.toNack("角色不存在");
    }

}
