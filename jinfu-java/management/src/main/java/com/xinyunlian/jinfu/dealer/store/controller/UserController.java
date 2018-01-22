package com.xinyunlian.jinfu.dealer.store.controller;

import com.xinyunlian.jinfu.common.dto.ResultDto;
import com.xinyunlian.jinfu.common.dto.ResultDtoFactory;
import com.xinyunlian.jinfu.user.dto.req.UserSearchDto;
import com.xinyunlian.jinfu.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by menglei on 2016年09月18日.
 */
@Controller
@RequestMapping(value = "dealer/store/user")
public class UserController {

    @Autowired
    private UserService userService;


    /**
     * 分页查询商户
     *
     * @param userSearchDto
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ResultDto<UserSearchDto> getUserPage(UserSearchDto userSearchDto) {
        UserSearchDto page = userService.getUserPage(userSearchDto);
        return ResultDtoFactory.toAck("获取成功", page);
    }

}
