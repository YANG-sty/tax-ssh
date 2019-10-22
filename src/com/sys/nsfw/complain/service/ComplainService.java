package com.sys.nsfw.complain.service;

import com.sys.basecore.service.BaseService;
import com.sys.nsfw.complain.entity.Complain;

import java.util.List;
import java.util.Map;

public interface ComplainService extends BaseService<Complain> {
    //自动受理投诉
    public void autoDeal();

    //根据年份获取统计年度的每个月的投诉数
    public List<Map> getAnnualStatisticDataByYear(int year);
}
