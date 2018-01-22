package com.xinyunlian.jinfu.report.dealer.dao;

import com.xinyunlian.jinfu.report.dealer.entity.DealerStatsOrderPo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by menglei on 2016/12/07.
 */
public interface DealerStatsOrderDao extends JpaRepository<DealerStatsOrderPo, String>, JpaSpecificationExecutor<DealerStatsOrderPo> {

    /**
     * 按分销员id 月查询订单统计
     * @return
     */
    @Query(nativeQuery = true, value = "select DATE_FORMAT(od.RepDate,'%Y-%m') as MONTH,dso.PROD_ID,dso.PROD_NAME,sum(CASE WHEN STATUS='SUCCESS' THEN 1 ELSE 0 END) as SUCCESSCOUNT,count(dso.PROD_ID) as ALLCOUNT from obj_date od" +
            " left join dealer_stats_order dso on instr(dso.CREATE_TS, od.RepDate) > 0" +
            " where dso.USER_ID=?1 and od.RepDate > '2016-09-01' and od.RepDate < curdate() and DATE_FORMAT(od.RepDate,'%Y-%m') in ?2 group by MONTH,dso.PROD_ID")
    List<Object[]> findMonthByUserId(String userId, List<String> months);

    /**
     * 按分销商id 月查询订单统计
     * @return
     */
    @Query(nativeQuery = true, value = "select DATE_FORMAT(od.RepDate,'%Y-%m') as MONTH,dso.PROD_ID,dso.PROD_NAME,sum(CASE WHEN STATUS='SUCCESS' THEN 1 ELSE 0 END) as SUCCESSCOUNT,count(dso.PROD_ID) as ALLCOUNT from obj_date od" +
            " left join dealer_stats_order dso on instr(dso.CREATE_TS, od.RepDate) > 0" +
            " where dso.DEALER_ID=?1 and od.RepDate > '2016-09-01' and od.RepDate < curdate() and DATE_FORMAT(od.RepDate,'%Y-%m') in ?2 group by MONTH,dso.PROD_ID")
    List<Object[]> findMonthByDealerId(String dealerId, List<String> months);

}
