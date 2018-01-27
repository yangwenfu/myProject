package com.xinyunlian.jinfu.user.dto;

import com.xinyunlian.jinfu.common.dto.PagingDto;

/**
 * Created by dongfangchao on 2016/12/6/0006.
 */
public class MgtRoleSearchDto extends PagingDto<MgtRoleDto> {
    private static final long serialVersionUID = 316312760334372858L;

    private String roleName;

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
}
