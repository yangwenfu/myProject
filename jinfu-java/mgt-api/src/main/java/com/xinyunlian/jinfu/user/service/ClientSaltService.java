package com.xinyunlian.jinfu.user.service;

import com.xinyunlian.jinfu.user.dto.ClientSaltDto;

/**
 * Created by DongFC on 2016-10-19.
 */
public interface ClientSaltService {

    ClientSaltDto getClientSalt(String clientId);

}
