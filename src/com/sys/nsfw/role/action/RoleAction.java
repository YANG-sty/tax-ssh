package com.sys.nsfw.role.action;

import com.opensymphony.xwork2.ActionContext;
import com.sys.basecore.action.BaseAction;
import com.sys.basecore.constant.Constant;
import com.sys.basecore.util.QueryHelper;
import com.sys.nsfw.role.entity.Role;
import com.sys.nsfw.role.entity.RolePrivilege;
import com.sys.nsfw.role.entity.RolePrivilegeId;
import com.sys.nsfw.role.service.RoleService;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.Resource;
import java.net.URLDecoder;
import java.util.HashSet;
import java.util.List;

public class RoleAction extends BaseAction {
    @Resource
    private RoleService roleService;

    private List<Role> roleList;
    private Role role;
    private String[] privilegeIds;
    private String strName;

    //列表页面
    public String listUI(){
        //加载权限集合
        ActionContext.getContext().getContextMap().put("privilegeMap", Constant.PRIVILEGE_MAP);
        QueryHelper queryHelper = new QueryHelper(Role.class, "r");
        try {
            if(role != null){
                if(StringUtils.isNotBlank(role.getName())){
                    role.setName(URLDecoder.decode(role.getName(),"utf-8"));
                    queryHelper.addCondition("r.name like ?","%"+role.getName()+"%");
                }
            }
            pageResult = roleService.getPageResult(queryHelper, getPageNo(), getPageSize());
//            roleList = roleService.findObjects();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "listUI";
    }
    //跳转到新增页面
    public String addUI(){
        //加载权限集合
        ActionContext.getContext().getContextMap().put("privilegeMap",Constant.PRIVILEGE_MAP);
        //解决 查询条件覆盖的问题
        strName = role.getName();
        //将 role 对象中的数据清除
        role = new Role();
        return "addUI";
    }
    //保存新增
    public String add(){
        if(role != null){
            //处理权限保存
            if(privilegeIds != null){
                HashSet<RolePrivilege> set = new HashSet<RolePrivilege>();
                for(int i=0; i< privilegeIds.length; i++){
                    set.add(new RolePrivilege(new RolePrivilegeId(role, privilegeIds[i])));
                }
                role.setRolePrivileges(set);
            }
            roleService.save(role);
        }
        return "list";
    }
    //跳转到编辑页面
    public String editUI(){
        //加载权限集合
        ActionContext.getContext().getContextMap().put("privilegeMap",Constant.PRIVILEGE_MAP);
        if(role != null && role.getRoleId() != null){

            //解决 查询条件覆盖的问题
            strName = role.getName();

            role = roleService.findObjectById(role.getRoleId());
            //处理权限回显
            if(role.getRolePrivileges() != null){
                privilegeIds = new String[role.getRolePrivileges().size()];
                int i=0;
                for (RolePrivilege rolePrivilege : role.getRolePrivileges()) {
                    privilegeIds[i++] = rolePrivilege.getId().getCode();
                }
            }
        }
        return "editUI";
    }
    //保存编辑
    public String edit(){
        if(role != null){
            //处理权限保存
            if(privilegeIds != null){
                HashSet<RolePrivilege> rolePrivileges = new HashSet<>();
                for (int i = 0; i < privilegeIds.length; i++) {
                    rolePrivileges.add(new RolePrivilege(new RolePrivilegeId(role, privilegeIds[i])));
                }
                role.setRolePrivileges(rolePrivileges);
            }
            roleService.update(role);
        }
        return "list";
    }
    //删除
    public String delete(){
        if(role != null && role.getRoleId() != null){
            //解决 查询条件覆盖的问题
            strName = role.getName();

            roleService.delete(role.getRoleId());
        }
        return "list";
    }
    //批量删除
    public String deleteSelected(){
        //解决 查询条件覆盖的问题
        strName = role.getName();
        if(selectedRow != null){
            for (String id : selectedRow) {
                roleService.delete(id);
            }
        }
        return "list";
    }


    public List<Role> getRoleList() {
        return roleList;
    }

    public void setRoleList(List<Role> roleList) {
        this.roleList = roleList;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String[] getPrivilegeIds() {
        return privilegeIds;
    }

    public void setPrivilegeIds(String[] privilegeIds) {
        this.privilegeIds = privilegeIds;
    }

    public String getStrName() {
        return strName;
    }

    public void setStrName(String strName) {
        this.strName = strName;
    }
}
