<%@ page contentType="text/html;charset=utf-8" %>
<HTML>
<HEAD>
    <div align="center">
        <H3>电子商务网站</H3>
    </div>
</HEAD>
<BODY>
<div align="center">
    <form action="RegisterServlet" Method="post">
        <table border=2>
            <tr>
                <th>登录</th>
            </tr>
            <tr>
                <td>登录名称:<Input type=text name="name"></td>
            </tr>
            <tr>
                <td>输入密码:<Input type=password name="password"></td>
            </tr>
            <tr>
                <td>输入确认密码:<Input type=password name="password2"></td>
            </tr>
            <tr>
                <td>输入电话号码:<Input type=text name="phonenumber"></td>
            </tr>
            <tr>
                <td>输入电子邮箱:<Input type=text name="email"></td>
            </tr>

        </table>
        <Input type=submit name="g" value="提交">
        <a href="Login.jsp"><button type="button"  name="login" vaule="注册">注册好了？前往登录</button></a>
    </form>
</div>
<div align="center">
    <jsp:useBean id="userBean" class="com.keng.Bean.Register" scope="request"/>
    <jsp:getProperty name="userBean"  property="backNews" />
</div>
</BODY>
</HTML>

