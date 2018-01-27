package com.xinyunlian.jinfu.house.service;

import com.xinyunlian.jinfu.house.dto.UserHouseDto;

import java.util.List;

/**
 * 用户房产信息Service
 * @author jll
 * @version 
 */

public interface UserHouseService {
    UserHouseDto save(UserHouseDto userHouseDto);

    void delete(Long id);

    UserHouseDto get(Long id);

    List<UserHouseDto> list(String userId);

}
