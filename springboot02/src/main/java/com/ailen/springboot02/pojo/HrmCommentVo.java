package com.ailen.springboot02.pojo;

import java.io.Serializable;
import java.util.List;

public class HrmCommentVo implements Serializable {
    private String id;
    private String userName;
    private String account;
    private String password;
    private List<Comment> comments ;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    @Override
    public String toString() {
        return "HrmCommentVo{" +
                "id='" + id + '\'' +
                ", userName='" + userName + '\'' +
                ", account='" + account + '\'' +
                ", password='" + password + '\'' +
                ", comments=" + comments +
                '}';
    }
}
