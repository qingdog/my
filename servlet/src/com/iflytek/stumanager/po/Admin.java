package com.iflytek.stumanager.po;

public class Admin {

    private int id;
    private String account;
    private String password;

    public Admin(String account, String password) {
        this.account = account;
        this.password = password;
    }
    public String getAccount() {
        return account;
    }

    public String getPassword() {
        return password;
    }
}
