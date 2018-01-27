package com.xinyunlian.jinfu.user.service;

import com.xinyunlian.jinfu.common.converter.ConverterService;
import com.xinyunlian.jinfu.common.enums.EErrorCode;
import com.xinyunlian.jinfu.common.exception.BizServiceException;
import com.xinyunlian.jinfu.common.util.BizUtil;
import com.xinyunlian.jinfu.user.dao.MgtPermissionDao;
import com.xinyunlian.jinfu.user.dao.MgtRoleDao;
import com.xinyunlian.jinfu.user.dao.MgtRolePermDao;
import com.xinyunlian.jinfu.user.dao.MgtUserRoleDao;
import com.xinyunlian.jinfu.user.dto.MgtPermissionDto;
import com.xinyunlian.jinfu.user.dto.MgtRoleDto;
import com.xinyunlian.jinfu.user.dto.MgtRoleSearchDto;
import com.xinyunlian.jinfu.user.entity.MgtPermissionPo;
import com.xinyunlian.jinfu.user.entity.MgtRolePermPo;
import com.xinyunlian.jinfu.user.entity.MgtRolePo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by dongfangchao on 2016/12/6/0006.
 */
@Service
public class MgtAuthorityServiceImpl implements MgtAuthorityService{

    private static final Logger LOGGER = LoggerFactory.getLogger(MgtAuthorityServiceImpl.class);

    @Autowired
    private MgtRoleDao mgtRoleDao;

    @Autowired
    private MgtRolePermDao mgtRolePermDao;

    @Autowired
    private MgtUserRoleDao mgtUserRoleDao;

    @Autowired
    private MgtPermissionDao mgtPermissionDao;

    @Override
    public MgtRoleSearchDto getRolePage(MgtRoleSearchDto searchDto) throws BizServiceException {

        Specification<MgtRolePo> spec = (root, query, cb) -> {
            Predicate predicate = cb.conjunction();
            List<Expression<Boolean>> expressions = predicate.getExpressions();
            if (searchDto != null) {
                if (!StringUtils.isEmpty(searchDto.getRoleName())) {
                    expressions.add(cb.like(root.get("roleName"), BizUtil.filterString(searchDto.getRoleName())));
                }
            }
            return predicate;
        };

        Pageable pageable = new PageRequest(searchDto.getCurrentPage() - 1, searchDto.getPageSize());
        Page<MgtRolePo> page = mgtRoleDao.findAll(spec, pageable);

        List<MgtRoleDto> data = new ArrayList<>();
        page.getContent().forEach(po -> {
            MgtRoleDto mgtRoleDto = ConverterService.convert(po, MgtRoleDto.class);
            data.add(mgtRoleDto);
        });

        searchDto.setList(data);
        searchDto.setTotalPages(page.getTotalPages());
        searchDto.setTotalRecord(page.getTotalElements());
        return searchDto;
    }

    @Override
    @Transactional
    public void deleteRoleByRoleId(Long roleId) throws BizServiceException {
        try {
            mgtRolePermDao.deleteByRoleId(roleId);
            mgtUserRoleDao.deleteByRoleId(roleId);
            mgtRoleDao.delete(roleId);
        } catch (Exception e) {
            LOGGER.error("删除角色异常", e);
            throw new BizServiceException(EErrorCode.MGT_ROLE_DELETE_FAILED);
        }
    }

    @Override
    public List<MgtPermissionDto> getPermList() throws BizServiceException {
        List<MgtPermissionDto> retList = new ArrayList<>();

        Sort sort = new Sort(Sort.Direction.ASC, "permOrder");
        List<MgtPermissionPo> poList = mgtPermissionDao.findAll(sort);
        if (!CollectionUtils.isEmpty(poList)){
            poList.forEach( po -> {
                MgtPermissionDto dto = ConverterService.convert(po, MgtPermissionDto.class);
                retList.add(dto);
            });
        }

        return retList;
    }

    @Override
    public MgtPermissionDto getPermTree() throws BizServiceException {
        List<MgtPermissionDto> perms = getPermList();
        if (!CollectionUtils.isEmpty(perms)){

            MgtPermissionDto root = new MgtPermissionDto();
            for (MgtPermissionDto perm : perms) {
                if (-1 == perm.getPermParent()){
                    root = perm;
                    perms.remove(perm);
                    break;
                }
            }

            root.getChildPerms(perms);
            return root;
        }

        return null;
    }

