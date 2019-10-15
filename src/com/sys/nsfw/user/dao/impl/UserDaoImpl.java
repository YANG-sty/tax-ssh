package com.sys.nsfw.user.dao.impl;


import com.sys.basecore.dao.impl.BaseDaoImpl;
import com.sys.basecore.util.HibernateUtils;
import com.sys.nsfw.user.dao.UserDao;
import com.sys.nsfw.user.entity.User;
import com.sys.nsfw.user.entity.UserRole;
import com.sys.test.entity.Person;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.io.Serializable;
import java.util.List;

public class UserDaoImpl extends BaseDaoImpl<User> implements UserDao {

    @Override
    public User findByAccount(String account) {

        List<User> users = getHibernateTemplate().find("from User user where user.account=?", account);
        /**
         * 当 users 数组没有值的时候，获取第一个 值会出现 空指针异常
         * 进行 异常处理，当进行到 users.get(0); 的时候，如果有值，则返回，
         * 如果没有值 则发生异常，进行异常处理，返回null。
         */
        try {
            User user = users.get(0);
            return user;
        } catch (Exception e) {
//            e.printStackTrace();
//            throw new RuntimeException(e);
            /**
             * 不进行异常的输出。
             */
            return null;
        }
    }

    /**
     * 根据用户 id 和 账号查找用户
     * Create by y_zzu on 2019/10/10 on 13:58
     *
     * @param id
     * @param account
     */
    @Override
    public List<User> findUserByAccountAndId(String id, String account) {
        String hql = "from User where account = ? ";
        if(StringUtils.isNotBlank(id)){
            hql += " and id != ?";
        }
        Query query = getSession().createQuery(hql);
        query.setParameter(0,account);
        if(StringUtils.isNotBlank(id)){
            query.setParameter(1,id);
        }

        return query.list();
    }

    /**
     * 保存用户角色
     * Create by y_zzu on 2019/10/13 on 16:25
     *
     * @param userRole
     */
    @Override
    public void saveUserRole(UserRole userRole) {
        getHibernateTemplate().save(userRole);
    }

    /**
     * 根据用户id 删除该用户的所有 用户角色
     * Create by y_zzu on 2019/10/13 on 16:26
     *
     * @param id
     */
    @Override
    public void deleteUserRoleByUserId(Serializable id) {
        Query query = getSession().createQuery("DELETE from UserRole where id.userId=?");
        query.setParameter(0,id);
        query.executeUpdate();
    }

    /**
     * 根据用户 id 获取该用户对应的所有 用户角色
     * Create by y_zzu on 2019/10/13 on 16:52
     *
     * @param id
     */
    @Override
    public List<UserRole> getUserRolesByUserId(Serializable id) {
        Query query = getSession().createQuery("from UserRole where id.userId=?");
        query.setParameter(0,id);
        return query.list();
    }

    /**
     * 根据用户的账号 和 密码 查询用户列表
     * Create by y_zzu on 2019/10/13 on 16:26
     *
     * @param account
     * @param password
     */
    @Override
    public List<User> findUserByAccountAndPass(String account, String password) {
        Query query = getSession().createQuery("from User where account=? AND password=?");
        query.setParameter(0,account);
        query.setParameter(1,password);
        return query.list();
    }


}