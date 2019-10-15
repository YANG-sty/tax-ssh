package com.sys.nsfw.user.service;

import com.sys.basecore.exception.ServiceException;
import com.sys.basecore.service.BaseService;
import com.sys.nsfw.user.entity.User;
import com.sys.nsfw.user.entity.UserRole;

import javax.servlet.Servlet;
import javax.servlet.ServletOutputStream;
import javax.sql.rowset.serial.SerialException;
import java.io.File;
import java.io.Serializable;
import java.util.List;

public interface UserService extends BaseService<User>  {
    /*//新增
    public void save(User user);
    //更新
    public void update(User user);
    //根据 id 删除
    public void delete(Serializable id);
    //根据 id 查找
    public User findObjectById(Serializable id);
    //查找列表
    public List<User> findObjects();*/


    //导出用户列表
    public void exportExcel(List<User> userList, ServletOutputStream outputStream);
    //导入用户列表
    public void importExcel(File userExcel, String userExcelFileName);

    /**
     * 根据账号 和 用户id 查询用户
     * Create by y_zzu on 2019/10/10 on 13:55
     */
    List<User> findUserByAccountAndId(String id, String account);

    /**
     * 保存用户及对应的角色
     * Create by y_zzu on 2019/10/13 on 16:57
     */
    public void saveUserAndRole(User user, String... roleIds);

    /**
     * 更新用户 及其对应的角色信息
     * Create by y_zzu on 2019/10/13 on 17:07
     */
    public void updateUserAndRole(User user, String... roleIds);

    /**
     * 根据用户 id 获取该用户对应的所有 角色
     * Create by y_zzu on 2019/10/13 on 17:08
     */
    public List<UserRole> getUserRolesByUserId(String id);

    /**
     * 根据用户 的账号和密码 查询所有用户列表
     * Create by y_zzu on 2019/10/13 on 17:09
     */
    public List<User> findUserByAccountAndPass(String account, String password);
}
