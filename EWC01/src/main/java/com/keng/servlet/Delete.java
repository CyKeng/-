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
import java.util.LinkedList;

public class Delete extends HttpServlet {
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }
    public  void  doPost(HttpServletRequest request,HttpServletResponse response)
            throws ServletException,IOException {
        request.setCharacterEncoding("utf-8");
        String delete = request.getParameter("delete");
        Login loginBean=null;
        HttpSession session=request.getSession(true);
        try{  loginBean=(Login)session.getAttribute("loginBean");
            boolean b =loginBean.getName()==null||
                    loginBean.getName().length()==0;
            if(b)
                response.sendRedirect("Login.jsp");//重定向到登录页面
            LinkedList<String> car = loginBean.getCar();
            car.remove(delete);
        }
        catch(Exception exp){
            response.sendRedirect("Login.jsp");//重定向到登录页面
        }
        RequestDispatcher dispatcher=
                request.getRequestDispatcher("LookShoppingCar.jsp");
        dispatcher.forward(request, response);//转发
    }
    public  void  doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request,response);
    }
}
