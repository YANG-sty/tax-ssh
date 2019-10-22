package com.sys.basecore.dao.impl;

import com.sys.basecore.dao.BaseDao;
import com.sys.basecore.page.PageResult;
import com.sys.basecore.util.QueryHelper;
import org.hibernate.Query;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

public abstract class BaseDaoImpl<T>
        extends HibernateDaoSupport implements BaseDao<T> {

    Class<T> clazz;

    public BaseDaoImpl(){
        ParameterizedType pt =
                (ParameterizedType) this.getClass().getGenericSuperclass();//BaseDaoImpl<User>
        clazz = (Class<T>)pt.getActualTypeArguments()[0];
    }

    @Override
    public void save(T entity) {
        getHibernateTemplate().save(entity);
    }

    @Override
    public void update(T entity) {
        getHibernateTemplate().update(entity);
    }

    @Override
    public void delete(Serializable id) {
        getHibernateTemplate().delete(findObjectById(id));
    }

    @Override
    public T findObjectById(Serializable id) {
        return getHibernateTemplate().get(clazz,id);
    }

    @Override
    public List<T> findObjects() {
        Query query = getSession().createQuery("FROM " + clazz.getSimpleName());
        return query.list();
    }

    @Override
    public List<T> findObjects(QueryHelper queryHelper) {
        Query query = getSession().createQuery(queryHelper.getQueryListHql());
        List<Object> parameters = queryHelper.getParameters();
        if(parameters != null){
            for (int i = 0; i < parameters.size(); i++) {
                query.setParameter(i, parameters.get(i));
            }
        }
        return query.list();
    }

    @Override
    public PageResult getPageResult(QueryHelper queryHelper, int pageNo, int pageSize) {
        Query query = getSession().createQuery(queryHelper.getQueryListHql());
        List<Object> parameters = queryHelper.getParameters();
        if(parameters != null){
            for (int i = 0; i < parameters.size(); i++) {
                query.setParameter(i, parameters.get(i));
            }
        }
        if(pageNo < 1){
            pageNo = 1;
        }
        query.setFirstResult((pageNo-1)*pageSize);
        query.setMaxResults(pageSize);
        List items = query.list();
        //获取总记录数
        Query queryCount = getSession().createQuery(queryHelper.getQueryCountHql());
        if(parameters != null){
            for (int i = 0; i < parameters.size(); i++) {
                queryCount.setParameter(i, parameters.get(i));
            }
        }
        long totalCount = (long) queryCount.uniqueResult();
        return new PageResult(totalCount, pageNo, pageSize, items);
    }
}
