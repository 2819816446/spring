package com.ailen.springboot02.pojo;

import java.io.Serializable;

public class Comment implements Serializable {
    int commentId ; //评论主键id
    int hrmId; //人员id
    String title; //评论标题
    String content; //品论内容

    public int getCommentId() {
        return commentId;
    }

    public void setCommentId(int commentId) {
        this.commentId = commentId;
    }

    public int getHrmId() {
        return hrmId;
    }

    public void setHrmId(int hrmId) {
        this.hrmId = hrmId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "commentId=" + commentId +
                ", hrmId=" + hrmId +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
