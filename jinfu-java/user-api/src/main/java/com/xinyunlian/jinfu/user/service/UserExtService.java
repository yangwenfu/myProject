package com.xinyunlian.jinfu.user.service;

import com.xinyunlian.jinfu.user.dto.UserExtDto;
import com.xinyunlian.jinfu.user.dto.ext.UserExtIdDto;

/**
 * Created by dell1 on 2016/9/6.
 */
public interface UserExtService {
    /**
     * 保存修改用户扩展表
     * @param userExtDto
     * @return
     */
    void saveUserExt(UserExtDto userExtDto);

    /**
     * 获取扩展信息
     * @param userId
     * @return
     */
    UserExtDto findUserByUserId(String userId);

    UserExtIdDto getUserExtPart(UserExtIdDto extIdDto);
    /**
     * 部分保存
     * @param extIdDto
     */
    void saveUserExtPart(UserExtIdDto extIdDto);

    /**
     *更新订烟系统授权状态
     * @param userExtDto
     */
    void updateTobaccoAuth(UserExtDto userExtDto);
}
