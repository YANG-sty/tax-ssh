package com.sys.test.dao;

import com.sys.test.entity.Person;

import java.io.Serializable;

public interface TestDao {
    //保存人员
    public void save(Person person);

    //根据id 查询人员
    public Person findPerson(Serializable id);

    public Person finByName(String name);
}
