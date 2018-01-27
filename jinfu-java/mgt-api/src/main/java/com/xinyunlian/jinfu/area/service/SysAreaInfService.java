package com.xinyunlian.jinfu.area.service;

import com.xinyunlian.jinfu.area.dto.SysAreaInfDto;
import com.xinyunlian.jinfu.area.dto.SysAreaInfSearchDto;
import com.xinyunlian.jinfu.area.enums.ESysAreaLevel;

import java.util.List;

/**
 * Created by DongFC on 2016-08-22.
 */
public interface SysAreaInfService {

    /**
     * 获取地区列表
     * @param sysAreaInfSearchDto
     * @return
     */
    List<SysAreaInfDto> getSysAreaInfList(SysAreaInfSearchDto sysAreaInfSearchDto);

    /**
     * 新增地区
     * @param sysAreaInfDto
     */
    void createSysAreaInf(SysAreaInfDto sysAreaInfDto);

    /**
     * 修改地区信息
     * @param sysAreaInfDto
     */
    void updateSysAreaInf(SysAreaInfDto sysAreaInfDto);

    /**
     * 删除地区
     * @param areaId
     */
    void deleteSysAreaInf(Long areaId);

    /**
     * 根据id获取地区
     * @param areaId
     * @return
     */
    SysAreaInfDto getSysAreaInfById(Long areaId);

    /**
     * 根据指定的regionId，获取指定级别的下属地区信息
     * @param regionId
     * @param sysAreaLevel
     * @return
     */
    List<SysAreaInfDto> getSpecificSysArea(Long regionId, ESysAreaLevel sysAreaLevel);

    /**
     * 获取指定级别的所有地区信息
     * @param sysAreaLevel
     * @return
     */
    List<SysAreaInfDto> getSysAreaInfByLevel(ESysAreaLevel sysAreaLevel);

    /**
     * 根据地区id list获取地区信息列表
     * @param areaIds
     * @return
     */
    List<SysAreaInfDto> getSysAreaInfByIds(List<Long> areaIds);

    /**
     * 根据gb_code获取地区信息
     * @param gbCode
     * @return
     */
    SysAreaInfDto getSysAreaByGbCode(String gbCode);

    /**
     * 根据name地区信息
     * @param name
     * @return
     */
    List<SysAreaInfDto> getSysAreaByName(String name);

    /**
     * 获取指定级别和名字（模糊匹配）的地区
     * @param sysAreaLevel
     * @param regionName
     * @param parent
     * @return
     */
    List<SysAreaInfDto> getSysAreaByLvlAndName(ESysAreaLevel sysAreaLevel, String regionName, Long parent);

    /**
     * 根据 gbCode 查询子级地区信息
     * @param gbCode
     * @return
     */
     List<SysAreaInfDto> getSysAreaUnderGbCode(String gbCode);

    /**
     * 根据地区fullName list获取地区信息列表
     * @param fullName
     * @return
     */
    List<SysAreaInfDto>  getSysAreaInfByFullName(String fullName);

}
