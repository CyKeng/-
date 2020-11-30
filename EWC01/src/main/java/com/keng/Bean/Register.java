package com.keng.Bean;

public class Register {
    String  name="" , phone="",
            email="",backNews="请输入信息";
    public void setName(String name){
        this.name=name;
    }
    public String getName(){
        return name;
    }
    public void setPhone(String phone){
        this.phone=phone;
    }
    public String getPhone(){
        return phone;
    }
    public void setEmail(String email){
        this.email=email;
    }
    public String getEmail(){
        return email;
    }
    public void setBackNews(String backNews){
        this.backNews=backNews;
    }
    public String getBackNews(){
        return backNews;
    }
}