    @Override
    @Transactional
    public void addRole(MgtRoleDto roleDto) throws BizServiceException {

        try {
            MgtRolePo dbRole = mgtRoleDao.findByRoleCode(roleDto.getRoleCode());
            if (dbRole != null){
                throw new BizServiceException(EErrorCode.MGT_ROLE_CODE_EXISTS);
            }

            MgtRolePo rolePo = ConverterService.convert(roleDto, MgtRolePo.class);
            mgtRoleDao.save(rolePo);

            if (!CollectionUtils.isEmpty(roleDto.getMgtPermissionDtoList())){
                List<MgtRolePermPo> rpPoList = new ArrayList<>();
                roleDto.getMgtPermissionDtoList().forEach( perm -> {
                    MgtRolePermPo rp = new MgtRolePermPo();
                    rp.setRoleId(rolePo.getRoleId());
                    rp.setPermissionId(perm.getPermissionId());
                    rpPoList.add(rp);
                });
                mgtRolePermDao.save(rpPoList);
            }

        } catch (BizServiceException e) {
            LOGGER.error("添加角色异常", e);
            throw e;
        }catch (Exception e){
            LOGGER.error("添加角色异常", e);
            throw new BizServiceException(EErrorCode.MGT_ROLE_ADD_FAILED);
        }
    }

    @Override
    public List<MgtRoleDto> getRoleList() throws BizServiceException {
        List<MgtRoleDto> retList = new ArrayList<>();

        List<MgtRolePo> list = mgtRoleDao.findAll();
        if (!CollectionUtils.isEmpty(list)){
            list.forEach(po -> {
                MgtRoleDto dto = ConverterService.convert(po, MgtRoleDto.class);
                retList.add(dto);
            });
        }
        return retList;
    }

    @Override
    @Transactional
    public void updateRole(MgtRoleDto roleDto) throws BizServiceException {

        try {
            MgtRolePo rolePo = mgtRoleDao.findOne(roleDto.getRoleId());

            if (!rolePo.getRoleCode().equals(roleDto.getRoleCode())){
                MgtRolePo dbRole = mgtRoleDao.findByRoleCode(roleDto.getRoleCode());
                if (dbRole != null){
                    throw new BizServiceException(EErrorCode.MGT_ROLE_CODE_EXISTS);
                }
            }

            rolePo.setRoleName(roleDto.getRoleName());
            rolePo.setRoleCode(roleDto.getRoleCode());
            rolePo.setRoleDesc(roleDto.getRoleDesc());

            mgtRolePermDao.deleteByRoleId(roleDto.getRoleId());

            if (!CollectionUtils.isEmpty(roleDto.getMgtPermissionDtoList())){
                List<MgtRolePermPo> rpPoList = new ArrayList<>();
                roleDto.getMgtPermissionDtoList().forEach( perm -> {
                    MgtRolePermPo rp = new MgtRolePermPo();
                    rp.setRoleId(rolePo.getRoleId());
                    rp.setPermissionId(perm.getPermissionId());
                    rpPoList.add(rp);
                });
                mgtRolePermDao.save(rpPoList);
            }

        } catch (BizServiceException e) {
            LOGGER.error("更新角色异常", e);
            throw e;
        } catch (Exception e){
            LOGGER.error("更新角色异常", e);
            throw new BizServiceException(EErrorCode.MGT_ROLE_UPDATE_FAILED);
        }
    }

    @Override
    public MgtRoleDto getRoleDetail(Long roleId) throws BizServiceException {
        MgtRolePo rolePo = mgtRoleDao.findOne(roleId);
        MgtRoleDto roleDto = ConverterService.convert(rolePo, MgtRoleDto.class);

        if (roleDto != null){
            List<MgtPermissionPo> perms = mgtPermissionDao.findPermsByRoleId(roleId);
            if (!CollectionUtils.isEmpty(perms)){
                List<MgtPermissionDto> retList = new ArrayList<>();
                perms.forEach(po -> {
                    MgtPermissionDto dto = ConverterService.convert(po, MgtPermissionDto.class);
                    retList.add(dto);
                });
                roleDto.setMgtPermissionDtoList(retList);
            }
        }
        return roleDto;
    }
}
