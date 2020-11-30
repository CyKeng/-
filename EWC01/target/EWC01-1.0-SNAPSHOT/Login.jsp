<%@ page contentType="text/html;charset=utf-8" %>
<HTML>
<HEAD>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <div align="center">
        <H3>电子商务网站</H3>
    </div>
</HEAD>
<BODY>
<div align="center">
    <form action="LoginServlet" Method="post">
        <table border=2>
            <tr>
                <th>登录</th>
            </tr>
            <tr>
                <td>登录名称:<Input type=text name="logname"></td>
            </tr>
            <tr>
                <td>输入密码:<Input type=password name="password"></td>
            </tr>
        </table>
        <Input type=submit name="g" value="提交">
        <a href="Register.jsp"><button type="button"  name="login" vaule="注册">还没注册？前往注册</button></a>
    </form>
</div>

<div align="center">
    <jsp:useBean id="LoginBean" class="com.keng.Bean.Login" scope="request"/>
    <jsp:getProperty name="LoginBean"  property="backNews" />
</div>

</BODY>
</HTML>

