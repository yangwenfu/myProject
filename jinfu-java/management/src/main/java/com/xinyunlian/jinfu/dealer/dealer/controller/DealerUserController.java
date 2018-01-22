package com.xinyunlian.jinfu.dealer.dealer.controller;

import com.xinyunlian.jinfu.common.dto.ResultDto;
import com.xinyunlian.jinfu.common.dto.ResultDtoFactory;
import com.xinyunlian.jinfu.common.security.SecurityContext;
import com.xinyunlian.jinfu.common.security.authc.ESourceType;
import com.xinyunlian.jinfu.dealer.dto.DealerDto;
import com.xinyunlian.jinfu.dealer.service.DealerService;
import com.xinyunlian.jinfu.user.dto.DealerUserDto;
import com.xinyunlian.jinfu.user.dto.DealerUserPwdDto;
import com.xinyunlian.jinfu.user.enums.EDealerUserStatus;
import com.xinyunlian.jinfu.user.service.DealerUserService;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by menglei on 2016年09月05日.
 */
@RequiresPermissions({"DT_USER"})
@Controller
@RequestMapping(value = "dealer/dealerUser")
public class DealerUserController {

    @Autowired
    private DealerUserService dealerUserService;
    @Autowired
    private DealerService dealerService;

    /**
     * 查询分销员列表
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ResultDto<List<DealerUserDto>> getDealerProdList(DealerUserDto dealerUserDto) {
        if (StringUtils.isNumeric(dealerUserDto.getMobile())) {
            //是手机号
            dealerUserDto.setName(StringUtils.EMPTY);
        } else {
            dealerUserDto.setMobile(StringUtils.EMPTY);
        }
        List<DealerUserDto> dealerUserList = dealerUserService.getDealerUserList(dealerUserDto);
        return ResultDtoFactory.toAck("获取成功", dealerUserList);
    }

    /**
     * 根据id查询分销员详情
     *
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/detail", method = RequestMethod.GET)
    public ResultDto<DealerUserDto> getDetail(@RequestParam String id) {
        DealerUserDto dto = dealerUserService.getDealerUserById(id);
        return ResultDtoFactory.toAck("获取成功", dto);
    }

    /**
     * 删除分销员
     *
     * @param userId
     * @return
     */
    @RequiresPermissions({"DT_USER_DELETE"})
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    public ResultDto<Object> delete(@RequestParam String userId) {
        dealerUserService.deleteDealerUser(userId);
        SecurityContext.clearAuthcCacheByUserId(userId, ESourceType.DEALER_USER);
        return ResultDtoFactory.toAck("删除成功");
    }

    /**
     * 添加分销员
     *
     * @param dealerUserDto
     * @return
     */
    @RequiresPermissions({"DT_USER_CREATE"})
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public ResultDto<Object> add(@RequestBody DealerUserDto dealerUserDto) {
        DealerUserDto user = dealerUserService.findDealerUserByMobile(dealerUserDto.getMobile());
        if (user != null) {
            return ResultDtoFactory.toNack("该手机号已注册");
        }
        dealerUserService.createDealerUser(dealerUserDto);
        return ResultDtoFactory.toAck("添加成功");
    }

    /**
     * 修改分销员
     *
     * @param dealerUserDto
     * @return
     */
    @RequiresPermissions({"DT_USER_UPDATE"})
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public ResultDto<Object> update(@RequestBody DealerUserDto dealerUserDto) {
        dealerUserService.updateDealerUser(dealerUserDto);
        return ResultDtoFactory.toAck("修改成功");
    }

    /**
     * 设置分销员组织
     *
     * @param dealerUserDto
     * @return
     */
    @RequestMapping(value = "/updateGroup", method = RequestMethod.POST)
    @ResponseBody
    public ResultDto<Object> updateGroup(@RequestBody DealerUserDto dealerUserDto) {
        dealerUserService.updateDealerUserGroup(dealerUserDto);
        return ResultDtoFactory.toAck("修改成功");
    }

    /**
     * 重置分销员密码
     *
     * @param dealerUserPwdDto
     * @return
     */
    @RequiresPermissions({"DT_USER_RESET"})
    @ResponseBody
    @RequestMapping(value = "/resetPwd", method = RequestMethod.POST)
    public ResultDto<String> resetPwd(@RequestBody DealerUserPwdDto dealerUserPwdDto) {
        dealerUserService.updateDealerUserPwd(dealerUserPwdDto);
        return ResultDtoFactory.toAck("重置密码成功");
    }

