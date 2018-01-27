package com.xinyunlian.jinfu.api.service;

import com.xinyunlian.jinfu.api.dto.ApiBaiduDto;
import com.xinyunlian.jinfu.store.dao.StoreDao;
import com.xinyunlian.jinfu.store.dto.StoreInfDto;
import com.xinyunlian.jinfu.store.entity.StoreInfPo;
import com.xinyunlian.jinfu.store.enums.ELocationSource;
import org.apache.commons.lang.StringUtils;
import org.json.JSONObject;
import com.xinyunlian.jinfu.common.exception.BizServiceException;
import com.xinyunlian.jinfu.common.util.HttpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by menglei on 2016年10月27日.
 */
@Service
public class ApiBaiduServiceImpl implements ApiBaiduService {

    @Autowired
    private StoreDao storeDao;

    private static String AK = "F7Nh61NEbXcvpzGLGigwSNX92jl3TQ6c";
    private static String URL = "http://api.map.baidu.com";

    /**
     * 根据详细地址获取经纬度坐标
     *
     * @param address
     * @return
     * @throws BizServiceException
     */
    @Override
    public String getPoint(String address) {
        if (StringUtils.isEmpty(address)) {
            return null;
        }
        String result = null;
        try {
            String queryString = "?address=" + address.replace("#", StringUtils.EMPTY).replace(" ", StringUtils.EMPTY) + "&output=json&ak=" + AK;
            result = HttpUtil.doGetToString(URL + "/geocoder/v2/" + queryString);
        } catch (Exception e) {
            //throw new BizServiceException(EErrorCode.DEALER_API_GET_ERROR);
            return null;
        }
        JSONObject jSONObject = new JSONObject(result);
        if (!jSONObject.get("status").toString().equals("0")) {
            if (jSONObject.get("status").toString().equals("1")) {
                //return "0,0";
                return null;
            }
            return null;
        }
        String lng = jSONObject.getJSONObject("result").getJSONObject("location").get("lng").toString();
        String lat = jSONObject.getJSONObject("result").getJSONObject("location").get("lat").toString();
        return lng + "," + lat;
    }

    /**
     * 根据经纬度坐标获取详细地址
     *
     * @param location
     * @return
     * @throws BizServiceException
     */
    @Override
    public ApiBaiduDto getArea(String location) {
        if (StringUtils.isEmpty(location)) {
            return null;
        }
        String result = null;
        try {
            String queryString = "?location=" + location + "&output=json&ak=" + AK;
            result = HttpUtil.doGetToString(URL + "/geocoder/v2/" + queryString);
        } catch (Exception e) {
            //throw new BizServiceException(EErrorCode.DEALER_API_GET_ERROR);
            return null;
        }
        JSONObject jSONObject = new JSONObject(result);
        if (!jSONObject.get("status").toString().equals("0")) {
            return null;
        }
        String province = jSONObject.getJSONObject("result").getJSONObject("addressComponent").get("province").toString();
        String city = jSONObject.getJSONObject("result").getJSONObject("addressComponent").get("city").toString();
        String district = jSONObject.getJSONObject("result").getJSONObject("addressComponent").get("district").toString();
        String gbCode = jSONObject.getJSONObject("result").getJSONObject("addressComponent").get("adcode").toString();
        ApiBaiduDto apiBaiduDto = new ApiBaiduDto();
        apiBaiduDto.setProvince(province);
        apiBaiduDto.setCity(city);
        apiBaiduDto.setArea(district);
        apiBaiduDto.setGbCode(gbCode);
        return apiBaiduDto;
    }

    /**
     * 更新店铺坐标
     *
     * @param storeInfDto address city id
     */
    @Override
    @Transactional
    public void updatePoint(StoreInfDto storeInfDto) {
        StoreInfPo storeInfPo = storeDao.findOne(storeInfDto.getStoreId());
        if (storeInfPo != null && ELocationSource.CONVERT.equals(storeInfPo.getLocationSource())) {
            if (StringUtils.isNotEmpty(storeInfDto.getCity()) && StringUtils.isNotEmpty(storeInfDto.getAddress())) {
                String address = storeInfDto.getCity();
                if (address.equals("市辖区") || address.equals("市辖县")) {
                    address = storeInfDto.getProvince();
                }
                String point = getPoint(address + storeInfDto.getArea() + storeInfDto.getAddress());
                if (StringUtils.isNotEmpty(point)) {
                    storeInfPo.setLng(point.split(",")[0]);
                    storeInfPo.setLat(point.split(",")[1]);
                } else {
                    storeInfPo.setLng(null);
                    storeInfPo.setLat(null);
                }
                storeDao.save(storeInfPo);
            }

        }
    }

}
