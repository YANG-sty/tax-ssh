package com.sys.home.action;

import com.opensymphony.xwork2.ActionSupport;

public class HomeAction extends ActionSupport {
    //跳转到 系统 首页
    public String execute(){
        return "home";
    }
}
