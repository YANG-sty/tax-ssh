package com.sys.basecore.service;

import com.sys.basecore.page.PageResult;
import com.sys.basecore.util.QueryHelper;

import java.io.Serializable;
import java.util.List;

public interface BaseService<T> {
    //新增
    public void save(T entity);

    //更新
    public void update(T entity);

    //根据 id 删除
    public void delete(Serializable id);

    //根据 id 查找
    public T findObjectById(Serializable id);

    //查找列表
    @Deprecated
    public List<T> findObjects();

    //条件查询实体列表--查询助手 QueryHelper
    public List<T> findObjects(QueryHelper queryHelper);

    //分页 条件查询 实体列表-- 查询助手
    public PageResult getPageResult(QueryHelper queryHelper, int pageNo, int pageSize);
}
