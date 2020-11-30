package com.keng.Bean;

import java.sql.ResultSet;
import java.util.LinkedList;

public class Login {
    String name="",
            backNews="未登录";
    LinkedList<String> car; //用户的购物车
    private ResultSet rs;
    public Login() {
        car = new LinkedList<String>();
    }
    public void setName(String name){
        this.name = name;
    }
    public String getName(){
        return name;
    }
    public void setBackNews(String s) {
        backNews = s;
    }
    public String getBackNews(){
        return backNews;
    }
    public LinkedList<String> getCar() {
        return car;
    }
    public ResultSet getRs() {
        return rs;
    }
    public void setRs(ResultSet rs) {
        this.rs = rs;
    }
}
