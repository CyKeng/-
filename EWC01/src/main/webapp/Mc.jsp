<%@ page import="java.sql.*" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Mc</title>
</head>
<body>
<div align="center">
    <table border=2>
        <tr>
            <th>商品名称</th>
            <th>商品价格</th>
            <th>更新</th>
        </tr>
        <%!
            String button1 = "<form  action='addServlet' method = 'post'>" +
                    "<input type ='text' name='name' >" +
                    "<input type ='text' name='price' >" +
                    "<input type ='submit' name='add' value='添加' ></form>";
            String button2;
        %>
        <%
            try {
                Class.forName("com.mysql.jdbc.Driver");
                System.out.println("数据库驱动");
            } catch (Exception e) {
            }
            String uri = "jdbc:mysql://localhost:3306/ECW?useUnicode=true&characterEncoding=utf-8";
            Connection con;
            Statement sql;
            ResultSet rs;
            try {
                con = DriverManager.getConnection(uri, "root", "115351");
                System.out.println("数据库");
                sql = con.createStatement();
                //读取classify表，获得分类：
                rs = sql.executeQuery("SELECT * FROM product  ");
                if (rs == null) {
                    out.print("没有任何查询信息，无法浏览");
                    return;
                }
                //boolean boo = true;
                System.out.println(rs.getRow());
                for (int i = 1; rs.next(); i++) {
                    String name = rs.getString(2);
                    String price = rs.getDouble(3) + "";
                    String goods = "(" + name + "," + price + ")#" + price;
                    goods = goods.replaceAll("\\p{Blank}", "");
                    button2 = "<form  action='deServlet' method = 'post'>" +
                            "<input type ='hidden' name='pro' value='"+name+"' >" +
                            "<input type ='submit'  value='删除' ></form>";
                    out.print("<tr>");
                    out.print("<td>" + name + "</td>");
                    out.print("<td>" + price + "</td>");
                    out.print("<td>" + button2 + "</td>");
                    out.print("</tr>");
                    //boo = rs.next();
                }
            } catch (SQLException e) {
                out.print(e);
            }
        %>
    </table>
    <% out.print("<td>" + button1 + "</td>");%>

</div>
</body>
</html>
