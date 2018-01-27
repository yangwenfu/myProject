package com.xinyunlian.jinfu.center.service;

import com.xinyunlian.jinfu.center.dto.CenterUserDto;

/**
 * Created by King on 2017/5/11.
 */
public interface CenterUserService {
    public CenterUserDto findByUuid(String uuid);

    public void addUserFromMQ(String userDto) throws Exception;

}
