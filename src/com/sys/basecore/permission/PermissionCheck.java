package com.sys.basecore.permission;

import com.sys.nsfw.user.entity.User;

public interface PermissionCheck {
    /**
     * 判断用户是否有 code 对应的权限
     * Create by y_zzu on 2019/10/14 on 14:56
     */
    public boolean isAccessible(User user, String code);
}
