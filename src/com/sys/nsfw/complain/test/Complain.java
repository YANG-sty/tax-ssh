package com.sys.nsfw.complain.test;

import java.sql.Timestamp;
import java.util.*;

public class Complain {
    private String compId;
    private String compCompany;
    private String compName;
    private String compMobile;
    private Byte isNm;
    private Timestamp compTime;
    private String compTitle;
    private String toCompName;
    private String toCompDept;
    private String compContent;
    private String state;
    private Set complainReplies = new HashSet(0);

    //状态
    //状态
    public static String COMPLAIN_STATE_UNDONE = "0";
    public static String COMPLAIN_STATE_DONE = "1";
    public static String COMPLAIN_STATE_INVALID = "2";
    public static Map<String, String> COMPLAIN_STATE_MAP;
    static {
        COMPLAIN_STATE_MAP = new HashMap<String, String>();
        COMPLAIN_STATE_MAP.put(COMPLAIN_STATE_UNDONE, "待受理");
        COMPLAIN_STATE_MAP.put(COMPLAIN_STATE_DONE, "已受理");
        COMPLAIN_STATE_MAP.put(COMPLAIN_STATE_INVALID, "已失效");
    }

    public Complain() {
    }

    public Complain(String compTitle) {
        this.compTitle = compTitle;
    }

    public Complain(String compId, String compCompany, String compName, String compMobile, Byte isNm, Timestamp compTime, String compTitle, String toCompName, String toCompDept, String compContent, String state, Set complainReplies) {
        this.compId = compId;
        this.compCompany = compCompany;
        this.compName = compName;
        this.compMobile = compMobile;
        this.isNm = isNm;
        this.compTime = compTime;
        this.compTitle = compTitle;
        this.toCompName = toCompName;
        this.toCompDept = toCompDept;
        this.compContent = compContent;
        this.state = state;
        this.complainReplies = complainReplies;
    }

    public String getCompId() {
        return compId;
    }

    public void setCompId(String compId) {
        this.compId = compId;
    }

    public String getCompCompany() {
        return compCompany;
    }

    public void setCompCompany(String compCompany) {
        this.compCompany = compCompany;
    }

    public String getCompName() {
        return compName;
    }

    public void setCompName(String compName) {
        this.compName = compName;
    }

    public String getCompMobile() {
        return compMobile;
    }

    public void setCompMobile(String compMobile) {
        this.compMobile = compMobile;
    }

    public Byte getIsNm() {
        return isNm;
    }

    public void setIsNm(Byte isNm) {
        this.isNm = isNm;
    }

    public Timestamp getCompTime() {
        return compTime;
    }

    public void setCompTime(Timestamp compTime) {
        this.compTime = compTime;
    }

    public String getCompTitle() {
        return compTitle;
    }

    public void setCompTitle(String compTitle) {
        this.compTitle = compTitle;
    }

    public String getToCompName() {
        return toCompName;
    }

    public void setToCompName(String toCompName) {
        this.toCompName = toCompName;
    }

    public String getToCompDept() {
        return toCompDept;
    }

    public void setToCompDept(String toCompDept) {
        this.toCompDept = toCompDept;
    }

    public String getCompContent() {
        return compContent;
    }

    public void setCompContent(String compContent) {
        this.compContent = compContent;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Set getComplainReplies() {
        return complainReplies;
    }

    public void setComplainReplies(Set complainReplies) {
        this.complainReplies = complainReplies;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Complain complain = (Complain) o;
        return Objects.equals(compId, complain.compId) &&
                Objects.equals(compCompany, complain.compCompany) &&
                Objects.equals(compName, complain.compName) &&
                Objects.equals(compMobile, complain.compMobile) &&
                Objects.equals(isNm, complain.isNm) &&
                Objects.equals(compTime, complain.compTime) &&
                Objects.equals(compTitle, complain.compTitle) &&
                Objects.equals(toCompName, complain.toCompName) &&
                Objects.equals(toCompDept, complain.toCompDept) &&
                Objects.equals(compContent, complain.compContent) &&
                Objects.equals(state, complain.state);
    }

    @Override
    public int hashCode() {
        return Objects.hash(compId, compCompany, compName, compMobile, isNm, compTime, compTitle, toCompName, toCompDept, compContent, state);
    }

}
