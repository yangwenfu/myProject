package com.xinyunlian.jinfu.center.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xinyunlian.jinfu.area.dto.SysAreaInfDto;
import com.xinyunlian.jinfu.area.service.SysAreaInfService;
import com.xinyunlian.jinfu.center.dto.CenterStoreDto;
import com.xinyunlian.jinfu.mq.DestinationDefine;
import com.xinyunlian.jinfu.store.dao.StoreDao;
import com.xinyunlian.jinfu.store.entity.StoreInfPo;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by King on 2017/5/11.
 */
@Service
public class CenterStoreServiceImpl implements CenterStoreService {
    private static Logger LOGGER = LoggerFactory.getLogger(CenterStoreService.class);
    @Autowired
    private CenterClientService centerClientService;
    @Autowired
    private StoreDao storeDao;
    @Autowired
    private SysAreaInfService  sysAreaInfService;

    @Override
    public CenterStoreDto findByUuid(String uuid) {
        return null;
    }

    @Override
    @Transactional
    @JmsListener(destination = DestinationDefine.ADD_STORE_TO_CENTER)
    public void addStoreFromMQ(String storeDto) {
        try {
            CenterStoreDto centerStoreDto = JSONObject.parseObject(storeDto, CenterStoreDto.class);
            if(StringUtils.isEmpty(centerStoreDto.getUserUUID())){
                return;
            }

            if(centerStoreDto.getAreaId() != null) {
                SysAreaInfDto sysAreaInfDto = sysAreaInfService.getSysAreaInfById(centerStoreDto.getAreaId());
                centerStoreDto.setAreaGbCode(sysAreaInfDto.getGbCode());
            }
            if(centerStoreDto.getStreetId() != null) {
                SysAreaInfDto sysAreaInfDto = sysAreaInfService.getSysAreaInfById(centerStoreDto.getStreetId());
                centerStoreDto.setStreetGbCode(sysAreaInfDto.getGbCode());
            }

            String req = JSON.toJSONString(centerStoreDto);
            LOGGER.info("request body ;{}", req);
            if(!StringUtils.isEmpty(centerStoreDto.getUuid())) {
                String ret = centerClientService.put("store/" + centerStoreDto.getUuid(),req);
                LOGGER.info("repose ;{}", ret);
            }else {
                String ret = centerClientService.post("store", req);
                LOGGER.info("repose ;{}", ret);
                JSONObject jsonObject = JSONObject.parseObject(ret);
                if ("1".equals(jsonObject.getString("status"))) {
                    StoreInfPo storeInfPo = storeDao.findOne(centerStoreDto.getStoreId());
                    storeInfPo.setSuid(jsonObject.getString("data"));
                    storeDao.save(storeInfPo);
                }
            }
        }catch (Exception e) {
            LOGGER.error("推送店铺新增数据出错",e);
            throw e;
        }
    }
}
