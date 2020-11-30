<%@ page import="java.sql.*" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Ss</title>
</head>
<body>
<div align="center">
    <table border=2>
        <tr>
            <th>商品名称</th>
            <th>商品售出数量</th>
        </tr>
        <%!
            String button = "<form  action='SServlet' method = 'post'>" +
                    "<input type ='submit'  value='返回' ></form>";
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
                rs = sql.executeQuery("SELECT * FROM market");
                if (rs == null) {
                    System.out.println("这里呢");
                    out.print("没有任何查询信息，无法浏览");
                    System.out.println("查询不到");
                    return;
                }
                //boolean boo = true;
                System.out.println(rs.getRow());
                for (int i = 1; rs.next(); i++) {
                    String name = rs.getString(2);
                    String quantity = rs.getDouble(3) + "";
                    out.print("<tr>");
                    out.print("<td>" + name + "</td>");
                    out.print("<td>" + quantity + "</td>");
                    out.print("</tr>");
                    //boo = rs.next();
                }
            } catch (SQLException e) {
                out.print(e);
            }
        %>
    </table>
    <% out.print("<td>" + button + "</td>");%>

</div>
</body>
</html>
