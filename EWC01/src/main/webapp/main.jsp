<%@ page contentType="text/html;charset=utf-8" %>
<%@ page import="java.sql.*" %>
<HTML>
<HEAD></HEAD>
<BODY>
<div align="center">
    <table border=2>
        <tr>
            <th>商品名称</th>
            <th>商品价格</th>
            <td>添加到购物车</td>
        </tr>
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
                System.out.println("查询");
                if (rs == null) {
                    out.print("没有任何查询信息，无法浏览");
                    return;
                }
                //boolean boo = true;
                System.out.println(rs.getRow());
                for (int i = 1; rs.next(); i++) {
                    String name = rs.getString(2);
                    String price = rs.getDouble(3) + "";
                    String goods = "(" + name + "," + price + ")#" + price;//便于购物车计算价格,尾缀上"#价格值"
                    goods = goods.replaceAll("\\p{Blank}", "");
                    String button = "<form  action='putGoodsServlet' method = 'post'>" +
                            "<input type ='hidden' name='java' value= " + goods + ">" +
                            "<input type ='submit'  value='放入购物车' ></form>";
                    out.print("<tr>");
                    out.print("<td>" + name + "</td>");
                    out.print("<td>" + price + "</td>");
                    out.print("<td>" + button + "</td>");
                    out.print("</tr>");
                    //boo = rs.next();
                }
            } catch (SQLException e) {
                out.print(e);
            }
        %>
    </table>
</div>
</BODY>
</HTML>
