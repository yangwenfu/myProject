package com.xinyunlian.jinfu.user.controller;

import com.xinyunlian.jinfu.common.dto.ResultDto;
import com.xinyunlian.jinfu.common.dto.ResultDtoFactory;
import com.xinyunlian.jinfu.user.dto.MgtDeptDto;
import com.xinyunlian.jinfu.user.dto.MgtUserDto;
import com.xinyunlian.jinfu.user.service.MgtDeptService;
import com.xinyunlian.jinfu.user.service.MgtUserService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by dongfangchao on 2016/12/6/0006.
 */
@RestController
@RequestMapping("mgtDept")
@RequiresPermissions({"DEPT_EMP"})
public class MgtDeptController {
    private static final Logger LOGGER = LoggerFactory.getLogger(MgtDeptController.class);

    @Autowired
    private MgtUserService mgtUserService;

    @Autowired
    private MgtDeptService mgtDeptService;

    /**
     * 获取部门树
     * @return
     */
    @RequestMapping(value = "/getDeptTree", method = RequestMethod.GET)
    public ResultDto<Object> getDeptTree(){
        MgtDeptDto root = mgtDeptService.getDeptTree();
        return ResultDtoFactory.toAck("查询成功", root);
    }

    /**
     * 获取单个部门信息
     * @param deptId
     * @return
     */
    @RequestMapping(value = "/{deptId}", method = RequestMethod.GET)
    public ResultDto<Object> getDept(Long deptId){
        MgtDeptDto dept = mgtDeptService.getDeptById(deptId);
        return ResultDtoFactory.toAck("查询成功", dept);
    }

    /**
     * 删除部门，包括子部门
     * @param deptId
     * @return
     */
    @RequestMapping(value = "/{deptId}", method = RequestMethod.DELETE)
    public ResultDto<Object> deleteDept(Long deptId){
        List<MgtUserDto> userDtos = mgtUserService.getUserByDept(deptId);
        if (!CollectionUtils.isEmpty(userDtos)){
            return ResultDtoFactory.toNack("当前部门下有员工，不能删除!");
        }else{
            mgtDeptService.deleteDeptById(deptId);
        }

        return ResultDtoFactory.toAck("删除成功");
    }

    /**
     * 新增部门
     * @param deptDto
     * @return
     */
    @RequestMapping(value = "/addDept", method = RequestMethod.POST)
    public ResultDto<Object> addDept(MgtDeptDto deptDto){
        mgtDeptService.addDept(deptDto);
        return ResultDtoFactory.toAck("添加部门成功");
    }

    /**
     * 修改部门名称
     * @param deptDto
     * @return
     */
    @RequestMapping(value = "/updateDeptName", method = RequestMethod.POST)
    public ResultDto<Object> modifyDeptName(MgtDeptDto deptDto){
        mgtDeptService.updateDeptName(deptDto);
        return ResultDtoFactory.toAck("更新成功");
    }

}
