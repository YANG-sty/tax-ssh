package com.sys.nsfw.user.dao;

import com.sys.basecore.dao.BaseDao;
import com.sys.nsfw.user.entity.User;
import com.sys.nsfw.user.entity.UserRole;

import java.io.Serializable;
import java.util.List;

public interface UserDao extends BaseDao<User> {
    /**
     * 根据 账号 查找
     * */
    public User findByAccount(String account);

    /**
     * 根据用户 id 和 账号查找用户
     * Create by y_zzu on 2019/10/10 on 13:58
     */
    List<User> findUserByAccountAndId(String id, String account);

    /**
     * 保存用户角色
     * Create by y_zzu on 2019/10/13 on 16:25
     */
    public void saveUserRole(UserRole userRole);

    /**
     * 根据用户id 删除该用户的所有 用户角色
     * Create by y_zzu on 2019/10/13 on 16:26
     */
    public void deleteUserRoleByUserId(Serializable id);

    /**
     * 根据用户 id 获取该用户对应的所有 用户角色
     * Create by y_zzu on 2019/10/13 on 16:52
     */
    public List<UserRole> getUserRolesByUserId(Serializable id);
    /**
     * 根据用户的账号 和 密码 查询用户列表
     * Create by y_zzu on 2019/10/13 on 16:26
     */
    public List<User> findUserByAccountAndPass(String account, String password);
}
