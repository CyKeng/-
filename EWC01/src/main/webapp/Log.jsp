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
            <th>客户</th>
            <th>总消费金额</th>
        </tr>
        <%!
            String button = "<form  action='LogServlet' method = 'post'>" +
                    "<input type ='submit'  value='返回' ></form>";
        %>
        <%
            try {
                Class.forName("com.mysql.jdbc.Driver");
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
                rs = sql.executeQuery("SELECT * FROM bill");
                if (rs == null) {
                    out.print("没有任何查询信息，无法浏览");
                    return;
                }
                //boolean boo = true;
                System.out.println(rs.getRow());
                for (int i = 1; rs.next(); i++) {
                    String name = rs.getString(3);
                    String totalprice = rs.getDouble(2) + "";
                    out.print("<tr>");
                    out.print("<td>" + name + "</td>");
                    out.print("<td>" + totalprice + "</td>");
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
