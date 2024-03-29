package com.sys.basecore.action;

import com.opensymphony.xwork2.ActionSupport;
import com.sys.basecore.page.PageResult;

public abstract class BaseAction extends ActionSupport {
    protected String[] selectedRow;
    protected PageResult pageResult;
    //当前页号
    protected int pageNo;
    protected int pageSize;
    //默认翻页大小
    public static int DEFAULT_PAGE_SIZE = 10;


    public String[] getSelectedRow() {
        return selectedRow;
    }

    public void setSelectedRow(String[] selectedRow) {
        this.selectedRow = selectedRow;
    }

    public PageResult getPageResult() {
        return pageResult;
    }

    public void setPageResult(PageResult pageResult) {
        this.pageResult = pageResult;
    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getPageSize() {
        if(pageSize < 1){
            pageSize = DEFAULT_PAGE_SIZE;
        }
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}
