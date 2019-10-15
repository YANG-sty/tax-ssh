package com.sys.basecore.permission.impl;

import com.sys.basecore.permission.PermissionCheck;
import com.sys.nsfw.role.entity.Role;
import com.sys.nsfw.role.entity.RolePrivilege;
import com.sys.nsfw.user.entity.User;
import com.sys.nsfw.user.entity.UserRole;
import com.sys.nsfw.user.service.UserService;

import javax.annotation.Resource;
import java.security.acl.Permission;
import java.util.List;

public class PermissionCheckImpl implements PermissionCheck {

    @Resource
    private UserService userService;

    /**
     * 判断用户是否有 code 对应的权限
     * Create by y_zzu on 2019/10/14 on 14:56
     *
     * @param user
     * @param code
     */
    @Override
    public boolean isAccessible(User user, String code) {
        //获取用户所有角色
        List<UserRole> list = user.getUserRoles();
        if(list == null){
            list = userService.getUserRolesByUserId(user.getId());
        }
        //根据每个角色 对应的所有权限进行对比
        if(list != null && list.size()>0){
            for (UserRole userRole : list) {
                Role role = userRole.getId().getRole();
                for (RolePrivilege rolePrivilege : role.getRolePrivileges()) {
                    //对比是否有 code 对应的权限
                    if(code.equals(rolePrivilege.getId().getCode())){
                        //说明 有权限，返回 true
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
