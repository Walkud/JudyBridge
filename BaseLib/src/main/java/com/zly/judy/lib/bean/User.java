package com.zly.judy.lib.bean;

/**
 * 用户信息
 * Created by Zhuliya on 2018/8/22
 */
public class User {

    private String name;
    private String nick;

    public User() {
    }

    public User(String name, String nick) {
        this.name = name;
        this.nick = nick;
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
}
