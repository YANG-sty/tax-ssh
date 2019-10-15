package com.sys.nsfw.role.dao.impl;

import com.sys.basecore.dao.impl.BaseDaoImpl;
import com.sys.nsfw.role.dao.RoleDao;
import com.sys.nsfw.role.entity.Role;
import org.hibernate.Query;

public class RoleDaoImpl extends BaseDaoImpl<Role> implements RoleDao {

    /**
     * 删除该 角色对于的所有权限
     * Create by y_zzu on 2019/10/11 on 10:36
     *
     * @param roleId
     */
    @Override
    public void deleteRolePrivilegeByRoleId(String roleId) {
        Query query = getSession().createQuery("delete from RolePrivilege where id.role.roleId=?");
        query.setParameter(0,roleId);
        query.executeUpdate();
    }





}
