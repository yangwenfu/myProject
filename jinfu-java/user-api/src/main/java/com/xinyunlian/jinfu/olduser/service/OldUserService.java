package com.xinyunlian.jinfu.olduser.service;

import com.xinyunlian.jinfu.olduser.dto.OldUserDto;
import com.xinyunlian.jinfu.user.dto.req.UserSearchDto;
import com.xinyunlian.jinfu.user.dto.resp.UserDetailDto;

/**
 * Created by DongFC on 2016-11-07.
 */
public interface OldUserService {

    /**
     * 新增临时用户
     * @param dto
     * @return
     */
    OldUserDto addOldUser(OldUserDto dto);

    OldUserDto updateMobile(OldUserDto dto);

    OldUserDto findByTobaccoCertificateNo(String tobaccoCertificateNo);

    UserDetailDto findUserDetailByUserId(String userId);

    UserSearchDto getOldUserPage(UserSearchDto userSearchDto);

}
