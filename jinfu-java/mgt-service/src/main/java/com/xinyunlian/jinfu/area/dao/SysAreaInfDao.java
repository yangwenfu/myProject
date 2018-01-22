package com.xinyunlian.jinfu.area.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.xinyunlian.jinfu.area.entity.SysAreaInfPo;

/**
 * Created by DongFC on 2016-08-19.
 */
public interface SysAreaInfDao extends JpaRepository<SysAreaInfPo, Long>, JpaSpecificationExecutor<SysAreaInfPo> {

    @Query(nativeQuery = true, value = "SELECT t1.* FROM sys_area_inf t1 INNER JOIN prod_area t2 on t1.ID = t2.AREA_ID" +
            " WHERE t2.PROD_ID = ?1")
    List<SysAreaInfPo> findByProduct(String prodId);

    List<SysAreaInfPo> findByParent(Long parent);

    @Modifying
    @Query(nativeQuery = true, value = "delete from sys_area_inf where ID = ?1")
    void deleteById(Long areaId);

    @Query(nativeQuery = true, value = "select * from sys_area_inf where TREE_PATH like concat('%,',?1,',%') " +
            "and length(TREE_PATH)-length(replace(TREE_PATH,',','')) = ?2")
    List<SysAreaInfPo> findSpecificSysAreaInf(Long regionId, Integer level);

    @Query(nativeQuery = true, value = "select * from sys_area_inf where length(TREE_PATH)-length(replace(TREE_PATH,',','')) = ?1")
    List<SysAreaInfPo> findByLevel(Integer level);

    List<SysAreaInfPo> findByIdInOrderByGbCodeAsc(List<Long> ids);

    SysAreaInfPo findByGbCode(String gbCode);

    List<SysAreaInfPo> findByName(String name);

    List<SysAreaInfPo> findByParentOrderByGbCodeAsc(Long parent);

    List<SysAreaInfPo> findByFullName(String fullName);

    @Query(nativeQuery = true, value = "select * from sys_area_inf where " +
            "length(TREE_PATH)-length(replace(TREE_PATH,',','')) = ?1 and `NAME` LIKE CONCAT('%',?2, '%')")
    List<SysAreaInfPo> findByLvlAndName(Integer level, String regionName);

    @Query(nativeQuery = true, value = "select * from sys_area_inf where " +
            "length(TREE_PATH)-length(replace(TREE_PATH,',','')) = ?1 and `NAME` LIKE CONCAT('%',?2, '%') and parent = ?3")
    List<SysAreaInfPo> findByLvlAndNameAndParent(Integer level, String regionName, Long parent);
}
