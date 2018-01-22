package com.xinyunlian.jinfu.user.controller;

import com.xinyunlian.jinfu.common.converter.ConverterService;
import com.xinyunlian.jinfu.common.dto.ResultDto;
import com.xinyunlian.jinfu.common.dto.ResultDtoFactory;
import com.xinyunlian.jinfu.common.exception.BizServiceException;
import com.xinyunlian.jinfu.common.security.SecurityContext;
import com.xinyunlian.jinfu.common.util.EncryptUtil;
import com.xinyunlian.jinfu.user.dto.*;
import com.xinyunlian.jinfu.user.enums.EMgtUserSearchType;
import com.xinyunlian.jinfu.user.service.MgtUserService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by DongFC on 2016-08-24.
 */
@RestController
@RequestMapping("mgtUser")
public class MgtUserController {

    private static final Logger LOGGER = LoggerFactory.getLogger(MgtUserController.class);

    @Autowired
    private MgtUserService mgtUserService;

    /**
     * 分页查询用户
     * @param mgtUserSearchDto
     * @return
     */
    @RequiresPermissions({"DEPT_EMP"})
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ResultDto<MgtUserSearchDto> getMgtUserPage(MgtUserSearchDto mgtUserSearchDto){

        //查询部门下的员工
        if (mgtUserSearchDto.getSearchType() != null){
            List<MgtUserDto> userDtoList = new ArrayList<>();

            if (EMgtUserSearchType.DIRECT == mgtUserSearchDto.getSearchType()){
                userDtoList = mgtUserService.findUserByDirectDept(mgtUserSearchDto.getDeptId());
            }else if (EMgtUserSearchType.INCLUDE_SUB_DEPT == mgtUserSearchDto.getSearchType()){
                userDtoList = mgtUserService.getUserByDept(mgtUserSearchDto.getDeptId());
            }

            List<String> userIdList = new ArrayList<>();
            if (!CollectionUtils.isEmpty(userDtoList)){
                userDtoList.forEach(mgtUserDto -> {
                    userIdList.add(mgtUserDto.getUserId());
                });
            }else {
                userIdList.add("-1");
            }
            mgtUserSearchDto.setUserIdList(userIdList);
        }

        MgtUserSearchDto dto = mgtUserService.getMgtUserPage(mgtUserSearchDto);
        return ResultDtoFactory.toAck("获取成功", dto);
    }

    /**
     * 新增用户
     * @param mgtUserAddDto
     * @param result
     * @return
     */
    @RequiresPermissions({"DEPT_EMP"})
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResultDto<String> addMgtUser(@RequestBody @Valid MgtUserAddDto mgtUserAddDto, BindingResult result){
        //校验参数
        if (result.hasErrors()){
            return ResultDtoFactory.toNack(result.getFieldError().getDefaultMessage());
        }

        MgtUserDto loginIdUser = mgtUserService.getMgtUserInfByLoginId(mgtUserAddDto.getLoginId());
        if (loginIdUser != null){
            return ResultDtoFactory.toNack("账号已存在");
        }

        MgtUserDto mobileUser = mgtUserService.getMgtUserByMobile(mgtUserAddDto.getMobile());
        if (mobileUser != null){
            return ResultDtoFactory.toNack("手机号已存在，新增失败");
        }

        MgtUserDto mgtUserDto = ConverterService.convert(mgtUserAddDto, MgtUserDto.class);
        mgtUserDto.setRoleOwnedList(mgtUserAddDto.getRoleOwnedList());
        mgtUserDto.setMgtDeptDto(mgtUserAddDto.getMgtDeptDto());
        mgtUserService.createMgtUserAcct(mgtUserDto);
        return ResultDtoFactory.toAck("新增用户成功");
    }

    /**
     * 删除用户
     * @param userId
     * @return
     */
    @RequiresPermissions({"DEPT_EMP"})
    @RequestMapping(value = "/{userId}", method = RequestMethod.DELETE)
    public ResultDto<String> deleteMgtUserById(@PathVariable String userId){
        mgtUserService.deleteMgtUserAcct(userId);
        return ResultDtoFactory.toAck("删除成功");
    }

    /**
     * 更新用户状态
     * @param mgtUserDto
     * @return
     */
    @RequiresPermissions({"DEPT_EMP"})
    @RequestMapping(value = "/updateMgtUserStatus", method = RequestMethod.POST)
    public ResultDto<String> updateMgtUserStatus(@RequestBody MgtUserDto mgtUserDto){
        mgtUserService.updateMgtUserStatus(mgtUserDto);
        return ResultDtoFactory.toAck("更新用户状态成功");
    }

