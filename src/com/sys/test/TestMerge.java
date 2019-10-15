package com.sys.test;


import com.sys.test.entity.Person;
import com.sys.test.service.TestService;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;


public class TestMerge {

    ClassPathXmlApplicationContext ctx;

    @Before
    public void loadCtx(){
        ctx = new ClassPathXmlApplicationContext("config/applicationContext.xml");
    }
    @Test
    public void testSpring(){
        TestService ts = (TestService) ctx.getBean("testService");
        ts.say();
    }

    @Test
    public void testHibernate() {
        SessionFactory sf = (SessionFactory)ctx.getBean("sessionFactory");

        Session session = sf.openSession();
        Transaction transaction = session.beginTransaction();
        //保存人员
        session.save(new Person("人员1-1"));
        transaction.commit();
        session.close();
    }

    @Test
    public void testServiceAndDao(){
        TestService ts = (TestService) ctx.getBean("testService");
        ts.save(new Person("rengyuan2-2"));
    }

    @Test
    public void testTransationReadOnly(){
        TestService ts = (TestService) ctx.getBean("testService");
        System.out.println(ts.findPerson("40287d816d5dec0d016d5dec0f780000").getName());
    }

    @Test
    public void testTransationRollback(){
        TestService ts = (TestService) ctx.getBean("testService");
        ts.save(new Person("test"));
    }

    @Test
    public void testfindByName(){
        TestService ts = (TestService) ctx.getBean("testService");
        System.out.println(ts.findByName("test"));

    }

}
