package com.keng.servlet;

import com.keng.Bean.Login;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.LinkedList;

public class BuyServlet extends HttpServlet {
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        try{
            Class.forName("com.mysql.jdbc.Driver");
        }
        catch(Exception e){}
    }
    public  void  doPost(HttpServletRequest request,HttpServletResponse response)
            throws ServletException,IOException {
        request.setCharacterEncoding("utf-8");
        String buyGoodsMess = request.getParameter("buy");
        if(buyGoodsMess==null||buyGoodsMess.length()==0) {
            fail(request,response,"购物车没有物品");
            return;
        }
        String price = request.getParameter("price");
        /*if(price==null||price.length()==0) {
            fail(request,response,"没有计算价格和，无法支付");
            return;
        }*/
        float sum = Float.parseFloat(price);
        Login loginBean=null;
        HttpSession session=request.getSession(true);
        try{  loginBean=(Login)session.getAttribute("loginBean");
            boolean b =loginBean.getName()==null||
                    loginBean.getName().length()==0;
            if(b)
                response.sendRedirect("Login.jsp");//重定向到登录页面
        }
        catch(Exception exp){
            response.sendRedirect("Login.jsp");//重定向到登录页面
        }
        String uri="jdbc:mysql://localhost:3306/ECW?useUnicode=true&characterEncoding=utf-8";
        Connection con = null;
        PreparedStatement sql = null;
        Statement statement = null;
        ResultSet rs= null;
        try{ con= DriverManager.getConnection(uri,"root","115351");
            String insertCondition="INSERT INTO bill(totalPrice,customer) VALUES (?,?)";
            sql=con.prepareStatement(insertCondition);
            //sql.setInt(1,0);
            // 订单序号会自定增加
            sql.setFloat(1,sum);
            sql.setString(2,loginBean.getName());
            sql.executeUpdate();
            LinkedList car=loginBean.getCar();
            car.clear();  //清空购物车
            success(request,response,"支付成功");
            String[] products = buyGoodsMess.split(",");
            String queryCondition = "";
            for(String i :products){
                i = i.substring(1,i.length()-1);
                queryCondition= "select quantitysold from market where productname='"+i+"'";
                String insertCondition1 = "update market set quantitysold = quantitysold+1 where productname='"+i+"'";
                String insertCondition2 = "insert into market(productname,quantitysold) values('"+i+"',1)";
                statement = con.createStatement();
                rs = statement.executeQuery(queryCondition);
                if(rs.next()){
                    statement.executeUpdate(insertCondition1);
                }else{
                    statement.executeUpdate(insertCondition2);
                }
            }
            System.out.println("HandleBuyGoods - 支付成功");
        }
        catch(SQLException exp){
            exp.printStackTrace();
            fail(request,response,"支付失败"+exp);
        }
        finally {
            try {
                rs.close();
                statement.close();
                sql.close();
                con.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }
    public  void  doGet(HttpServletRequest request,HttpServletResponse response)
            throws ServletException,IOException {
        doPost(request,response);
    }
    public void success(HttpServletRequest request,HttpServletResponse response,
                        String backNews) {
        response.setContentType("text/html;charset=utf-8");
        try {
            PrintWriter out=response.getWriter();
            out.println("<html><body>");
            out.println("<h2>"+backNews+"</h2>") ;
            out.println("返回主页<br>");
            out.println("<br><a href =main.jsp>主页</a>");
            out.println("</body></html>");
        }
        catch(IOException exp){}
    }
    public void fail(HttpServletRequest request, HttpServletResponse response,
                     String backNews) {
        response.setContentType("text/html;charset=utf-8");
        try {
            PrintWriter out=response.getWriter();
            out.println("<html><body>");
            out.println("<h2>"+backNews+"</h2>") ;
            out.println("返回主页：");
            out.println("<a href =index.jsp>主页</a>");
            out.println("</body></html>");
        }
        catch(IOException exp){}
    }
}
