package com.sys.nsfw.info.action;

import com.opensymphony.xwork2.ActionContext;
import com.sys.basecore.action.BaseAction;
import com.sys.basecore.service.impl.BaseServiceImpl;
import com.sys.basecore.util.QueryHelper;
import com.sys.nsfw.info.entity.Info;
import com.sys.nsfw.info.service.InfoService;
import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.ServletActionContext;
import sun.swing.StringUIClientPropertyKey;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.HttpURLConnection;
import java.net.URLDecoder;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

public class InfoAction extends BaseAction {
    @Resource
    private InfoService infoService;

    private List<Info> infoList;
    private Info info;
    private String[] privilegeIds;
    private String strTitle;

    //列表页面
    public String listUI() throws Exception{
        //加载分类集合
        ActionContext.getContext().getContextMap().put("infoTypeMap", Info.INFO_TYPE_MAP);
        QueryHelper queryHelper = new QueryHelper(Info.class, "i");
        try{
            if(info != null){
                if(StringUtils.isNotBlank(info.getTitle())){
                    info.setTitle(URLDecoder.decode(info.getTitle(),"utf-8"));
                    queryHelper.addCondition("i.title like ? ", "%"+ info.getTitle()+"%");
                }
            }
            //根据创建时间降序排序
            queryHelper.addOrderByProperty("i.createTime", QueryHelper.ORDER_BY_DESC);
//            infoList = infoService.findObjects(queryHelper);
            pageResult = infoService.getPageResult(queryHelper, getPageNo(), getPageSize());
//            infoList = infoService.findObjects();
        }catch(Exception e){
            e.printStackTrace();
            throw new Exception(e.getMessage());
        }
        return "listUI";
    }
    //跳转到新增页面
    public String addUI(){
        //加载分类集合
        ActionContext.getContext().getContextMap().put("infoTypeMap", Info.INFO_TYPE_MAP);

        //解决 查询条件覆盖的问题
        strTitle = info.getTitle();

        info = new Info();
        info.setCreateTime(new Timestamp(new Date().getTime()));
        return "addUI";
    }
    //保存新增
    public String add(){
        try{
            if(info != null){
                infoService.save(info);
            }
        }catch(Exception e){
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return "list";
    }

    //跳转到编辑页面
    public String editUI(){
        //加载分类集合
        ActionContext.getContext().getContextMap().put("infoTypeMap",Info.INFO_TYPE_MAP);
        if(info != null && info.getInfoId() != null){
            //解决 查询条件覆盖的问题
            strTitle = info.getTitle();
            info = infoService.findObjectById(info.getInfoId());
        }
        return "editUI";
    }
    //保存编辑
    public String edit(){
        try{
            if(info != null){
                infoService.update(info);
            }
        }catch(Exception e){
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return "list";
    }

    //删除
    public String delete(){

        System.out.println("............................delete()");
        //解决 查询条件覆盖的问题
        strTitle = info.getTitle();

        if(info != null && info.getInfoId() != null){
            infoService.delete(info.getInfoId());
        }
        return "list";
    }
    //批量删除
    public String deleteSelected(){

        System.out.println("............................deleteSelected()");
        //解决 查询条件覆盖的问题
        strTitle = info.getTitle();

        if(selectedRow != null){
            for (String s : selectedRow) {
                infoService.delete(s);
            }
        }
        return "list";
    }
    //异步发布信息
    public void publicInfo(){
        try{
            if(info != null){
                //更新信息状态
                Info temp = infoService.findObjectById(info.getInfoId());
                temp.setState(info.getState());
                infoService.update(temp);

                //输出更新结果
                HttpServletResponse response = ServletActionContext.getResponse();
                response.setContentType("text/html");
                ServletOutputStream outputStream = response.getOutputStream();
                outputStream.write("更新状态成功！！！！！".getBytes("utf-8"));
                outputStream.close();

            }
        }catch(Exception e){
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }



    public InfoService getInfoService() {
        return infoService;
    }

    public void setInfoService(InfoService infoService) {
        this.infoService = infoService;
    }

    public List<Info> getInfoList() {
        return infoList;
    }

    public void setInfoList(List<Info> infoList) {
        this.infoList = infoList;
    }

    public Info getInfo() {
        return info;
    }

    public void setInfo(Info info) {
        this.info = info;
    }

    public String[] getPrivilegeIds() {
        return privilegeIds;
    }

    public void setPrivilegeIds(String[] privilegeIds) {
        this.privilegeIds = privilegeIds;
    }

    public String getStrTitle() {
        return strTitle;
    }

    public void setStrTitle(String strTitle) {
        this.strTitle = strTitle;
    }
}
