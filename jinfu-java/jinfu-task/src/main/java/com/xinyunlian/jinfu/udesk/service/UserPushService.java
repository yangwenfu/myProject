package com.xinyunlian.jinfu.udesk.service;

import com.xinyunlian.jinfu.common.util.DateHelper;
import com.xinyunlian.jinfu.common.util.HttpUtil;
import com.xinyunlian.jinfu.common.util.JsonUtil;
import com.xinyunlian.jinfu.common.util.SHA1;
import com.xinyunlian.jinfu.udesk.dto.CustomerDto;
import com.xinyunlian.jinfu.udesk.dto.CustomerUpdateDto;
import com.xinyunlian.jinfu.udesk.dto.UserImportDto;
import com.xinyunlian.jinfu.udesk.dto.UserUpdateDto;
import com.xinyunlian.jinfu.user.dto.UserInfoDto;
import com.xinyunlian.jinfu.user.dto.req.UserSearchDto;
import com.xinyunlian.jinfu.user.service.UserService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.net.URLDecoder;
import java.util.*;

/**
 * Created by King on 2017/4/11.
 */
@Component
public class UserPushService {
    @Value("${udesk.email}")
    private String EMAIL;

    @Value("${udesk.password}")
    private String PASSWORD;

    @Value("${udesk.url}")
    private String URL;

    private static String TOKEN = "";

    private final static Logger LOGGER = LoggerFactory.getLogger(UserPushService.class);

    @Autowired
    private UserService userService;

    public void autoUserImport(){
        TOKEN = "";
        UserSearchDto userSearchDto = new UserSearchDto();
        userSearchDto.setIdAuthStartDate(DateHelper.formatDate(
                DateHelper.add(new Date(), Calendar.DATE, -1)));
        userSearchDto.setCurrentPage(1);
        userSearchDto.setPageSize(100);
        userSearchDto = userService.getUserPage(userSearchDto);
        LOGGER.info("Udesk import total={},totalPages={}", userSearchDto.getTotalRecord(),userSearchDto.getTotalPages());
        this.executeBatchImport(userSearchDto.getList());
        for (int i=2;i<=userSearchDto.getTotalPages();i++) {
            userSearchDto.setCurrentPage(i);
            userSearchDto = userService.getUserPage(userSearchDto);
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            this.executeBatchImport(userSearchDto.getList());
        }
    }

