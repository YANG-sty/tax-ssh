package com.sys.nsfw.complain.dao;

import com.sys.basecore.dao.BaseDao;
import com.sys.nsfw.complain.entity.Complain;

import java.util.List;

public interface ComplainDao extends BaseDao<Complain> {
    //根据年份获取 统计年度 的每个月的 投诉数
    public List<Object[]> getAnnualStatisticDataByYear(int year);
}
