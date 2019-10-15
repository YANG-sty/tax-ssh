package com.sys.test.service;

import com.sys.test.entity.Person;

import java.io.Serializable;

public interface TestService {
    //输出
    public void say();
    //保存人员
    public void save(Person person);
    //根据 id 查询人员
    public Person findPerson (Serializable id);

    public Person findByName(String name);
}
