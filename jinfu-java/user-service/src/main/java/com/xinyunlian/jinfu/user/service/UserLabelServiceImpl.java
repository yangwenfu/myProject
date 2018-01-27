package com.xinyunlian.jinfu.user.service;

import com.xinyunlian.jinfu.common.converter.ConverterService;
import com.xinyunlian.jinfu.user.dao.UserLabelDao;
import com.xinyunlian.jinfu.user.dto.UserLabelDto;
import com.xinyunlian.jinfu.user.entity.UserLabelPo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 用户标签ServiceImpl
 * @author jll
 * @version 
 */

@Service
public class UserLabelServiceImpl implements UserLabelService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserLabelServiceImpl.class);

	@Autowired
	private UserLabelDao userLabelDao;

    @Transactional
    @Override
    public void save(UserLabelDto userLabelDto) {
        if (null != userLabelDto){
            List<UserLabelPo> list = userLabelDao.findByUserIdAndLabelType(userLabelDto.getUserId(),userLabelDto.getLabelType());
            if(!CollectionUtils.isEmpty(list)){
                return;
            }
            UserLabelPo po = ConverterService.convert(userLabelDto, UserLabelPo.class);
            userLabelDao.save(po);
        }
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if (null != id){
            userLabelDao.delete(id);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserLabelDto> listUserLabel(String userId) {
        List<UserLabelDto> userLabelDtos = new ArrayList<>();
        List<UserLabelPo> userLabelPos = userLabelDao.findByUserId(userId);
        if(!CollectionUtils.isEmpty(userLabelPos)){
            userLabelPos.forEach(userLabelPo -> {
                UserLabelDto userLabelDto = ConverterService.convert(userLabelPo,UserLabelDto.class);
                userLabelDtos.add(userLabelDto);
            });
        }
        return userLabelDtos;
    }
	
}
