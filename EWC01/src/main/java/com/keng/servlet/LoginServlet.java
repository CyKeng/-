package com.keng.servlet;

import com.keng.Bean.Login;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

public class LoginServlet extends HttpServlet {
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        try{
            Class.forName("com.mysql.jdbc.Driver");
        }
        catch(Exception e){}
    }
    public String handleString(String s){
        try{  byte bb[]=s.getBytes("utf-8");
            s=new String(bb);
        }
        catch(Exception ee){}
        return s;
    }
    public void doPost(HttpServletRequest request,HttpServletResponse response)
            throws ServletException,IOException{
        Connection con;
        Statement sql;
        String logname=request.getParameter("logname"),
                password=request.getParameter("password");
        logname=handleString(logname);
        password=handleString(password);
        String uri="jdbc:mysql://localhost:3306/ECW?useUnicode=true&characterEncoding=utf-8";
        boolean boo=(logname.length()>0)&&(password.length()>0);
        System.out.println("目前一切顺利");
        try{
            con= DriverManager.getConnection(uri,"root","115351");
            String condition="select * from userinfo where name = '"+logname+
                    "' and password ='"+password+"'";
            sql=con.createStatement();
            if(boo){
                ResultSet rs=sql.executeQuery(condition);
                boolean m=rs.next();
                if(m==true){
                    //调用登录成功的方法:
                    System.out.println("成功了");
                    success(request,response,logname,password);
                    System.out.println("到这里来了吗");
                    System.out.println(logname);
                    if(logname.equals("admin")){
                        response.sendRedirect("admin.jsp");
                    }else{
                        response.sendRedirect("main.jsp");
                    }
                    /*RequestDispatcher dispatcher=
                            request.getRequestDispatcher("Login.jsp");//转发
                    dispatcher.forward(request,response);*/
                }
                else{
                    String backNews="您输入的用户名不存在，或密码不般配";
                    //调用登录失败的方法:
                    fail(request,response,logname,backNews);
                }
            }
            else{
                String backNews="请输入用户名和密码";
                fail(request,response,logname,backNews);
            }
            con.close();
        }
        catch(SQLException exp){
            String backNews=""+exp;
            fail(request,response,logname,backNews);
            System.out.println("出错了");
        }
    }
    public  void  doGet(HttpServletRequest request,HttpServletResponse response)
            throws ServletException,IOException{
        doPost(request,response);
    }
    public void success(HttpServletRequest request,HttpServletResponse response
            ,String logname,String password) {
        Login loginBean=null;
        HttpSession session=request.getSession(true);
        try{  loginBean=(Login)session.getAttribute("loginBean");
            if(loginBean==null){
                loginBean=new Login();  //创建新的数据模型
                session.setAttribute("loginBean",loginBean);
                loginBean=(Login)session.getAttribute("loginBean");
            }
            String name =loginBean.getName();
            if(name.equals(logname)) {
                loginBean.setBackNews(logname+"已经登录了");
                loginBean.setName(logname);
            }
            else {  //数据模型存储新的登录用户
                System.out.println("应该在这吧？");
                loginBean.setBackNews(logname+"登录成功");
                loginBean.setName(logname);
            }
        }
        catch(Exception ee){
            System.out.println("难道成功了？");
            loginBean=new Login();
            session.setAttribute("loginBean",loginBean);
            loginBean.setBackNews(logname+"登录成功");
            loginBean.setName(logname);
        }
    }
    public void fail(HttpServletRequest request, HttpServletResponse response
            , String logname, String backNews) {
        response.setContentType("text/html;charset=utf-8");
        try {
            PrintWriter out=response.getWriter();
            out.println("<html><body>");
            out.println("<h2>"+logname+"登录反馈结果<br>"+backNews+"</2>") ;
            out.println("返回登录页面或主页<br>");
            out.println("<a href =Login.jsp>登录页面</a>");
            out.println("</body></html>");
        }
        catch(IOException exp){}
    }
}
