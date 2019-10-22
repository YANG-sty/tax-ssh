package com.sys.nsfw.complain.action;

import com.opensymphony.xwork2.ActionContext;
import com.sys.basecore.action.BaseAction;
import com.sys.basecore.util.QueryHelper;
import com.sys.nsfw.complain.entity.Complain;
import com.sys.nsfw.complain.entity.ComplainReply;
import com.sys.nsfw.complain.service.ComplainService;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.ServletActionContext;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.net.URLDecoder;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ComplainAction extends BaseAction {
    @Resource
    private ComplainService complainService;
    private Complain complain;
    private String startTime;
    private String endTime;
    private ComplainReply reply;

    private String strTitle;
    private String strState;

    private Map<String, Object> statisticMap;

    //列表
    public String listUI(){
        //加载状态集合
        ActionContext.getContext().getContextMap().put("complainStateMap", Complain.COMPLAIN_STATE_MAP);
        try {
            QueryHelper queryHelper = new QueryHelper(Complain.class, "c");
            if(StringUtils.isNotBlank(startTime)){
                //查询时间不为空，查询开始时间之后的投诉数据
                startTime = URLDecoder.decode(startTime, "utf-8");
                queryHelper.addCondition("c.compTime >= ?", DateUtils.parseDate(startTime + ":00", new String[]{"yyyy-MM-dd HH:mm:ss"}));
            }
            if(StringUtils.isNotBlank(endTime)){
                //查询时间不为空，查询开始时间之后的投诉数据
                endTime = URLDecoder.decode(endTime, "utf-8");
                queryHelper.addCondition("c.compTime <= ?", DateUtils.parseDate(endTime + ":00", new String[]{"yyyy-MM-dd HH:mm:ss"}));
            }
            if(complain != null){
                if(StringUtils.isNoneBlank(complain.getState())){
                    queryHelper.addCondition("c.state=?", complain.getState());
                }
                if(StringUtils.isNoneBlank(complain.getCompTitle())){
                    queryHelper.addCondition("c.compTitle like ?", "%"+ complain.getCompTitle()+"%");
                }
            }
            //排序，按照状态升序0,1,2 待受理,已受理,已失效
            queryHelper.addOrderByProperty("c.state", QueryHelper.ORDER_BU_ASC);
            //排序，按照投诉时间升序排序
            queryHelper.addOrderByProperty("c.compTime", QueryHelper.ORDER_BU_ASC);
            pageResult = complainService.getPageResult(queryHelper, getPageNo(), getPageSize());
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return "listUI";
    }
    //跳转到受理页面
    public String dealUI(){
        //加载状态集合
        ActionContext.getContext().getContextMap().put("complainStateMap", Complain.COMPLAIN_STATE_MAP);
        if(complain != null){
            strTitle = complain.getCompTitle();
            strState = complain.getState();
            complain = complainService.findObjectById(complain.getCompId());
        }
        return "dealUI";
    }

    //受理页面处理
    public String deal(){
        if(complain != null){
            Complain temp = complainService.findObjectById(complain.getCompId());
            //更新投诉的状态为 已受理
            if( !Complain.COMPLAIN_STATE_DONE.equals(temp.getState()) ){
                //投诉的状态不为 已受理 则改为 已受理
                temp.setState(Complain.COMPLAIN_STATE_DONE);
            }
            //保存 回复的信息
            if(reply != null){
                reply.setComplain(temp);
                reply.setReplyTime(new Timestamp(new Date().getTime()));
                /**
                 * 将回复的信息 添加到 Complain 中的 complainReplies 属性中。
                 */
                temp.getComplainReplies().add(reply);
            }
            complainService.update(temp);
        }
        return "list";
    }
    //跳转到 统计页面
    public String annualStatisticChartUI(){
        return "annualStatisticChartUI";
    }
    //根据年度获取该年度下的统计数
    public String getAnnualStatisticData(){
        //1.获取年份
        HttpServletRequest request = ServletActionContext.getRequest();
        int year = 0;
        /**
         * 在 annualStatisticChartUI 页面已经对 year 判断过了，所以year不会为空，
         * 在使用ajax 进行请求的时候，是在加载完元素后，执行 doAnnualStatistics 方法，
         * year 的数据不会为空
         */
        if(request.getParameter("year") != null){
            year = Integer.valueOf(request.getParameter("year"));
        }else{
            //默认 当前年份
            year = Calendar.getInstance().get(Calendar.YEAR);
        }
        //2.获取统计年度的每个月的投诉数
        statisticMap = new HashMap<String, Object>();
        statisticMap.put("msg", "success");
        statisticMap.put("chartData", complainService.getAnnualStatisticDataByYear(year));
        return "annualStatisticData";
    }



    public Complain getComplain() {
        return complain;
    }

    public void setComplain(Complain complain) {
        this.complain = complain;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public ComplainReply getReply() {
        return reply;
    }

    public void setReply(ComplainReply reply) {
        this.reply = reply;
    }

    public String getStrTitle() {
        return strTitle;
    }

    public void setStrTitle(String strTitle) {
        this.strTitle = strTitle;
    }

    public String getStrState() {
        return strState;
    }

    public void setStrState(String strState) {
        this.strState = strState;
    }

    public Map<String, Object> getStatisticMap() {
        return statisticMap;
    }

    public void setStatisticMap(Map<String, Object> statisticMap) {
        this.statisticMap = statisticMap;
    }
}
