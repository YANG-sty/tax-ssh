package com.sys.nsfw.user.action;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.sys.basecore.action.BaseAction;
import com.sys.basecore.exception.ActionException;
import com.sys.basecore.exception.ServiceException;
import com.sys.basecore.exception.SysException;
import com.sys.nsfw.role.service.RoleService;
import com.sys.nsfw.user.entity.User;
import com.sys.nsfw.user.entity.UserRole;
import com.sys.nsfw.user.service.UserService;
import org.apache.commons.io.FileUtils;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.struts2.ServletActionContext;
import org.aspectj.util.FileUtil;
import org.hibernate.envers.RevisionEntity;
import org.junit.Test;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.sql.rowset.serial.SerialException;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

public class UserAction extends BaseAction {

    @Resource
    private UserService userService;
    @Resource
    private RoleService roleService;

    private List<User> userList;
    private User user;

    private File headImg;
    private String headImgContentType;
    private String headImgFileName;

    private File userExcel;
    private String userExcelContentType;
    private String userExcelFileName;

    private String[] userRoleIds;


    //列表页面
    public String listUI() throws Exception{
        try {
            userList = userService.findObjects();
        } catch (Exception e) {
//            throw new ActionException("action 66666666666666!!!!!"+ e.getMessage());
            throw new Exception(e.getMessage());
        }
        return "listUI";
    }
    //跳转到新增页面
    public String addUI(){
        //加载角色列表
        ActionContext.getContext().getContextMap().put("roleList", roleService.findObjects());
        return "addUI";
    }
    //保存新增
    public String add(){
        try {
            if(user != null){
                //处理头像
                if(headImg != null){
                    //1、保存头像到 upload/user
                    //获取 保存路径的绝对底子
                    String  filePath =
                            ServletActionContext.getServletContext().getRealPath("upload/user");
                    String fileName =
                            UUID.randomUUID().toString().replaceAll("-","")+
                                    headImgFileName.substring(headImgFileName.lastIndexOf("."));
                    //复制文件
                    FileUtils.copyFile(headImg,new File(filePath,fileName));
                    //2.设置用户头像路径
                    user.setHeadImg("user/"+fileName);
                }
                userService.saveUserAndRole(user, userRoleIds);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "list";
    }
    //跳转到编辑页面
    public String editUI(){
        //加载角色列表
        ActionContext.getContext().getContextMap().put("roleList", roleService.findObjects());
        if (user != null && user.getId() != null) {
            user = userService.findObjectById(user.getId());
            //处理角色回显
            List<UserRole> list = userService.getUserRolesByUserId(user.getId());
            if(list != null && list.size()>0){
                userRoleIds = new String[list.size()];
                for (int i = 0; i < list.size(); i++) {
                    userRoleIds[i] = list.get(i).getId().getRole().getRoleId();
                }
            }
        }
        return "editUI";
    }
    //保存编辑
    public String edit(){
        try {
            if(user != null){
                //处理头像
                if(headImg != null){
                    //将之前的头像进行删除，减少内存的消耗
                    //0.1、获得头像地址
                    String lastHeadImg = user.getHeadImg();
                    //0.2、获得图片所在的 真实路径，删除 图片
                    new File(ServletActionContext.getServletContext().getRealPath("upload/")+lastHeadImg).delete();
                    //1.保存头像 到 upload/user
                    //获取保存路径的绝对地址
                    String filePath = ServletActionContext.getServletContext().getRealPath("upload/user");
                    String fileName = UUID.randomUUID().toString().replaceAll("-","")+
                            headImgFileName.substring(headImgFileName.lastIndexOf("."));
                    //复制文件
                    FileUtils.copyFile(headImg,new File(filePath,fileName));
                    //2.设置用户头像路径
                    user.setHeadImg("user/"+fileName);
                }
                userService.updateUserAndRole(user, userRoleIds);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "list";
    }
    //删除
    public String delete(){
        if(user != null && user.getId() != null){
            userService.delete(user.getId());
        }
        return "list";
    }
    //批量删除
    public String deleteSelected(){
        if(selectedRow != null){
            for(String id: selectedRow){
                userService.delete(id);
            }
        }
        return "list";
    }

    //导出用户列表
    public void exportExcel(){

        try{
            //1.查找用户列表
            userList = userService.findObjects();
//            userList=null;
            //2.导出
            HttpServletResponse response = ServletActionContext.getResponse();
            response.setContentType("application/x-execl");
            response.setHeader("Content-Disposition","attachment;filename=" +
                    new String("用户列表.xls".getBytes(),"ISO-8859-1"));
            ServletOutputStream outputStream = response.getOutputStream();
            userService.exportExcel(userList,outputStream);
            if(outputStream != null){
                outputStream.close();
            }
        }catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 导入用户列表
     * Create by y_zzu on 2019/10/7 on 17:42
     */
    public String importExcel(){
        //1.获取 excel 文件
        if(userExcel != null){
            //是否是 Excel
            if(userExcelFileName.matches("^.+\\.(?i)((xls)|(xlsx))$")){
                //导入
                userService.importExcel(userExcel, userExcelFileName);
            }
        }
        return "list";
    }

    //校验用户帐号唯一
    public void verifyAccount(){
        try {
            //1、获取帐号
            if(user != null && StringUtils.isNotBlank(user.getAccount())){
                //2、根据帐号到数据库中校验是否存在该帐号对应的用户
                List<User> list = userService.findUserByAccountAndId(user.getId(), user.getAccount());
                String strResult = "true";
                if(list != null && list.size() > 0){
                    //说明该帐号已经存在
                    strResult = "false";
                }

                //输出
                HttpServletResponse response = ServletActionContext.getResponse();
                response.setContentType("text/html");
                ServletOutputStream outputStream = response.getOutputStream();
                outputStream.write(strResult.getBytes());
                outputStream.close();
            }
            /**
             * 如果user 为空，怎不进行任何操作，js中的ajax 有个等待时间，超过等待时间，会返回一个值，该值不为true，
             * 所以会 产生验证内容。
             */
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<User> getUserList() {
        return userList;
    }
    public void setUserList(List<User> userList) {
        this.userList = userList;
    }
    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }
    public String[] getSelectedRow() {
        return selectedRow;
    }
    public void setSelectedRow(String[] selectedRow) {
        this.selectedRow = selectedRow;
    }
    public File getHeadImg() {
        return headImg;
    }

    public void setHeadImg(File headImg) {
        this.headImg = headImg;
    }

    public String getHeadImgContentType() {
        return headImgContentType;
    }

    public void setHeadImgContentType(String headImgContentType) {
        this.headImgContentType = headImgContentType;
    }

    public String getHeadImgFileName() {
        return headImgFileName;
    }

    public void setHeadImgFileName(String headImgFileName) {
        this.headImgFileName = headImgFileName;
    }

    public File getUserExcel() {
        return userExcel;
    }

    public void setUserExcel(File userExcel) {
        this.userExcel = userExcel;
    }

    public String getUserExcelContentType() {
        return userExcelContentType;
    }

    public void setUserExcelContentType(String userExcelContentType) {
        this.userExcelContentType = userExcelContentType;
    }

    public String getUserExcelFileName() {
        return userExcelFileName;
    }

    public void setUserExcelFileName(String userExcelFileName) {
        this.userExcelFileName = userExcelFileName;
    }

    public String[] getUserRoleIds() {
        return userRoleIds;
    }

    public void setUserRoleIds(String[] userRoleIds) {
        this.userRoleIds = userRoleIds;
    }
}
