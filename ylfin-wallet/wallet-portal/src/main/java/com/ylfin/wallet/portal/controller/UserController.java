package com.ylfin.wallet.portal.controller;

import java.util.List;

import com.xinyunlian.jinfu.bank.dto.BankCardDto;
import com.xinyunlian.jinfu.bank.service.BankService;
import com.xinyunlian.jinfu.user.dto.DealerUserDto;
import com.xinyunlian.jinfu.user.dto.UserExtDto;
import com.xinyunlian.jinfu.user.dto.UserInfoDto;
import com.xinyunlian.jinfu.user.enums.EHouseProperty;
import com.xinyunlian.jinfu.user.enums.EMarryStatus;
import com.xinyunlian.jinfu.user.service.DealerUserService;
import com.xinyunlian.jinfu.user.service.UserExtService;
import com.xinyunlian.jinfu.user.service.UserService;
import com.ylfin.core.auth.AuthenticationAdapter;
import com.ylfin.core.beanmapper.BeanMapperOperations;
import com.ylfin.core.exception.ServiceException;
import com.ylfin.wallet.portal.CurrentUser;
import com.ylfin.wallet.portal.controller.vo.DealerUserInfo;
import com.ylfin.wallet.portal.controller.vo.UserDetail;
import com.ylfin.wallet.portal.controller.vo.UserExtraInfo;
import com.ylfin.wallet.portal.controller.vo.UserInfo;
import io.swagger.annotations.Api;
import com.ylfin.wallet.portal.util.EnumConverter;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
@Api("用户信息接口")
@RestController
@RequestMapping("/api/user")
public class UserController {

	@Autowired
	private AuthenticationAdapter<CurrentUser, String> authenticationAdapter;

    @Autowired
    private UserExtService userExtService;

    @Autowired
    private BeanMapperOperations beanMapperOperations;

    @Autowired
    private DealerUserService dealerUserService;

    @Autowired
    private UserService userService;

    @Autowired
    private BankService bankService;


    @ApiOperation("获取当前用户信息")
    @GetMapping("/info")
    public UserInfo userInfo() {
        CurrentUser currentUserInfo = authenticationAdapter.getCurrentUserInfo();
        UserInfo userInfo = new UserInfo();
        userInfo.setUserId(currentUserInfo.getUserId());
        userInfo.setMobile(currentUserInfo.getMobile());
        userInfo.setName(currentUserInfo.getRealName());
        return userInfo;
    }

    @ApiOperation("获取当前用户基本信息")
    @GetMapping("/extra-info")
    public UserExtraInfo getExtraInfo() {
        UserExtDto userExtDto = userExtService.findUserByUserId(authenticationAdapter.getCurrentUserId());
        UserExtraInfo userExtraInfo = beanMapperOperations.map(userExtDto,UserExtraInfo.class);
        return userExtraInfo;
    }

    @ApiOperation("修改当前用户基本信息")
    @PostMapping
    public void updateExtraInfo(@RequestBody UserExtraInfo extraInfo) {
        UserExtDto userExtDto = beanMapperOperations.map(extraInfo,UserExtDto.class);
        if (StringUtils.isNotBlank(extraInfo.getHouseProperty())) {
            EHouseProperty eHouseProperty = EnumConverter.getEHouseProperty(extraInfo.getHouseProperty());
            if (eHouseProperty != null){
                userExtDto.setHouseProperty(eHouseProperty);
            }
        }
        if (StringUtils.isNotBlank(extraInfo.getMarryStatus())){
            EMarryStatus eMarryStatus = EnumConverter.getEMarryStatus(extraInfo.getMarryStatus());
            if (eMarryStatus != null){
                userExtDto.setMarryStatus(eMarryStatus);
            }
        }
        userExtDto.setUserId(authenticationAdapter.getCurrentUserId());
        userExtService.saveUserExt(userExtDto);
    }



    @ApiOperation("我的客户经理")
    @GetMapping("/dealer-info")
    public DealerUserInfo getDealerUserInfo(@RequestParam("mobile") String mobile){
        if (StringUtils.isBlank(mobile)){
            throw new ServiceException("我的客户经理电话不能为空");
        }
        DealerUserDto dealerUserDto = dealerUserService.findDealerUserByMobile(mobile);
        if (dealerUserDto == null){
            throw new ServiceException("我的客户经理不存在");
        }
        DealerUserInfo dealerUserInfo = beanMapperOperations.map(dealerUserDto, DealerUserInfo.class);
        return dealerUserInfo;
    }

    @ApiOperation("用户的详细信息")
    @GetMapping("/user-detail")
    public UserDetail getUserDetail(){
        String currentUserId = authenticationAdapter.getCurrentUserId();
        UserInfoDto userInfoDto = userService.findUserByUserId(currentUserId);
        if(userInfoDto == null){
            throw new ServiceException("当前用户不存在");
        }
        UserDetail userDetail = new UserDetail();
        userDetail.setUserId(currentUserId);
        userDetail.setUserName(userInfoDto.getUserName());
        if (StringUtils.isNotBlank(userInfoDto.getDealPassword())){
            userDetail.setDealPasswordIsExist(true);
        }else {
            userDetail.setDealPasswordIsExist(false);
        }
        userDetail.setIdentityAuth(userInfoDto.getIdentityAuth() == null? false:userInfoDto.getIdentityAuth());
        List<BankCardDto> bankCardDtos = bankService.findByUserId(currentUserId);
        if (CollectionUtils.isEmpty(bankCardDtos)){
            userDetail.setBankCarNum(0);
        }else {
            userDetail.setBankCarNum(bankCardDtos.size());
        }
        userDetail.setUserHeadPic(null);//目前页面上没有设置头像功能
        return userDetail;
    }

}
