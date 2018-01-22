package com.xinyunlian.jinfu.dealer.dealer.controller;

import com.xinyunlian.jinfu.common.dto.ResultDto;
import com.xinyunlian.jinfu.common.dto.ResultDtoFactory;
import com.xinyunlian.jinfu.common.util.excel.DataTablesExcelService;
import com.xinyunlian.jinfu.report.dealer.dto.DealerLogDto;
import com.xinyunlian.jinfu.report.dealer.dto.DealerLogSearchDto;
import com.xinyunlian.jinfu.dealer.dto.DealerUserLogDto;
import com.xinyunlian.jinfu.dealer.dto.DealerUserLogSearchDto;
import com.xinyunlian.jinfu.report.dealer.service.DealerReportService;
import com.xinyunlian.jinfu.dealer.service.DealerUserLogService;
import com.xinyunlian.jinfu.store.dto.StoreInfDto;
import com.xinyunlian.jinfu.store.service.StoreService;
import com.xinyunlian.jinfu.user.dto.UserInfoDto;
import com.xinyunlian.jinfu.user.service.UserService;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by menglei on 2016年09月26日.
 */
@Controller
@RequestMapping(value = "dealer/userLog")
@RequiresPermissions({"DA_LOG"})
public class DealerUserLogController {

    @Autowired
    private DealerUserLogService dealerUserLogService;
    @Autowired
    private StoreService storeService;
    @Autowired
    private UserService userService;

    @Autowired
    private DealerReportService dealerReportService;

    @RequestMapping(value = "/export", method = RequestMethod.GET)
    public ModelAndView exportLog(DealerLogSearchDto searchDto){
        List<DealerLogDto> data = dealerReportService.getLogReport(searchDto);
        Map<String, Object> model = new HashedMap();
        model.put("data", data);
        model.put("fileName","日志记录.xls");
        model.put("tempPath","/templates/日志记录.xls");
        DataTablesExcelService dataTablesExcelService = new DataTablesExcelService();
        return new ModelAndView(dataTablesExcelService, model);
    }


    /**
     * 分页查询日志
     *
     * @param dealerUserLogSearchDto
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ResultDto<DealerUserLogSearchDto> getFeedbackPage(DealerUserLogSearchDto dealerUserLogSearchDto) {
        DealerUserLogSearchDto page = dealerUserLogService.getUserLogPage(dealerUserLogSearchDto);
        List<String> storeUserIds = new ArrayList<>();
        List<Long> storeIds = new ArrayList<>();
        for (DealerUserLogDto dealerUserLogDto : page.getList()) {
            if (StringUtils.isNotEmpty(dealerUserLogDto.getStoreUserId())) {
                storeUserIds.add(dealerUserLogDto.getStoreUserId());
            }
            if (StringUtils.isNotEmpty(dealerUserLogDto.getStoreId())) {
                storeIds.add(Long.valueOf(dealerUserLogDto.getStoreId()));
            }
        }
        
        List<UserInfoDto> userList = new ArrayList<>();
        List<StoreInfDto> storeList = new ArrayList<>();
        if (storeUserIds.size() > 0) {
            userList = userService.findUserByUserId(storeUserIds);
        }
        if (storeIds.size() > 0) {
            storeList = storeService.findByStoreIds(storeIds);
        }

        Map<String, UserInfoDto> userMap = new HashMap<>();
        Map<String, StoreInfDto> storeMap = new HashMap<>();
        for (UserInfoDto userInfoDto : userList) {
            userMap.put(userInfoDto.getUserId(), userInfoDto);
        }
        for (StoreInfDto storeInfDto : storeList) {
            storeMap.put(storeInfDto.getStoreId() + StringUtils.EMPTY, storeInfDto);
        }

        List<DealerUserLogDto> list = new ArrayList<>();
        for (DealerUserLogDto dealerUserLogDto : page.getList()) {
            if (userMap.get(dealerUserLogDto.getStoreUserId()) != null) {
                dealerUserLogDto.setStoreUserName(userMap.get(dealerUserLogDto.getStoreUserId()).getUserName());
            }
            if (storeMap.get(dealerUserLogDto.getStoreId()) != null) {
                dealerUserLogDto.setStoreName(storeMap.get(dealerUserLogDto.getStoreId()).getStoreName());
            }
            list.add(dealerUserLogDto);
        }

        page.setList(list);
        return ResultDtoFactory.toAck("获取成功", page);
    }
}
