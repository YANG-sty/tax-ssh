package com.sys.nsfw.complain.service.impl;

import com.sys.basecore.service.impl.BaseServiceImpl;
import com.sys.basecore.util.QueryHelper;
import com.sys.nsfw.complain.dao.ComplainDao;
import com.sys.nsfw.complain.entity.Complain;
import com.sys.nsfw.complain.service.ComplainService;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

@Service("complainService")
public class ComplainServiceImpl extends BaseServiceImpl<Complain> implements ComplainService {
    private ComplainDao complainDao;

    @Resource
    public void setComplainDao(ComplainDao complainDao) {
        super.setBaseDao(complainDao);
        this.complainDao = complainDao;
    }

    @Override
    public void autoDeal() {
        System.out.println("进入 ComplainServiceImpl.atuoDeal方法。。。。。。。。。。。。。。。。");
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, 1);//设置当前时间的日期为 1 号
        calendar.set(Calendar.HOUR_OF_DAY, 0);//设置当前时间的日期为 1 号 0 时
        calendar.set(Calendar.MINUTE, 0);//设置当前时间为 1 号 0 分
        calendar.set(Calendar.SECOND, 0);//设置当前时间为 1 号 0 秒

        //查询本月之前的 待受理的投诉列表
        QueryHelper queryHelper = new QueryHelper(Complain.class, "c");
        queryHelper.addCondition("c.state=? ",Complain.COMPLAIN_STATE_UNDONE);
        queryHelper.addCondition("c.compTime < ? ", calendar.getTime());//时间小于本月1号0时0分0秒的记录
        List<Complain> list = findObjects(queryHelper);
        System.out.println("未受理的投诉为："+list);
        if(list != null && list.size()>0){
            //更新投诉信息的状态为 已失效
            for (Complain complain : list) {
                complain.setState(Complain.COMPLAIN_STATE_INVALID);
                update(complain);
                System.out.println("状态改为 已失效 ！！！！！！！！！！！！！！！！！");
            }
        }
    }

    @Override
    public List<Map> getAnnualStatisticDataByYear(int year) {
        List<Map> resList = new ArrayList<>();
        //获取统计数据
        List<Object[]> list = complainDao.getAnnualStatisticDataByYear(year);
        System.out.println(list);
        if(list != null && list.size()>0){
            Calendar calendar = Calendar.getInstance();
            //是否当前年份
            boolean isCurYear = (calendar.get(Calendar.YEAR) == year);
            int curMonth = calendar.get(Calendar.MONTH)+1;//当前月份
            //2、格式化统计结果
            int temMonth = 0;
            Map<String, Object> map = null;
            for (Object[] objects : list) {
                //objects[0] 是获得 list 循环过程中的一个对象的第一个属性
                temMonth = Integer.valueOf((objects[0])+"");
                map = new HashMap<String, Object>();
                map.put("label", temMonth + " 月");
                if(isCurYear){
                    //如果传入的值是当前年份
                    /**
                     * 当前年份：如果月份已经过的，则直接取投诉数并且值为空或null时设为0，
                     * 如果月份未过的则全部投诉置为空
                     */
                    if(temMonth > curMonth){
                        // 月份大于 当前月份
                        map.put("value", "");
                    }else {
                        // 已经过去的月份
                        //object[1] 获得的是循环对象中，第二个值 统计数
                        map.put("value", objects[1]==null?"0":objects[1]);
                    }
                }else{
                    //年份是 之前的年份
                    //直接获取 投诉数并且值为空 或 null 时则设为 0
                    map.put("value", objects[1]==null?"0":objects[1]);
                }
                resList.add(map);
            }
        }
        return resList;
    }
}
