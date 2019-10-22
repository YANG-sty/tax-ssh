package com.sys.home.action;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.sun.net.httpserver.Authenticator;
import com.sys.basecore.constant.Constant;
import com.sys.basecore.util.QueryHelper;
import com.sys.nsfw.complain.entity.Complain;
import com.sys.nsfw.complain.service.ComplainService;
import com.sys.nsfw.info.entity.Info;
import com.sys.nsfw.info.service.InfoService;
import com.sys.nsfw.user.entity.User;
import com.sys.nsfw.user.service.UserService;
import net.sf.json.JSONObject;
import net.sf.json.processors.JsonBeanProcessor;
import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.ServletActionContext;

import javax.annotation.Resource;
import javax.servlet.Servlet;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.xml.crypto.Data;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HomeAction extends ActionSupport {
    @Resource
    private UserService userService;
    @Resource
    private ComplainService complainService;
    @Resource
    private InfoService infoService;

    private Map<String, Object> return_map;
//    private Complain comp = new Complain();
    private Complain complain;
    private Info info;


    //跳转到 系统 首页
    public String execute() {
        //加载信息列表
        //加载分类集合
        ActionContext.getContext().getContextMap().put("infoTypeMap", Info.INFO_TYPE_MAP);
        QueryHelper queryHelper1 = new QueryHelper(Info.class, "i");
        queryHelper1.addOrderByProperty("i.createTime", QueryHelper.ORDER_BY_DESC);
        List<Info> infoList = infoService.getPageResult(queryHelper1, 1, 5).getItems();
        ActionContext.getContext().getContextMap().put("infoList", infoList);

        //获得 此时登录的用户信息
        User user = (User) ServletActionContext.getRequest().getSession().getAttribute(Constant.USER);

        //加载 我的 投诉信息列表
        //加载状态集合
        ActionContext.getContext().getContextMap().put("complainStateMap", Complain.COMPLAIN_STATE_MAP);
        QueryHelper queryHelper2 = new QueryHelper(Complain.class, "c");
        //获得 投诉人 是 该用户的投诉信息
        queryHelper2.addCondition("c.compName = ?", user.getName());
        //根据投诉时间升序排序
        queryHelper2.addOrderByProperty("c.compTime", QueryHelper.ORDER_BU_ASC);
        //根据 投诉转态 降序排序，已失效，已受理，待受理
        queryHelper2.addOrderByProperty("c.state", QueryHelper.ORDER_BY_DESC);
        List<Complain> complainList = complainService.getPageResult(queryHelper2, 1,6).getItems();
        ActionContext.getContext().getContextMap().put("complainList", complainList);

        return "home";
    }

    //跳转到我要投诉
    public String complainAddUI() {
        return "complainAddUI";
    }

    // 通过部门获得 该属于该部们的人员信息，并将数组信息转化为json 格式
    public void getUserJson() {
        //获取部门
        String dept = ServletActionContext.getRequest().getParameter("department");

        try {
            if (StringUtils.isNoneBlank(dept)) {
                QueryHelper queryHelper = new QueryHelper(User.class, "u");
                queryHelper.addCondition("u.department like ? ", "%" + dept);
                //根据部门查询用户列表
                List<User> userList = userService.findObjects(queryHelper);
                //创建 json对象
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("msg", "success");
                jsonObject.accumulate("userList", userList);
                //输出用户列表以 json 格式字符串形式输出
                HttpServletResponse response = ServletActionContext.getResponse();
                response.setContentType("text/html");
                ServletOutputStream outputStream = response.getOutputStream();
                outputStream.write(jsonObject.toString().getBytes("utf-8"));
                outputStream.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getUserJson2() {
        try {
            //获取部门
            String dept = ServletActionContext.getRequest().getParameter("department");
            if (StringUtils.isNoneBlank(dept)) {
                QueryHelper queryHelper = new QueryHelper(User.class, "u");
                queryHelper.addCondition("u.department like ?", "%" + dept + "%");
                //获取部门 查询用户列表
                return_map = new HashMap<String, Object>();
                return_map.put("msg", "success");
                return_map.put("userList", userService.findObjects(queryHelper));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return SUCCESS;
    }

    //保存投诉信息
    public void complainAdd() {
        System.out.println("保存投诉信息： complainAdd 方法。。。。。。。");
        try {
            if (complain != null) {
                //设置 投诉状态默认为 待受理
                complain.setState(Complain.COMPLAIN_STATE_UNDONE);
                complain.setCompTime(new Timestamp(new Date().getTime()));
                complainService.save(complain);
                //输出投诉结果
                HttpServletResponse response = ServletActionContext.getResponse();
                response.setContentType("text/html");
                ServletOutputStream outputStream = response.getOutputStream();
                outputStream.write("success".getBytes("utf-8"));
                outputStream.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //查看信息
    public String infoViewUI(){
        //加载分类结合
        ActionContext.getContext().getContextMap().put("infoTypeMap", Info.INFO_TYPE_MAP);
        if(info != null){
            info = infoService.findObjectById(info.getInfoId());
        }
        return "infoViewUI";
    }

    //查看投诉信息
    public String complainViewUI(){
        //加载状态集合
        ActionContext.getContext().getContextMap().put("complainStateMap", Complain.COMPLAIN_STATE_MAP);
        if(complain != null){
            complain = complainService.findObjectById(complain.getCompId());
        }
        return "complainViewUI";
    }

    public Map<String, Object> getReturn_map() {
        return return_map;
    }

    public void setReturn_map(Map<String, Object> return_map) {
        this.return_map = return_map;
    }

    /*public Complain getComp() {
        return comp;
    }

    public void setCom(Complain comp) {
        this.comp = comp;
    }*/
    public Complain getComplain() {
        return complain;
    }

    public void setComplain(Complain complain) {
        this.complain = complain;
    }

    public Info getInfo() {
        return info;
    }

    public void setInfo(Info info) {
        this.info = info;
    }
}
