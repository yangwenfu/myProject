package com.xinyunlian.jinfu.customer.controller;

import com.xinyunlian.jinfu.common.dto.ResultDto;
import com.xinyunlian.jinfu.common.dto.ResultDtoFactory;
import com.xinyunlian.jinfu.olduser.dto.OldUserDto;
import com.xinyunlian.jinfu.olduser.service.OldUserService;
import com.xinyunlian.jinfu.user.dto.req.UserSearchDto;
import com.xinyunlian.jinfu.user.dto.resp.UserDetailDto;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 商城未激活用户controller
 * Created by King on 2016/12/5.
 */
@RestController
@RequestMapping("oldUser")
public class OldUserController {
    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerController.class);

    @Autowired
    private OldUserService oldUserService;

    /**
     * 分页获取商城未激活用户列表
     * @param userSearchDto
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ResultDto<UserSearchDto> getUserPage(UserSearchDto userSearchDto){
        UserSearchDto dto = oldUserService.getOldUserPage(userSearchDto);
        return ResultDtoFactory.toAck("获取成功", dto);
    }

    /**
     * 修改老用户手机号
     * @param oldUserDto
     * @return
     */
    @RequiresPermissions({"MALL_USER_EDIT"})
    @RequestMapping(value = "/updateMobile", method = RequestMethod.POST)
    public ResultDto<OldUserDto> updateMobile(@RequestBody OldUserDto oldUserDto){
        oldUserService.updateMobile(oldUserDto);
        return ResultDtoFactory.toAck("修改成功");
    }

    /**
     * 获取商城未激活用户详情
     * @param userId
     * @return
     */
    @RequestMapping(value = "/{userId}/detail", method = RequestMethod.GET)
    public ResultDto<UserDetailDto> getUserDetail(@PathVariable String userId){
        UserDetailDto dto = oldUserService.findUserDetailByUserId(userId);
        return ResultDtoFactory.toAck("获取成功", dto);
    }
}
