package com.sys.basecore.util;

import java.util.ArrayList;
import java.util.List;

public class QueryHelper {
    //from 字句
    private String fromClause = "";
    //where 字句
    private String whereClause = "";
    //order 字句
    private String orderByClause = "";

    private List<Object> parameters;
    //排序顺序
    public static String ORDER_BY_DESC = "DESC"; //降序
    public static String ORDER_BU_ASC = "ASC";//升序

    /**
     * 构造from 字句
     * Create by y_zzu on 2019/10/16 on 11:00
     */
    public QueryHelper(Class clazz, String alias){
        fromClause = "from " + clazz.getSimpleName() + " " + alias;
    }

    /**
     * 构造 where 字句
     * @param condition 查询条件语句；例如：i.title like ?
     * @param params 查询条件语句中 ? 对应的 查询条件值 ；例如： %标题%
     */
    public void addCondition(String condition, Object... params){
        if(whereClause.length() > 1){
            //where字句有值，说明不是第一个查询条件
            whereClause += " and " + condition;
        }else{
            //第一个查询条件
            whereClause += " where " + condition;
        }
        //设置查询条件值到 查询条件值 集合中
        if(parameters == null){
            //如果 parameter集合 为空，初始化该集合
            parameters = new ArrayList<Object>();
        }
        if(params != null){
            //params 不为空，说明有查询条件的值，将内容添加到parameters 条件集合中
            for (Object param : params) {
                parameters.add(param);
            }
        }

    }

    /**
     * 构造order by子句
     * @param property  排序属性，如：i.createTime
     * @param order  排序顺序，如：DESC 或者 ASC
     */
    public void  addOrderByProperty(String property, String order){
        if(orderByClause.length() > 1){
            //不是第一个排序属性
            orderByClause += "," + property + " " + order;
        }else {
            //第一个排序属性
            orderByClause = " order by " + property + " " + order;
        }

    }
    
    //查询 hql 语句
    public String getQueryListHql(){
        return fromClause + whereClause + orderByClause;
    }

    //查询 统计数的 hql 语句
    public String getQueryCountHql(){
        return "select count(*) " + fromClause + whereClause;
    }

    //查询 hql 语句中，对应的查询条件值集合
    public List<Object> getParameters(){
        return parameters;
    }

}
