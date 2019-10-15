package com.sys.test.dao.impl;

import com.sys.test.dao.TestDao;
import com.sys.test.entity.Person;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import java.io.Serializable;

public class TestDaoImpl extends HibernateDaoSupport implements TestDao {

    @Override
    public void save(Person person) {
        getHibernateTemplate().save(person);
        //测试在 除 只读事物外 的 其他事物 出现 错误进行回滚操作。
//        int i= 1/0;
    }

    @Override
    public Person findPerson(Serializable id) {
        //测试在 只读事物中 出现 更新操作，进行回滚
//        save(new Person("小明"));
        return getHibernateTemplate().get(Person.class,id);
    }

    @Override
    public Person finByName(String name) {
        return getHibernateTemplate().get(Person.class,name);
    }

}
