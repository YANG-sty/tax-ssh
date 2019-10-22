package com.sys.nsfw.complain.dao.impl;

import com.sys.basecore.dao.impl.BaseDaoImpl;
import com.sys.nsfw.complain.dao.ComplainDao;
import com.sys.nsfw.complain.entity.Complain;
import org.hibernate.SQLQuery;

import java.util.List;

public class ComplainDaoImpl extends BaseDaoImpl<Complain> implements ComplainDao {
    @Override
    public List<Object[]> getAnnualStatisticDataByYear(int year) {
        StringBuffer sb = new StringBuffer();
        sb.append("select imonth, count(comp_id)")
                .append(" from tmonth left join complain on imonth=MONTH(comp_time)")
                .append(" and year(comp_time)=?")
                .append(" group by imonth")
                .append(" order by imonth");
        SQLQuery sqlQuery = getSession().createSQLQuery(sb.toString());
        sqlQuery.setParameter(0, year);
        return sqlQuery.list();
    }
}
