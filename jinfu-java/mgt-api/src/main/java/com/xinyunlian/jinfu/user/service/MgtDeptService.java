package com.xinyunlian.jinfu.user.service;

import com.xinyunlian.jinfu.common.exception.BizServiceException;
import com.xinyunlian.jinfu.user.dto.MgtDeptDto;

import java.util.List;

/**
 * Created by dongfangchao on 2016/12/6/0006.
 */
public interface MgtDeptService {

    /**
     * 获取所有部门列表
     * @return
     */
    List<MgtDeptDto> getDeptList() throws BizServiceException;

    /**
     * 获取所有部门树结构
     * @return
     */
    MgtDeptDto getDeptTree() throws BizServiceException;

    /**
     * 根据Id，查询部门信息
     * @param deptId
     * @return
     */
    MgtDeptDto getDeptById(Long deptId) throws BizServiceException;

    /**
     * 新增部门
     * @param dept
     * @return
     */
    MgtDeptDto addDept(MgtDeptDto dept) throws BizServiceException;

    /**
     * 删除部门，不删除子部门
     * @param deptId
     */
    void deleteDirectDeptById(Long deptId) throws BizServiceException;

    /**
     * 删除部门，包括子部门也删除
     * @param deptId
     * @throws BizServiceException
     */
    void deleteDeptById(Long deptId) throws BizServiceException;

    /**
     * 修改部门名称
     * @param deptDto
     * @throws BizServiceException
     */
    void updateDeptName(MgtDeptDto deptDto) throws BizServiceException;

}
