package com.sys.test.action;

import com.opensymphony.xwork2.ActionSupport;
import com.sys.test.service.TestService;


import javax.annotation.Resource;

public class TestAction extends ActionSupport {
    @Resource
    TestService testService;

    public String execute(){
        testService.say();
        return SUCCESS;
    }
}
