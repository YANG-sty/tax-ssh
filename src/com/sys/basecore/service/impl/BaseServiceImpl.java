package com.sys.basecore.service.impl;

import com.sys.basecore.dao.BaseDao;
import com.sys.basecore.page.PageResult;
import com.sys.basecore.service.BaseService;
import com.sys.basecore.util.QueryHelper;

import java.io.Serializable;
import java.util.List;

public class BaseServiceImpl<T> implements BaseService<T> {

    private BaseDao<T> baseDao;

    public void setBaseDao(BaseDao<T> baseDao) {
        this.baseDao = baseDao;
    }

    @Override
    public void save(T entity) {
        baseDao.save(entity);
    }

    @Override
    public void update(T entity) {
        baseDao.update(entity);
    }

    @Override
    public void delete(Serializable id) {
        baseDao.delete(id);
    }

    @Override
    public T findObjectById(Serializable id) {
        return baseDao.findObjectById(id);
    }

    @Override
    public List<T> findObjects() {
        return baseDao.findObjects();
    }

    @Override
    public List<T> findObjects(QueryHelper queryHelper) {
        return baseDao.findObjects(queryHelper);
    }

    @Override
    public PageResult getPageResult(QueryHelper queryHelper, int pageNo, int pageSize) {
        return baseDao.getPageResult(queryHelper, pageNo, pageSize);
    }
}
