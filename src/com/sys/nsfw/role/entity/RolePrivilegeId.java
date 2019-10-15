package com.sys.nsfw.role.entity;

import java.io.Serializable;
import java.util.Objects;

/**
 * 联合主键：
 * 1.实现 Serializable
 * 2.重写 hashCode，equals 方法
 */
public class RolePrivilegeId implements Serializable {
    /**
     * 权限id 和 Role 实体类有关。
     * Create by y_zzu on 2019/10/11 on 10:06
     */
    private Role role;
    private String code;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RolePrivilegeId that = (RolePrivilegeId) o;
        return role.equals(that.role) &&
                code.equals(that.code);
    }

    @Override
    public int hashCode() {
        return Objects.hash(role, code);
    }

    public RolePrivilegeId() {
    }

    public RolePrivilegeId(Role role, String code) {
        this.role = role;
        this.code = code;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
