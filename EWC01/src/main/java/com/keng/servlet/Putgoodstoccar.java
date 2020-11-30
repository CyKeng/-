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
import java.util.LinkedList;

public class Putgoodstoccar extends HttpServlet {
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }
    public  void  doPost(HttpServletRequest request,HttpServletResponse response)
            throws ServletException,IOException {
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        String goods = request.getParameter("java");
        Login loginBean=null;
        HttpSession session=request.getSession(true);
        try{  loginBean=(Login)session.getAttribute("loginBean");
            boolean b =loginBean.getName()==null||
                    loginBean.getName().length()==0;
            if(b)
                response.sendRedirect("Login.jsp");//重定向到登录页面
            LinkedList<String> car = loginBean.getCar();
            car.add(goods);
            speakSomeMess(request,response,goods);
        }
        catch(Exception exp){
            response.sendRedirect("Login.jsp");//重定向到登录页面
        }
    }
    public  void  doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request,response);
    }
    public void speakSomeMess(HttpServletRequest request,
                              HttpServletResponse response,String goods) {
        response.setContentType("text/html;charset=utf-8");
        try {
            System.out.println(goods);
            PrintWriter out=response.getWriter();
            out.println("<html><body>");
            out.println("<h2>"+goods+"放入购物车</h2>") ;
            out.println("查看购物车或返回浏览商品<br>");
            out.println("<a href =LookShoppingCar.jsp>查看购物车</a>");
            out.println("<br><a href =main.jsp>浏览商品</a>");
            out.println("</body></html>");
        }
        catch(IOException exp){}
    }
}
