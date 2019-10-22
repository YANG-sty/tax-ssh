package com.sys.basecore.dao;

import com.sys.basecore.page.PageResult;
import com.sys.basecore.util.QueryHelper;

import java.io.Serializable;
import java.util.List;

public interface BaseDao<T> {
    //新增
    public void save(T entity);
    //更新
    public void update(T entity);
    //根据 id 删除
    public void delete(Serializable id);
    //根据 id 查找
    public T findObjectById(Serializable id);
    //查找列表
    public List<T> findObjects();
    //条件查询实体列表--查询助手queryHelper
    List<T> findObjects(QueryHelper queryHelper);
    //分页条件查询实体列表--查询助手queryHelper
    PageResult getPageResult(QueryHelper queryHelper, int pageNo, int pageSize);
}
