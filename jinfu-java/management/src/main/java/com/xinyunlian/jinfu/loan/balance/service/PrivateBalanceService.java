package com.xinyunlian.jinfu.loan.balance.service;

import com.xinyunlian.jinfu.balance.dto.BalanceDetailDto;
import com.xinyunlian.jinfu.balance.dto.BalanceDetailListDto;
import com.xinyunlian.jinfu.balance.dto.BalanceOutlineDto;
import com.xinyunlian.jinfu.user.dto.MgtUserDto;
import com.xinyunlian.jinfu.user.dto.UserInfoDto;
import com.xinyunlian.jinfu.user.service.MgtUserService;
import com.xinyunlian.jinfu.user.service.UserService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.*;

/**
 * Created by Administrator on 2017/6/1 0001.
 */
@Component
public class PrivateBalanceService {

    @Autowired
    private MgtUserService mgtUserService;

    @Autowired
    private UserService userService;

    public List<BalanceOutlineDto> completeMgtUserName(List<BalanceOutlineDto> outlines) {
        Set<String> userIds = (Set<String>) this.extraSet(outlines, "balanceUserId");
        List<MgtUserDto> users = mgtUserService.findByMgtUserIds(userIds);
        Map<String, MgtUserDto> userMap = new HashMap<>();
        users.forEach(user -> userMap.put(user.getUserId(), user));

        outlines.forEach(outline ->
                outline.setBalanceUserName(
                        userMap.get(outline.getBalanceUserId()) != null ? userMap.get(outline.getBalanceUserId()).getName() : ""));


        return outlines;
    }

    public BalanceDetailListDto completeUserName(BalanceDetailListDto balanceDetailListDto){
        Set<String> userIds = (Set<String>) this.extraSet(balanceDetailListDto.getLoans(), "userId");
        List<UserInfoDto> users;
        if(userIds.isEmpty()){
            users = new ArrayList<>();
        }else{
            users = userService.findUserByUserId(userIds);
        }
        Map<String, UserInfoDto> userMap = new HashMap<>();
        users.forEach(user -> userMap.put(user.getUserId(), user));

        balanceDetailListDto.getLoans().forEach(loan -> {
            String userName = userMap.get(loan.getUserId()) != null ? userMap.get(loan.getUserId()).getUserName() : "";
            loan.setUserName(userName);
        });

        return balanceDetailListDto;
    }

    public BalanceDetailDto completeUserName(BalanceDetailDto balanceDetailDto){
        String userId = balanceDetailDto.getLoan().getUserId();
        UserInfoDto userInfoDto = userService.findUserByUserId(userId);
        if(userInfoDto != null){
            balanceDetailDto.getLoan().setUserName(userInfoDto.getUserName());
        }

        return balanceDetailDto;
    }

    private Set<?> extraSet(Collection<?> list, String fieldName){
        return new HashSet<>(this.extra(list, fieldName));
    }

    private List<?> extra(Collection<?> list, String fieldName) {
        List<Object> rs = new ArrayList<>();
        Iterator iter = list.iterator();
        if (!iter.hasNext() || fieldName.isEmpty() || list.isEmpty()) {
            return rs;
        }

        Field[] fields = iter.next().getClass().getDeclaredFields();

        list.forEach(item -> {
            for (int i = 0; i < fields.length; i++) {
                if (fieldName.equals(fields[i].getName())) {
                    try {
                        if (!fields[i].isAccessible()) {
                            fields[i].setAccessible(true);
                        }

                        if(fields[i].get(item) == null){
                            continue;
                        }
                        rs.add(fields[i].get(item));
                    } catch (IllegalAccessException e) {
                    }
                }
            }
        });

        return rs;
    }

}
