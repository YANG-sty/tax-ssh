package com.sys.nsfw.role.entity;

import java.io.Serializable;
import java.util.Set;

public class Role implements Serializable {
    private String roleId;
    private String name;
    private String state;

    /**
     * 角色权限
     * Create by y_zzu on 2019/10/11 on 10:05
     */
    private Set<RolePrivilege> rolePrivileges;
    /**
     * 角色状态
     * Create by y_zzu on 2019/10/11 on 10:07
     */
    private static String ROLE_STATE_VALID = "1";// 有效
    private static String ROLE_STATE_INVALID = "0";// 无效

    public Role() {
    }

    public Role(String roleId) {
        this.roleId = roleId;
    }

    public Role(String roleId, String name, String state, Set<RolePrivilege> rolePrivileges) {
        this.roleId = roleId;
        this.name = name;
        this.state = state;
        this.rolePrivileges = rolePrivileges;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }
    public String getRoleId() {
        return roleId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Set<RolePrivilege> getRolePrivileges() {
        return rolePrivileges;
    }

    public void setRolePrivileges(Set<RolePrivilege> rolePrivileges) {
        this.rolePrivileges = rolePrivileges;
    }

    public static String getRoleStateValid() {
        return ROLE_STATE_VALID;
    }

    public static void setRoleStateValid(String roleStateValid) {
        ROLE_STATE_VALID = roleStateValid;
    }

    public static String getRoleStateInvalid() {
        return ROLE_STATE_INVALID;
    }

    public static void setRoleStateInvalid(String roleStateInvalid) {
        ROLE_STATE_INVALID = roleStateInvalid;
    }
}
