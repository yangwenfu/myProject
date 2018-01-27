package com.xinyunlian.jinfu.car.service;

import com.xinyunlian.jinfu.car.dto.UserCarDto;

import java.util.List;

/**
 * 用户车辆信息Service
 * @author jll
 * @version 
 */

public interface UserCarService {
    UserCarDto save(UserCarDto userCarDto);

    void delete(Long id);

    UserCarDto get(Long id);

    List<UserCarDto> list(String userId);

}
