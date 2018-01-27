package com.xinyunlian.jinfu.channel.service;

import com.xinyunlian.jinfu.channel.dao.ChannelUserAreaDao;
import com.xinyunlian.jinfu.channel.dao.ChannelUserRelationDao;
import com.xinyunlian.jinfu.channel.dto.ChannelUserAreaDto;
import com.xinyunlian.jinfu.channel.dto.ChannelUserDto;
import com.xinyunlian.jinfu.channel.dto.ChannelUserRelationDto;
import com.xinyunlian.jinfu.channel.dto.ChannelUserSearchDto;
import com.xinyunlian.jinfu.channel.entity.ChannelUserAreaPo;
import com.xinyunlian.jinfu.channel.entity.ChannelUserRelationPo;
import com.xinyunlian.jinfu.common.converter.ConverterService;
import com.xinyunlian.jinfu.common.util.BizUtil;
import com.xinyunlian.jinfu.user.dao.MgtUserDao;
import com.xinyunlian.jinfu.user.entity.MgtUserPo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

/**
 * 渠道用户ServiceImpl
 * @author jll
 * @version 
 */

@Service
public class ChannelUserServiceImpl implements ChannelUserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ChannelUserServiceImpl.class);
    @Autowired
    private MgtUserDao mgtUserDao;
	@Autowired
	private ChannelUserRelationDao channelUserDao;
    @Autowired
    private ChannelUserAreaDao channelUserAreaDao;

    @Override
    @Transactional
    public void saveUserRelation(List<ChannelUserRelationDto> list){
        List<ChannelUserRelationPo> channelUserRelationPos  = ConverterService.convertToList(list,ChannelUserRelationPo.class);
        if(channelUserRelationPos != null){
            channelUserDao.deleteByParentUserId(channelUserRelationPos.get(0).getParentUserId());
            if(channelUserRelationPos.size() == 1 && StringUtils.isEmpty(channelUserRelationPos.get(0).getUserId())){
                return;
            }
            channelUserDao.save(channelUserRelationPos);
        }
    }

    @Override
    public List<ChannelUserRelationDto> listUserRelation(String parentUserId){
        List<ChannelUserRelationDto> dtos = new ArrayList<>();
        List<ChannelUserRelationPo> pos = channelUserDao.findByParentUserId(parentUserId);
        if(pos != null){
            dtos = ConverterService.convertToList(pos,ChannelUserRelationDto.class);
        }
        return dtos;
    }

    @Override
    @Transactional
    public void saveUserArea(List<ChannelUserAreaDto> list){
        List<ChannelUserAreaPo> channelUserAreaPos = ConverterService.convertToList(list,ChannelUserAreaPo.class);
        if(channelUserAreaPos != null){
            channelUserAreaDao.deleteByUserId(channelUserAreaPos.get(0).getUserId());
            channelUserAreaDao.save(channelUserAreaPos);
        }
    }

    @Override
    public List<ChannelUserAreaDto> listUserArea(String userId){
        List<ChannelUserAreaDto> dtos = new ArrayList<>();
        List<ChannelUserAreaPo> pos = channelUserAreaDao.findByUserId(userId);
        if(pos != null){
            dtos = ConverterService.convertToList(pos,ChannelUserAreaDto.class);
        }
        return dtos;
    }

    @Override
    public ChannelUserSearchDto getChannelUserPage(ChannelUserSearchDto searchDto) {
        List<String> userIds = new ArrayList<>();
        userIds.add("-9999");
        if(!StringUtils.isEmpty(searchDto.getParentUserId())) {
            List<ChannelUserRelationPo> channelUserRelationPos = channelUserDao.findByParentUserId(searchDto.getParentUserId());
            if(!CollectionUtils.isEmpty(channelUserRelationPos)){
                channelUserRelationPos.forEach(channelUserRelationPo -> {
                    userIds.add(channelUserRelationPo.getUserId());
                });
            }
        }

        Specification<MgtUserPo> spec = (root, query, cb) -> {
            Predicate predicate = cb.conjunction();
            List<Expression<Boolean>> expressions = predicate.getExpressions();
            if (null != searchDto) {
                if (!StringUtils.isEmpty(searchDto.getLoginId())) {
                    expressions.add(cb.like(root.get("loginId"), BizUtil.filterString(searchDto.getLoginId())));
                }
                if (!StringUtils.isEmpty(searchDto.getName())) {
                    expressions.add(cb.like(root.get("name"), BizUtil.filterString(searchDto.getName())));
                }
                if (!StringUtils.isEmpty(searchDto.getMobile())) {
                    expressions.add(cb.like(root.get("mobile"), BizUtil.filterString(searchDto.getMobile())));
                }
                if (!StringUtils.isEmpty(searchDto.getDuty())) {
                    expressions.add(cb.equal(root.get("duty"),searchDto.getDuty()));
                }
                if (!StringUtils.isEmpty(searchDto.getParentUserId())) {
                    expressions.add(cb.in(root.get("userId")).value(userIds));
                }
            }
            return predicate;
        };

        Pageable pageable = new PageRequest(searchDto.getCurrentPage() - 1, searchDto.getPageSize());
        Page<MgtUserPo> page = mgtUserDao.findAll(spec, pageable);

        List<ChannelUserDto> data = new ArrayList<>();
        page.getContent().forEach(po -> {
            ChannelUserDto channelUserDto = ConverterService.convert(po, ChannelUserDto.class);
            channelUserDto.setChildrenCount(channelUserDao.countByParentUserId(po.getUserId()));
            channelUserDto.setAreaCount(channelUserAreaDao.countByUserId(po.getUserId()));
            data.add(channelUserDto);
        });

        searchDto.setList(data);
        searchDto.setTotalPages(page.getTotalPages());
        searchDto.setTotalRecord(page.getTotalElements());
        return searchDto;
    }

}
