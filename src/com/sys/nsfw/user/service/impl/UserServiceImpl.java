package com.sys.nsfw.user.service.impl;

import com.sys.basecore.exception.ServiceException;
import com.sys.basecore.service.BaseService;
import com.sys.basecore.service.impl.BaseServiceImpl;
import com.sys.basecore.util.ExcelUtil;
import com.sys.nsfw.role.entity.Role;
import com.sys.nsfw.user.dao.UserDao;
import com.sys.nsfw.user.entity.User;
import com.sys.nsfw.user.entity.UserRole;
import com.sys.nsfw.user.entity.UserRoleId;
import com.sys.nsfw.user.service.UserService;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Service("userService")
public class UserServiceImpl extends BaseServiceImpl<User> implements UserService {

    private UserDao userDao;

    @Resource
    public void setUserDao(UserDao userDao) {
        super.setBaseDao(userDao);
        this.userDao = userDao;
    }
    /*@Override
    public void save(User user) {
        userDao.save(user);
    }

    @Override
    public void update(User user) {
        userDao.update(user);
    }*/

    @Override
    public void delete(Serializable id) {
        //在删除 用户的时候，同时删除用户的角色表中的有关的信息。
        List<UserRole> list = userDao.getUserRolesByUserId(id);
        if(list != null && list.size()>0){
            userDao.deleteUserRoleByUserId(id);
        }
        /**
         * 不用先先删除 角色信息 ，在删除用户的时候，用户的角色表的内容也会跟着删除。
         */
        userDao.delete(id);
    }

    /*@Override
    public User findObjectById(Serializable id) {
        return userDao.findObjectById(id);
    }
*/
    /*@Override
    public List<User> findObjects() {
        *//*try {
            int i= 1/0;
        } catch (Exception e) {
            throw new ServiceException();
        }*//*
        return userDao.findObjects();
    }*/

    @Override
    public void exportExcel(List<User> userList, ServletOutputStream outputStream) {
        ExcelUtil.exportUserExcel(userList,outputStream);
    }

    @Override
    public void importExcel(File userExcel, String userExcelFileName) {
        try{
            FileInputStream fileInputStream = new FileInputStream(userExcel);
            boolean is03Excel = userExcelFileName.matches("^.+\\.(?i)(xls)$");
            //1.读取工作薄
            Workbook workbook = is03Excel ? new HSSFWorkbook(fileInputStream):new XSSFWorkbook(fileInputStream);
            //2.读取工作表
            Sheet sheet = workbook.getSheetAt(0);   //读取工作薄 中的 第一个工作表
            //3.读取行
            if(sheet.getPhysicalNumberOfRows() > 2){
                User user = null;
                for(int k=2; k<sheet.getPhysicalNumberOfRows(); k++){
                    //4.读取单元格
                    Row row = sheet.getRow(k);//工作表中的的数据文件信息是从第三行开始的。
                    user = new User();
                    //用户名
                    Cell cell0 = row.getCell(0);//获得第k行，第一列，单元格的内容
                    user.setName(cell0.getStringCellValue());
                    //账号
                    Cell cell1 = row.getCell(1);
                    String account = cell1.getStringCellValue();
                    user.setAccount(account);
                    //所属部门
                    Cell cell2 = row.getCell(2);
                    user.setDepartment(cell2.getStringCellValue());
                    //性别
                    Cell cell3 = row.getCell(3);
                    user.setGender(cell3.getStringCellValue().equals("男"));
                    //手机号
                    String mobile = "";
                    Cell cell4 = row.getCell(4);
                    try{
                        mobile = cell4.getStringCellValue();
                        /**
                         * mobile 变量的类型是string型的，如果赋值成功，说明该单元格的内容是字符串
                         * 如果赋值失败，则说明不是字符串，会出现异常，然后进行catch 操作，做之后的处理
                         */
                    }catch(Exception e){
                        double dMobile = cell4.getNumericCellValue();
                        mobile = BigDecimal.valueOf(dMobile).toString();
                    }
                    user.setMobile(mobile);

                    //电子邮箱
                    Cell cell5 = row.getCell(5);
                    user.setEmail(cell5.getStringCellValue());

                    //生日
                    Cell cell6 = row.getCell(6);
                    if(cell6.getDateCellValue() != null){
                        user.setBirthday(cell6.getDateCellValue());
                    }

                    //设置用户的默认密码为 123456
                    user.setPassword("123456");
                    //设置用户默认状态 有效
                    user.setState(User.USER_STATE_VALID);

                    /**
                     * 用户在保存之前要进行判断操作。看数据库中管是否已经存在该名字的用户。
                     * 如果用户存在，则user1 的内容不为空，不进行保存操作，
                     * 当 account 不存在数据库中的时候，user1 的内容为空，进行保存操作。
                     */
                    try {
                        User user1 = null;
                        user1 = userDao.findByAccount(account);
                        if(user1==null || "".equals(user1)){
                            save(user);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            }
            workbook.close();
            fileInputStream.close();

        }catch(Exception e){
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    /**
     * 根据账号 和 用户id 查询用户
     * Create by y_zzu on 2019/10/10 on 13:55
     *
     * @param id
     * @param account
     */
    @Override
    public List<User> findUserByAccountAndId(String id, String account) {
        return userDao.findUserByAccountAndId(id, account);
    }

    /**
     * 保存用户及对应的角色
     * Create by y_zzu on 2019/10/13 on 16:57
     *
     * @param user
     * @param roleIds
     */
    @Override
    public void saveUserAndRole(User user, String... roleIds) {
        //保存用户
        save(user);
        // 保存用户对应的角色信息
        if(roleIds != null){
            for (String roleId : roleIds) {
                userDao.saveUserRole(new UserRole(new UserRoleId(new Role(roleId), user.getId())));
            }
        }
    }

    /**
     * 更新用户 及其对应的角色信息
     * Create by y_zzu on 2019/10/13 on 17:07
     *
     * @param user
     * @param roleIds
     */
    @Override
    public void updateUserAndRole(User user, String... roleIds) {
        //根据用户 删除该 用户的所有角色
        userDao.deleteUserRoleByUserId(user.getId());
        //更新用户
        update(user);
        //保存用户对应的角色
        if(roleIds != null){
            for (String roleId : roleIds) {
                userDao.saveUserRole(new UserRole(new UserRoleId(new Role(roleId), user.getId())));
            }
        }

    }

    /**
     * 根据用户 id 获取该用户对应的所有 角色
     * Create by y_zzu on 2019/10/13 on 17:08
     *
     * @param id
     */
    @Override
    public List<UserRole> getUserRolesByUserId(String id) {

        return userDao.getUserRolesByUserId(id);
    }

    /**
     * 根据用户 的账号和密码 查询所有用户列表
     * Create by y_zzu on 2019/10/13 on 17:09
     *
     * @param account
     * @param password
     */
    @Override
    public List<User> findUserByAccountAndPass(String account, String password) {
        return userDao.findUserByAccountAndPass(account,password);
    }


}
