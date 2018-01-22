package com.xinyunlian.jinfu.user.service;

import com.xinyunlian.jinfu.user.dto.UserLabelDto;

import java.util.List;

/**
 * 用户标签Service
 *
 * @author jll
 */

public interface UserLabelService {

    void save(UserLabelDto userLabelDto);

    void delete(Long id);

    List<UserLabelDto> listUserLabel(String userId);

}
