package com.sys.login.action;

import com.opensymphony.xwork2.ActionSupport;
import com.sys.basecore.constant.Constant;
import com.sys.nsfw.user.entity.User;
import com.sys.nsfw.user.service.UserService;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;

import javax.annotation.Resource;
import java.util.List;

public class LoginAction extends ActionSupport {
    @Resource
    private UserService userService;

    private User user;
    private String loginResult;
    //跳转到登录页面
    public String toLoginUI(){
        return "loginUI";
    }
    //登录
    public String login(){
        if(user != null){
            if(StringUtils.isNotBlank(user.getAccount()) && StringUtils.isNotBlank(user.getPassword())){
                //根据用户的账号 和 密码查询用户列表
                List<User> list = userService.findUserByAccountAndPass(user.getAccount(), user.getPassword());
                if(list != null && list.size()>0){
                    /**
                     * 根据账号和密码查到，用户的信息，登录成功
                     */
                    //登录成功
                    User user = list.get(0);
                    //根据用户 id 查询该用户的所有角色
                    user.setUserRoles(userService.getUserRolesByUserId(user.getId()));
                    //将 用户信息保存到session 中
                    ServletActionContext.getRequest().getSession().setAttribute(Constant.USER, user);

                    //将用户登录记录到日志文件
                    Log log = LogFactory.getLog(getClass());
                    log.info("用户名称为： "+user.getName()+" 登录了系统。。。。。。。。。。。。");
                    //重定向跳转到首页
                    return "home";
                }else{
                    /**
                     * 没有查到 用户名 和 密码的信息
                     */
                    loginResult = "账户名 或 密码 不正确！！！";
                }
            }else {
                // 账号 和 密码 有为空的
                loginResult = "账号名 或 密码 不能为空！！！";
            }
        }else {
            // 账号和密码 没有进行输入
            loginResult = "请输入 账号 和 密码！！！！";
        }
        return toLoginUI();
    }

    //退出，注销
    public String logout(){
        //清除 session 中保存的用户信息
        ServletActionContext.getRequest().getSession().removeAttribute(Constant.USER);
        return toLoginUI();
    }

    //跳转到没有权限提示页面
    public String toNoPermissionUI(){
        return "noPermissionUI";
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getLoginResult() {
        return loginResult;
    }

    public void setLoginResult(String loginResult) {
        this.loginResult = loginResult;
    }
}
