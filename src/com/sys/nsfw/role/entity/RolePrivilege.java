package com.sys.nsfw.role.entity;

import java.io.Serializable;

public class RolePrivilege implements Serializable {
    /**
     * 权限 id
     * Create by y_zzu on 2019/10/11 on 10:06
     */
    //自定义一个联合组件类
    private RolePrivilegeId id;

    public RolePrivilege() {
    }

    public RolePrivilege(RolePrivilegeId id) {
        this.id = id;
    }

    public RolePrivilegeId getId() {
        return id;
    }

    public void setId(RolePrivilegeId id) {
        this.id = id;
    }
}
