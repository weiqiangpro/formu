package com.formu.bean;

public class User {
    private Integer userId;

    private String userName;

    private String passwd;

    private String friends;

    private String pho;

    private String articleGood;

    private String email;

    private String yiban;

    private String account;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd == null ? null : passwd.trim();
    }

    public String getFriends() {
        return friends;
    }

    public void setFriends(String friends) {
        this.friends = friends == null ? null : friends.trim();
    }

    public String getPho() {
        return pho;
    }

    public void setPho(String pho) {
        this.pho = pho == null ? null : pho.trim();
    }

    public String getArticleGood() {
        return articleGood;
    }

    public void setArticleGood(String articleGood) {
        this.articleGood = articleGood == null ? null : articleGood.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public String getYiban() {
        return yiban;
    }

    public void setYiban(String yiban) {
        this.yiban = yiban == null ? null : yiban.trim();
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account == null ? null : account.trim();
    }
}