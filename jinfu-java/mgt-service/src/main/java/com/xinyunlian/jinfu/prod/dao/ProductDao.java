package com.xinyunlian.jinfu.prod.dao;

import com.xinyunlian.jinfu.prod.entity.ProductPo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by DongFC on 2016-08-19.
 */
public interface ProductDao extends JpaRepository<ProductPo, String>, JpaSpecificationExecutor<ProductPo> {

    List<ProductPo> findByProdIdIn(List<String> ids);

    @Query(nativeQuery = true, value = "select t1.PROD_ID, t1.PIC_PATH, t1.PROD_TYPE_PATH, t1.PROD_NAME, t1.PROD_ALIAS, t1.PROVIDER from product t1 " +
            "INNER JOIN prod_shelf t2 on t1.PROD_ID = t2.PROD_ID WHERE t1.PROD_ID in ?1 and t2.SHELF_PLATFORM = ?2")
    List<ProductPo> findByProdIdAndPlatform(List<String> ids, String platform);

    @Query(nativeQuery = true, value = "select t1.PROD_ID, t1.PIC_PATH, t1.PROD_TYPE_PATH, t1.PROD_NAME, t1.PROD_ALIAS, t1.PROVIDER from product t1 " +
            "INNER JOIN prod_shelf t2 on t1.PROD_ID = t2.PROD_ID WHERE t2.SHELF_PLATFORM = ?1")
    List<ProductPo> findByPlatform(String platform);

    @Query(nativeQuery = true, value = "select t1.PROD_ID, t1.PIC_PATH, t1.PROD_TYPE_PATH, t1.PROD_NAME, t1.PROD_ALIAS, t1.PROVIDER from product t1 " +
            "INNER JOIN prod_shelf t2 on t1.PROD_ID = t2.PROD_ID WHERE t1.PROD_TYPE_PATH like ?1 and t2.SHELF_PLATFORM = ?2")
    List<ProductPo> findByTypeAndPlatform(String prodTypePath, String platform);

    @Query(nativeQuery = true, value = "select t1.PROD_ID, t1.PIC_PATH, t1.PROD_TYPE_PATH, t1.PROD_NAME, t1.PROD_ALIAS, t1.PROVIDER " +
            "from product t1 INNER JOIN prod_shelf t2 on t1.PROD_ID = t2.PROD_ID " +
            "INNER  JOIN prod_app_detail t3 on t1.PROD_ID = t3.PROD_ID " +
            "WHERE t2.SHELF_PLATFORM = ?1 AND (CASE WHEN ?2='RCMD' THEN t3.CFG_RCMD_FLAG=1" +
            " WHEN ?2='NEW' THEN t3.CFG_NEW_FLAG=1" +
            " ELSE t3.CFG_HOT_FLAG=1 END)" +
            " ORDER BY (" +
            " CASE WHEN ?2='RCMD' THEN t3.CFG_RCMD_ORDER" +
            " WHEN ?2='NEW' THEN t3.CFG_NEW_ORDER" +
            " ELSE t3.CFG_HOT_ORDER END) ASC LIMIT ?3")
    List<ProductPo> findByPlatformAndCfg(String platform, String cfg,Integer limit);

    @Query(nativeQuery = true, value = "select t1.PROD_ID, t1.PIC_PATH, t1.PROD_TYPE_PATH, t1.PROD_NAME, t1.PROD_ALIAS, t1.PROVIDER from product t1 " +
            "INNER JOIN prod_shelf t2 on t1.PROD_ID = t2.PROD_ID INNER JOIN prod_industry t3 ON t1.PROD_ID = t3.PROD_ID " +
            "WHERE t1.PROD_ID in ?1 and t2.SHELF_PLATFORM = ?2 AND t3.IND_MCC = ?3")
    List<ProductPo> findByProdIdPlatformInd(List<String> ids, String platform, String indMcc);

    @Query(nativeQuery = true, value = "select t1.PROD_ID, t1.PIC_PATH, t1.PROD_TYPE_PATH, t1.PROD_NAME, t1.PROD_ALIAS, t1.PROVIDER from product t1 " +
            "INNER JOIN prod_shelf t2 on t1.PROD_ID = t2.PROD_ID INNER JOIN prod_industry t3 ON t1.PROD_ID = t3.PROD_ID " +
            "WHERE t1.PROD_TYPE_PATH like ?1 and t2.SHELF_PLATFORM = ?2 and t3.IND_MCC IN ?3")
    List<ProductPo> findByTypePlatformInd(String prodTypePath, String platform, List<String> indMccList);

    @Query(nativeQuery = true, value = "select t1.PROD_ID, t1.PIC_PATH, t1.PROD_TYPE_PATH, t1.PROD_NAME, t1.PROD_ALIAS, t1.PROVIDER " +
            "from product t1 INNER JOIN prod_shelf t2 on t1.PROD_ID = t2.PROD_ID " +
            "INNER JOIN prod_app_detail t3 on t1.PROD_ID = t3.PROD_ID " +
            "INNER JOIN prod_industry t4 on t1.PROD_ID = t4.PROD_ID " +
            "WHERE t2.SHELF_PLATFORM = ?1 AND t4.IND_MCC = ?3 " +
            "AND (CASE WHEN ?2='RCMD' THEN t3.CFG_RCMD_FLAG=1" +
            " WHEN ?2='NEW' THEN t3.CFG_NEW_FLAG=1" +
            " ELSE t3.CFG_HOT_FLAG=1 END)" +
            " ORDER BY (" +
            " CASE WHEN ?2='RCMD' THEN t3.CFG_RCMD_ORDER" +
            " WHEN ?2='NEW' THEN t3.CFG_NEW_ORDER" +
            " ELSE t3.CFG_HOT_ORDER END) ASC LIMIT ?4")
    List<ProductPo> findByPlatformCfgInd(String platform, String cfg, String indMcc, Integer limit);

}
