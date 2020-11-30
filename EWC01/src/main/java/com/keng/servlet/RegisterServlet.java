package com.keng.servlet;

import com.keng.Bean.Register;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class RegisterServlet extends HttpServlet {
    public void init(ServletConfig config) throws ServletException{
        super.init(config);
        try{
            Class.forName("com.mysql.jdbc.Driver");
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    public String handleString(String s) {
        try {
            byte bb[] = s.getBytes("utf-8");
            s = new String(bb);
        } catch (Exception ee) {
        }
        return s;
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String uri = "jdbc:mysql://localhost:3306/ECW?useUnicode=true&characterEncoding=utf-8";
        Connection con = null;
        PreparedStatement sql;

        // <jsp:useBean id="userBean" class="Register的完整类名"
        // 			scope="request" />
        Register userBean = new Register();  //创建的Javabean模型
        request.setAttribute("userBean", userBean);
        // 临时变量， 保存表单数据
        // 合法性校验后，保存到成员变量
        String name = request.getParameter("name");
        String password = request.getParameter("password");
        String password2 = request.getParameter("password2");
        String phone = request.getParameter("phonenumber");
        String email = request.getParameter("email");
        System.out.println(name);
        if (name == null)
            name = "";
        if (password == null)
            password = "";
        if (!password.equals(password2)) {
            userBean.setBackNews("两次密码不同，注册失败，");
            RequestDispatcher dispatcher =
                    request.getRequestDispatcher("Register.jsp");
            dispatcher.forward(request, response);
            return;
        }
        boolean isLD = true;
        for (int i = 0; i < name.length(); i++) {
            char c = name.charAt(i);
            if (!((c <= 'z' && c >= 'a') || (c <= 'Z' && c >= 'A') || (c <= '9' && c >= '0')))
                isLD = false;
        }
        boolean boo = name.length() > 0 && password.length() > 0 && isLD;
        String backNews = "";
        try {
            con = DriverManager.getConnection(uri, "root", "115351");
            String insertCondition = "INSERT INTO userinfo(name,password,phone,email) VALUES (?,?,?,?)";
            sql = con.prepareStatement(insertCondition);
            if (boo) {
                sql.setString(1, handleString(name));
                sql.setString(2, handleString(password));
                sql.setString(3, handleString(phone));
                sql.setString(4, handleString(email));
                int m = sql.executeUpdate();
                if (m != 0) {
                    backNews = "注册成功";
                    // <jsp:setProperty name="userBean" property="logname" param="logname" />
                    // <jsp:setProperty name="userBean" property="*" />

                    userBean.setName(name);
                    userBean.setBackNews(backNews);
                    userBean.setPhone(handleString(phone));
                    userBean.setEmail(handleString(email));
                }
            } else {
                backNews = "信息填写不完整或名字中有非法字符";
                userBean.setBackNews(backNews);
                System.out.println("信息不完整");
            }
            con.close();
        } catch (SQLException exp) {
            backNews = "该会员名已被使用，请您更换名字" + exp;
            userBean.setBackNews(backNews);
            System.out.println("出错了");
            System.out.println(exp.getStackTrace());
            exp.printStackTrace();
        }
        RequestDispatcher dispatcher =
                request.getRequestDispatcher("Register.jsp");
        dispatcher.forward(request, response);//转发
    }
}