    /**
     * 重置用户密码
     * @param mgtUserPwdDto
     * @return
     */
    @RequiresPermissions({"DEPT_EMP"})
    @RequestMapping(value = "/resetMgtUserPwd", method = RequestMethod.POST)
    public ResultDto<String> resetMgtUserPwd(@RequestBody MgtUserPwdDto mgtUserPwdDto){
        try {
            String encrypPwd = EncryptUtil.encryptMd5("000000");
            mgtUserPwdDto.setNewPassword(encrypPwd);
            mgtUserService.updateMgtUserPwd(mgtUserPwdDto);
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
            LOGGER.error("用户重置密码加密失败", e);
            return ResultDtoFactory.toNack("重置用户密码失败");
        }

        return ResultDtoFactory.toAck("重置用户密码成功");
    }

    /**
     * 设置用户密码
     * @param mgtUserPwdDto
     * @return
     */
    @RequiresPermissions({"DEPT_EMP"})
    @RequestMapping(value = "/updatePwd", method = RequestMethod.POST)
    public ResultDto<String> updatePwd(@RequestBody MgtUserPwdDto mgtUserPwdDto){
        try {
            if (StringUtils.isEmpty(mgtUserPwdDto.getUserId()) || StringUtils.isEmpty(mgtUserPwdDto.getNewPassword())){
                return ResultDtoFactory.toNack("userId或密码为空，设置失败");
            }
            mgtUserService.updateMgtUserPwd(mgtUserPwdDto);
        } catch (BizServiceException e) {
            return ResultDtoFactory.toNack(e.getError());
        }

        return ResultDtoFactory.toAck("设置用户密码成功");
    }

    /**
     *用户修改自己的密码
     * @param mgtUserPwdDto
     * @return
     */
    @RequestMapping(value = "/updateMyPwd", method = RequestMethod.POST)
    public ResultDto<String> updateMgtUserPwd(@RequestBody MgtUserPwdDto mgtUserPwdDto){
        String userId = SecurityContext.getCurrentUserId();
        mgtUserPwdDto.setUserId(userId);
        try {
            mgtUserService.updateMgtUserPwd(mgtUserPwdDto);
        } catch (BizServiceException e) {
            return ResultDtoFactory.toNack(e.getError());
        }
        return ResultDtoFactory.toAck("密码修改成功");
    }

    /**
     * 根据userId获取用户信息
     * @param userId
     * @return
     */
    @RequestMapping(value = "/{userId}", method = RequestMethod.GET)
    public ResultDto<Object> getMgtUserInf(@PathVariable String userId){
        MgtUserDto userDto = mgtUserService.getMgtUserInf(userId);
        return ResultDtoFactory.toAck("查询成功", userDto);
    }


    /**
     * 更新用户信息
     * @param mgtUserEditDto
     * @param result
     * @return
     */
    @RequiresPermissions({"DEPT_EMP"})
    @RequestMapping(value = "/updateMgtUser", method = RequestMethod.POST)
    public ResultDto<Object> updateMgtUser(@RequestBody @Valid MgtUserEditDto mgtUserEditDto, BindingResult result){
        //校验参数
        if (result.hasErrors()){
            return ResultDtoFactory.toNack(result.getFieldError().getDefaultMessage());
        }

        MgtUserDto mgtUserDto = ConverterService.convert(mgtUserEditDto, MgtUserDto.class);

        MgtUserDto dbUserDto = mgtUserService.getMgtUserInf(mgtUserEditDto.getUserId());
        if (!mgtUserDto.getLoginId().equals(dbUserDto.getLoginId())){
            MgtUserDto loginIdUser = mgtUserService.getMgtUserInfByLoginId(mgtUserDto.getLoginId());
            if (loginIdUser != null){
                return ResultDtoFactory.toNack("账号已存在，更新失败");
            }
        }
        if (!mgtUserDto.getMobile().equals(dbUserDto.getMobile())){
            MgtUserDto mobileUser = mgtUserService.getMgtUserByMobile(mgtUserDto.getMobile());
            if (mobileUser != null){
                return ResultDtoFactory.toNack("手机号已存在，更新失败");
            }
        }

        mgtUserDto.setRoleOwnedList(mgtUserEditDto.getRoleOwnedList());
        mgtUserService.updateMgtUser(mgtUserDto);

        return ResultDtoFactory.toAck("更新用户成功");
    }

}