    public void autoUserUpdate(){
        List<UserInfoDto> userInfoDtos = new ArrayList<>();
        TOKEN = "";
        UserSearchDto userSearchDto = new UserSearchDto();
        userSearchDto.setLastMntStartDate(DateHelper.formatDate(
                DateHelper.add(new Date(), Calendar.DATE, -1)));
        userSearchDto.setCurrentPage(1);
        userSearchDto.setPageSize(10000);
        userSearchDto = userService.getUserPage(userSearchDto);
        LOGGER.info("Udesk update total={},totalPages={}", userSearchDto.getTotalRecord(),userSearchDto.getTotalPages());
        if(!CollectionUtils.isEmpty(userSearchDto.getList())) {
            userSearchDto.getList().forEach(userInfoDto -> {
                String code = this.executeUpdate(userInfoDto);
                if("2005".equals(code)){
                    userInfoDtos.add(userInfoDto);
                }
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }

        if(!CollectionUtils.isEmpty(userInfoDtos)){
            this.executeBatchImport(userSearchDto.getList());
        }
    }

    public void executeBatchImport(List<UserInfoDto> userInfoDtos){
        LOGGER.info("Udesk batchImport:");
        if(!CollectionUtils.isEmpty(userInfoDtos)) {
            LOGGER.info("will be import size = " + userInfoDtos.size());

            UserImportDto userPushDto = new UserImportDto();
            userInfoDtos.forEach(userInfoDto -> {
                CustomerDto customer = new CustomerDto();
                customer.setNick_name(userInfoDto.getUserName());
                customer.getCellphones().add(userInfoDto.getMobile());
                userPushDto.getCustomers().add(customer);
            });

            try {
                if(StringUtils.isEmpty(TOKEN)) {
                    TOKEN = this.gerAuthToken();
                    this.getAgents(TOKEN);
                }
                this.batchImport(TOKEN,userPushDto);
            } catch (Exception e) {
                LOGGER.error("Udesk batchImport error,content=" + JsonUtil.toJson(userPushDto),e);
            }
        }else{
            LOGGER.info("will be import size = 0");
        }
    }

    public String executeUpdate(UserInfoDto userInfoDto){
        LOGGER.info("Udesk update:");

        String code = "";
        UserUpdateDto userUpdateDto = new UserUpdateDto();
        CustomerUpdateDto customer = new CustomerUpdateDto();
        customer.setNick_name(userInfoDto.getUserName());
        userUpdateDto.setType("cellphone");
        userUpdateDto.setContent(userInfoDto.getMobile());
     /*   List<Long> cellPhones = new ArrayList<>(2);
        cellPhones.add(null);
        cellPhones.add(Long.valueOf(userInfoDto.getMobile()));
        customer.getCellphones().add(cellPhones);*/
        userUpdateDto.setCustomer(customer);
        try {
            if(StringUtils.isEmpty(TOKEN)) {
                TOKEN = this.gerAuthToken();
                this.getAgents(TOKEN);
            }
            code = this.updateCustomer(TOKEN,userUpdateDto);

        } catch (Exception e) {
            LOGGER.error("Udesk update error,content=" + JsonUtil.toJson(userUpdateDto),e);
        }
        return code;
    }

    /**
     * 获取token
     * @return
     * @throws
     */
    public String gerAuthToken() throws Exception {
        String result;
        Map<String,String> account = new HashMap<>();
        account.put("email",EMAIL);
        account.put("password",PASSWORD);
        result = HttpUtil.doPost(URL + "/log_in", "application/json; charset=UTF-8", JsonUtil.toJson(account).toString());
        result = URLDecoder.decode(result, "UTF-8");
        return JsonUtil.toMap(result).get("open_api_auth_token");
    }

    public String getAgents(String authToken) throws Exception {
        String timestamp = String.valueOf(System.currentTimeMillis()).toString().substring(0,10);
        String sign_str = EMAIL + "&" + authToken + "&" + timestamp;
        String queryString = "email=" + EMAIL + "&password=" + PASSWORD + "&timestamp=" + timestamp
                + "&sign=" +  new SHA1().getDigestOfString(sign_str.getBytes()).toLowerCase();

        String result = HttpUtil.doGetToString(URL + "/agents?" + queryString);
        return result;
    }

    public String batchImport(String authToken, UserImportDto userPushDto) throws Exception {
        String timestamp = String.valueOf(System.currentTimeMillis()).toString().substring(0,10);
        String sign_str = EMAIL + "&" + authToken + "&" + timestamp;
        userPushDto.setEmail(EMAIL);
        userPushDto.setTimestamp(timestamp);
        userPushDto.setSign(new SHA1().getDigestOfString(sign_str.getBytes()).toLowerCase());

        String result = HttpUtil.doPost(URL + "/customers/batch_import_async", "application/json; charset=UTF-8",
                JsonUtil.toJson(userPushDto).toString());
        result = URLDecoder.decode(result, "UTF-8");
        Map map = JsonUtil.toObject(Map.class,result);
        return map.get("code").toString();
    }

    public String updateCustomer(String authToken, UserUpdateDto userUpdateDto) throws Exception {
        String timestamp = String.valueOf(System.currentTimeMillis()).toString().substring(0,10);
        String sign_str = EMAIL + "&" + authToken + "&" + timestamp;
        userUpdateDto.setEmail(EMAIL);
        userUpdateDto.setTimestamp(timestamp);
        userUpdateDto.setSign(new SHA1().getDigestOfString(sign_str.getBytes()).toLowerCase());

        String result = HttpUtil.doPut(URL + "/customers/update_customer", "application/json; charset=UTF-8",
                JsonUtil.toJson(userUpdateDto).toString());
        result = URLDecoder.decode(result, "UTF-8");
        Map map = JsonUtil.toObject(Map.class,result);
        return map.get("code").toString();
    }
}