    /**
     * 批量导入分销员
     *
     * @param file
     * @return
     */
    @RequiresPermissions({"DT_USER_IMPORT"})
    @ResponseBody
    @RequestMapping(value = "/importBatch", method = RequestMethod.POST)
    public ResultDto<String> importBatch(@RequestParam String dealerId, @RequestParam("file") MultipartFile file) {

        DealerDto dealerDto = dealerService.getDealerById(dealerId);
        if (dealerDto == null) {
            return ResultDtoFactory.toNack("分销商不存在");
        }

        List<DealerUserDto> list = new ArrayList<>();
        List<String> mobileList = new ArrayList<>();//所有手机号
        DealerUserDto dealerUserDto;

        try {
            HSSFWorkbook wb = new HSSFWorkbook(file.getInputStream());
            HSSFSheet hssfSheet = wb.getSheetAt(0);
            if (hssfSheet != null) {
                for (int rowNum = 1; rowNum <= hssfSheet.getLastRowNum(); rowNum++) {
                    HSSFRow hssfRow = hssfSheet.getRow(rowNum);
                    if (hssfRow == null) {
                        continue;
                    }
                    HSSFCell nameCell = hssfRow.getCell(0);
                    HSSFCell mobileCell = hssfRow.getCell(1);
                    if (nameCell == null || mobileCell == null) {
                        return ResultDtoFactory.toNack("姓名或手机号不能为空");
                    }
                    nameCell.setCellType(HSSFCell.CELL_TYPE_STRING);
                    mobileCell.setCellType(HSSFCell.CELL_TYPE_STRING);
                    String name = nameCell.getStringCellValue().trim();
                    String mobile = mobileCell.getStringCellValue().trim();
                    if (StringUtils.isEmpty(name) || StringUtils.isEmpty(mobile)) {
                        return ResultDtoFactory.toNack("姓名或手机号不能为空");
                    }
                    if (name.length() > 20) {
                        return ResultDtoFactory.toNack("姓名长度超过限制");
                    }
                    boolean result = mobile.matches("[0-9]+");
                    if (!result || mobile.length() != 11) {
                        return ResultDtoFactory.toNack("手机号格式不正确");
                    }
                    dealerUserDto = new DealerUserDto();
                    dealerUserDto.setName(name);
                    dealerUserDto.setMobile(mobile);
                    dealerUserDto.setDealerId(dealerId);
                    list.add(dealerUserDto);
                    mobileList.add(mobile);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResultDtoFactory.toNack("导入失败");
        }
        if (CollectionUtils.isEmpty(list)) {
            return ResultDtoFactory.toNack("没有可导入的分销员");
        } else if (list.size() > 100) {
            return ResultDtoFactory.toNack("最大导入数量100个");
        }
        //手机号是否有重复
        Map<String, Integer> mobileMap = new HashMap<>();
        for (String obj : mobileList) {
            if (mobileMap.containsKey(obj)) {
                mobileMap.put(obj, mobileMap.get(obj).intValue() + 1);
            } else {
                mobileMap.put(obj, 1);
            }
        }
        StringBuilder tempSb = new StringBuilder();
        for (Map.Entry<String, Integer> entry : mobileMap.entrySet()) {
            if (entry.getValue() > 1) {
                tempSb.append(",").append(entry.getKey());
            }
        }
        if (StringUtils.isNotEmpty(tempSb.toString())) {
            return ResultDtoFactory.toNack("上传文件存在重复手机号" + tempSb.toString().substring(1));
        }
        //查询手机号是否被使用
        List<DealerUserDto> dealerUserList = dealerUserService.findByMobiles(mobileList);
        if (!CollectionUtils.isEmpty(dealerUserList)) {
            StringBuilder sb = new StringBuilder();
            for (DealerUserDto dealerUser : dealerUserList) {
                sb.append(",").append(dealerUser.getMobile());
            }
            return ResultDtoFactory.toNack("手机号已被使用" + sb.toString().substring(1));
        }
        //批量添加
        dealerUserService.createDealerUsers(list);
        return ResultDtoFactory.toAck("导入成功");
    }

    /**
     * 分销员禁用启用
     *
     * @param userId
     * @return
     */
    @RequiresPermissions({"DT_USER_FROZEN"})
    @RequestMapping(value = "/frozen", method = RequestMethod.POST)
    @ResponseBody
    public ResultDto<Object> frozen(@RequestParam String userId) {
        dealerUserService.updateFrozen(userId);
        DealerUserDto dealerUserDto = dealerUserService.getDealerUserById(userId);
        if (dealerUserDto != null && EDealerUserStatus.FROZEN.equals(dealerUserDto.getStatus())) {
            SecurityContext.clearAuthcCacheByUserId(userId, ESourceType.DEALER_USER);
        }
        return ResultDtoFactory.toAck("删除成功");
    }

}
