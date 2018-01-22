package com.xinyunlian.jinfu.user.service;

import com.xinyunlian.jinfu.common.converter.ConverterService;
import com.xinyunlian.jinfu.common.exception.BizServiceException;
import com.xinyunlian.jinfu.user.dao.UserLinkmanDao;
import com.xinyunlian.jinfu.user.dto.UserLinkmanDto;
import com.xinyunlian.jinfu.user.entity.UserLinkmanPo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * 用户联系人ServiceImpl
 *
 * @author KimLL
 */

@Service
public class UserLinkmanServiceImpl implements UserLinkmanService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserLinkmanServiceImpl.class);

    @Autowired
    private UserLinkmanDao userLinkmanDao;

    @Override
    @Transactional
    public void saveUserLinkman(List<UserLinkmanDto> userLinkmanDtos) throws BizServiceException {
        for (UserLinkmanDto dto :
                userLinkmanDtos) {
            UserLinkmanPo userLinkmanPo = new UserLinkmanPo();
            if (dto.getLinkmanId() != null) {
                userLinkmanPo = userLinkmanDao.findOne(dto.getLinkmanId());
            }
            ConverterService.convert(dto, userLinkmanPo);
            userLinkmanDao.save(userLinkmanPo);
        }
    }

    @Override
    public void deleteUserLinkman(Long linkmanId) {
        userLinkmanDao.delete(linkmanId);
    }

    @Override
    public List<UserLinkmanDto> findByUserId(String userId) {
        List<UserLinkmanPo> userLinkmanPoList = userLinkmanDao.findByUserId(userId);
        if(userLinkmanPoList == null){
            return null;
        }
        List<UserLinkmanDto> userLinkmanDtoList = new ArrayList<>();
        for (UserLinkmanPo userLinkmanPo : userLinkmanPoList) {
            UserLinkmanDto userLinkmanDto = ConverterService.convert(userLinkmanPo, UserLinkmanDto.class);
            userLinkmanDtoList.add(userLinkmanDto);
        }
        return userLinkmanDtoList;
    }

    @Override
    public List<String> findUserIdByLinkmanPhone(String phone) {
        List<UserLinkmanPo> userLinkmanPos = userLinkmanDao.findByMobileContains(phone);
        List<String> ids = new ArrayList<>();
        userLinkmanPos.forEach(userLinkmanPo -> {
            ids.add(userLinkmanPo.getUserId());
        });
        return ids;
    }
}
