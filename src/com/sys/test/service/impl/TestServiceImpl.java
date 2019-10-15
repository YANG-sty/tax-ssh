package com.sys.test.service.impl;


import com.sys.test.dao.TestDao;
import org.springframework.stereotype.Service;
import com.sys.test.entity.Person;
import com.sys.test.service.TestService;

import javax.annotation.Resource;
import java.io.Serializable;

@Service("testService")
public class TestServiceImpl implements TestService {

    @Resource
    TestDao testDao;

    @Override
    public void say() {
        System.out.println("service saying hi.");
    }

    @Override
    public void save(Person person) {
        testDao.save(person);
    }

    @Override
    public Person findPerson(Serializable id) {
        return testDao.findPerson(id);
    }

    @Override
    public Person findByName(String name) {
        return testDao.finByName(name);
    }
}
