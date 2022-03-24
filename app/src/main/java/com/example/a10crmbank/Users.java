package com.example.a10crmbank;

public class Users {
    private String user_id;
    private String playerid,name,loginid;

    public Users(String user_id, String playerid, String name, String loginid) {
        this.user_id = user_id;
        this.playerid = playerid;
        this.name = name;
        this.loginid = loginid;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getPlayerid() {
        return playerid;
    }

    public void setPlayerid(String playerid) {
        this.playerid = playerid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLoginid() {
        return loginid;
    }

    public void setLoginid(String loginid) {
        this.loginid = loginid;
    }
}
