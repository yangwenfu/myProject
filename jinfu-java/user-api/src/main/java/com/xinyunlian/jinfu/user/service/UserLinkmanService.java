package com.xinyunlian.jinfu.user.service;

import com.xinyunlian.jinfu.common.exception.BizServiceException;
import com.xinyunlian.jinfu.user.dto.UserLinkmanDto;

import java.util.List;

/**
 * 用户联系人Service
 *
 * @author KimLL
 */

public interface UserLinkmanService {

    /**
     * @param userLinkmanDtos
     * @return
     * @throws BizServiceException
     */
    void saveUserLinkman(List<UserLinkmanDto> userLinkmanDtos) throws BizServiceException;

    /**
     * 删除联系人
     *
     * @param linkmanId
     */
    void deleteUserLinkman(Long linkmanId);

    /**
     * 获取联系人
     *
     * @param userId
     * @return
     */
    List<UserLinkmanDto> findByUserId(String userId);

    List<String> findUserIdByLinkmanPhone(String phone);
}
