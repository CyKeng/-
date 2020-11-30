<%@ page contentType="text/html;charset=utf-8" %>
<%@ page import="com.keng.Bean.Login" %>
<%@ page import="java.util.*" %>
<jsp:useBean id="loginBean" class="com.keng.Bean.Login" scope="session"/>
<HTML>
<BODY>
<div align="center">
    <%
        if (loginBean == null) {
            response.sendRedirect("Login.jsp");//重定向到登录页面
        } else {
            boolean b = loginBean.getName() == null ||
                    loginBean.getName().length() == 0;
            if (b)
                response.sendRedirect("Login.jsp");//重定向到登录页面
        }
        LinkedList car = loginBean.getCar();
        if (car == null)
            out.print("<h2> 购物车没有物品.</h2>");
        else {
            Iterator<String> iterator = car.iterator();
            StringBuffer buyGoods = new StringBuffer();
            int n = 0;
            double priceSum = 0;
            out.print("购物车中的物品：<table border=2>");
            while (iterator.hasNext()) {
                String goods = iterator.next();
                String showGoods = "";
                n++;
                //购车车物品的后缀是“#价格数字"，比如“化妆品价格3989 #3989”
                int index = goods.lastIndexOf("#");
                if (index != -1) {
                    priceSum += Double.parseDouble(goods.substring(index + 1));
                    showGoods = goods.substring(0, index);
                }
                int start = showGoods.indexOf("(");
                int end = showGoods.indexOf(",");
                String product = showGoods.substring(start,end);
                buyGoods.append(product+"),");
                String del = "<form  action='Deleteservlet' method = 'post'>" +
                        "<input type ='hidden' name='delete' value= " + goods + ">" +
                        "<input type ='submit'  value='删除' ></form>";

                out.print("<tr><td>" + showGoods + "</td>");
                out.print("<td>" + del + "</td></tr>");
            }
            out.print("</table>");
            out.print("<p>合计" + priceSum + "元<p>");
            String orderForm = "<form action='buyServlet' method='post'>" +
                    " <input type ='hidden' name='buy' value= " + buyGoods + " >" +
                    " <input type ='hidden' name='price' value= " + priceSum + " >" +
                    "<input type ='submit'  value='支付'></form>";
            out.print(orderForm);
        }
    %>
</div>

</BODY>
</HTML>
