package com.keng.servlet;

import java.sql.*;

public class dbtest {
    public static void main(String[] args) throws SQLException {
        String dbURL = "jdbc:mysql://localhost:3306/ECW?useUnicode=true&characterEncoding=utf-8";
        Connection dbConn = null;
        PreparedStatement statement=null;
        Statement statement1 = null;
        ResultSet res=null;
        try {
            //1.加载驱动
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("加载驱动成功！");
            //2.连接
            dbConn = DriverManager.getConnection(dbURL,"root","115351");
            System.out.println("连接数据库成功！");
            String sql="select * from userinfo";
            //statement=dbConn.prepareStatement(sql);
            statement1=dbConn.createStatement();
            res=statement1.executeQuery(sql);
            while(res.next()){
                String title=res.getString("name");
                String title2=res.getString("password");
                System.out.println(title);
                System.out.println(title2);
            }
        }catch(Exception e) {
            e.printStackTrace();
            System.out.println("连接数据库失败！");
        }
        finally {
            res.close();
            statement1.close();
            dbConn.close();
        }
    }
}