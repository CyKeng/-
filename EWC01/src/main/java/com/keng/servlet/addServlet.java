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
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class addServlet extends HttpServlet {
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        String uri="jdbc:mysql://localhost:3306/ECW?useUnicode=true&characterEncoding=utf-8";
        Login loginBean=null;
        HttpSession session=request.getSession(true);
        try{
            loginBean=(Login)session.getAttribute("loginBean");
            boolean b =loginBean.getName()==null||
                    loginBean.getName().length()==0||
                    !loginBean.getName().equals("admin");
            if(b)
                response.sendRedirect("Login.jsp");//重定向到登录页面
        }
        catch(Exception exp){
            response.sendRedirect("Login.jsp");//重定向到登录页面
        }
        Connection conn = null;
        Statement statement = null;
        String newname = request.getParameter("name");
        double newprice = Double.parseDouble(request.getParameter("price"));
        String sql = "insert into product(productName,productPrice) values('"+newname+"','"+newprice+"') ";
        try {
            conn = DriverManager.getConnection(uri,"root","115351");
            statement = conn.createStatement();
            int result = statement.executeUpdate(sql);
            success(request,response,"添加成功");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            try {
                statement.close();
                conn.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }
    public void success(HttpServletRequest request, HttpServletResponse response,
                        String backNews) {
        response.setContentType("text/html;charset=utf-8");
        try {
            PrintWriter out=response.getWriter();
            out.println("<html><body>");
            out.println("<h2>"+backNews+"</h2>") ;
            out.println("返回主页<br>");
            out.println("<br><a href =admin.jsp>主页</a>");
            out.println("</body></html>");
        }
        catch(IOException exp){}
    }
}
