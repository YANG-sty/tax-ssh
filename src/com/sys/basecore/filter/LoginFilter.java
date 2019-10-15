package com.sys.basecore.filter;

import com.sys.basecore.constant.Constant;
import com.sys.basecore.permission.PermissionCheck;
import com.sys.nsfw.user.entity.User;
import org.aspectj.apache.bcel.classfile.ConstantNameAndType;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Permission;
import java.security.PermissionCollection;

public class LoginFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String uri = request.getRequestURI();
        //判断当前请求地址是否是 登录的请求地址
        if(!uri.contains("sys/login_")){
            if(request.getSession().getAttribute(Constant.USER) != null){
                //不为空，说明登录成功
                //判断是否访问纳税服务系统
                if(uri.contains("/nsfw/")){
                    //访问纳税服务子系统
                    User user = (User) request.getSession().getAttribute(Constant.USER);
                    //获取 spring 容器
                    WebApplicationContext applicationContext =
                            WebApplicationContextUtils.getWebApplicationContext(request.getSession().getServletContext());
                    PermissionCheck pc = (PermissionCheck) applicationContext.getBean("permissionCheck");
                    if(pc.isAccessible(user, "nsfw")){
                        //有权限，放行
                        filterChain.doFilter(request,response);
                    }else{
                        //没有权限，跳转到 没有权限提示页面
                        response.sendRedirect(request.getContextPath() + "/sys/login_toNoPermissionUI.action");
                    }
                }else{
                    //不是访问，纳税服务系统，放行
                    filterChain.doFilter(request,response);
                }
            }else{
                //没有登录，跳转到登录页面
                response.sendRedirect(request.getContextPath() + "/sys/login_toLoginUI.action");
            }
        }else{
            //登录请求，直接放行
            filterChain.doFilter(request,response);
        }

    }

    @Override
    public void destroy() {

    }
}
