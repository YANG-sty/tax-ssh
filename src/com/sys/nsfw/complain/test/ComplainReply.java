package com.sys.nsfw.complain.test;

import java.sql.Timestamp;
import java.util.Objects;

public class ComplainReply {
    private String replyId;
    private String replyer;
    private String replyDept;
    private Timestamp replyTime;
    private String replyContent;
    private Complain complainByCompId;

    public Complain getComplainByCompId() {
        return complainByCompId;
    }

    public void setComplainByCompId(Complain complainByCompId) {
        this.complainByCompId = complainByCompId;
    }

    public String getReplyId() {
        return replyId;
    }

    public void setReplyId(String replyId) {
        this.replyId = replyId;
    }

    public String getReplyer() {
        return replyer;
    }

    public void setReplyer(String replyer) {
        this.replyer = replyer;
    }

    public String getReplyDept() {
        return replyDept;
    }

    public void setReplyDept(String replyDept) {
        this.replyDept = replyDept;
    }

    public Timestamp getReplyTime() {
        return replyTime;
    }

    public void setReplyTime(Timestamp replyTime) {
        this.replyTime = replyTime;
    }

    public String getReplyContent() {
        return replyContent;
    }

    public void setReplyContent(String replyContent) {
        this.replyContent = replyContent;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ComplainReply that = (ComplainReply) o;
        return Objects.equals(replyId, that.replyId) &&
                Objects.equals(replyer, that.replyer) &&
                Objects.equals(replyDept, that.replyDept) &&
                Objects.equals(replyTime, that.replyTime) &&
                Objects.equals(replyContent, that.replyContent);
    }

    @Override
    public int hashCode() {
        return Objects.hash(replyId, replyer, replyDept, replyTime, replyContent);
    }

}
