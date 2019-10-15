package com.sys.nsfw.info.action;

import com.opensymphony.xwork2.ActionContext;
import com.sys.basecore.action.BaseAction;
import com.sys.basecore.service.impl.BaseServiceImpl;
import com.sys.nsfw.info.entity.Info;
import com.sys.nsfw.info.service.InfoService;
import org.apache.struts2.ServletActionContext;
import sun.swing.StringUIClientPropertyKey;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.HttpURLConnection;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

public class InfoAction extends BaseAction {
    @Resource
    private InfoService infoService;

    private List<Info> infoList;
    private Info info;
    private String[] privilegeIds;

    //列表页面
    public String listUI() throws Exception{
        //加载分类集合
        ActionContext.getContext().getContextMap().put("infoTypeMap", Info.INFO_TYPE_MAP);
        try{
            infoList = infoService.findObjects();
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
        if(info != null && info.getInfoId() != null){
            infoService.delete(info.getInfoId());
        }
        return "list";
    }
    //批量删除
    public String deleteSelected(){
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
}
