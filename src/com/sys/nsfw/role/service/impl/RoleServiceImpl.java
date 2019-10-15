package com.sys.nsfw.role.service.impl;

import com.sys.basecore.service.impl.BaseServiceImpl;
import com.sys.nsfw.role.dao.RoleDao;
import com.sys.nsfw.role.entity.Role;
import com.sys.nsfw.role.service.RoleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;

@Service("roleService")
public class RoleServiceImpl extends BaseServiceImpl<Role> implements RoleService {

    private RoleDao roleDao;

    @Resource
    public void setRoleDao(RoleDao roleDao) {
        super.setBaseDao(roleDao);
        this.roleDao = roleDao;
    }

    /**
     * 新增
     * Create by y_zzu on 2019/10/11 on 10:42
     *
     * @param role
     */
    /*@Override
    public void save(Role role) {
        roleDao.save(role);
    }*/

    /**
     * 更新
     * Create by y_zzu on 2019/10/11 on 10:43
     *
     * @param role
     */
   /* @Override
    public void update(Role role) {
        //删除 该角色对应的 所有权限
        roleDao.deleteRolePrivilegeByRoleId(role.getRoleId());
        //更新 角色 及其 权限
        roleDao.update(role);

    }*/

    /**
     * 根据 id 删除
     * Create by y_zzu on 2019/10/11 on 10:43
     *
     * @param id
     */
   /* @Override
    public void delete(Serializable id) {
        roleDao.delete(id);
    }*/

    /**
     * 根据 id 查找
     * Create by y_zzu on 2019/10/11 on 10:43
     *
     * @param id
     */
    /*@Override
    public Role findObjectById(Serializable id) {
        return roleDao.findObjectById(id);
    }*/

    /**
     * 查找列表
     * Create by y_zzu on 2019/10/11 on 10:44
     */
    /*@Override
    public List<Role> findObjects() {
        return roleDao.findObjects();
    }*/
}
