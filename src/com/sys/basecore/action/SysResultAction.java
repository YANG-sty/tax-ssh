package com.sys.basecore.action;

import com.opensymphony.xwork2.ActionInvocation;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.dispatcher.StrutsResultSupport;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SysResultAction extends StrutsResultSupport {
    @Override
    protected void doExecute(String s, ActionInvocation actionInvocation) throws Exception {
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpServletResponse response = ServletActionContext.getResponse();
        BaseAction action = (BaseAction) actionInvocation.getAction();
        
        // error 跳转过来后可以做其他的控制
        System.out.println("wo shi SysReultAction.............");
    }
}
