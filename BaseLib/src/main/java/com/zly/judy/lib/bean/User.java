package com.zly.judy.lib.bean;

/**
 * 用户信息
 * Created by Zhuliya on 2018/8/22
 */
public class User {

    private int userId;
    private String name;
    private String nick;

    public User() {
    }

    public User(int userId, String name, String nick) {
        this.userId = userId;
        this.name = name;
        this.nick = nick;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", name='" + name + '\'' +
                ", nick='" + nick + '\'' +
                '}';
    }
}
