package com.sys.nsfw.home.action;

import com.sys.basecore.action.BaseAction;

public class HomeAction extends BaseAction {
    //跳转到 纳税访问系统首页
    public String frame(){
        return "frame";
    }
    //跳转到 纳税 访问系统首页-顶部
    public String top(){
        return "top";
    }
    //跳转到 纳税 访问系统首页-左边菜单
    public String left(){
        return "left";
    }

}
