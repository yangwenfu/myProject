package com.xinyunlian.jinfu.report.dealer.dao;

import com.xinyunlian.jinfu.report.dealer.entity.DealerStatsMonthPo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by menglei on 2016/12/07.
 */
public interface DealerStatsMonthDao extends JpaRepository<DealerStatsMonthPo, Long>, JpaSpecificationExecutor<DealerStatsMonthPo> {

    /**
     * 按分销员id 月查询拜访统计
     * @return
     */
    @Query(nativeQuery = true, value = "select DATE_FORMAT(od.RepDate,'%Y-%m') as MONTH_DATE,si.USER_ID as USER_ID,si.DEALER_ID as DEALER_ID,count(si.ID) as SIGN_COUNT from obj_date od" +
            " left join jinfu_dealer_pro.sign_info_view si on instr(si.CREATE_TS, od.RepDate) > 0" +
            " where si.USER_ID=?1 and od.RepDate > '2016-09-01' and od.RepDate < curdate() and DATE_FORMAT(od.RepDate,'%Y-%m') in ?2 group by MONTH_DATE,USER_ID order by MONTH_DATE desc")
    List<Object[]> findMonthSignInfoByUserId(String userId, List<String> months);

    /**
     * 按分销商id 月查询拜访统计
     * @return
     */
    @Query(nativeQuery = true, value = "select DATE_FORMAT(od.RepDate,'%Y-%m') as MONTH_DATE,si.USER_ID as USER_ID,si.DEALER_ID as DEALER_ID,count(si.ID) as SIGN_COUNT from obj_date od" +
            " left join jinfu_dealer_pro.sign_info_view si on instr(si.CREATE_TS, od.RepDate) > 0" +
            " where si.DEALER_ID=?1 and od.RepDate > '2016-09-01' and od.RepDate < curdate() and DATE_FORMAT(od.RepDate,'%Y-%m') in ?2 group by MONTH_DATE,DEALER_ID order by MONTH_DATE desc")
    List<Object[]> findMonthSignInfoByDealerId(String dealerId, List<String> months);

    /**
     * 按分销员id 月查询云码统计
     * @return
     */
    @Query(nativeQuery = true, value = "select DATE_FORMAT(od.RepDate,'%Y-%m') as MONTH_DATE,ym.CREATE_OPID as CREATE_OPID,count(ym.ID) as MEMBER_COUNT from jinfu_report_pro.obj_date od" +
            " left join jinfu_cloudcode_pro.ym_member_view ym on instr(ym.CREATE_TS, od.RepDate) > 0" +
            " where ym.CREATE_OPID=?1 and od.RepDate > '2016-09-01' and od.RepDate < curdate() and DATE_FORMAT(od.RepDate,'%Y-%m') in ?2 group by MONTH_DATE,CREATE_OPID order by MONTH_DATE desc")
    List<Object[]> findMonthMemberByUserId(String userId, List<String> months);

    /**
     * 按分销商id 月查询云码统计
     * @return
     */
    @Query(nativeQuery = true, value = "select DATE_FORMAT(od.RepDate,'%Y-%m') as MONTH_DATE,ym.CREATE_OPID as CREATE_OPID,count(ym.ID) as MEMBER_COUNT from jinfu_report_pro.obj_date od" +
            " left join jinfu_cloudcode_pro.ym_member_view ym on instr(ym.CREATE_TS, od.RepDate) > 0" +
            " left join jinfu_dealer_pro.dealer_user du on ym.CREATE_OPID=du.USER_ID" +
            " where du.DEALER_ID=?1 and od.RepDate > '2016-09-01' and od.RepDate < curdate() and DATE_FORMAT(od.RepDate,'%Y-%m') in ?2 group by MONTH_DATE,du.DEALER_ID order by MONTH_DATE desc")
    List<Object[]> findMonthMemberByDealerId(String dealerId, List<String> months);

    /**
     * 根据分销员id按月查询
     * @return
     */
    @Query(nativeQuery = true, value = "select a.MONTH_DATE as MONTH_DATE,ifnull(b.REGISTER_COUNT,0) as REGISTER_COUNT,ifnull(b.QR_CODE_COUNT,0) as QR_CODE_COUNT,ifnull(b.NOTE_COUNT,0) as NOTE_COUNT from " +
            " (select DATE_FORMAT(RepDate,'%Y-%m') as MONTH_DATE from  obj_date group by DATE_FORMAT(RepDate,'%Y-%m')) as a left join " +
            " (select DATE_FORMAT(dss.CREATE_TS,'%Y-%m') as MONTH_DATE,dss.USER_ID as USER_ID,dss.DEALER_ID as DEALER_ID,count(dss.ID) as REGISTER_COUNT,sum(CASE WHEN QR_CODE_URL is null THEN 0 WHEN QR_CODE_URL='' THEN 0 ELSE 1 END) as QR_CODE_COUNT,0 as NOTE_COUNT from dealer_stats_store dss " +
            " where 1=1 and dss.USER_ID=?1 and dss.CREATE_TS < curdate() group by DATE_FORMAT(dss.CREATE_TS,'%Y-%m'),USER_ID) as b on a.MONTH_DATE=b.MONTH_DATE " +
            " where a.MONTH_DATE >= '2016-09' and a.MONTH_DATE < DATE_FORMAT(curdate(),'%Y-%m') order by a.MONTH_DATE desc")
    List<Object[]> findMonthByUserId(String userId);

    /**
     * 根据分销商id按月查询
     * @return
     */
    @Query(nativeQuery = true, value = "select a.MONTH_DATE as MONTH_DATE,ifnull(b.REGISTER_COUNT,0) as REGISTER_COUNT,ifnull(b.QR_CODE_COUNT,0) as QR_CODE_COUNT,ifnull(b.NOTE_COUNT,0) as NOTE_COUNT from " +
            " (select DATE_FORMAT(RepDate,'%Y-%m') as MONTH_DATE from  obj_date group by DATE_FORMAT(RepDate,'%Y-%m')) as a left join " +
            " (select DATE_FORMAT(dss.CREATE_TS,'%Y-%m') as MONTH_DATE,dss.USER_ID as USER_ID,dss.DEALER_ID as DEALER_ID,count(dss.ID) as REGISTER_COUNT,sum(CASE WHEN QR_CODE_URL is null THEN 0 WHEN QR_CODE_URL='' THEN 0 ELSE 1 END) as QR_CODE_COUNT,0 as NOTE_COUNT from dealer_stats_store dss " +
            " where 1=1 and dss.DEALER_ID=?1 and dss.CREATE_TS < curdate() group by DATE_FORMAT(dss.CREATE_TS,'%Y-%m'),DEALER_ID) as b on a.MONTH_DATE=b.MONTH_DATE " +
            " where a.MONTH_DATE >= '2016-09' and a.MONTH_DATE < DATE_FORMAT(curdate(),'%Y-%m') order by a.MONTH_DATE desc")
    List<Object[]> findMonthByDealerId(String dealerId);

}
