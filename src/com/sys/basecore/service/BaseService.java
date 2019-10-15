package com.sys.basecore.service;

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
    public List<T> findObjects();
}
