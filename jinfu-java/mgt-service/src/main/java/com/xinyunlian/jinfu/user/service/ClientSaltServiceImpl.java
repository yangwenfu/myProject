package com.xinyunlian.jinfu.user.service;

import com.xinyunlian.jinfu.common.converter.ConverterService;
import com.xinyunlian.jinfu.user.dao.ClientSaltDao;
import com.xinyunlian.jinfu.user.dto.ClientSaltDto;
import com.xinyunlian.jinfu.user.entity.ClientSaltPo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * Created by DongFC on 2016-10-19.
 */
@Service
public class ClientSaltServiceImpl implements ClientSaltService {

    @Autowired
    private ClientSaltDao clientSaltDao;

    @Override
    public ClientSaltDto getClientSalt(String clientId) {

        if (!StringUtils.isEmpty(clientId)){

            ClientSaltPo po = clientSaltDao.findByClientId(clientId);
            ClientSaltDto dto = ConverterService.convert(po, ClientSaltDto.class);

            return dto;
        }

        return null;
    }
}
