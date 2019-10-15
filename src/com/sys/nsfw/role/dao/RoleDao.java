package com.sys.nsfw.role.dao;

import com.sys.basecore.dao.BaseDao;
import com.sys.nsfw.role.entity.Role;

public interface RoleDao extends BaseDao<Role> {
    /**
     * 删除该 角色对于的所有权限
     * Create by y_zzu on 2019/10/11 on 10:36
     */
    public void deleteRolePrivilegeByRoleId(String roleId);
}
