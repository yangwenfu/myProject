package com.xinyunlian.jinfu.spider.service;

/**
 * Created by lenovo on on 2017/7/25.
 */
public interface EnterpriseInfoService {

    /**
     * 根据企业名称爬取企业基本信息
     * @param enpName 企业名称
     * @return



     */
    void crawlerEnterpriseInfo(String enpName,String userId);
   // EnterpriseInfoDto findByOrgCode(String orgCode);

    //void insertEnterPriseInfo(EnterpriseInfoDto epi);





}
